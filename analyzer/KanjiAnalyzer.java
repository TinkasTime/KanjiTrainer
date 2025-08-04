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
        kanjiDatabase.add(new Kanji("一", 1, List.of(0)));
        kanjiDatabase.add(new Kanji("二", 2, List.of(0,0)));
        kanjiDatabase.add(new Kanji("三", 3, List.of(0,0,0)));
        kanjiDatabase.add(new Kanji("十", 2, List.of(0, 1)));
        kanjiDatabase.add(new Kanji("土", 3, List.of(0, 1, 0)));
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
        List<Integer> drawnDirections = extractStrokeDirections(strokes);

        for (Kanji kanji: kanjiDatabase) {
            if (kanji.getStrokeCount() == drawnStrokeCount && kanji.getStrokeDirections().equals(drawnDirections)) {
                res += "\nErkanntes Kanji: " + kanji.getCharacter() + " (Striche: " + drawnStrokeCount + ")";
                return res;
            }
        }

        res += "\nKein bekanntes Kanji mit " + drawnStrokeCount + " Strichen gefunden.";
        
        return res;
    }

    private List<Integer> extractStrokeDirections(List<CanvasStroke> strokes) {
        List<Integer> directions = new ArrayList<>();

        for (CanvasStroke stroke : strokes) {
            List<Point> points = stroke.getPoints();
            if (points.size() < 2) {
                directions.add(-1);
                continue;
            }

            Point start = points.get(0);
            Point end = points.get(points.size()- 1);

            double deltaX = Math.abs(end.getX() - start.getX());
            double deltaY = Math.abs(end.getY() - start.getY());

            if (deltaX > deltaY) {
                directions.add(0);
            } else {
                directions.add(1);
            }
        }

        return directions;
    }
    
}
