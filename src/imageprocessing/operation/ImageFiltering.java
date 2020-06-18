package imageprocessing.operation;

/**
 * This abstract class contains the common field and constructor for all types of image filtering
 * operations on 24-bit images, and offers all the methods mandated by the {@link ImageProcessing}
 * interface. It contains the kernel of the filter, which is a 2D array of numbers, having odd
 * dimensions (3x3, 5x5, etc.). Image filtering can be done by applying its filter to every channel
 * of every pixel of an image to produce the output image. Examples of such operation are image blur
 * and image sharpening, etc.
 */
public abstract class ImageFiltering extends AbstractImageProcessing {
  protected double[][] kernel;

  /**
   * Construct an image filtering object with the given image.
   *
   * @param img the image to which this image filtering operation is to be applied
   * @throws IllegalArgumentException if the given image is null
   */
  public ImageFiltering(Image img) throws IllegalArgumentException {
    super(img);
  }

  @Override
  public Image apply() {
    int[][][] rgb = img.getRGB();
    int height = img.getHeight();
    int width = img.getWidth();
    int dimOfKernel = kernel.length;
    int[][][] rgbOutput = new int[height][width][3];

    // Iterate to rgb[i][j], apply filter to it
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Find leftmost position in input matrix to apply filter
        int x = i - (dimOfKernel - 1) / 2;
        int y = j - (dimOfKernel - 1) / 2;

        double r = 0;
        double g = 0;
        double b = 0;

        // Apply filter
        for (int u = x; u < x + dimOfKernel; u++) {
          for (int v = y; v < y + dimOfKernel; v++) {
            // If u, v goes outside input matrix, do nothing, so sum increments 0
            if (0 <= u && u < height && 0 <= v && v < width) {
              r += rgb[u][v][0] * kernel[u - x][v - y];
              g += rgb[u][v][1] * kernel[u - x][v - y];
              b += rgb[u][v][2] * kernel[u - x][v - y];
            }
          }
        }

        // Set the new values to output
        rgbOutput[i][j][0] = (int) Math.round(r);
        rgbOutput[i][j][1] = (int) Math.round(g);
        rgbOutput[i][j][2] = (int) Math.round(b);
      }
    }

    // Clamp values
    clamp(rgbOutput);

    return new Image(rgbOutput);
  }
}
