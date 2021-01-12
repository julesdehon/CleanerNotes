package kmeans;

public class EuclideanDistance implements Distance {

  @Override
  public double calculate(DataPoint p1, DataPoint p2) {
    double sum = 0;
    for (int i = 0; i < p1.getDimensions(); i++) {
      double diff = p1.getNthDimension(i) - p2.getNthDimension(i);
      sum += diff * diff;
    }
    return Math.sqrt(sum);
  }
}
