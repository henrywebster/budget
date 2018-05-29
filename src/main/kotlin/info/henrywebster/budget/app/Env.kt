package info.henrywebster.budget.app

import info.henrywebster.budget.command.CommandFactory
import info.henrywebster.budget.account.ItemFactory
import info.henrywebster.budget.core.BudgetManager
import info.henrywebster.budget.state.MenuContext
import info.henrywebster.budget.state.MenuContextFactory
import info.henrywebster.budget.time.PeriodFactory
import info.henrywebster.budget.ui.UIContextBuilder
import java.io.BufferedReader
import java.io.EOFException
import java.io.File
import java.io.InputStreamReader
import java.util.function.Predicate

class Env {
    companion object {

        fun setup() {


            // budget menu setup
            val budgetContextBuilder = UIContextBuilder()

            /*
            SAVE FILE COMMAND
             */
            val saveBudgetMethod = {
                BudgetManager.save()
            }
            val saveBudgetCommand = { _: String ->
                CommandFactory.newNoInputCommand(saveBudgetMethod)
            }
            budgetContextBuilder.addDirective(
                    "save",
                    0,
                    saveBudgetCommand,
                    Predicate { true },
                    "error: could not save file"
            )

            /*
            EXIT BUDGET COMMAND
             */
            val switchToPastMethod = {
                activeState = pastState
            }

            val switchToPastCommand = { _: String ->
                CommandFactory.newNoInputCommand(switchToPastMethod)
            }
            budgetContextBuilder.addDirective(
                    "exit",
                    0,
                    switchToPastCommand,
                    Predicate({ true }),
                    "exit failed"
            )

            /*
            MAKE ENTRY DIRECTIVE
             */
            val enterItemMethod = { input: String ->
                val strings = input.split(" ")
                val period = when (strings[1]) {
                    "d" -> PeriodFactory.createDay()
                    "m" -> PeriodFactory.createMonth()
                    "y" -> PeriodFactory.createYear()
                    "o" -> PeriodFactory.createOnce()
                    else -> throw RuntimeException()
                }
                val value = strings[0].toDouble()

                val categories = strings[2].split(":")
                val debit = BudgetManager.getActive().getItem(categories[0])
                val credit = BudgetManager.getActive().getItem(categories[1])

                val item = ItemFactory.newLine(value, period)
                BudgetManager.getActive().addItem(item, credit, debit)
            }

            val enterItemCommand = { input: String ->
                CommandFactory.newMonoCommand(input, enterItemMethod)
            }

            budgetContextBuilder.addDirective(
                    "enter",
                    3,
                    enterItemCommand,
                    Predicate({ input: String ->
                        val strings = input.split(" ")
                        try {
                            strings[0].toDouble()
                        } catch (e: NumberFormatException) {
                            return@Predicate false
                        }

                        val valid = when (strings[1]) {
                            "d" -> true
                            "m" -> true
                            "y" -> true
                            "o" -> true
                            else -> false
                        }

                        if (!valid)
                            return@Predicate false

                        val categories = strings[2].split(":")
                        if (categories.size != 2)
                            return@Predicate false

                        if (BudgetManager.getActive().contains(categories[0])
                                && BudgetManager.getActive().contains(categories[1])) {
                            return@Predicate true
                        }
                        return@Predicate false
                    }),
                    "incorrect format or invalid account"
            )

            /*
            CALCULATE BUDGET DIRECTIVE
             */
            val calcArgMethod = { input: String ->
                val strings = input.split(" ")
                val period = when (strings[0]) {
                    "d" -> PeriodFactory.createDay()
                    "m" -> PeriodFactory.createMonth()
                    "y" -> PeriodFactory.createYear()
                    else -> throw RuntimeException()
                }
                println(BudgetManager.getActive().toString(period, strings[1]))
            }

            val calcArgCommand = { input: String ->
                CommandFactory.newMonoCommand(input, calcArgMethod)
            }

            budgetContextBuilder.addDirective(
                    "calc",
                    2,
                    calcArgCommand,
                    Predicate { input: String ->
                        val strings = input.split(" ")
                        val valid = when (strings[0]) {
                            "d" -> true
                            "m" -> true
                            "y" -> true
                            else -> false
                        }

                        if (!valid)
                            return@Predicate false
                        return@Predicate BudgetManager.getActive().contains(strings[1])
                    },
                    "could not calculate"
            )

            /*
            LIST ENTRIES DIRECTIVE
             */

            val listEntryMethod = {
                BudgetManager.getActive().printView()
            }
            val listEntryCommand = { _: String ->
                CommandFactory.newNoInputCommand(listEntryMethod)
            }
            budgetContextBuilder.addDirective(
                    "list",
                    0,
                    listEntryCommand,
                    Predicate({ true }),
                    "list entries failed"
            )


            val budgetContext = budgetContextBuilder.build()
            val budgetMenuContext = MenuContextFactory.newLabeledMenuContext(
                    budgetContext,
                    "> ",
                    { BudgetManager.getActive().name }
            )


            // main menu setup
            val contextBuilder = UIContextBuilder()


            /*
            CREATE BUDGET DIRECTIVE
             */

            val createBudgetMethod = { name: String ->
                BudgetManager.newBudget(name)
            }
            val createBudgetCommand = { name: String ->
                CommandFactory.newMonoCommand(name, createBudgetMethod)
            }
            contextBuilder.addDirective(
                    "create",
                    1,
                    createBudgetCommand,
                    Predicate({ name: String -> !BudgetManager.contains(name) }),
                    "already exists"
            )

            /*
            DELETE BUDGET DIRECTIVE
             */
            val deleteBudgetMethod = { name: String ->
                BudgetManager.removeBudget(name)
            }

            val deleteBudgetCommand = { name: String ->
                CommandFactory.newMonoCommand(name, deleteBudgetMethod)
            }
            contextBuilder.addDirective(
                    "delete",
                    1,
                    deleteBudgetCommand,
                    Predicate({ name: String -> BudgetManager.contains(name) }),
                    "does not exist"
            )

            /*
            LIST BUDGETS DIRECTIVE
             */
            val listBudgetMethod = {
                for (budget in BudgetManager.getList()) {
                    println("    - $budget")
                }
            }

            val listBudgetCommand = { _: String ->
                CommandFactory.newNoInputCommand(listBudgetMethod)
            }
            contextBuilder.addDirective(
                    "list",
                    0,
                    listBudgetCommand,
                    Predicate { true },
                    "list budget failed"
            )

            /*
            LOAD FILE DIRECTIVE
             */
            val loadBudgetMethod = { filename: String ->
                BudgetManager.open(filename)
            }
            val loadBudgetCommand = { filename: String ->
                CommandFactory.newMonoCommand(filename, loadBudgetMethod)
            }
            contextBuilder.addDirective(
                    "load",
                    1,
                    loadBudgetCommand,
                    Predicate { filename: String ->
                        File(filename).exists()
                    },
                    "could not open file"
            )

            /*
            OPEN BUDGET DIRECTIVE
             */
            val openBudgetContextMethod = { name: String ->
                BudgetManager.setActive(BudgetManager.find(name))
                pastState = activeState
                activeState = budgetMenuContext
            }

            val openBudgetContextCommand = { name: String ->
                CommandFactory.newMonoCommand(name, openBudgetContextMethod)
            }
            contextBuilder.addDirective(
                    "open",
                    1,
                    openBudgetContextCommand,
                    Predicate { name: String -> BudgetManager.contains(name) },
                    "does not exist"
            )


            /*
            EXIT MAIN MENU DIRECTIVE
             */
            val exitMethod = { throw EOFException() }
            val exitCommand = { _: String -> CommandFactory.newNoInputCommand(exitMethod) }
            contextBuilder.addDirective(
                    "exit",
                    0,
                    exitCommand,
                    Predicate({ true }),
                    "exit failed"
            )


            val uiContext = contextBuilder.build()
            val menuContext = MenuContextFactory.newMenuContext(uiContext, "> ")
            setContext(menuContext)
        }

        fun exec(input: String) {
            activeState.parse(input.trim())
        }

        private fun setContext(context: MenuContext) {
            activeState = context
        }

        fun run() {
            setup()
            print(activeState.getPrompt())

            // read input from the user
            BufferedReader(InputStreamReader(System.`in`)).use {
                while (true) {
                    try {
                        val input = it.readLine() ?: throw EOFException()
                        exec(input)
                    } catch (e: IllegalArgumentException) {
                        System.err.println(e.message)
                    } catch (e: EOFException) {
                        print("goodbye!")
                        System.exit(0)
                    } catch (e: RuntimeException) {
                        System.err.println("encountered unexpected error. shutting down.")
                        System.exit(1)
                    }
                    print(activeState.getPrompt())
                }
            }
        }

        private val nullState = MenuContextFactory.newMenuContext(UIContextBuilder().build(), "")
        private var pastState = nullState
        private var activeState = nullState
    }
}