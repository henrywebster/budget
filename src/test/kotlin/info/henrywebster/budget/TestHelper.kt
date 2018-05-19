package info.henrywebster.budget

import info.henrywebster.budget.core.Budget
import java.util.function.BiFunction

class TestHelper {


    companion object {
        // static helper function objects
        // TODO(this smells)
        val addStringFunction = { list: MutableList<String>, item: String -> list.add(item) }
        val removeStringFunction = { list: MutableList<String>, item: String -> list.remove(item) }
        val addBudgetFunction = { list: MutableList<Budget>, item: Budget -> list.add(item) }
        val removeBudgetFunction = { list: MutableList<Budget>, item: Budget -> list.remove(item) }

        //fun <C: MutableCollection<T>, T> addMutableFunction(list: C, item: T): Boolean {
        //    return list.add(item)
        //}
    }
}
