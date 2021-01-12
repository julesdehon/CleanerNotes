package notecleaner;

public class Options {

  private final double valueThreshold;
  private final double saturationThreshold;
  private final int numColors;
  private final int bitsPerChannel;
  private final double sampleFraction;
  private final boolean whiteBackground;
  private final boolean saturate;

  protected Options(
      double valueThreshold,
      double saturationThreshold,
      int numColors,
      int bitsPerChannel,
      double sampleFraction,
      boolean whiteBackground,
      boolean saturate) {
    this.valueThreshold = valueThreshold;
    this.saturationThreshold = saturationThreshold;
    this.numColors = numColors;
    this.bitsPerChannel = bitsPerChannel;
    this.sampleFraction = sampleFraction;
    this.whiteBackground = whiteBackground;
    this.saturate = saturate;
  }

  public double getValueThreshold() {
    return valueThreshold;
  }

  public double getSaturationThreshold() {
    return saturationThreshold;
  }

  public int getNumColors() {
    return numColors;
  }

  public int getBitsPerChannel() {
    return bitsPerChannel;
  }

  public double getSampleFraction() {
    return sampleFraction;
  }

  public boolean isWhiteBackground() {
    return whiteBackground;
  }

  public boolean isSaturate() {
    return saturate;
  }
}
