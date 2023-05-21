package s_kademlia.operations;

import java.util.logging.Logger;

import s_kademlia.routing.RoutingTable;
import s_kademlia.utils.KademliaUtils;

public class PeriodicPing implements Runnable {
    RoutingTable routingTable;

    public PeriodicPing(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(KademliaUtils.PERIODIC_PING_INTERVAL);
            routingTable.pingAll();
        } catch (InterruptedException e) {
            Logger.getLogger(PeriodicPing.class.getName()).severe(e.getMessage());
        }
    }

}
