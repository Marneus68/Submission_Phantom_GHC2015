package gen;

import in.Input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by hugo on 12/03/15.
 */
public class Solution {

    protected int [][] result;
    public Input curInput;
    public Map<Integer, Input.Server> servers;
    private Integer[] groupCapacities;

    public Solution(Input input){

        servers  = deepCopy(input.getServers());
        groupCapacities = new Integer[input.getPoolsCount()];
        Collections.fill(Arrays.asList(groupCapacities), 0);
        curInput = input;
        fillMap();

    }

    private Map<Integer, Input.Server> deepCopy(Map<Integer, Input.Server> original) {
        Map<Integer, Input.Server> copy = new HashMap<>(original.size());
        for(Map.Entry<Integer, Input.Server> entry : original.entrySet()) {
            copy.put(entry.getKey(), (Input.Server) entry.getValue().clone());
        }
        return copy;
    }


    public void fillMap(){
        HashMap<Integer, Input.Server> serversToPlace  = new HashMap<>(servers);
        HashMap<Integer, Input.UnavailableSlot>  unavailableSlots = curInput.getUnavailableSlots();

        //set undispo
        int range = curInput.getRows();
        int col = curInput.getSlots();
        result = new int[range][col];

        for (int i = 0; i < unavailableSlots.size(); i++){
            Input.UnavailableSlot curUS =  unavailableSlots.get(i);
            result[curUS.row][curUS.slot] = -1;
        }


        int slotIndex = 0;
        for(int i = 0; i < curInput.getRows(); i++){
            slotIndex = 0;
            while(slotIndex < curInput.getSlots()){

                if(result[i][slotIndex] == -1) {
                    slotIndex++;
                    continue;
                }
                int slotSize = getSlotSizeAvailable(i, slotIndex);
                Input.Server randServer = getRandomServerOf(slotSize, serversToPlace);
                if(randServer == null){
                    slotIndex++;
                }
                else {
                    placeServer(servers.get(randServer.index), i, slotIndex);
                    slotIndex += randServer.slot;
                }
            }
        }
    }

    public int getSlotSizeAvailable(int x, int y){
        int slotCap = 0;
        do{
            y++;
            slotCap++;
        }while(y < curInput.getSlots() && result[x][y] != -1);

        return slotCap;
    }

    public void placeServer(Input.Server serverIn, int x, int y){
        for(int i = y; i < (serverIn.slot+y); i++){
            result[x][i] = serverIn.index;
        }
        int groupIndex = chooseGroup();

        serverIn.group = groupIndex;
        serverIn.position = new int[]{x, y};

        groupCapacities[groupIndex] += serverIn.capacity;

    }


    public Input.Server getRandomServerOf(int slotSize, HashMap<Integer, Input.Server> servers){
        List<Input.Server> listOfServers = new ArrayList<>();
        for(int i = 0; i < servers.size(); i++){
           Input.Server curServer =  servers.get(i);
           if(curServer.slot <= slotSize){
               listOfServers.add(curServer);
           }
       }
        if(listOfServers.size() == 0)
            return null;

        Random rd = new Random();
        int idx = rd.nextInt(listOfServers.size()-1);
        Input.Server out = listOfServers.get(idx);
        servers.remove(out);
        return out;
    }



    public void writeSolution(String path){

        BufferedWriter buffer;
        try {
            buffer = new BufferedWriter(new FileWriter(path));

            for (int i = 0; i < servers.size(); i++) {

                int[] position = getPosServer(i);
                Input.Server server = servers.get(i);
                if (position == null) {
                    buffer.write("x\n");
                    server.group = -1;
                    server.position = null;
                }else{
                    buffer.write(position[0]+" "+ position[1] + " " + servers.get(i).group + "\n");
                }

            }
            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public int[] getPosServer(int index){
        for (int i = 0; i < curInput.getRows(); i++) {
            for (int j = 0; j < curInput.getSlots(); j++) {
                if(result[i][j] == index){
                    return new int[]{ i , j};
                }
            }
        }
        return null;
    }

    public void display(){
        for (int i = 0; i < curInput.getRows(); i++) {
            for (int j = 0; j < curInput.getSlots(); j++) {
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }
    }

    public int[][] getResult(){
        return result;
    }

    private int chooseGroup(){
        int index = 0;
        int min = groupCapacities[0];
        for (int i = 1; i < groupCapacities.length; i++) {
            if(groupCapacities[i] < min){
                index = i;
                min = groupCapacities[i];
            }
        }
        return index;
    }

}
