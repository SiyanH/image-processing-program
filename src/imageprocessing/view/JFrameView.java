package imageprocessing.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import imageprocessing.controller.Features;
import imageprocessing.model.CountryAlphaCode;
import imageprocessing.model.ProcessingOperation;
import imageprocessing.operation.PatternDirection;

/**
 * This class represents a JFrame view for the image processing program using Java Swing, and offers
 * all the methods mandated by the {@link ImageProcessingView} interface. It uses all the features
 * offered in the {@link Features} interface as callbacks, and exposes them suitably through menus.
 * It shows any error messages using a pop-up dialog box, and displays the image on the left or
 * right area of this view.
 */
public class JFrameView extends JFrame implements ImageProcessingView, ActionListener {
  private JMenu menuFile;
  private JMenu menuProcess;
  private JMenu menuEdit;
  private JMenu subMenuCreate;
  private JLabel[] imageLabel;
  private Features features;

  /**
   * Construct a JFrame view and set up the GUI for the program.
   */
  public JFrameView() {
    // init window
    setTitle("Image Processing GUI");
    setSize(1500, 1200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // init main menu
    // File: Load, Save, Create, Exit
    //      Create: Board, Flag, Rainbow
    // Process: Blur, Sharpen, Grey ...
    // Edit: Redo, Undo, Script
    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);

    // Menu file
    menuFile = new JMenu("File");
    menuBar.add(menuFile);

    // items under menu file
    load();
    save();

    // Submenu create
    subMenuCreate = new JMenu("Create");
    menuFile.add(subMenuCreate);

    // items under create
    board();
    flag();
    rainbow();

    // Add exit item under menu file
    fileExit();

    // Menu Process
    menuProcess = new JMenu("Process");
    menuBar.add(menuProcess);

    // items under menu process
    createItemProcess();

    // Menu Edit
    menuEdit = new JMenu("Edit");
    menuBar.add(menuEdit);

    // items under menu edit
    redo();
    undo();
    script();

    // img
    initImagePanel();

    setVisible(true);
  }

  /**
   * Set up the load menu item.
   */
  private void load() {
    JMenuItem itemLoad = new JMenuItem("Load");
    itemLoad.addActionListener(this);
    menuFile.add(itemLoad);
  }

  /**
   * Set up the save menu item.
   */
  private void save() {
    JMenuItem itemSave = new JMenuItem("Save");
    itemSave.addActionListener(this);
    menuFile.add(itemSave);
  }

  /**
   * Set up the checker board generation menu item.
   */
  private void board() {
    JMenuItem itemBoard = new JMenuItem("Board");
    itemBoard.addActionListener(this);
    subMenuCreate.add(itemBoard);
  }

  /**
   * Set up the flag generation menu item.
   */
  private void flag() {
    JMenuItem itemFlag = new JMenuItem("Flag");
    itemFlag.addActionListener(this);
    subMenuCreate.add(itemFlag);
  }

  /**
   * Set up the rainbow stripes generation menu item.
   */
  private void rainbow() {
    JMenuItem itemRainbow = new JMenuItem("Rainbow");
    itemRainbow.addActionListener(this);
    subMenuCreate.add(itemRainbow);
  }

  /**
   * Set up the file edit menu item.
   */
  private void fileExit() {
    JMenuItem itemExit = new JMenuItem("Exit");
    itemExit.addActionListener(this);
    menuFile.add(itemExit);
  }

  /**
   * Set up the redo menu item.
   */
  private void redo() {
    JMenuItem itemRedo = new JMenuItem("Redo");
    itemRedo.addActionListener(this);
    menuEdit.add(itemRedo);
  }

  /**
   * Set up the undo menu item.
   */
  private void undo() {
    JMenuItem itemUndo = new JMenuItem("Undo");
    itemUndo.addActionListener(this);
    menuEdit.add(itemUndo);
  }

  /**
   * Set up the script menu item.
   */
  private void script() {
    JMenuItem itemScript = new JMenuItem("Script");
    itemScript.addActionListener(this);
    menuEdit.add(itemScript);
  }

