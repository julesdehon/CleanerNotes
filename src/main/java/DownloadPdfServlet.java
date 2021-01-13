import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NotDirectoryException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DownloadPdfServlet")
public class DownloadPdfServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String cleanedDirName = request.getParameter("cleanedDir");
    if (cleanedDirName == null || cleanedDirName.equals("")) {
      throw new ServletException("Cleaned directory name can't be empty");
    }
    File cleanedDir = new File(cleanedDirName);
    if (!cleanedDir.exists() || !cleanedDir.isDirectory()) {
      throw new ServletException("Something is wrong with the cleaned directory");
    }
    Document document = new Document(PageSize.A4, 20.0F, 20.0F, 20.0F, 150.0F);
    String docPath = cleanedDirName + File.separator + "clean.pdf";
    File doc = new File(docPath);
    FileOutputStream fos = new FileOutputStream(doc);
    try {
      PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    document.open();
    File[] cleanedImgs = cleanedDir.listFiles();
    if (cleanedImgs == null) throw new NotDirectoryException(cleanedDirName);
    for (File imgFile : cleanedImgs) {
      Image image = null;
      try {
        image = Image.getInstance(imgFile.getAbsolutePath());
      } catch (Exception e) {
        continue;
      }
      document.setPageSize(image);
      document.newPage();
      assert image != null;
      image.setAbsolutePosition(0, 0);
      try {
        document.add(image);
      } catch (DocumentException ignored) {
      }
    }
    document.close();

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
  }
}