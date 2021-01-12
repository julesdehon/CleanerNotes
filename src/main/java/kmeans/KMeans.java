package kmeans;

/* With help from https://www.baeldung.com/java-k-means-clustering-algorithm */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KMeans {

  public static Map<DataPoint, List<DataPoint>> fit(
      List<DataPoint> samples, int numColors, int maxIterations, Distance distance) {

    List<DataPoint> centroids = randomCentroids(samples, numColors);
    Map<DataPoint, List<DataPoint>> clusters = new HashMap<>();
    Map<DataPoint, List<DataPoint>> lastState = new HashMap<>();

    for (int i = 0; i < maxIterations; i++) {
      for (DataPoint dp : samples) {
        DataPoint centroid = nearestCentroid(dp, centroids, distance);
        assignToCluster(clusters, dp, centroid);
      }

      if (clusters.equals(lastState)) {
        break;
      }

      lastState = clusters;
      centroids = relocateCentroids(clusters);
      clusters = new HashMap<>();
    }
    return lastState;
  }

  // Assumes samples are in a random order - the first k elements in samples will be the centroids
  private static List<DataPoint> randomCentroids(List<DataPoint> samples, int numColors) {
    List<DataPoint> centroids = new ArrayList<>();
    for (int i = 0; i < numColors; i++) {
      centroids.add(samples.get(i).copy());
    }
    return centroids;
  }

  private static DataPoint nearestCentroid(
      DataPoint dataPoint, List<DataPoint> centroids, Distance distance) {
    return centroids.stream()
        .min(Comparator.comparing(c -> distance.calculate(c, dataPoint)))
        .orElse(null);
  }

  private static void assignToCluster(
      Map<DataPoint, List<DataPoint>> clusters, DataPoint dp, DataPoint centroid) {
    clusters.compute(centroid, (key, list) -> {
      if (list == null) {
        list = new ArrayList<>();
      }
      list.add(dp);
      return list;
    });
  }

  private static DataPoint average(DataPoint centroid, List<DataPoint> assignments) {
    if (assignments == null || assignments.isEmpty()) return centroid;

    DataPoint avg = new DataPoint(centroid.getDimensions());
    for (DataPoint point : assignments) {
      for (int i = 0; i < point.getDimensions(); i++) {
        avg.setNthDimension(i, avg.getNthDimension(i) + point.getNthDimension(i));
      }
    }
    for (int i = 0; i < avg.getDimensions(); i++) {
      avg.setNthDimension(i, avg.getNthDimension(i) / assignments.size());
    }
    return avg;
  }

  private static List<DataPoint> relocateCentroids(Map<DataPoint, List<DataPoint>> clusters) {
    return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(
        Collectors.toList());
  }
}
