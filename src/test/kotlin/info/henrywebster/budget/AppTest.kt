package info.henrywebster.budget

import info.henrywebster.budget.app.Env
import info.henrywebster.budget.core.BudgetManager
import info.henrywebster.budget.time.PeriodFactory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.EOFException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppTest {


    // helper function for comparing doubles with an epsilon
    private fun checkDouble(a: Double, b: Double, epsilon: Double = 0.001): Boolean {
        return Math.abs(a - b) < epsilon
    }

    @BeforeAll
    fun beforeAll() {
        Env.setup()
    }

    @BeforeEach
    fun beforeTest() {
        BudgetManager.clear()
    }

    @Test
    fun createOnMainMenuTest() {
        assertFalse(BudgetManager.contains("foo"))
        Env.exec("create foo")
        assertTrue(BudgetManager.contains("foo"))

        assertFailsWith<IllegalArgumentException>(
                "foo already exists", { Env.exec("create foo") })
        assertFailsWith<IllegalArgumentException>(
                "0 invalid number of arguments", { Env.exec("create") }
        )
        assertTrue(BudgetManager.contains("foo"))
    }

    @Test
    fun deleteOnMainMenuTest() {

        assertFalse(BudgetManager.contains("foo"))
        assertFailsWith<IllegalArgumentException>(
                "foo does not exist", { Env.exec("delete foo") })

        Env.exec("create foo")
        assertTrue(BudgetManager.contains("foo"))

        Env.exec("delete foo")
        assertFalse(BudgetManager.contains("foo"))

        assertFailsWith<IllegalArgumentException>(
                "0 invalid number of arguments", { Env.exec("delete") }
        )
    }

    @Test
    fun listOnMainMenuTest() {
        Env.exec("create foo")
        Env.exec("list")

        assertFailsWith<IllegalArgumentException>(
                "1 invalid number of arguments", { Env.exec("list a") }
        )
    }

    @Test
    fun openBudgetMainMenuTest() {

        assertFailsWith<IllegalArgumentException>(
                "foo does not exist", { Env.exec("open foo") }
        )
        Env.exec("create foo")
        Env.exec("open foo")
        Env.exec("exit")
    }

    @Test
    fun loadAndSaveTest() {
        Env.exec("create foo")
        Env.exec("open foo")
        Env.exec("save")
        Env.exec("exit")
        Env.exec("delete foo")

        Env.exec("load foo.bgt")
        assertTrue(BudgetManager.contains("foo"))

        assertFailsWith<IllegalArgumentException>(
                "bar.bgt could not open file", { Env.exec("load bar.bgt") }
        )
        assertFailsWith<IllegalArgumentException>(
                "0 invalid number or arguments", { Env.exec("load") }
        )
    }

    @Test
    fun exitOnMainMenuTest() {
        assertFailsWith(EOFException::class, { Env.exec("exit") })
        assertFailsWith<IllegalArgumentException>(
                "1 invalid number of arguments", { Env.exec("exit bar") }
        )
    }

    @Test
    fun exitOnBudgetTest() {
        Env.exec("create foo")
        Env.exec("open foo")
        Env.exec("exit")
        Env.exec("create bar")
        Env.exec("open bar")

        assertEquals("bar", BudgetManager.getActive().name)
        Env.exec("exit")
    }

    @Test
    fun enterItemBudgetTest() {
        Env.exec("create foo")
        Env.exec("open foo")
        Env.exec("enter 70000 y cash:income")

        val cashAccount = BudgetManager.getActive().getItem("cash")
        val incomeAccount = BudgetManager.getActive().getItem("income")
        assertTrue(checkDouble(
                BudgetManager.getActive().calculate(PeriodFactory.createYear(), cashAccount).getValue(),
                70000.0))
        assertTrue(checkDouble(
                BudgetManager.getActive().calculate(PeriodFactory.createYear(), incomeAccount).getValue(),
                -70000.0))

        assertFailsWith<IllegalArgumentException>(
                "incorrect format or invalid account",
                { Env.exec("enter a y cash:income") }
        )

        assertFailsWith<IllegalArgumentException>(
                "incorrect format or invalid account",
                { Env.exec("enter 70000 l cash:income") }
        )
        assertFailsWith<IllegalArgumentException>(
                "incorrect format or invalid account",
                { Env.exec("enter 70000 y invalid:income") }
        )
        assertFailsWith<IllegalArgumentException>(
                "incorrect format or invalid account",
                { Env.exec("enter a y cash:invalid") }
        )

        Env.exec("exit")
    }

    @Test
    fun listEntriesTest() {
        Env.exec("create foo")
        Env.exec("open foo")
        Env.exec("list")
        Env.exec("exit")
    }
}