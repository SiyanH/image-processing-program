package imageprocessing.view;

import imageprocessing.controller.Features;

/**
 * This interface represents a view for the image processing program. It includes methods that a GUI
 * view for the program is expected to offer, which are designed to called by a controller.
 */
public interface ImageProcessingView {
  /**
   * Get the set of feature callbacks that this view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  void setFeatures(Features f);

  /**
   * Pop up an error message box in this view with the given message.
   *
   * @param msg the message to be displayed in the box
   */
  void errorMsgBox(String msg);

  /**
   * Display an image in this view, on the left or right specified by the option. The only valid
   * options are 0 for left, and 1 for right.
   *
   * @param option   the location of the image (0 for left and 1 for right)
   * @param filename the path of the image file
   * @throws IllegalArgumentException if the given option is invalid
   */
  void display(int option, String filename) throws IllegalArgumentException;
}
