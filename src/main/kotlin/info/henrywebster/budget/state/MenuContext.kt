package info.henrywebster.budget.state

/**
 * Menu state pattern
 */
interface MenuContext {
    fun parse(input: String)
    fun getPrompt(): String
}
