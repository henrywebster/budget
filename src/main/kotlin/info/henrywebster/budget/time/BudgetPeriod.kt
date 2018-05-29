package info.henrywebster.budget.time

import java.io.Serializable

/**
 * This class represents a period of time on the ledger.
 *
 * It is used to calculate time based on another time.
 * One time object visits another and sets its factor
 * to the correct time.
 */
abstract class BudgetPeriod : Serializable {

    abstract fun visit(period: BudgetPeriod)
    internal abstract fun visit(period: PeriodMonth)
    internal abstract fun visit(period: PeriodYear)
    internal abstract fun visit(period: PeriodDay)
    internal abstract fun visit(period: PeriodOnce)

    /**
     * Set the internal factor of this period
     * @param factor the factor to set
     */
    internal fun setFactor(factor: Double) {
        assert(factor > 0.0)
        f = factor
    }

    /**
     * Get the internal factor
     * @return the current factor of the period
     */
    fun getFactor(): Double {
        assert(f > 0.0)
        return f
    }

    /**
     * internal factor datum
     */
    private var f = 0.0
}