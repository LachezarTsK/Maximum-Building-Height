
using System;

public class Solution
{
    private static readonly int FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO = 2;

    public int MaxBuilding(int numberOfBuildings, int[][] restrictions)
    {
        if (restrictions.Length == 0)
        {
            return numberOfBuildings - 1;
        }
        SortAndUpdateRestrictionsWithAllConstraintRules(restrictions);
        return FindMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions);
    }

    private void SortAndUpdateRestrictionsWithAllConstraintRules(int[][] restrictions)
    {
        Array.Sort(restrictions, (x, y) => x[0] - y[0]);
        restrictions[0][1] = Math.Min(restrictions[0][1], restrictions[0][0] - 1);

        for (int i = 1; i < restrictions.Length; ++i)
        {
            restrictions[i][1] = Math.Min(restrictions[i][1], restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]));
        }
        for (int i = restrictions.Length - 2; i >= 0; --i)
        {
            restrictions[i][1] = Math.Min(restrictions[i][1], restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]));
        }
    }

    private int FindMaxPossibleHeightOfBuilding(int numberOfBuildings, int[][] restrictions)
    {
        int maxHeight = restrictions[0][1];
        for (int i = 1; i < restrictions.Length; ++i)
        {
            int potentialPeakBetweenTwoBuildings
                    = ((restrictions[i][0] - restrictions[i - 1][0])
                    - Math.Abs(restrictions[i][1] - restrictions[i - 1][1])) / 2;
            int currentHeight = potentialPeakBetweenTwoBuildings + Math.Max(restrictions[i - 1][1], restrictions[i][1]);
            maxHeight = Math.Max(maxHeight, currentHeight);
        }

        if (restrictions[0][0] > FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO)
        {
            int potentialPeakBetweenTwoBuildings = (restrictions[0][0] - 1) / 2;
            int currentHeight = potentialPeakBetweenTwoBuildings + restrictions[0][1];
            maxHeight = Math.Max(maxHeight, currentHeight);
        }

        if (restrictions[restrictions.Length - 1][0] < numberOfBuildings)
        {
            int potentialPeakBetweenTwoBuildings = numberOfBuildings - restrictions[restrictions.Length - 1][0];
            int currentHeight = potentialPeakBetweenTwoBuildings + restrictions[restrictions.Length - 1][1];
            maxHeight = Math.Max(maxHeight, currentHeight);
        }

        return maxHeight;
    }
}
