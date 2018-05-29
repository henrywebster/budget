package info.henrywebster.budget.core

import info.henrywebster.budget.account.Entry
import info.henrywebster.budget.account.Item
import info.henrywebster.budget.account.Value
import info.henrywebster.budget.time.BudgetPeriod


/**
 * Private class for the Budget object,
 * representing a tree of items
 */
internal class Ledger(
        private val rootItem: Item,
        private val list: List<Entry>
) {

    /**
     * Build the tree of items based on the entry list
     */
    private fun buildTree() {
        for (entry in list) {
            entry.item.clear()
        }
        for (entry in list) {
            if (null != entry.debitItem) {
                entry.debitItem.addDebitItem(entry.item)
            }
            if (null != entry.creditItem) {
                entry.creditItem.addCreditItem(entry.item)
            }
        }
    }

    /**
     * Calculate the tree value
     * @param period the period to calculate for
     * @param start the root account to calculate
     * @return the value of the root at a certain period
     */
    fun calculate(period: BudgetPeriod, start: Item = rootItem): Value {
        buildTree()
        return start.calculate(period)
    }
}