package info.henrywebster.budget.ui

import java.util.function.Predicate
import kotlin.collections.ArrayList
import kotlin.collections.MutableList

class UIFormBuilder() {

//    public fun UIFormBuilder() {}

    /**
     * Turn whatever is currently in the builder to a UI form
     * @heading the heading of the entire menu panel
     * @return the complete UI form object
     */
    fun toUIForm(heading: String): UIForm {
        if (menu.size <= 1)
            throw IllegalStateException()
        val array = ArrayList<UIForm.Pair<String>>(menu)
        return UIForm(heading, array)
    }

    /**
     *  Adds an option to the builder
     *  @prompt the message for this option
     *  @predicate the boolean that must be true for the option to be valid
     */
    fun add(prompt: String, predicate: Predicate<String>) {
        menu.add(UIForm.Pair(prompt, predicate))
    }

    /**
     * The internal list of current UI elements in the builder
     * TODO("Improvement"): heuristically determine a good initial size
     */
    private val menu: MutableList<UIForm.Pair<String>> = ArrayList()
}