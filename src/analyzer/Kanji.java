package analyzer;

import java.util.List;

public class Kanji {
    
    private String character;
    private int strokeCount;
    private List<StrokeType> strokeTypes;
    private RelationMatrix relationMatrix;

    public Kanji(String character, int strokeCount, List<StrokeType> strokeTypes, RelationMatrix matrix) {
        this.character = character;
        this.strokeCount = strokeCount;
        this.strokeTypes = strokeTypes;
        this.relationMatrix = matrix;
    }

    public String getCharacter() {
        return this.character;
    }

    public int getStrokeCount() {
        return this.strokeCount;
    }

    public List<StrokeType> getStrokeTypes() {
        return this.strokeTypes;
    }

    public RelationMatrix getRelationMatrix() {
        return this.relationMatrix;
    }
}
