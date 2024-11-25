public class BTree
{
    private No raiz;
    private int n;

    public BTree(int n)
    {
        raiz = null;
        this.n = n;
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
        No cx1 = new No(n);
        No cx2 = new No(n);

        int qtd;

        if(folha.getvLig(0) == null)
        {
            qtd = (int) Math.ceil((double)(n-1)/2);
            for(int i=0;i<qtd;i++)
            {
                cx1.setvInfo(i, folha.getvInfo(i));
            }
            cx1.setTl(qtd);
            for(int i=qtd; i < folha.getTl(); i++)
            {
                cx2.setvInfo(i-qtd, folha.getvInfo(i));
            }
            cx2.setTl(folha.getTl() - qtd);

            if(folha.getAnt() != null)
                folha.getAnt().setProx(cx1);
            cx1.setProx(cx2);
            cx2.setProx(folha.getProx());
            if(folha.getProx() != null)
                folha.getProx().setAnt(cx2);
            cx2.setAnt(cx1);
            cx1.setAnt(folha.getAnt());
            folha.setAnt(null);
            folha.setProx(null);
        }
        else
        {
            qtd = (int) Math.ceil((double)n/2) - 1;
            for(int i=0;i<qtd;i++)
            {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setvLig(i, folha.getvLig(i));
            }
            cx1.setvLig(qtd, folha.getvLig(qtd));
            cx1.setTl(qtd);
            int j = 0;
            for(int i=qtd+1;i<folha.getTl();i++)
            {
                cx2.setvInfo(j, folha.getvInfo(i));
                cx2.setvLig(j++, folha.getvLig(i));
            }
            cx2.setvLig(j, folha.getvLig(folha.getTl()));
            cx2.setTl(j);
        }
        if(folha == pai)
        {
            folha.setvInfo(0, folha.getvInfo(qtd));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        }
        else
        {
            int pos = pai.procurarPosicao(folha.getvInfo(qtd));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(qtd));
            pai.setTl(pai.getTl() + 1);
            pai.setvLig(pos, cx1);
            pai.setvLig(pos+1, cx2);
            if(pai.getTl() >= n)
            {
                folha = pai;
                pai = localizarPai(pai, pai.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    public void inserir(int info)
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
            if(folha.getTl() == n)
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
