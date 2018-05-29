package info.henrywebster.budget.core

import info.henrywebster.budget.account.Entry
import info.henrywebster.budget.account.ItemFactory
import java.io.*

/**
 * BudgetManager
 *
 * Singleton for keeping the global budget list
 */

class BudgetManager {
    companion object {
        /**
         * Create a new budget object and populate with default fields
         * @param name string to be the name of the budget
         */
        fun newBudget(name: String) {
            assert(!map.contains(name))

            val root = ItemFactory.newAssetCategory("root")
            val rootAsset = ItemFactory.newAssetCategory("rootAsset")
            val rootLiability = ItemFactory.newLiabilityCategory("rootLiability")
            val income = ItemFactory.newAssetCategory("income")
            val expense = ItemFactory.newLiabilityCategory("expense")
            val cash = ItemFactory.newAssetCategory("cash")

            val list = ArrayList<Entry>()
            list.add(Entry(root, null, null))
            list.add(Entry(rootAsset, root, null))
            list.add(Entry(rootLiability, root, null))
            list.add(Entry(income, rootAsset, rootLiability))
            list.add(Entry(expense, rootLiability, rootAsset))
            list.add(Entry(cash, rootAsset, rootLiability))

            map[name] = Budget(name, root, list)
        }

        /**
         * Remove a budget of name from the table
         * @param name the name of the budget to remove
         * @throws RuntimeException if no budget of name is in the table
         */
        fun removeBudget(name: String) {
            assert(map.contains(name))
            map.remove(name) ?: throw RuntimeException("attempted to remove non-existent budget")
        }

        /**
         * Set the active budget
         * @param budget the budget to be made active
         */
        fun setActive(budget: Budget) {
            assert(activeBudget != budget)
            activeBudget = budget
        }

        /**
         * Get the active budget in the manager
         * @return the active budget
         * @throws RuntimeException if the active manager is null
         */
        fun getActive(): Budget {
            assert(null != activeBudget)
            return activeBudget ?: throw RuntimeException("unsuspected error opening budget menu")
        }

        /**
         * Query if a budget of a name is in the table
         * @return true if in table, false if not
         */
        fun contains(name: String): Boolean {
            return map.contains(name)
        }

        /**
         * Gets a budget of the name
         * @returns the budget of that name
         * @throws RuntimeException if no budget of that name is in the table
         */
        fun find(name: String): Budget {
            // assert(map.contains(name))
            return map[name] ?: throw RuntimeException("input not properly checked")
        }

        /**
         * Deletes all budget objects in the manager
         */
        fun clear() {
            map.clear()
        }

        /**
         * Write a budget file. The file will automatically have a filename with
         * the budget's name.
         */
        fun save() {
            // write to a file
            val budget = getActive()
            ObjectOutputStream(FileOutputStream(File("${budget.name}.bgt"))).use {
                it.writeObject(getActive())
            }
        }

        /**
         * Open a budget file
         * @param filename filename of the budget file
         */
        fun open(filename: String) {
            ObjectInputStream(FileInputStream(File(filename))).use {
                val budget = it.readObject() as Budget
                map[budget.name] = budget
            }
        }

        /**
         * Get a list of budget objects
         * @return immutable list of budget objects
         */
        fun getList(): List<Budget> {
            return ArrayList(map.values)
        }

        // data

        /**
         * The table for keeping track of budgets.
         * The name of the budget is the key,
         * the budget object is the entry
         */
        private val map: HashMap<String, Budget> = HashMap()

        /**
         * The current active budget
         */
        private var activeBudget: Budget? = null
    }
}