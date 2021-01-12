package notecleaner;

import picture.Picture;
import picture.Utils;

public class Client {

  public static void main(String[] args) {
    Picture pic = Utils.loadPicture("src/test/image/notes-white.jpg");
    Cleaner cleaner = new Cleaner(pic, OptionsBuilder.defaultOptions().create());
    Picture cleaned = cleaner.clean();
    Utils.savePicture(cleaned, "src/test/image/notes-white-cleaned.jpg");
  }
}
