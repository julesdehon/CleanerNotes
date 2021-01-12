package notecleaner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import picture.Color;
import picture.Picture;

public class Cleaner {

  private Picture pic;
  private Options options;

  public Cleaner(Picture pic, Options options) {
    this.pic = pic;
    this.options = options;
  }

  public Picture clean() {
    List<Color> sample = sample(options.getSampleFraction());
    Palette palette = Palette.getPaletteFor(sample, options);
    return palette.applyTo(pic);
  }

  private List<Color> sample(double percent) {
    List<Color> colors = new ArrayList<>();
    Random rand = new Random();
    for (int i = 0; i < percent * (pic.getWidth() * pic.getHeight()); i++) {
      colors.add(
          new Color(pic.getPixel(rand.nextInt(pic.getWidth()), rand.nextInt(pic.getHeight()))));
    }
    return colors;
  }

  public static boolean isForegroundColor(Color color, Color bg, Options options) {
    double bgSaturation = bg.getSaturation();
    double bgValue = bg.getValue();
    return Math.abs(color.getSaturation() - bgSaturation) > options.getSaturationThreshold()
        || Math.abs(color.getValue() - bgValue) > options.getValueThreshold();
  }
}
