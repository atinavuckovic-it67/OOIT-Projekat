package test;

import model.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShapeTest {

    public static void main(String[] args) {
        System.out.println("Running Shape Unit Tests...");
        int passed = 0;
        int total = 0;

        total++;
        Donut donut1 = new Donut(new Point(100, 100), 50, 20, Color.BLACK, Color.RED);
        double expectedArea = Math.PI * (50 * 50 - 20 * 20);
        if (Math.abs(donut1.area() - expectedArea) < 0.0001) {
            System.out.println("[PASS] Donut area calculation: " + donut1.area());
            passed++;
        } else {
            System.err.println("[FAIL] Donut area calculation expected " + expectedArea + " but got " + donut1.area());
        }

        total++;
        boolean insideRing = donut1.contains(130, 100);
        boolean insideHole = donut1.contains(105, 100);
        if (insideRing && !insideHole) {
            System.out.println("[PASS] Donut contains point logic.");
            passed++;
        } else {
            System.err.println("[FAIL] Donut contains point logic failed. Ring: " + insideRing + ", Hole: " + insideHole);
        }

        total++;
        Donut donut2 = new Donut(new Point(0, 0), 100, 10);
        Donut donut3 = new Donut(new Point(0, 0), 30, 5);

        List<Donut> donuts = new ArrayList<>();
        donuts.add(donut1);
        donuts.add(donut2);
        donuts.add(donut3);

        Collections.sort(donuts, new Comparator<Donut>() {
            @Override
            public int compare(Donut d1, Donut d2) {
                return Double.compare(d2.area(), d1.area());
            }
        });

        if (donuts.get(0) == donut2 && donuts.get(1) == donut1 && donuts.get(2) == donut3) {
            System.out.println("[PASS] Descending area sorting.");
            passed++;
        } else {
            System.err.println("[FAIL] Descending area sorting failed.");
        }

        total++;
        Rectangle rect = new Rectangle(new Point(10, 10), 100, 50);
        if (rect.area() == 5000 && rect.contains(50, 30) && !rect.contains(5, 5)) {
            System.out.println("[PASS] Rectangle area and contains logic.");
            passed++;
        } else {
            System.err.println("[FAIL] Rectangle logic failed.");
        }

        System.out.println("\nResults: " + passed + "/" + total + " tests passed.");
        if (passed != total) {
            System.exit(1);
        }
    }
}
