package info.henrywebster.budgeti

import info.henrywebster.budget.core.Budget

class TestHelper {
    companion object {
        // static helper function objects
        val addStringFunction = { list: MutableList<String>, item: String -> list.add(item) }
        val removeStringFunction = { list: MutableList<String>, item: String -> list.remove(item) }
        val addBudgetFunction = { list: MutableList<Budget>, item: Budget -> list.add(item) }
        val removeBudgetFunction = { list: MutableList<Budget>, item: Budget -> list.remove(item) }
    }
}
