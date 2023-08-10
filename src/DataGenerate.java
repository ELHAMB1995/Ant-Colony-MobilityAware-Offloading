import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by ParnianPC on 12/05/2021.
 */

public class DataGenerate {
    public DataGenerate(){}
    Random random = new Random();
    int nodeCount ;
    int fogCount = 200;
    int nearFogIndex[];
    int appCount = 10;
    int neighborhood[][] ;
    double SNR[];
    double nodeTransmissionRate[];
   double primaryLoad[];
    double fogTransmissionRate[][];
    double[] taskSize = new double[nodeCount];
    double transmissionDelay[][];
    double deploymentDelay[][] ;
    double systemDelay[][];
    double sendCloudDelay[] ;
    double procCloudDelay[];
    double averageSojournTime[][] ;
    double fogDelay[][] ;
    double cloudDelay[] ;
    double availableBandeidth[];
    double probabiltyMigration[][];
    int hopCount[][] ;
    double tansitionProbability[][][] ;
    double costMigration[][] ;
    double fogCost[][] ;
    double cloudCost[] ;
    double localCost[] ;
    double offloadEnergyConsume[] ;
    double localEnergyConsume[] ;
    double availableEnergy[];
    double minBandwidth[] ;
    double maxDelay[] ;
    double transmisionPower[] ;
    double isNear[][];
    int appResourceReq[];
    int fogAvailableResource[] ;
    double fogCPUFrequency[] ;
    double taskComputionReq[];
    int taskapp[] ;
    double Es[][];
    double perclock[];
    ArrayList<HashSet> fogAppList = new ArrayList();
    double ruleCap[];
    double rulReq[];
    double fogtransmisionPower[];
    double fogSNR[];
    int cycles;
    double fogEnergy[][];
    double v;
    DataGenerate(int c){
        nodeCount=c;
        Random random = new Random();
        nearFogIndex = new int[nodeCount];
        appCount = 10;
         appResourceReq = new int[appCount];
         fogAvailableResource = new int[fogCount];
         fogCPUFrequency = new double[fogCount];
         taskComputionReq = new double[nodeCount];
         taskapp = new int[nodeCount];
         ruleCap=new double[fogCount];
         rulReq=new double[nodeCount];
        neighborhood = new int[fogCount][fogCount];
        SNR = new double[nodeCount];
        nodeTransmissionRate = new double[nodeCount];
        primaryLoad=new double[fogCount];
        fogTransmissionRate = new double[nodeCount][fogCount];
        taskSize = new double[nodeCount];
        transmissionDelay = new double[nodeCount][fogCount];
        deploymentDelay = new double[nodeCount][fogCount];
        systemDelay = new double[nodeCount][fogCount];
        sendCloudDelay = new double[nodeCount];
        procCloudDelay = new double[nodeCount];
        averageSojournTime = new double[nodeCount][fogCount];
        fogDelay = new double[nodeCount][fogCount];
        cloudDelay = new double[nodeCount];
        availableBandeidth=new double[fogCount];
        probabiltyMigration= new double[nodeCount][fogCount];
        hopCount = new int[fogCount][fogCount];
        tansitionProbability = new double[nodeCount][fogCount][fogCount];
        costMigration = new double[nodeCount][fogCount];
        fogCost = new double[nodeCount][fogCount];
        cloudCost = new double[nodeCount];
        localCost = new double[nodeCount];
        offloadEnergyConsume = new double[nodeCount];
        localEnergyConsume = new double[nodeCount];
        availableEnergy= new double[nodeCount];
        minBandwidth = new double[nodeCount];
        maxDelay = new double[nodeCount];
        transmisionPower = new double[nodeCount];
        isNear=new double[nodeCount][fogCount];
        Es=new double[nodeCount][fogCount];
        fogtransmisionPower=new double[fogCount];
        fogSNR=new double[fogCount];
        fogEnergy=new double[nodeCount][fogCount];
        perclock=new double[fogCount];
        double v;

    }
    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public int getFogCount() {
        return fogCount;
    }

    public void setFogCount(int fogCount) {
        this.fogCount = fogCount;
    }

