/*
 * ════════════════════════════════════════════════════════════
 *  ✅ SOLUTIONS: Classes & Objects
 * ════════════════════════════════════════════════════════════
 *
 *  Run this file to verify all solutions pass:
 *
 *    javac Solution.java && java Solution
 *
 * ════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;

/*
 * ─────────────────────────────────────────────
 * SOLUTION 1: Rectangle Class
 * ─────────────────────────────────────────────
 */

class Rectangle {
    double width;
    double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    double getArea() {
        return this.width * this.height;
    }

    double getPerimeter() {
        return 2 * (this.width + this.height);
    }

    boolean isSquare() {
        return this.width == this.height;
    }

    @Override
    public String toString() {
        // Format as integers if they are whole numbers
        String w = (this.width == Math.floor(this.width)) ? String.valueOf((int) this.width) : String.valueOf(this.width);
        String h = (this.height == Math.floor(this.height)) ? String.valueOf((int) this.height) : String.valueOf(this.height);
        return "Rectangle(" + w + " x " + h + ")";
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 2: TodoItem Class
 * ─────────────────────────────────────────────
 */

class TodoItem {
    private boolean completed = false;
    final int id;
    String title;

    TodoItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    boolean isCompleted() {
        return this.completed;
    }

    void toggle() {
        this.completed = !this.completed;
    }

    void complete() {
        this.completed = true;
    }

    @Override
    public String toString() {
        String icon = this.completed ? "✅" : "⬜";
        return "[" + icon + "] " + this.title;
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 3: Stopwatch Class
 * ─────────────────────────────────────────────
 */

class Stopwatch {
    private boolean running = false;
    private long startTime = 0;
    private long accumulated = 0;

    boolean isRunning() {
        return this.running;
    }

    long getElapsedMs() {
        if (this.running) {
            return this.accumulated + (System.currentTimeMillis() - this.startTime);
        }
        return this.accumulated;
    }

    void start() {
        if (this.running) {
            throw new IllegalArgumentException("Stopwatch is already running!");
        }
        this.running = true;
        this.startTime = System.currentTimeMillis();
    }

    void stop() {
        if (!this.running) {
            throw new IllegalArgumentException("Stopwatch is not running!");
        }
        this.accumulated += System.currentTimeMillis() - this.startTime;
        this.running = false;
    }

    void reset() {
        if (this.running) {
            throw new IllegalArgumentException("Cannot reset while running!");
        }
        this.accumulated = 0;
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 4: Inventory (Static Members)
 * ─────────────────────────────────────────────
 */

class InventoryItem {
    private static List<InventoryItem> items = new ArrayList<>();
    private static int nextId = 1;

    final int id;
    String name;
    int quantity;
    double price;

    InventoryItem(String name, int quantity, double price) {
        this.id = InventoryItem.nextId++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        InventoryItem.items.add(this);
    }

    static List<InventoryItem> getAll() {
        return new ArrayList<>(InventoryItem.items);
    }

    static double getTotalValue() {
        double total = 0;
        for (InventoryItem item : InventoryItem.items) {
            total += item.quantity * item.price;
        }
        return total;
    }

    static InventoryItem findByName(String name) {
        for (InventoryItem item : InventoryItem.items) {
            if (item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    static int getCount() {
        return InventoryItem.items.size();
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 5: Temperature Converter Class
 * ─────────────────────────────────────────────
 */

class TemperatureConverter {
    private double celsius;

    TemperatureConverter(double celsius) {
        setCelsius(celsius); // Use setter for validation
    }

    double getCelsius() {
        return this.celsius;
    }

    void setCelsius(double value) {
        if (value < -273.15) {
            throw new IllegalArgumentException(
                "Temperature cannot be below absolute zero (-273.15°C)!");
        }
        this.celsius = value;
    }

    double getFahrenheit() {
        return (this.celsius * 9) / 5 + 32;
    }

    void setFahrenheit(double value) {
        setCelsius((value - 32) * 5.0 / 9.0); // Use setCelsius for validation
    }

    double getKelvin() {
        return this.celsius + 273.15;
    }

    void setKelvin(double value) {
        setCelsius(value - 273.15); // Use setCelsius for validation
    }

    static boolean isFreezing(double celsius) {
        return celsius <= 0;
    }

    static boolean isBoiling(double celsius) {
        return celsius >= 100;
    }
}

// ─────────────────────────────────────────────
// Main class with test runner
// ─────────────────────────────────────────────
public class Solution {
    static int testCount = 0;
    static int passCount = 0;

    static void test(String description, boolean condition) {
        testCount++;
        if (condition) {
            passCount++;
            System.out.println("  ✅ " + description);
        } else {
            System.out.println("  ❌ " + description);
        }
    }

    public static void main(String[] args) {

        // ═══ Exercise 1: Rectangle ═══
        System.out.println("═══ Exercise 1: Rectangle ═══");

        Rectangle rect1 = new Rectangle(10, 5);
        test("Area of 10x5 = 50", rect1.getArea() == 50);
        test("Perimeter of 10x5 = 30", rect1.getPerimeter() == 30);
        test("10x5 is not a square", rect1.isSquare() == false);
        test("toString = \"Rectangle(10 x 5)\"",
                rect1.toString().equals("Rectangle(10 x 5)"));

        Rectangle square = new Rectangle(7, 7);
        test("7x7 is a square", square.isSquare() == true);
        test("Area of 7x7 = 49", square.getArea() == 49);

        // ═══ Exercise 2: TodoItem ═══
        System.out.println("\n═══ Exercise 2: TodoItem ═══");

        TodoItem todo1 = new TodoItem(1, "Learn Java");
        test("Todo starts uncompleted", todo1.isCompleted() == false);
        test("toString shows \"[⬜] Learn Java\"",
                todo1.toString().equals("[⬜] Learn Java"));

        todo1.toggle();
        test("After toggle, completed = true", todo1.isCompleted() == true);
        test("toString shows \"[✅] Learn Java\"",
                todo1.toString().equals("[✅] Learn Java"));

        todo1.toggle();
        test("After second toggle, completed = false", todo1.isCompleted() == false);

        todo1.complete();
        test("After complete(), completed = true", todo1.isCompleted() == true);

        test("id is 1", todo1.id == 1);

        // ═══ Exercise 3: Stopwatch ═══
        System.out.println("\n═══ Exercise 3: Stopwatch ═══");

        Stopwatch sw = new Stopwatch();
        test("Stopwatch starts not running", sw.isRunning() == false);
        test("Initial elapsed = 0", sw.getElapsedMs() == 0);

        sw.start();
        test("After start, isRunning = true", sw.isRunning() == true);

        // Try starting again — should throw
        boolean threwOnDoubleStart = false;
        try { sw.start(); } catch (IllegalArgumentException e) { threwOnDoubleStart = true; }
        test("Cannot start when already running", threwOnDoubleStart == true);

        // Wait a tiny bit and stop
        long waitStart = System.currentTimeMillis();
        while (System.currentTimeMillis() - waitStart < 50) {} // busy wait ~50ms
        sw.stop();
        test("After stop, isRunning = false", sw.isRunning() == false);
        test("Elapsed > 0 after running", sw.getElapsedMs() > 0);
        System.out.println("  ⏱ Elapsed time: " + sw.getElapsedMs() + " ms");

        // Try stopping again — should throw
        boolean threwOnDoubleStop = false;
        try { sw.stop(); } catch (IllegalArgumentException e) { threwOnDoubleStop = true; }
        test("Cannot stop when not running", threwOnDoubleStop == true);

        sw.reset();
        test("After reset, elapsed = 0", sw.getElapsedMs() == 0);

        // ═══ Exercise 4: Inventory ═══
        System.out.println("\n═══ Exercise 4: Inventory ═══");

        InventoryItem item1 = new InventoryItem("Laptop", 5, 999.99);
        InventoryItem item2 = new InventoryItem("Mouse", 20, 29.99);
        InventoryItem item3 = new InventoryItem("Keyboard", 15, 79.99);

        test("Item 1 has id 1", item1.id == 1);
        test("Item 2 has id 2", item2.id == 2);
        test("Item 3 has id 3", item3.id == 3);
        test("Total items = 3", InventoryItem.getCount() == 3);

        double totalValue = 5 * 999.99 + 20 * 29.99 + 15 * 79.99;
        test("Total value is correct",
                Math.abs(InventoryItem.getTotalValue() - totalValue) < 0.01);

        InventoryItem found = InventoryItem.findByName("mouse");
        test("Find by name (case-insensitive)",
                found != null && found.name.equals("Mouse"));

        InventoryItem notFound = InventoryItem.findByName("tablet");
        test("Returns null for missing item", notFound == null);

        // ═══ Exercise 5: Temperature Converter ═══
        System.out.println("\n═══ Exercise 5: Temperature Converter ═══");

        TemperatureConverter t = new TemperatureConverter(100);
        test("100°C = 212°F", t.getFahrenheit() == 212);
        test("100°C = 373.15K", Math.abs(t.getKelvin() - 373.15) < 0.01);

        t.setFahrenheit(32);
        test("32°F = 0°C", Math.abs(t.getCelsius()) < 0.01);

        t.setKelvin(0);
        test("0K = -273.15°C", Math.abs(t.getCelsius() - (-273.15)) < 0.01);

        boolean threwOnInvalid = false;
        try { t.setCelsius(-300); } catch (IllegalArgumentException e) { threwOnInvalid = true; }
        test("Throws on below absolute zero", threwOnInvalid == true);

        test("0°C is freezing", TemperatureConverter.isFreezing(0) == true);
        test("100°C is boiling", TemperatureConverter.isBoiling(100) == true);
        test("25°C is not freezing", TemperatureConverter.isFreezing(25) == false);

        // ─────────────────────────────────────────────
        // RESULTS
        // ─────────────────────────────────────────────
        System.out.println("\n════════════════════════════════════");
        System.out.println("  Results: " + passCount + "/" + testCount + " tests passed");
        if (passCount == testCount) {
            System.out.println("  🎉 All tests passed! Great job!");
        } else {
            System.out.println("  ⚠️  Some tests failed. Check the solutions above.");
        }
        System.out.println("════════════════════════════════════\n");
    }
}
