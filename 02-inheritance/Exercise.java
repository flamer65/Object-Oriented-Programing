/*
 * ════════════════════════════════════════════════════════════
 *  🏋️ EXERCISES: Inheritance
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

/*
 * ─────────────────────────────────────────────
 * EXERCISE 1: Shape Hierarchy
 * ─────────────────────────────────────────────
 *
 * Create a class hierarchy:
 *
 *   Shape (parent)
 *   ├── CircleShape (child)
 *   └── RectangleShape (child)
 *
 * Shape:
 *   - Constructor: takes color (String)
 *   - Method getArea(): returns 0 (base implementation)
 *   - Method describe(): returns "A {color} shape"
 *
 * CircleShape (extends Shape):
 *   - Constructor: takes color (String) and radius (double)
 *   - Override getArea(): returns π × radius²
 *   - Override describe(): returns "A {color} circle with radius {radius}"
 *
 * RectangleShape (extends Shape):
 *   - Constructor: takes color (String), width (double), height (double)
 *   - Override getArea(): returns width × height
 *   - Override describe(): returns "A {color} rectangle ({width} x {height})"
 *
 * HINT: Use super() to pass color to the parent constructor.
 */

// TODO: Create Shape, CircleShape, and RectangleShape classes here
class Shape {
    String color;

    Shape(String color) {
        // TODO: implement
        this.color = color;
    }

    double getArea() {
        // TODO: implement
        return 0;
    }

    String describe() {
        // TODO: implement — return "A {color} shape"
        return "A " + color + " shape";
    }
}

class CircleShape extends Shape {
    int radius;

    CircleShape(String color, int radius) {
        super(color);
        this.radius = radius;
        // TODO: implement
    }

    @Override
    double getArea() {
        // TODO: implement — return π × radius²
        return Math.PI * radius * radius;
    }

    @Override
    String describe() {
        // TODO: implement — return "A {color} circle with radius {radius}"
        return "A " + color + " circle with radius " + radius;
    }
}

class RectangleShape extends Shape {
    int width;
    int height;

    RectangleShape(String color, int width, int height) {
        super(color);
        // TODO: implement
        this.width = width;
        this.height = height;
    }

    @Override
    double getArea() {
        // TODO: implement — return width × height
        return width * height;
    }