  /**
   * Set up the image processing operation menu items.
   */
  private void createItemProcess() {
    String[] items = {"Mosaic", "Blur", "Sharpen", "Sepia", "Greyscale", "Dither"};

    for (String item : items) {
      JMenuItem menuItem = new JMenuItem(item);
      menuItem.addActionListener(this);
      menuProcess.add(menuItem);
    }
  }

  /**
   * Set up the panel for image display.
   */
  private void initImagePanel() {
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image display area"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    this.add(imagePanel);

    String[] images = {"Origin", "After"};
    imageLabel = new JLabel[images.length];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length];

    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);
      imageScrollPane[i].setPreferredSize(new Dimension(100, 600));
      imagePanel.add(imageScrollPane[i]);
    }
  }

  /**
   * Add valid file filters for the given file chooser.
   *
   * @param fc the file chooser
   */
  private static void setFileFilters(JFileChooser fc) {
    FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter(
            "JPG File", "jpg", "jpeg");
    FileNameExtensionFilter pngFilter = new FileNameExtensionFilter(
            "PNG File", "png");
    FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter(
            "BMP File", "bmp");
    FileNameExtensionFilter wbmpFilter = new FileNameExtensionFilter(
            "WBMP File", "wbmp");
    FileNameExtensionFilter gifFilter = new FileNameExtensionFilter(
            "GIF File", "gif");

    fc.addChoosableFileFilter(jpgFilter);
    fc.addChoosableFileFilter(pngFilter);
    fc.addChoosableFileFilter(bmpFilter);
    fc.addChoosableFileFilter(wbmpFilter);
    fc.addChoosableFileFilter(gifFilter);
    fc.setAcceptAllFileFilterUsed(false);
  }

  @Override
  public void errorMsgBox(String msg) {
    JOptionPane.showMessageDialog(JFrameView.this, msg, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setFeatures(Features f) {
    this.features = f;
  }

  @Override
  public void display(int option, String path) throws IllegalArgumentException {
    if (option != 0 && option != 1) {
      throw new IllegalArgumentException("Invalid display option");
    }

    try {
      imageLabel[option].setIcon(new ImageIcon(ImageIO.read(new File(path))));
    } catch (IOException e) {
      errorMsgBox("Image display failed");
    }

    imageLabel[option].setHorizontalAlignment(SwingConstants.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Load": {
        final JFileChooser fc = new JFileChooser();
        setFileFilters(fc);

        int retVal = fc.showOpenDialog(JFrameView.this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
          String path = fc.getSelectedFile().getAbsolutePath();
          // load via controller
          try {
            features.load(path);
          } catch (IOException ioe) {
            errorMsgBox("Load failed: " + ioe.getMessage());
          }
        }
        break;
      }

      case "Save": {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save (JPG, PNG, BMP, WBMP, GIF)");

        int retVal = fc.showSaveDialog(JFrameView.this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
          String path = fc.getSelectedFile().getAbsolutePath();
          // save
          try {
            features.save(path);
          } catch (IOException ioe) {
            errorMsgBox("Save failed: " + ioe.getMessage());
          } catch (IllegalStateException ise) {
            errorMsgBox("No image to save");
          }
        }
        break;
      }

      case "Exit": {
        features.exitProgram();
        break;
      }

      case "Board": {
        int size = Integer.parseInt(JOptionPane.showInputDialog("Please enter size of squares "
                + "(e.g. 30):"));
        try {
          features.checkerBoard(size);
        } catch (IllegalArgumentException iae) {
          errorMsgBox("Illegal argument: " + iae.getMessage());
        }
        break;
      }

      case "Rainbow": {
        int height = Integer.parseInt(JOptionPane.showInputDialog("Please enter height of image:"));
        int width = Integer.parseInt(JOptionPane.showInputDialog("Please enter width of image:"));
        String option = JOptionPane.showInputDialog("Please enter h or v for horizontal/vertical:");

        option = option.toLowerCase();

        try {
          if (option.equals("h")) {
            features.rainbow(height, width, PatternDirection.HORIZONTAL);
          } else if (option.equals("v")) {
            features.rainbow(height, width, PatternDirection.VERTICAL);
          } else {
            errorMsgBox("Unrecognized h/v option.");
          }
        } catch (IllegalArgumentException iae) {
          errorMsgBox("Illegal argument: " + iae.getMessage());
        }
        break;
      }

      case "Flag": {
        int height = Integer.parseInt(JOptionPane.showInputDialog("Please enter height of image:"));
        int width = Integer.parseInt(JOptionPane.showInputDialog("Please enter width of image:"));
        String option = JOptionPane.showInputDialog("Please enter country alpha code "
                + "(GR for Greece, FR for France, CH for Switzerland):");

        option = option.toUpperCase();

        try {
          switch (option) {
            case "GR":
              features.flag(height, width, CountryAlphaCode.GR);
              break;
            case "FR":
              features.flag(height, width, CountryAlphaCode.FR);
              break;
            case "CH":
              features.flag(height, width, CountryAlphaCode.CH);
              break;
            default:
              errorMsgBox("Unrecognized country alpha code.");
          }
        } catch (IllegalArgumentException iae) {
          errorMsgBox("Illegal argument: " + iae.getMessage());
        }
        break;
      }

      case "Redo": {
        try {
          features.redo();
        } catch (IllegalStateException ise) {
          errorMsgBox(ise.getMessage());
        }
        break;
      }

      case "Undo": {
        try {
          features.undo();
        } catch (IllegalStateException ise) {
          errorMsgBox(ise.getMessage());
        }
        break;
      }

      case "Mosaic": {
        int numSeeds = Integer.parseInt(JOptionPane.showInputDialog("Please enter "
                + "number of seeds (e.g. 1000):"));

        try {
          features.mosaic(numSeeds);
        } catch (IllegalArgumentException iae) {
          errorMsgBox("Illegal argument: " + iae.getMessage());
        } catch (IllegalStateException ise) {
          errorMsgBox("Missing image: " + ise.getMessage());
        }
        break;
      }

      case "Blur": {
        try {
          features.process(ProcessingOperation.BLUR);
        } catch (IllegalStateException ise) {
          errorMsgBox("Missing image: " + ise.getMessage());
        }
        break;
      }

      case "Sharpen": {
        try {
          features.process(ProcessingOperation.SHARPEN);
        } catch (IllegalStateException ise) {
          errorMsgBox("Missing image: " + ise.getMessage());
        }
        break;
      }

      case "Sepia": {
        try {
          features.process(ProcessingOperation.SEPIATONE);
        } catch (IllegalStateException ise) {
          errorMsgBox("Missing image: " + ise.getMessage());
        }
        break;
      }

      case "Greyscale": {
        try {
          features.process(ProcessingOperation.GREYSCALE);
        } catch (IllegalStateException ise) {
          errorMsgBox("Missing image: " + ise.getMessage());
        }
        break;
      }

      case "Dither": {
        try {
          features.process(ProcessingOperation.DITHER);
        } catch (IllegalStateException ise) {
          errorMsgBox("Missing image: " + ise.getMessage());
        }
        break;
      }

      case "Script": {
        JDialog inputDialog = new JDialog(this, "Script");
        JTextArea scriptText = new JTextArea(14, 20);
        JScrollPane scrollPane = new JScrollPane(scriptText);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(l -> {
          try {
            String script = scriptText.getText();
            if (script.isEmpty()) {
              errorMsgBox("Please enter your script");
            } else {
              features.processScript(script);
              inputDialog.dispose();
            }
          } catch (IllegalArgumentException iae) {
            errorMsgBox(iae.getMessage());
          }
        });

        inputDialog.setLayout(new FlowLayout());
        inputDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        inputDialog.setSize(300, 300);
        inputDialog.getContentPane().add(scrollPane);
        inputDialog.getContentPane().add(runButton);
        inputDialog.setLocationRelativeTo(null);
        inputDialog.setResizable(false);
        inputDialog.setVisible(true);

        break;
      }

      default:
        errorMsgBox("Undefined operation");
    }
  }
}
