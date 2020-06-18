/**
 * This class represents a 24-bit board image generation operation, and offers all the methods
 * mandated by the {@link ImageProcessing} interface. It contains the size of the squares on the
 * board to be generated. The board consists of squares of alternating black and white color.
 */
public class GenerationBoard extends AbstractImageGeneration {
  private int size;

  /**
   * Construct a board generation object with the given height and width of the board to be
   * generated, and the size of the squares on the board.
   *
   * @param height the height of the board to be generated
   * @param width  the width of the board to be generated
   * @param size   the size of the squares on the board to be generated
   * @throws IllegalArgumentException if the given height, width or size value is not positive, or
   *                                  the size is larger than the height or width
   */
  public GenerationBoard(int height, int width, int size) throws IllegalArgumentException {
    super(height, width);

    if (size <= 0 || size > height || size > width) {
      throw new IllegalArgumentException("Invalid size: negative or too large");
    }

    this.size = size;
  }

  @Override
  protected int[] color(int i, int j) {
    int[] white = new int[]{255, 255, 255};
    int[] black = new int[]{0, 0, 0};
    return (i / size + j / size) % 2 == 0 ? white : black;
  }
}
