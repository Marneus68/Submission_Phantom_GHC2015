package Solution;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * File created by duane
 * 2015-03-11 | 1:14 AM
 */

public abstract class Solution implements ISolution {

    protected static final String default_file_name = "solution.out";

    protected BufferedWriter output = null;

    public Solution() {
        this(default_file_name);
    }

    public Solution(String output_file_name) {
        try {
            output = new BufferedWriter(new FileWriter(output_file_name));
        } catch (Exception e) {
            handleSolutionException(e);
        }
    }

    public void run() {
        if (output != null) {
            try {
                solutionWriter(output);
            } catch (Exception e) {
                handleSolutionException(e);
            }
        }
        try {
            output.close();
        } catch (Exception e) {
            handleSolutionException(e);
        }
    }

    protected void handleSolutionException(Exception e) {
        System.err.print("Solution.Solution Exception: " + e.getMessage());
    }
}
