import javax.swing.*;
import java.awt.BorderLayout;

public class Notepad {
    public Notepad() {
        JFrame frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        JTextArea textArea = new JTextArea();
        frame.add(textArea, BorderLayout.CENTER); // Add the text area to the center of the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
       new Notepad();
    }
}
