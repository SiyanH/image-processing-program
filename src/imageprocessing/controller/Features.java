package imageprocessing.controller;

import java.io.IOException;

import imageprocessing.model.CountryAlphaCode;
import imageprocessing.model.ProcessingOperation;
import imageprocessing.operation.PatternDirection;

/**
 * This interface represents a set of features that the image processing program offers. Each
 * feature is exposed as a function in this interface. The function is used suitably as a callback
 * by the view, to pass control to the controller. How the view uses them as callbacks is completely
 * up to how the view is designed (e.g. it could use them as a callback for a button, or a callback
 * for a dialog box, or a set of text inputs, etc.). Each function is designed to take in the
 * necessary data to complete that functionality.
 */
public interface Features {
  /**
   * Exit the program.
   */
  void exitProgram();

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
   * Load an image with the given path of the file.
   *
   * @param filename the path of the file
   * @throws IOException if any error occurs when reading the file
   */
  void load(String filename) throws IOException;

  /**
   * Save the processed image to the given path.
   *
   * @param filename the path of the file
   * @throws IOException           if any error occurs when writing the file
   * @throws IllegalStateException if if there is no loaded image to be saved
   */
  void save(String filename) throws IOException, IllegalStateException;

  /**
   * Process the input batch script entered by the user, and execute the corresponding commands.
   *
   * @param script the batch script to be processed
   * @throws IllegalArgumentException if the given script is invalid
   */
  void processScript(String script) throws IllegalArgumentException;

  /**
   * Perform the given image processing operation on the image (blur, sharpen, sepia, greyscale, or
   * dithering).
   *
   * @param operation the image processing operation to be performed on the image
   * @throws IllegalStateException if there is no image loaded to be processed
   */
  void process(ProcessingOperation operation) throws IllegalStateException;

  /**
   * Convert the image into a mosaic image, with the given number of seeds for the mosaic
   * operation.
   *
   * @param numSeeds the number of seeds for the mosaic operation
   * @throws IllegalArgumentException if the given number of seeds is not positive
   * @throws IllegalStateException    if there is no image loaded to be processed
   */
  void mosaic(int numSeeds) throws IllegalArgumentException, IllegalStateException;

  /**
   * Generate a checker board image of the given size.
   *
   * @param size the size of the squares on the checkerboard to be generated.
   * @throws IllegalArgumentException if the given size is not positive
   */
  void checkerBoard(int size) throws IllegalArgumentException;

  /**
   * Generate a national flag image of the given size and country.
   *
   * @param height  the height of the flag to be generated
   * @param width   the width of the flag to be generated
   * @param country the country of the flag to be generated
   * @throws IllegalArgumentException if the given height or width is not positive
   */
  void flag(int height, int width, CountryAlphaCode country) throws IllegalArgumentException;

  /**
   * Generate a rainbow image of the given size and the direction of the stripes.
   *
   * @param height    the height of the rainbow image to be generated
   * @param width     the width of the rainbow image to be generated
   * @param direction the direction of the rainbow stripes to be generated
   * @throws IllegalArgumentException if the given height or width of the image is not positive
   */
  void rainbow(int height, int width, PatternDirection direction) throws IllegalArgumentException;
}
