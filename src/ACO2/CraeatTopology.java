package ACO2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 * Created by ParnianPC on 29/08/2021.
 */
public class CraeatTopology {
    static int fogCount = 200;
    static int neighborhood[][] = new int[fogCount][fogCount];


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        for (int i = 0; i < fogCount; i++) {
            int n = 0;
            for (int j = 0; j < fogCount; j++) {
              Random random = new Random(System.nanoTime());
                if (i == j) {
                    neighborhood[i][j] = 1;
                } else {
                    if (i > j) {
                        neighborhood[i][j] = neighborhood[j][i];
                    } else {

                        double b = random.nextDouble();
                        if (b>0.5) {
                            neighborhood[i][j] = 1;
                        }else {
                            neighborhood[i][j]=1000;
                        }}
                        if (n>=20){
                            neighborhood[i][j]=1000;
                        }
                    if (neighborhood[i][j]==1){
                        n++;}
                }
                if (i!=j){
               System.out.print(neighborhood[i][j] + " ");}
                else {
                    System.out.print("1000 ");
                }
            }
            System.out.println();
        }
        FileOutputStream fileOut = new FileOutputStream("D://t20.txt");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(neighborhood);
        objectOut.close();
        System.out.println("The Object  was succesfully written to a file");


        System.out.println();
        for (int i = 0; i < fogCount; i++) {

            System.out.print(i + ": ");
            for (int j = 0; j < fogCount; j++) {
                if (neighborhood[i][j] ==1) {

             System.out.print(j+ " ");
            }}
            System.out.println();
        }

        }
    }


