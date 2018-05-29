package info.henrywebster.budget.state

import info.henrywebster.budget.ui.UIContext

internal open class MenuContextImpl(
        private val context: UIContext,
        private val prompt: String
) : MenuContext {

    /**
     * Parse the input based on the context
     * @param input the raw input to pass to the parse state
     * @throws IllegalArgumentException if the underlying context detects invalid input
     */
    override fun parse(input: String) {
        context.input(input)
    }

    /**
     * Get the prompt for the menu state
     * @return a string representing the prompt for the user
     */
    override fun getPrompt(): String {
        return prompt
    }
}
