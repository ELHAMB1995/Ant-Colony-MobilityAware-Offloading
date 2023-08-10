package ACO2;

import ilog.concert.IloException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ParnianPC on 09/08/2021.
 */
public class RandMethod {
    double[] avlbw;
    double[] rulCap;
    ArrayList assign= new ArrayList();
    public double getBest() {
        return best;
    }

    public void setBest(double best) {
        this.best = best;
    }
    double cost=0;
    double best;
    public void hoptimal(DataGenerate data) throws IOException, IloException {
        long start=System.currentTimeMillis();
        RandMethod h = new RandMethod();
        DataGenerate data2=data;
        h.avlbw=data2.getAvailableBandeidth().clone();
        h.rulCap=data2.getRuleCap().clone();
        ArrayList nodes = new ArrayList();
        for (int i = 0; i < data2.getNodeCount(); i++) {
            ArrayList fogs = new ArrayList();
            for (int j = 0; j < data2.getFogCount() * 2; j++) {
                ArrayList val=new ArrayList();
                val.add(j);
                if (j<data2.getFogCount()){
                    val.add(data2.getFogCost()[i][j]);}
                fogs.add(val);
            }
            nodes.add(fogs);

        }
        int u=0;
        for (int i = 0; i < data2.getNodeCount(); i++) {
            Random random1 = new Random(System.nanoTime());
            Random random = new Random(random1.nextLong());
            boolean a = false;
            int j=0;
            Random random2 = new Random();
            int z=random2.nextInt(2);
            if (z == 0) {

                j=data2.getFogCount() + 1;
            }
            else {
                j = random.nextInt(data2.getFogCount()-1);
            }
            if (h.calcV(data2, i, j) == 0) {
                u=0;
                assign.add(j);
                a = true;
            }else{
                i--;
                u++;
                if(u>100000){
                    System.out.println("model can not solve by husristic");
                }
            }


        }
        if (assign.size()==data2.getNodeCount()){
            for (int i = 0; i <data2.getNodeCount() ; i++) {
                int v = (int) assign.get(i);
                if (v<data.getFogCount()){
                    cost+=(double)data.getFogCost()[i][v];}
                else {
                    cost+=(double)data.getLocalCost()[i];
                }
            }}}


    public double calcV(DataGenerate data2, int i, int j){

        double violation1=0;
        double violation2=0;
        double violation3=0;
        double violation4=0;
        double totalViolation=0;
        double[] minbw=data2.getMinBandwidth();
        double[] energyConsume=data2.getOffloadEnergyConsume();
        double[] lenergyConsume=data2.getLocalEnergyConsume();
        double[] avlen=data2.getAvailableEnergy();
        double[] rulReq=data2.getRulReq();
        double[] maxDelay=data2.getMaxDelay();
        double[][] fDelay=data2.getFogDelay();
        boolean bw=false;
        boolean rc=false;
        if (j<data2.getFogCount()){
            violation1=Math.max(0,(((float)(minbw[i]/avlbw[data2.getNearFogIndex()[i]]))-1)+((float)(minbw[i]/avlbw[j])-1));
            violation2=Math.max(0,((float)((energyConsume[i]/avlen[i]))-1));
            violation3=Math.max(0,(((float)((rulReq[i]/rulCap[j]))-1)+((float)((rulReq[i]/rulCap[data2.getNearFogIndex()[i]]))-1)));
            violation4=Math.max(0,((float)((fDelay[i][j]/maxDelay[i]))-1));
            if (violation1>0&&lenergyConsume[i]<avlen[i]&&data2.getLocalCost()[i]<maxDelay[i]){
                violation1=0;
                j=data2.getFogCount()+1;
                bw=true;
                rc=true;
            }
            if (violation3>0){
                if (lenergyConsume[i]<avlen[i]&&data2.getLocalCost()[i]<maxDelay[i]){
                    j=data2.getFogCount()+1;
                    violation3=0;
                    rc=true;
                    bw=true;
                }
            }
            if (bw==false){
                if (j==data2.getNearFogIndex()[i]){
                    avlbw[data2.getNearFogIndex()[i]]=Math.max(1,avlbw[data2.getNearFogIndex()[i]]-minbw[i]);}
                else {
                    avlbw[j]=Math.max(1,avlbw[j]-minbw[i]);
                    avlbw[data2.getNearFogIndex()[i]]=Math.max(1,avlbw[data2.getNearFogIndex()[i]]-minbw[i]);
                }}
            if (rc==false){
                if (j==data2.getNearFogIndex()[i]){
                    rulCap[data2.nearFogIndex[i]]=Math.max(1,rulCap[data2.nearFogIndex[i]]-rulReq[i]);}
                else {
                    rulCap[j]=Math.max(1,rulCap[j]-rulReq[i]);
                    rulCap[data2.nearFogIndex[i]]=Math.max(1,rulCap[data2.nearFogIndex[i]]-rulReq[i]);
                }
            }}
        else {
            violation1=0;
            violation2=Math.max(0,((float)((data2.getLocalEnergyConsume()[i]/avlen[i]))-1));
            violation4=Math.max(0,((float)((data2.getLocalCost()[i]/maxDelay[i]))-1));
            violation3=0;
        }
        totalViolation=(violation1+violation2+violation3+violation4);
        return totalViolation;
    }


}
