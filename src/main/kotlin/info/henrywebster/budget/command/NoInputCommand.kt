package info.henrywebster.budget.command

class NoInputCommand(
        private val cmd: () -> Unit
) : Command {
    override fun run() {
        cmd.invoke()
    }
}