    public int[] getNearFogIndex() {
        return nearFogIndex;
    }

    public void setNearFogIndex(int[] nearFogIndex) {
        this.nearFogIndex = nearFogIndex;
    }

    public int getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }

    public int[][] getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(int[][] neighborhood) {
        this.neighborhood = neighborhood;
    }

    public double[] getSNR() {
        return SNR;
    }

    public void setSNR(double[] SNR) {
        this.SNR = SNR;
    }

    public double[] getNodeTransmissionRate() {
        return nodeTransmissionRate;
    }

    public void setNodeTransmissionRate(double[] nodeTransmissionRate) {
        this.nodeTransmissionRate = nodeTransmissionRate;
    }

    public double[] getPrimaryLoad() {
        return primaryLoad;
    }

    public void setPrimaryLoad(double[] primaryLoad) {
        this.primaryLoad = primaryLoad;
    }

    public double[][] getFogTransmissionRate() {
        return fogTransmissionRate;
    }

    public void setFogTransmissionRate(double[][] fogTransmissionRate) {
        this.fogTransmissionRate = fogTransmissionRate;
    }

    public double[] getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(double[] taskSize) {
        this.taskSize = taskSize;
    }

    public double[][] getTransmissionDelay() {
        return transmissionDelay;
    }

    public void setTransmissionDelay(double[][] transmissionDelay) {
        this.transmissionDelay = transmissionDelay;
    }

    public double[][] getDeploymentDelay() {
        return deploymentDelay;
    }

    public void setDeploymentDelay(double[][] deploymentDelay) {
        this.deploymentDelay = deploymentDelay;
    }

    public double[][] getSystemDelay() {
        return systemDelay;
    }

    public void setSystemDelay(double[][] systemDelay) {
        this.systemDelay = systemDelay;
    }

    public double[] getSendCloudDelay() {
        return sendCloudDelay;
    }

    public void setSendCloudDelay(double[] sendCloudDelay) {
        this.sendCloudDelay = sendCloudDelay;
    }

    public double[] getProcCloudDelay() {
        return procCloudDelay;
    }

    public void setProcCloudDelay(double[] procCloudDelay) {
        this.procCloudDelay = procCloudDelay;
    }

    public double[][] getAverageSojournTime() {
        return averageSojournTime;
    }

    public void setAverageSojournTime(double[][] averageSojournTime) {
        this.averageSojournTime = averageSojournTime;
    }

    public double[][] getFogDelay() {
        return fogDelay;
    }

    public void setFogDelay(double[][] fogDelay) {
        this.fogDelay = fogDelay;
    }

    public double[] getCloudDelay() {
        return cloudDelay;
    }

    public void setCloudDelay(double[] cloudDelay) {
        this.cloudDelay = cloudDelay;
    }

    public double[] getAvailableBandeidth() {
        return availableBandeidth;
    }

    public void setAvailableBandeidth(double[] availableBandeidth) {
        this.availableBandeidth = availableBandeidth;
    }

    public double[][] getProbabiltyMigration() {
        return probabiltyMigration;
    }

    public void setProbabiltyMigration(double[][] probabiltyMigration) {
        this.probabiltyMigration = probabiltyMigration;
    }

    public int[][] getHopCount() {
        return hopCount;
    }

    public void setHopCount(int[][] hopCount) {
        this.hopCount = hopCount;
    }

    public double[][][] getTansitionProbability() {
        return tansitionProbability;
    }

    public void setTansitionProbability(double[][][] tansitionProbability) {
        this.tansitionProbability = tansitionProbability;
    }

    public double[][] getCostMigration() {
        return costMigration;
    }

    public void setCostMigration(double[][] costMigration) {
        this.costMigration = costMigration;
    }

    public double[][] getFogCost() {
        return fogCost;
    }

    public void setFogCost(double[][] fogCost) {
        this.fogCost = fogCost;
    }

    public double[] getCloudCost() {
        return cloudCost;
    }

    public void setCloudCost(double[] cloudCost) {
        this.cloudCost = cloudCost;
    }

    public double[] getLocalCost() {
        return localCost;
    }

    public void setLocalCost(double[] localCost) {
        this.localCost = localCost;
    }

    public double[] getOffloadEnergyConsume() {
        return offloadEnergyConsume;
    }

    public void setOffloadEnergyConsume(double[] offloadEnergyConsume) {
        this.offloadEnergyConsume = offloadEnergyConsume;
    }

    public double[] getLocalEnergyConsume() {
        return localEnergyConsume;
    }

    public void setLocalEnergyConsume(double[] localEnergyConsume) {
        this.localEnergyConsume = localEnergyConsume;
    }

    public double[] getAvailableEnergy() {
        return availableEnergy;
    }

    public void setAvailableEnergy(double[] availableEnergy) {
        this.availableEnergy = availableEnergy;
    }

    public double[] getMinBandwidth() {
        return minBandwidth;
    }

    public void setMinBandwidth(double[] minBandwidth) {
        this.minBandwidth = minBandwidth;
    }

    public double[] getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(double[] maxDelay) {
        this.maxDelay = maxDelay;
    }

    public double[] getTransmisionPower() {
        return transmisionPower;
    }

    public void setTransmisionPower(double[] transmisionPower) {
        this.transmisionPower = transmisionPower;
    }

    public int[] getAppResourceReq() {
        return appResourceReq;
    }

    public void setAppResourceReq(int[] appResourceReq) {
        this.appResourceReq = appResourceReq;
    }

    public int[] getFogAvailableResource() {
        return fogAvailableResource;
    }

    public void setFogAvailableResource(int[] fogAvailableResource) {
        this.fogAvailableResource = fogAvailableResource;
    }

    public double[] getFogCPUFrequency() {
        return fogCPUFrequency;
    }

    public void setFogCPUFrequency(double[] fogCPUFrequency) {
        this.fogCPUFrequency = fogCPUFrequency;
    }

    public double[] getTaskComputionReq() {
        return taskComputionReq;
    }

    public void setTaskComputionReq(double[] taskComputionReq) {
        this.taskComputionReq = taskComputionReq;
    }

    public int[] getTaskapp() {
        return taskapp;
    }

    public void setTaskapp(int[] taskapp) {
        this.taskapp = taskapp;
    }

    public ArrayList<HashSet> getFogAppList() {
        return fogAppList;
    }

    public void setFogAppList(ArrayList<HashSet> fogAppList) {
        this.fogAppList = fogAppList;
    }

    public double[] getRuleCap() {
        return ruleCap;
    }

    public void setRuleCap(double[] ruleCap) {
        this.ruleCap = ruleCap;
    }

    public double[] getRulReq() {
        return rulReq;
    }

    public void setRulReq(double[] rulReq) {
        this.rulReq = rulReq;
    }


    public void generate() throws IOException {

// rule Cap
//        for (int i = 0; i < fogCount; i++) {
//            int low = 100;
//            int high = 500;
//            4p[i] = ((low + random.nextInt(high - low)));
//        }
// rule Req
        for (int i = 0; i < nodeCount; i++) {
            rulReq[i] = 1;
        }
//        neighborhood
        // System.out.println("neighborhood");
//        for (int i = 0; i < fogCount; i++) {
//            for (int j = 0; j < fogCount; j++) {
//                if (i == j) {
//                    neighborhood[i][j] = 1;
//                } else {
//                    if (i > j) {
//                        neighborhood[i][j] = neighborhood[j][i];
//                    } else {
//
//                        boolean b = random.nextBoolean();
//                        neighborhood[i][j] = (b) ? 1 : 1000;
//                    }
//                }
//                 System.out.print(neighborhood[i][j] + " ");
//            }
//             System.out.println();
//        }
        //nearFogIndex
        int node[] = new int[fogCount];
        for (int i = 0; i < nodeCount; i++) {
            random = new Random(System.nanoTime());
            nearFogIndex[i] = random.nextInt(fogCount);
            if (node[nearFogIndex[i]] > ((float) (nodeCount / fogCount) + 5)) {
                System.out.println(i +" "+nearFogIndex[i]+" "+node[nearFogIndex[i]]);
                i--;
                continue;
            }
            node[nearFogIndex[i]]++;
        }

//isnear
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                if (j == nearFogIndex[i]) {
                    isNear[i][j] = 1;}}}
