package info.henrywebster.budget.core

class KeywordManager {
    companion object {
        private val monoMap: HashMap<String, (String) -> Boolean> = HashMap()
        private val biMap: HashMap<String, (String, String) -> Boolean> = HashMap()

        fun bind(keyword: String, action: (String) -> Boolean) {
            monoMap[keyword] = action
        }

        fun bind(keyword: String, action: (String, String) -> Boolean) {
            biMap[keyword] = action
        }

        fun get(keyword: String): ((String) -> Boolean)? {
            return monoMap[keyword]
        }
    }
}