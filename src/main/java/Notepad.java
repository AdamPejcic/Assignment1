import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notepad {
    public Notepad() {
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JTextArea textArea = new JTextArea();
        frame.add(textArea, BorderLayout.CENTER); // Add the text area to the center of the frame

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem timeAndDateMenuItem = new JMenu("Time and Date");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem selectAllMenuItem = new JMenuItem("Select All");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        fileMenu.add(timeAndDateMenuItem);
        fileMenu.add(aboutMenuItem);
        fileMenu.add(exitMenuItem);
        editMenu.add(selectAllMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(cutMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "This program was made by Adam Pejcic", "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
       new Notepad();
    }
}
