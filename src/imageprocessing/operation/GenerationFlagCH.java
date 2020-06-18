package imageprocessing.operation;

/**
 * This class represents a 24-bit Switzerland flag image generation operation, and offers all the
 * methods mandated by the {@link ImageProcessing} interface. The ration of the flag is 1.
 */
public class GenerationFlagCH extends GenerationFlag {
  /**
   * Construct a Switzerland flag generation object with the given height and width of the
   * Switzerland flag to be generated.
   *
   * @param height the height of the Switzerland flag to be generated
   * @param width  the width of the Switzerland flag to be generated
   * @throws IllegalArgumentException if the given height or width of the flag is not positive
   */
  public GenerationFlagCH(int height, int width) throws IllegalArgumentException {
    super(height, width, 1);
  }

  @Override
  protected int[] color(int i, int j) {
    int[] red = new int[]{213, 43, 30};
    int[] white = new int[]{255, 255, 255};
    int unit = height / 32;
    if ((6 * unit < i && i < 26 * unit && 13 * unit < j && j < 19 * unit)
            || (6 * unit < j && j < 26 * unit && 13 * unit < i && i < 19 * unit)) {
      return white;
    } else {
      return red;
    }
  }
}
