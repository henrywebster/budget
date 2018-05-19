package info.henrywebster.budget.command

import info.henrywebster.budget.core.Budget
import kotlin.collections.MutableList

// import kotlin.collections.HashMap

class CommandFactory {
    companion object {

        fun <T> newListCommand(list: MutableList<T>, item: T, cmd: (MutableList<T>, T) -> Boolean): Command {
            return ListCmd(list, item, cmd)
        }

        fun newBudgetCommand(budget: Budget, cmd: (Budget) -> Boolean): Command {
            return BudgetCommand(budget, cmd)
        }

//        private val listCommandMap = HashMap<String, (MutableList<T>, T) -> Boolean>()
//
//        // add type of commands and associated HashMap
//        //fun <T> addCommandType
//
//        fun <T> addListCommand(keyword: String, cmd: (MutableList<T>, T) -> Boolean) {
//            commandMap.put(keyword, )
//        }
    }
}