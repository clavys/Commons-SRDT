package CrdtTestUsage;

import org.atlanmod.commons.LocalStoreBuilder;
import org.junit.Test;

public class GCounterTest {

    @Test
    public void test() {
        LocalStoreBuilder builder1 = new LocalStoreBuilder();
        LocalStoreBuilder builder2 = new LocalStoreBuilder();
        LocalStoreBuilder builder3 = new LocalStoreBuilder();

        builder1.connect(builder2).connect(builder3);

        var set1 = builder1.createGCounter("ID_1");
        var set2 = builder2.createGCounter("ID_1");
        var set3 = builder3.createGCounter("ID_1");

        set1.increment();
        long result = set1.increment().increment().get();
        System.out.println("Node 1 : " + set1.get());


        /*
        final LocalCrdtStore cst1 = new LocalCrdtStore();
        final LocalCrdtStore cst2 = new LocalCrdtStore();
        final  LocalCrdtStore cst3 = new LocalCrdtStore();

        cst1.connect(cst2);
        cst1.connect(cst3);

        final GCounter gc1 = cst1.createGCounter("id_23");
        final GCounter gc2 = cst2.findGCounter("id_23").get();
        final GCounter gc3 = cst3.findGCounter("id_23").get();

        gc1.increment(3L);
        System.out.println("Node 1 : " + gc1.get());
        System.out.println("Node 2 : " + gc2.get());
        System.out.println("Node 3 : " + gc3.get());
        cst2.disconnect(cst1);
        System.out.println("Node 2 is disconnected");
        gc2.increment();
        System.out.println("Node 2's incrementing");
        System.out.println("Node 1 : " + gc1.get());
        System.out.println("Node 2 : " + gc2.get());
        System.out.println("Node 3 : " + gc3.get());
        System.out.println("Node 1's incrementing by 4");
        gc1.increment(4L);
        System.out.println("Node 1 : " + gc1.get());
        System.out.println("Node 2 : " + gc2.get());
        System.out.println("Node 3 : " + gc3.get());
        System.out.println("Node 2 reconnected");
        cst1.connect(cst2);
       // gc3.increment(-1L); //Error case
        System.out.println("Node 1 : " + gc1.get());
        System.out.println("Node 2 : " + gc2.get());
        System.out.println("Node 3 : " + gc3.get());

         */
    }


}
