package info.henrywebster.budget.account

import info.henrywebster.budget.time.BudgetPeriod

/**
 * Composite pattern - implementation of a collection of items
 */
internal class ItemCategory(
        private val name: String,
        private val method: (Double, Double) -> Double,
        private val period: BudgetPeriod
) : Item {

    /**
     * Add an item to the list of credits
     * @param item item to add to credits
     */
    override fun addCreditItem(item: Item) {
        assert(!debitList.contains(item))
        assert(!creditList.contains(item))
        creditList.add(item)
    }

    /**
     * Add an item to the list of debits
     * @param item item to add to debits
     */
    override fun addDebitItem(item: Item) {
        assert(!debitList.contains(item))
        assert(!creditList.contains(item))
        debitList.add(item)
    }

    /**
     * Calculate value of sub-items
     * @param calcPeriod period to calculate off
     * @return value result for the calculation
     */
    override fun calculate(calcPeriod: BudgetPeriod): Value {
        val builder = ValueBuilder(method)

        for (item in creditList) {
            builder.addLiability(item.calculate(calcPeriod))
        }
        for (item in debitList) {
            builder.addAsset(item.calculate(calcPeriod))
        }
        return builder.build()
    }

    /**
     * Clear the list of debits and credits
     */
    override fun clear() {
        debitList.clear()
        creditList.clear()
    }

    override fun getName(): String {
        return name
    }

    private val debitList = ArrayList<Item>()
    private val creditList = ArrayList<Item>()
}