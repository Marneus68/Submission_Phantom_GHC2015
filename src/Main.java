import gen.Population;
import in.Input;

/**
 * File created by duane
 * 2015-03-11 | 1:09 AM
 */

public class Main {
    public static void main(String [] args) {
        Input in = new Input("./dc.in");
        Population pop = new Population(in, 1);

        pop.saveGeneration();

        for (int i = 0; i < 1; i++) {
            pop.saveGeneration();
            pop = pop.getNextGeneration();
        }
    }
}
