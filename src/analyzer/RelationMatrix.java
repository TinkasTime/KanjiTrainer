package analyzer;

import java.util.ArrayList;
import java.util.List;

public class RelationMatrix {
    
    public enum Relation {UNKNOWN, INTERSECT, CONNECT, ABOVE, BENEATH, LEFT, RIGHT}

    private List<ArrayList<Relation>> relationMatrix;

    public RelationMatrix(List<Relation> list) {
        List<ArrayList<Relation>> rM = new ArrayList<ArrayList<Relation>>();
        
        // die Matrix muss symmetrisch NxN sein
        double res = Math.sqrt(list.size());
        if (list.size() % res == 0) {

            
            for (int i = 0; i < list.size(); i++) {
                
                for (int a = 0; a < res; a++) {
                    rM.add(new ArrayList<Relation>());
                    for (int b = 0; b < res; b++) {
                        rM.get(a).add(list.get(i));
                    }
                }

            }

        } else {
            System.out.println("Oh je, die Relationsmatrix ist nicht symmetrisch NxN :(");
        }

        this.relationMatrix = rM;
    }

    public List<ArrayList<Relation>> getRelationMatrix() {
        return this.relationMatrix;
    }
}
