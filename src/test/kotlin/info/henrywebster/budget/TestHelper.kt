package info.henrywebster.budget

import info.henrywebster.budget.command.MethodFactory
import info.henrywebster.budget.core.Budget

class TestHelper {


    companion object {
        // static helper function objects
        // TODO(this smells)
        //val addStringFunction = { list: MutableList<String>, item: String -> list.add(item) }
        val addStringFunction = { list: MutableCollection<String>, item: String
            ->
            MethodFactory.addToMutableCollectionRecipe(list, item)
        }

        val removeStringFunction = { collection: MutableCollection<String>, item: String
            ->
            MethodFactory.removeFromMutableCollectionRecipe(collection, item)
        }

        val addBudgetFunction = { list: MutableList<Budget>, item: Budget -> list.add(item) }
        val removeBudgetFunction = { list: MutableList<Budget>, item: Budget -> list.remove(item) }

        //fun <C: MutableCollection<T>, T> addMutableFunction(list: C, item: T): Boolean {
        //    return list.add(item)
        //}
    }
}
