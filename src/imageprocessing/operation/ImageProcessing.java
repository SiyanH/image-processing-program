package imageprocessing.operation;

/**
 * This interface represents an image processing operation on 24-bit images. It includes methods
 * that an image processing operation is expected to offer. Examples of such operation are image
 * filtering (image blur, image sharpening, etc.) and color transformations (greyscale, sepia tone,
 * etc.).
 */
public interface ImageProcessing {
  /**
   * Apply this image processing operation to the image, and return the result as an {@link Image}
   * object.
   *
   * @return the resulting image after applying this image processing operation
   */
  Image apply();
}
