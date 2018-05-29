package info.henrywebster.budget.time

/**
 * Represents a month of time
 */
internal class PeriodMonth : BudgetPeriod() {

    /**
     * Entry point for the visitor pattern
     * @param period period to visit
     */
    override fun visit(period: BudgetPeriod) {
        period.visit(this)
    }

    /**
     * A month visiting a month (no change)
     * @param period month period to visit
     */
    override fun visit(period: PeriodMonth) {
        period.setFactor(1.0)
    }

    /**
     * A month visiting a year (divide the month amount by 12)
     * @param period year period to visit
     */
    override fun visit(period: PeriodYear) {
        period.setFactor(1.0 / 12.0)
    }

    /**
     * A month visiting a year (no change in factor)
     * @param period once to visit
     */
    override fun visit(period: PeriodOnce) {
        period.setFactor(1.0)
    }

    /**
     * A month visiting a day (divide month amount by 30)
     * @param period day to visit
     */
    override fun visit(period: PeriodDay) {
        period.setFactor(30.0)
    }
}