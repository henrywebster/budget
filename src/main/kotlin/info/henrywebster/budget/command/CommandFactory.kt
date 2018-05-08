package info.henrywebster.budget.command

class CommandFactory {
    companion object {
        fun newCommand(keyword: String) : Command {
            return CommandImpl(keyword)
        }
    }
}