package CrdtTestUsage;

import com.netopyr.wurmloch.crdt.PNCounter;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.junit.Test;

public class PNCounterTest {
    @Test
    public void testPNCounter() {
        final LocalCrdtStore store1 = new LocalCrdtStore();
        final LocalCrdtStore store2 = new LocalCrdtStore();
        final LocalCrdtStore store3 = new LocalCrdtStore();

        store1.connect(store2);
        store1.connect(store3);
        final PNCounter pnCounter = store1.createPNCounter();
        final PNCounter pnCounter2 = store2.findPNCounter(pnCounter.getId()).get();
        final PNCounter pnCounter3 = store3.findPNCounter(pnCounter.getId()).get();
        pnCounter.increment(2);
        System.out.println("Node 1 : " +pnCounter.get());
        System.out.println("Node 2 : "+pnCounter2.get());
        System.out.println("Node 3 : "+pnCounter3.get());
        System.out.println("Node 3 is decrementing by one");
        pnCounter3.decrement();
        System.out.println("Node 1 : " +pnCounter.get());
        System.out.println("Node 2 : "+pnCounter2.get());
        System.out.println("Node 3 : "+pnCounter3.get());
        System.out.println("Node 2 has disctonnected, Node 1 incremented 3 units and Node 2 one");
        store2.disconnect(store1);
        pnCounter2.increment();
        pnCounter.increment(3L);
        System.out.println("Node 1 : " +pnCounter.get());
        System.out.println("Node 2 : "+pnCounter2.get());
        System.out.println("Node 3 : "+pnCounter3.get());
        System.out.println("Node 2 connected");
        store1.connect(store2);
        System.out.println("Node 1 : " +pnCounter.get());
        System.out.println("Node 2 : "+pnCounter2.get());
        System.out.println("Node 3 : "+pnCounter3.get());

    }
}
