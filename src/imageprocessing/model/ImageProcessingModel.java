package imageprocessing.model;

import imageprocessing.operation.Image;
import imageprocessing.operation.PatternDirection;

/**
 * This interface represents a model for the image processing program. It includes methods that an
 * image processing model is expected to offer, which are the operations designed to be supported by
 * the program and called by the controller.
 */
public interface ImageProcessingModel {
  /**
   * Return as a 3D array the RGB values of the image contained in this model. The image is either
   * the processed image or the generated image. The dimensions of the returned array are row, col
   * and channel respectively.
   *
   * @return the RGB values of the image contained in this model
   * @throws IllegalStateException if there is no image contained in this model
   */
  int[][][] getImageRGB() throws IllegalStateException;

  /**
   * Return the height of the image contained in this model.
   *
   * @return the height of the image contained in this model
   * @throws IllegalStateException if there is no image contained in this model
   */
  int getImageHeight() throws IllegalStateException;

  /**
   * Return the width of the image contained in this model.
   *
   * @return the width of the image contained in this model
   * @throws IllegalStateException if there is no image contained in this model
   */
  int getImageWidth() throws IllegalStateException;

  /**
   * Set the image contained in this model to the given image.
   *
   * @param img the image object to be set
   */
  void setImage(Image img);

  /**
   * Redo the last undone image operation, excluding file operations (i.e., save/load).
   *
   * @throws IllegalStateException if there is no undone operation
   */
  void redo() throws IllegalStateException;

  /**
   * Undo the last image operation, excluding file operations (i.e., save/load).
   *
   * @throws IllegalStateException if there is no operation done before
   */
  void undo() throws IllegalStateException;

  /**
   * Perform the given image processing operation on the image contained in this model. The result
   * is designed to replace the image in this model.
   *
   * @param operation the image processing operation to be performed on the image
   * @throws IllegalStateException if there is no image contained in this model to be processed
   */
  void process(ProcessingOperation operation) throws IllegalStateException;

  /**
   * Create a mosaic version of the image contained in this model, with the given number of seeds
   * for the mosaic operation. The mosaic image is designed to replace the image in this model.
   *
   * @param numSeeds the number of seeds for the mosaic operation
   * @throws IllegalArgumentException if the given number of seeds is not positive
   * @throws IllegalStateException    if there is no image contained in this model to be processed
   */
  void mosaic(int numSeeds) throws IllegalArgumentException, IllegalStateException;

  /**
   * Generate a checker board image of the given size, and set it to the image contained in this
   * model.
   *
   * @param size the size of the squares on the checkerboard to be generated.
   * @throws IllegalArgumentException if the given size is not positive
   */
  void generateCheckerBoard(int size) throws IllegalArgumentException;

  /**
   * Generate a national flag image of the given size and country, and set it to the image contained
   * in this model.
   *
   * @param height  the height of the flag to be generated
   * @param width   the width of the flag to be generated
   * @param country the country of the flag to be generated
   * @throws IllegalArgumentException if the given height or width is not positive
   */
  void generateFlag(int height, int width, CountryAlphaCode country)
          throws IllegalArgumentException;

  /**
   * Generate a rainbow image of the given size and the direction of the stripes, and set it to the
   * image contained in this model.
   *
   * @param height    the height of the rainbow image to be generated
   * @param width     the width of the rainbow image to be generated
   * @param direction the direction of the rainbow stripes to be generated
   * @throws IllegalArgumentException if the given height or width of the image is not positive
   */
  void generateRainbow(int height, int width, PatternDirection direction)
          throws IllegalArgumentException;
}
