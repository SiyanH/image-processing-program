package imageprocessing.operation;

/**
 * This class represents the blurring operation on an image. It offers all the methods mandated by
 * the {@link ImageProcessing} interface. Blurring can be done by applying its filter to every
 * channel of every pixel of an image to produce the output image.
 */
public class ImageBlurring extends ImageFiltering {

  /**
   * Construct an image blurring object with the given image, and initialize its kernel.
   *
   * @param img the image to which this blurring operation is to be applied
   * @throws IllegalArgumentException if the image is null
   */
  public ImageBlurring(Image img) throws IllegalArgumentException {
    super(img);
    this.kernel = new double[][]{{0.0625, 0.125, 0.0625},
                                 {0.125, 0.25, 0.125},
                                 {0.0625, 0.125, 0.0625}};
  }
}
