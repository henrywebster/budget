package info.henrywebster.budget.command

internal class CommandImpl(val keyword: String) : Command {
    override fun run() {
        println("RAN")
    }
}