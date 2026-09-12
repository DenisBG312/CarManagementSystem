import javax.swing.*;

public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    SwingUtilities.invokeLater(() -> new CarApp().setVisible(true));
}