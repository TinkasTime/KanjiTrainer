package canvas;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CanvasStroke {
    
    private List<Point> points;

    public CanvasStroke() {
        this.points = new ArrayList<Point>();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point p) {
        this.points.add(p);
    }

    public int getSize() {
        return points.size();
    }
}
