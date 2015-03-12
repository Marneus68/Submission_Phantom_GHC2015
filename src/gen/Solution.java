package gen;

import in.Input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by hugo on 12/03/15.
 */
public class Solution {

    protected int [][] result;
    protected Input curInput;
    protected long curArrayIndex;

    public Solution(Input input){

        fillMap(input);

    }


    public void fillMap(Input inputIn){
        curInput = inputIn;
        HashMap<Integer, Input.Server> servers  = (HashMap<Integer, Input.Server>) inputIn.getServers().clone();
        HashMap<Integer, Input.UnavailableSlot>  unavailableSlots = inputIn.getUnavailableSlots();

        //set undispo
        int range = 100;
        int col = 100;
        result = new int[range][col];

        for (int i = 0; i < unavailableSlots.size(); i++){
        //for(Input.UnavailableSlot curUS : unavailableSlots){
            Input.UnavailableSlot curUS =  unavailableSlots.get(i);
            result[curUS.row][curUS.slot] = -1;
        }


        int slotIndex = 0;
        for(int i = 0; i < inputIn.getRows(); i++){
            slotIndex = 0;
            while(slotIndex < inputIn.getSlots()){

                if(result[i][slotIndex] == -1) {
                    slotIndex++;
                    continue;
                }
                int slotSize = getSlotSizeAvailable(i, slotIndex);
                Input.Server randServer = getRandomServerOf(slotSize, servers);
                if(randServer == null){
                 slotIndex++;
                }
                else {
                    placeServer(randServer, i, slotIndex);
                    slotIndex += getSlotSizeAvailable(i, slotIndex);
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
        for(int i = y; i < serverIn.capacity; i++){
            result[x][i] = serverIn.index;
        }
    }


    public Input.Server getRandomServerOf(int slotSize, HashMap<Integer, Input.Server> servers){
        List<Input.Server> listOfServers = new ArrayList<Input.Server>();
        int serverListIdx = 0;
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

            int currentGroup = 0;
            for (int i = 0; i < curInput.getServers().size(); i++) {

                int[] position = getPosServer(i);
                if (position == null) {
                    buffer.write("x\n");
                }else{
                    buffer.write(position[0]+" "+ position[1] + " " + currentGroup + "\n"); //curInput.getServers().get(i).group
                }
                currentGroup = (int) Math.floor(i * ((float)curInput.getPoolsCount() / curInput.getServersCount()));
            }
            buffer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private int[] getPosServer(int index){
        for (int i = 0; i < curInput.getRows(); i++) {
            for (int j = 0; j < curInput.getSlots(); j++) {
                if(result[i][j] == index){
                    return new int[]{ i , j};
                }
            }
        }
        return null;
    }

}
