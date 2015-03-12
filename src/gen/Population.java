package gen;

import gen.manipulations.IManipulation;
import in.Input;

import java.io.File;
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

    public Population(Input in, int popSize){
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
        for(IManipulation manip : manipulations){

            manip.doManipulation(this, newPopulation);

        }
        return newPopulation;
    }

    public void saveGeneration(){

        File folderPath = new File(pathGeneration+ "/" + launchDate.toString() + "/gen_"+ generation);

        folderPath.mkdirs();

        for(Solution s : solutions){
            s.writeSolution(folderPath.getAbsolutePath());
        }

    }



}
