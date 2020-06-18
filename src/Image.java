/**
 * This class represents a 24-bit image (3 8-bit channels–red, green, blue). It contains the RGB
 * matrix of this image (the image data as a 3D array of integers with rows=height, columns=width
 * and depth=3). With 8-bit channels, each value is between 0 and 255. For example, the red, green,
 * blue channel of the pixel at (5, 4) of this image is represented by rgb[5][4][0], rgb[5][4][1],
 * rgb[5][4][2] respectively.
 */
public class Image {
  private int[][][] rgb;

  /**
   * Construct an image object with the given RGB matrix.
   *
   * @param rgb the RGB matrix of this image whose dimensions are row, col and channel respectively
   * @throws IllegalArgumentException if the given RGB matrix is null or the RGB values are out of
   *                                  bound
   */
  public Image(int[][][] rgb) throws IllegalArgumentException {
    if (rgb == null) {
      throw new IllegalArgumentException("The RGB matrix is null");
    }
    if (!isValidRGB(rgb)) {
      throw new IllegalArgumentException("The RGB values should be 0-255");
    }

    this.rgb = rgb;
  }

  /**
   * Return the RGB matrix of this image as a 3D array of integers whose dimensions are row, col and
   * channel respectively.
   *
   * @return the RGB matrix of this image
   */
  public int[][][] getRGB() {
    int h = getHeight();
    int w = getWidth();
    int[][][] rgb = new int[h][w][3];

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        for (int m = 0; m < 3; m++) {
          rgb[i][j][m] = this.rgb[i][j][m];
        }
      }
    }

    return rgb;
  }

  /**
   * Return the height of this image.
   *
   * @return the height of this image
   */
  public int getHeight() {
    return rgb.length;
  }

  /**
   * Return the width of this image.
   *
   * @return the width of this image
   */
  public int getWidth() {
    return rgb[0].length;
  }

  /**
   * Check whether the given RGB matrix is valid (i.e., the RGB values are all within the 0-255
   * range). Return true if so, false otherwise.
   *
   * @param rgb the RGB matrix of this image
   * @return true if the given RGB matrix is valid, false otherwise
   */
  private static boolean isValidRGB(int[][][] rgb) {
    int value;
    int h = rgb.length;
    int w = rgb[0].length;

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        for (int m = 0; m < 3; m++) {
          value = rgb[i][j][m];
          if (value < 0 || value > 255) {
            return false;
          }
        }
      }
    }

    return true;
  }
}
