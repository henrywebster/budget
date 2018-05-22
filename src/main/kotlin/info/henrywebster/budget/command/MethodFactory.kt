package info.henrywebster.budget.command


class MethodFactory {

    companion object {
        fun <C : MutableCollection<T>, T> addToMutableCollectionRecipe(collection: C, item: T): Boolean {
            return collection.add(item)
        }

        fun <C : MutableCollection<T>, T> removeFromMutableCollectionRecipe(collection: C, item: T): Boolean {
            return collection.remove(item)
        }
    }
}

