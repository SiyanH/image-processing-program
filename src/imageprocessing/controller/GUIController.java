package imageprocessing.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import imageprocessing.model.CountryAlphaCode;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ProcessingOperation;
import imageprocessing.operation.Image;
import imageprocessing.operation.PatternDirection;
import imageprocessing.util.ImageUtil;
import imageprocessing.view.ImageProcessingView;

/**
 * This class represents a controller for the image processing program with GUI. It offers all the
 * methods mandated by the {@link ImageProcessingController} and the {@link Features} interfaces,
 * and each of those methods will give control to the controller. It contains the model and the view
 * of the program, and runs by setting the view. It also stores the path for the temporary file
 * created during image operations.
 */
public class GUIController implements ImageProcessingController, Features {
  private final ImageProcessingModel model;
  private final ImageProcessingView view;
  private final String tempFilePath;

  /**
   * Construct a controller for the image processing program with GUI, with the given model and view
   * for the program, and initialize the path for the temporary file used by the program.
   *
   * @param model the model for the program
   * @param view  the view for the program
   */
  public GUIController(ImageProcessingModel model, ImageProcessingView view) {
    this.model = model;
    this.view = view;
    tempFilePath = "ImgProcTemp.png";
  }

  @Override
  public void run() {
    view.setFeatures(this);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void redo() throws IllegalStateException {
    model.redo();
    displayResult();
  }

  @Override
  public void undo() throws IllegalStateException {
    model.undo();
    displayResult();
  }

  @Override
  public void load(String filename) throws IOException {
    model.setImage(new Image(ImageUtil.readImage(filename)));
    view.display(0, filename);
  }

  @Override
  public void save(String filename) throws IOException, IllegalStateException {
    ImageUtil.writeImage(model.getImageRGB(), model.getImageWidth(), model.getImageHeight(),
            filename);
  }

  @Override
  public void processScript(String script) throws IllegalArgumentException {
    ScriptController s = new ScriptController(new StringReader(script), model);

    try {
      s.run();
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("Script processing failed: " + e.getMessage());
    }

    try {
      displayResult();
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("Please enter a valid script");
    }
  }

  @Override
  public void process(ProcessingOperation operation) throws IllegalStateException {
    model.process(operation);
    displayResult();
  }

  @Override
  public void mosaic(int numSeeds) throws IllegalArgumentException, IllegalStateException {
    model.mosaic(numSeeds);
    displayResult();
  }

  @Override
  public void checkerBoard(int size) throws IllegalArgumentException {
    model.generateCheckerBoard(size);
    displayResult();
  }

  @Override
  public void flag(int height, int width, CountryAlphaCode country)
          throws IllegalArgumentException {
    model.generateFlag(height, width, country);
    displayResult();
  }

  @Override
  public void rainbow(int height, int width, PatternDirection direction)
          throws IllegalArgumentException {
    model.generateRainbow(height, width, direction);
    displayResult();
  }

  /**
   * Display the resulting image in the view after an image operation is applied. It does this by
   * outputting the image to an temporary file and then asking the view to display the image from
   * this file. The temporary file is deleted after the image is displayed by the view.
   *
   * @throws IllegalStateException if there is no image to display (i.e., cannot save temp file)
   */
  private void displayResult() throws IllegalStateException {
    try {
      save(tempFilePath);
    } catch (IOException ioe) {
      view.errorMsgBox("Failed to create the new image");
    }

    view.display(1, tempFilePath);

    if (!new File(tempFilePath).delete()) {
      view.errorMsgBox("Failed to delete the temp file: " + tempFilePath
              + ".You may delete it manually.");
    }
  }
}
