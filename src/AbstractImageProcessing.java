import java.util.Objects;

/**
 * This abstract class contains the common field and constructor for all types of image processing
 * operations on 24-bit images, and offers all the methods mandated by the {@link ImageProcessing}
 * interface. It has the image to be processed.
 */
public abstract class AbstractImageProcessing implements ImageProcessing {
  protected Image img;

  /**
   * Construct an image processing object with the given image.
   *
   * @param img the image to which this image processing operation is to be applied
   * @throws IllegalArgumentException if the given image is null
   */
  public AbstractImageProcessing(Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("The image cannot be null");
    }

    this.img = img;
  }

  /**
   * Clamp all the values in the given RGB matrix to 8 bits per channel (each value in each channel
   * is between 0 and 255) after applying this image processing operation, to avoid overflow and
   * underflow. The operation deals with 24-bit image so the permissible minimum is 0, and maximum
   * is 255. Each value in an image that is lesser than the minimum is assigned to the minimum, and
   * each value greater than the maximum is assigned to the maximum. Clamping is usually implemented
   * as the last step of an image processing operation, to ensure that the resulting image can be
   * properly saved and displayed.
   *
   * @param rgb the RGB matrix of an image to be clamped
   */
  protected static void clamp(int[][][] rgb) {
    Objects.requireNonNull(rgb, "The RGB matrix must not be null to be clamped");
    int h = rgb.length;
    int w = rgb[0].length;

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        for (int m = 0; m < 3; m++) {
          if (rgb[i][j][m] < 0) {
            rgb[i][j][m] = 0;
          } else if (rgb[i][j][m] > 255) {
            rgb[i][j][m] = 255;
          }
        }
      }
    }
  }
}
