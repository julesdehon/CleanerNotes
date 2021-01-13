package notecleaner;

public class OptionsBuilder {

  private double valueThreshold = 0.25;
  private double saturationThreshold = 0.2;
  private int numColors = 8;
  private int bitsPerChannel = 6;
  private double sampleFraction = 0.05;
  private boolean whiteBackground = false;
  private boolean saturate = true;

  private OptionsBuilder() {}

  public static OptionsBuilder defaultOptions() {
    return new OptionsBuilder();
  }

  public OptionsBuilder withValueThreshold(double valueThreshold) {
    this.valueThreshold = valueThreshold;
    return this;
  }

  public OptionsBuilder withSaturationThreshold(double saturationThreshold) {
    this.saturationThreshold = saturationThreshold;
    return this;
  }

  public OptionsBuilder withNOutputColors(int numColors) {
    this.numColors = numColors;
    return this;
  }

  public OptionsBuilder withNBitsPerChannel(int bitsPerChannel) {
    if (bitsPerChannel < 0 || bitsPerChannel > 8) {
      System.err.println(
          "Can't have less than 0 or more than 8 bits per channel. Using default value of 6.");
      return this;
    }
    this.bitsPerChannel = bitsPerChannel;
    return this;
  }

  public OptionsBuilder withSampleSize(double sampleFraction) {
    this.sampleFraction = sampleFraction;
    return this;
  }

  public OptionsBuilder setWhiteBackground(boolean whiteBackground) {
    this.whiteBackground = whiteBackground;
    return this;
  }

  public OptionsBuilder setSaturate(boolean saturate) {
    this.saturate = saturate;
    return this;
  }

  public Options create() {
    return new Options(
        valueThreshold,
        saturationThreshold,
        numColors,
        bitsPerChannel,
        sampleFraction,
        whiteBackground,
        saturate);
  }
}
