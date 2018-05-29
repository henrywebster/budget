package info.henrywebster.budget.time

/**
 * Represents a one-time period
 * All factors will be 1
 */
internal class PeriodOnce : BudgetPeriod() {

    /**
     * Entry point
     * @param period period to visit
     */
    override fun visit(period: BudgetPeriod) {
        period.visit(this)
    }

    /**
     * Month visiting a once
     * @param period month period to visit
     */
    override fun visit(period: PeriodMonth) {
        period.setFactor(1.0)
    }

    /**
     * Year visiting a once
     * @param period year period to visit
     */
    override fun visit(period: PeriodYear) {
        period.setFactor(1.0)
    }

    /**
     * Once visiting a once
     * @param period once period to visit
     */
    override fun visit(period: PeriodOnce) {
        period.setFactor(1.0)
    }

    /**
     * Day visiting a once
     * @param period day period to visit
     */
    override fun visit(period: PeriodDay) {
        period.setFactor(1.0)
    }
}