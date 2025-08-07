package analyzer;

import java.util.List;

public class Kanji {
    
    private String character;
    private int strokeCount;
    private List<StrokeType> strokeTypes;

    public Kanji(String character, int strokeCount, List<StrokeType> strokeTypes) {
        this.character = character;
        this.strokeCount = strokeCount;
        this.strokeTypes = strokeTypes;
    }

    public String getCharacter() {
        return character;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public List<StrokeType> getStrokeTypes() {
        return strokeTypes;
    }
}
