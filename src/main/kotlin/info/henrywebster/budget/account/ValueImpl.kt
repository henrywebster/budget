package info.henrywebster.budget.account

/**
 * Internal implementation of value class
 */
internal class ValueImpl(
        private val value: Double
) : Value {

    /**
     * Get the value in currency
     * @return value amount in currency
     */
    override fun getValue(): Double {
        return value
    }

    /**
     * Get a string representation
     * @return string of currency amount
     */
    override fun toString(): String {
        return getValue().toString()
    }
}