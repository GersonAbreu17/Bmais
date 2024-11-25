import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Aplicacao {

    public static void main(String[] args) {
        BTree b = new BTree(4);

        int[] v = {32, 10, 16, 40, 52, 37, 98, 73, 21, 1, 4, 81, 67, 29, 48, 55, 22, 94, 8, 79, 26, 14, 65, 90, 12, 34, 75, 88, 46, 3, 57, 85};

        for(int i=0;i<32;i++)
            b.inserir(v[i]);

        System.out.println("Com N igual a 4:");

        b.in_ordem();

        b = new BTree(5);

        for(int i=0;i<32;i++)
            b.inserir(v[i]);

        System.out.println("\n\nCom N igual a 5");

        b.in_ordem();


    }
}
