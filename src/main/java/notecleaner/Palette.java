package notecleaner;

import static notecleaner.SampleProcessor.sampleProcessorWithOptions;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import picture.Color;
import picture.Picture;
import picture.Utils;

public class Palette {

  private final Color background;
  private final Set<Color> foreground;
  private final Options options;

  public Palette(Color background, Set<Color> foreground, Options options) {
    this.background = background;
    this.foreground = foreground;
    this.options = options;
  }

  public static Palette getPaletteFor(List<Color> sample, Options options) {
    SampleProcessor processor = sampleProcessorWithOptions(options);
    Color bg = processor.getBackgroundColor(sample);
    List<Color> foregroundColors = processor.getForegroundColors(sample, bg);
    Set<Color> colorGroups =
        KMeansColorAdapter.getColorGroups(foregroundColors, options.getNumColors());
    if (options.isSaturate()) saturate(colorGroups);
    return new Palette(bg, colorGroups, options);
  }

  private static void saturate(Collection<Color> colorGroups) {
    int min = 255;
    int max = 0;
    for (Color color : colorGroups) {
      min = Math.min(min, Math.min(color.getRed(), Math.min(color.getGreen(), color.getBlue())));
      max = Math.max(max, Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue())));
    }
    for (Color color : colorGroups) {
      color.setRed((int) Math.round(255 * ((double) (color.getRed() - min) / (max - min))));
      color.setGreen((int) Math.round(255 * ((double) (color.getGreen() - min) / (max - min))));
      color.setBlue((int) Math.round(255 * ((double) (color.getBlue() - min) / (max - min))));
    }
  }

  public Picture applyTo(Picture pic) {
    Picture appliedTo = Utils.createPicture(pic.getWidth(), pic.getHeight());
    for (int i = 0; i < pic.getWidth(); i++) {
      for (int j = 0; j < pic.getHeight(); j++) {
        Color cur = pic.getPixel(i, j);
        if (Cleaner.isForegroundColor(cur, background, options)) {
          appliedTo.setPixel(i, j, new Color(closestGroup(cur, foreground)));
        } else {
          if (options.isWhiteBackground()) {
            appliedTo.setPixel(i, j, new Color(255, 255, 255));
          } else {
            appliedTo.setPixel(i, j, new Color(background));
          }
        }
      }
    }
    return appliedTo;
  }

  private static Color closestGroup(Color color, Set<Color> colorGroups) {
    double minDistance = Integer.MAX_VALUE;
    Color closestColor = null;
    for (Color group : colorGroups) {
      int rDiff = group.getRed() - color.getRed();
      int gDiff = group.getGreen() - color.getGreen();
      int bDiff = group.getBlue() - color.getBlue();
      double dist = Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
      if (dist < minDistance) {
        minDistance = dist;
        closestColor = group;
      }
    }
    return closestColor;
  }
}
