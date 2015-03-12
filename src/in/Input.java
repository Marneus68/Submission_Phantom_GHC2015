package in;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created by hugo on 12/03/15.
 */

public class Input {

    public class Server {
        public Server(int index, int slot, int capacity) {
            this.index = index;
            this.slot = slot;
            this.capacity = capacity;
        }

        public int index;
        public int slot;
        public int capacity;
    }

    public class UnavailableSlot {
        public  UnavailableSlot(int row, int slot) {
            this.row = row;
            this.slot = slot;
        }

        public int row;
        public int slot;
    }

    protected int rows = 0;
    protected int slots = 0;
    protected int pools = 0;

    protected HashMap<Integer, Server> servers;
    protected HashMap<Integer, UnavailableSlot> unavailableSlots;

    public Input(String file) {
        servers = new HashMap<Integer, Server>();
        unavailableSlots = new HashMap<Integer, UnavailableSlot>();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            int c = 0;
            String line;
            int una = 0;
            while ((line = br.readLine()) != null) {

                if (0 == c) {
                    rows = Integer.parseInt(line.split(" ")[0]);
                    slots = Integer.parseInt(line.split(" ")[1]);
                    pools = Integer.parseInt(line.split(" ")[3]);
                    una = Integer.parseInt(line.split(" ")[2]);
                } else if (c <= una) {
                    unavailableSlots.put(c-1, new UnavailableSlot(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1])));
                } else {
                    servers.put(c-una-1, new Server(c-una-1, Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1])));
                }
                c++;
            }

        } catch (Exception e) {
            System.err.print("Input Exception: " + e.getMessage());
        }

        System.out.print("# Input file \"" + file + "\"  says... \n# " + rows + " rows of " + slots + " slots where " + pools + " pools of " + servers.size() + " servers are to be allocated with " + unavailableSlots.size() + " unavailable slots.\n");
    }

    public int getRows() {
        return rows;
    }

    public int getSlots() {
        return slots;
    }

    public int getPoolsCount() {
        return pools;
    }

    public HashMap<Integer, Server> getServers() {
        return servers;
    }

    public int getServersCount() {
        return servers.size();
    }

    public HashMap<Integer, UnavailableSlot> getUnavailableSlots() {
        return unavailableSlots;
    }

    public int getUnavailableSlotsCount() {
        return unavailableSlots.size();
    }
}
