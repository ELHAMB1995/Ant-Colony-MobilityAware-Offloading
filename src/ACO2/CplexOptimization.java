package ACO2;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.cplex.IloCplex;
import java.io.IOException;
/**
 * Created by ParnianPC on 28/05/2021.
 */
public class CplexOptimization {
    double bestValue;
    double X[][];
    double Y[];
    int index[];
    public static void main(String[] args) throws IloException, IOException {
//        CplexOptimization2 cp = new CplexOptimization2();
//        cp.solveModel(new Data42());
    }
    public  void solveModel(DataGenerate data2) throws IloException, IOException {
        IloCplex model = new IloCplex();
//        C=new double[data2.getNodeCount()];
        X=new double[data2.getNodeCount()][data2.getFogCount()];
        Y=new double[data2.getNodeCount()];
        //define variable
        IloIntVar[][] x = new IloIntVar[data2.getNodeCount()][data2.getFogCount()];
        IloIntVar[] y = new IloIntVar[data2.getNodeCount()];
        for (int i = 0; i <data2.getNodeCount() ; i++) {
            y[i]=model.boolVar();
            for (int j = 0; j < data2.fogCount; j++) {
                x[i][j]=model.boolVar();
            }}
        //define objective function
        IloLinearNumExpr objective = model.linearNumExpr();
        for (int i = 0; i <data2.getNodeCount() ; i++) {
            for (int j = 0; j < data2.fogCount; j++) {
                objective.addTerm(data2.fogCost[i][j],x[i][j]);
            }
            objective.addTerm(data2.localCost[i],y[i]);
        }
        model.addMinimize(objective);
//// constraints
        IloLinearNumExpr constantx = model.linearNumExpr();
        IloLinearNumExpr constantc = model.linearNumExpr();
        for (int i = 0; i <data2.getNodeCount(); i++) {
            constantx.clear();
            for (int j = 0; j <data2.fogCount ; j++) {
                constantx.addTerm(1,x[i][j]);
            }
            model.addLe(constantx,1);
            model.addGe(constantx, 0);
        }
        IloNumExpr num1 = null;
        IloNumExpr num2 = null;
        IloLinearNumExpr constanty = model.linearNumExpr();
        for (int i = 0; i <data2.nodeCount; i++) {
            constantx.clear();
            constantc.clear();
            constanty.clear();
            for (int j = 0; j <data2.fogCount ; j++) {
                constantx.addTerm(1,x[i][j]);
            }
            constanty.addTerm(1,y[i]);
            num2 = model.sum(constantx,constanty);
            model.addLe(num2,1);
            model.addGe(num2, 1);}
        for (int i = 0; i <data2.nodeCount; i++) {
            constantx.clear();
            constantc.clear();
            constanty.clear();
            for (int j = 0; j <data2.fogCount ; j++) {
                constantx.addTerm(data2.fogDelay[i][j],x[i][j]);
            }
            constanty.addTerm(data2.localCost[i], y[i]);
            num2=model.sum(constantx,constanty);
            model.addLe(num2,data2.maxDelay[i]);
        }
//neighborhood
        for (int i = 0; i <data2.nodeCount; i++) {
            constantx.clear();
            constantc.clear();
            constanty.clear();
            for (int j = 0; j <data2.fogCount ; j++) {
                constantx.addTerm(data2.neighborhood[data2.getNearFogIndex()[i]][j],x[i][j]);
            }
            model.addLe(constantx, 1);
        }
//deployment
        for (int i = 0; i <data2.nodeCount; i++) {
            constantx.clear();
            constantc.clear();
            constanty.clear();
            for (int j = 0; j <data2.fogCount ; j++) {
                constantx.addTerm(data2.deploymentDelay[i][j],x[i][j]);
            }
            model.addLe(constantx, 0);}
        for (int i = 0; i <data2.nodeCount; i++) {
            constantx.clear();
            constantc.clear();
            constanty.clear();
            for (int j = 0; j <data2.fogCount ; j++) {
                constantx.addTerm(data2.offloadEnergyConsume[i],x[i][j]);
            }
            constanty.addTerm(data2.localEnergyConsume[i], y[i]);
            num2=model.sum(constantx,constanty);
            model.addLe(num2,data2.availableEnergy[i]);
        }
        for (int j = 0; j <data2.fogCount; j++) {
            constantx.clear();
            constanty.clear();
            for (int i = 0; i <data2.nodeCount ; i++) {
                if (j==data2.getNearFogIndex()[i]){
                    constantx.addTerm(data2.minBandwidth[i],y[i]);}
            }
            double bw=0;
            for (int i = 0; i <data2.nodeCount  ; i++) {
                if (j==data2.getNearFogIndex()[i]){
                    bw+=data2.minBandwidth[i];
                }

            }
            num1=model.prod(-1,constantx);
            num2 = model.sum(num1, bw);
            model.addLe(num2,data2.availableBandeidth[j]);
        }
        for (int j = 0; j <data2.fogCount; j++) {
            constantx.clear();
            for (int i = 0; i <data2.nodeCount ; i++) {
                constantx.addTerm(data2.minBandwidth[i],x[i][j]);}
            model.addLe(constantx,data2.availableBandeidth[j]);
        }
        for (int j = 0; j <data2.fogCount; j++) {
            constantx.clear();
            constantc.clear();
            constanty.clear();
            for (int i = 0; i <data2.nodeCount ; i++) {
                if (j==data2.getNearFogIndex()[i]){
                    constantx.addTerm(data2.rulReq[i],y[i]);}
            }
            double rule=0;
            for (int i = 0; i <data2.nodeCount  ; i++) {
                if (j==data2.getNearFogIndex()[i]){
                    rule+=data2.rulReq[i];
                }

            }
            num1=model.prod(-1,constantx);
            num2 = model.sum(num1, rule);
            model.addLe(num2,data2.ruleCap[j]);
        }
        for (int j = 0; j <data2.fogCount; j++) {
            constantx.clear();
            for (int i = 0; i <data2.nodeCount ; i++) {
                constantx.addTerm(data2.rulReq[i],x[i][j]);}
            model.addLe(constantx,data2.ruleCap[j]);
        }

        boolean isSolve = model.solve();
//        System.out.println(model.isQO());
//        System.out.println(model.isQC());
//        System.out.println(model.isDualFeasible());
//        System.out.println(model.isPrimalFeasible());
//        System.out.println(model.isMIP());

        if (isSolve){
            index=new int[data2.getNodeCount()];
//            System.out.println("best val : "+ model.getBestObjValue());
            bestValue=model.getBestObjValue();
//            System.out.println("x: ");
            for (int i = 0; i <data2.nodeCount; i++) {
                for (int j = 0; j <data2.fogCount ; j++) {
//                    System.out.print(Math.abs(model.getValue(x[i][j]))+" ");
                    X[i][j]=model.getValue(x[i][j]);
                    if ( X[i][j]==1){
                        index[i]=j;
                    }
                }
            }

            for (int i = 0; i <data2.nodeCount; i++) {

//                System.out.print(model.getValue(y[i])+" ");
                if ( model.getValue(y[i])==1){
                    index[i]=data2.fogCount+1;
                }
            }

        }else {
            System.out.println("model can not solve");
        }

    }
}
