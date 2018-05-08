package info.henrywebster.budget

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import info.henrywebster.budget.command.CommandFactory

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandTest {

    @Test
    fun addListTest() {

        val list = ArrayList<String>()
        val c = CommandFactory.newAddListCommand(list, "add")
        c.run()

        assertFalse(list.isEmpty())
        assertTrue(list.contains("add"))
    }

    @Test
    fun removeListTest() {

        val list = ArrayList<String>()
        val cmdAdd = CommandFactory.newAddListCommand(list, "add")
        cmdAdd.run()

        val cmdRemove = CommandFactory.newRemoveListCommand(list, "add")
        cmdRemove.run()

        assertTrue(list.isEmpty())
        assertFalse(list.contains("add"))
    }
}