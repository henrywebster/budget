package info.henrywebster.budget.command

internal class MonoCommand<T>(

        private val item: T,
        private val cmd: (T) -> Boolean
) : Command {

    // TODO(fix): check for false boolean
    override fun run() {
        cmd.invoke(item)
    }
}

