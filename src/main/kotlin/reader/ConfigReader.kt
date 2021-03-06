package reader

import data.Region
import org.apache.poi.xssf.usermodel.XSSFSheet

class ConfigReader(
    private val sheet: XSSFSheet,
    private var MAX_SHOPS: Int = -1,
    private var MIN_SHOPS: Int = -1,
    private var SPECIAL_ITEMS: Int = -1,
) {

    val maxShops: Int get() = MAX_SHOPS
    val minShops: Int get() = MIN_SHOPS
    val specialItems: Int get() = SPECIAL_ITEMS

    companion object {
        const val SKIP_ROW = 0
        const val VAL_COL = 1
        const val MIN_SHOPS_ROW = 1
        const val MAX_SHOPS_ROW = 2
        const val SPECIAL_ITEMS_ROW = 3
    }

    init {
        readRegions()
        readConstants()

        println(
            """CONFIG LOADED
            REGIONS: ${Region.masterList}
            CONSTANTS:
                MIN STALLS PER MARKET: $minShops
                MAX STALLS PER MARKET: $maxShops
                SPECIAL ITEMS NUMBER: $specialItems
        """.trimMargin()
        )
    }

    private fun readConstants() {
        for (row in sheet) {
            if (row.rowNum == SKIP_ROW) continue
            if (row.getCell(0).stringCellValue == "X") break

            for (cell in row) {
                if (cell.columnIndex == 0) continue
                else if (cell.columnIndex > VAL_COL) break

                when (row.rowNum) {
                    MIN_SHOPS_ROW -> {
                        MIN_SHOPS = cell.numericCellValue.toInt()
                        break
                    }
                    MAX_SHOPS_ROW -> {
                        MAX_SHOPS = cell.numericCellValue.toInt()
                        break
                    }
                    SPECIAL_ITEMS_ROW -> {
                        SPECIAL_ITEMS = cell.numericCellValue.toInt()
                    }
                }
            }
        }
    }

    private fun readRegions() {
        val row = sheet.getRow(0)
        for (cell in row) {
            if (cell.columnIndex == SKIP_ROW) continue
            val name = cell.stringCellValue
            if (name.isNotEmpty()) {
                val region = Region(name = cell.stringCellValue)
                Region.masterList.add(region)
            }
        }
    }
}