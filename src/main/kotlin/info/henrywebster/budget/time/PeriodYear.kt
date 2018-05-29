package info.henrywebster.budget.time

/**
 * Represents a yearly period
 */
internal class PeriodYear : BudgetPeriod() {

    /**
     * Entry point
     * @param period period to be visited
     */
    override fun visit(period: BudgetPeriod) {
        period.visit(this)
    }

    /**
     * Year visiting a month (factor by 12)
     * @param period month period to visit
     */
    override fun visit(period: PeriodMonth) {
        period.setFactor(12.0)
    }

    /**
     * Year visiting year (factor by 1)
     * @param period year period to visit
     */
    override fun visit(period: PeriodYear) {
        period.setFactor(1.0)
    }

    /**
     * Year visiting once (factor by 1)
     * @param period once period to visit
     */
    override fun visit(period: PeriodOnce) {
        period.setFactor(1.0)
    }

    /**
     * Year visiting day (factor by 365)
     * @param period day period to visit
     */
    override fun visit(period: PeriodDay) {
        period.setFactor(365.0)
    }
}