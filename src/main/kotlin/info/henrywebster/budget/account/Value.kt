package info.henrywebster.budget.account

import java.io.Serializable

/**
 * Data class for the different parts of accounting
 */
interface Value : Serializable {

    /**
     * Get the value in double of the value object
     */
    fun getValue(): Double
}