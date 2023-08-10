import ilog.concert.IloException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

/**
 * Created by ParnianPC on 16/08/2021.
 */
public class Evaluate_methods {

    public static void main(String[] args) throws IOException, IloException, ClassNotFoundException {
        int neighborhood[][];
        int fogCount=200;
        FileInputStream fileIn = new FileInputStream("D://t20.txt");
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Object obj = objectIn.readObject();
        neighborhood = (int[][]) obj;
        double Load[]=new double[fogCount];
        Random random = new Random();
        double primaryLoad[]=new double[fogCount];
        double fogCPUFrequency[]=new double[fogCount];
        double ruleCap[]=new double[fogCount];

        // fogCPUFrequency // Mobility-Aware Task Offloading and Migratio Schemes in Fog Computing Networks and Hierarchical Fog-Cloud Computing for IoT Systems: A Computation Offloading Game
        for (int j = 0; j < fogCount; j++) {
            int low = 2900;//2.9 GHz 1000MHz
            int high = 4200;//4.2 GHz 4000MHz
            fogCPUFrequency[j] = low + random.nextDouble()*(high - low);// fogCPUFrequency is Mega cycles/bit
        }
        for (int i = 0; i < fogCount; i++) {
            int low = 100;
            int high = 500;
            ruleCap[i] = ((low + random.nextInt(high - low)));
        }
        double ep[]=new double[fogCount];
        for (int i = 0; i <fogCount ; i++) {
            Random r = new Random();
            ep[i]=0;
        }

        for (int i = 0; i < fogCount; i++) {
             Load[i] =random.nextDouble();
        }
int g=0;
////
        DataGenerate data2;
        while (g<5){
        double cost0 = 0;
        double cost1 = 0;
        double cost3 = 0;
        double cost4 = 0;
        double d0 = 0;
        double d1 = 0;
        double d3 = 0;
        double d4 = 0;
        double e0 = 0;
        double e1 = 0;
        double e3 = 0;
        double e4 = 0;
        double m0 = 0;
        double m1 = 0;
        double m3 = 0;
        double m4 = 0;
        double g0 = 0;
        double g1 = 0;
        double g3 = 0;
        double g4 = 0;
        double ac1=0;
        double ac2=0;
            int[] nodeCount ={300,600,900,1100,1400};
            for (int i = 0; i < fogCount; i++) {
                int low = 1;//50ms
                int high = 4;//200ms
                primaryLoad[i] = low + Load[i]*(nodeCount[g]/fogCount);
//            // System.out.println( primaryLoad[i]+" ");

            }
            System.out.println("task number:"+nodeCount[g]);
//            double sojourn[][]= new double[nodeCount[g]][fogCount];
//            double[] sjval={10,20,30,40,50};
//            for (int i = 0; i <nodeCount[g] ; i++) {
//                for (int j = 0; j <fogCount ; j++) {
//                    sojourn[i][j]=sjval[g];
//                }
//            }

//            double[] vs={10,40,70,100,130};

        for (int k = 0; k <20; k++) {
           data2 = new DataGenerate(nodeCount[g]);
            data2.setNodeCount(nodeCount[g]);
            data2.setRuleCap(ruleCap);
            data2.setFogCPUFrequency(fogCPUFrequency);
            data2.setPrimaryLoad(primaryLoad);
            data2.setNeighborhood(neighborhood);
//             data2.setAverageSojournTime(sojourn);

            data2.generate();
            long startTime = System.currentTimeMillis();
            //cplex
            System.out.println("cplex");
            CplexOptimization cp = new CplexOptimization();
            cp.solveModel(data2);
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
//            System.out.println("time" + duration);
            cost0 += cp.bestValue;
            double temp = 0;
            double temp2 = 0;
            double temp3 = 0;
            double temp4=0;
            for (int i = 0; i < data2.getNodeCount(); i++) {
                int j = cp.index[i];
                if (j < data2.getFogCount()) {
                    temp += data2.getFogDelay()[i][j]+restime(data2,i,j,data2.probabiltyMigration[i][j])*0.2;
                    temp2 +=  restime(data2,i,j,data2.probabiltyMigration[i][j]);
                    temp3 += data2.costMigration[i][j];

                    temp4 +=data2.getOffloadEnergyConsume()[i];
                }
                if (j == data2.getFogCount() + 1) {
                    temp += data2.getLocalCost()[i];
                    temp4 +=data2.getLocalEnergyConsume()[i];
                }
            }
            d0 += temp;// dealay cplex
            e0 += temp2;// hop count cplex
            m0 += temp3;//cost migration cplex
            g0+=temp4;// node energy cplex
            //Ant clony
             System.out.println("ACO");
             startTime = System.currentTimeMillis();
             AntColonyOptimization aco = new AntColonyOptimization(data2);
             aco.startAntOptimization();
             endTime = System.currentTimeMillis();
//             System.out.println("time" + (endTime - startTime));
             cost1 += aco.bestTourLength;
             temp = 0;
             temp2 = 0;
             temp3 = 0;
             temp4=0;
            for (int i = 0; i < data2.getNodeCount(); i++) {
                int j = aco.bestTourOrder[i];
                if (j < data2.getFogCount()) {
                    temp += data2.getFogDelay()[i][j]+restime(data2,i,j,data2.probabiltyMigration[i][j])*0.2;
                    temp2 += restime(data2,i,j,data2.probabiltyMigration[i][j]);
                    temp3 += data2.costMigration[i][j];
                    temp4+=data2.getOffloadEnergyConsume()[i];

                }
                if (j == data2.getFogCount() + 1) {
                    temp += data2.getLocalCost()[i];
                    temp4 += data2.getLocalEnergyConsume()[i];
                }
            }
            d1 += temp;//Aco delay
            e1 += temp2;//Aco hop count
            m1 += temp3;//Aco cost migration
            g1+=temp4;//Aco thing energy consumption

//softvan
            System.out.println("soft-van");
            SoftVAN softVAN = new SoftVAN(data2);
            softVAN.taskoffloading();
            cost3 += softVAN.cost;
            temp = 0;
            temp2 = 0;
            temp3 = 0;
            temp4=0;
            for (int i = 0; i < data2.getNodeCount(); i++) {
                int j = softVAN.order[i];
                if (j < data2.getFogCount()) {
                    temp += data2.getFogDelay()[i][j]+restime(data2,i,j,data2.probabiltyMigration[i][j])*0.2;
                    temp2 += restime(data2,i,j,data2.probabiltyMigration[i][j]);
                    temp3 += data2.costMigration[i][j];
                    temp4+=data2.offloadEnergyConsume[i];
                }
                if (j == data2.getFogCount() + 1) {
                    temp += data2.getLocalCost()[i];
                    temp4 += data2.getLocalEnergyConsume()[i];
                }
            }
            d3 += temp;//soft-VAN delay
            e3 += temp2;//soft-VAN hop count
            m3 += temp3;//soft-VAN cost migration
            g3+=temp4;//soft-VAN energy consumption
// random
            System.out.println("random");
            RandMethod RM = new RandMethod();
            RM.hoptimal(data2);
            cost4 += RM.cost;
            temp = 0;
            temp2 = 0;
            temp3 = 0;
            temp4=0;
            for (int i = 0; i < data2.getNodeCount(); i++) {
                int j = (int) RM.assign.get(i);
                if (j < data2.getFogCount()) {
                    temp += data2.getFogDelay()[i][j]+restime(data2,i,j,data2.probabiltyMigration[i][j])*0.2;
                    temp2 +=  restime(data2,i,j,data2.probabiltyMigration[i][j]);
                    temp3 += data2.costMigration[i][j];
                    temp4+=data2.getOffloadEnergyConsume()[i];
                }
                if (j == data2.getFogCount() + 1) {
                    temp += data2.getLocalCost()[i];
                    temp4 += data2.getLocalEnergyConsume()[i];
                }
            }
            d4 += temp;//random delay
            e4 += temp2;//random hop count
            m4 += temp3;//random cost migration
            g4+=temp4;//random energy consumption
        }
        int i=20;
        System.out.println("best total cost "+(float)cost0/i+" Aco total cost "+(float)cost1/i+" soft-VAN "+(float)cost3/i+" random "+(float)cost4/i);
        System.out.println("best total delay "+(float)d0/i+" Aco total delay "+(float)d1/i+" soft-VAN total delay "+(float)d3/i+" random  total delay "+(float)d4/i);
        System.out.println("best total hopCount "+(float)e0/i+" Aco total hopCount "+(float)e1/i+" soft-VAN total hopCount "+(float)e3/i+" random  total hopCount "+(float)e4/i);
        System.out.println("best total migration cost "+(float)m0/i+" Aco total migration cost "+(float)m1/i+" soft-VAN total migration cost "+(float)m3/i+" random total migration cost "+(float)m4/i);
        System.out.println("best total thing Energy "+(float)g0/i+" Aco total thing Energy "+(float)g1/i+" soft-VAN total thing Energy "+(float)g3/i+" random total thing Energy "+(float)g4/i);
        System.out.println(" Avg best cost "+(float)cost0/(i*nodeCount[g])+" Avg Aco cost "+(float)cost1/(i*nodeCount[g])+" Avg soft-VAN cost "+(float)cost3/(i*nodeCount[g])+" Avg Random cost "+(float)cost4/(i*nodeCount[g]));
        System.out.println("Avg best delay ms "+(float)(d0*1000)/(i*nodeCount[g])+" Avg Aco delay ms "+(float)(d1*1000)/(i*nodeCount[g])+" Avg soft-VAN delay ms "+(float)(d3*1000)/(i*nodeCount[g])+" Avg random delay ms "+(float)(d4*1000)/(i*nodeCount[g]));
        System.out.println("best Avg hopCount "+(float)e0/(i*nodeCount[g])+" Aco Avg hopCount "+(float)e1/(i*nodeCount[g])+" soft-VAN Avg hopCount "+(float)e3/(i*nodeCount[g])+" Random Avg hopCount "+(float)e4/(i*nodeCount[g]));
        System.out.println("best Avg migration cost "+(float)m0/(i*nodeCount[g])+" Aco Avg migration cost "+(float)m1/(i*nodeCount[g])+" soft-VAN Avg migration cost "+(float)m3/(i*nodeCount[g])+" Random Avg migration cost "+(float)m4/(i*nodeCount[g]));
        System.out.println("best avg thing Energy "+(float)g0/(i*nodeCount[g])+" Aco avg thing Energy "+(float)g1/(i*nodeCount[g])+" soft-VAN avg thing Energy "+(float)g3/(i*nodeCount[g])+" Random avg thing Energy "+(float)g4/(i*nodeCount[g]));
        System.out.println( ((float)ac1/i)*100+" ***********"+((float)ac2/i)*100);
        System.gc();
            g++;

    }
}
public static double restime(DataGenerate data2, int i, int j, double prob){
        double total=0;
    for (int k = 0; k < data2.fogCount ; k++) {
        total+=data2.tansitionProbability[i][j][k];
        if (total>prob){
            return data2.hopCount[j][k];

        }
    }
    double t=0;
        return t ;
}


}