public class No
{
    public static final int M = 3;
    private int vInfo[];
    private No vLig[];
    private int tl;
    private No prox;
    private No ant;

    public No()
    {
        vInfo = new int[M+1];
        vLig = new No[M+2];
    }

    public No(int info)
    {
        this();
        vInfo[0] = info;
        tl = 1;
    }

    public int procurarPosicao(int info)
    {
        int i=0;
        while(i<tl && info > vInfo[i])
            i++;
        return i;
    }

    public void remanejar(int pos)
    {
        vLig[tl+1] = vLig[tl];
        for(int i=tl; i>pos; i--)
        {
            vInfo[i] = vInfo[i-1];
            vLig[i] = vLig[i-1];
        }
    }

    public int getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, int info) {
        vInfo[p] = info;
    }

    public No getvLig(int p) {
        return vLig[p];
    }

    public void setvLig(int p, No lig) {
        vLig[p] = lig;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }

    public void remanejarExclusao(int pos)
    {
        for(int i=pos;i<tl-1;i++)
        {
            vInfo[i] = vInfo[i+1];
            vLig[i+1] = vLig[i+2];
        }
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }
}
