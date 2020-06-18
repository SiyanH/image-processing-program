package imageprocessing.operation;

/**
 * This class represents the sharpening operation on an image. It offers all the methods mandated by
 * the {@link ImageProcessing} interface. Sharpening can be done by applying its filter to every
 * channel of every pixel of an image to produce the output image.
 */
public class ImageSharpening extends ImageFiltering {

  /**
   * Construct an image sharpening object with the given image, and initialize its kernel.
   *
   * @param img the image to which this sharpening operation is to be applied
   * @throws IllegalArgumentException if the image is null
   */
  public ImageSharpening(Image img) throws IllegalArgumentException {
    super(img);
    this.kernel = new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
                                 {-0.125, 0.25, 0.25, 0.25, -0.125},
                                 {-0.125, 0.25, 1, 0.25, -0.125},
                                 {-0.125, 0.25, 0.25, 0.25, -0.125},
                                 {-0.125, -0.125, -0.125, -0.125, -0.125}};
  }
}
