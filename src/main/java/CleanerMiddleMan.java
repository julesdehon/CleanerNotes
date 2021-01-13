import java.io.File;
import java.nio.file.FileSystemException;
import java.nio.file.NotDirectoryException;
import notecleaner.Cleaner;
import notecleaner.OptionsBuilder;
import picture.Picture;
import picture.Utils;

public class CleanerMiddleMan {

  public static String cleanImagesInFolder(String directoryPath) throws FileSystemException {
    File dir = new File(directoryPath);
    File[] directoryListing = dir.listFiles();
    if (directoryListing == null) throw new NotDirectoryException(directoryPath);

    File outDir = new File(directoryPath + File.separator + "cleaned");
    if (!outDir.exists()) {
      if (!outDir.mkdirs()) {
        throw new FileSystemException(
            outDir.getAbsolutePath(), null, "Could not make directory: " + outDir.getName());
      }
    }
    for (File img : directoryListing) {
      if (img.isDirectory()) continue;
      Picture pic = Utils.loadPicture(img.getAbsolutePath());
      if (pic == null) continue;
      Cleaner cleaner = new Cleaner(pic, OptionsBuilder.defaultOptions().create());
      Picture cleaned = cleaner.clean();
      Utils.savePicture(
          cleaned,
          outDir
              + File.separator
              + removeExtension(img.getName())
              + "-cleaned.png");
    }
    return outDir.getAbsolutePath();
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
