package info.henrywebster.budget.time

/**
 * Represents a daily entry
 */
internal class PeriodDay : BudgetPeriod() {

    /**
     * Entry point
     * @param period period to be visited
     */
    override fun visit(period: BudgetPeriod) {
        period.visit(this)
    }

    /**
     * A day visiting a month
     * @param period month period to be visited (divide by 30)
     */
    override fun visit(period: PeriodMonth) {
        period.setFactor(1 / 30.0)
    }

    /**
     * A day visiting a year
     * @param period year period to be visited (divide by 365)
     */
    override fun visit(period: PeriodYear) {
        period.setFactor(1.0 / 365.0)
    }

    /**
     * A day visiting a once
     * @param period once period to be visited (factored by 1)
     */
    override fun visit(period: PeriodOnce) {
        period.setFactor(1.0)
    }

    /**
     * A day visiting a day
     * @param period day period to be visited (factored by 1)
     */
    override fun visit(period: PeriodDay) {
        period.setFactor(1.0)
    }
}