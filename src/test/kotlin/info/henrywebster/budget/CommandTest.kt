package info.henrywebster.budget


import info.henrywebster.budget.command.CommandFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandTest {



    @Test
    fun monoAddTest() {

        val list = ArrayList<String>()
        val testString = "exec"

        val addToList = test@{ str: String ->
            list.add(str)
            return@test
        }

        val c = CommandFactory.newMonoCommand(testString, addToList)

        c.run()

        assertFalse(list.isEmpty())
        assertTrue(list.contains(testString))
    }

    @Test
    fun monoAddRemoveTest() {

        val list = ArrayList<String>()
        val testString = "add"

        val addToList = test@{ str: String ->
            list.add(str)
            return@test
        }

        val cmdAdd = CommandFactory.newMonoCommand(testString, addToList)
        cmdAdd.run()

        val removeFromList = test@{ str: String ->
            list.remove(str)
            return@test
        }

        val cmdRemove = CommandFactory.newMonoCommand(testString, removeFromList)
        cmdRemove.run()

        assertTrue(list.isEmpty())
        assertFalse(list.contains("add"))
    }

    @Test
    fun noInputAddTest() {
        val list = ArrayList<String>()
        val testString = "add"

        val addStringToList = test@{
            list.add(testString)
            return@test
        }

        val cmdAdd = CommandFactory.newNoInputCommand(addStringToList)
        cmdAdd.run()

        assertTrue(list.contains(testString))
    }
}