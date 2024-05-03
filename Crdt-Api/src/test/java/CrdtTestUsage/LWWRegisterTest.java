package CrdtTestUsage;

import com.netopyr.wurmloch.crdt.GCounter;
import com.netopyr.wurmloch.crdt.LWWRegister;
import com.netopyr.wurmloch.store.CrdtStore;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import com.netopyr.wurmloch.vectorclock.StrictVectorClock;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LWWRegisterTest {
    @Test
    public void test() {

        // create two LocalCrdtStores and connect them
        final LocalCrdtStore crdtStore1 = new LocalCrdtStore("N_1");
        final LocalCrdtStore crdtStore2 = new LocalCrdtStore("N_2");
        final LocalCrdtStore crdtStore3 = new LocalCrdtStore("N_3");
        crdtStore1.connect(crdtStore2);
        crdtStore1.connect(crdtStore3);

        // create an LWW-Register and find the according replica in the second store
        final LWWRegister<String> replica1 = crdtStore1.createLWWRegister("ID_1");
        final LWWRegister<String> replica2 = crdtStore2.<String>findLWWRegister("ID_1").get();
        final LWWRegister<String> replica3 = crdtStore3.<String>findLWWRegister("ID_1").get();

        // set values in both replicas
        replica1.set("apple");
        replica2.set("pen");
        replica3.set("banana");

        // the stores are connected, thus the last write wins
        System.out.println("node1 =" + replica1.get());
        System.out.println("node1 =" + replica2.get());
        System.out.println("node1 =" + replica3.get());
        assertThat(replica1.get(), is("banana"));
        assertThat(replica2.get(), is("banana"));


        // disconnect the stores simulating a network issue, offline mode etc.
        crdtStore1.disconnect(crdtStore2);
        crdtStore1.disconnect(crdtStore3);

        // add one entry to each replica
        replica1.set("strawberry");
        replica2.set("pinaple");
        replica3.set("pear");

        // the stores are not connected, thus the changes have only local effects
        System.out.println("node1 =" + replica1.get());
        System.out.println("node1 =" + replica2.get());
        System.out.println("node1 =" + replica3.get());
        assertThat(replica1.get(), is("strawberry"));
        assertThat(replica3.get(), is("pear"));

        // reconnect the stores
        crdtStore1.connect(crdtStore2);
        crdtStore1.connect(crdtStore3);

        // the LWW-Register is synchronized automatically.
        // as the update happened concurrently, the update from the node with the larger ID wins
        System.out.println("node1 =" + replica1.get());
        System.out.println("node1 =" + replica2.get());
        System.out.println("node1 =" + replica3.get());
        assertThat(replica1.get(), is("pear"));
        assertThat(replica2.get(), is("pear"));
        assertThat(replica3.get(), is("pear"));





    }

}
