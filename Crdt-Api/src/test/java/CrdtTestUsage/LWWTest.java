package CrdtTestUsage;

import com.netopyr.wurmloch.crdt.LWWRegister;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.junit.Test;

import java.util.Arrays;

public class LWWTest {
    @Test
    public void LWWTst() {
        final LocalCrdtStore store1 = new LocalCrdtStore();
        final LocalCrdtStore store2 = new LocalCrdtStore();
        final LocalCrdtStore store3 = new LocalCrdtStore();

        store1.connect(store2);
        store1.connect(store3);
        final LWWRegister<String> rep1 = store1.createLWWRegister("ID_21");
        final LWWRegister<String> rep2 = store2.<String>findLWWRegister(rep1.getId()).get();
        final LWWRegister<String> rep3 = store3.<String>findLWWRegister(rep1.getId()).get();

        rep1.set("Ninja");
        rep2.set("Fuji");
        System.out.println("Node 1 : " + rep1.get());
        System.out.println("Node 2 : " + rep2.get());
        System.out.println("Node 3 : " + rep3.get());
        System.out.println("Node 2 has disconneted");
        store1.disconnect(store2);
        //store2.disconnect(store1); //Cas bizarre
        rep2.set("Fromage");
        rep3.set("Toage");
        System.out.println("Node 1 : " + rep1.get());
        System.out.println("Node 2 : " + rep2.get());
        System.out.println("Node 3 : " + rep3.get());
        store1.connect(store2);
        System.out.println("Node 2 connected");
        System.out.println("Node 1 : " + rep1.get());
        System.out.println("Node 2 : " + rep2.get());
        System.out.println("Node 3 : " + rep3.get());
    }
}