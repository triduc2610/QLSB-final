import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import controller.MainController;

public class FootballPitchApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception ex) {
                System.out.println("Failed to initialize LaF");
            }
            MainController mainController = new MainController();
            mainController.start("");
        });
    }
}
