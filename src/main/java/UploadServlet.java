import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
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
    Part filePart = request.getPart("file");
    String fileName = filePart.getSubmittedFileName();
    String uploadFilePath = request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName;
    for (Part part : request.getParts()) {
      part.write(uploadFilePath);
    }
    response.getWriter().print("The file uploaded sucessfully.");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }
}
