package info.henrywebster.budget.command

import kotlin.collections.MutableList;

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
    }
}