    @Override
    String describe() {
        // TODO: implement — return "A {color} rectangle ({width} x {height})"
        return "A " + color + " rectangle (" + width + " x " + height + ")";
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 2: Employee Hierarchy
 * ─────────────────────────────────────────────
 *
 * Create a class hierarchy:
 *
 *   Employee (parent)
 *   ├── Manager (child)
 *   └── Developer (child)
 *
 * Employee:
 *   - Constructor: name (String), baseSalary (double)
 *   - Protected field baseSalary
 *   - Method calculatePay(): returns baseSalary
 *   - Method getRole(): returns "Employee"
 *   - Method toString(): returns "{getRole()}: {name} - ${calculatePay()}"
 *
 * Manager (extends Employee):
 *   - Constructor: name, baseSalary, bonus (double)
 *   - Override calculatePay(): returns baseSalary + bonus
 *   - Override getRole(): returns "Manager"
 *
 * Developer (extends Employee):
 *   - Constructor: name, baseSalary, language (String)
 *   - Public language field
 *   - Override getRole(): returns "Developer"
 *   - Override toString(): calls super.toString() and appends " [{language}]"
 *
 * HINT: Use super.toString() in Developer to reuse parent's formatting.
 * HINT: Use protected for baseSalary so children can access it.
 */

// TODO: Create Employee, Manager, and Developer classes here
class Employee {
    String name;
    protected int baseSalary;

    Employee(String name, int baseSalary) {
        // TODO: implement
        this.name = name;
        this.baseSalary = baseSalary;
    }

    int calculatePay() {
        // TODO: implement
        return baseSalary;
    }

    String getRole() {
        // TODO: implement
        return "Employee";
    }

    @Override
    public String toString() {
        // TODO: implement — return "{getRole()}: {name} - ${calculatePay()}"
        return String.format("%s: %s - $%d", getRole(), name, calculatePay());
    }
}

class Manager extends Employee {
    private int bonus;

    Manager(String name, int baseSalary, int bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
        // TODO: implement
    }

    @Override
    int calculatePay() {
        // TODO: implement — return baseSalary + bonus
        return baseSalary +  this.bonus;
    }

    @Override
    String getRole() {
        // TODO: implement
        return "Manager";
    }
}

class Developer extends Employee {
    String language;

    Developer(String name, int baseSalary, String language) {
        super(name, baseSalary);
        // TODO: implement
        this.language = language;
    }

    @Override
    String getRole() {
        // TODO: implement
        return "Developer";
    }

    @Override
    public String toString() {
        // TODO: implement — call super.toString() and append " [{language}]"
        return super.toString() + " [" + language + "]";
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 3: Media Player Hierarchy
 * ─────────────────────────────────────────────
 *
 * Create a class hierarchy:
 *
 *   MediaItem (parent)
 *   ├── Song (child)
 *   └── Podcast (child)
 *       └── LivePodcast (grandchild of Podcast)
 *
 * MediaItem:
 *   - Constructor: title (String), durationSec (int)
 *   - Method play(): returns "▶ Playing: {title}"
 *   - Method getFormattedDuration(): returns "Xm Ys"
 *     HINT: minutes = durationSec / 60, seconds = durationSec % 60
 *
 * Song (extends MediaItem):
 *   - Constructor: title, durationSec, artist (String)
 *   - Override play(): returns "▶ Playing: {title} by {artist}"
 *
 * Podcast (extends MediaItem):
 *   - Constructor: title, durationSec, host (String), episode (int)
 *   - Override play(): returns "▶ Playing: {title} - Ep.{episode} with {host}"
 *
 * LivePodcast (extends Podcast):
 *   - Constructor: title, durationSec, host, episode, listenerCount (int)
 *   - Override play(): calls super.play() and appends
 *     " [LIVE 🔴 {listenerCount} listeners]"
 *
 * HINT: Multi-level inheritance — LivePodcast extends Podcast extends MediaItem.
 * HINT: Use super.play() in LivePodcast to get Podcast's play result.
 */

// TODO: Create MediaItem, Song, Podcast, and LivePodcast classes here
class MediaItem {
    String title;
    int durationSec;

    MediaItem(String title, int durationSec) {
        // TODO: implement
        this.title = title;
        this.durationSec = durationSec;
    }

    String play() {
        // TODO: implement — return "▶ Playing: {title}"
        return "▶ Playing: " + title;
    }

    String getFormattedDuration() {
        // TODO: implement — return "Xm Ys"
        return durationSec / 60 + "m " + durationSec % 60 + "s";
    }
}

class Song extends MediaItem {
    String artist;

    Song(String title, int durationSec, String artist) {
        super(title, durationSec);
        // TODO: implement
        this.artist = artist;
    }

    @Override
    String play() {
        // TODO: implement — return "▶ Playing: {title} by {artist}"
        return "▶ Playing: " + title + " by " + artist;
    }
}

class Podcast extends MediaItem {
    String host;
    int episode;

    Podcast(String title, int durationSec, String host, int episode) {
        super(title, durationSec);
        // TODO: implement
        this.host = host;
        this.episode = episode;
    }

    @Override
    String play() {
        // TODO: implement — return "▶ Playing: {title} - Ep.{episode} with {host}"
        return "▶ Playing: " + title + " - Ep." + episode + " with " + host;
    }
}

class LivePodcast extends Podcast {
    int listenerCount;

    LivePodcast(String title, int durationSec, String host, int episode, int listenerCount) {
        super(title, durationSec, host, episode);
        // TODO: implement
        this.listenerCount = listenerCount;
    }

    @Override
    String play() {
        // TODO: implement — call super.play() and append " [LIVE 🔴 {listenerCount} listeners]"
        return super.play() + " [LIVE 🔴 " + listenerCount + " listeners]";
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 4: Discount System with Protected Members
 * ─────────────────────────────────────────────
 *
 * Create a class hierarchy:
 *
 *   Coupon (parent)
 *   ├── PercentageCoupon (child)
 *   └── FixedCoupon (child)
 *
 * Coupon:
 *   - Constructor: code (String, final), minPurchase (double, protected)
 *   - Protected method isValid(orderTotal): returns true if orderTotal >= minPurchase
 *   - Protected method calculateDiscount(orderTotal): returns 0 (base)
 *   - Public method apply(orderTotal): returns orderTotal if not valid;
 *     otherwise returns orderTotal - discount (floored at 0, never negative)
 *
 * PercentageCoupon (extends Coupon):
 *   - Constructor: code, minPurchase, percent (double, e.g., 20 for 20%)
 *   - Override calculateDiscount(): returns orderTotal * percent / 100
 *
 * FixedCoupon (extends Coupon):
 *   - Constructor: code, minPurchase, discountAmount (double)
 *   - Override calculateDiscount(): returns discountAmount
 *
 * HINT: Protected members let children access isValid() and minPurchase.
 */

// TODO: Create Coupon, PercentageCoupon, and FixedCoupon classes here
class Coupon {
    final String code;
    protected double minPurchase;

    Coupon(String code, double minPurchase) {
        // TODO: implement
        this.code = code;
        this.minPurchase = minPurchase;
    }

    protected boolean isValid(double orderTotal) {
        // TODO: implement
        return orderTotal >= minPurchase;
    }

    protected double calculateDiscount(double orderTotal) {
        // TODO: implement
        return 0;
    }

    double apply(double orderTotal) {
        // TODO: implement — check isValid, calculate discount, floor at 0
        if (!isValid(orderTotal)) {
            return orderTotal;
        }
        return Math.max(0, orderTotal - calculateDiscount(orderTotal));
    }
}

class PercentageCoupon extends Coupon {
    private double percent;

    PercentageCoupon(String code, double minPurchase, double percent) {
        super(code, minPurchase);
        // TODO: implement
        this.percent = percent;
    }

    @Override
    protected double calculateDiscount(double orderTotal) {
        // TODO: implement — return orderTotal * percent / 100
        return orderTotal * percent / 100;
    }
}

class FixedCoupon extends Coupon {
    private double discountAmount;

    FixedCoupon(String code, double minPurchase, double discountAmount) {
        super(code, minPurchase);
        this.discountAmount = discountAmount;
        // TODO: implement
    }

    @Override
    protected double calculateDiscount(double orderTotal) {
        // TODO: implement — return discountAmount
        return discountAmount;
    }
}

/*
 * ─────────────────────────────────────────────
 * EXERCISE 5: Vehicle Fleet with toString Override
 * ─────────────────────────────────────────────
 *
 * Create a class hierarchy:
 *
 *   FleetVehicle (parent)
 *   ├── Truck (child)
 *   └── Bus (child)
 *
 * FleetVehicle:
 *   - Constructor: id (String), make (String), mileage (int, default 0)
 *   - Method drive(km): adds km to mileage
 *   - Method needsMaintenance(): returns true if mileage >= 10000
 *   - Override toString(): returns "[{id}] {make} - {mileage} km"
 *
 * Truck (extends FleetVehicle):
 *   - Constructor: id, make, cargoCapacityTons (int), mileage
 *   - Override toString(): calls super.toString() and appends " (Truck, {cargoCapacityTons}T)"
 *   - Override needsMaintenance(): returns true if mileage >= 8000
 *     (trucks need more frequent maintenance)
 *
 * Bus (extends FleetVehicle):
 *   - Constructor: id, make, passengerCapacity (int), mileage
 *   - Override toString(): calls super.toString() and appends " (Bus, {passengerCapacity} seats)"
 *
 * HINT: Use super.toString() to reuse parent formatting.
 */

// TODO: Create FleetVehicle, Truck, and Bus classes here
class FleetVehicle {
    String id;
    String make;
    int mileage;

    FleetVehicle(String id, String make, int mileage) {
        // TODO: implement
        this.id = id;
        this.make = make;
        this.mileage = mileage;
    }

    void drive(int km) {
        // TODO: implement
        this.mileage += km;
    }

    boolean needsMaintenance() {
        // TODO: implement — return true if mileage >= 10000
        return mileage >= 10000;
    }

    @Override
    public String toString() {
        // TODO: implement — return "[{id}] {make} - {mileage} km"
        return String.format("[%s] %s - %d km", id, make, mileage);
    }
}

class Truck extends FleetVehicle {
    int cargoCapacityTons;

    Truck(String id, String make, int cargoCapacityTons, int mileage) {
        super(id, make, mileage);
        this.cargoCapacityTons = cargoCapacityTons;
        // TODO: implement
    }

    @Override
    public String toString() {
        // TODO: implement — call super.toString() and append " (Truck, {cargoCapacityTons}T)"
        return String.format("%s (Truck, %dT)", super.toString(), cargoCapacityTons);
    }

    @Override
    boolean needsMaintenance() {
        // TODO: implement — return true if mileage >= 8000
        return mileage >= 8000;
    }
}

class Bus extends FleetVehicle {
    int passengerCapacity;

    Bus(String id, String make, int passengerCapacity, int mileage) {
        super(id, make, mileage);
        // TODO: implement
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        // TODO: implement — call super.toString() and append " (Bus, {passengerCapacity} seats)"
        return String.format("%s (Bus, %d seats)", super.toString(), passengerCapacity);
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

        // ═══ Exercise 1: Shape Hierarchy ═══
        System.out.println("═══ Exercise 1: Shape Hierarchy ═══");

        Shape s = new Shape("gray");
        test("Shape describe = \"A gray shape\"",
                s.describe().equals("A gray shape"));
        test("Shape area = 0", s.getArea() == 0);

        CircleShape c = new CircleShape("red", 5);

        test("Circle describe",
                c.describe().equals("A red circle with radius 5"));
        test("Circle area ≈ 78.54",
                Math.abs(c.getArea() - Math.PI * 25) < 0.01);
        test("Circle instanceof Shape", c instanceof Shape);

        RectangleShape r = new RectangleShape("blue", 4, 6);
       
        test("Rectangle describe",
                r.describe().equals("A blue rectangle (4 x 6)"));
        test("Rectangle area = 24", r.getArea() == 24);

        // ═══ Exercise 2: Employee Hierarchy ═══
        System.out.println("\n═══ Exercise 2: Employee Hierarchy ═══");

        Employee emp = new Employee("Alice", 50000);
        test("Employee role", emp.getRole().equals("Employee"));
        test("Employee pay", emp.calculatePay() == 50000);
        System.out.println(emp.toString());
        test("Employee toString",
                emp.toString().equals("Employee: Alice - $50000"));
        Manager mgr = new Manager("Bob", 80000, 20000);
        test("Manager role", mgr.getRole().equals("Manager"));
        test("Manager pay includes bonus", mgr.calculatePay() == 100000);
        test("Manager toString",
                mgr.toString().equals("Manager: Bob - $100000"));
        test("Manager instanceof Employee", mgr instanceof Employee);

        Developer dev = new Developer("Charlie", 90000, "Java");
        test("Developer role", dev.getRole().equals("Developer"));
        test("Developer language", dev.language.equals("Java"));
        test("Developer toString includes language",
                dev.toString().equals("Developer: Charlie - $90000 [Java]"));

        // ═══ Exercise 3: Media Player ═══
        System.out.println("\n═══ Exercise 3: Media Player ═══");

        MediaItem media = new MediaItem("Test", 125);
        test("MediaItem play",
                media.play().equals("▶ Playing: Test"));
        test("Duration 125s = 2m 5s",
                media.getFormattedDuration().equals("2m 5s"));

        Song song = new Song("Bohemian Rhapsody", 354, "Queen");
        test("Song play",
                song.play().equals("▶ Playing: Bohemian Rhapsody by Queen"));
        test("Song duration 354s = 5m 54s",
                song.getFormattedDuration().equals("5m 54s"));
        test("Song instanceof MediaItem", song instanceof MediaItem);

        Podcast podcast = new Podcast("Tech Talk", 1800, "Naman", 42);
        test("Podcast play",
                podcast.play().equals("▶ Playing: Tech Talk - Ep.42 with Naman"));
        test("Podcast instanceof MediaItem", podcast instanceof MediaItem);

        LivePodcast live = new LivePodcast("Live Code", 0, "Naman", 100, 5000);
        test("LivePodcast play",
                live.play().equals("▶ Playing: Live Code - Ep.100 with Naman [LIVE 🔴 5000 listeners]"));
        test("LivePodcast instanceof Podcast", live instanceof Podcast);

        // ═══ Exercise 4: Discount System ═══
        System.out.println("\n═══ Exercise 4: Discount System ═══");

        PercentageCoupon pct = new PercentageCoupon("SAVE20", 50, 20);
        test("20% off $100 = $80", pct.apply(100) == 80);
        test("20% off $200 = $160", pct.apply(200) == 160);
        test("Below min purchase → no discount", pct.apply(30) == 30);
        test("Code is SAVE20", pct.code.equals("SAVE20"));

        FixedCoupon fixed = new FixedCoupon("FLAT15", 25, 15);
        test("$15 off $100 = $85", fixed.apply(100) == 85);
        test("$15 off $10 → $10 (below min purchase)", fixed.apply(10) == 10);
        test("$15 off $30 → $15", fixed.apply(30) == 15);

        // Edge case: discount larger than price
        FixedCoupon bigDiscount = new FixedCoupon("BIG50", 0, 50);
        test("$50 off $30 → $0 (never negative)", bigDiscount.apply(30) == 0);

        // ═══ Exercise 5: Vehicle Fleet ═══
        System.out.println("\n═══ Exercise 5: Vehicle Fleet ═══");

        Truck truck = new Truck("T-001", "Volvo", 20, 7500);
        test("Truck toString",
                truck.toString().equals("[T-001] Volvo - 7500 km (Truck, 20T)"));
        test("Truck no maintenance at 7500",
                truck.needsMaintenance() == false);
        truck.drive(1000);
        test("Truck needs maintenance at 8500",
                truck.needsMaintenance() == true);
        test("Mileage updated after drive",
                truck.toString().equals("[T-001] Volvo - 8500 km (Truck, 20T)"));

        Bus bus = new Bus("B-042", "Mercedes", 50, 9000);
        System.out.println(bus.toString());
        test("Bus toString",
                bus.toString().equals("[B-042] Mercedes - 9000 km (Bus, 50 seats)"));
        test("Bus no maintenance at 9000",
                bus.needsMaintenance() == false);
        bus.drive(1500);
        test("Bus needs maintenance at 10500",
                bus.needsMaintenance() == true);

        test("Truck instanceof FleetVehicle", truck instanceof FleetVehicle);
        test("Bus instanceof FleetVehicle", bus instanceof FleetVehicle);

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
