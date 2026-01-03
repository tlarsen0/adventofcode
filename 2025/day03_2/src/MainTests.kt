
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class MainTests {
    @Test
    @DisplayName("Test firstBatteryIndex")
    fun firstBatteryIndexTest() {
        val batteryIndexes = Array(12) { index -> index }
        assertEquals(0, firstBatteryIndex(batteryIndexes, 0))
        assertEquals(1, firstBatteryIndex(batteryIndexes, 1))
        assertEquals(2, firstBatteryIndex(batteryIndexes, 2))
        assertEquals(3, firstBatteryIndex(batteryIndexes, 3))
        assertEquals(11, firstBatteryIndex(batteryIndexes, 11))
    }

    @Test
    @DisplayName("Test lastBatteryIndex")
    fun lastBatteryIndexTest() {
        val batteryBank = "818181911112111"
        assertEquals(14, lastBatteryIndex(batteryBank, 11))
        assertEquals(13, lastBatteryIndex(batteryBank, 10))
        assertEquals(12, lastBatteryIndex(batteryBank, 9))
    }

    @Test
    @DisplayName("Test calculateMaxJoltage for 987654321111111")
    fun testCalculateMaxJoltage987654321111111() {
        val batteryBank = "987654321111111"
        assertEquals(987654321111, calculateMaxJoltage(batteryBank))
    }

    @Test
    @DisplayName("Test calculateMaxJoltage for value 818181911112111")
    fun testCalculateMaxJoltage818181911112111() {
        val batteryBank = "818181911112111"
        val maxJoltage = calculateMaxJoltage(batteryBank)
        assertEquals(888911112111, maxJoltage, "818181911112111 should have max joltage 888911112111")
    }

    @Test
    @DisplayName("Test calculateMaxJoltage for value 234234234234278")
    fun testCalculateMaxJoltage234234234234278() {
        val batteryBank = "234234234234278"
        assertEquals(434234234278, calculateMaxJoltage(batteryBank), "234234234234278 should have max Joltage 434234234278")
    }

    @Test
    @DisplayName("Test Real Input Line 1")
    fun testRealInput1Line() {
        val batteryBank = "2343453422641331233623444434443422422234243434644344344333436434324443344243444547343426444313413747"
        val maxJoltage = calculateMaxJoltage(batteryBank)
        assertEquals(764443413747, maxJoltage)
    }
}