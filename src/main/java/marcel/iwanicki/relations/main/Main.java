package marcel.iwanicki.relations.main;

import marcel.iwanicki.relations.util.RelationIndicator;

import java.util.Scanner;

public class Main {

    public static final String SRC_PATH = "src/main/resources/input.txt";

    public static void main(String[] args) {
        generateInfoMessage();

        String srcPath = readPathFromUser();
        RelationIndicator relationIndicator = new RelationIndicator(srcPath);
        boolean equivalence = relationIndicator.checkEquivalenceRelation();

        System.out.println(equivalence ? "Relacja jest relacja rownowaznosci" : "Relacja nie jest relacja rownowaznosci");
    }

    private static void generateInfoMessage() {
        System.out.println("=======================================");
        System.out.println("[PLIK POWINIEN WYGLADAC NASTEPUJACO] : ");
        System.out.println("X={[DANE]}\n" +
                "R={[PARY UPORZADKOWANE]}");
        System.out.println("\n[PRZYKLAD] : ");
        System.out.println("X={a,b,c,d}\n" +
                "R={(a,a),(b,b),(c,c),(d,d),(a,b),(b,a)}\n");
        System.out.println("BEZ SPACJI");
        System.out.println("=======================================");
    }

    private static String readPathFromUser() {
        System.out.print("\nPodaj sciezke pliku tekstowego, lub wpisz '0' aby wybrac domyslna sciezke \"src/main/resources/input.txt\": ");
        Scanner scanner = new Scanner(System.in);
        String srcPath = scanner.next();
        String actualSrcPath = (srcPath.equals("0")) ? SRC_PATH : srcPath;

        scanner.close();
        return actualSrcPath;
    }
}
