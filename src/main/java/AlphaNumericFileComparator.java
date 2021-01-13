import java.io.File;
import java.util.Comparator;

public class AlphaNumericFileComparator implements Comparator<File> {

  @Override
  public int compare(File f1, File f2) {
    String f1NumS = f1.getName().replaceAll("\\D", "");
    Integer f1Num = f1NumS.isEmpty() ? 0 : Integer.parseInt(f1NumS);

    String f2NumS = f2.getName().replaceAll("\\D", "");
    Integer f2Num = f2NumS.isEmpty() ? 0 : Integer.parseInt(f2NumS);

    if (!f1Num.equals(f2Num)) {
      return f1Num.compareTo(f2Num);
    } else {
      return f1.getName().compareTo(f2.getName());
    }
  }
}
