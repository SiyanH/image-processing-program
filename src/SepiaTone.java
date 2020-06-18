/**
 * This class represents the sepia tone operation on an image. It offers all the methods mandated by
 * the {@link ImageProcessing} interface. Photographs taken in the 19th and early 20th century had a
 * characteristic reddish brown tone. This is referred to as a sepia tone.
 */
public class SepiaTone extends ColorTransformation {

  /**
   * Construct an sepia tone object with the given image, and initialize its color transformation
   * matrix.
   *
   * @param img the image to which this sepia tone operation is to be applied
   * @throws IllegalArgumentException if the given image is null
   */
  public SepiaTone(Image img) throws IllegalArgumentException {
    super(img);
    this.transMatrix = new double[][]{{0.393, 0.769, 0.189},
                                      {0.349, 0.686, 0.168},
                                      {0.272, 0.534, 0.131}};
  }
}
