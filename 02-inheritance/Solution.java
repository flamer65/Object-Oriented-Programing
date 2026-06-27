/*
 * ════════════════════════════════════════════════════════════
 *  ✅ SOLUTIONS: Inheritance
 * ════════════════════════════════════════════════════════════
 *
 *  Run this file to verify all solutions pass:
 *
 *    javac Solution.java && java Solution
 *
 * ════════════════════════════════════════════════════════════
 */

/*
 * ─────────────────────────────────────────────
 * SOLUTION 1: Shape Hierarchy
 * ─────────────────────────────────────────────
 */

class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    double getArea() {
        return 0;
    }

    String describe() {
        return "A " + this.color + " shape";
    }
}

class CircleShape extends Shape {
    double radius;

    CircleShape(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double getArea() {
        return Math.PI * this.radius * this.radius;
    }

    @Override
    String describe() {
        String r = (this.radius == Math.floor(this.radius))
                ? String.valueOf((int) this.radius)
                : String.valueOf(this.radius);
        return "A " + this.color + " circle with radius " + r;
    }
}

class RectangleShape extends Shape {
    double width;
    double height;

    RectangleShape(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    double getArea() {
        return this.width * this.height;
    }

    @Override
    String describe() {
        String w = (this.width == Math.floor(this.width))
                ? String.valueOf((int) this.width)
                : String.valueOf(this.width);
        String h = (this.height == Math.floor(this.height))
                ? String.valueOf((int) this.height)
                : String.valueOf(this.height);
        return "A " + this.color + " rectangle (" + w + " x " + h + ")";
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 2: Employee Hierarchy
 * ─────────────────────────────────────────────
 */

class Employee {
    String name;
    protected double baseSalary;

    Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    double calculatePay() {
        return this.baseSalary;
    }

    String getRole() {
        return "Employee";
    }

    @Override
    public String toString() {
        // Format pay as integer if it's a whole number
        String pay = (this.calculatePay() == Math.floor(this.calculatePay()))
                ? String.valueOf((int) this.calculatePay())
                : String.valueOf(this.calculatePay());
        return this.getRole() + ": " + this.name + " - $" + pay;
    }
}

class Manager extends Employee {
    private double bonus;

    Manager(String name, double baseSalary, double bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
    }

    @Override
    double calculatePay() {
        return this.baseSalary + this.bonus;
    }

    @Override
    String getRole() {
        return "Manager";
    }
}

class Developer extends Employee {
    String language;

    Developer(String name, double baseSalary, String language) {
        super(name, baseSalary);
        this.language = language;
    }

    @Override
    String getRole() {
        return "Developer";
    }

    @Override
    public String toString() {
        return super.toString() + " [" + this.language + "]";
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 3: Media Player Hierarchy
 * ─────────────────────────────────────────────
 */

class MediaItem {
    String title;
    int durationSec;

    MediaItem(String title, int durationSec) {
        this.title = title;
        this.durationSec = durationSec;
    }

    String play() {
        return "▶ Playing: " + this.title;
    }

    String getFormattedDuration() {
        int minutes = this.durationSec / 60;
        int seconds = this.durationSec % 60;
        return minutes + "m " + seconds + "s";
    }
}

class Song extends MediaItem {
    String artist;

    Song(String title, int durationSec, String artist) {
        super(title, durationSec);
        this.artist = artist;
    }

    @Override
    String play() {
        return "▶ Playing: " + this.title + " by " + this.artist;
    }
}

class Podcast extends MediaItem {
    String host;
    int episode;

    Podcast(String title, int durationSec, String host, int episode) {
        super(title, durationSec);
        this.host = host;
        this.episode = episode;
    }

    @Override
    String play() {
        return "▶ Playing: " + this.title + " - Ep." + this.episode + " with " + this.host;
    }
}

class LivePodcast extends Podcast {
    int listenerCount;

    LivePodcast(String title, int durationSec, String host, int episode, int listenerCount) {
        super(title, durationSec, host, episode);
        this.listenerCount = listenerCount;
    }

    @Override
    String play() {
        return super.play() + " [LIVE 🔴 " + this.listenerCount + " listeners]";
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 4: Discount System with Protected Members
 * ─────────────────────────────────────────────
 */

class Coupon {
    final String code;
    protected double minPurchase;

    Coupon(String code, double minPurchase) {
        this.code = code;
        this.minPurchase = minPurchase;
    }

    protected boolean isValid(double orderTotal) {
        return orderTotal >= this.minPurchase;
    }

    protected double calculateDiscount(double orderTotal) {
        return 0;
    }

    double apply(double orderTotal) {
        if (!this.isValid(orderTotal)) {
            return orderTotal;
        }
        double discount = this.calculateDiscount(orderTotal);
        return Math.max(0, orderTotal - discount);
    }
}

class PercentageCoupon extends Coupon {
    private double percent;

    PercentageCoupon(String code, double minPurchase, double percent) {
        super(code, minPurchase);
        this.percent = percent;
    }

    @Override
    protected double calculateDiscount(double orderTotal) {
        return orderTotal * this.percent / 100;
    }
}

class FixedCoupon extends Coupon {
    private double discountAmount;

    FixedCoupon(String code, double minPurchase, double discountAmount) {
        super(code, minPurchase);
        this.discountAmount = discountAmount;
    }

    @Override
    protected double calculateDiscount(double orderTotal) {
        return this.discountAmount;
    }
}

/*
 * ─────────────────────────────────────────────
 * SOLUTION 5: Vehicle Fleet with toString Override
 * ─────────────────────────────────────────────
 */

class FleetVehicle {
    String id;
    String make;
    int mileage;

    FleetVehicle(String id, String make, int mileage) {
        this.id = id;
        this.make = make;
        this.mileage = mileage;
    }

    void drive(int km) {
        this.mileage += km;
    }

    boolean needsMaintenance() {
        return this.mileage >= 10000;
    }

    @Override
    public String toString() {
        return "[" + this.id + "] " + this.make + " - " + this.mileage + " km";
    }
}

class Truck extends FleetVehicle {
    int cargoCapacityTons;

    Truck(String id, String make, int cargoCapacityTons, int mileage) {
        super(id, make, mileage);
        this.cargoCapacityTons = cargoCapacityTons;
    }

    @Override
    public String toString() {
        return super.toString() + " (Truck, " + this.cargoCapacityTons + "T)";
    }

    @Override
    boolean needsMaintenance() {
        return this.mileage >= 8000;
    }
}

class Bus extends FleetVehicle {
    int passengerCapacity;

    Bus(String id, String make, int passengerCapacity, int mileage) {
        super(id, make, mileage);
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        return super.toString() + " (Bus, " + this.passengerCapacity + " seats)";
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
            System.out.println("  ⚠️  Some tests failed. Check the solutions above.");
        }
        System.out.println("════════════════════════════════════\n");
    }
}
