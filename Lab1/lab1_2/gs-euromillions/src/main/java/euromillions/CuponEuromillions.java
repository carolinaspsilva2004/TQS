package euromillions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * models a set of several dips, as submitted by a player
 *
 * @author ico0
 */
public class CuponEuromillions implements Iterable<Dip> {

    private final ArrayList<Dip> luckyDipsCollection = new ArrayList<>();

    public void addDipToCuppon(Dip dip) {
        luckyDipsCollection.add(dip);
    }

    public int countDips() {
        return luckyDipsCollection.size();

    }

    public Dip getDipByIndex(int order) {

        return luckyDipsCollection.get(order);
    }

    /**
     * @return a formatted string with the data contained
     */
    public String format() {
        StringBuilder sb = new StringBuilder();
        int dipIndex = 1;
        for (Dip dip : this) {
            sb.append(String.format("Dip #%d:", dipIndex++));
            sb.append(dip.format());
            sb.append("\n");
        }

        return sb.toString();

    }

    @Override
    public Iterator<Dip> iterator() {
        return luckyDipsCollection.iterator();
    }
}
