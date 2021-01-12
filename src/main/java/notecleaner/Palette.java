package notecleaner;

import static notecleaner.SampleProcessor.sampleProcessorWithOptions;

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
    Set<Color> colorGroups = KMeansColorAdapter.getColorGroups(foregroundColors);
    return new Palette(bg, colorGroups, options);
  }

  public Picture applyTo(Picture pic) {
    Picture appliedTo = Utils.createPicture(pic.getWidth(), pic.getHeight());
    for (int i = 0; i < pic.getWidth(); i++) {
      for (int j = 0; j < pic.getHeight(); j++) {
        Color cur = pic.getPixel(i, j);
        if (Cleaner.isForegroundColor(cur, background, options)) {
          appliedTo.setPixel(i, j, new Color(closestGroup(cur, foreground)));
        } else {
          appliedTo.setPixel(i, j, new Color(background));
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
