package fr.inria.atlanmod.commons.io;

import fr.inria.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test-case about {@link MoreFiles}.
 */
@ParametersAreNonnullByDefault
public class MoreFilesTest extends AbstractTest {

    @Test
    public void testFileExtension() {
        assertThat(MoreFiles.fileExtension(".log")).isEqualTo("log");
        assertThat(MoreFiles.fileExtension("atlanmod.log")).isEqualTo("log");
        assertThat(MoreFiles.fileExtension("atlanmod..log")).isEqualTo("log");
        assertThat(MoreFiles.fileExtension(".atlanmod.log")).isEqualTo("log");
        assertThat(MoreFiles.fileExtension("dir/atlanmod.log")).isEqualTo("log");
        assertThat(MoreFiles.fileExtension("atlanmod.tar.gz")).isEqualTo("gz");
        assertThat(MoreFiles.fileExtension("/")).isEmpty();
        assertThat(MoreFiles.fileExtension(".")).isEmpty();
        assertThat(MoreFiles.fileExtension("..")).isEmpty();
        assertThat(MoreFiles.fileExtension("...")).isEmpty();
        assertThat(MoreFiles.fileExtension("atlanmod")).isEmpty();
        assertThat(MoreFiles.fileExtension("atlanmod.")).isEmpty();
        assertThat(MoreFiles.fileExtension(".atlanmod.")).isEmpty();
        assertThat(MoreFiles.fileExtension("parent.dir/atlanmod")).isEmpty();
        assertThat(MoreFiles.fileExtension("parent/.dir/atlanmod")).isEmpty();
    }

    @Test
    public void testNameWithoutExtension() {
        assertThat(MoreFiles.nameWithoutExtension(".log")).isEmpty();
        assertThat(MoreFiles.nameWithoutExtension("atlanmod.log")).isEqualTo("atlanmod");
        assertThat(MoreFiles.nameWithoutExtension("atlanmod..log")).isEqualTo("atlanmod.");
        assertThat(MoreFiles.nameWithoutExtension(".atlanmod.log")).isEqualTo(".atlanmod");
        assertThat(MoreFiles.nameWithoutExtension("dir/atlanmod.log")).isEqualTo("atlanmod");
        assertThat(MoreFiles.nameWithoutExtension("atlanmod.tar.gz")).isEqualTo("atlanmod.tar");
        assertThat(MoreFiles.nameWithoutExtension("/")).isEmpty();
        assertThat(MoreFiles.nameWithoutExtension(".")).isEmpty();
        assertThat(MoreFiles.nameWithoutExtension("..")).isEqualTo(".");
        assertThat(MoreFiles.nameWithoutExtension("...")).isEqualTo("..");
        assertThat(MoreFiles.nameWithoutExtension("atlanmod")).isEqualTo("atlanmod");
        assertThat(MoreFiles.nameWithoutExtension("atlanmod.")).isEqualTo("atlanmod");
        assertThat(MoreFiles.nameWithoutExtension(".atlanmod.")).isEqualTo(".atlanmod");
        assertThat(MoreFiles.nameWithoutExtension("parent.dir/atlanmod")).isEqualTo("atlanmod");
        assertThat(MoreFiles.nameWithoutExtension("parent/.dir/atlanmod")).isEqualTo("atlanmod");
    }
}