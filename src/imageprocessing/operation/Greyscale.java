package imageprocessing.operation;

/**
 * This class represents the greyscale operation on an image. It offers all the methods mandated by
 * the {@link ImageProcessing} interface. A greyscale image is composed only of shades of grey (if
 * the red, green and blue values are the same, it is a shade of grey).
 */
public class Greyscale extends ColorTransformation {

  /**
   * Construct an greyscale object with the given image, and initialize its color transformation
   * matrix.
   *
   * @param img the image to which this greyscale operation is to be applied
   * @throws IllegalArgumentException if the given image is null
   */
  public Greyscale(Image img) throws IllegalArgumentException {
    super(img);
    this.transMatrix = new double[][]{{0.2126, 0.7152, 0.0722},
                                      {0.2126, 0.7152, 0.0722},
                                      {0.2126, 0.7152, 0.0722}};
  }
}
