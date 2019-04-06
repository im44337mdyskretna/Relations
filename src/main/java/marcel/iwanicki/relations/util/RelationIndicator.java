package marcel.iwanicki.relations.util;

import java.util.ArrayList;
import java.util.List;

public class RelationIndicator {

    private RelationReader relationReader;
    private List<String> inputCollection;
    private List<RelationReader.Relation> inputRelation;

    public RelationIndicator(String srcPath) {
        initInputData(srcPath);
    }

    private void initInputData(String srcPath) {
        relationReader = new RelationReader(srcPath);
        inputCollection = relationReader.readCollection();
        inputRelation = relationReader.readRelation();
    }

    /*
    Sprawdza czy relacja jest zwrotna
    Funkcja przyjumuje, że relacja jest zwrotna gdy Lista złożona z (a,a),(b,b),(c,c)...itd.
    zawiera się w liście inputRelation, jeśli tak nie jest zwraca fałsz i wypisuje komunikat.
     */
    private boolean checkReturnRelationCondition() {
        List<RelationReader.Relation> returnRelationCondition = new ArrayList<RelationReader.Relation>();
        for(int i=0; i<inputCollection.size(); i++)
            returnRelationCondition.add(new RelationReader().new Relation(inputCollection.get(i), inputCollection.get(i)));

        boolean result = inputRelation.containsAll(returnRelationCondition);
        System.out.println(result ? "" : "Nie jest relacja zwrotna : nie zawiera wszystkich par (a,a),(b,b)...(z,z)");

        return result;
    }

    /*
    Sprawdz czy relacja jest symetryczna
    Funkcja przyjumuje ze relacja jest symetryczna, gdy znajdziemy wsrod relacji takie pary, że
    xRy => yRz, gdzie
    xRy = current (iterowane po wszystkich relacjach)
    yRx = (iterowane po j, jesli żadna z par nie spelnia warunku funkcja zwraca fałsz)
     */
    private boolean checkSymmetricalRelationCondition() {
        for(int i=0; i<inputRelation.size(); i++) {

            RelationReader.Relation current = inputRelation.get(i);

            for(int j=0; j<inputRelation.size(); j++) {

                if(inputRelation.get(j).getRelA().equals(current.getRelB()) && inputRelation.get(j).getRelB().equals(current.getRelA()))
                    break;

                if(j == inputRelation.size() - 1) {
                    System.out.println("Nie jest relacja symetrii: (" + current.getRelA() + "," + current.getRelB() + ") nie ma symetrii");
                    return false;
                }
            }
        }

        return true;
    }

    /*
    Sprawdz czy relacja jest przechodnia
    Funkcja przyjmuje ze relacja jest przechodnia, gdy znajdziemy wśród relacji takie pary, że
    xRy ^ yRz => xRz, gdzie
    xRy = currentLeft_A (iterowane po wszystkich relacjach)
    yRz = currentLeft_B (przypisywane tylko gdy w relacjach znaleziona zostanie taka, ze currentLeft_A ma y po prawej, a currentLeft_B ma y po lewej)
    xRz = currentRight (iterowane po k, jesli żadna z par nie spelnia warunku funkcja zwraca fałsz)
     */
    private boolean checkTransitiveRelationCondition() {
        for(int i=0; i<inputRelation.size(); i++) {
            RelationReader.Relation currentLeft_A = inputRelation.get(i);

            for(int j=0; j<inputRelation.size(); j++) {

                RelationReader.Relation currentLeft_B = null;
                if(inputRelation.get(j).getRelA().equals(currentLeft_A.getRelB())) {
                    currentLeft_B = inputRelation.get(j);

                    for(int k=0; k<inputRelation.size(); k++) {

                        RelationReader.Relation currentRight = inputRelation.get(k);
                        if(currentRight.getRelA().equals(currentLeft_A.getRelA()) && currentRight.getRelB().equals(currentLeft_B.getRelB()))
                            break;

                        if(k == inputRelation.size() - 1) {
                            System.out.println("Nie jest relacja przechodnia dla: (" + currentLeft_A.getRelA() + "," + currentLeft_A.getRelB() + "), (" + currentLeft_B.getRelA() + "," + currentLeft_B.getRelB() + ")");
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /*
    Sprawdz czy relacja jest relacją równoważności
     */
    public boolean checkEquivalenceRelation() {
        boolean a = checkReturnRelationCondition();
        boolean b = checkSymmetricalRelationCondition();
        boolean c = checkTransitiveRelationCondition();

        return (a && b && c);
    }
}
