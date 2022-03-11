# DynamicMarketKt
A command-line-interface (CLI) project for generating markets for Tabletop RPGs (TTRPGs) like Dungeons & Dragons. 
It takes input from an excel spread-sheet for the types of possible stalls, items and regions.

It supports regional stores that roll items from special regional stock lists while having global stock lists. It also supports 
special stalls that add a new pool of items (consider a store-owner following specific religion or cult, etc.)

# Prerequisites
Java 16 for execution

Kotlin for building

## Usage

`java -jar DynamicMarket.jar [PATH-TO-SHEET]`

The provided sheet must be properly formatted or it will not work. **See the provided .xlsx file in the repo for a sample source file.** 
Every page must be terminated with an `X` in the first cell of the last line.

### Items

The Item page is your masterlist. They consist of a name, a minimum amount, maxium amount and the boolean flag if they can be rolled multiple times. For an item to exist for the program (excluding special items), it must be in that list. Item names must be unique.

#### Special Items

Items can be prefixed with `SPC_` for the program to recognize it as a special item, in which case it will jump to the separate sheet and roll an item from there. This function is useful if you want to have a single item in multiple varieties but not clog up the masterlist and therefore the chance of rolling said item. Special variants do not have to be in the masterlist. The separate special item pages have the same structure as the Item page.

### Shops

The Shop page is where you define your shops. They have a (unique) name followed by the global stock that can always be rolled, followed by the possible regions where the shop can appear ("all" is possible for all regions), special chance and stock and then regional stocks.

### Config

The first page of the file is the config which edits global constants. Here you have to input your regions, how many stalls you want generated per market (if you want the number to be constant, make min and max the same) and how many special items you want a stall to have should it be rolled as special.
