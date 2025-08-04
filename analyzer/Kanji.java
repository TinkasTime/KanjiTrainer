package analyzer;

import java.util.List;

public class Kanji {
    
    private String character;
    private int strokeCount;
    private List<Integer> strokeDirections;

    public Kanji(String character, int strokeCount, List<Integer> strokeDirections) {
        this.character = character;
        this.strokeCount = strokeCount;
        this.strokeDirections = strokeDirections;
    }

    public String getCharacter() {
        return character;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public List<Integer> getStrokeDirections() {
        return strokeDirections;
    }
}
