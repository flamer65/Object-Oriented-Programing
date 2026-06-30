/*
 * ════════════════════════════════════════════════════════════
 *  🏋️ EXERCISES: Classes & Objects
 * ════════════════════════════════════════════════════════════
 *
 *  Complete each exercise by replacing the TODO comments with
 *  your implementation. Compile and run this file with:
 *
 *    javac Exercise.java && java Exercise
 *
 *  If your implementation is correct, you'll see ✅ for each test.
 *  If something is wrong, you'll see ❌ with what went wrong.
 *
 * ════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.READER;

/*
 * ─────────────────────────────────────────────
 * EXERCISE 1: Rectangle Class
 * ─────────────────────────────────────────────
 *
 * Create a class called Rectangle with:
 * - Properties: width (double) and height (double)
 * - Constructor that takes width and height
 * - Method getArea()        → returns width × height
 * - Method getPerimeter()   → returns 2 × (width + height)
 * - Method isSquare()       → returns true if width equals height
 * - Method toString()       → returns "Rectangle(width x height)"
 *
 * HINT: Store width and height as fields assigned in the constructor.
 */

// TODO: Create the Rectangle class here
class Rectangle {
    double width;
    double height;
    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    double getArea() {
        // TODO: implement
        return this.width * this.height;
    }

    double getPerimeter() {
        // TODO: implement
        return 2 * (this.width + this.height);
    }

    boolean isSquare() {
        // TODO: implement
        return this.width == this.height;
    }

