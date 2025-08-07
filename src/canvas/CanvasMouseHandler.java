package canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;

public class CanvasMouseHandler extends MouseAdapter {

    private CanvasPanel canvasPanel;
    private CanvasStroke currentStroke = null;

    private Runnable undoButtonCallback;

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
        if (e.getButton() == MouseEvent.BUTTON1 && canvasPanel.getCurrentCanvasMode() == DrawingMode.PEN) {
            currentStroke = new CanvasStroke(canvasPanel.getCurrentCanvasMode());
            currentStroke.addPoint(e.getPoint());
        }
    }

    /**
     * Wenn gerade ein Strich gemalt wird, füge weiter Points hinzu und repaint
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentStroke != null) {
            currentStroke.addPoint(e.getPoint());
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
        if (e.getButton() == MouseEvent.BUTTON1 && currentStroke != null) {
            canvasPanel.addDrawingStroke(currentStroke);
            currentStroke = null;
            canvasPanel.repaint();
        }
    }
}
