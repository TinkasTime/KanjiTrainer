package analyzer;

import java.util.List;

import canvas.CanvasStroke;

public class KanjiAnalyzer {

    public KanjiAnalyzer() {
        System.out.println("KanjiAnalyzer wurde initialisiert.");
    }

    public String analyzeKanji(List<CanvasStroke> strokes) {
        String res = "Start der Analyse";
        
        if (strokes == null || strokes.isEmpty()) {
            res = "Keine Striche zum Analysieren.";
        } else {
            System.out.println("Analysiere " + strokes.size() + " Striche...");
            res += "\nAnalysiere " + strokes.size() + " Striche...";

            if (strokes.size() == 1) {
                res += "\nPotenziell ichi - eins";
            } else if (strokes.size() == 2) {
                res += "\nPotenziell ni - zwei";
            } else if (strokes.size() == 3) {
                res += "\nPotenziell san - drei";
            } else {
                res += "\nErkennung nicht implementiert für " +
                       strokes.size() + " Striche.";
            }
        }
        
        return res;
    }
    
}
