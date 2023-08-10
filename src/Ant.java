public class Ant
{
    int[] selected;

    double totalCost=0;
    double v1=0;
    double v2=0;
    double v3=0;
    double v4=0;
    int nodeCount;
    int fogCount;
    double totalViolation=0;
    DataGenerate data2;
    public Ant(int nodeCount, int fogCount, DataGenerate data2)
    {
        selected= new int[nodeCount];
        this.nodeCount=nodeCount;
        this.fogCount=fogCount;
        this.data2=data2;
    }
    protected void clear()
    {

        for (int j = 0; j <2*fogCount ; j++) {
            for (int i = 0; i <nodeCount ; i++) {
                selected[i] = -1;
                totalViolation=0;
            }

        }

    }

}
