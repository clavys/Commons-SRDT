package CrdtTestUsage;

import com.netopyr.wurmloch.crdt.GSet;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.atlanmod.commons.LocalStoreBuilder;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
public class gsetTest {

        @Test
        public void gSetTest() {
            LocalStoreBuilder builder1 = new LocalStoreBuilder();
            LocalStoreBuilder builder2 = new LocalStoreBuilder();
            LocalStoreBuilder builder3 = new LocalStoreBuilder();

            builder1.connect(builder2).connect(builder3);

            var set1 = builder1.createGSet("ID_1");
            var set2 = builder2.createGSet("ID_1");
            var set3 = builder3.createGSet("ID_1");
            set1.add("gogo");

            System.out.println(Arrays.toString(set3.getArray()));

            /*
            LocalCrdtStore crdtStore1 = new LocalCrdtStore();
            LocalCrdtStore crdtStore2 = new LocalCrdtStore();
            LocalCrdtStore crdtStore3 =  new LocalCrdtStore();
            crdtStore1.connect(crdtStore2);
            crdtStore1.connect(crdtStore3);
            GSet<String> replica1 = crdtStore1.createGSet("ID_1");
            GSet<String> replica2 = crdtStore2.<String>findGSet("ID_1").get();
            GSet<String> replica3 = crdtStore3.<String>findGSet("ID_1").get();
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
        /*
        assertThat(replica1.contains("banana"), is(true));
        assertThat(replica2.contains("apple"), is(true));
        crdtStore1.disconnect(crdtStore2);
        replica1.add("pineapple");
        replica2.add("strawberry");
        crdtStore1.connect(crdtStore2);
        assertThat(replica1.contains("strawberry"), is(true));*/
            //replica1.remove("apple");
            //assertThat(replica1.remove("apple"), is(false));
        }

}
