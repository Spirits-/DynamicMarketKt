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
## Examples
```
CONFIG LOADED
            REGIONS: [Paperkind Region, Dusgar, Mirrimam, Bazaar Below, Yackrix, Shattered Island, Underix, Orterix, Ascensia, Baktix, Wortsmar, Kroaka, Tralarry, Coar, Granvidas, Flamka, Dyoist, Gryist, Nyuist, Phoist, Elven Region, Ghewerk, Aerether, Genia, Raskatir, Consake, Helta, Crisol, Faskir, Lenesis, Underground, Talron]
            CONSTANTS:
                MIN STALLS PER MARKET: 8
                MAX STALLS PER MARKET: 8
                SPECIAL ITEMS NUMBER: 2
Market Generator
            0) Quit
            1) Generate Market
            Please enter your choice: 1
Select a region: (0) Paperkind Region (1) Dusgar (2) Mirrimam (3) Bazaar Below (4) Yackrix (5) Shattered Island (6) Underix (7) Orterix (8) Ascensia (9) Baktix (10) Wortsmar (11) Kroaka (12) Tralarry (13) Coar (14) Granvidas (15) Flamka (16) Dyoist (17) Gryist (18) Nyuist (19) Phoist (20) Elven Region (21) Ghewerk (22) Aerether (23) Genia (24) Raskatir (25) Consake (26) Helta (27) Crisol (28) Faskir (29) Lenesis (30) Underground (31) Talron 
0,5,12
Shop: drinkmaker SPECIAL: false
	red wine x13
	black gold teabags x6
	karmakh x5

Shop: grocers SPECIAL: false
	broccoli x17
	cabbage x9
	eggplant x6

Shop: baker SPECIAL: false
	crackers x41
	sponge cake x2
	scones x23

Shop: trinket seller SPECIAL: true
	spices x10
	spider preserved in amber x5
	congo tourmaline necklace x1
	bag of diamond dust x6
	gemstone ladybug x1
	golden ring x1

Shop: blacksmith SPECIAL: false
	tracking arrow x4
	tanglefoot arrow x2
	longsword x1
	dagger x7

Shop: adventurer's friend SPECIAL: true
	bear trap x1
	whetstone x1
	10 feet rope x3
	tinderboxes x10
	large tent x2

Shop: traveller SPECIAL: false
	angelfruit2 x17
	dense rations x18
	world map x3
	info reserve x1

Shop: alchemist SPECIAL: true
	wound disinfectant x6
	batterum x1
	black adder venom x19
	hunter urchin venom x13
	deathblade x29
```
