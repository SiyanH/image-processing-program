package imageprocessing.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import imageprocessing.model.CountryAlphaCode;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImgProcModel;
import imageprocessing.model.ProcessingOperation;
import imageprocessing.operation.Image;
import imageprocessing.operation.PatternDirection;
import imageprocessing.util.ImageUtil;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This contains all the unit tests for the image processing controller.
 */
public class ImgProcControllerTest {
  private ImageProcessingModel model;
  private ImageProcessingController controller;

  /**
   * Set up the image processing model for the controller.
   */
  @Before
  public void setUp() {
    model = new ImgProcModel();
  }

  /**
   * Test whether invalid scripts can be detected by the controller or not.
   */
  @Test
  public void testRunInvalidScript() {
    String[] invalidScript = {"save", "load", "generate", "mosaic", "flag", "save load",
                              "save\ndither", "load res/img0.jpggreysacle",
                              "generate flag 100 100 US", "test"};

    for (String s : invalidScript) {
      try {
        controller = new ImgProcController(new StringReader(s), model);
        controller.run();
        fail("An exception should be thrown");
      } catch (IllegalStateException e) {
        assertEquals("Command not found", e.getMessage());
      }
    }

    try {
      controller = new ImgProcController(new StringReader("generate checkerboard 0"), model);
      controller.run();
      fail("An exception should be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Invalid command argument: Not a valid image size",
              e.getMessage());
    }

    try {
      controller = new ImgProcController(new StringReader("load res/img0.jpg\nmosaic 0"), model);
      controller.run();
      fail("An exception should be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Invalid command argument: Number of seeds must be positive",
              e.getMessage());
    }

    try {
      controller = new ImgProcController(new StringReader("load img0.jpg"), model);
      controller.run();
      fail("An exception should be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Failed to read/write image file: img0.jpg (No such file or directory)",
              e.getMessage());
    }
  }

  /**
   * Test whether the image read and write operations work correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testImageIO() throws IOException {
    String script = "load res/img0.jpg\nsave res/test/img0.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    assertArrayEquals(ImageUtil.readImage("res/img0.jpg"),
            ImageUtil.readImage("res/test/img0.png"));
  }

  /**
   * Test whether the greyscale operation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testGreyScale() throws IOException {
    String script = "load res/img0.jpg\ngreyscale\nsave res/test/img0-greyscale.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.setImage(new Image(ImageUtil.readImage("res/img0.jpg")));
    model.process(ProcessingOperation.GREYSCALE);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/img0-greyscale.png"));
  }

  /**
   * Test whether the blur operation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testBlur() throws IOException {
    String script = "load res/img0.jpg\nblur\nsave res/test/img0-blur.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.setImage(new Image(ImageUtil.readImage("res/img0.jpg")));
    model.process(ProcessingOperation.BLUR);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/img0-blur.png"));
  }

  /**
   * Test whether the sepia tone operation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testSepiaTone() throws IOException {
    String script = "load res/img0.jpg\nsepiatone\nsave res/test/img0-sepiatone.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.setImage(new Image(ImageUtil.readImage("res/img0.jpg")));
    model.process(ProcessingOperation.SEPIATONE);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/img0-sepiatone.png"));
  }

  /**
   * Test whether the sharpen operation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testSharpen() throws IOException {
    String script = "load res/img0.jpg\nsharpen\nsave res/test/img0-sharpen.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.setImage(new Image(ImageUtil.readImage("res/img0.jpg")));
    model.process(ProcessingOperation.SHARPEN);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/img0-sharpen.png"));
  }

  /**
   * Test whether the dither operation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testDither() throws IOException {
    String script = "load res/img0.jpg\ndither\nsave res/test/img0-dither.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.setImage(new Image(ImageUtil.readImage("res/img0.jpg")));
    model.process(ProcessingOperation.DITHER);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/img0-dither.png"));
  }

  /**
   * Test whether the checker board generation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testGenerateCheckerBoard() throws IOException {
    String script = "generate checkerboard 20\nsave res/test/checkerboard.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.generateCheckerBoard(20);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/checkerboard.png"));
  }

  /**
   * Test whether the flag generation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testGenerateFlag() throws IOException {
    String script = "generate flag 290 436 FR\nsave res/test/flagFR.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.generateFlag(290, 436, CountryAlphaCode.FR);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/flagFR.png"));
  }

  /**
   * Test whether the rainbow generation works correctly for the controller.
   *
   * @throws IOException if error occurs during image file I/O operations
   */
  @Test
  public void testRainbow() throws IOException {
    String script = "generate rainbow 175 210 v\nsave res/test/rainbow.png";
    controller = new ImgProcController(new StringReader(script), model);
    controller.run();

    model.generateRainbow(175, 210, PatternDirection.VERTICAL);

    assertArrayEquals(model.getImage().getRGB(),
            ImageUtil.readImage("res/test/rainbow.png"));
  }
}