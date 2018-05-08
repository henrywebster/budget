package info.henrywebster.budget

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import info.henrywebster.budget.ui.UIParser
import info.henrywebster.budget.ui.UIToken
import info.henrywebster.budget.ui.UIBundle
import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UITest {

    @Test
    fun basicUITest() {

        val parser = UIParser()
        val example = "add mybudget"


        val bundle = parser.parse(example)
        assertEquals(bundle.token, UIToken.ADD)
        assertTrue(bundle.args.contains("mybudget"))

        // parser.parse(ByteArrayInputStream(example.toByteArray(Charsets.UTF_8)))
    }
}