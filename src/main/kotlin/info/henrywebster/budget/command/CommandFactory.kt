package info.henrywebster.budget.command

import kotlin.collections.MutableList

// import kotlin.collections.HashMap

class CommandFactory {
    companion object {
        fun <T> newAddListCommand(list: MutableList<T>, item: T): Command {
            val cmd = { l: MutableList<T>, i: T -> l.add(i) }
            return ListCmd(list, item, cmd)
        }

        fun <T> newRemoveListCommand(list: MutableList<T>, item: T): Command {
            val cmd = { l: MutableList<T>, i: T -> l.remove(i) }
            return ListCmd(list, item, cmd)
        }

        fun <T> newListCommand(list: MutableList<T>, item: T, cmd: (MutableList<T>, T) -> Boolean): Command {
            return ListCmd(list, item, cmd)
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