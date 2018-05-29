package info.henrywebster.budget.account

/**
 * Builder class for value interface
 */
class ValueBuilder(
        private val method: (Double, Double) -> Double
) {

    /**
     * Add an asset value to the builder
     * @param value value to add to assets
     */
    fun addAsset(value: Value) {
        assetList.add(value)
    }

    /**
     * Add a liability value to the builder
     * @param value value to add to liabilities
     */
    fun addLiability(value: Value) {
        liabilityList.add(value)
    }

    /**
     * create a value based on the data passed
     * @return the value based on the asset and liability list as well as the value class
     */
    fun build(): Value {
        var credit = 0.0
        var debit = 0.0

        for (value in assetList) {
            credit += value.getValue()
        }

        for (value in liabilityList) {
            debit += value.getValue()
        }

        return ValueFactory.newValue(method.invoke(credit, debit))
    }

    private val assetList = ArrayList<Value>()
    private val liabilityList = ArrayList<Value>()
}