    @Override
    public String toString() {
        // TODO: implement — return "Rectangle(W x H)"
        return "Rectangle(" + this.width + " x " + this.height + ")";
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 2: TodoItem Class
 * ─────────────────────────────────────────────
 *
 * Create a class called TodoItem with:
 * - Private field completed (boolean, default: false)
 * - Final field id (int)
 * - Public field title (String)
 * - Method isCompleted()  → returns whether the todo is completed
 * - Method toggle()       → flips completed from true↔false
 * - Method complete()     → sets completed to true
 * - Method toString()     → returns "[✅] title" or "[⬜] title"
 *
 * HINT: Use final for id to prevent changes after creation.
 */

// TODO: Create the TodoItem class here
class TodoItem {
    private boolean completed = false;
    final int id;
    public String title;

    TodoItem(int id, String title) {
        // TODO: implement
        this.id = id;
        this.title = title;
    }

    boolean isCompleted() {
        // TODO: implement
       return completed;
    }

    void toggle() {
        // TODO: implement
        completed = !completed;
    }

    void complete() {
        // TODO: implement
        completed = true;
    }

    @Override
    public String toString() {
        // TODO: implement — return "[✅] title" or "[⬜] title"
        return (completed ? "[✅] " : "[⬜] ") + title;
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 3: Stopwatch Class
 * ─────────────────────────────────────────────
 *
 * Create a class called Stopwatch with:
 * - Private fields to track start time and accumulated elapsed time
 * - Private field running (boolean)
 * - Method isRunning()   → returns whether it's running
 * - Method start()       → starts the stopwatch
 *                          (throw IllegalArgumentException if already running)
 * - Method stop()        → stops it and accumulates elapsed time
 *                          (throw IllegalArgumentException if not running)
 * - Method reset()       → resets to 0
 *                          (throw IllegalArgumentException if currently running)
 * - Method getElapsedMs() → returns total elapsed time in milliseconds
 *
 * HINT: Use System.currentTimeMillis() to get the current time.
 * HINT: You'll need to track both accumulated time and the
 *       current run's start time.
 */

// TODO: Create the Stopwatch class here
class Stopwatch {
    private boolean running = false;
    private long startTime = 0;
    private long accumulated = 0;

    boolean isRunning() {
        // TODO: implement
        return running;
    }

    long getElapsedMs() {
        // TODO: implement
        return running ? System.currentTimeMillis() - startTime : accumulated;
    }

    void start() {
        // TODO: implement — throw IllegalArgumentException if already running
        if (running) {
            throw new IllegalArgumentException("Stopwatch is already running");
        }
        running = true;
        startTime = System.currentTimeMillis();
    }

    void stop() {
        // TODO: implement — throw IllegalArgumentException if not running
        if (!running) {
            throw new IllegalArgumentException("Stopwatch is not running");
        }
        running = false;
        accumulated += System.currentTimeMillis() - startTime;
    }

    void reset() {
        // TODO: implement — throw IllegalArgumentException if running
        if (running) {
            throw new IllegalArgumentException("Stopwatch is running");
        }
        accumulated = 0;
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 4: Inventory (Static Members)
 * ─────────────────────────────────────────────
 *
 * Create a class called InventoryItem with:
 * - Static field items (private List of all items)
 * - Static field nextId (private auto-incrementing ID)
 * - Instance field id (final, auto-assigned)
 * - Instance fields: name (String), quantity (int), price (double)
 * - Static method getAll()         → returns copy of all items
 * - Static method getTotalValue()  → returns sum of (quantity × price)
 * - Static method findByName(name) → finds item by name (case-insensitive)
 * - Static method getCount()       → returns number of items
 *
 * HINT: Auto-assign id using the static nextId counter.
 * HINT: Add each new item into the static items list in the constructor.
 */

// TODO: Create the InventoryItem class here
class InventoryItem {
    private static List<InventoryItem> items = new ArrayList<>();
    private static int nextId = 1;

    final int id;
    String name;
    int quantity;
    double price;

    InventoryItem(String name, int quantity, double price) {
        // TODO: implement — auto-assign id, add to items list
        // 
        this.id = nextId++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        items.add(this);
    }

    static List<InventoryItem> getAll() {
        // TODO: implement — return a copy of items
        //
        return new ArrayList<>(items);
    }

    static double getTotalValue() {
        // TODO: implement
        double total = 0;
        for (InventoryItem item : items) {
            total += item.quantity * item.price;
        }
        return total;
    }

    static InventoryItem findByName(String name) {
        // TODO: implement — case-insensitive search, return null if not found
        for (InventoryItem item : items) {
            if (item.name.equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    static int getCount() {
        // TODO: implement
        return items.size();
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 5: Temperature Converter Class
 * ─────────────────────────────────────────────
 *
 * Create a class called TemperatureConverter with:
 * - Private field celsius (double)
 * - Constructor that takes celsius value
 * - getCelsius() / setCelsius(value)
 *     → validate: not below -273.15, throw IllegalArgumentException
 * - getFahrenheit() / setFahrenheit(value)
 *     → converts to/from celsius internally
 * - getKelvin() / setKelvin(value)
 *     → converts to/from celsius internally
 * - Static method isFreezing(celsius) → returns true if celsius <= 0
 * - Static method isBoiling(celsius)  → returns true if celsius >= 100
 *
 * FORMULAS:
 *   F = C × 9/5 + 32       C = (F - 32) × 5/9
 *   K = C + 273.15          C = K - 273.15
 */

// TODO: Create the TemperatureConverter class here
class TemperatureConverter {
    private double celsius;

    TemperatureConverter(double celsius) {
        // TODO: implement — use setCelsius for validation
        this.celsius = 0;
    }

    double getCelsius() {
        // TODO: implement
        if (celsius < -273.15) {
            throw new IllegalArgumentException("Celsius cannot be below -273.15");
        }
        return celsius;
    }

    void setCelsius(double value) {
        // TODO: implement — throw IllegalArgumentException if below -273.15
        if (value < -273.15) {
            throw new IllegalArgumentException("Celsius cannot be below -273.15");
        }
        celsius = value;
    }

    double getFahrenheit() {
        // TODO: implement
        return celsius * 9 / 5 + 32;
    }

    void setFahrenheit(double value) {
        // TODO: implement — convert to celsius, use setCelsius for validation
        celsius = (value - 32) * 5 / 9;
    }

    double getKelvin() {
        // TODO: implement
        return celsius + 273.15;
    }

    void setKelvin(double value) {
        // TODO: implement — convert to celsius, use setCelsius for validation
        celsius = value - 273.15;
    }

    static boolean isFreezing(double celsius) {
        // TODO: implement
        return celsius <= 0;
    }

    static boolean isBoiling(double celsius) {
        // TODO: implement
        return celsius >= 100;
    }
}

// ─────────────────────────────────────────────
// Main class with test runner (don't modify)
// ─────────────────────────────────────────────
public class Exercise {
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
            System.out.println("  💪 Keep going! Implement the TODO exercises.");
        }
        System.out.println("════════════════════════════════════\n");
    }
}
