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
        for(int i=0; i<No.m; i++)
        {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setvLig(i, folha.getvLig(i));
        }
        cx1.setvLig(No.m, folha.getvLig(No.m));
        cx1.setTl(No.m);

        for(int i=No.m+1; i<No.m*2+1; i++)
        {
            cx2.setvInfo(i-(No.m+1), folha.getvInfo(i));
            cx2.setvPos(i-(No.m+1), folha.getvPos(i));
            cx2.setvLig(i-(No.m+1), folha.getvLig(i));
        }
        cx2.setvLig(No.m, folha.getvLig(No.m*2+1));
        cx2.setTl(No.m);

        if(folha==pai)
        {
            folha.setvInfo(0, folha.getvInfo(No.m));
            folha.setvPos(0, folha.getvPos(No.m));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        }
        else
        {
            int pos = pai.procurarPosicao(folha.getvInfo(No.m));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(No.m));
            pai.setvPos(pos, folha.getvPos(No.m));
            pai.setTl(pai.getTl()+1);
            pai.setvLig(pos, cx1);
            pai.setvLig(pos+1, cx2);
            if(pai.getTl() > No.m*2)
            {
                folha = pai;
                pai = localizarPai(folha, folha.getvInfo(No.m));
                split(folha, pai);
            }
        }
    }

    public void inserir(int info, int posArq)
    {
        No folha,pai;
        int pos;
        if (raiz==null)
            raiz = new No(info, posArq);
        else
        {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl()+1);
            if(folha.getTl() > No.m*2)
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public void in_ordem()
    {
        in_ordem(raiz);
    }

    public void in_ordem(No raiz)
    {
        if(raiz!=null)
        {
            for(int i=0; i<raiz.getTl(); i++)
            {
                in_ordem(raiz.getvLig(i));
                System.out.println(raiz.getvInfo(i));
            }
            in_ordem(raiz.getvLig(raiz.getTl()));
        }
    }

    //--- Exclusao ---
    private No buscaNo(int info)
    {
        No aux = raiz;
        boolean flag=false;
        int pos;
        while(aux!=null && !flag)
        {
            pos = aux.procurarPosicao(info);
            if (pos<aux.getTl() && info == aux.getvInfo(pos))
                flag = true;
            else
                aux = aux.getvLig(pos);
        }
        return aux;
    }

    private No localizarSubE(No no, int pos)
    {
        no = no.getvLig(pos);
        while (no.getvLig(0)!=null)
            no = no.getvLig(no.getTl());
        return no;
    }

    private No localizarSubD(No no, int pos)
    {
        no = no.getvLig(pos);
        while (no.getvLig(0)!=null)
            no = no.getvLig(0);
        return no;
    }

    public void excluir(int info)
    {
        No no, subE, subD, folha;
        no = buscaNo(info);
        int pos;
        if(no!=null)
        {
            pos = no.procurarPosicao(info);
            if(no.getvLig(0)!=null) //nao folha
            {
                subE = localizarSubE(no, pos);
                subD = localizarSubD(no, pos+1);
                if(subE.getTl() > No.m || subD.getTl() == No.m)
                {
                    no.setvInfo(pos, subE.getvInfo(subE.getTl()-1));
                    no.setvPos(pos, subE.getvPos(subE.getTl()-1));
                    folha = subE;
                    pos = subE.getTl()-1;
                }
                else {
                    no.setvInfo(pos, subD.getvInfo(0));
                    no.setvPos(pos, subD.getvPos(0));
                    folha = subD;
                    pos = 0;
                }
            }
            else
                folha = no;

            //excluir da folha
            folha.remanejarExclusao(pos);
            folha.setTl(folha.getTl()-1);

            if (folha ==raiz && folha.getTl()==0)
                raiz = null;
            else
                if(folha!=raiz && folha.getTl()<No.m)
                    redistribuirConcatenar(folha);
        }
    }

    public void redistribuirConcatenar(No folha)
    {
        No irmaE = null, irmaD = null;
        No pai = localizarPai(folha, folha.getvInfo(0));
        int posPai = pai.procurarPosicao(folha.getvInfo(0));
        if (posPai>0)
            irmaE = pai.getvLig(posPai-1);
        if (posPai<pai.getTl())
            irmaD = pai.getvLig(posPai+1);

        if(irmaE!=null && irmaE.getTl()>No.m)
        {
            folha.remanejar(0);
            folha.setvInfo(0, pai.getvInfo(posPai-1));
            folha.setvPos(0, pai.getvPos(posPai-1));
            folha.setTl(folha.getTl()+1);
            pai.setvInfo(posPai-1, irmaE.getvInfo(irmaE.getTl()-1));
            pai.setvPos(posPai-1, irmaE.getvPos(irmaE.getTl()-1));
            folha.setvLig(0, irmaE.getvLig(irmaE.getTl()));
            irmaE.setTl(irmaE.getTl()-1);
        }
        else
        if(irmaD!=null && irmaD.getTl()>No.m)
        {
        }
        else //concatenacao
        {
            if(irmaE!=null)
            {
                irmaE.setvInfo(irmaE.getTl(), pai.getvInfo(posPai-1));
                irmaE.setvPos(irmaE.getTl(), pai.getvPos(posPai-1));
                irmaE.setTl(irmaE.getTl()+1);
                pai.remanejarExclusao(posPai-1);
                pai.setTl(pai.getTl()-1);
                pai.setvLig(posPai-1, irmaE);
                for(int i=0; i<folha.getTl(); i++)
                {
                    irmaE.setvInfo(irmaE.getTl(), folha.getvInfo(i));
                    irmaE.setvPos(irmaE.getTl(), folha.getvPos(i));
                    irmaE.setvLig(irmaE.getTl(), folha.getvLig(i));
                    irmaE.setTl(irmaE.getTl()+1);
                }
                irmaE.setvLig(irmaE.getTl(), folha.getvLig(folha.getTl()));
            }
            else //irmaD!=null
            {

            }




        }
    }
}
