
import java.util.Arrays;

public class Solution {

    private static final int FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO = 2;

    public int maxBuilding(int numberOfBuildings, int[][] restrictions) {
        if (restrictions.length == 0) {
            return numberOfBuildings - 1;
        }
        sortAndUpdateRestrictionsWithAllConstraintRules(restrictions);
        return findMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions);
    }

    private void sortAndUpdateRestrictionsWithAllConstraintRules(int[][] restrictions) {
        Arrays.sort(restrictions, (x, y) -> x[0] - y[0]);
        restrictions[0][1] = Math.min(restrictions[0][1], restrictions[0][0] - 1);

        for (int i = 1; i < restrictions.length; ++i) {
            restrictions[i][1] = Math.min(restrictions[i][1], restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]));
        }
        for (int i = restrictions.length - 2; i >= 0; --i) {
            restrictions[i][1] = Math.min(restrictions[i][1], restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]));
        }
    }

    private int findMaxPossibleHeightOfBuilding(int numberOfBuildings, int[][] restrictions) {
        int maxHeight = restrictions[0][1];
        for (int i = 1; i < restrictions.length; ++i) {
            int potentialPeakBetweenTwoBuildings
                    = ((restrictions[i][0] - restrictions[i - 1][0])
                    - Math.abs(restrictions[i][1] - restrictions[i - 1][1])) / 2;
            int currentHeight = potentialPeakBetweenTwoBuildings + Math.max(restrictions[i - 1][1], restrictions[i][1]);
            maxHeight = Math.max(maxHeight, currentHeight);
        }

        if (restrictions[0][0] > FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO) {
            int potentialPeakBetweenTwoBuildings = (restrictions[0][0] - 1) / 2;
            int currentHeight = potentialPeakBetweenTwoBuildings + restrictions[0][1];
            maxHeight = Math.max(maxHeight, currentHeight);
        }

        if (restrictions[restrictions.length - 1][0] < numberOfBuildings) {
            int potentialPeakBetweenTwoBuildings = numberOfBuildings - restrictions[restrictions.length - 1][0];
            int currentHeight = potentialPeakBetweenTwoBuildings + restrictions[restrictions.length - 1][1];
            maxHeight = Math.max(maxHeight, currentHeight);
        }

        return maxHeight;
    }
}
