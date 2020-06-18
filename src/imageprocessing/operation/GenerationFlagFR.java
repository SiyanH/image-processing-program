package imageprocessing.operation;

/**
 * This class represents a 24-bit France flag image generation operation, and offers all the methods
 * mandated by the {@link ImageProcessing} interface. The ration of the flag is 1.5.
 */
public class GenerationFlagFR extends GenerationFlag {
  /**
   * Construct a France flag generation object with the given height and width of the France flag to
   * be generated.
   *
   * @param height the height of the France flag to be generated
   * @param width  the width of the France flag to be generated
   * @throws IllegalArgumentException if the given height or width of the flag is not positive
   */
  public GenerationFlagFR(int height, int width) throws IllegalArgumentException {
    super(height, width, 1.5);
  }

  @Override
  protected int[] color(int i, int j) {
    int[] red = new int[]{239, 65, 53};
    int[] white = new int[]{255, 255, 255};
    int[] blue = new int[]{0, 85, 164};
    if (j < width / 3) {
      return blue;
    } else if (j < width / 3 * 2) {
      return white;
    } else {
      return red;
    }
  }
}
