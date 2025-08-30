package analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KanjiDatabase {
    
    private List<Kanji> allKanji;

    public KanjiDatabase() {
        this. allKanji = initializeKanjiDatabase();
    }

    private List<Kanji> initializeKanjiDatabase() {
        List<Kanji> kanjiDatabase = new ArrayList<>();

        kanjiDatabase.add(new Kanji("一", 1, List.of(StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("二", 2, List.of(StrokeType.HORIZONTAL, StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("三", 3, List.of(StrokeType.HORIZONTAL, StrokeType.HORIZONTAL, StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("十", 2, List.of(StrokeType.HORIZONTAL, StrokeType.VERTICAL)));
        kanjiDatabase.add(new Kanji("土", 3, List.of(StrokeType.HORIZONTAL, StrokeType.VERTICAL, StrokeType.HORIZONTAL)));
        kanjiDatabase.add(new Kanji("口", 3, List.of(StrokeType.VERTICAL, StrokeType.HORIZONTAL, StrokeType.VERTICAL)));
        kanjiDatabase.add(new Kanji("工", 3, List.of(StrokeType.HORIZONTAL, StrokeType.VERTICAL,  StrokeType.HORIZONTAL)));

        return kanjiDatabase;
    }

    public List<Kanji> filterMatchingKanji(List<StrokeType> strokes) {
        if (strokes.isEmpty()) {
            return new ArrayList<>(allKanji);
        }

        return allKanji.stream().filter(kanji -> kanji.getStrokeCount() >= strokes.size() && kanji.getStrokeTypes().subList(0, strokes.size()).equals(strokes)).collect(Collectors.toList());
    }

}
