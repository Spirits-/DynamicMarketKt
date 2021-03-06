package data

class Shop(
    val name: String,
    val globalStock: List<Item>,
    val itemRolls: Int,
    val validRegions: List<Region>,
    val regionalStock: Map<Region, List<Item>>,
    val specialStock: List<Item>,
    val specialChance: Double
) {

    companion object {
        val shopList: MutableList<Shop> = mutableListOf()
    }

    class Builder(
        var name: String = "",
        var globalStock: List<Item> = mutableListOf(),
        var itemRolls: Int = -1,
        var validRegions: List<Region> = mutableListOf(),
        private var regionalStock: MutableMap<Region, List<Item>> = mutableMapOf(),
        var specialStock: List<Item> = mutableListOf(),
        var specialChance: Double = -1.0
    ) {

        init {
            for (r in Region.masterList) {
                regionalStock[r] = mutableListOf() //Avoids NPEs later down the line.
            }
        }

        fun build(): Shop {
            if (name == "" || globalStock.isEmpty() || itemRolls == -1 || validRegions.isEmpty() || specialChance == -1.0) throw IllegalArgumentException(
                "A shop was initialized with incomplete values: $this"
            )
            return Shop(name, globalStock, itemRolls, validRegions, regionalStock, specialStock, specialChance)
        }

        fun addRegionalStock(region: Region, items: List<Item>) {
            regionalStock[region] = items
        }

        override fun toString(): String {
            return "Shop.Builder(name='$name', globalStock=$globalStock, itemRolls=$itemRolls, validRegions=$validRegions, regionalStock=$regionalStock)"
        }
    }

    data class Instance(val name: String, val isSpecial: Boolean, val stock: Map<Item, Int>) {
        override fun toString(): String {
            return """Shop: $name SPECIAL: $isSpecial
                |${stockToString()}
            """.trimMargin()
        }

        private fun stockToString(): String {
            val sb = StringBuilder()
            for (entry in stock.entries) {
                sb.append("\t")
                sb.append(entry.key.name)
                sb.append(" x")
                sb.append(entry.value)
                sb.append("\n")
            }
            return sb.toString()
        }
    }

    override fun toString(): String {
        return "Shop(name='$name', globalStock=$globalStock, itemRolls=$itemRolls, validRegions=$validRegions, regionalStock=$regionalStock)"
    }

}