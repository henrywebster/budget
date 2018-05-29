package info.henrywebster.budget.account

import info.henrywebster.budget.time.BudgetPeriod

/**
 * Represents a single line account on a statement.
 * Leaf not of account composite pattern
 */
internal class ItemLine(
        private val name: String,
        private val internalPeriod: BudgetPeriod,
        private var value: Value
) : Item {

    /**
     * Overwrite an account to the entry
     * @param item account value to add
     */
    override fun addDebitItem(item: Item) {
        value = item.calculate(internalPeriod)
    }

    /**
     * Overwrite an account to the entry
     * @param item account value to add
     */
    override fun addCreditItem(item: Item) {
        value = item.calculate(internalPeriod)
    }

    override fun clear() {
        // nothing to do
    }

    /**
     * Calculate a value based on the input period
     * @param calcPeriod period to calculate in terms of
     * @return value of the line account in terms of a certain time
     */
    override fun calculate(calcPeriod: BudgetPeriod): Value {
        internalPeriod.visit(calcPeriod)
        return ValueFactory.newValue(value.getValue() * internalPeriod.getFactor())
    }

    /**
     * Name accessor
     * @return the name string of the account
     */
    override fun getName(): String {
        return name
    }
}