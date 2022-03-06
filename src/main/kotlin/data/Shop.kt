package data

class Shop(
    val name: String,
    val globalStock: List<Item>,
    val itemRolls: Int,
    val validRegions: List<Region>,
    val regionalStock: Map<Region, List<Item>>
) {

    companion object {
        val shopList: MutableList<Shop> = mutableListOf()
    }

    class Builder(
        var name: String = "",
        var globalStock: List<Item> = mutableListOf(),
        var itemRolls: Int = -1,
        var validRegions: List<Region> = mutableListOf(),
        private var regionalStock: MutableMap<Region, List<Item>> = mutableMapOf()
    ) {

        init {
            for (r in Region.masterList) {
                regionalStock[r] = mutableListOf() //Avoids NPEs later down the line.
            }
        }

        fun build(): Shop {
            if (name == "" || globalStock.isEmpty() || itemRolls == -1 || validRegions.isEmpty()) throw IllegalArgumentException(
                "A shop was initialized with incomplete values: $this"
            )
            return Shop(name, globalStock, itemRolls, validRegions, regionalStock)
        }

        fun addRegionalStock(region: Region, items: List<Item>) {
            regionalStock[region] = items
        }

        override fun toString(): String {
            return "Shop.Builder(name='$name', globalStock=$globalStock, itemRolls=$itemRolls, validRegions=$validRegions, regionalStock=$regionalStock)"
        }
    }

    data class Instance(val name: String, val stock: Map<Item, Int>) {
        override fun toString(): String {
            return """Shop: $name
                |Stock: ${stockToString()}
            """.trimMargin()
        }

        private fun stockToString(): String {
            val sb = StringBuilder()
            for (entry in stock.entries) {
                sb.append(entry.key.name)
                sb.append(" x")
                sb.append(entry.value)
                sb.append("; ")
            }
            return sb.toString()
        }
    }

    override fun toString(): String {
        return "Shop(name='$name', globalStock=$globalStock, itemRolls=$itemRolls, validRegions=$validRegions, regionalStock=$regionalStock)"
    }

}