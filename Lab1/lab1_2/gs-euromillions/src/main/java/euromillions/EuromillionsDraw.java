package euromillions;

import java.util.ArrayList;

/**
 * Models an Euromillions draw. Contains the solution and allows to find
 * matches.
 *
 * @author ico
 */
public class EuromillionsDraw {

    private final Dip drawResults;

    /**
     * Creates a new draw assuming the given solution.
     *
     * @param drawResults the given solution is used
     */
    public EuromillionsDraw(Dip drawResults) {
        this.drawResults = drawResults;
    }

    /**
     * Creates a new draw from a random solution.
     *
     * @return new instance
     */
    static public EuromillionsDraw generateRandomDraw() {
        return new EuromillionsDraw(Dip.generateRandomDip());
    }

    /**
     * Find the numbers and start matches.
     *
     * @param playCuppon the user sheet
     * @return an array of dips containing only the numbers and starts that
     * are also present in the draw
     */
    public ArrayList<Dip> findMatches(CuponEuromillions playCuppon) {

        ArrayList<Dip> results = new ArrayList<Dip>();
        Dip workingDip;

        for (Dip dip : playCuppon) {
            workingDip = new Dip();
            for (int number : dip.getNumbersColl()) {
                if (drawResults.getNumbersColl().contains(number)) {
                    workingDip.getNumbersColl().add(number);
                }
            }
            for (int start : dip.getStarsColl()) {
                if (drawResults.getStarsColl().contains(start)) {
                    workingDip.getStarsColl().add(start);
                }
            }
            results.add(workingDip);
        }
        return results;
    }

    public Dip getDrawResults() {
        return drawResults;
    }
}
