package info.henrywebster.budget.ui

import kotlin.collections.ArrayList

sealed class UIFormBuilder(
    private val menu: List<UIForm.Pair<String>>
) {
    fun toUIForm(heading: String): UIForm {
        if (menu.size <= 1)
            throw IllegalStateException()
        val array = ArrayList<UIForm.Pair<String>>(menu)
        return UIForm(heading, array)
    }
}