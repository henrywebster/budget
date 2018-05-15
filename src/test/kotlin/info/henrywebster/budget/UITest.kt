package info.henrywebster.budget

import info.henrywebster.budget.core.Budget
import info.henrywebster.budget.ui.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import org.junit.jupiter.api.TestFactory
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.util.function.Predicate
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

    @Test
    fun uiBuilderTest() {

        val budgetMap = HashMap<String, Budget>()


        budgetMap["mybudget"] = Budget("mybudget")

        val builder = UIFormBuilder()

        val testFunc = { arg: String -> budgetMap.contains(arg) }
        val predicate = Predicate<String>(testFunc)

        builder.add("add", predicate)
        builder.add("remove", predicate)

        val form = builder.toUIForm("Test Menu")

        assertEquals(
                "Test Menu",
                form.getHeading()
        )

        assertTrue(
                form.checkInput(0, "mybudget")
        )

        assertFalse(
                form.checkInput(0, "mybudget2")
        )
    }

    // TODO(tmp)
    @Test
    fun uiTmpFormTest() {

        val builder = UIFormBuilder()

        val testFun: (String) -> Boolean = { arg: String ->
            try {
                Integer.parseInt(arg)
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
        val predicate = Predicate<String>(testFun)

        builder.add("balance", predicate)
        builder.add("exit?", predicate)

        val form = builder.toUIForm("Account balance")

        assertTrue(form.checkInput(0, "100"))
        assertFalse(form.checkInput(0, "notANumber"))
    }
}