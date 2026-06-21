
package main

import (
    "math"
    "slices"
)

const FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO = 2

func maxBuilding(numberOfBuildings int, restrictions [][]int) int {
    if len(restrictions) == 0 {
        return numberOfBuildings - 1
    }
    sortAndUpdateRestrictionsWithAllConstraintRules(restrictions)
    return findMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions)
}

func sortAndUpdateRestrictionsWithAllConstraintRules(restrictions [][]int) {
    slices.SortFunc(restrictions, func(x []int, y []int) int { return x[0] - y[0] })
    restrictions[0][1] = min(restrictions[0][1], restrictions[0][0] - 1)

    for i := 1; i < len(restrictions); i++ {
        restrictions[i][1] = min(restrictions[i][1], restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]))
    }
    for i := len(restrictions) - 2; i >= 0; i-- {
        restrictions[i][1] = min(restrictions[i][1], restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]))
    }
}

func findMaxPossibleHeightOfBuilding(numberOfBuildings int, restrictions [][]int) int {
    maxHeight := restrictions[0][1]
    for i := 1; i < len(restrictions); i++ {
        potentialPeakBetweenTwoBuildings := ((restrictions[i][0] - restrictions[i - 1][0]) -
                                            int(math.Abs(float64(restrictions[i][1] - restrictions[i - 1][1])))) / 2
        currentHeight := potentialPeakBetweenTwoBuildings + max(restrictions[i - 1][1], restrictions[i][1])
        maxHeight = max(maxHeight, currentHeight)
    }

    if restrictions[0][0] > FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO {
        potentialPeakBetweenTwoBuildings := (restrictions[0][0] - 1) / 2
        currentHeight := potentialPeakBetweenTwoBuildings + restrictions[0][1]
        maxHeight = max(maxHeight, currentHeight)
    }

    if restrictions[len(restrictions) - 1][0] < numberOfBuildings {
        potentialPeakBetweenTwoBuildings := numberOfBuildings - restrictions[len(restrictions) - 1][0]
        currentHeight := potentialPeakBetweenTwoBuildings + restrictions[len(restrictions) - 1][1]
        maxHeight = max(maxHeight, currentHeight)
    }

    return maxHeight
}
