package ACO2;
import ilog.concert.IloException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class AntColonyOptimization {
    private int vioCount=0;
    public String result = "";
    DataGenerate data2;
    private double alpha =1;           //pheromone importance
    private double beta =5;            //distance priority
    private double evaporationRate = 0.7;
    private double Q=30 ;             //pheromone left on trail per ant
    private double randomFactor =0.5; //introducing randomness
    private int maxIterations =400;
    private int nodecount;
    private int fogcount;
    private double[][] tau0;
    private int numberOfAnts =20;
    private ArrayList graph;
    private double pheromones[][];
    private List<HAnt> ants = new ArrayList();
    private Random random = new Random();
    private double probabilities[];
    int[] bestTourOrder;
    double bestTourLength;
    private double lastBest=Double.MAX_VALUE;
    int it;
    private HAnt bAnt;
    Double inf=Double.MAX_VALUE;
    int notChange=0;
    ArrayList<Integer> order = new ArrayList<Integer>();
    public AntColonyOptimization(DataGenerate data2) throws IOException {
        this.data2 = data2;
        this.nodecount = data2.getNodeCount();
        this.fogcount = data2.getFogCount();
        pheromones = new double[nodecount][fogcount+2];
        tau0 = new double[nodecount][fogcount+2];
        probabilities = new double[fogcount+2];
        graph = generateMatrix();
        for (int i = 0; i < numberOfAnts; i++){
            ants.add(new HAnt(nodecount, fogcount, data2));}
        double maxCost[] = new double[nodecount];
        for (int i = 0; i < nodecount; i++) {
            HashMap map= (HashMap) graph.get(i);
            maxCost[i]=0;
            for (Object key:map.keySet()) {
                if (maxCost[i] < (double)map.get(key)){
                    maxCost[i] = (double)map.get(key);}}}
        for (int i = 0; i < nodecount; i++) {
            HashMap map= (HashMap) graph.get(i);
            for (Object key:map.keySet()) {
                tau0[i][(int) key] =0.001;}}
        for (int i = 0; i <nodecount ; i++) {
            order.add(i);}}
    /**
     * Generate initial solution
     */
    public ArrayList generateMatrix() {
        ArrayList <HashMap>  matrix = new ArrayList();
        double[][] fogCost = data2.getFogCost();
        double[] cloudCost = data2.getCloudCost();
        for (int i = 0; i < nodecount; i++) {
            HashMap map = new HashMap<>();
            for (int j = 0; j < fogcount+2; j++) {
                if (j < fogcount) {
                    if (fogCost[i][j]!=0 && (double)data2.getDeploymentDelay()[i][j]!=Double.MAX_VALUE){
                        map.put(j,fogCost[i][j]);
                    }
                } else {
                    if (j==fogcount){
//                      map.put(j,data2.cloudCost[i]));
                    }
                    if (j==fogcount+1){
                        map.put(j,data2.localCost[i]);
                    }
                }
            }
            matrix.add(map);
        }

        return matrix;
    }
    ArrayList bestcost = new ArrayList();
    /**
     * Perform ant optimization
     */
    public void startAntOptimization() throws IOException, IloException {
        solve();
        FileWriter writer = new FileWriter("D://output28.txt");
        for(Object str: bestcost.toArray()) {
            writer.write(String.valueOf(str) + System.lineSeparator());}
        writer.close();
        System.out.println(bAnt.selected);}

    /**
     * Use this method to run the main logic
     */
    boolean endit=false;
    public void solve() {
        clearTrails();
        for (int i = 0; i < maxIterations; i++) {
            endit=false;
            moveAnts();
            changeOrder();
            boolean changegb = false;
            changegb = updateBest(changegb);
            updatePheromones();
            bestcost.add( bestTourLength);
            result = ("\nAttempt #" + i);
            result += ("\nBest tour length: " + (bestTourLength));
            result += ("\nBest tour order:");
            result += ("\n" + Arrays.toString(bestTourOrder));
            result += "\n";
            randomFactor = randomFactor * 0.99;
            if (randomFactor < 0.05) {
                randomFactor = 0.05;
            }
            it++;
            endit=true;
            updatePheromones();}}
    /**
     * At each iteration, move ants
     */
    private void moveAnts() {
        for (HAnt ant : ants) {
            ant.clear();
            for (int i = 0; i < nodecount; i++) {
                boolean asignment = false;
                HashMap map= (HashMap) graph.get(i);
                int size = map.size();
                List keysAsArray = new ArrayList<>(map.keySet());
                Random rand = new Random();
                int t = (int) keysAsArray.get(rand.nextInt(keysAsArray.size()));
                if (random.nextDouble() < randomFactor) {
                    ant.selected[i] = t;}
                else {
                    calculateProbabilities(ant, i);
                    double r = random.nextDouble();
                    double total = 0;
                    for (Object key:map.keySet()) {
                        total += probabilities[(Integer) key];
                        if (total >= r) {

                            ant.selected[i] = (Integer) key;
                            break;}}
                    if(ant.selected[i]==-1){
                        i--;}}}}}
    public void calculateProbabilities(HAnt ant, int nodeIndex) {
        double sum = 0.0;
        HashMap map= (HashMap) graph.get(nodeIndex);
        for (Object key:map.keySet()) {
            sum += Math.pow(pheromones[nodeIndex][((Integer) key)], alpha) * Math.pow((float)Q / (double)map.get(key), beta);
        }
        for (Object key:map.keySet()) {
            double numerator = Math.pow(pheromones[nodeIndex][(Integer) key], alpha) * Math.pow((float)Q/ (double)map.get(key), beta);
            probabilities[(Integer) key] = (float)numerator / sum;}}
    /**
     * Update trails that ants used
     */
    private void updatePheromones() {
        if (endit==true) {
            if(bAnt.totalViolation==0){
             if (bestTourLength<lastBest) {
                 double b = 1;
                 if (lastBest < Double.MAX_VALUE) {
                     b = (lastBest - bestTourLength);
                 }
                 double gcontribution = (float) (b) / bAnt.totalCost;
                 for (int i = 0; i < nodecount; i++) {
                     int j = bAnt.selected[i];
                     pheromones[i][j] += (pheromones[i][j] + gcontribution);
                 }
                 lastBest = bestTourLength;}
            return;
        }else {
                return;
            }}
        for (int i = 0; i < nodecount; i++) {
            for (int j = 0; j < fogcount+2; j++) {
                pheromones[i][j] = pheromones[i][j] * (1-evaporationRate);
            }
        }
        for (HAnt a : ants) {
            double contribution=  (float)(Q / a.totalCost);
            if (a.totalViolation!=0){
                contribution=0;
            }
            for (int i = 0; i < nodecount; i++) {
                int j = a.selected[i];
                pheromones[i][j]=pheromones[i][j]+ contribution;
            }
        }
    }
    /**
     * Update the best solution
     */

    private boolean updateBest(boolean changedb) {
        changedb = false;
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).selected.clone();
            bestTourLength =getTotalCost(graph,ants.get(0));
            bAnt = new HAnt(nodecount,fogcount,data2);
            bAnt.totalViolation=1000000000;
        }

        for (HAnt a : ants) {
            double cost=getTotalCost(graph,a);
            if (cost < bestTourLength) {
                bestTourLength=cost;
//                System.out.println("Best"+bestTourLength+" "+it);
                bestTourOrder = a.selected.clone();
                bAnt.totalCost = a.totalCost;
                bAnt.totalViolation=a.totalViolation;
                bAnt.v1=a.v1;
                bAnt.v2=a.v2;
                bAnt.v3=a.v3;
                bAnt.v4=a.v4;
                bAnt.selected=a.selected.clone();
                changedb=true;
            }}

        notChange++;

        return changedb;
    }

    /**
     * Clear trails after simulation
     */
    private void clearTrails() {
        for (int i = 0; i < nodecount; i++) {
            for (int j = 0; j < fogcount+2; j++)
                pheromones[i][j] = tau0[i][j];
        }
    }

    protected double getTotalCost(ArrayList graph, HAnt ant)
    {
        double alfa=100000;
        double gama=100000;
        double lamda=30000;
        double omega=30000;
        double violation1=0;
        double violation2=0;
        double violation3=0;
        double violation4=0;
        double violation5=0;
        double violation6=0;
        double totalViolation=0;
        double totalCost=0;
        double[] minbw=data2.getMinBandwidth();
        double[] avlbw=data2.getAvailableBandeidth().clone();
        double[] energyConsume=data2.getOffloadEnergyConsume();
        double[] lenergyConsume=data2.getLocalEnergyConsume();
        double[] avlen=data2.getAvailableEnergy();
        double[] rulCap=data2.getRuleCap().clone();
        double[] rulReq=data2.getRulReq();
        double[] maxDelay=data2.getMaxDelay();
        double[][] fDelay=data2.getFogDelay();
        double[] cDelay=data2.getCloudDelay();
        int fogCount =data2.getFogCount();
        for (int i:order) {
            HashMap map= (HashMap) graph.get(i);
            violation5=0;
            violation6=0;
            int j =ant.selected[i];
            boolean bw=false;
            boolean rc=false;
            if (j<fogCount){
                violation1=Math.max(0,(((float)(minbw[i]/avlbw[data2.getNearFogIndex()[i]]))-1)+((float)(minbw[i]/avlbw[j])-1));//bandwidth violation
                violation2=Math.max(0,((float)((energyConsume[i]/avlen[i]))-1));//energy
                violation3=Math.max(0,(((float)((rulReq[i]/rulCap[j]))-1)+((float)((rulReq[i]/rulCap[data2.getNearFogIndex()[i]]))-1)));//rulecap
                violation4=Math.max(0,((float)((fDelay[i][j]/maxDelay[i]))-1));//delay
                if (violation1>0&&lenergyConsume[i]<avlen[i]&&data2.getLocalCost()[i]<maxDelay[i]){
                    violation1=0;
                    ant.selected[i]=fogCount+1;
                    j=fogCount+1;
                    bw=true;
                    rc=true;
//                    totalCost += graph[i][j];
                }
                if (violation3>0){
                    if (lenergyConsume[i]<avlen[i]&&data2.getLocalCost()[i]<maxDelay[i]){
                        j=fogCount+1;
                        ant.selected[i]=fogCount+1;
//                        totalCost += graph[i][j];
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
                violation2=Math.max(0,((float)((data2.localEnergyConsume[i]/avlen[i]))-1));
                violation4=Math.max(0,((float)((data2.getLocalCost()[i]/maxDelay[i]))-1));
                violation3=0;}
            totalViolation+=(alfa*violation1+lamda*violation2+gama*violation3+omega*violation4+10000*violation5+10000*violation6);
            totalCost += (double)map.get(j);}
        ant.v1=violation1;
        ant.v2=violation3;
        ant.v3=violation3;
        ant.v4=violation4;
        totalCost+=totalViolation;
        ant.totalViolation=totalViolation;
        ant.totalCost=totalCost;
        return totalCost;}
    public void changeOrder(){
        Random rnd = new Random();
        for (int i = nodecount - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = (int) order.get(index);
            order.set(index,order.get(i));
            order.set(i,a);}}}