package CrdtTestUsage;

import com.netopyr.wurmloch.crdt.RGA;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.junit.Test;

import java.util.Arrays;

public class RGATest {
    @Test
    public void gSetTest() {

        LocalCrdtStore crdtStore1 = new LocalCrdtStore();
        LocalCrdtStore crdtStore2 = new LocalCrdtStore();
        LocalCrdtStore crdtStore3 = new LocalCrdtStore();
        crdtStore1.connect(crdtStore2);
        crdtStore1.connect(crdtStore3);
        RGA<String> replica1 = crdtStore1.createRGA("ID_1");
        RGA<String> replica2 = crdtStore2.<String>findRGA("ID_1").get();
        RGA<String> replica3 = crdtStore3.<String>findRGA("ID_1").get();
        replica1.add("apple");
        replica2.add("banana");
        replica3.add("peach");
        System.out.println(Arrays.toString(replica1.toArray()));
        System.out.println(Arrays.toString(replica2.toArray()));
        System.out.println(Arrays.toString(replica3.toArray()));
        crdtStore3.disconnect(crdtStore1);
        System.out.println("replica 3 is disconneted");
        System.out.println("Pinapple is added to replica3 and orange is added to replica2");
        replica3.add("Pinapple");
        replica2.add("Orange");
        System.out.println(Arrays.toString(replica1.toArray()));
        System.out.println(Arrays.toString(replica2.toArray()));
        System.out.println(Arrays.toString(replica3.toArray()));
        System.out.println("replica 3 has reconnected");
        crdtStore1.connect(crdtStore3);
        System.out.println(Arrays.toString(replica1.toArray()));
        System.out.println(Arrays.toString(replica2.toArray()));
        System.out.println(Arrays.toString(replica3.toArray()));
        replica3.remove(2);
        System.out.println("2nd element removed");
        System.out.println(Arrays.toString(replica1.toArray()));
        System.out.println(Arrays.toString(replica2.toArray()));
        System.out.println(Arrays.toString(replica3.toArray()));
    }
}
