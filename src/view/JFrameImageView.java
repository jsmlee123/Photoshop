package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * JFrame GUI class used to view the image model. Contains menu items and buttons to represent image
 * model operations and image display to represent the actual model. Contains methods to ask for
 * user input when only menu items
 */
public class JFrameImageView extends JFrame implements ImageGraphicsView {


  //show image
  private final JLabel imageLabel;

  //create layer button
  private final JButton createLayerButton;

  //image transform/filter buttons
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton sepiaButton;
  private final JButton greyButton;
  private final JButton mosaic;

  //image io buttons
  private final JMenuItem saveAllppm;
  private final JMenuItem saveAlljpeg;
  private final JMenuItem saveAllpng;
  private final JMenuItem loadFromText;
  private final JMenuItem fileOpenButton;
  private final JMenuItem fileSaveButton;
  private final JMenuItem checkerboard;

  //layer manipulation buttons
  private final JMenuItem createLayerItem;
  private final JMenuItem current;
  private final JMenuItem toggleVisible;
  private final JMenuItem remove;
  private final JMenuItem swap;
  private final JMenuItem duplicate;

  //script read menu item
  private final JMenuItem script;

  //transformation/filter menu items
  private final JMenuItem blurItem;
  private final JMenuItem sharpenItem;
  private final JMenuItem sepiaItem;
  private final JMenuItem greyscaleItem;
  private final JMenuItem mosaicItem;

  //error/success display
  private final JLabel displayMessage;

  /**
   * Constructs the entire JFrame GUI.
   */
  public JFrameImageView() {
    //init general stuff about gui.
    super();
    this.setTitle("Image Editor");
    this.setSize(5000, 5000);
    this.setPreferredSize(new Dimension(1000, 750));
    this.setLocation(300, 50);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //main panel
    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPanel = new JScrollPane(mainPanel);
    this.add(mainScrollPanel);

    //image panel
    //panel for showing the image
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Top Layer Image"));
    imagePanel.setPreferredSize(new Dimension(250, 500));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imagePanel);

    //image label to place on the panel
    imageLabel = new JLabel();
    JScrollPane imageScrollPanel = new JScrollPane(imageLabel);
    imageScrollPanel.setPreferredSize(new Dimension(1000, 1000));
    imagePanel.add(imageScrollPanel);

    //transform panel
    //panel for filter/transformation operations
    JPanel transformPanel = new JPanel();
    transformPanel.setBorder(BorderFactory.createTitledBorder("Transform/Filter"));
    transformPanel.setLayout(new FlowLayout());
    mainPanel.add(transformPanel);

    //blur button
    blurButton = new JButton("Blur");
    blurButton.setActionCommand("Blur");
    transformPanel.add(blurButton);

    //sharpen button
    sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("Sharpen");
    transformPanel.add(sharpenButton);

    //sepia button
    sepiaButton = new JButton("Sepia");
    sepiaButton.setActionCommand("Sepia");
    transformPanel.add(sepiaButton);

    //greyscale button
    greyButton = new JButton("Grey");
    greyButton.setActionCommand("Grey");
    transformPanel.add(greyButton);

    //mosaic operation
    mosaic = new JButton("Mosaic");
    mosaic.setActionCommand("Mosaic");
    transformPanel.add(mosaic);

    //general menu
    //menu bar
    JMenuBar menuBar = new JMenuBar();
    //menu for file operations
    JMenu file = new JMenu("File");
    menuBar.add(file);
    this.setJMenuBar(menuBar);

    //load file
    fileOpenButton = new JMenuItem("Open");
    fileOpenButton.setActionCommand("Load file");
    file.add(fileOpenButton);

    //save file
    fileSaveButton = new JMenuItem("Save");
    fileSaveButton.setActionCommand("Save file");
    file.add(fileSaveButton);

    //Save All menu items
    JMenu saveAllSub = new JMenu("Save All");
    file.add(saveAllSub);

    //save all in ppm format
    saveAllppm = new JMenuItem("ppm");
    saveAllppm.setActionCommand("Save All PPM");
    saveAllSub.add(saveAllppm);

    //save all in png format
    saveAllpng = new JMenuItem("png");
    saveAllpng.setActionCommand("Save All PNG");
    saveAllSub.add(saveAllpng);

    //save all in jpeg format
    saveAlljpeg = new JMenuItem("jpeg");
    saveAlljpeg.setActionCommand("Save All JPEG");
    saveAllSub.add(saveAlljpeg);

    //load From text item
    loadFromText = new JMenuItem("Load All");
    loadFromText.setActionCommand("Load All");
    file.add(loadFromText);

    //create new checkerBoard
    checkerboard = new JMenuItem("Create Checker");
    checkerboard.setActionCommand("Checkerboard");
    file.add(checkerboard);

    //menu for layer operations
    //menu for layer operations
    JMenu layerMenu = new JMenu("Layer");
    menuBar.add(layerMenu);

    //createLayer opertaion
    createLayerItem = new JMenuItem("Create Layer");
    createLayerItem.setActionCommand("Create Layer");
    layerMenu.add(createLayerItem);

    //set current operation
    current = new JMenuItem("Current Layer");
    current.setActionCommand("Current");
    layerMenu.add(current);

