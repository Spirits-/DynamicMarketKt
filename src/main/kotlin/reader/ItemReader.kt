package reader

import data.Item
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet

class ItemReader(private val sheet: XSSFSheet, val items: MutableList<Item>) {

    companion object {
        const val SKIP_ROW = 0

        const val NAME_COL = 0
        const val MIN_COL = 1
        const val MAX_COL = 2
        const val MULTI_ROLL_COL = 3
    }

    init {
        readAllItems()
    }

    private fun readAllItems() {
        for (row in sheet) {
            if (row.rowNum == SKIP_ROW) continue
            if (row.getCell(0).stringCellValue == "X") break
            readSingleItem(row)
        }
    }

    private fun readSingleItem(row: Row?) {
        if (row != null) {
            val itemBuilder = Item.Builder()
            for (cell in row) {
                when (cell.columnIndex) {
                    NAME_COL -> {
                        if (cell.stringCellValue == "") {
                            break
                        }
                        if (cell.stringCellValue.startsWith(Item.SPECIAL_PREFIX)) {
                            ItemReader(sheet.workbook.getSheet(cell.stringCellValue), itemBuilder.specialVariants)
                        }
                        itemBuilder.name = cell.stringCellValue
                    }
                    MIN_COL -> itemBuilder.min = cell.numericCellValue.toInt()
                    MAX_COL -> itemBuilder.max = cell.numericCellValue.toInt()
                    MULTI_ROLL_COL -> {
                        itemBuilder.multiRoll = cell.booleanCellValue
                        val it = itemBuilder.build()
                        items.add(it)
                    }
                }
            }
        }
    }
}