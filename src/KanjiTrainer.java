import javax.swing.SwingUtilities;

import canvas.SimpleCanvas;

public class KanjiTrainer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KanjiTrainer app = new KanjiTrainer();
            app.startApplication();
        });        
    }

    private void startApplication() {
        new SimpleCanvas();
    }

}
