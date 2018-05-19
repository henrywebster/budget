package info.henrywebster.budget

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import info.henrywebster.budget.command.CommandFactory
import info.henrywebster.budget.core.Budget
import info.henrywebster.budgeti.TestHelper
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandTest {



    @Test
    fun addListTest() {

        val list = ArrayList<String>()
        val testString = "test"
        val c = CommandFactory.newListCommand<String>(list, testString, TestHelper.addStringFunction)
        c.run()

        assertFalse(list.isEmpty())
        assertTrue(list.contains(testString))
    }

    @Test
    fun removeListTest() {

        val list = ArrayList<String>()

        val cmdAdd = CommandFactory.newListCommand(list, "add", TestHelper.addStringFunction)
        cmdAdd.run()

        val cmdRemove = CommandFactory.newListCommand(list, "add", TestHelper.removeStringFunction)
        cmdRemove.run()

        assertTrue(list.isEmpty())
        assertFalse(list.contains("add"))
    }

    @Test
    fun budgetCommandTest() {

        val budget = Budget("mybudget")

        val testAmt = 945
        val testLiab = 500
        val cmdBuySomething = CommandFactory.newBudgetCommand(
                budget, { b: Budget ->
            b.assets += testAmt;
            b.liabilities += testLiab;
            b.equity = b.assets - b.liabilities; true
        })

        assertEquals(0, budget.assets)
        assertEquals(0, budget.equity)
        assertEquals(0, budget.liabilities)

        cmdBuySomething.run()

        assertEquals(945, budget.assets)
        assertEquals(445, budget.equity)
        assertEquals(500, budget.liabilities)
    }
}