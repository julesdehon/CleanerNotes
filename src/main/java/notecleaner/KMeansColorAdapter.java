package notecleaner;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kmeans.DataPoint;
import kmeans.EuclideanDistance;
import kmeans.KMeans;
import picture.Color;

public class KMeansColorAdapter {

  public static Set<Color> getColorGroups(List<Color> foregroundColors) {
    List<DataPoint> samples =
        foregroundColors.stream().map(KMeansColorAdapter::dpFromColor).collect(Collectors.toList());
    Map<DataPoint, List<DataPoint>> clusters = KMeans.fit(samples, 8, 50, new EuclideanDistance());
    return clusters.keySet().stream()
        .map(KMeansColorAdapter::colorFromDp)
        .collect(Collectors.toSet());
  }

  public static DataPoint dpFromColor(Color color) {
    double r = (double) color.getRed() / 255D;
    double g = (double) color.getGreen() / 255D;
    double b = (double) color.getBlue() / 255D;
    return new DataPoint(new double[] {r, g, b});
  }

  public static Color colorFromDp(DataPoint dp) {
    int r = (int) Math.round(dp.getNthDimension(0) * 255);
    int g = (int) Math.round(dp.getNthDimension(1) * 255);
    int b = (int) Math.round(dp.getNthDimension(2) * 255);
    return new Color(r, g, b);
  }
}
