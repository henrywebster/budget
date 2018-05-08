package info.henrywebster.budget.command

import kotlin.collections.MutableList;

// TODO
// look into in and out generic type
//internal class AddToListCmd<T>(private val list: MutableList<T>, private val item: T) : Command {
//    override fun run() {
//        list.add(item)
//    }

internal class AddToListCmd(private val list: MutableList<String>, private val item: String) : Command {
    override fun run() {
        list.add(item)
    }
}