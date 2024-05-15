package CrdtTestUsage;


import com.netopyr.wurmloch.crdt.MVRegister;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.junit.Test;

public class MVRegisterTest {
    @Test
    public void gSetTest() {

        final LocalCrdtStore cst1 = new LocalCrdtStore();
        final LocalCrdtStore cst2 = new LocalCrdtStore();
        final LocalCrdtStore cst3 = new LocalCrdtStore();

        cst1.connect(cst2);
        cst1.connect(cst3);


        final MVRegister<String> lww1 = cst1.createMVRegister("id_23");
        final var lww2 = cst2.findMVRegister("id_23").get();
        final var lww3 = cst3.findMVRegister("id_23").get();

        System.out.println("id node1 =" + lww1.getId());
        System.out.println("id node2 =" + lww2.getId());
        System.out.println("id node3 =" + lww3.getId());

        lww1.set("first");

        System.out.println("node1 =" + lww1.get());
        System.out.println("node2 =" + lww2.get());
        System.out.println("node3 =" + lww3.get());

        cst1.disconnect(cst2);

        lww1.set("1st");
        lww2.set("2nd");
        lww3.set("3rd");

        System.out.println("node1 =" + lww1.get());
        System.out.println("node2 =" + lww2.get());
        System.out.println("node3 =" + lww3.get());

        cst1.connect(cst2);
//bizard
        System.out.println("node1 =" + lww1.get());
        System.out.println("node2 =" + lww2.get());
        System.out.println("node3 =" + lww3.get());


    }
}
