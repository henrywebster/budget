package info.henrywebster.budget.ui

/**
 * State pattern for parsing input
 */
interface UIContext {
    /**
     * Act on some input
     * @param line the new-line ended string that was input to console
     */
    fun input(line: String)
}