// fog-node transmisionPower
        for (int i = 0; i < nodeCount; i++) {
            double low =8;//0.08 watt
            double high = 10;//0.1 / 20dbm
            transmisionPower[i] = (float) ((low + (random.nextDouble()*(high - low)))/10);
        }
//        fogtransmisionPower
        for (int i = 0; i < fogCount; i++) {
            int low = 8;//8 watt
            int high = 20;//20 watt
            fogtransmisionPower[i] = (float) ((low + (random.nextDouble()*(high - low))));
            fogSNR[i] = Math.log(1 + (fogtransmisionPower[i] / Math.pow(10, -13))) / Math.log(2);
        }
        //SNR
        for (int i = 0; i < nodeCount; i++) {
            //0.08-0.1 watt  , noise power: -100dbm or 10^-13
            SNR[i] = Math.log(1 + (transmisionPower[i] / Math.pow(10, -13))) / Math.log(2);
        }
        // minBandwidth req
        for (int i = 0; i < nodeCount; i++) {

                int low = 500000;//0.5MHz
                int high = 1000000;//1MHz
                minBandwidth[i] = low + (random.nextDouble()*(high-low));
            }
        //node transmission rate
        for (int i = 0; i < nodeCount; i++) {
            nodeTransmissionRate[i] = minBandwidth[i] * SNR[i];
        }

        //        primary load ms
