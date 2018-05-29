package info.henrywebster.budget.ui

import info.henrywebster.budget.command.Command
import java.util.function.Predicate

/**
 * Builder for the UIContext interface
 */
class UIContextBuilder {

    /**
     * Add an directive to the context parser
     * @param keyword word that corresponds to the new directive
     * @param paramCount number of parameters the directive takes
     * @param command action to do when the directive is executed
     * @param predicate test on the input to make sure arguments are valid
     * @param errorMsg message for the user when input is incorrect
     */
    fun addDirective(
            keyword: String,
            paramCount: Int,
            command: (String) -> Command,
            predicate: Predicate<String>,
            errorMsg: String) {

        val keywordMap = map[keyword] ?: HashMap()
        map[keyword] = keywordMap
        keywordMap[paramCount] = UIContextImpl.ContextAction(predicate, errorMsg, command)
    }

    /**
     * Return a UIContext of the builder's data
     * @return a new UIContext
     */
    fun build(): UIContext {
        return UIContextImpl(map)
    }

    /**
     * Map from keyword to map of paramCount to context action.
     * Directives can have multiple commands based on paramCount.
     */
    private val map = HashMap<String, HashMap<Int, UIContextImpl.ContextAction<String>>>()
}
