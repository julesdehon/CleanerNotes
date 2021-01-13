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
    Part filePart = request.getPart("file");
    String fileName = filePart.getSubmittedFileName();
    String uploadFilePath = request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName;
    for (Part part : request.getParts()) {
      part.write(uploadFilePath);
    }
    String cleanedPath = CleanerMiddleMan.handle(uploadFilePath);
    try (PrintWriter writer = response.getWriter()) {
      writer.println("<!DOCTYPE html><html>");
      writer.println("<head>");
      writer.println("<meta charset=\"UTF-8\" />");
      writer.println("<title>Retrieve your cleaned image!</title>");
      writer.println("</head>");
      writer.println("<body>");

      writer.println("Click <a href=\"UploadServlet?fileName=" + cleanedPath + "\">here</a> to download your file");

      writer.println("</body>");
      writer.println("</html>");
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String fileName = request.getParameter("fileName");
    if (fileName == null || fileName.equals("")) {
      throw new ServletException("File name can't be empty");
    }
    File file = new File(fileName);
    if (!file.exists()) {
      throw new ServletException("File doesn't exist on the server");
    }
    ServletContext ctx = getServletContext();
    InputStream fis = new FileInputStream(file);
    String mimeType = ctx.getMimeType(file.getAbsolutePath());
    response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
    response.setContentLength((int) file.length());
    response.setHeader("Content-Disposition", "attachment;filename=\"cleaned.jpg\"");
    ServletOutputStream os = response.getOutputStream();
    byte[] bufferData = new byte[1024];
    int read = 0;
    while ((read = fis.read(bufferData)) != -1) {
      os.write(bufferData, 0, read);
    }
    os.flush();
    os.close();
    fis.close();
  }
}
