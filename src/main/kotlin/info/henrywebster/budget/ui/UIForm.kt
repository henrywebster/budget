package info.henrywebster.budget.ui

import java.util.function.Predicate
import kotlin.collections.List

class UIForm(val heading: String, private val form: List<Pair<String>>) {

    class Pair<T>(
        val prompt: String,
        val predicate: Predicate<T>
    )

    fun checkInput(i: Int, input: String): Boolean {
        return form[i].predicate.test(input)
    }
}