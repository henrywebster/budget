package info.henrywebster.budget.command

import java.util.function.BiFunction
import kotlin.collections.MutableCollection;

// TODO
// look into in and out generic type

internal class MutableCollectionCmd<C : MutableCollection<T>, T>(
        private val list: C,
        private val item: T,
        private val function: (C, T) -> Boolean
        //private val function: BiFunction<C, T, Boolean>
) : Command {

    override fun run() {
        function.invoke(list, item)
        //function.apply(list, item)
    }
}
