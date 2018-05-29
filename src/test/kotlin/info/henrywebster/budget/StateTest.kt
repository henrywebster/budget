package info.henrywebster.budget

import info.henrywebster.budget.command.CommandFactory
import info.henrywebster.budget.state.MenuContextFactory
import info.henrywebster.budget.ui.UIContextBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.function.Predicate
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StateTest {

    companion object {
        const val defaultPrompt = "$ "
    }


    @Test
    fun testFactory() {

        val builder = UIContextBuilder()

        val testMethod = { _: String -> }

        val testCommand = { str: String ->
            CommandFactory.newMonoCommand(str, testMethod)
        }

        builder.addDirective(
                "test",
                1,
                testCommand,
                Predicate { str: String -> str == "input" },
                "error"
        )

        val ui = builder.build()

        val menuContext = MenuContextFactory.newMenuContext(ui, defaultPrompt)

        assertEquals(defaultPrompt, menuContext.getPrompt())

        menuContext.parse("test input")

        assertFailsWith<IllegalArgumentException>(
                "bad unknown keyword", { menuContext.parse("bad test") })
        assertFailsWith<IllegalArgumentException>(
                "error", { menuContext.parse("test bad") }
        )
        assertFailsWith<IllegalArgumentException>(
                "0 invalid number of arguments", { menuContext.parse("test") }
        )

        val labelContext = MenuContextFactory.newLabeledMenuContext(ui, defaultPrompt, { "test" })

        assertEquals("test$ ", labelContext.getPrompt())
    }
}