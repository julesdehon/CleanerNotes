package picture;

import java.util.Objects;

/**
 * Encapsulate the colours using the RGB direct color-model. The individual red, green and blue
 * components of a colour are assigned a value ranging from 0 to 255. A component value of 0
 * signifies no contribution is made to the color.
 */
public class Color {

  /** the intensity of the red component */
  private int red;

  /** the intensity of the green component */
  private int green;

  /** the intensity of the blue component */
  private int blue;

  /**
   * Default Construct. Construct a new Color object with the specified intensity values for the
   * red, green and blue components.
   *
   * @param red the intensity of the red component contributed to this Color.
   * @param green the intensity of the green component contributed to this Color.
   * @param blue the intensity of the blue component contributed to this Color.
   */
  public Color(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public Color(Color color) {
    this.red = color.red;
    this.green = color.green;
    this.blue = color.blue;
  }

  /**
   * Return the contribution of the red component to <tt>this</tt> Color.
   *
   * @return the intensity of the red component.
   */
  public int getRed() {
    return red;
  }

  /**
   * Return the contribution of the green component to <tt>this</tt> Color.
   *
   * @return the intensity of the green component.
   */
  public int getGreen() {
    return green;
  }

  /**
   * Return the contribution of the blue component to <tt>this</tt> Color.
   *
   * @return the intensity of the blue component.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Set the contribution of the red component to <tt>this</tt> Color.
   *
   * @param red the new intensity value of the red component.
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   * Set the contribution of the green component to <tt>this</tt> Color.
   *
   * @param green the new intensity value of the green component.
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * Set the contribution of the blue component to <tt>this</tt> Color.
   *
   * @param blue the new intensity value of the blue component.
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  public double getSaturation() {
    double r = (double) red / 255;
    double g = (double) green / 255;
    double b = (double) blue / 255;
    double cmax = Double.max(Double.max(r, g), b);
    double cmin = Double.min(Double.min(r, g), b);
    if (cmax == 0) return 0;
    else return (cmax - cmin) / cmax;
  }

  public double getValue() {
    double r = (double) red / 255;
    double g = (double) green / 255;
    double b = (double) blue / 255;
    return Double.max(Double.max(r, g), b);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Color color = (Color) o;
    return red == color.red &&
        green == color.green &&
        blue == color.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }
}
