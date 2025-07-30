package canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CanvasMouseHandler implements MouseListener, MouseMotionListener {
    
    private static final Color DRAW_COLOR = Color.BLACK;
    private static final Color ERASE_COLOR = Color.WHITE;

    private CanvasPanel canvasPanel;
    private CanvasStroke currentStroke = null;
    private Point lastPoint = null;

    private Runnable undoButtonCallback;

    public CanvasMouseHandler(CanvasPanel panel, Runnable undoCallback) {
        this.canvasPanel = panel;
        this.undoButtonCallback = undoCallback;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (canvasPanel.getCurrentCanvasMode() == DrawingMode.PEN || canvasPanel.getCurrentCanvasMode() == DrawingMode.ERASER) {

            lastPoint = e.getPoint();
            currentStroke = new CanvasStroke(canvasPanel.getCurrentCanvasMode());
            currentStroke.addPoint(lastPoint);

            canvasPanel.addDrawingStroke(currentStroke);

            canvasPanel.repaint();

            if (undoButtonCallback != null) {
                undoButtonCallback.run();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (canvasPanel.getCurrentCanvasMode() == DrawingMode.PEN || canvasPanel.getCurrentCanvasMode() == DrawingMode.ERASER
            ) {

            lastPoint = null;
            currentStroke = null;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((canvasPanel.getCurrentCanvasMode() == DrawingMode.PEN || canvasPanel.getCurrentCanvasMode() == DrawingMode.ERASER) && lastPoint != null && currentStroke != null) {
            
            Point currentPoint = e.getPoint();
            currentStroke.addPoint(currentPoint);
            
            Graphics g = canvasPanel.getGraphics();
            if (g != null) {
                if (canvasPanel.getCurrentCanvasMode() == DrawingMode.PEN) {
                    g.setColor(DRAW_COLOR);
                } else {
                    g.setColor(ERASE_COLOR);
                }
                g.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
                g.dispose();
            }
            lastPoint = currentPoint;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
