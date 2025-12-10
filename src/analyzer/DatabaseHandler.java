package analyzer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    
    private static final String CSV_SEPARATOR = ",";
    private static final String INDEX_SEPERATOR = "/";
    private static final String CSV_COMMENT = "#";

    private Map<String, Kanji> database = new HashMap<>();

    /**
     *  @param analyzer.KanjiDatabase.csv;
     * @throws IOException 
     */
    public void loadDatabaseFromCSV(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            
            String line;

            // CSV Datei einlesen
            while ((line = br.readLine()) != null) {

                //leere Zeilen und Kommentare überspringen
                if (line.trim().isEmpty() || line.startsWith(CSV_COMMENT)){
                    continue;
                }
                
                database.put(line.charAt(0)+" ", parse(line));
            }

            System.out.println("Datenbank geladen. " + database.size() + " Kanji verfügbar.");
        }
    }

    private Kanji parse(String info) {
        String[] part = info.split(CSV_SEPARATOR);

        String character = part[0];
        int strokeCount = parseStrokeCount(part[1]);
        List<StrokeType> strokeTypes = parseStrokeTypes(part[2]);
        RelationMatrix relationMatrix = parseRelationMatrix(part[3]);

        return new Kanji(character, strokeCount, strokeTypes, relationMatrix);
    }

    private int parseStrokeCount(String info) {
        return Integer.valueOf(info);
    }

    private List<StrokeType> parseStrokeTypes(String info) {
        String[] part = info.split(INDEX_SEPERATOR);
        List<StrokeType> strokeTypes = new ArrayList<StrokeType>();

        for (String p : part) {
            switch (p) {
                case "HORIZONTAL":
                    strokeTypes.add(StrokeType.HORIZONTAL);
                    break;
                case "VERTICAL":
                    strokeTypes.add(StrokeType.VERTICAL);
                    break;
                case "ASCEND":
                    strokeTypes.add(StrokeType.ASCEND);
                    break;
                case "DESCEND":
                    strokeTypes.add(StrokeType.DESCEND);
                    break;

                default:
                    System.out.println("In der DatabaseHandler.java wurde ein unbehandelter StrokeType entdeckt." + info);
                    System.exit(0);
                    break;
            }
        }

        return strokeTypes;
    }

    private RelationMatrix parseRelationMatrix(String info) {
        String[] part = info.split(INDEX_SEPERATOR);
        List<RelationMatrix.Relation> list = new ArrayList<RelationMatrix.Relation>(); 

        for (String p : part) {
            switch (p) {
                case "UNKNOWN":
                    list.add(RelationMatrix.Relation.UNKNOWN);
                    break;
                case "INTERSECT":
                    list.add(RelationMatrix.Relation.INTERSECT);
                    break;
                case "CONNECT":
                    list.add(RelationMatrix.Relation.CONNECT);
                    break;
                case "ABOVE":
                    list.add(RelationMatrix.Relation.ABOVE);
                    break;
                case "BENEATH":
                    list.add(RelationMatrix.Relation.BENEATH);
                    break;
                case "LEFT":
                    list.add(RelationMatrix.Relation.LEFT);
                    break;
                case "RIGHT":
                    list.add(RelationMatrix.Relation.RIGHT);
                    break;

                default:
                    System.out.println("In der DatabaseHandler.java wurde ein unbehandelter RelationType entdeckt.");
                    System.exit(0);
                    break;
            }
        }

        RelationMatrix relationMatrix = new RelationMatrix(list);

        return relationMatrix;
    }

    public Map<String,Kanji> getDatabase() {
        return database;
    }

}
