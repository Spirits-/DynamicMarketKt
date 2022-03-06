import data.Item
import data.Region
import data.Shop
import kotlin.random.Random
import kotlin.random.nextInt

class MarketGenerator(private val shopsPerMarket: Int, private val canGenerateLess: Boolean) {

    fun generateMarket(regions: List<Region>): List<Shop.Instance> {
        val market = mutableListOf<Shop.Instance>()
        val shopNr = if (canGenerateLess) {
            val rng = Random(System.currentTimeMillis())
            rng.nextInt(1..shopsPerMarket)
        } else {
            shopsPerMarket
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

        return Shop.Instance(shopType.name, actualStock)
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