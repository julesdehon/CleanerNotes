import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;

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

    // Should be .../tmpdir
    String tmpDir = request.getServletContext().getAttribute("FILES_DIR").toString();
    String nextFolder = String.valueOf(counter.getAndIncrement()); // next available folder
    String uploadDir = tmpDir + File.separator + nextFolder;
    File ud = new File(uploadDir);
    // Clean or create this new folder where we're going to upload the files
    if (ud.exists()) {
      FileUtils.cleanDirectory(ud);
    } else {
      ud.mkdirs();
    }
    // Upload all files to this new folder
    String fileName;
    for (Part part : request.getParts()) {
      fileName = part.getSubmittedFileName();
      part.write(uploadDir + File.separator + fileName);
    }
    // Will usually just return "cleaned"
    String cleanedPath = CleanerMiddleMan.cleanImagesInFolder(uploadDir);

    // New page just has a download button, which links to DownloadPdfServlet, and has the name
    // of the folder where the cleaned images are as a parameter
    try (PrintWriter writer = response.getWriter()) {
      writer.println("<!DOCTYPE html><html>");
      writer.println("<head>");
      writer.println("<meta charset=\"UTF-8\" />");
      writer.println("<title>Retrieve your cleaned image!</title>");
      writer.println("</head>");
      writer.println("<body>");

      writer.println(
          "Click <a href=\"DownloadPdfServlet?cleanedDir="
              + nextFolder
              + File.separator
              + cleanedPath
              + "\">here</a> to download your file");

      writer.println("</body>");
      writer.println("</html>");
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}
}
