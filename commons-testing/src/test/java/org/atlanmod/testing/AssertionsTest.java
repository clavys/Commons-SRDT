package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssertionsTest {


    @Test
    void testObjectIsNullWithAssertJ() {

        Object o = new String("toto");
        org.assertj.core.api.Assertions.assertThat((Object) null).isNotNull();
    }

}