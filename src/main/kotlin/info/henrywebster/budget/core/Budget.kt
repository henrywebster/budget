package info.henrywebster.budget.core

class Budget(
        val name: String,
        var assets: Int = 0,
        var liabilities: Int = 0,
        var equity: Int = 0
) {

    override fun toString(): String {
        return String.format("[%s] a: %d l: %d e: %d\n");
    }
}
