import java.io.File;
import notecleaner.Cleaner;
import notecleaner.Options;
import org.apache.commons.io.FilenameUtils;
import picture.Picture;
import picture.Utils;

public class CleanerMiddleMan {

  public static String cleanImagesInFolder(
      String directoryPath, Options options, Problems problems) {
    File dir = new File(directoryPath); // .../tmpfiles/10234/
    File[] directoryListing = dir.listFiles(); // All uploaded images (could be from pdf)
    if (directoryListing == null) {
      problems.encountered(
          "Could not list the uploaded images at " + dir.getAbsolutePath(),
          "An error occurred when trying to retrieve your uploaded images. Please contact the site owner",
          true);
      return null;
    }

    // .../tmpfiles/10234/cleaned/
    File outDir = new File(directoryPath + File.separator + "cleaned");
    if (!outDir.exists()) {
      if (!outDir.mkdirs()) {
        problems.encountered(
            "Could not make directory: " + outDir.getAbsolutePath(),
            "An error occurred when trying to create a folder on the server. Please contact the site owner",
            true);
        return null;
      }
    }
    for (File img : directoryListing) {
      if (img.isDirectory()) continue; // Skip over the 'cleaned/' folder we just made
      // Clean the picture using notecleaner library
      Picture pic = Utils.loadPicture(img.getAbsolutePath());
      if (pic == null) {
        problems.encountered(
            "Failed to load image "
                + img.getName()
                + ", perhaps it is not a supported file type. Continued without it",
            false);
        continue;
      }
      Cleaner cleaner = new Cleaner(pic, options);
      Picture cleaned;
      try {
        cleaned = cleaner.clean();
      } catch (Exception e) {
        problems.encountered(
            "Could not clean notes. If you think the site should have been able to "
                + "clean your notes, please contact the site owner with details of the failure",
            true);
        return null;
      }
      boolean saved =
          Utils.savePicture(
              cleaned,
              outDir
                  + File.separator
                  + FilenameUtils.getBaseName(img.getAbsolutePath())
                  + "-cleaned.jpg"); // Save as .../tmpfiles/10234/cleaned/<img-name>-cleaned.jpg
      if (!saved) {
        problems.encountered(
            "Could not save cleaned version of " + img.getName() + ". Continued without it", false);
      }
    }
    return outDir.getName(); // returns "cleaned"
  }
}
