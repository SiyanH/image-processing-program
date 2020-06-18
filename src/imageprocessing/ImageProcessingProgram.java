package imageprocessing;

import java.io.FileNotFoundException;
import java.io.FileReader;

import imageprocessing.controller.ImageProcessingController;
import imageprocessing.controller.ImgProcController;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImgProcModel;

/**
 * This class contains a program to apply various image processing operations on images (blurring,
 * sharpening, greyscale, sepia tone, dither and mosaic). It also generates images with horizontal
 * and vertical rainbow stripes and a checkerboard pattern, and offer ways to create the flags of
 * France, Greece and Switzerland of a user-specified size, but of the prescribed proportions.
 */
public class ImageProcessingProgram {

  /**
   * The entry point of the program. It creates a model and a controller for the program, and then
   * give control to the controller, with the user input (command script from a txt file) and model.
   * The program exits if any error occurs.
   */
  public static void main(String[] args) {
    ImageProcessingModel model = new ImgProcModel();

    try {
      ImageProcessingController controller = new ImgProcController(new FileReader(args[0]), model);
      controller.run();
    } catch (FileNotFoundException | IllegalStateException e) {
      System.err.println("Failed to execute script: " + e.getMessage());
      System.exit(1);
    }
  }
}
