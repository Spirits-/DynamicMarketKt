package data

import java.lang.IllegalArgumentException

data class Region(val name: String) {

    companion object {
        val masterList: MutableList<Region> = mutableListOf()

        fun getListNumbers(): String {
            val sb = StringBuilder()
            for (i in masterList.indices) {
                sb.append("(")
                sb.append(i)
                sb.append(") ")
                sb.append(masterList[i])
                sb.append(" ")
            }

            return sb.toString()
        }

        fun getRegionFromMasterList(name: String): Region {
            for (i in masterList) {
                if (name == i.name) {
                    return i
                }
            }
            throw IllegalArgumentException("Region $name not found in master list")
        }
    }

}