public class BTree
{
    private No raiz;

    public BTree()
    {
        raiz = null;
    }

    private No navegarAteFolha(int info)
    {
        No no = raiz;
        int pos;
        while(no.getvLig(0) != null)
        {
            pos = no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return no;
    }

    private No localizarPai(No folha, int info)
    {
       No aux = raiz;
       No pai = raiz;
       int pos;
       while(aux!=folha) {
           pai = aux;
           pos = aux.procurarPosicao(info);
           aux = aux.getvLig(pos);
       }
       return pai;
    }

    private void split(No folha, No pai)
    {
        No cx1 = new No();
        No cx2 = new No();
        int qtd;
        if(folha.getvLig(0) == null) {
            qtd = (int) Math.ceil((No.M - 1) / 2);

            for (int i = 0; i < qtd; i++) {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setvLig(i, folha.getvLig(i));
            }
            cx1.setTl(qtd);

            for (int i = qtd; i < No.M; i++) {
                cx2.setvInfo(i - (qtd), folha.getvInfo(i));
                cx2.setvLig(i - (qtd), folha.getvLig(i));
            }
            cx2.setTl(No.M - qtd);

        }
        else
        {
            qtd = (int) Math.ceil(No.M/2)-1;

            for(int i=0; i<qtd; i++)
            {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setvLig(i, folha.getvLig(i));
            }
            cx1.setvLig(qtd, folha.getvLig(qtd));
            cx1.setTl(qtd);

            for(int i=qtd+1; i<No.M; i++)
            {
                cx2.setvInfo(i-(qtd+1), folha.getvInfo(i));
                cx2.setvLig(i-(qtd+1), folha.getvLig(i));
            }
            cx2.setvLig(No.M - (qtd + 1), folha.getvLig(No.M));
            cx2.setTl(No.M - (qtd + 1));
        }
        if(folha==pai) {

            folha.setvInfo(0, folha.getvInfo(qtd));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);

            int pos = pai.procurarPosicao(folha.getvInfo(qtd));

            No irmaE = null, irmaD = null;

            if (pos > 0) {
                irmaE = pai.getvLig(pos - 1);
                irmaE.setProx(cx1);
            }
            if (pos + 1 < pai.getTl()) {
                irmaD = pai.getvLig(pos + 2);
                irmaD.setAnt(cx2);
            }

            cx1.setAnt(irmaE);
            cx2.setProx(irmaD);

            cx1.setProx(cx2);
            cx2.setAnt(cx1);
        }
        else
        {
            // Inserindo o indice no pai
            int pos = pai.procurarPosicao(folha.getvInfo(qtd));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(qtd));
            pai.setTl(pai.getTl()+1);

            // Ligando as caixas
            pai.setvLig(pos, cx1);
            pai.setvLig(pos+1, cx2);

            cx1.setAnt(pai.getAnt());


            // Ligando a folha nos irmaos
            cx1.setProx(cx2);
            cx2.setAnt(cx1);

            No irmaD = null;
            No irmaE = null;

            if(pos > 0){
                irmaE = pai.getvLig(pos-1);
                irmaE.setProx(cx1);
            }
            if(pos + 1 < pai.getTl()){
                irmaD = pai.getvLig(pos+2);
                irmaD.setAnt(cx2);
            }

            cx1.setAnt(irmaE);
            cx2.setProx(irmaD);

            if(pai.getTl() > No.M-1)
            {
                folha = pai;
                pai = localizarPai(folha, folha.getvInfo(qtd));
                split(folha, pai);
            }
        }
    }

    public void inserir(int info, int posArq)
    {
        No folha,pai;
        int pos;
        if (raiz==null)
            raiz = new No(info);
        else
        {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setTl(folha.getTl()+1);
            if(folha.getTl() == No.M)
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }
    public void in_ordem()
    {
        No no =  raiz;
        if(no != null) {
            while (no.getvLig(0) != null)
                no = no.getvLig(0);
            while(no != null) {
                for(int i=0;i<no.getTl();i++)
                    System.out.println(no.getvInfo(i));
                no = no.getProx();
            }
        }
    }
}
