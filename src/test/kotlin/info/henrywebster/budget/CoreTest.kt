package info.henrywebster.budget

import info.henrywebster.budget.command.Command
import kotlin.collections.ArrayList
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import info.henrywebster.budget.ui.UIParser
import info.henrywebster.budget.ui.UIToken
import info.henrywebster.budget.core.Budget
import info.henrywebster.budget.command.CommandFactory


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoreTest {

    companion object {
        // static helper with common factory
    }

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
}