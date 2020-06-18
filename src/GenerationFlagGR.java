/**
 * This class represents a 24-bit Greece flag image generation operation, and offers all the methods
 * mandated by the {@link ImageProcessing} interface. The ration of the flag is 1.5.
 */
public class GenerationFlagGR extends GenerationFlag {
  /**
   * Construct a Greece flag generation object with the given height and width of the Greece flag to
   * be generated.
   *
   * @param height the height of the Greece flag to be generated
   * @param width  the width of the Greece flag to be generated
   * @throws IllegalArgumentException if the given height or width of the flag is not positive
   */
  public GenerationFlagGR(int height, int width) throws IllegalArgumentException {
    super(height, width, 1.5);
  }

  @Override
  protected int[] color(int i, int j) {
    int stripe = height / 9;
    int size = stripe * 5;
    int[] white = new int[]{255, 255, 255};
    int[] blue = new int[]{13, 94, 175};

    if (i < size && j < size) {
      // this is in up-left cross
      if (i / stripe == 2 || j / stripe == 2) {
        return white;
      } else {
        return blue;
      }
    } else {
      // now only stripes left
      return (i / stripe % 2 == 0) ? blue : white;
    }
  }
}
