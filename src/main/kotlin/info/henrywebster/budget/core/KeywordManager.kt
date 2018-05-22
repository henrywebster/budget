package info.henrywebster.budget.core

import info.henrywebster.budget.command.CommandBuilder

class KeywordManager {
    companion object {
        private val map: HashMap<String, CommandBuilder> = HashMap()

        fun bind(keyword: String, action: CommandBuilder) {
            map[keyword] = action
        }

        fun get(keyword: String): CommandBuilder {
            return map[keyword] ?: throw IllegalArgumentException("Unknown keyword: $keyword")
        }

        /**
         * > Ask the keyword manager for a description of how the function is built
         * > verify the input string
         *
         *
         * create myBudget
         *
         * 1.   look up 'create' in KeywordManager hash table
         * 2.   pre-check input (make sure budget doesn't already exist)
         * 3.   provide back a Command
         */
    }
}