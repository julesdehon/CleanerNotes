import java.io.File;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class FileLocationContextListener implements ServletContextListener {

  // Public constructor is required by servlet spec
  public FileLocationContextListener() {}

  // -------------------------------------------------------
  // ServletContextListener implementation
  // -------------------------------------------------------
  public void contextInitialized(ServletContextEvent sce) {
    /* This method is called when the servlet context is
       initialized(when the Web application is deployed).
       You can initialize servlet context related data here.
    */
    String rootPath = System.getProperty("catalina.home");
    ServletContext ctx = sce.getServletContext();
    String relativePath = ctx.getInitParameter("tempfile.dir");
    File file = new File(rootPath + File.separator + relativePath);
    if (!file.exists()) file.mkdirs();
    System.out.println(
        "File Directory created to be used for storing files, " + file.getAbsolutePath());
    ctx.setAttribute("FILES_DIR_FILE", file);
    ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
  }

  public void contextDestroyed(ServletContextEvent sce) {
    /* This method is invoked when the Servlet Context
       (the Web application) is undeployed or
       Application Server shuts down.
    */
  }
}
