import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "UploadServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
    maxFileSize = 1024 * 1024 * 50,       // 50 MB
    maxRequestSize = 1024 * 1024 * 100    // 100 MB
)
public class UploadServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    /* Receive file uploaded to the Servlet from the HTML5 form */
    String uploadDir = request.getServletContext().getAttribute("FILES_DIR").toString();
    String fileName;
    for (Part part : request.getParts()) {
      fileName = part.getSubmittedFileName();
      System.out.println(fileName);
      part.write(uploadDir + File.separator + fileName);
    }
    String cleanedPath = CleanerMiddleMan.cleanImagesInFolder(uploadDir);
    try (PrintWriter writer = response.getWriter()) {
      writer.println("<!DOCTYPE html><html>");
      writer.println("<head>");
      writer.println("<meta charset=\"UTF-8\" />");
      writer.println("<title>Retrieve your cleaned image!</title>");
      writer.println("</head>");
      writer.println("<body>");

      writer.println("Click <a href=\"DownloadPdfServlet?cleanedDir=" + cleanedPath + "\">here</a> to download your file");

      writer.println("</body>");
      writer.println("</html>");
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }
}