////        // System.out.println("primary load");
//        for (int i = 0; i < fogCount; i++) {
//            int low = 0;//50ms
//            int high = 2;//200ms
//            primaryLoad[i] = low + random.nextInt(5);
////            // System.out.println( primaryLoad[i]+" ");
//
//        }
        // System.out.println();
// task size
        for (int i = 0; i < nodeCount; i++) {
            int low = 10000000;//10MB
            int high = 15000000;//15MB
            taskSize[i] = low + random.nextDouble()*(high-low);

        }

        // taskComputionReq femto
        for (int i = 0; i < nodeCount; i++) {
            int low = 200;//  200  megacycles/MB = CPU cycles for processing 1 bit data at FN
            int high = 300;//300  megacycles/MB = CPU cycles for processing 1 bit data at FN
          int cycles = low + random.nextInt(high - low);
            double size = (float) taskSize[i] / 8000000;// MB
            taskComputionReq[i] = size * cycles; //cycles * size;// MB* megacycle//MB = megacycle
        }
//        // fogCPUFrequency // Mobility-Aware Task Offloading and Migratio Schemes in Fog Computing Networks and Hierarchical Fog-Cloud Computing for IoT Systems: A Computation Offloading Game
//        for (int j = 0; j < fogCount; j++) {
//            int low = 2900;//2.9 GHz 1000MHz
//            int high = 4200;//4.2 GHz 4000MHz
//            fogCPUFrequency[j] = low + random.nextInt(high - low);// fogCPUFrequency is Mega cycles/bit
//
//        }


        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                // System.out.print(systemDelay[i][j]+" ");
            }
            // System.out.println();

        }
        // Bandwidth available
