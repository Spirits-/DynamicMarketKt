package reader

import data.Region
import org.apache.poi.xssf.usermodel.XSSFSheet

class ConfigReader(private val sheet: XSSFSheet, private var SHOP_NUM: Int = -1, private var CAN_GENERATE_LESS: Boolean = false) {

    val generateLess: Boolean get() = CAN_GENERATE_LESS
    val shopNum: Int get() = SHOP_NUM

    companion object {
        const val SKIP_ROW = 0
        const val VAL_ROW = 1
        const val SHOP_PER_MARKET_ROW = 1
        const val CAN_GENERATE_LESS_ROW = 2
    }

    init {
        readRegions()
        readConstants()

        println("""CONFIG LOADED
            REGIONS: ${Region.masterList}
            CONSTANTS:
                STALLS PER MARKET: $shopNum
                CAN GENERATE LESS STALLS PER MARKET: $generateLess
        """.trimMargin())
    }

    private fun readConstants() {
        for (row in sheet) {
            if (row.rowNum == SKIP_ROW) continue
            if (row.getCell(0).stringCellValue == "X") break

            for (cell in row) {
                if (row.rowNum == SHOP_PER_MARKET_ROW && cell.columnIndex == VAL_ROW) {
                    SHOP_NUM = cell.numericCellValue.toInt()
                    break
                }
                if (row.rowNum == CAN_GENERATE_LESS_ROW && cell.columnIndex > VAL_ROW) {
                    CAN_GENERATE_LESS = cell.booleanCellValue
                    break
                }
            }
        }
    }

    private fun readRegions() {
        val row = sheet.getRow(0)
        for (cell in row) {
            if (cell.columnIndex == SKIP_ROW) continue
            val region = Region(name = cell.stringCellValue)
            Region.masterList.add(region)
        }
    }
}