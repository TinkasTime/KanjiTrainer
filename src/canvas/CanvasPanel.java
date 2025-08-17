package canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class CanvasPanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color DRAW_COLOR = Color.BLACK;
    private static final BasicStroke STROKE_THICKNESS = new BasicStroke(2.0f);

    private List<CanvasStroke> drawingStrokes;
    private CanvasStroke currentStroke;
    private boolean penActive;

    private Runnable strokeFinishedCallback;

    public CanvasPanel() {
        this.setBackground(BACKGROUND_COLOR);
        // Liste an Strichen
        this.drawingStrokes = new ArrayList<CanvasStroke>();
        this.currentStroke = null;

        CanvasMouseHandler mouseHandler = new CanvasMouseHandler(this);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public List<CanvasStroke> getDrawingStrokes() {
        return drawingStrokes;
    }
    

    // Hilfsmethoden für die Button Funktionen
    /**
     * Ändert den Pen Mode im Canvas.
     * @param mode true = aktiviert den Pen Mode, false = deaktiviert den Pen Mode
     */
    public void setPenMode(boolean mode) {
        if (mode)
            this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        else
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.penActive = mode;
    }
    
    public boolean getPenMode() {
        return this.penActive;
    }

    public void clearDrawing() {
        drawingStrokes.clear();
        repaint();
    }

    public boolean hasStrokes() {
        return !drawingStrokes.isEmpty();
    }

    public void undoLastStroke() {
        if (!drawingStrokes.isEmpty()) {
            drawingStrokes.remove(drawingStrokes.size()-1);
            repaint();
        }
    }


    // Hilfsmethoden für den MouseHandler zum Erzeugen eines Striches
    public void startNewStroke(Point p) {
        this.currentStroke = new CanvasStroke();
        this.currentStroke.addPoint(p);
    }

    public void addPointToCurrentStroke(Point p) {
        if (this.currentStroke != null) {
            this.currentStroke.addPoint(p);
        }
    }

    public void finalizeCurrentStroke() {
        if (currentStroke != null) {
            drawingStrokes.add(currentStroke);
            currentStroke = null;

            if (strokeFinishedCallback != null)
                strokeFinishedCallback.run();
        }
    }


    // Malen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Male den aktuellen sowie die abgeschlossenen Striche
        if (currentStroke != null) {
            drawStroke(g2d, currentStroke);
        }
        // Male alle abgeschlossenen Striche
        for (CanvasStroke stroke : drawingStrokes) {
            drawStroke(g2d, stroke);
        }
    }

    private void drawStroke(Graphics2D g2d, CanvasStroke stroke) {
        g2d.setColor(DRAW_COLOR);
        g2d.setStroke(STROKE_THICKNESS);

        List<Point> points = stroke.getPoints();
        if (points.isEmpty())
            return;

        if (points.size() < 2) {
            Point p = points.get(0);
            g2d.drawLine(p.x, p.y, p.x, p.y);
        }

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }


    // Callbacks
    /**
     * Soll Analyse der Striche starten, wenn Strich gemalt wurde
     */
    public void setStrokeFinishedCallback(Runnable callback) {
        this.strokeFinishedCallback = callback;
    }

}
