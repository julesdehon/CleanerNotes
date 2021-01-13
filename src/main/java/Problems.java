import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Problems {

  private boolean fatal;
  private List<String> advancedDescriptions;
  private List<String> userDescriptions;

  public Problems() {
    fatal = false;
    advancedDescriptions = new ArrayList<>();
    userDescriptions = new ArrayList<>();
  }

  public void encountered(String userDesc, boolean fatal) {
    this.fatal |= fatal;
    advancedDescriptions.add(userDesc);
    userDescriptions.add(userDesc);
  }

  public void encountered(String advancedDesc, String userDesc, boolean fatal) {
    this.fatal |= fatal;
    advancedDescriptions.add(advancedDesc);
    userDescriptions.add(userDesc);
  }

  public boolean fatalProblemEncountered() {
    return fatal;
  }

  public List<String> getAdvancedDescriptions() {
    return advancedDescriptions;
  }

  public List<String> getUserDescriptions() {
    return userDescriptions;
  }

  public static void goToErrorPage(
      HttpServletRequest request, HttpServletResponse response, Problems problems)
      throws ServletException, IOException {
    RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
    request.setAttribute("problems", problems.getUserDescriptions());
    rd.forward(request, response);
  }
}
