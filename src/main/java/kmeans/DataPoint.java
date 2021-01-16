package kmeans;

import java.util.Arrays;

public class DataPoint {

  private final int dimensions;
  private final double[] values;

  public DataPoint(double[] values) {
    this.dimensions = values.length;
    this.values = values;
  }

  public DataPoint(int dimensions) {
    this.dimensions = dimensions;
    this.values = new double[dimensions];
  }

  public int getDimensions() {
    return dimensions;
  }

  public double getNthDimension(int n) {
    return values[n];
  }

  public void setNthDimension(int n, double val) {
    values[n] = val;
  }

  public DataPoint copy() {
    return new DataPoint(Arrays.copyOf(values, values.length));
  }
}
