import notecleaner.Cleaner;
import notecleaner.OptionsBuilder;
import picture.Picture;
import picture.Utils;

public class CleanerMiddleMan {

  public static String handle(String fileName) {
    Picture pic = Utils.loadPicture(fileName);
    if (pic == null) return null;
    Cleaner cleaner = new Cleaner(pic, OptionsBuilder.defaultOptions().create());
    Picture cleaned = cleaner.clean();
    String newFileName = removeExtension(fileName) + "-cleaned" + getExtension(fileName);
    Utils.savePicture(cleaned, newFileName);
    return newFileName;
  }

  private static String removeExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    return fileName.substring(0, dotIndex);
  }

  private static String getExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    return fileName.substring(dotIndex);
  }
}
