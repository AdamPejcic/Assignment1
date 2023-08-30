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
    public Notepad() throws FileNotFoundException {
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        Yaml buildFile = new Yaml();
        InputStream inputStream = new FileInputStream(new File("build.yml"));
        Map<String, Object> obj = buildFile.load(inputStream);
        String font = (String) obj.get("font");
        int size = (int) obj.get("size");
        int red = (int) obj.get("red");
        int green = (int) obj.get("green");
        int blue = (int) obj.get("blue");
        Color textColor = new Color(red, green, blue);
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font(font, Font.PLAIN, size));
        textArea.setForeground(textColor);
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
                int start = textArea.getSelectionStart();
                int end = textArea.getSelectionEnd();
                textArea.replaceRange("", start, end);
            }
        });

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection copySelection = new StringSelection(selectedText);
                    clipboard.setContents(copySelection, null);
                }
            }
        });

        cutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedText = textArea.getSelectedText();
                if (selectedText != null) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection copySelection = new StringSelection(selectedText);
                    clipboard.setContents(copySelection, null);
                    int start = textArea.getSelectionStart();
                    int end = textArea.getSelectionEnd();
                    textArea.replaceRange("", start, end);
                }
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

        frame.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException {
       new Notepad();
    }
}