    //duplicate operation
    duplicate = new JMenuItem("Duplicate Layer");
    duplicate.setActionCommand("Duplicate");
    layerMenu.add(duplicate);

    //toggle operation
    toggleVisible = new JMenuItem("Toggle Layers");
    toggleVisible.setActionCommand("Toggle");
    layerMenu.add(toggleVisible);

    //remove operation
    remove = new JMenuItem("Remove Layers");
    remove.setActionCommand("Remove");
    layerMenu.add(remove);

    //swap operation
    swap = new JMenuItem("Swap Layers");
    swap.setActionCommand("Swap");
    layerMenu.add(swap);

    //script menu
    //menu for script
    JMenu scriptMenu = new JMenu("Script");
    menuBar.add(scriptMenu);

    //script
    script = new JMenuItem("Load Script");
    script.setActionCommand("Script");
    scriptMenu.add(script);

    //transformation menu
    //menu for transformation/Filter
    JMenu transformationMenu = new JMenu("Transformation");
    menuBar.add(transformationMenu);

    //blur item
    blurItem = new JMenuItem("Blur Current");
    blurItem.setActionCommand("Blur");
    transformationMenu.add(blurItem);

    //sharpen item
    sharpenItem = new JMenuItem("Sharpen Current");
    sharpenItem.setActionCommand("Sharpen");
    transformationMenu.add(sharpenItem);

    //sepia item
    sepiaItem = new JMenuItem("Sepia Current");
    sepiaItem.setActionCommand("Sepia");
    transformationMenu.add(sepiaItem);

    //greyscale item
    greyscaleItem = new JMenuItem("Greyscale Current");
    greyscaleItem.setActionCommand("Grey");
    transformationMenu.add(greyscaleItem);

    //mosaic item
    mosaicItem = new JMenuItem("Mosaic Current");
    mosaicItem.setActionCommand("Mosaic");
    transformationMenu.add(mosaicItem);

    //panel for layer operations
    //panel for layer operations
    JPanel layerPanel = new JPanel();
    layerPanel.setBorder(BorderFactory.createTitledBorder("Create Layer"));
    layerPanel.setLayout(new FlowLayout());
    mainPanel.add(layerPanel);

    //createLayer button
    createLayerButton = new JButton("Create Layer");
    createLayerButton.setActionCommand("Create Layer");
    layerPanel.add(createLayerButton);

    //panel to display messages
    //message display panel
    JPanel messageDisplay = new JPanel();
    messageDisplay.setBorder(BorderFactory.createTitledBorder("Error/Success Display"));
    messageDisplay.setLayout(new FlowLayout());
    mainPanel.add(messageDisplay);

    //for displaying error and success messages.
    displayMessage = new JLabel(" ");
    messageDisplay.add(displayMessage);

    this.pack();
  }

  @Override
  public void setListener(ActionListener listener) {
    blurButton.addActionListener(listener);
    sharpenButton.addActionListener(listener);
    greyButton.addActionListener(listener);
    sepiaButton.addActionListener(listener);
    fileOpenButton.addActionListener(listener);
    fileSaveButton.addActionListener(listener);
    createLayerButton.addActionListener(listener);
    saveAllppm.addActionListener(listener);
    saveAlljpeg.addActionListener(listener);
    saveAllpng.addActionListener(listener);
    loadFromText.addActionListener(listener);
    current.addActionListener(listener);
    remove.addActionListener(listener);
    toggleVisible.addActionListener(listener);
    swap.addActionListener(listener);
    script.addActionListener(listener);
    duplicate.addActionListener(listener);
    mosaic.addActionListener(listener);
    checkerboard.addActionListener(listener);
    createLayerItem.addActionListener(listener);
    blurItem.addActionListener(listener);
    sharpenItem.addActionListener(listener);
    sepiaItem.addActionListener(listener);
    greyscaleItem.addActionListener(listener);
    mosaicItem.addActionListener(listener);
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public String loadImages() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "ppm,jpeg,png", "jpeg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(JFrameImageView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  @Override
  public String loadTXT() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "txt", "txt");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(JFrameImageView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  @Override
  public String getNum(String message) {
    return JOptionPane.showInputDialog(message);
  }


  @Override
  public String loadDirectory() {
    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setAcceptAllFileFilterUsed(false);
    fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retvalue = fchooser.showOpenDialog(JFrameImageView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  @Override
  public String chooseLayer(int layers) {
    String[] options = new String[layers];
    for (int i = 1; i <= layers; i++) {
      options[i - 1] = "Layer " + i;
    }

    int retvalue = 0;

    if (layers != 0) {
      retvalue = JOptionPane.showOptionDialog(JFrameImageView.this, "Pick A Layer:"
          , "Options", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE
          , null, options, options[layers - 1]);
      if (retvalue >= 0) {
        String[] result = options[retvalue].split(" ");
        return result[1];
      } else {
        return "";
      }
    } else {
      return "";
    }


  }

  @Override
  public String saveImage() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(JFrameImageView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  @Override
  public String getDirectory() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(JFrameImageView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }


  @Override
  public void setImage(BufferedImage bufferedImage) {
    if (bufferedImage != null) {
      imageLabel.setIcon(new ImageIcon(bufferedImage));
    }
  }


  @Override
  public void renderMessage(String message) {
    displayMessage.setText(message);
  }
}
