
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

class Solution {

    private companion object {
        const val FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO = 2
    }

    fun maxBuilding(numberOfBuildings: Int, restrictions: Array<IntArray>): Int {
        if (restrictions.isEmpty()) {
            return numberOfBuildings - 1
        }
        sortAndUpdateRestrictionsWithAllConstraintRules(restrictions)
        return findMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions)
    }

    private fun sortAndUpdateRestrictionsWithAllConstraintRules(restrictions: Array<IntArray>) {
        restrictions.sortWith { x, y -> x[0] - y[0] }
        restrictions[0][1] = min(restrictions[0][1], restrictions[0][0] - 1)

        for (i in 1..<restrictions.size) {
            restrictions[i][1] = min(restrictions[i][1], restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]))
        }
        for (i in restrictions.size - 2 downTo (0)) {
            restrictions[i][1] = min(restrictions[i][1], restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]))
        }
    }

    private fun findMaxPossibleHeightOfBuilding(numberOfBuildings: Int, restrictions: Array<IntArray>): Int {
        var maxHeight = restrictions[0][1]
        for (i in 1..<restrictions.size) {
            val potentialPeakBetweenTwoBuildings = ((restrictions[i][0] - restrictions[i - 1][0])
                                                 - abs(restrictions[i][1] - restrictions[i - 1][1])) / 2
            val currentHeight = potentialPeakBetweenTwoBuildings + max(restrictions[i - 1][1], restrictions[i][1])
            maxHeight = max(maxHeight, currentHeight)
        }

        if (restrictions[0][0] > FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO) {
            val potentialPeakBetweenTwoBuildings = (restrictions[0][0] - 1) / 2
            val currentHeight = potentialPeakBetweenTwoBuildings + restrictions[0][1]
            maxHeight = max(maxHeight, currentHeight)
        }

        if (restrictions[restrictions.size - 1][0] < numberOfBuildings) {
            val potentialPeakBetweenTwoBuildings = numberOfBuildings - restrictions[restrictions.size - 1][0]
            val currentHeight = potentialPeakBetweenTwoBuildings + restrictions[restrictions.size - 1][1]
            maxHeight = max(maxHeight, currentHeight)
        }

        return maxHeight
    }
}
