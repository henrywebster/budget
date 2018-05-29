package info.henrywebster.budget.state

import info.henrywebster.budget.ui.UIContext

/**
 * Factory class for menu contexts (states)
 */
class MenuContextFactory {
    companion object {

        /**
         * Create a new generic menu context
         * @param context UIContext parser to be used by the menu
         * @param prompt string printed on the command line before input
         * @return new MenuContext
         */
        fun newMenuContext(context: UIContext, prompt: String): MenuContext {
            return MenuContextImpl(context, prompt)
        }

        /**
         * Create a labeled menu context
         * @param context UIContext parser to be used by the menu
         * @param prompt string printed on the command line before input
         * @return new MenuContext
         */
        fun newLabeledMenuContext(
                context: UIContext,
                prompt: String,
                getter: () -> String
        ): MenuContext {
            return MenuContextLabeled(context, prompt, getter)
        }
    }
}