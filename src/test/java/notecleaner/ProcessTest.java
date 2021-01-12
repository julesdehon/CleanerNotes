package notecleaner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import picture.Color;
import picture.Utils;

class ProcessTest {

//  @Test
//  void getBackgroundColor() {
//    Process process = new Process(Utils.loadPicture("src/test/image/notes-white.jpg"));
//    Color bg = process.getBackgroundColor();
//    assertTrue(isNearColor(bg, new Color(240, 240, 240), 5));
//
//    process = new Process(Utils.loadPicture("src/test/image/notes-white-2.jpg"));
//    bg = process.getBackgroundColor();
//    assertTrue(isNearColor(bg, new Color(215, 215, 210), 5));
//
//    process = new Process(Utils.loadPicture("src/test/image/notes-yellow.jpg"));
//    bg = process.getBackgroundColor();
//    assertTrue(isNearColor(bg, new Color(255, 255, 165), 5));
//  }
//
//  private static boolean isNearColor(Color bg, Color color, int within) {
//    boolean redWithin = Math.abs(bg.getRed() - color.getRed()) < within;
//    boolean greenWithin = Math.abs(bg.getGreen() - color.getGreen()) < within;
//    boolean blueWithin = Math.abs(bg.getBlue() - color.getBlue()) < within;
//    return redWithin && greenWithin && blueWithin;
//  }
}
