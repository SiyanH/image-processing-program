import java.io.IOException;

/**
 * This class contains a program to apply various image processing operations on images (blurring,
 * sharpening, greyscale and sepia tone). It also generates images with horizontal and vertical
 * rainbow stripes and a checkerboard pattern, and offer ways to create the flags of France, Greece
 * and Switzerland of a user-specified size, but of the prescribed proportions.
 */
public class ImageProcessingProgram {

  /**
   * A simple "driver" main method that suitably loads and saves images, apply various image
   * processing operations on them, and also generates various images with different patterns.
   */
  public static void main(String[] args) {
    Image[] img = new Image[3];

    // Read images from files
    for (int i = 0; i < 3; i++) {
      try {
        img[i] = new Image(ImageUtil.readImage("res/img" + i + ".jpg"));
      } catch (IOException e) {
        System.err.println("Cannot read image file");
        System.exit(1);
      }
    }

    // Apply image processing operations
    for (int i = 0; i < 3; i++) {
      int width = img[i].getWidth();
      int height = img[i].getHeight();

      Image blurred = new ImageBlurring(img[i]).apply();
      Image sharpened = new ImageSharpening(img[i]).apply();
      Image greyscaled = new Greyscale(img[i]).apply();
      Image sepiaToned = new SepiaTone(img[i]).apply();

      try {
        ImageUtil.writeImage(blurred.getRGB(), width, height, "res/img" + i
                + "_blurred.jpg");
        ImageUtil.writeImage(sharpened.getRGB(), width, height, "res/img" + i
                + "_sharpened.jpg");
        ImageUtil.writeImage(greyscaled.getRGB(), width, height, "res/img" + i
                + "_greyscaled.jpg");
        ImageUtil.writeImage(sepiaToned.getRGB(), width, height, "res/img" + i
                + "_sepiaToned.jpg");
      } catch (IOException e) {
        System.err.println("Cannot write processed image files");
        System.exit(1);
      }
    }

    // Generate rainbow images
    Image rainbow1 = new GenerationRainbow(350, 637,
            PatternDirection.HORIZONTAL).apply();
    Image rainbow2 = new GenerationRainbow(175, 210,
            PatternDirection.VERTICAL).apply();

    try {
      ImageUtil.writeImage(rainbow1.getRGB(), rainbow1.getWidth(),
              rainbow1.getHeight(), "res/rainbow1.png");
      ImageUtil.writeImage(rainbow2.getRGB(), rainbow2.getWidth(),
              rainbow2.getHeight(), "res/rainbow2.png");
    } catch (IOException e) {
      System.err.println("Cannot write rainbow image files");
      System.exit(1);
    }

    // Generate checkerboard images
    Image checkerboard1 = new GenerationCheckerBoard(201).apply();
    Image checkerboard2 = new GenerationCheckerBoard(56).apply();

    try {
      ImageUtil.writeImage(checkerboard1.getRGB(), checkerboard1.getWidth(),
              checkerboard1.getHeight(), "res/checkerboard1.png");
      ImageUtil.writeImage(checkerboard2.getRGB(), checkerboard2.getWidth(),
              checkerboard2.getHeight(), "res/checkerboard2.png");
    } catch (IOException e) {
      System.err.println("Cannot write checkerboard image files");
      System.exit(1);
    }

    // Generate flag images
    Image flagFR = new GenerationFlagFR(290, 436).apply();
    Image flagGR = new GenerationFlagGR(145, 218).apply();
    Image flagCH = new GenerationFlagCH(320, 320).apply();

    try {
      ImageUtil.writeImage(flagFR.getRGB(), flagFR.getWidth(),
              flagFR.getHeight(), "res/flagFR.png");
      ImageUtil.writeImage(flagGR.getRGB(), flagGR.getWidth(),
              flagGR.getHeight(), "res/flagGR.png");
      ImageUtil.writeImage(flagCH.getRGB(), flagCH.getWidth(),
              flagCH.getHeight(), "res/flagCH.png");
    } catch (IOException e) {
      System.err.println("Cannot write flag image files");
      System.exit(1);
    }
  }
}
