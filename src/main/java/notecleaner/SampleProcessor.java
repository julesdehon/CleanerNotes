package notecleaner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import picture.Color;

public class SampleProcessor {

  private final Options options;

  private SampleProcessor(Options options) {
    this.options = options;
  }

  public static SampleProcessor defaultSampleProcessor() {
    return new SampleProcessor(OptionsBuilder.defaultOptions().create());
  }

  public static SampleProcessor sampleProcessorWithOptions(Options options) {
    return new SampleProcessor(options);
  }

  public List<Color> getForegroundColors(List<Color> sample, Color bg) {
    List<Color> foregroundColors = new ArrayList<>();
    for (Color color : sample) {
      if (Cleaner.isForegroundColor(color, bg, options)) {
        foregroundColors.add(color);
      }
    }
    return foregroundColors;
  }

  public Color getBackgroundColor(List<Color> sample) {
    List<Color> reduced = reduceBitsPerChannelTo(options.getBitsPerChannel(), sample);
    return mode(reduced);
  }

  private List<Color> reduceBitsPerChannelTo(int n, List<Color> colors) {
    assert (0 <= n && n <= 8);
    List<Color> reduced = new ArrayList<>();
    int mask = ~(1 << (8 - n) - 1);
    for (Color color : colors) {
      int red = color.getRed() & mask;
      int green = color.getGreen() & mask;
      int blue = color.getBlue() & mask;
      reduced.add(new Color(red, green, blue));
    }
    return reduced;
  }

  private Color mode(List<Color> colors) {
    Map<Color, Integer> count = new HashMap<>();
    int max = 0;
    Color mode = colors.get(0);
    for (Color color : colors) {
      if (!count.containsKey(color)) {
        count.put(color, 1);
        continue;
      }
      int n = count.get(color) + 1;
      count.put(color, n);
      if (n > max) {
        max = n;
        mode = color;
      }
    }
    return mode;
  }

}