//        // System.out.println("Bandwidth available");
        for (int i = 0; i < fogCount; i++) {
            //availableBandeidth[i] = 20000000;
            availableBandeidth[i] = 20000000;
//            // System.out.println( availableBandeidth[i]+" ");

        }
        //fog to fog
        //fog to fog
        //fogTransmissionRate
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                fogTransmissionRate[i][j] =minBandwidth[i]*fogSNR[j];   //bw * Math.log(1 + (tp / Math.pow(10, -13))) / Math.log(2);
            }
        }
        //        transmission delay
        for (int i = 0; i < nodeCount; i++) {
            int xij = 0;
            transmissionDelay[i][nearFogIndex[i]] = ((float) taskSize[i] / nodeTransmissionRate[i]);//ms
            fogEnergy[i][nearFogIndex[i]]=transmisionPower[i] * transmissionDelay[i][nearFogIndex[i]];
//            transmissionDelay[i][nearFogIndex[i]] += 2.5;
            for (int j = 0; j < fogCount; j++) {
                if (neighborhood[nearFogIndex[i]][j] == 1 && nearFogIndex[i] != j) {
                    double td = ((float) taskSize[i] / fogTransmissionRate[i][j]);
                    fogEnergy[i][j]=fogEnergy[i][nearFogIndex[i]]+(td*fogtransmisionPower[j]);
                    transmissionDelay[i][j] = transmissionDelay[i][nearFogIndex[i]] + td;//ms
                    transmissionDelay[i][j] += random.nextDouble()*2;;//propagation
                }
            }
        }

        // System.out.println("system delay");

        for (int i = 0; i < nodeCount; i++) {
            for (int k = 0; k < fogCount; k++) {
                if (nearFogIndex[i] == k || neighborhood[nearFogIndex[i]][k] == 1) {
                    systemDelay[i][k] = ((float) taskComputionReq[i] / fogCPUFrequency[k]);//ms
                    systemDelay[i][k] = systemDelay[i][k];
                    fogEnergy[i][k]+=perclock[k]*taskComputionReq[i];
                }

            }
        }
        //   fogAvailableResource
        // System.out.println("\nfogAvailableResource");
        for (int i = 0; i < fogCount; i++) {
            int low = 1000;
            int high = 5500;
            fogAvailableResource[i] = low + random.nextInt(high - low);
            // System.out.print(fogAvailableResource[i] + " ");

        }
//            taskapp
        // System.out.println("\ntaskapp");
        for (int i = 0; i < nodeCount; i++) {
            taskapp[i] = random.nextInt(appCount);
            // System.out.print(taskapp[i] + " ");
        }
//            fogAppList
        for (int i = 0; i < fogCount; i++) {
            HashSet appList = new HashSet();
            for (int j = 0; j < appCount; j++) {
                appList.add(random.nextInt(appCount));
            }
            fogAppList.add(appList);
        }
//    deploymentDelay
        // System.out.println("\ndeployment delay");
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                if (fogAppList.get(j).contains(taskapp[i])) {
                    deploymentDelay[i][j] = 0;//app size 5MB , deployment delay is ms
                } else {
                    if (random.nextDouble() < 0.1) {
                        deploymentDelay[i][j] = Double.MAX_VALUE;
                    }
                }


            }
        }
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                // System.out.print(deploymentDelay[i][j]+" ");}
                // System.out.println();

            }
        }

//   fogDelay
        // System.out.println("fog delay");
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                double fd = 0;
                if (nearFogIndex[i] == j || neighborhood[nearFogIndex[i]][j] == 1) {
                    fogDelay[i][j] = systemDelay[i][j] + transmissionDelay[i][j] + primaryLoad[j];
                }
            }

            // System.out.println();
        }

        //sendCloudDelay
        for (int i = 0; i < nodeCount; i++) {
            double cdelay = 5;//300-1500 // upload+sendcloud
            // System.out.print(cdelay + " ");
            sendCloudDelay[i] = cdelay;
        }
//cloud process
        double cloudCPURate = 10000;//6GH
        for (int i = 0; i < nodeCount; i++) {
            procCloudDelay[i] =0;
           // System.out.print(procCloudDelay[i]+" ");

        }
        // System.out.println();
        //     cloudDelay
        for (int i = 0; i < nodeCount; i++) {
            double cd = transmissionDelay[i][nearFogIndex[i]] + sendCloudDelay[i] + procCloudDelay[i];
            cloudDelay[i] = cd;
        }
        // mobility and migration
        // averageSojournTime
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
             averageSojournTime[i][j] = Math.abs(random.nextGaussian() * 10 + 30);// mean:600ms or 2500ms , std-deviation:10
            }}
        for (int i = 0; i < nodeCount; i++) {
            for (int k = 0; k < fogCount; k++) {
                if (neighborhood[nearFogIndex[i]][k] == 1 || k == nearFogIndex[i]) {
                    probabiltyMigration[i][k] = 1 - Math.exp(-fogDelay[i][k] / averageSojournTime[i][nearFogIndex[i]]);
                } else {
                    probabiltyMigration[i][k] = 0;
                }
            }
        }
        // System.out.println("hop count");
