package imageprocessing.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;

import imageprocessing.model.CountryAlphaCode;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ProcessingOperation;
import imageprocessing.operation.Image;
import imageprocessing.operation.PatternDirection;
import imageprocessing.util.ImageUtil;

/**
 * This class represents a controller for the image processing program. It offers all the methods
 * mandated by the {@link ImageProcessingController} interface, and contains the command script read
 * from the user input, the model for the program, the command supported by the program, and the
 * maps from command string to method argument.
 */
public class ImgProcController implements ImageProcessingController {
  private final Readable script;
  private final ImageProcessingModel model;
  private final String[] validCommand;
  private final Map<String, CountryAlphaCode> countryCodeMap;
  private final Map<String, ProcessingOperation> procOperationMap;

  /**
   * Construct a image processing controller, with the given command script and the model for the
   * program.
   *
   * @param script the command script with one command per line
   * @param model  the model for the program
   */
  public ImgProcController(Readable script, ImageProcessingModel model) {
    this.script = script;
    this.model = model;
    this.validCommand = setValidCommand();
    this.countryCodeMap = setCountryCodeMap();
    this.procOperationMap = setProcOperationMap();
  }

  @Override
  public void run() throws IllegalStateException {
    Scanner sc = new Scanner(script);
    String cmd;

    while (sc.hasNext()) {
      cmd = sc.nextLine();

      if (!isValidCommand(cmd)) {
        throw new IllegalStateException("Command not found");
      }

      try {
        executeCommand(parseCommand(cmd));
      } catch (IOException e) {
        throw new IllegalStateException("Failed to read/write image file: " + e.getMessage());
      } catch (IllegalArgumentException e) {
        throw new IllegalStateException("Invalid command argument: " + e.getMessage());
      }
    }

    sc.close();
  }

  /**
   * Parse a command by the supported syntax using regular expression. Return the match result if a
   * valid command is found, return null otherwise.
   *
   * @param cmd the command string to be parsed
   * @return the match result of the command
   */
  private MatchResult parseCommand(String cmd) {
    Scanner sc = new Scanner(cmd);

    for (String c : validCommand) {
      if (sc.findInLine(c) != null) {
        return sc.match();
      }
    }

    sc.close();
    return null;
  }

  /**
   * Execute the command with the given parsed tokens of the command (including the command itself
   * and its arguments). Note that the given command must be valid to execute. This method does not
   * verify its validity.
   *
   * @param cmdToken the parsed tokens of the command
   * @throws IOException              if error occurs when reading or writing an image file
   * @throws IllegalStateException    if an image processing command executed before the image is
   *                                  loaded
   * @throws IllegalArgumentException if any argument of a command is invalid
   */
  private void executeCommand(MatchResult cmdToken) throws IOException, IllegalStateException,
          IllegalArgumentException {
    if (cmdToken == null) {
      throw new IllegalArgumentException("command token cannot be null");
    }

    String cmd = cmdToken.group(1);

    switch (cmd) {
      case "load":
        model.setImage(new Image(ImageUtil.readImage(cmdToken.group(4))));
        break;
      case "save":
        Image img = model.getImage();
        ImageUtil.writeImage(img.getRGB(), img.getWidth(), img.getHeight(), cmdToken.group(4));
        break;
      case "generate":
        String pattern = cmdToken.group(2);

        if (pattern.equals("checkerboard")) {
          model.generateCheckerBoard(Integer.parseInt(cmdToken.group(3)));
        } else {
          int height = Integer.parseInt(cmdToken.group(3));
          int width = Integer.parseInt(cmdToken.group(4));

          if (pattern.equals("flag")) {
            model.generateFlag(height, width, countryCodeMap.get(cmdToken.group(5)));
          } else {
            PatternDirection direction = cmdToken.group(5).equals("v")
                    ? PatternDirection.VERTICAL : PatternDirection.HORIZONTAL;
            model.generateRainbow(height, width, direction);
          }
        }
        break;
      case "mosaic":
        model.mosaic(Integer.parseInt(cmdToken.group(2)));
        break;
      default:
        model.process(procOperationMap.get(cmd));
    }
  }

  /**
   * Check whether the given command is valid. Return true if so, false otherwise.
   *
   * @param cmd the command string to be checked
   * @return true if the given command is valid, false otherwise
   */
  private boolean isValidCommand(String cmd) {
    for (String c : validCommand) {
      if (cmd.matches(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set all valid commands supported by the program, and return them as an array of strings.
   *
   * @return the valid commands as an array
   */
  private String[] setValidCommand() {
    String saveOrLoad
            = "^\\s*((save)|(load))\\s+([^\\s\\\\:\"*?<>|]+\\.((jpe?g)|(png)|(w?bmp)|(gif)))\\s*$";
    String processing = "^\\s*((blur)|(sharpen)|(greyscale)|(sepiatone)|(dither))\\s*$";
    String checkerBoard = "^\\s*(generate)\\s+(checkerboard)\\s+(\\d+)\\s*$";
    String flag = "^\\s*(generate)\\s+(flag)\\s+(\\d+)\\s+(\\d+)\\s+(CH|FR|GR)\\s*$";
    String rainbow = "^\\s*(generate)\\s+(rainbow)\\s+(\\d+)\\s+(\\d+)\\s+(v|h)\\s*$";
    String mosaic = "^\\s*(mosaic)\\s+(\\d+)\\s*$";

    return new String[]{saveOrLoad, processing, checkerBoard, flag, rainbow, mosaic};
  }

  /**
   * Set the map from country alpha code string to its {@link CountryAlphaCode} enum counterpart.
   * Note that the map must include all supported country codes.
   *
   * @return the map from country alpha code string to its enum counterpart
   */
  private Map<String, CountryAlphaCode> setCountryCodeMap() {
    Map<String, CountryAlphaCode> countryCodeMap = new HashMap<>();

    for (CountryAlphaCode code : CountryAlphaCode.values()) {
      countryCodeMap.put(code.toString(), code);
    }

    return countryCodeMap;
  }

  /**
   * Set the map from image processing operation string to its {@link ProcessingOperation} enum
   * counterpart. Note that the map must include all supported processing operations.
   *
   * @return the map from image processing operation string to its enum counterpart
   */
  private Map<String, ProcessingOperation> setProcOperationMap() {
    Map<String, ProcessingOperation> operationMap = new HashMap<>();

    for (ProcessingOperation operation : ProcessingOperation.values()) {
      operationMap.put(operation.toString().toLowerCase(), operation);
    }

    return operationMap;
  }
}
