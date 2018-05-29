package info.henrywebster.budget

import info.henrywebster.budget.account.ItemFactory
import info.henrywebster.budget.account.ValueBuilder
import info.henrywebster.budget.account.ValueFactory
import info.henrywebster.budget.time.PeriodFactory
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {

    // helper function for comparing doubles with an epsilon
    private fun checkDouble(a: Double, b: Double, epsilon: Double = 0.001): Boolean {
        return Math.abs(a - b) < epsilon
    }

    @Test
    fun valueTest() {

        val valueA = ValueFactory.newValue(10.0)
        val valueB = ValueFactory.newValue(15.0)

        assertEquals(10.0, valueA.getValue())
        assertEquals(15.0, valueB.getValue())
    }

    @Test
    fun valueBuilderAssetDebitTest() {
        val valueA = ValueFactory.newValue(10.0)
        val valueB = ValueFactory.newValue(15.0)


        val builder = ValueBuilder({ c: Double, d: Double -> c - d })
        builder.addAsset(valueA)
        builder.addAsset(valueB)
        val valueC = builder.build()

        assertEquals(25.0, valueC.getValue())
    }

    @Test
    fun valueBuilderAssetCreditTest() {
        val valueA = ValueFactory.newValue(10.0)
        val valueB = ValueFactory.newValue(15.0)


        val builder = ValueBuilder({ c: Double, d: Double -> c - d })
        builder.addLiability(valueA)
        builder.addLiability(valueB)
        val valueC = builder.build()

        assertEquals(-25.0, valueC.getValue())
    }

    @Test
    fun valueBuilderAssetCreditAndDebitTest() {
        val valueA = ValueFactory.newValue(10.0)
        val valueB = ValueFactory.newValue(15.0)


        val builder = ValueBuilder({ c: Double, d: Double -> c - d })
        builder.addAsset(valueA)
        builder.addLiability(valueB)
        val valueC = builder.build()

        assertEquals(-5.0, valueC.getValue())
    }

    @Test
    fun itemLineFactoryTest() {
        val item = ItemFactory.newLine(42.0, PeriodFactory.createMonth())
        assertTrue(checkDouble(42.0, item.calculate(PeriodFactory.createMonth()).getValue()))
        assertTrue(checkDouble(42.0 * 12, item.calculate(PeriodFactory.createYear()).getValue()))
    }

    @Test
    fun itemAssetFactoryTest() {
        val asset = ItemFactory.newAssetCategory("test")
        assertEquals("test", asset.getName())

        val debitItem = ItemFactory.newLine(100.0, PeriodFactory.createMonth())
        asset.addDebitItem(debitItem)

        val creditItem = ItemFactory.newLine(2.0, PeriodFactory.createOnce())
        asset.addCreditItem(creditItem)

        assertTrue(checkDouble(100.00 * 12 - 2.0, asset.calculate(PeriodFactory.createYear()).getValue()))
    }

    @Test
    fun itemLiabilityFactoryTest() {
        val asset = ItemFactory.newLiabilityCategory("test")
        assertEquals("test", asset.getName())

        val debitItem = ItemFactory.newLine(100.0, PeriodFactory.createMonth())
        asset.addDebitItem(debitItem)

        val creditItem = ItemFactory.newLine(2.0, PeriodFactory.createOnce())
        asset.addCreditItem(creditItem)

        assertTrue(checkDouble(-(100.00 * 12) + 2.0, asset.calculate(PeriodFactory.createYear()).getValue()))
    }
}