//        hopCount
        for (int i = 0; i < fogCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                if (i == j) {
                    hopCount[i][j] = 0;
                } else {
                    hopCount[i][j] = Math.abs(j - i);
                }
            }
        }
//tansitionProbability
        double g;
        for (int i = 0; i < nodeCount; i++) {
            double d=100;
            v= 30+random.nextDouble()*60;
            double a[] = new double[fogCount];
            double s =0;
            for (int j = 0; j < fogCount; j++) {
                double dis= fogDelay[i][j]*v;
                float h= (float) (dis/d);
                for (int k = 0; k < fogCount; k++) {

                    if(hopCount[j][k]<h+random.nextInt(5) && j!=k && fogDelay[i][j]!=0){
                    a[k] = random.nextDouble();}
                    else {
                        a[k]=0;
                    }
                    if (a[k]<0){
                        a[k]=-a[k];
                    }
                    s += a[k];
                }
                g = 0;
                for (int k = 0; k < fogCount; k++) {

                    a[k] /= s;
                    g = g + a[k];
                }

                for (int k = 0; k < fogCount; k++) {
                    if(probabiltyMigration[i][j]==0){
                        tansitionProbability[i][j][k] = 0;
                    }else{
                    tansitionProbability[i][j][k] = a[k];}
                }
            }
        }
//        costMigration
        // System.out.println("cost migration");
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < fogCount; j++) {
                int hopCost = 2;

                float E = 0;
                for (int k = 0; k < fogCount; k++) {
                        E = (float) (E + (hopCount[j][k] * tansitionProbability[i][j][k]));// E(hopcount) mean if iot node migrate Expection of hop count
                }
                if (neighborhood[nearFogIndex[i]][j] == 1 || j == nearFogIndex[i]) {
                    Es[i][j]=E;
                } else {
                    Es[i][j]=0;
                }

               costMigration[i][j] = (Es[i][j] * hopCost) + (10*probabiltyMigration[i][j]);
//                    costMigration[i][j] =(Es[i][j] * hopCost);

            }
        }
        // System.out.println("fogCost");
        for (int i = 0; i < nodeCount; i++) {
            for (int k = 0; k < fogCount; k++) {
                double fc = 0;
                fc =0.5*fogDelay[i][k] + (0.5*costMigration[i][k]);
                fogCost[i][k] = fc;

            }
        }

        for (int i = 0; i < nodeCount; i++) {
            for (int k = 0; k < fogCount; k++) {
                // System.out.print(fogCost[i][k]+" ");
            }
            // System.out.println();
        }
        // System.out.println("cloud cost");
        for (int i = 0; i < nodeCount; i++) {
            double networkCost = 0;
            double cc = cloudDelay[i] + networkCost;
            cloudCost[i] =cc;
            // System.out.print(cloudCost[i]+" ");
        }
        // System.out.println();

        //energyConsume
        // System.out.println("energyConsume");
        for (int i = 0; i < nodeCount; i++) {

            offloadEnergyConsume[i] = transmisionPower[i] * (transmissionDelay[i][nearFogIndex[i]]);
            // System.out.print(offloadEnergyConsume[i] + " ");

        }
        // System.out.println();
        // System.out.println("availableEnergy");
        // availableEnergy
        for (int i = 0; i < nodeCount; i++) {
            availableEnergy[i] = 2000;
            // System.out.print(availableEnergy[i] + " ");
        }
        // System.out.println();

        // maxDelay
        // System.out.println("max delay");
        for (int i = 0; i < nodeCount; i++) {
            maxDelay[i] = 50;// 30s - 20s
            // System.out.print(maxDelay[i]+" ");
        }
        //localCost
        for (int i = 0; i < nodeCount; i++) {
            double localCpu = 10 + random.nextDouble()*30;
            localCost[i] = ((float) taskComputionReq[i] / localCpu);
        }
        //localEnergy
        for (int i = 0; i < nodeCount; i++) {
            double e = taskComputionReq[i] * (0.0005);
            localEnergyConsume[i] = e;
        }
        System.out.println();
//******************************************************************************
    }}






