package imageprocessing.operation;

/**
 * This abstract class contains the common field and constructor for all types of 24-bit image
 * generation operations, and offers all the methods mandated by the {@link ImageProcessing}
 * interface. It has the RGB matrix, height and width of the image to be generated.
 */
public abstract class AbstractImageGeneration implements ImageProcessing {
  protected int[][][] rgb;
  protected int height;
  protected int width;

  /**
   * Construct an image generation object with the given height and width of the image to be
   * generated.
   *
   * @param height the height of the image to be generated
   * @param width  the width of the image to be generated
   * @throws IllegalArgumentException if the given height or width value is not positive
   */
  public AbstractImageGeneration(int height, int width) throws IllegalArgumentException {
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Not a valid image size");
    }

    this.height = height;
    this.width = width;
    this.rgb = new int[height][width][3];
  }

  @Override
  public Image apply() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb[i][j] = color(i, j);
      }
    }
    return new Image(rgb);
  }

  /**
   * Return as an array the RGB values of the pixel at (i ,j) of the image to be generated. The
   * order of the values in the array is red, green and blue.
   *
   * @param i the row position of the pixel
   * @param j the column position of the pixel
   * @return the RGB values of the pixel at (i ,j) as an array
   */
  protected abstract int[] color(int i, int j);
}
