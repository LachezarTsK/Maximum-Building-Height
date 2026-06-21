
/**
 * @param {number} numberOfBuildings
 * @param {number[][]} restrictions
 * @return {number}
 */
var maxBuilding = function (numberOfBuildings, restrictions) {
    if (restrictions.length === 0) {
        return numberOfBuildings - 1;
    }
    sortAndUpdateRestrictionsWithAllConstraintRules(restrictions);
    return findMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions);
};

const FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO = 2;

/**
 * @param {number[][]} restrictions
 * @return {void}
 */
function sortAndUpdateRestrictionsWithAllConstraintRules(restrictions) {
    restrictions.sort((x, y) => x[0] - y[0]);
    restrictions[0][1] = Math.min(restrictions[0][1], restrictions[0][0] - 1);

    for (let i = 1; i < restrictions.length; ++i) {
        restrictions[i][1] = Math.min(restrictions[i][1], restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]));
    }
    for (let i = restrictions.length - 2; i >= 0; --i) {
        restrictions[i][1] = Math.min(restrictions[i][1], restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]));
    }
}

/**
 * @param {number} numberOfBuildings
 * @param {number[][]} restrictions
 * @return {number}
 */
function findMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions) {
    let maxHeight = restrictions[0][1];
    for (let i = 1; i < restrictions.length; ++i) {
        const potentialPeakBetweenTwoBuildings
                = Math.floor(((restrictions[i][0] - restrictions[i - 1][0])
                - Math.abs(restrictions[i][1] - restrictions[i - 1][1])) / 2);
        const currentHeight = potentialPeakBetweenTwoBuildings + Math.max(restrictions[i - 1][1], restrictions[i][1]);
        maxHeight = Math.max(maxHeight, currentHeight);
    }

    if (restrictions[0][0] > FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO) {
        const potentialPeakBetweenTwoBuildings = Math.floor((restrictions[0][0] - 1) / 2);
        const currentHeight = potentialPeakBetweenTwoBuildings + restrictions[0][1];
        maxHeight = Math.max(maxHeight, currentHeight);
    }

    if (restrictions[restrictions.length - 1][0] < numberOfBuildings) {
        const potentialPeakBetweenTwoBuildings = numberOfBuildings - restrictions[restrictions.length - 1][0];
        const currentHeight = potentialPeakBetweenTwoBuildings + restrictions[restrictions.length - 1][1];
        maxHeight = Math.max(maxHeight, currentHeight);
    }

    return maxHeight;
}
