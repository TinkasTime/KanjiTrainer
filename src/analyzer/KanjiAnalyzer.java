package analyzer;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import canvas.CanvasStroke;

public class KanjiAnalyzer {

    private Map<String, Kanji> Database;

    public KanjiAnalyzer() {
        DatabaseHandler db = new DatabaseHandler();
        try {
            db.loadDatabaseFromCSV("src/analyzer/KanjiDatabase.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String analyzeKanji(List<CanvasStroke> strokes) {
        if (strokes == null || strokes.isEmpty()) {
            return "Keine Striche zum Analysieren.";
        }

        List<StrokeType> drawnStrokeTypes = extractStrokeTypes(strokes);
        //List<Kanji> filteredKanji = kanjiDatabase.filterMatchingKanji(drawnStrokeTypes);

        // Szenario 1: Es gibt einen genauen Treffer
        /* for (Kanji kanji: filteredKanji) {
            if (kanji.getStrokeCount() == drawnStrokeTypes.size()) {
                return "Erkanntes Kanji: " + kanji.getCharacter() + " (Striche: " + drawnStrokeTypes.size() + ")";
            }
        } */

        // Szenario 2: Es gibt potentielle Kanji, aber keinen exakten
        // if (!filteredKanji.isEmpty()) {
        //     String res = "Potenzielle Kanji: ";
        //     for (Kanji kanji : filteredKanji) {
        //         res += kanji.getCharacter() + " ";
        //     }
        //     return res.trim();
        // }

        // Szenario 3: Es wurde kein Kanji gefunden
        return "Kein bekanntes Kanji mit diesen Strichen gefunden.";
    }

    private List<StrokeType> extractStrokeTypes(List<CanvasStroke> strokes) {
        List<StrokeType> strokeTypes = new ArrayList<>();

        for (CanvasStroke stroke : strokes) {
            if (isHorizontal(stroke)) {
                System.out.println("Horizontal detected");
                strokeTypes.add(StrokeType.HORIZONTAL);
            } else if (isVertical(stroke)) {
                System.out.println("Vertical detected");
                strokeTypes.add(StrokeType.VERTICAL);
            } else if (isAscend(stroke)) {
                System.out.println("Ascend detected");
                strokeTypes.add(StrokeType.ASCEND);
            }  else if (isDescend(stroke)) {
                System.out.println("Descend detected");
                strokeTypes.add(StrokeType.DESCEND);
            } else {
                System.out.println("UNKNOWN detected");
                strokeTypes.add(StrokeType.UNKNOWN);
            }
        }

        return strokeTypes;
    }

    /**
     * Prüft, ob der Strich eine horizontale Linie ist. Das Verhältnis von Breite und Höhe ist 2:1
     * @param stroke zu analysierender Strich, besteht aus Points
     * @return true wenn horizontal, sonst false
     */
    private boolean isHorizontal(CanvasStroke stroke) {
        List<Point> points = stroke.getPoints();

        // Strich muss aus mindestens zwei Points bestehen.
        if (points.size() < 2)  return false;

        double deltaX = getBoundingBoxWidth(points);
        double deltaY = getBoundingBoxHeight(points);

        // Wenn die Breite mindestens doppelt so lang ist wie die Höhe
        if (deltaX >= deltaY * 2.0) return true;
        return false;
    }

    /**
     * Prüft, ob der Strich eine vertikale Linie ist. Das Verhältnis von Breite und Höhe ist 1:2
     * @param stroke zu analysierender Strich, besteht aus Points
     * @return true wenn vertikal, sonst false
     */
    private boolean isVertical(CanvasStroke stroke) {
        List<Point> points = stroke.getPoints();

        // Strich muss aus mindestens zwei Points bestehen.
        if (points.size() < 2) return false;

        double deltaX = getBoundingBoxWidth(points);
        double deltaY = getBoundingBoxHeight(points);

        // Wenn die Höhe mindestens doppelt so hoch ist wie die Breite
        if (deltaY >= deltaX * 2.0) return true;
        return false;
    }

    /**
     * Berechnet die Pixelbreite der Fläche, die der Strich einnimmt
     * @param points zu analysierender Strich
     * @return Pixelbreite
     */
    private double getBoundingBoxWidth(List<Point> points) {
        if (points.isEmpty())  return 0;
        int minX = points.get(0).x;
        int maxX = points.get(0).x;
        for (Point p: points) {
            if (p.x < minX) minX = p.x;
            if (p.x > maxX) maxX = p.x;
        }
        return maxX - minX;
    }

    /**
     * Berechnet die Pixelhöhe der Fläche, die der Strich einnimmt
     * @param points zu analysierender Strich
     * @return Pixelhöhe
     */
    private double getBoundingBoxHeight(List<Point> points) {
        if (points.isEmpty()) return 0;
        int minY = points.get(0).y;
        int maxY = points.get(0).y;
        for (Point p : points) {
            if (p.y < minY) minY = p.y;
            if (p.y > maxY) maxY = p.y;
        }
        return maxY - minY;
    }
    
    private boolean isAscend(CanvasStroke stroke) {

        // Zu wenige Punkte für eine Analyse
        if (stroke.getSize() < 2) return false;

        // lineare Regression
        int n = stroke.getSize();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        double x = 0, y = 0;

        for (Point p : stroke.getPoints()) {
            x = p.getX();
            y = p.getY();

            sumX += x;
            sumY += y;
            sumXY += (x*y);
            sumX2 += (x*x);
        }

        // Formel: m = (n*sumXY - sumX*sumY) / (n*sumX2 - sumX*sumX)
        double zähler = (n * sumXY) - (sumX * sumY);
        double nenner = (n * sumX2) - (sumX * sumX);

        // Steigung m
        double m = zähler / nenner;

        // Koordinatenursprung ist oben links; negative Steigung = steigend
        if (m < 0) return true;

        return false;
    }

    private boolean isDescend(CanvasStroke stroke) {

        // Zu wenige Punkte für eine Analyse
        if (stroke.getSize() < 2) return false;

        // lineare Regression
        int n = stroke.getSize();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        double x = 0, y = 0;

        for (Point p : stroke.getPoints()) {
            x = p.getX();
            y = p.getY();

            sumX += x;
            sumY += y;
            sumXY += (x*y);
            sumX2 += (x*x);
        }

        // Formel: m = (n*sumXY - sumX*sumY) / (n*sumX2 - sumX*sumX)
        double zähler = (n * sumXY) - (sumX * sumY);
        double nenner = (n * sumX2) - (sumX * sumX);

        // nenner geht gegen 0
        if (Math.abs(nenner) < 0.001) {
            return false; // vertical
        }

        // Steigung m
        double m = zähler / nenner;

        if (m > 0) return true;

        return false;
    }

}
