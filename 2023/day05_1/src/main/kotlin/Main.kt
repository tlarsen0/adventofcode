import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 5, Part 1 starting!!!")

    File(args[0]).forEachLine {
        parseLine(it)
    }

    println("Seeds: ${allSeeds.seeds}")
    println("Seeds To Soil count           : ${allSeedToSoil.sourceToDestination.size}")
    println("Soil to Fertilizer count      : ${allSoilToFertilizer.sourceToDestination.size}")
    println("Fertilizer to Water count     : ${allFertilizerToWater.sourceToDestination.size}")
    println("Water to Light count          : ${allWaterToLight.sourceToDestination.size}")
    println("Light to Temperature count    : ${allLightToTemperature.sourceToDestination.size}")
    println("Temperature to Humidity count : ${allTemperatureToHumidity.sourceToDestination.size}")
    println("Humidity to Location count    : ${allHumidityToLocation.sourceToDestination.size}")

    var finalAnswer = 0L
    for(seed in allSeeds.seeds) {
        val answer = calculateAnswer(seed)

        if((finalAnswer == 0L) || (answer < finalAnswer)) {
            finalAnswer = answer
        }
    }

    println("Final answer: $finalAnswer")

    println("AOC 2023, Day 5, Part 1 completed!!!")
}

fun calculateAnswer(seed:Long):Long {
    return allHumidityToLocation.getMap(
        allTemperatureToHumidity.getMap(
            allLightToTemperature.getMap(
                allWaterToLight.getMap(
                    allFertilizerToWater.getMap(
                        allSoilToFertilizer.getMap(
                            allSeedToSoil.getMap(seed)
                        )
                    )
                )
            )
        )
    )
}

var lastMatch:String = String()

enum class PlantingOptions(val theName:String) {
    SEEDS("seeds:"),
    SEED_TO_SOIL_MAP("seed-to-soil map:"),
    SOIL_TO_FERTILIZER_MAP("soil-to-fertilizer map:"),
    FERTILIZER_TO_WATER_MAP("fertilizer-to-water map:"),
    WATER_TO_LIGHT_MAP("water-to-light map:"),
    LIGHT_TO_TEMPERATURE_MAP("light-to-temperature map:"),
    TEMPERATURE_TO_HUMIDITY_MAP("temperature-to-humidity map:"),
    HUMIDITY_TO_LOCATION_MAP("humidity-to-location map:")
}

class Seeds {
    val seeds:ArrayList<Long> = ArrayList()
}

data class InfoDataSet(val destinationRange:Long, val sourceRange:Long, val range:Long)

open class MyDataSet {
    val sourceToDestination = ArrayList<InfoDataSet>()

    fun getMap(v: Long): Long {
        for(sToD in sourceToDestination) {
            if ((v > sToD.sourceRange) && (v <= (sToD.sourceRange + sToD.range - 1))) {
                val offset = v - sToD.sourceRange
                return sToD.destinationRange + offset
            }
        }

        return v
    }
}

class SeedToSoil : MyDataSet()

class SoilToFertilizer : MyDataSet()

class FertilizerToWater : MyDataSet()

class WaterToLight : MyDataSet()

class LightToTemperature : MyDataSet()

class TemperatureToHumidity : MyDataSet()

class HumidityToLocation : MyDataSet()


val allSeeds = Seeds()
val allSeedToSoil = SeedToSoil()
val allSoilToFertilizer = SoilToFertilizer()
val allFertilizerToWater = FertilizerToWater()
val allWaterToLight = WaterToLight()
val allLightToTemperature = LightToTemperature()
val allTemperatureToHumidity = TemperatureToHumidity()
val allHumidityToLocation = HumidityToLocation()

fun parseLine(line:String) {
    if(line.isEmpty()) {
        // if line is blank, skip
        lastMatch = String()
        return
    }

    if((lastMatch.compareTo(PlantingOptions.SEEDS.theName) == 0) || (line.substring(0, 6).compareTo(PlantingOptions.SEEDS.theName) == 0)) {
        lastMatch = PlantingOptions.SEEDS.theName
        for(c in line.split(' ')) {
            if(c.compareTo(PlantingOptions.SEEDS.theName) == 0) {
                continue
            }
            allSeeds.seeds.add(c.toLong())
        }
        return
    }

    if((lastMatch.compareTo(PlantingOptions.SEED_TO_SOIL_MAP.theName) == 0) || (line.compareTo(PlantingOptions.SEED_TO_SOIL_MAP.theName) == 0)) {
        if (lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.SEED_TO_SOIL_MAP.theName
            return
        }
        val split = line.split(' ')
        allSeedToSoil.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        return
    }

    if((lastMatch.compareTo(PlantingOptions.SOIL_TO_FERTILIZER_MAP.theName) == 0) || (line.compareTo(PlantingOptions.SOIL_TO_FERTILIZER_MAP.theName) == 0)) {
        if(lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.SOIL_TO_FERTILIZER_MAP.theName
        } else {
            val split = line.split(' ')
            allSoilToFertilizer.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        }
    } else if ((lastMatch.compareTo(PlantingOptions.FERTILIZER_TO_WATER_MAP.theName) == 0) || (line.compareTo(PlantingOptions.FERTILIZER_TO_WATER_MAP.theName) == 0)) {
        if(lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.FERTILIZER_TO_WATER_MAP.theName
        } else {
            val split = line.split(' ')
            allFertilizerToWater.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        }
    }

    if ((lastMatch.compareTo(PlantingOptions.WATER_TO_LIGHT_MAP.theName) == 0) || (line.compareTo(PlantingOptions.WATER_TO_LIGHT_MAP.theName) == 0)) {
        if(lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.WATER_TO_LIGHT_MAP.theName
            return
        }
        val split = line.split(' ')
        allWaterToLight.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        return
    }

    if((lastMatch.compareTo(PlantingOptions.LIGHT_TO_TEMPERATURE_MAP.theName) == 0) || (line.compareTo(PlantingOptions.LIGHT_TO_TEMPERATURE_MAP.theName) == 0)) {
        if(lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.LIGHT_TO_TEMPERATURE_MAP.theName
            return
        }
        val split = line.split(' ')
        allLightToTemperature.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        return
    }

    if((lastMatch.compareTo(PlantingOptions.TEMPERATURE_TO_HUMIDITY_MAP.theName) == 0) || (line.compareTo(PlantingOptions.TEMPERATURE_TO_HUMIDITY_MAP.theName) == 0)) {
        if(lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.TEMPERATURE_TO_HUMIDITY_MAP.theName
            return
        }
        val split = line.split(' ')
        allTemperatureToHumidity.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        return
    }

    if((lastMatch.compareTo(PlantingOptions.HUMIDITY_TO_LOCATION_MAP.theName) == 0) || (line.compareTo(PlantingOptions.HUMIDITY_TO_LOCATION_MAP.theName) == 0)) {
        if(lastMatch.isEmpty()) {
            lastMatch = PlantingOptions.HUMIDITY_TO_LOCATION_MAP.theName
            return
        }
        val split = line.split(' ')
        allHumidityToLocation.sourceToDestination.add(InfoDataSet(split[0].toLong(), split[1].toLong(), split[2].toLong()))
        return
    }
}