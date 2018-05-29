package info.henrywebster.budget.ui

import info.henrywebster.budget.command.Command
import java.util.function.Predicate

/**
 * Implementation of the UIContext
 */
internal class UIContextImpl(
        private val form: HashMap<String, HashMap<Int, ContextAction<String>>>
) : UIContext {

    /**
     * class to represent an input test and action
     */
    class ContextAction<in T>(
            private val predicate: Predicate<T>,
            private val errorMsg: String,
            private val command: (T) -> Command
    ) {
        /**
         * Test the input and run the action if valid
         * @param input the input to the context action
         */
        fun invoke(input: T) {
            if (!predicate.test(input)) {
                throw IllegalArgumentException("error: $errorMsg $input")
            }
            command.invoke(input).run()
        }
    }

    /**
     * Take the raw input and parse into directive keyword and arguments
     * @param line Raw input from parser
     * @throws IllegalArgumentException if input is not valid
     */
    override fun input(line: String) {

        val size = line.split(" ").size - 1
        val keyword = line.substringBefore(" ")
        val arguments = line.substringAfter(" ")

        val actionMap = form[keyword]
                ?: throw IllegalArgumentException("$keyword unknown keyword")

        val action = actionMap[size]
                ?: throw IllegalArgumentException("$size invalid number of arguments")

        action.invoke(arguments)
    }
}