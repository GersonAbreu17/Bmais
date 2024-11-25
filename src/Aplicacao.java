public class Aplicacao {

    public static void main(String[] args) {
        BTree b = new BTree();

        for(int i=9; i>0; i--)
            b.inserir(i, 0);

        System.out.println("Terminou a insercao");
        b.in_ordem();

    }
}
