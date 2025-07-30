package canvas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class CanvasPanel extends JPanel {

    private static final Color DRAW_COLOR = Color.BLACK;
    private static final Color ERASE_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private List<CanvasStroke> drawingStrokes = new ArrayList<>();

    private DrawingMode currentCanvasMode = DrawingMode.NONE;

    private Runnable undoButtonCallback;
    private Runnable strokeFinishedCallback;

    public CanvasPanel() {
        setDrawingMode(currentCanvasMode);

        CanvasMouseHandler mouseHandler = new CanvasMouseHandler(this, () -> {
            if (undoButtonCallback != null) {
                undoButtonCallback.run();
            }
        });
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setUndoButtonCallback (Runnable callback) {
        this.undoButtonCallback = callback;
    }

    public void setStrokeFinishedCallback(Runnable callback) {
        this.strokeFinishedCallback = callback;
    }

    public void setDrawingMode(DrawingMode mode) {
        this.currentCanvasMode = mode;
        if (mode == DrawingMode.PEN || mode == DrawingMode.ERASER) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public DrawingMode getCurrentCanvasMode() {
        return currentCanvasMode;
    }

    public void addDrawingStroke(CanvasStroke stroke) {
        this.drawingStrokes.add(stroke);
        if (strokeFinishedCallback != null) {
            strokeFinishedCallback.run();
        }
    }

    public List<CanvasStroke> getDrawingStrokes() {
        return Collections.unmodifiableList(drawingStrokes);
    }

    public void clearDrawing() {
        drawingStrokes.clear();
        repaint();
        if (undoButtonCallback != null) {
            undoButtonCallback.run();
        }
        if (strokeFinishedCallback != null) {
            strokeFinishedCallback.run();
        }
    }

    public void undoLastStroke() {
        if (!drawingStrokes.isEmpty()) {
            drawingStrokes.remove(drawingStrokes.size()-1);
            repaint();
            if (undoButtonCallback != null) {
                undoButtonCallback.run();
            }
            if (strokeFinishedCallback != null) {
                strokeFinishedCallback.run();
            }
        }
    }

    public boolean hasStrokes() {
        return !drawingStrokes.isEmpty();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;

        for (CanvasStroke stroke : drawingStrokes) {
            if (stroke.getMode() == DrawingMode.PEN) {
                g2d.setColor(DRAW_COLOR);
            } else {
                g2d.setColor(ERASE_COLOR);
            }

            List<Point> points = stroke.getPoints();
            if (points.size() > 1) {
                for (int i = 0; i < points.size()-1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i+1);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            } else if (points.size() == 1) {
                Point p = points.get(0);
                g2d.drawLine(p.x, p.y, p.x, p.y);
            }
        }
    }
}
