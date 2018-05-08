package info.henrywebster.budget

import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import info.henrywebster.budget.command.CommandFactory
// import info.henrywebster.budget.command.Command;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestExample {

    @Test
    fun test1() {

        val c = CommandFactory.newCommand("add")
        c.run()
        assertTrue(true)
    }
}