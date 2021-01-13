import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;

@WebServlet(name = "UploadServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
    maxFileSize = 1024 * 1024 * 50, // 50 MB
    maxRequestSize = 1024 * 1024 * 100 // 100 MB
    )
public class UploadServlet extends HttpServlet {

  private static final AtomicInteger counter = new AtomicInteger(1);

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Problems problems = new Problems();

    // Should be .../tmpdir
    String tmpDir = request.getServletContext().getAttribute("FILES_DIR").toString();

    /* Clean up the tmp folder so we don't have old files lying around */
    AgeFileFilter filter = new AgeFileFilter(System.currentTimeMillis() - 20 * 1000);
    File[] children = new File(tmpDir).listFiles();
    if (children != null) {
      for (File toDelete : FileFilterUtils.filter(filter, children)) {
        FileUtils.forceDelete(toDelete);
      }
    }

    String nextFolder = String.valueOf(counter.getAndIncrement()); // next available folder
    String uploadDir = tmpDir + File.separator + nextFolder;
    File ud = new File(uploadDir);
    // Clean or create this new folder where we're going to upload the files
    if (ud.exists()) {
      FileUtils.cleanDirectory(ud);
    } else {
      if (!ud.mkdirs()) {
        problems.encountered("Could not create upload directory at " + ud.getAbsolutePath(),
            "An error occurred on the server's file system, please contact the site owner", true);
        Problems.goToErrorPage(request, response, problems);
        return;
      }
    }
    // Upload all files to this new folder
    String fileName;
    for (Part part : request.getParts()) {
      fileName = part.getSubmittedFileName();
      try {
        part.write(uploadDir + File.separator + fileName);
      } catch (IOException e) {
        problems.encountered("Could not upload file " + fileName + ", continued without it", false);
      }
    }
    // Will usually just return "cleaned"
    String cleanedPath = CleanerMiddleMan.cleanImagesInFolder(uploadDir, problems);

    if (problems.fatalProblemEncountered()) {
      FileUtils.forceDelete(ud);
      Problems.goToErrorPage(request, response, problems);
      return;
    }

    // New page just has a download button, which links to DownloadPdfServlet, and has the name
    // of the folder where the cleaned images are as a parameter
    RequestDispatcher rd = request.getRequestDispatcher("retrieve.jsp");
    request.setAttribute("cleanedDir", nextFolder + File.separator + cleanedPath);
    request.setAttribute("problems", problems.getUserDescriptions());
    rd.forward(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}
}
