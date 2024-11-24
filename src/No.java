public class No
{
    public static final int m = 2;
    private int vInfo[];
    private int vPos[];
    private No vLig[];
    private int tl;

    public No()
    {
        vInfo = new int[m*2+1];
        vPos = new int[m*2+1];
        vLig = new No[m*2+2];
    }

    public No(int info, int posArq)
    {
        this();
        vInfo[0] = info;
        vPos[0] = posArq;
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
            vPos[i] = vPos[i-1];
            vLig[i] = vLig[i-1];
        }
    }

    public int getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, int info) {
        vInfo[p] = info;
    }

    public int getvPos(int p) {
        return vPos[p];
    }

    public void setvPos(int p, int pos) {
        vPos[p] = pos;
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
            vPos[i] = vPos[i+1];
            vLig[i+1] = vLig[i+2];
        }
    }
}
