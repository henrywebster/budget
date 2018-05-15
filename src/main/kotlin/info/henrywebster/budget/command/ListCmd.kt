package info.henrywebster.budget.command

import kotlin.collections.MutableList;

// TODO
// look into in and out generic type

internal class ListCmd<T>(
    private val list: MutableList<T>,
    private val item: T,
    private val cmd: (MutableList<T>, T) -> Boolean
) : Command {

    override fun run() {
        cmd.invoke(list, item)
    }
}
