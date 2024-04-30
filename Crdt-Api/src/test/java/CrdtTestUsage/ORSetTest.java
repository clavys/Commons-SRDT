package CrdtTestUsage;


import org.junit.Test;
import com.netopyr.wurmloch.crdt.ORSet;
import com.netopyr.wurmloch.store.LocalCrdtStore;
import org.junit.Test;


import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;



public class ORSetTest {

    
    
    @Test
    public void test() {

        final LocalCrdtStore cst1 = new LocalCrdtStore();
        final LocalCrdtStore cst2 = new LocalCrdtStore();
        cst1.connect(cst2);

        final ORSet<String> ors1 = cst1.createORSet("ID_1");
        final ORSet<String> ors2 = cst2.<String>findORSet("ID_1").get();

        ors1.add("apple");
        ors2.add("banana");

        System.out.println(Arrays.toString(ors1.toArray()));
        System.out.println(Arrays.toString(ors2.toArray()));

        cst1.disconnect(cst2);

        ors1.remove("banana");
        ors2.add("strawberry");

        System.out.println(Arrays.toString(ors1.toArray()));
        System.out.println(Arrays.toString(ors2.toArray()));

        cst1.connect(cst2);

        System.out.println(Arrays.toString(ors1.toArray()));
        System.out.println(Arrays.toString(ors2.toArray()));

        cst1.disconnect(cst2);

        ors1.add("pear");
        ors2.add("pear");
        ors2.remove("pear");

        System.out.println(Arrays.toString(ors1.toArray()));
        System.out.println(Arrays.toString(ors2.toArray()));

        cst1.connect(cst2);

        System.out.println(Arrays.toString(ors1.toArray()));
        System.out.println(Arrays.toString(ors2.toArray()));
    }
}
