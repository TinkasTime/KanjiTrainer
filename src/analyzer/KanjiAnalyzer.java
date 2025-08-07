package analyzer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import canvas.CanvasStroke;

public class KanjiAnalyzer {

    private List<Kanji> kanjiDatabase;

    public KanjiAnalyzer() {
        System.out.println("KanjiAnalyzer wurde initialisiert.");
        initializedKanjiDatabase();
    }

    private void initializedKanjiDatabase() {
        kanjiDatabase = new ArrayList<>();
        kanjiDatabase.add(new Kanji("一", 1, List.of(StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("二", 2, List.of(StrokeType.HORIZONTAL, StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("三", 3, List.of(StrokeType.HORIZONTAL, StrokeType.HORIZONTAL, StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("十", 2, List.of(StrokeType.HORIZONTAL, StrokeType.VERTICAL)));
        kanjiDatabase.add(new Kanji("土", 3, List.of(StrokeType.HORIZONTAL, StrokeType.VERTICAL, StrokeType.HORIZONTAL)));
    }

    public String analyzeKanji(List<CanvasStroke> strokes) {
        String res = "Start der Analyse";
        
        if (strokes == null || strokes.isEmpty()) {
            res = "Keine Striche zum Analysieren.";
            return res;
        }

        System.out.println("Analysiere " + strokes.size() + " Striche...");
        res += "\nAnalysiere " + strokes.size() + " Striche...";

        int drawnStrokeCount = strokes.size();
        List<StrokeType> drawnStrokeTypes = extractStrokeTypes(strokes);

        for (Kanji kanji: kanjiDatabase) {
            if (kanji.getStrokeCount() == drawnStrokeCount && kanji.getStrokeTypes().equals(drawnStrokeTypes)) {
                res += "\nErkanntes Kanji: " + kanji.getCharacter() + " (Striche: " + drawnStrokeCount + ")";
                return res;
            }
        }

        res += "\nKein bekanntes Kanji mit " + drawnStrokeCount + " Strichen gefunden.";
        return res;
    }

    private List<StrokeType> extractStrokeTypes(List<CanvasStroke> strokes) {
        List<StrokeType> strokeTypes = new ArrayList<>();

        for (CanvasStroke stroke : strokes) {
            if (isHorizontal(stroke)) {
                strokeTypes.add(StrokeType.HORIZONTAL);
            } else if (isVertical(stroke)) {
                strokeTypes.add(StrokeType.VERTICAL);
            } else {
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
    
}
