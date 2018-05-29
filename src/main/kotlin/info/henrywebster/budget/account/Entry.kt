package info.henrywebster.budget.account

import info.henrywebster.budget.time.PeriodFactory
import java.io.Serializable

/**
 * Describes the parent relationships to the item
 */
class Entry(
        val item: Item,
        val debitItem: Item?,
        val creditItem: Item?
) : Serializable {
    init {
        assert(debitItem == null || debitItem is ItemCategory)
        assert(creditItem == null || creditItem is ItemCategory)
    }

    /**
     * Get the string representation of an entry
     * @return string representing the entry
     */
    override fun toString(): String {
        return "%-20s %.2f".format(
                item.getName(), item.calculate(PeriodFactory.createYear()).getValue())
    }
}