package info.henrywebster.budget.core

class BudgetManager {
    companion object {
        private val map: HashMap<String, Budget> = HashMap()

        // TODO(fix): what if the budget by that name already exists?
        fun newBudget(name: String): Budget {
            val budget = Budget(name)
            map[name] = budget
            return budget
        }

        fun removeBudget(name: String): Boolean {
            if (map.containsKey(name)) {
                map.remove(name)
                return true
            }
            return false
        }

        fun contains(name: String): Boolean {
            return map.contains(name)
        }

        // TODO: return exception if not round
        fun find(name: String): Budget? {
            return map[name]
        }
    }
}