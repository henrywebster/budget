package info.henrywebster.budget.command


class CommandFactory {
    companion object {

        fun <C : MutableCollection<T>, T> newMutableCollectionCommand(list: C, item: T, method: (C, T) -> Boolean): Command {
            return MutableCollectionCmd(list, item, method)
        }

        fun <T> newMonoCommand(item: T, method: (T) -> Boolean): Command {
            return MonoCommand(item, method)
        }


    }
}