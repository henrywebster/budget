package info.henrywebster.budget

import info.henrywebster.budget.account.ItemFactory
import info.henrywebster.budget.core.Budget
import info.henrywebster.budget.core.BudgetManager
import info.henrywebster.budget.time.PeriodFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoreTest {

    companion object {
        const val budgetName = "fooBudget"
    }

    // helper function for comparing doubles with an epsilon
    private fun checkDouble(a: Double, b: Double, epsilon: Double = 0.001): Boolean {
        return Math.abs(a - b) < epsilon
    }

    @BeforeEach
    fun beforeTest() {
        BudgetManager.clear()
    }

    @Test
    fun addValidTest() {
        BudgetManager.newBudget(budgetName)
        assertTrue(BudgetManager.contains(budgetName))

        val budget = BudgetManager.find(budgetName)
        assertEquals(budget.name, budgetName)
    }

    @Test
    fun addAndRemoveValidTest() {

        BudgetManager.newBudget(budgetName)
        assertTrue(BudgetManager.contains(budgetName))

        val budget = BudgetManager.find(budgetName)
        assertEquals(budget.name, budgetName)

        BudgetManager.removeBudget(budgetName)
        assertFalse(BudgetManager.contains(budgetName))
    }

    @Test
    fun findInvalidTest() {
        assertFailsWith(RuntimeException::class,
                { BudgetManager.find("barBudget") })
    }

    @Test
    fun clearTest() {
        BudgetManager.newBudget(budgetName)
        assertTrue(BudgetManager.contains(budgetName))

        BudgetManager.clear()
        assertFalse(BudgetManager.contains(budgetName))
    }

    @Test
    fun setActiveValidTest() {

        BudgetManager.newBudget(budgetName)
        val budget = BudgetManager.find(budgetName)
        BudgetManager.setActive(budget)
        assertEquals(budget, BudgetManager.getActive())
    }

    @Test
    fun calculateTest() {

        BudgetManager.newBudget(budgetName)
        val budget = BudgetManager.find(budgetName)

        val debit = budget.getItem("cash")
        val credit = budget.getItem("income")
        val entry = ItemFactory.newLine(1000.0, PeriodFactory.createMonth())

        budget.addItem(entry, credit, debit)
        val valueA = budget.calculate(PeriodFactory.createMonth(), debit)
        assertTrue(checkDouble(1000.00, valueA.getValue()))

        val valueB = budget.calculate(PeriodFactory.createYear(), debit)
        assertTrue(checkDouble(12000.00, valueB.getValue()))

        val valueC = budget.calculate(PeriodFactory.createMonth(), credit)
        assertTrue(checkDouble(-1000.00, valueC.getValue()))

        val valueD = budget.calculate(PeriodFactory.createYear(), credit)
        assertTrue(checkDouble(-12000.00, valueD.getValue()))
    }

    @Test
    fun serializeTest() {

        BudgetManager.newBudget(budgetName)
        val budget = BudgetManager.find(budgetName)

        val debit = budget.getItem("cash")
        val credit = budget.getItem("income")
        val entry = ItemFactory.newLine(1000.0)

        budget.addItem(entry, credit, debit)

        val value = budget.calculate(PeriodFactory.createYear(), debit)

        assertEquals(1000.0, value.getValue())

        // write to a file
        ObjectOutputStream(FileOutputStream(File("test.bgt"))).use {
            it.writeObject(budget)
        }

        // read from file
        ObjectInputStream(FileInputStream(File("test.bgt"))).use {
            val readBudget = it.readObject() as Budget
            val readDebit = readBudget.getItem("cash")
            val readValue = readBudget.calculate(PeriodFactory.createYear(), readDebit)

            assertEquals(value.getValue(), readValue.getValue())
        }
    }
}