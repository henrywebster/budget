package info.henrywebster.budget.time

/**
 * Factory for period objects
 */
class PeriodFactory {
    companion object {
        /**
         * Create a year object
         * @return year period
         */
        fun createYear(): BudgetPeriod {
            return PeriodYear()
        }

        /**
         * create a month object
         * @return month period
         */
        fun createMonth(): BudgetPeriod {
            return PeriodMonth()
        }

        /**
         * create a once object
         * @return once period
         */
        fun createOnce(): BudgetPeriod {
            return PeriodOnce()
        }

        /**
         * create a day object
         * @return day period
         */
        fun createDay(): BudgetPeriod {
            return PeriodDay()
        }
    }
}