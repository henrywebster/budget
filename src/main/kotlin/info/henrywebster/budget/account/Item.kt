package info.henrywebster.budget.account

import info.henrywebster.budget.time.BudgetPeriod
import java.io.Serializable

/**
 * Public interface for a budget item,
 * which has a list of credits and debits and
 * acts differently based on what kind of account it is
 */
interface Item : Serializable {

    /**
     * Add a debit to the account
     * @param item item to add to debit list
     */
    fun addDebitItem(item: Item)

    /**
     * Add a credit to the account
     * @param item item to add to credit list
     */
    fun addCreditItem(item: Item)

    /**
     * Clear the item of value
     */
    fun clear()

    /**
     * Calculate based on a period of time
     * @param calcPeriod period of time to calculate to
     */
    fun calculate(calcPeriod: BudgetPeriod): Value

    /**
     * Get the name of the item
     * @return the string name of the item
     */
    fun getName(): String
}