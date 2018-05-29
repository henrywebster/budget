package info.henrywebster.budget

import info.henrywebster.budget.command.CommandFactory
import info.henrywebster.budget.core.BudgetManager
import info.henrywebster.budget.ui.UIContext
import info.henrywebster.budget.ui.UIContextBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.function.Predicate
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UITest {

    companion object {

        // example budget map, would be in the BudgetManager
        private const val budgetName = "fooBudget"
    }

    private fun defaultContext(): UIContext {
        val createBudgetMethod = { name: String ->
            BudgetManager.newBudget(name)
        }

        val createBudgetCommand = { name: String ->
            CommandFactory.newMonoCommand(name, createBudgetMethod)
        }

        val deleteBudgetMethod = { name: String ->
            BudgetManager.removeBudget(name)
        }

        val deleteBudgetCommand = { name: String ->
            CommandFactory.newMonoCommand(name, deleteBudgetMethod)
        }

        val listBudgetMethod = {
            for (budget in BudgetManager.getList()) {
                println("    - $budget")
            }
        }

        val listBudgetCommand = { _: String ->
            CommandFactory.newNoInputCommand(listBudgetMethod)
        }

        val exitMethod = { System.exit(0) }
        val exitCommand = { _: String -> CommandFactory.newNoInputCommand(exitMethod) }

        val contextBuilder = UIContextBuilder()

        contextBuilder.addDirective(
                "create",
                1,
                createBudgetCommand,
                Predicate({ name: String -> !BudgetManager.contains(name) }),
                "already exists"
        )

        contextBuilder.addDirective(
                "delete",
                1,
                deleteBudgetCommand,
                Predicate({ name: String -> BudgetManager.contains(name) }),
                "does not exist"
        )

        contextBuilder.addDirective(
                "list",
                0,
                listBudgetCommand,
                Predicate { true },
                "list budget failed"
        )

        contextBuilder.addDirective(
                "exit",
                0,
                exitCommand,
                Predicate({ true }),
                "exit failed"
        )

        return contextBuilder.build()
    }

    @BeforeEach
    fun beforeTest() {
        BudgetManager.clear()
    }

    @Test
    fun validKeywordTest() {

        val context = defaultContext()

        context.input("create $budgetName")
        assertTrue(BudgetManager.contains(budgetName))

        context.input("create fooBudget2")
        assertTrue(BudgetManager.contains("fooBudget2"))

        context.input("delete $budgetName")
        assertFalse(BudgetManager.contains(budgetName))
        assertTrue(BudgetManager.contains("fooBudget2"))
    }

    @Test
    fun invalidKeywordTest() {

        val context = defaultContext()

        // keyword "created" is not valid, so IllegalArgumentException should be thrown
        assertFailsWith(IllegalArgumentException::class, {
            context.input("created $budgetName")
        })

        // nothing should have been done
        assertFalse(BudgetManager.contains(budgetName))
    }

    @Test
    fun invalidArgTest() {

        val context = defaultContext()

        // try to add a budget with the same name twice
        context.input("create $budgetName")
        assertFailsWith(IllegalArgumentException::class, {
            context.input("create $budgetName")
        })
    }
}