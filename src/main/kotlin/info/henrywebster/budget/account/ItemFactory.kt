package info.henrywebster.budget.account

import info.henrywebster.budget.time.BudgetPeriod
import info.henrywebster.budget.time.PeriodFactory
import java.util.Random


/**
 * Factory class for item interface
 */
class ItemFactory {
    companion object {

        /**
         * Generates a name for anonymous entries
         * @return a random hex string to identify the entry
         */
        private fun generateName(): String {
            return Integer.toHexString(generator.nextInt())
        }

        /**
         * Create a new asset category
         * @param name string name for the entry
         * @param period time period class for the entry
         */
        fun newAssetCategory(
                name: String = generateName(),
                period: BudgetPeriod = PeriodFactory.createYear()
        ): Item {
            return ItemCategory(name, { c: Double, d: Double -> c - d }, period)
        }

        /**
         * Create a new liability category
         * @param name string name for the entry
         * @param period time period class for the entry
         */
        fun newLiabilityCategory(
                name: String = generateName(),
                period: BudgetPeriod = PeriodFactory.createYear()
        ): Item {
            return ItemCategory(name, { c: Double, d: Double -> d - c }, period)
        }

        /**
         * Create a new single entry
         * @param value currency value for the entry
         * @param period time period class for the entry
         * @param name string name for the entry
         */
        fun newLine(
                value: Double,
                period: BudgetPeriod = PeriodFactory.createYear(),
                name: String = generateName()
        ): Item {
            return ItemLine(name, period, ValueFactory.newValue(value))
        }

        private val generator = Random()
    }
}