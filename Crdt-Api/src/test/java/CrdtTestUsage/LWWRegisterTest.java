package CrdtTestUsage;

import com.netopyr.wurmloch.crdt.GCounter;
import com.netopyr.wurmloch.crdt.LWWRegister;
import com.netopyr.wurmloch.store.CrdtStore;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import com.netopyr.wurmloch.vectorclock.StrictVectorClock;
import org.junit.Test;
public class LWWRegisterTest {
    @Test
    public void test() {

        final LocalCrdtStore cst1 = new LocalCrdtStore();
        final LocalCrdtStore cst2 = new LocalCrdtStore();
        final LocalCrdtStore cst3 = new LocalCrdtStore();

        cst1.connect(cst2);
        cst1.connect(cst3);


        final LWWRegister<String> lww1 = cst1.createLWWRegister("id_23");
        final var lww2 = cst2.findLWWRegister("id_23").get();
        final var lww3 = cst3.findLWWRegister("id_23").get();

        System.out.println("id node1 =" + lww1.getId());
        System.out.println("id node2 =" + lww2.getId());
        System.out.println("id node3 =" + lww3.getId());

        lww1.set("first");

        System.out.println("node1 =" + lww1.get());
        System.out.println("node2 =" + lww2.get());
        System.out.println("node3 =" + lww3.get());

        cst1.disconnect(cst2);
        cst1.disconnect(cst3);
        lww1.set("1st");
        lww2.set("2nd");
        lww3.set("3rd");

        System.out.println("node1 =" + lww1.get());
        System.out.println("node2 =" + lww2.get());
        System.out.println("node3 =" + lww3.get());

        cst1.connect(cst2);
//étrange
        System.out.println("node1 =" + lww1.get());
        System.out.println("node2 =" + lww2.get());
        System.out.println("node3 =" + lww3.get());

    }

}
