package canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasMouseHandler extends MouseAdapter {

    private CanvasPanel canvasPanel;

    public CanvasMouseHandler(CanvasPanel panel) {
        this.canvasPanel = panel;
    }

    /**
     * Wenn die linke Maustaste gedrückt wird und PEN aktiv ist,
     * starte neuen Strich currentStroke mit Anfangspunkt
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // linke Maustaste = MouseEvent.BUTTON1
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (canvasPanel.getPenMode()) {
                canvasPanel.startNewStroke(e.getPoint());
                canvasPanel.repaint();
            }
        }
    }

    /**
     * Wenn der Pen Mode aktiv ist, füge weiter Points hinzu und repaint
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (canvasPanel.getPenMode()) {
            canvasPanel.addPointToCurrentStroke(e.getPoint());
            canvasPanel.repaint();
        }
    }

    /**
     * Wenn mit der linken Maustaste ein Strich gemalt wurde und jetzt losgelassen wird,
     * wird der gerade abgeschlossene Strich dem canvasPanel hinzugefügt,
     * der currentStroke ist wieder null und
     * das Canvas wird repaint.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // linke Maustaste = MouseEvent.BUTTON1
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (canvasPanel.getPenMode()) {
                canvasPanel.finalizeCurrentStroke();
                canvasPanel.repaint();
            }
        }
    }
}
