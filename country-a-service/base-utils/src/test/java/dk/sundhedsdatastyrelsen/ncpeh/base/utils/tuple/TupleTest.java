package dk.sundhedsdatastyrelsen.ncpeh.base.utils.tuple;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TupleTest {
    @Test
    public void equalityTest() {
        var pair1 = new Pair<>("String", 1);
        var pair2 = new Pair<>("String", 1);
        var pair3 = new Pair<>("DifferentString", 1);
        var pair4 = new Pair<>("String", 2);
        var pair5 = new Pair<>("DifferentString", 2);
        var pair6 = new Pair<>(1, "String");

        assertThat(pair1, is(equalTo(pair1)));
        assertThat(pair1, is(equalTo(pair2)));
        assertThat(pair1, is(not(equalTo(pair3))));
        assertThat(pair1, is(not(equalTo(pair4))));
        assertThat(pair1, is(not(equalTo(pair5))));
        assertThat(pair1, is(not(equalTo(pair6))));

        assertThat(pair1.hashCode(), is(equalTo(pair2.hashCode())));
        assertThat(pair1.hashCode(), is(not(equalTo(pair3.hashCode()))));
        assertThat(pair1.hashCode(), is(not(equalTo(pair4.hashCode()))));
        assertThat(pair1.hashCode(), is(not(equalTo(pair5.hashCode()))));
        assertThat(pair1.hashCode(), is(not(equalTo(pair6.hashCode()))));

        assertThat(pair1.getFirst(), is(equalTo(pair2.getFirst())));
        assertThat(pair1.getSecond(), is(equalTo(pair2.getSecond())));
    }


}
