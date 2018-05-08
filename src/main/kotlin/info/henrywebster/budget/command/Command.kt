package info.henrywebster.budget.command

/**
 * add budget HenrysBudget
 * ->
 * Command(string, command, arg);
 * Command add = new LiteralEval("add", new)
 * Command
 */

interface Command  {
    fun run()
}