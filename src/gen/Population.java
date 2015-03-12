package gen;

import gen.manipulations.IManipulation;
import in.Input;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hugo on 12/03/15.
 */
public class Population {

    public int generation;
    public static Date launchDate;

    public static final String pathGeneration = "generated_data";
    public static ArrayList<IManipulation> manipulations;

    public ArrayList<Solution> solutions;

    public int populationSize;

    static String stamp;

    public Population(Input in, int popSize){
        java.util.Date date= new java.util.Date();
        stamp = new Timestamp(date.getTime()).toString();

        populationSize = popSize;

        solutions = new ArrayList<Solution>();

        for (int i = 0; i < popSize; i++) {
            solutions.add(new Solution(in));
        }

    }

    private Population(int popSize){
        populationSize = popSize;
        solutions = new ArrayList<Solution>();
    }

    public Population getNextGeneration(){

        Population newPopulation = new Population(this.populationSize);
        /*
        for(IManipulation manip : manipulations){

            manip.doManipulation(this, newPopulation);

        }
        */
        newPopulation.generation = generation+1;
        return newPopulation;
    }

    public void saveGeneration(){

        java.util.Date date= new java.util.Date();

        try {
            File folderPath = new File("gens/"+stamp+"/gen_"+generation);
            System.out.print(folderPath.mkdirs() + "\n");
            int i = 0;
            for (Solution s : solutions) {
                s.display();
                s.writeSolution(folderPath.getAbsolutePath() + "/" + (i++) + ".out");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print("# Population Exception: " + e.getMessage());
        }
    }
}
