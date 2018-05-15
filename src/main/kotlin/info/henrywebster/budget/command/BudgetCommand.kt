package info.henrywebster.budget.command

import info.henrywebster.budget.core.Budget

internal class BudgetCommand(
        private val b: Budget,
        private val cmd: (Budget) -> Boolean
) : Command {

    // TODO(fix): check for false boolean
    override fun run() {
        cmd.invoke(b)
    }
}

