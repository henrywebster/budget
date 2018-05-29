package info.henrywebster.budget.core

import info.henrywebster.budget.account.*
import info.henrywebster.budget.time.BudgetPeriod
import info.henrywebster.budget.time.PeriodFactory
import java.io.Serializable

/**
 * The object for a specific budget, which holds budget items and accounts
 */
class Budget(
        val name: String,
        private val root: Item,
        private val list: ArrayList<Entry>
) : Serializable {

    /**
     * Add an account to the budget
     * @param item the account to add
     * @param creditItem the account to credit the input account
     * @param debitItem the account to debit the input account
     */
    fun addItem(item: Item, creditItem: Item, debitItem: Item) {
        list.add(Entry(item, debitItem, creditItem))
    }

    /**
     * Print a nice view of the budget
     */
    fun printView() {
        for (entry in list) {
            val ledger = Ledger(entry.item, list)
            println("%-20s %.2f".format(
                    entry.item.getName(),
                    ledger.calculate(PeriodFactory.createYear()).getValue()))
        }
    }

    /**
     * Query if an account of a name is in the budget
     * @param inName the name of the account to check
     * @return true if in the budget, false if not
     */
    fun contains(inName: String): Boolean {
        for (entry in list) {
            if (inName == entry.item.getName()) {
                return true
            }
        }
        return false
    }

    /**
     * Get a specific account from the budget
     * @param inName the name of the account to get
     * @return the specific account
     * @throws RuntimeException if the account is not in the budget
     */
    fun getItem(inName: String): Item {

        for (entry in list) {
            if (inName == entry.item.getName()) {
                return entry.item
            }
        }

        assert(false)
        throw RuntimeException("could not find account $inName")
    }


    /**
     * Calculate the value of a part of the budget
     * @param period the period to calculate for
     * @param start the root account to calculate
     * @return the value that represents the subtree in a certain period
     */
    fun calculate(period: BudgetPeriod, start: Item): Value {
        val ledger = Ledger(root, list)
        return ledger.calculate(period, start)
    }

    /**
     * Default to string method
     * @return a string of the year period
     */
    override fun toString(): String {
        return toString(PeriodFactory.createYear())
    }

    /**
     * Print budget based on a specific time period
     * @param period the period to calculate for
     * @return string representing the budget at a certain period
     */
    private fun toString(period: BudgetPeriod): String {
        val assets = getItem("cash")
        val liabilities = getItem("expense")

        val ledger = Ledger(root, list)
        val cashVal = ledger.calculate(period, assets).getValue()
        val expenseVal = ledger.calculate(period, liabilities).getValue()

        return ("[%s]\n   " +
                "cash:      %.2f\n   " +
                "expenses:  %.2f\n   " +
                "total:     %.2f\n").format(
                name,
                cashVal,
                expenseVal,
                cashVal + expenseVal)
    }

    /**
     * Print a specific budget account starting at root for a specific period
     * @param period the period to calculate for
     * @param rootName the name of the root account
     * @return the string representation
     */
    fun toString(period: BudgetPeriod, rootName: String): String {
        val item = getItem(rootName)

        val ledger = Ledger(item, list)
        val value = ledger.calculate(period, item).getValue()

        return "%.2f".format(value)
    }
}
