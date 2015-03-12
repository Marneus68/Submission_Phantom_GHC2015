package gen;

import gen.manipulations.Crossover;
import gen.manipulations.IManipulation;
import in.Input;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hugo on 12/03/15.
 */
public class PopulationLauncher {

    void launch(Input in){

        Population.launchDate = new Date();
        Population.manipulations = new ArrayList<IManipulation>();
        Population.manipulations.add(new Crossover());


        Population current = new Population(in, 20);
        int iteration = 12;
        for (int i = 0; i < iteration; i++) {

            current.saveGeneration();
            current = current.getNextGeneration();

        }

    }
}
