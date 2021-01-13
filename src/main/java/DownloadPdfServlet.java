import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.Comparator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

@WebServlet(name = "DownloadPdfServlet")
public class DownloadPdfServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Problems problems = new Problems();

    /* Get the right directory where all the cleaned images are */
    String cleanedDirName = request.getParameter("cleanedDir");
    if (cleanedDirName == null || cleanedDirName.equals("")) {
      problems.encountered("cleanedDir parameter passed to DownloadPdfServlet was null or empty",
          "Did you arrive at this page from somewhere else than the upload image page? Please return to the homepage", true);
      Problems.goToErrorPage(request, response, problems);
      return;
    }
    String tmpDir = request.getServletContext().getAttribute("FILES_DIR").toString();
    File cleanedDir = new File(tmpDir + File.separator + cleanedDirName);
    if (!cleanedDir.exists() || !cleanedDir.isDirectory()) {
      problems.encountered("cleanedDir passed to DownloadPdfServlet did not exist, or wasn't a directory",
          "Perhaps you took too long to click the download button. Your cleaned images couldn't be found", true);
      Problems.goToErrorPage(request, response, problems);
      return;
    }

    /* Create a pdf document with all the images that are in this cleaned images folder */
    Document document = new Document(PageSize.A4, 20.0F, 20.0F, 20.0F, 150.0F);
    String docPath = cleanedDir.getAbsolutePath() + File.separator + "clean.pdf";
    File doc = new File(docPath);
    FileOutputStream fos = new FileOutputStream(doc);
    try {
      PdfWriter.getInstance(document, fos);
    } catch (DocumentException e) {
      problems.encountered("Could not instantiate PdfWriter - threw a DocumentException",
          "The image to pdf converter does not seem to be working, please contact the site owner", true);
      Problems.goToErrorPage(request, response, problems);
      return;
    }
    document.open();
    File[] cleanedImgs = cleanedDir.listFiles();
    if (cleanedImgs == null) {
      problems.encountered("Could not list the files in cleanedDir, but it is a directory, so an IOException occurred",
          "An error occurred when trying to find your cleaned images. Please contact the site owner.", true);
      Problems.goToErrorPage(request, response, problems);
      return;
    }
    Arrays.sort(cleanedImgs, new AlphaNumericFileComparator()); // Sort alphanumerically

    for (File imgFile : cleanedImgs) {
      Image image;
      try {
        image = Image.getInstance(imgFile.getAbsolutePath());
      } catch (Exception e) {
        problems.encountered("Could not load the image " + imgFile.getName() + " into the pdf, continued without it", false);
        continue;
      }
      // Scale to fit A4
      if (image.getWidth() > image.getHeight()) {
        image.scaleToFit(Float.MAX_VALUE, PageSize.A4.getHeight());
      } else {
        image.scaleToFit(PageSize.A4.getWidth(), Float.MAX_VALUE);
      }
      document.setPageSize(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));
      document.newPage();
      image.setAbsolutePosition(0, 0);
      try {
        document.add(image);
      } catch (DocumentException ignored) {
        problems.encountered("Could not load the image " + imgFile.getName() + " into the pdf, continued without it", false);
      }
    }
    document.close();

    /* Make the user's browser download the pdf as "cleaned.pdf" */
    ServletContext ctx = getServletContext();
    InputStream fis = new FileInputStream(doc);
    String mimeType = ctx.getMimeType(doc.getAbsolutePath());
    response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
    response.setContentLength((int) doc.length());
    response.setHeader("Content-Disposition", "attachment;filename=\"cleaned.pdf\"");
    ServletOutputStream os = response.getOutputStream();
    byte[] bufferData = new byte[1024];
    int read;
    while ((read = fis.read(bufferData)) != -1) {
      os.write(bufferData, 0, read);
    }
    os.flush();
    os.close();
    fis.close();

    /* Delete the whole directory we were just working in */
    File uploadDir = new File(cleanedDir.getParent());
    if (uploadDir.exists() && uploadDir.isDirectory()) {
      FileUtils.deleteDirectory(uploadDir);
    }
  }
}
