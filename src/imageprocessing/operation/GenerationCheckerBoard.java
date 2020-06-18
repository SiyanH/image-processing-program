package imageprocessing.operation;

/**
 * This class represents a 24-bit checkerboard image generation operation, and offers all the
 * methods mandated by the {@link ImageProcessing} interface. A checkerboard is a board of chequered
 * pattern on which draughts (checkers) is played. It consists of 64 squares (8×8) of alternating
 * black and white color.
 */
public class GenerationCheckerBoard extends GenerationBoard {
  /**
   * Construct a checkerboard generation object with the given size of the squares on the
   * checkerboard to be generated.
   *
   * @param size the size of the squares on the checkerboard to be generated.
   * @throws IllegalArgumentException if the given size is not positive
   */
  public GenerationCheckerBoard(int size) throws IllegalArgumentException {
    super(size * 8, size * 8, size);
  }
}
