package imageprocessing;

import java.io.FileNotFoundException;
import java.io.FileReader;

import imageprocessing.controller.GUIController;
import imageprocessing.controller.ImageProcessingController;
import imageprocessing.controller.ScriptController;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImgProcModel;
import imageprocessing.view.ImageProcessingView;
import imageprocessing.view.JFrameView;

/**
 * This class contains a program to apply various image processing operations on images (blurring,
 * sharpening, greyscale, sepia tone, dither and mosaic). It also generates images with horizontal
 * and vertical rainbow stripes and a checkerboard pattern, and offer ways to create the flags of
 * France, Greece and Switzerland of a user-specified size, but of the prescribed proportions.
 */
public class ImageProcessingProgram {

  /**
   * The entry point of the program. It creates a model, view, and controller for the program, and
   * then give control to the controller with the model and view. The type of the controller depends
   * on the user specified command-line input. Only the following two command-line inputs are valid:
   * 1. "java -jar Program.jar -script path-of-script-file": when invoked in this manner the program
   * should open the script file, execute it and then shut down. 2. "java -jar Program.jar
   * -interactive": when invoked in this manner the program should open the graphical user
   * interface. The program displays an error message suitably and quits if the provided input is
   * invalid or any error occurs.
   */
  public static void main(String[] args) {
    ImageProcessingModel model = new ImgProcModel();
    ImageProcessingController controller;

    if (args.length == 1 && args[0].equals("-interactive")) {
      ImageProcessingView view = new JFrameView();
      controller = new GUIController(model, view);
      controller.run();
    } else if (args.length == 2 && args[0].equals("-script")) {
      try {
        controller = new ScriptController(new FileReader(args[1]), model);
        controller.run();
      } catch (FileNotFoundException | IllegalStateException e) {
        System.err.println("Failed to execute script: " + e.getMessage());
        System.exit(2);
      }
    } else {
      System.err.println("Invalid command-line argument");
      System.err.println("Valid argument:\n"
              + "-script path-of-script-file: execute a script file\n"
              + "-interactive: open the GUI");
      System.exit(1);
    }
  }
}
