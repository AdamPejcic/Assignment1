import org.yaml.snakeyaml.Yaml;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

public class Notepad {

    private JTextArea textArea;
    private JComboBox<String> fontComboBox;
    private JComboBox<String> styleComboBox;
    private JComboBox<Integer> sizeComboBox;
    private JComboBox<String> colourComboBox;
    private int style;
    private Color newColour;


    public Notepad() throws FileNotFoundException {
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        fontComboBox = new JComboBox<>(new String[] {"Arial", "Calibri", "Verdana"});
        styleComboBox = new JComboBox<>(new String[] {"Plain", "Bold", "Italic"});
        sizeComboBox = new JComboBox<>(new Integer[] {10, 14, 18, 24});
        colourComboBox = new JComboBox<>(new String[] {"Black", "Red", "Green", "Blue"});

        GridLayout comboBoxLayout = new GridLayout(2, 2);
        JPanel comboBoxPanel = new JPanel(comboBoxLayout);
        comboBoxPanel.add(new JLabel("Font:"));
        comboBoxPanel.add(fontComboBox);
        comboBoxPanel.add(new JLabel("Style:"));
        comboBoxPanel.add(styleComboBox);
        comboBoxPanel.add(new JLabel("Size:"));
        comboBoxPanel.add(sizeComboBox);
        comboBoxPanel.add(new JLabel("Colour:"));
        comboBoxPanel.add(colourComboBox);
        frame.add(comboBoxPanel, BorderLayout.NORTH);

        Yaml buildFile = new Yaml();
        InputStream inputStream = new FileInputStream(new File("build.yml"));
        Map<String, Object> obj = buildFile.load(inputStream);

        int font = (int) obj.get("font");
        fontComboBox.setSelectedIndex(font);
        int style = (int) obj.get("style");
        styleComboBox.setSelectedIndex(style);
        int size = (int) obj.get("size");
        sizeComboBox.setSelectedIndex(size);
        int colour = (int) obj.get("colour");
        colourComboBox.setSelectedIndex(colour);

        textArea = new JTextArea();
        changeTextProperties();
        frame.add(textArea, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem searchMenuItem = new JMenuItem("Search");
        JMenuItem timeAndDateMenuItem = new JMenuItem("Time and Date");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem selectAllMenuItem = new JMenuItem("Select All");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        fileMenu.add(searchMenuItem);
        fileMenu.add(timeAndDateMenuItem);
        fileMenu.add(aboutMenuItem);
        fileMenu.add(exitMenuItem);
        editMenu.add(selectAllMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(cutMenuItem);
        editMenu.add(deleteMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);

        timeAndDateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = new Date().toString();
                textArea.insert(date + "\n", 0);
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "This program was made by Adam Pejcic.", "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        selectAllMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });

        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedText();
            }
        });

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copySelectedText();
            }
        });

        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copySelectedText();
                deleteSelectedText();
            }
        });
        
        pasteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });

        searchMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = JOptionPane.showInputDialog(frame, "Enter a word to search:");
                String[] searchWords = searchText.split(" ");
                if (searchWords[0] != null) {
                    String text = textArea.getText();
                    int startIndex = text.indexOf(searchWords[0]);
                    if (startIndex != -1) {
                        textArea.setSelectionStart(startIndex);
                        textArea.setSelectionEnd(startIndex + searchWords[0].length());
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "The word being searched for could not be found.",
                                "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        fontComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTextProperties();
            }
        });

        styleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTextProperties();
            }
        });

        sizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTextProperties();
            }
        });

        colourComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTextProperties();
            }
        });

        frame.setVisible(true);
    }

    public void copySelectedText() {
        String selectedText = textArea.getSelectedText();
        if (selectedText != null) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection copySelection = new StringSelection(selectedText);
            clipboard.setContents(copySelection, null);
        }
    }

    public void deleteSelectedText() {
        int start = textArea.getSelectionStart();
        int end = textArea.getSelectionEnd();
        textArea.replaceRange("", start, end);
    }

    public void changeTextProperties() {
        String selectedFont = fontComboBox.getSelectedItem().toString();

        if (styleComboBox.getSelectedIndex() == 0) {
            style = Font.PLAIN;
        }
        else if (styleComboBox.getSelectedIndex() == 1) {
            style = Font.BOLD;
        }
        else if (styleComboBox.getSelectedIndex() == 2) {
            style = Font.ITALIC;
        }

        int fontSize = (Integer) sizeComboBox.getSelectedItem();

        String selectedColour = colourComboBox.getSelectedItem().toString();
        if (selectedColour.equals("Black")) {
            newColour = Color.BLACK;
        }
        else if (selectedColour.equals("Red")) {
            newColour = Color.RED;
        }
        else if (selectedColour.equals("Green")) {
            newColour = Color.GREEN;
        }
        else if (selectedColour.equals("Blue")) {
            newColour = Color.BLUE;
        }

        textArea.setFont(new Font(selectedFont, style, fontSize));
        textArea.setForeground(newColour);
    }

    public static void main(String[] args) throws FileNotFoundException {
       new Notepad();
    }
}
