package imageprocessing.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the mosaic operation on a 24-bit image, and offers all the methods mandated
 * by the {@link ImageProcessing} interface. It contains the image to be processed, and the number
 * of seeds for the mosaic operation.
 */
public class Mosaic implements ImageProcessing {
  private Image img;
  private int seeds;

  /**
   * Construct a mosaic operation, with the given image and number of seeds for the mosaic
   * operation.
   *
   * @param image    the image to create a mosaic version from
   * @param numSeeds the number of seeds for the mosaic operation
   * @throws IllegalArgumentException if the given number of seeds is not positive
   */
  public Mosaic(Image image, int numSeeds) throws IllegalArgumentException {
    if (numSeeds <= 0) {
      throw new IllegalArgumentException("Number of seeds must be positive");
    }

    this.img = image;
    this.seeds = numSeeds;
  }

  @Override
  public Image apply() {
    int[][][] rgb = img.getRGB();

    // generate random seeds
    List<int[]> listSeeds = getSeeds(seeds);

    // generate clusters
    List<List<int[]>> listClusters = getCluster(listSeeds);

    // set points in clusters to avg color
    for (List<int[]> cluster : listClusters) {
      int[] avgColor = getAverageColor(cluster, rgb);
      for (int[] point : cluster) {
        rgb[point[0]][point[1]] = avgColor;
      }
    }

    return new Image(rgb);
  }

  /**
   * Generate the seeds for the mosaic operation randomly, and return them as a list of integer
   * arrays. The array contains the coordinates of a seed (the first element in the array is its ith
   * position, and the second is its jth position, in the RGB matrix of the image).
   *
   * @param num the number of seeds for the mosaic operation
   * @return the seeds for the mosaic operation as a list of arrays
   */
  private List<int[]> getSeeds(int num) {
    int h = img.getHeight();
    int w = img.getWidth();
    Set<int[]> seeds = new HashSet<>();

    while (seeds.size() <= num) {
      int x = (int) (Math.random() * h);
      int y = (int) (Math.random() * w);
      seeds.add(new int[]{x, y});
    }

    return new ArrayList<>(seeds);
  }

  /**
   * Get the cluster of pixels for each seed in the given list.
   *
   * @param listSeeds the list of seeds
   * @return the cluster of pixels for each seed
   */
  private List<List<int[]>> getCluster(List<int[]> listSeeds) {
    int h = img.getHeight();
    int w = img.getWidth();

    // init clusters
    List<List<int[]>> listClusters = new ArrayList<>();
    for (int a = 0; a < listSeeds.size(); ++a) {
      listClusters.add(new ArrayList<>());
    }

    for (int i = 0; i < h; ++i) {
      for (int j = 0; j < w; ++j) {
        // (i, j) is current point
        List<Integer> distances = new ArrayList<>();

        // store distances to seeds
        for (int[] seed : listSeeds) {
          distances.add(Math.abs(i - seed[0]) + Math.abs(j - seed[1]));
        }

        // find nearest seed
        int minIndex = distances.indexOf(Collections.min(distances));

        // store the point in corresponding cluster
        listClusters.get(minIndex).add(new int[]{i, j});
      }
    }

    return listClusters;
  }

  /**
   * Compute and return the average RGB values for each cluster of pixels.
   *
   * @param cluster the list of clusters
   * @param rgb     the RGB matrix of the image
   * @return the average RGB values for each cluster of pixels
   */
  private static int[] getAverageColor(List<int[]> cluster, int[][][] rgb) {
    double sumR = 0;
    double sumG = 0;
    double sumB = 0;
    int n = cluster.size();

    for (int[] point : cluster) {
      sumR += rgb[point[0]][point[1]][0];
      sumG += rgb[point[0]][point[1]][1];
      sumB += rgb[point[0]][point[1]][2];
    }

    int avgR = (int) Math.round(sumR / n);
    int avgG = (int) Math.round(sumG / n);
    int avgB = (int) Math.round(sumB / n);

    return new int[]{avgR, avgG, avgB};
  }
}
