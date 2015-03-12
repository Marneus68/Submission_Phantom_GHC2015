package in;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 12/03/15.
 */

public class Input {

    public class Server {
        public int index;
        public int slot;
        public int capacity;
    }

    public class UnavailableSlot {
        public int row;
        public int slot;
    }

    protected long rows = 0;
    protected long slots = 0;
    protected long pools = 0;

    protected HashMap<Integer, Server> servers;
    protected HashMap<Integer, UnavailableSlot> unavailableSlots;

    public Input(String file) {
        servers = new HashMap<Integer, Server>();
        unavailableSlots = new HashMap<Integer, UnavailableSlot>();
    }

    public long getRows() {
        return rows;
    }

    public long getSlots() {
        return slots;
    }

    public long getPoolsCount() {
        return pools;
    }

    public HashMap<Integer, Server> getServers() {
        return servers;
    }

    public long getServersCount() {
        return servers.size();
    }

    public HashMap<Integer, UnavailableSlot> getUnavailableSlots() {
        return unavailableSlots;
    }

    public long getUnavailableSlotsCount() {
        return unavailableSlots.size();
    }

}
