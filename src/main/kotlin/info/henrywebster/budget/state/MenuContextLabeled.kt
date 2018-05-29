package info.henrywebster.budget.state

import info.henrywebster.budget.ui.UIContext

/**
 * Represents a menu that has a special prompt
 */
internal class MenuContextLabeled(
        context: UIContext,
        prompt: String,
        private val getLabel: () -> String
) : MenuContextImpl(context, prompt) {

    /**
     * Return a prompt
     * @return string representing the prompt with the label
     */
    override fun getPrompt(): String {
        return "${getLabel.invoke()}${super.getPrompt()}"
    }
}
