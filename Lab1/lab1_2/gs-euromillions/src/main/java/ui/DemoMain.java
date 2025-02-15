package ui;

import euromillions.CuponEuromillions;
import euromillions.Dip;
import euromillions.EuromillionsDraw;

public class DemoMain {

    /**
     * demonstrates a client for ramdom euromillions bets
     */
    public static void main(String[] args) {

        // prepare a cupon with three random bets
        CuponEuromillions thisWeek = new CuponEuromillions();
        System.out.println("Betting with three random bets...");
        thisWeek.addDipToCuppon( Dip.generateRandomDip());
        thisWeek.addDipToCuppon( Dip.generateRandomDip());
        thisWeek.addDipToCuppon( Dip.generateRandomDip());

        // simulate a random draw
        EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();

        //report results
        System.out.println("You played:");
        System.out.println(thisWeek.format());

        System.out.println("Draw results:");
        System.out.println(draw.getDrawResults().format());

        System.out.println("Your score:");
        for (Dip dip : draw.findMatches(thisWeek)) {
            System.out.println(dip.format());

        }
    }
}
