import data.Item
import data.Region
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import reader.ConfigReader
import reader.ItemReader
import reader.ShopReader
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.system.exitProcess

const val ASCII_OFFSET = 48

fun main(args: Array<String>) {
    if (args.isEmpty() || !args[0].endsWith(".xlsx")) {
        println("Specify a source file in XLSX format")
        exitProcess(1)
    }


    val fis = FileInputStream(args[0])
    val workbook = XSSFWorkbook(fis)
    val cfgReader = ConfigReader(workbook.getSheet("Config"))
    ItemReader(workbook.getSheet("Items"), Item.masterList)
    ShopReader(workbook.getSheet("Shops"))
    val marketGenerator = MarketGenerator(cfgReader.shopNum, cfgReader.generateLess)

    val console = BufferedReader(InputStreamReader(System.`in`))
    var choice: Int

    do {
        print(
            """Market Generator
            0) Quit
            1) Generate Market
            Please enter your choice: """.trimMargin()
        )
        choice = console.readLine()[0].code - ASCII_OFFSET

        when (choice) {
            0 -> exitProcess(0)
            1 -> {
                println("Select a region: " + Region.getListNumbers())
                val input = console.readLine()
                val selectedRegions = readRegions(input)
                val market = marketGenerator.generateMarket(selectedRegions)

                for (stall in market) {
                    println(stall)
                }
            }
            else -> println("Please enter a valid menu number.")
        }

    } while (true)
}

fun readRegions(input: String): List<Region> {
    val splitInput = splitInput(input)
    val convertedInput = splitInput.map { Integer.parseInt(it) }
    return convertedInput.map { Region.masterList[it] }
}

fun splitInput(input: String): List<String> {
    val splitInput = input.split(",")
    splitInput.forEach { it.trim() }
    return splitInput
}
