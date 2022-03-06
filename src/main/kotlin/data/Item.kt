package data

data class Item(val name: String, val min: Int, val max: Int, val multiRoll: Boolean, val isSpecial: Boolean = false, val specialVariants: ArrayList<Item>) {

    val indices = min..max

    companion object {
        val masterList: MutableList<Item> = mutableListOf()
        const val SPECIAL_PREFIX = "SPC_"
        fun getItemFromMasterList(name: String): Item {
            for (i in masterList) {
                if (name == i.name) {
                    return i
                }
            }
            throw IllegalArgumentException("Item $name not found in master list")
        }
    }

    class Builder(var name: String = "", var min: Int = -1, var max: Int = -1, var multiRoll: Boolean = true, var isSpecial: Boolean = false, var specialVariants: ArrayList<Item> = ArrayList()) {

        fun build(): Item {
            if (name == "" || min == -1 || max == -1) throw IllegalArgumentException("An item was initialized with incomplete values: $this")
            return Item(name, min, max, multiRoll, isSpecial, specialVariants)
        }

        override fun toString(): String {
            return "Item.Builder(name='$name', min=$min, max=$max)"
        }


    }

}
