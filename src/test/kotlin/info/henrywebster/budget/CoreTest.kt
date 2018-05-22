package info.henrywebster.budget

import info.henrywebster.budget.command.Command
import info.henrywebster.budget.command.CommandFactory
import info.henrywebster.budget.core.Budget
import info.henrywebster.budget.core.BudgetManager
import info.henrywebster.budget.ui.UIParser
import info.henrywebster.budget.ui.UIToken
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoreTest {

    private fun tmpHelper(token: UIToken, list: MutableList<Budget>, budget: Budget): Command {
        return when (token) {
            UIToken.ADD -> CommandFactory.newMutableCollectionCommand(list, budget, TestHelper.addBudgetFunction)
            UIToken.REMOVE -> CommandFactory.newMutableCollectionCommand(list, budget, TestHelper.removeBudgetFunction)
            else -> throw Exception()
        }
    }

    @Test
    fun addAndRemoveValidBudgetTest() {

        val parser = UIParser()
        val addBundle = parser.parse("add mybudget")

        // create the global list of budgets
        val budgetList = ArrayList<Budget>()

        // tmp
        val b = Budget(addBundle.args[0])

        // TODO(fix): make new class, also cheating by using the same budget object

        val addCmd = tmpHelper(addBundle.token, budgetList, b)
        addCmd.run()

        assertTrue(budgetList.contains(b))

        val remBundle = parser.parse("remove mybudget")
        val remCmd = tmpHelper(remBundle.token, budgetList, b)
        remCmd.run()

        assertFalse(budgetList.contains(b))
    }

    @Test
    fun budgetManagerAddThenRemoveTest() {

        val budgetName = "myBudget"
        val testBudget = BudgetManager.newBudget(budgetName)

        // check add worked
        assertEquals(testBudget.name, budgetName)
        assertTrue(BudgetManager.contains(budgetName))

        val removeResult = BudgetManager.removeBudget(budgetName)

        // assure removed worked
        assertTrue(removeResult)
        assertFalse(BudgetManager.contains(budgetName))
    }

    @Test
    fun budgetManagerRemoveInvalidTest() {

        val budgetName = "myBudget"
        assertFalse(BudgetManager.contains(budgetName))

        val result = BudgetManager.removeBudget(budgetName)
        assertFalse(result)
    }
/*
    @Test
    fun keywordBindingTest() {

        val keyword = "add"
        val budgetName = "mybudget"

        val function = { name: String -> BudgetManager.addBudget(name) }

        KeywordManager.bind(keyword, function)

        val someFunction = KeywordManager.get(keyword)
        val someCmd = CommandFactory.newMonoCommand(someFunction)

        //////
        val budgetList = ArrayList<Budget>()
        val budget = Budget

    }
*/
}