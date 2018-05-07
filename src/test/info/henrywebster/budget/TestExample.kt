package info.henrywebster.budget

import kotlin.test.assertTrue
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestExample {

    @Test
    fun test1() {
        assertTrue(true);
    }
}