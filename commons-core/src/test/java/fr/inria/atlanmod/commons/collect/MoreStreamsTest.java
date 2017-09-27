package fr.inria.atlanmod.commons.collect;

import fr.inria.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case that checks the behavior of {@link MoreStreams}.
 */
@ParametersAreNonnullByDefault
public class MoreStreamsTest extends AbstractTest {

    @Test
    public void indexOf() {
        List<Integer> list0 = Arrays.asList(1, 3, 2, 3, 1, 2, 1);

        assertThat(MoreStreams.indexOf(list0.stream(), 1)).isNotEmpty().contains(0);
        assertThat(MoreStreams.indexOf(list0.stream(), 2)).isNotEmpty().contains(2);
        assertThat(MoreStreams.indexOf(list0.stream(), 3)).isNotEmpty().contains(1);
        assertThat(MoreStreams.indexOf(list0.stream(), 0)).isEmpty();

        List<Integer> list1 = Collections.emptyList();
        assertThat(MoreStreams.indexOf(list1.stream(), 0)).isEmpty();
    }

    @Test
    public void lastIndexOf() {
        List<Integer> list0 = Arrays.asList(1, 3, 2, 3, 1, 2, 1);

        assertThat(MoreStreams.lastIndexOf(list0.stream(), 1)).isNotEmpty().contains(6);
        assertThat(MoreStreams.lastIndexOf(list0.stream(), 2)).isNotEmpty().contains(5);
        assertThat(MoreStreams.lastIndexOf(list0.stream(), 3)).isNotEmpty().contains(3);
        assertThat(MoreStreams.lastIndexOf(list0.stream(), 0)).isEmpty();

        List<Integer> list1 = Collections.emptyList();
        assertThat(MoreStreams.lastIndexOf(list1.stream(), 0)).isEmpty();
    }

    @Test
    public void size() {
        List<Integer> list0 = Arrays.asList(1, 3, 2, 3, 1, 2, 1);
        assertThat(MoreStreams.size(list0.stream())).isNotEmpty().contains(7);

        List<Integer> list1 = Collections.emptyList();
        assertThat(MoreStreams.size(list1.stream())).isEmpty();
    }
}