package info.henrywebster.budget

import info.henrywebster.budget.time.BudgetPeriod
import info.henrywebster.budget.time.PeriodFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TimeTest {

    // helper function for comparing doubles with an epsilon
    private fun checkDouble(a: Double, b: Double, epsilon: Double = 0.001): Boolean {
        return Math.abs(a - b) < epsilon
    }

    private fun calcHelper(a: Double, b: Double, period: BudgetPeriod): Double {
        return a * period.getFactor() - b
    }

    @Test
    fun factoryTest() {

        // create year
        val periodYearA = PeriodFactory.createYear()
        val periodYearB = PeriodFactory.createYear()

        assertNotEquals(periodYearA, periodYearB)
        periodYearA.visit(periodYearB)
        assertTrue(checkDouble(periodYearA.getFactor(), 1.0))

        // create month
        val periodMonthA = PeriodFactory.createMonth()
        val periodMonthB = PeriodFactory.createMonth()

        assertNotEquals(periodMonthA, periodMonthB)
        periodMonthA.visit(periodMonthB)
        assertTrue(checkDouble(periodMonthA.getFactor(), 1.0))

        // create day
        val periodDayA = PeriodFactory.createDay()
        val periodDayB = PeriodFactory.createDay()

        assertNotEquals(periodDayA, periodDayB)
        periodDayA.visit(periodDayB)
        assertTrue(checkDouble(periodDayA.getFactor(), 1.0))

        // create once
        val periodOnceA = PeriodFactory.createOnce()
        val periodOnceB = PeriodFactory.createOnce()

        assertNotEquals(periodOnceA, periodOnceB)
        periodOnceA.visit(periodDayB)
        assertTrue(checkDouble(periodOnceA.getFactor(), 1.0))
    }

    @Test
    fun visitOnceToOnceTest() {
        val periodOnceA = PeriodFactory.createOnce()
        val periodOnceB = PeriodFactory.createOnce()

        val valueA = 42.00
        val valueB = 20.00
        val expected = 22.00

        periodOnceA.visit(periodOnceB)
        val calculated = calcHelper(valueA, valueB, periodOnceA)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitOnceToDaily() {
        val periodOnce = PeriodFactory.createOnce()
        val periodDay = PeriodFactory.createDay()

        val valueA = 42.00
        val valueB = 20.00
        val expected = 22.00

        periodOnce.visit(periodDay)
        val calculated = calcHelper(valueA, valueB, periodOnce)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitOnceToMonthly() {
        val periodOnce = PeriodFactory.createOnce()
        val periodMonth = PeriodFactory.createMonth()

        val valueA = 10.00
        val valueB = 300.00
        val expected = -290.00

        periodOnce.visit(periodMonth)
        val calculated = calcHelper(valueA, valueB, periodOnce)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitOnceToYearly() {
        val periodOnce = PeriodFactory.createOnce()
        val periodYear = PeriodFactory.createYear()

        val valueA = 10.00
        val valueB = 300.00
        val expected = -290.00

        periodOnce.visit(periodYear)
        val calculated = calcHelper(valueA, valueB, periodOnce)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitDailyToOnce() {
        val periodDay = PeriodFactory.createDay()
        val periodOnce = PeriodFactory.createOnce()

        val valueA = 10.00
        val valueB = 300.00
        val expected = -290.00

        periodDay.visit(periodOnce)
        val calculated = calcHelper(valueA, valueB, periodDay)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitDailyToDaily() {
        val periodDayA = PeriodFactory.createDay()
        val periodDayB = PeriodFactory.createDay()

        val valueA = 10.00
        val valueB = 300.00
        val expected = -290.00

        periodDayA.visit(periodDayB)
        val calculated = calcHelper(valueA, valueB, periodDayA)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitDailyToMonthly() {
        val periodDay = PeriodFactory.createDay()
        val periodMonth = PeriodFactory.createMonth()

        val valueA = 10.00
        val valueB = 300.00
        val expected = 0.0

        periodDay.visit(periodMonth)
        val calculated = calcHelper(valueA, valueB, periodDay)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitDailyToYearly() {
        val periodDay = PeriodFactory.createDay()
        val periodYear = PeriodFactory.createYear()

        val valueA = 2.00
        val valueB = 70000.00
        val expected = -69270.00

        periodDay.visit(periodYear)
        val calculated = calcHelper(valueA, valueB, periodDay)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitMonthlyToOnce() {
        val periodMonth = PeriodFactory.createMonth()
        val periodOnce = PeriodFactory.createOnce()

        val valueA = 2.00
        val valueB = 10.00
        val expected = -8.0

        periodMonth.visit(periodOnce)
        val calculated = calcHelper(valueA, valueB, periodMonth)
        assertTrue(checkDouble(expected, calculated))

    }

    @Test
    fun visitMonthlyToDaily() {
        val periodMonth = PeriodFactory.createMonth()
        val periodDay = PeriodFactory.createDay()

        val valueA = 300.00
        val valueB = 10.00
        val expected = 0.0

        periodMonth.visit(periodDay)
        val calculated = calcHelper(valueA, valueB, periodMonth)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitMonthlyToMonthly() {
        val periodMonthA = PeriodFactory.createMonth()
        val periodMonthB = PeriodFactory.createMonth()

        val valueA = 20.00
        val valueB = 12.00
        val expected = 8.00

        periodMonthA.visit(periodMonthB)
        val calculated = calcHelper(valueA, valueB, periodMonthA)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitMonthlyToYearly() {
        val periodMonth = PeriodFactory.createMonth()
        val periodYear = PeriodFactory.createYear()

        val valueA = 10.00
        val valueB = 120.00
        val expected = 0.00

        periodMonth.visit(periodYear)
        val calculated = calcHelper(valueA, valueB, periodMonth)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitYearlyToOnceTest() {
        val periodOnce = PeriodFactory.createOnce()
        val periodYear = PeriodFactory.createYear()

        val valueA = 70000.00
        val valueB = 42.00
        val expected = 69958.00

        periodYear.visit(periodOnce)
        val calculated = calcHelper(valueA, valueB, periodYear)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitYearlyToDailyTest() {
        val periodDay = PeriodFactory.createDay()
        val periodYear = PeriodFactory.createYear()

        val valueA = 70000.00
        val valueB = 100.00
        val expected = 91.78

        periodYear.visit(periodDay)
        val calculated = calcHelper(valueA, valueB, periodYear)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitYearlyToMonthlyTest() {
        val periodMonth = PeriodFactory.createMonth()
        val periodYear = PeriodFactory.createYear()

        val valueA = 70000.00
        val valueB = 1000.00
        val expected = 4833.3333333333

        periodYear.visit(periodMonth)
        val calculated = calcHelper(valueA, valueB, periodYear)
        assertTrue(checkDouble(expected, calculated))
    }

    @Test
    fun visitYearlyToYearlyTest() {
        val periodYearA = PeriodFactory.createYear()
        val periodYearB = PeriodFactory.createYear()

        val valueA = 70000.00
        val valueB = 1000.00
        val expected = 69000.00

        periodYearA.visit(periodYearB)
        val calculated = calcHelper(valueA, valueB, periodYearA)
        assertTrue(checkDouble(expected, calculated))
    }


}