package imageprocessing.controller;

/**
 * This interface represents a controller for the image processing program. It includes methods that
 * an image processing controller is expected to offer, which are designed to give control to the
 * controller.
 */
public interface ImageProcessingController {
  /**
   * Start the program (i.e., give control to the controller).
   *
   * @throws IllegalStateException if any error occurs when running the program
   */
  void run() throws IllegalStateException;
}
