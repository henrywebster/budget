package info.henrywebster.budget.ui

import java.util.function.Predicate
import kotlin.collections.List

class UIForm(private val heading: String, private val form: List<Pair<String>>) {

    class Pair<T>(
            val prompt: String,
            val predicate: Predicate<T>
    )

    /**
     * Public method for getting the size of the form
     * @return the number of items in the form
     */
    fun size(): Int {
        return form.size
    }

    /**
     * Accessor method for the form's heading
     * @return the string heading for the form
     */
    fun getHeading(): String {
        return heading
    }

    /**
     * Access method for a particular item's prompt
     * @return the string for the item's prompt
     */
    fun getPrompt(i: Int): String {
        return form[i].prompt
    }

    /**
     * Test the form input against the form's input predicate
     * @param i form index to check
     * @param input string to be tested
     * @returns the result of the form's predicate test on the input string
     */
    fun checkInput(i: Int, input: String): Boolean {
        return form[i].predicate.test(input)
    }
}