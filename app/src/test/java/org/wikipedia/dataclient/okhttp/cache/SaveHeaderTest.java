package org.wikipedia.dataclient.okhttp.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class) public class SaveHeaderTest {
    @Test public void testIsSaveEnabledTrue() {
        assertThat(SaveHeader.isSaveEnabled("true"), is(true));
    }

    @Test public void testIsSaveEnabledFalse() {
        assertThat(SaveHeader.isSaveEnabled("false"), is(false));
    }

    @Test public void testIsSaveEnabledCaseInsensitive() {
        assertThat(SaveHeader.isSaveEnabled("TRUE"), is(true));
    }
}
