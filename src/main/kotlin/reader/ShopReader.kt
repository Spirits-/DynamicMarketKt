package reader

import data.Item
import data.Region
import data.Shop
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import java.util.*

class ShopReader(private val sheet: XSSFSheet) {
    init {
        readAllShops()
    }

    companion object {
        private const val SKIP_ROW = 0

        private const val NAME_COL = 0
        private const val GLOBAL_STOCK_COL = 1
        private const val VALID_REGIONS_COL = 2
        private const val ITEM_ROLL_COL = 3
        private const val SPECIAL_CHANCE_COL = 4
        private const val SPECIAL_ITEMS_COL = 5
        private const val REGIONS_COL = 6
        private val LAST_REGION_COL = REGIONS_COL + Region.masterList.size - 1
        private const val DIVISOR = ";"
    }

    private fun readAllShops() {
        for (row in sheet) {
            if (row.rowNum == SKIP_ROW) continue
            if (row.getCell(0).stringCellValue == "X") break
            Shop.shopList.add(readSingleShop(row))
        }
    }

    private fun readSingleShop(row: Row): Shop {
        val shopBuilder = Shop.Builder()
        for (cell in row) {
            when (cell.columnIndex) {
                NAME_COL -> shopBuilder.name = cell.stringCellValue
                GLOBAL_STOCK_COL -> {
                    if (cell == null) continue
                    shopBuilder.globalStock = readStockFromCell(cell)
                }
                VALID_REGIONS_COL -> {
                    if (cell == null) continue
                    shopBuilder.validRegions = readRegionsFromCell(cell)
                }
                ITEM_ROLL_COL -> {
                    shopBuilder.itemRolls = cell.numericCellValue.toInt()
                }
                SPECIAL_CHANCE_COL -> {
                    shopBuilder.specialChance = cell.numericCellValue
                }
                SPECIAL_ITEMS_COL -> {
                    shopBuilder.specialStock = readStockFromCell(cell)
                }
                in REGIONS_COL..LAST_REGION_COL -> {
                    val topCell = cell.getTopCell().stringCellValue

                    val region = Region.getRegionFromMasterList(topCell)
                    shopBuilder.addRegionalStock(region, readStockFromCell(cell))
                }
            }
        }
        return shopBuilder.build()
    }

    private fun Cell.getTopCell(): Cell {
        return this.sheet.getRow(0).getCell(this.columnIndex)

    }

    private fun readRegionsFromCell(cell: Cell): List<Region> {
        if (cell.stringCellValue.lowercase(Locale.getDefault()) == "all") return Region.masterList

        val dividedNames = cell.stringCellValue.split(DIVISOR)
        dividedNames.forEach(String::trim)
        val result: MutableList<Region> = ArrayList()
        dividedNames.forEach {
            val region = Region.getRegionFromMasterList(it)
            result.add(region)
        }
        return result
    }

    private fun readStockFromCell(cell: Cell): List<Item> {
        val result: MutableList<Item> = ArrayList()
        if (cell.cellTypeEnum == CellType.BLANK) {
            return result
        }

        val dividedNames = cell.stringCellValue.split(DIVISOR)
        dividedNames.forEach(String::trim)
        dividedNames.forEach {
            val item =
                Item.getItemFromMasterList(it.trim())
            result.add(item)
        }
        return result
    }
}