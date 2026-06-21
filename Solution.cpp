
#include <cmath>
#include <ranges>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {

    static const int FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO = 2;

public:
    int maxBuilding(int numberOfBuildings, vector<vector<int>>& restrictions) {
        if (restrictions.size() == 0) {
            return numberOfBuildings - 1;
        }
        sortAndUpdateRestrictionsWithAllConstraintRules(restrictions);
        return findMaxPossibleHeightOfBuilding(numberOfBuildings, restrictions);
    }

private:
    void sortAndUpdateRestrictionsWithAllConstraintRules(span<vector<int>> restrictions) {
        ranges::sort(restrictions, [](const auto& x, const auto& y) {return x[0] < y[0]; });
        restrictions[0][1] = min(restrictions[0][1], restrictions[0][0] - 1);

        for (int i = 1; i < restrictions.size(); ++i) {
            restrictions[i][1] = min(restrictions[i][1], restrictions[i - 1][1] + (restrictions[i][0] - restrictions[i - 1][0]));
        }
        for (int i = restrictions.size() - 2; i >= 0; --i) {
            restrictions[i][1] = min(restrictions[i][1], restrictions[i + 1][1] + (restrictions[i + 1][0] - restrictions[i][0]));
        }
    }

    int findMaxPossibleHeightOfBuilding(int numberOfBuildings, span<const vector<int>> restrictions) {
        int maxHeight = restrictions[0][1];
        for (int i = 1; i < restrictions.size(); ++i) {
            int potentialPeakBetweenTwoBuildings
                    = ((restrictions[i][0] - restrictions[i - 1][0])
                    - abs(restrictions[i][1] - restrictions[i - 1][1])) / 2;
            int currentHeight = potentialPeakBetweenTwoBuildings + max(restrictions[i - 1][1], restrictions[i][1]);
            maxHeight = max(maxHeight, currentHeight);
        }

        if (restrictions[0][0] > FIRST_BUILDING_WITH_POTENTIAL_HEIGHT_GREATER_THAN_ZERO) {
            int potentialPeakBetweenTwoBuildings = (restrictions[0][0] - 1) / 2;
            int currentHeight = potentialPeakBetweenTwoBuildings + restrictions[0][1];
            maxHeight = max(maxHeight, currentHeight);
        }

        if (restrictions[restrictions.size() - 1][0] < numberOfBuildings) {
            int potentialPeakBetweenTwoBuildings = numberOfBuildings - restrictions[restrictions.size() - 1][0];
            int currentHeight = potentialPeakBetweenTwoBuildings + restrictions[restrictions.size() - 1][1];
            maxHeight = max(maxHeight, currentHeight);
        }

        return maxHeight;
    }
};
