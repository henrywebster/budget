package info.henrywebster.budget.account

/**
 * Factory class for values
 */
class ValueFactory {
    companion object {

        /**
         * Create a new value
         * @param value the amount for the value in currency
         * @return new value
         */
        fun newValue(value: Double): Value {
            return ValueImpl(value)
        }
    }
}