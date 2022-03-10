import data.Item
import data.Region
import data.Shop
import kotlin.random.Random
import kotlin.random.nextInt

class MarketGenerator(private val minShops: Int, private val maxShops: Int, private val specialItems: Int) {

    fun generateMarket(regions: List<Region>): List<Shop.Instance> {
        val market = mutableListOf<Shop.Instance>()
        val shopNr =
            if (maxShops == minShops) {
                minShops
            } else {
                val rng = Random(System.currentTimeMillis())
                rng.nextInt(minShops..maxShops)
            }
        val possibleShops = getPossibleShops(regions)

        for (i in 1..shopNr) {
            market.add(generateStall(regions, possibleShops))
        }
        return market
    }

    private fun generateStall(regions: List<Region>, possibleShops: MutableList<Shop>): Shop.Instance {
        val rng = Random(System.currentTimeMillis())
        val shopType = selectShopType(rng, possibleShops)
        val isSpecial = isShopSpecial(shopType, rng)
        var possibleStock = getPossibleStock(regions, shopType)
        val actualStock = mutableMapOf<Item, Int>()

        for (i in 0..shopType.itemRolls) {
            var item = possibleStock[rng.nextInt(possibleStock.indices)]
            if (item.name.startsWith("SPC_")) {
                item = item.specialVariants[rng.nextInt(item.specialVariants.indices)]
            }
            val amount = rng.nextInt(item.indices)
            actualStock.merge(item, amount, Int::plus)
            if (!item.multiRoll)
                possibleStock = possibleStock.drop(i)
        }

        if (isSpecial) {
            val specialStockCopy = mutableListOf<Item>()
            shopType.specialStock.forEach { specialStockCopy.add(it) }
            for (i in 0 until specialItems) {
                val item = specialStockCopy[rng.nextInt(specialStockCopy.indices)]
                val amount = rng.nextInt(item.indices)
                actualStock.merge(item, amount, Int::plus)
                if (!item.multiRoll) {
                    specialStockCopy.remove(item)
                }
            }
        }

        return Shop.Instance(shopType.name, isSpecial, actualStock)
    }

    private fun isShopSpecial(shopType: Shop, rng: Random): Boolean {
        if (shopType.specialChance == 0.00) {
            return false
        }
        val roll = rng.nextInt(0..100)
        return (roll >= shopType.specialChance * 100)
    }

    private fun selectShopType(
        rng: Random,
        possibleShops: MutableList<Shop>
    ): Shop {
        val choice = rng.nextInt(possibleShops.indices)
        return possibleShops.removeAt(choice)
    }

    private fun getPossibleShops(regions: List<Region>): MutableList<Shop> {
        val result = mutableListOf<Shop>()
        for (shop in Shop.shopList) {
            if (regions.any { it in shop.validRegions }) {
                result.add(shop)
            }
        }
        return result
    }

    private fun getPossibleStock(regions: List<Region>, shopType: Shop): List<Item> {
        val possibleStock = ArrayList(shopType.globalStock)
        for (r in regions) {
            shopType.regionalStock[r]?.let { possibleStock.addAll(it) }
        }
        return possibleStock
    }
}