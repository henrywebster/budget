package info.henrywebster.budget.command

import java.util.function.Predicate
import kotlin.collections.ArrayList

/*
    1. test predicate
    2. perform some coordination action
 */

class CommandPair(
        private val predicate: Predicate<String>,
        private val method: (String) -> Boolean
) {

    fun act(input: String): Boolean {
        if (!predicate.test(input))
            return false

        return method.invoke(input)
    }
}


class CommandBuilder(
        private val args: ArrayList<CommandPair>,
        private var inputCount: Int
) {

    fun addArgument(predicate: Predicate<String>, action: (String) -> Boolean) {
        args.add(CommandPair(predicate, action))
    }

    fun inputArgument(input: String): Boolean {
        return args[inputCount++].act(input)
    }


    fun build(): Command

    for ()

}
}