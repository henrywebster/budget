package info.henrywebster.budget.command

import kotlin.collections.MutableList;

class CommandFactory {
    companion object {
        fun newCommand(list: MutableList<String>, item: String): Command {
            return AddToListCmd(list, item)
        }
    }
}