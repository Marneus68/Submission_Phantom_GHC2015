package test;

import gen.Solution;
import in.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by hugo on 13/03/15.
 */
public class SolutionTester {

    public Solution solution;
    public HashMap<Integer,ArrayList<Input.Server>> groups;
    public SolutionTester(Solution sol){
        solution = sol;
        groups = new HashMap<>();

        for (int i = 0; i < solution.curInput.getServersCount(); i++) {
            Input.Server server = solution.servers.get(i);
            if(server.group != -1 && server.position != null){
                if(groups.get(server.group) == null){
                    groups.put(server.group, new ArrayList<>());
                }

                groups.get(server.group).add(server);
            }
        }
    }

    public boolean testServerNotOverflow(){

        solution.getResult();
        for (int i = 0; i < solution.curInput.getServersCount(); i++) {
            Input.Server server = solution.servers.get(i);

            int[] pos = solution.getPosServer(i);

            if(pos != null){
                for (int j = 0, curPos = pos[1]; j < server.slot; j++) {
                    if(solution.getResult()[pos[0]][curPos] != i){
                        System.out.println("Erreur with :"+ i);
                        return false;
                    }
                    curPos++;
                }

            }

        }

        return true;
    }

    public int scoreSolution(){

        Integer[] groupsCapacities = new Integer[solution.curInput.getPoolsCount()];

        for (int i = 0; i < solution.curInput.getPoolsCount(); i++) {
            groupsCapacities[i] = groupScore(i);
        }

        return Collections.min(Arrays.asList(groupsCapacities));
    }

    public int groupScore(int index){

        Integer [] capacities = new Integer[solution.curInput.getRows()];
        for (int i = 0; i < solution.curInput.getRows(); i++) {
            capacities[i] = 0;
            if (groups.get(index) == null){
                System.out.println("Group " + index + " n'a pas de server !");
            }
            for(Input.Server server : groups.get(index)){
                if(server.position[0] != i){
                    capacities[i] += server.capacity;
                }
            }
        }

        return Collections.min(Arrays.asList(capacities));
    }
}
