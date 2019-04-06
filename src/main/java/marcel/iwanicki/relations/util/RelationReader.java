package marcel.iwanicki.relations.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelationReader {

    /*
    Relation
    Klasa jest zbiornikiem na pare Stringow
     */
    public class Relation {

        private String relA, relB;

        public Relation(String relA, String relB) {
            this.relA = relA;
            this.relB = relB;
        }

        public String getRelA() {
            return relA;
        }

        public String getRelB() {
            return relB;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Relation relation = (Relation) o;
            return Objects.equals(relA, relation.relA) &&
                    Objects.equals(relB, relation.relB);
        }

        @Override
        public int hashCode() {
            return Objects.hash(relA, relB);
        }
    }

    private String srcPath;

    public RelationReader() {}

    public RelationReader(String srcPath) {
        this.srcPath = srcPath;
    }

    private String readLineOfFile(int lineNumber) {

        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        String line = "";

        try {

            fileReader = new FileReader(srcPath);
            bufferedReader = new BufferedReader(fileReader);

            int counter = 0;
            String iLine;
            while((iLine = bufferedReader.readLine()) != null) {
                if(counter == lineNumber)
                    line = iLine;

                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    /*
    Wczytaj zbior X z pierwszej linijki pliku
     */
    public List<String> readCollection() {

        String sCollection = readLineOfFile(0);
        List<String> result = new ArrayList<String>();

        if(!(sCollection.startsWith("X={"))) {
            System.err.println("ERROR: Zbior powinien sie zaczynac od 'X={', nie 'X = {' itp...");
            return null;
        }
        else
            for(int i=3; i<sCollection.length(); i++)
                if(sCollection.charAt(i) != ',' && sCollection.charAt(i) != '}') {
                    String sToAdd = "";
                    char cToAdd = sCollection.charAt(i);
                    sToAdd += cToAdd;

                    result.add(sToAdd);
                }

        return result;
    }

    /*
    Wczytaj relacje R z drugiej linijki pliku
     */
    public List<Relation> readRelation() {

        String sRelation = readLineOfFile(1);
        List<Relation> result = new ArrayList<Relation>();

        if(!(sRelation.startsWith("R={"))) {
            System.err.println("ERROR: Relacja powinna sie zaczynac od 'R={', nie 'R = {' itp...");
            return null;
        }
        else
            for(int i=4; i<sRelation.length(); i++) {
                if(sRelation.charAt(i-1) == '(') {
                    String sToAdd_A = "";
                    String sToAdd_B = "";
                    char cToAdd_A = sRelation.charAt(i);
                    char cToAdd_B = sRelation.charAt(i+2);
                    sToAdd_A += cToAdd_A;
                    sToAdd_B += cToAdd_B;

                    result.add(new Relation(sToAdd_A, sToAdd_B));
                }
            }

        return result;
    }

}
