import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by ParnianPC on 13/08/2021.
 */
public class SoftVAN {
    DataGenerate data2;
    public SoftVAN(DataGenerate data2) {
        this.data2 = data2;
         costs=new double[data2.getFogCount()];
         order = new int[data2.getNodeCount()];
    }
    double a=0.5;
    double[] costs;
    int[] order ;
    public int clacY(int i) {
        boolean fog = false;
        boolean cloud = false;
        double y=0;
        for (int k = 0; k < data2.getFogCount(); k++) {
          y+= (data2.getFogDelay()[i][k] - data2.getLocalCost()[i]) / data2.getLocalCost()[i];}
            if (y < 0) {
                fog = true;
            } else {
                fog = false;
            }

        if (fog == true) {
            return 1;
        } else {
            return 0;
        }

    }
    double cost=0;
    public void taskoffloading() {
        Random random = new Random();
        double[] qdelay=data2.getPrimaryLoad().clone();
        for (int i = 0; i < data2.getNodeCount(); i++) {
                int y =clacY(i);
                if (y == 1) {
                    double total = 0;
                    for (int j = 0; j <data2.getFogCount(); j++) {
                          if (data2.getFogCost()[i][j]!=0){
                          costs[j]=a*(qdelay[j]+data2.getSystemDelay()[i][j]);
                                             }else {
                              costs[j]=Double.MAX_VALUE;
                          }}
                    ArrayList l = new ArrayList();
                    for (int j = 0; j <data2.getFogCount() ; j++) {
                        ArrayList e = new ArrayList();
                        e.add(j);
                        e.add(costs[j]);
                        l.add(e);
                    }
                    order[i]= (int) getFittest(0,l).get(0);
//                    qdelay[(int) getFittest(0,l).get(0)]+=data2.getSystemDelay()[i][(int) getFittest(0,l).get(0)];

                }else {
                   order[i]=data2.getFogCount()+1;
                }

            }

        for (int j = 0; j <data2.getNodeCount(); j++) {
            if (order[j]==data2.getFogCount()+1){
                cost+=data2.getLocalCost()[j];
            }else {
            cost+=data2.getFogCost()[j][order[j]];}
        }}
    public ArrayList getFittest(int offset, ArrayList a) {
        // Order population by fitness
        a.sort(new Comparator<ArrayList>() {
            @Override
            public int compare(ArrayList o1, ArrayList o2) {
                if ((double)o1.get(1) >(double) o2.get(1)) {
                    return 1;
                } else if((double)o1.get(1) < (double)o2.get(1)){
                    return -1;
                }
                return 0;
            }
        });
        return (ArrayList) a.get(offset);}}

