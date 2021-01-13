import java.io.File;
import java.nio.file.FileSystemException;
import java.nio.file.NotDirectoryException;
import notecleaner.Cleaner;
import notecleaner.OptionsBuilder;
import org.apache.commons.io.FilenameUtils;
import picture.Picture;
import picture.Utils;

public class CleanerMiddleMan {

  public static String cleanImagesInFolder(String directoryPath) throws FileSystemException {
    File dir = new File(directoryPath); // .../tmpfiles/10234/
    File[] directoryListing = dir.listFiles(); // All uploaded images (could be from pdf)
    if (directoryListing == null) throw new NotDirectoryException(directoryPath);

    // .../tmpfiles/10234/cleaned/
    File outDir = new File(directoryPath + File.separator + "cleaned");
    if (!outDir.exists()) {
      if (!outDir.mkdirs()) {
        throw new FileSystemException(
            outDir.getAbsolutePath(), null, "Could not make directory: " + outDir.getName());
      }
    }
    for (File img : directoryListing) {
      if (img.isDirectory()) continue; // Skip over the 'cleaned/' folder we just made
      // Clean the picture using notecleaner library
      Picture pic = Utils.loadPicture(img.getAbsolutePath());
      if (pic == null) continue;
      Cleaner cleaner = new Cleaner(pic, OptionsBuilder.defaultOptions().create());
      Picture cleaned = cleaner.clean();
      Utils.savePicture(
          cleaned,
          outDir
              + File.separator
              + FilenameUtils.getBaseName(img.getAbsolutePath())
              + "-cleaned.png"); // Save as .../tmpfiles/10234/cleaned/<img-name>-cleaned.png
    }
    return outDir.getName(); // returns "cleaned"
  }
}
