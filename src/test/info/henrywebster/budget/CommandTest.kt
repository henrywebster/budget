package info.henrywebster.budget

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import info.henrywebster.budget.command.CommandFactory

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandTest {

    @Test
    fun basicFactoryTest() {

        val list = ArrayList<String>()
        val c = CommandFactory.newCommand(list, "add")
        c.run()

        assertFalse(list.isEmpty())
        assertTrue(list.contains("add"))
    }
}