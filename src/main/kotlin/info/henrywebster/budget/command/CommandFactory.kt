package info.henrywebster.budget.command


class CommandFactory {
    companion object {
        fun <T> newMonoCommand(item: T, method: (T) -> Unit): Command {
            return MonoCommand(item, method)
        }

        fun newNoInputCommand(method: () -> Unit): Command {
            return NoInputCommand(method)
        }
    }
}