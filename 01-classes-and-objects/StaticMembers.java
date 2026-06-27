/*
 * ═════════════════════════════════════════════════════════════
 *  📘 LESSON 4: Static Members
 * ═════════════════════════════════════════════════════════════
 *
 *  WHAT ARE STATIC MEMBERS?
 *  ────────────────────────
 *  Regular fields/methods belong to each OBJECT (instance).
 *  Static fields/methods belong to the CLASS itself.
 *
 *  Think of it this way:
 *  ┌─────────────────────────┬──────────────────────────────────┐
 *  │  Instance Members       │  Static Members                  │
 *  ├─────────────────────────┼──────────────────────────────────┤
 *  │  Per-object data        │  Shared across ALL objects       │
 *  │  Accessed via `this`    │  Accessed via `ClassName.member` │
 *  │  new Dog().name         │  Dog.totalDogs                   │
 *  │  Different per instance │  Same for everyone               │
 *  └─────────────────────────┴──────────────────────────────────┘
 *
 *  WHEN TO USE STATIC?
 *  ───────────────────
 *  • Utility/helper methods (Math.random(), Math.max())
 *  • Tracking class-level data (count of all instances created)
 *  • Factory methods (alternative constructors)
 *  • Constants that belong to the class concept
 *
 *  JAVA vs TYPESCRIPT:
 *  ─────────────────────
 *  ┌──────────────────────────┬───────────────────────────────────┐
 *  │  TypeScript              │  Java                             │
 *  ├──────────────────────────┼───────────────────────────────────┤
 *  │  static count = 0        │  static int count = 0;            │
 *  │  static readonly X = 1   │  static final int X = 1;          │
 *  │  static method()         │  static returnType method()       │
 *  └──────────────────────────┴───────────────────────────────────┘
 *
 * ═════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ─────────────────────────────────────────────
// 1️⃣  Static Properties — Shared Across All Instances
// ─────────────────────────────────────────────
//
// Player has a static counter that tracks how many players
// have been created. Each new player gets a unique auto-incrementing ID.
// The counter is shared across ALL Player objects.

class Player {
    static int totalPlayers = 0; // Shared counter across ALL Player objects

    int id;
    String name;
    int score;

    Player(String name) {
        this(name, 0);
    }

    Player(String name, int score) {
        Player.totalPlayers++;              // Increment the shared counter
        this.id = Player.totalPlayers;      // Assign unique ID
        this.name = name;
        this.score = score;
    }

    void addScore(int points) {
        this.score += points;
    }

    @Override
    public String toString() {
        return "Player #" + this.id + ": " + this.name + " (Score: " + this.score + ")";
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Static Methods — Utility Functions on the Class
// ─────────────────────────────────────────────
//
// MathUtils is a utility class — you never create instances of it.
// All methods are static and called directly on the class.
// In Java, you can prevent instantiation with a private constructor.

class MathUtils {
    // Private constructor — prevents anyone from doing `new MathUtils()`
    private MathUtils() {}

    static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    static int randomInt(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static double degreesToRadians(double degrees) {
        return (degrees * Math.PI) / 180.0;
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Factory Methods — Alternative Constructors
// ─────────────────────────────────────────────
//
// Java only allows one constructor name (the class name).
// Factory methods (static methods that return new instances)
// let you create objects in different, named ways.
// This is even MORE useful in Java than in TypeScript!

class Color {
    private final int r;
    private final int g;
    private final int b;
    private final double a;

    // Primary constructor
    Color(int r, int g, int b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    Color(int r, int g, int b) {
        this(r, g, b, 1.0);
    }

    // Getters for the final fields
    int getR() { return r; }
    int getG() { return g; }
    int getB() { return b; }
    double getA() { return a; }

    // Factory method: from hex string
    static Color fromHex(String hex) {
        String cleaned = hex.replace("#", "");
        int r = Integer.parseInt(cleaned.substring(0, 2), 16);
        int g = Integer.parseInt(cleaned.substring(2, 4), 16);
        int b = Integer.parseInt(cleaned.substring(4, 6), 16);
        return new Color(r, g, b);
    }

    // Factory method: from HSL values
    static Color fromHSL(double h, double s, double l) {
        s /= 100.0;
        l /= 100.0;
        double a = s * Math.min(l, 1.0 - l);
        int rVal = (int) Math.round(hslHelper(0, h, l, a) * 255);
        int gVal = (int) Math.round(hslHelper(8, h, l, a) * 255);
        int bVal = (int) Math.round(hslHelper(4, h, l, a) * 255);
        return new Color(rVal, gVal, bVal);
    }

    private static double hslHelper(double n, double h, double l, double a) {
        double k = (n + h / 30.0) % 12.0;
        return l - a * Math.max(Math.min(Math.min(k - 3, 9 - k), 1), -1);
    }

    // Factory methods: predefined colors
    static Color red()   { return new Color(255, 0, 0); }
    static Color green() { return new Color(0, 255, 0); }
    static Color blue()  { return new Color(0, 0, 255); }
    static Color white() { return new Color(255, 255, 255); }
    static Color black() { return new Color(0, 0, 0); }

    // Instance method: convert to hex string
    String toHex() {
        return String.format("#%02x%02x%02x", this.r, this.g, this.b);
    }

    @Override
    public String toString() {
        return "rgba(" + this.r + ", " + this.g + ", " + this.b + ", " + this.a + ")";
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Static + Instance Working Together
// ─────────────────────────────────────────────
//
// ProductRegistry auto-registers every new product into
// a class-level list. Static methods query the registry
// while instance methods describe individual products.

class ProductItem {
    private static List<ProductItem> allProducts = new ArrayList<>();
    private static int nextId = 1;

    private final int id;
    private String name;
    private double price;
    private String category;

    ProductItem(String name, double price, String category) {
        this.id = ProductItem.nextId++;
        this.name = name;
        this.price = price;
        this.category = category;
        ProductItem.allProducts.add(this);
    }

    // Getters
    int getId() { return id; }
    String getName() { return name; }
    double getPrice() { return price; }
    String getCategory() { return category; }

    // Static method: get all products (returns a copy)
    static List<ProductItem> getAll() {
        return new ArrayList<>(allProducts);
    }

    // Static method: find products by category
    static List<ProductItem> findByCategory(String category) {
        List<ProductItem> result = new ArrayList<>();
        for (ProductItem p : allProducts) {
            if (p.category.equals(category)) {
                result.add(p);
            }
        }
        return result;
    }

    // Static method: find the cheapest product
    static ProductItem findCheapest() {
        if (allProducts.isEmpty()) return null;
        ProductItem cheapest = allProducts.get(0);
        for (ProductItem p : allProducts) {
            if (p.price < cheapest.price) {
                cheapest = p;
            }
        }
        return cheapest;
    }

    // Static method: average price across all products
    static double getAveragePrice() {
        if (allProducts.isEmpty()) return 0;
        double total = 0;
        for (ProductItem p : allProducts) {
            total += p.price;
        }
        return total / allProducts.size();
    }

    static int getCount() {
        return allProducts.size();
    }

    @Override
    public String toString() {
        return String.format("[#%d] %s - $%.2f (%s)", this.id, this.name, this.price, this.category);
    }
}

// ─────────────────────────────────────────────
// 5️⃣  Static Constants
// ─────────────────────────────────────────────
//
// `static final` fields are constants that belong to the class.
// They are set once and never change — perfect for fixed values
// like HTTP status codes, mathematical constants, or config values.

class HttpStatus {
    static final int OK = 200;
    static final int CREATED = 201;
    static final int BAD_REQUEST = 400;
    static final int UNAUTHORIZED = 401;
    static final int FORBIDDEN = 403;
    static final int NOT_FOUND = 404;
    static final int INTERNAL_SERVER_ERROR = 500;

    // Private constructor — this is a constants-only class
    private HttpStatus() {}

    static String getMessage(int code) {
        Map<Integer, String> messages = new HashMap<>();
        messages.put(OK, "OK");
        messages.put(CREATED, "Created");
        messages.put(BAD_REQUEST, "Bad Request");
        messages.put(UNAUTHORIZED, "Unauthorized");
        messages.put(FORBIDDEN, "Forbidden");
        messages.put(NOT_FOUND, "Not Found");
        messages.put(INTERNAL_SERVER_ERROR, "Internal Server Error");

        String message = messages.get(code);
        return message != null ? message : "Unknown Status";
    }
}

// ═════════════════════════════════════════════════════════════
// PUBLIC CLASS — File name must match: StaticMembers.java
// ═════════════════════════════════════════════════════════════

public class StaticMembers {
    public static void main(String[] args) {

        // ═══ 1. Static Properties ═══
        System.out.println("═══ 1. Static Properties ═══");
        System.out.println("Total players before: " + Player.totalPlayers); // 0

        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Charlie");
        p1.addScore(10);

        System.out.println(p1); // Player #1: Alice (Score: 10)
        System.out.println(p2); // Player #2: Bob (Score: 0)
        System.out.println(p3); // Player #3: Charlie (Score: 0)
        System.out.println("Total players after: " + Player.totalPlayers); // 3

        // Note: Access static members via the CLASS, not the object:
        // Player.totalPlayers ✅
        // p1.totalPlayers     ⚠️ (compiles in Java but is bad practice — use the class name)

        // ═══ 2. Static Methods ═══
        System.out.println("\n═══ 2. Static Methods ═══");

        // No `new MathUtils()` needed — call directly on the class!
        System.out.println("clamp(150, 0, 100): " + MathUtils.clamp(150, 0, 100));     // 100.0
        System.out.println("lerp(0, 100, 0.5): " + MathUtils.lerp(0, 100, 0.5));       // 50.0
        System.out.println("randomInt(1, 10): " + MathUtils.randomInt(1, 10));
        System.out.println("isPrime(17): " + MathUtils.isPrime(17));                     // true
        System.out.println("isPrime(15): " + MathUtils.isPrime(15));                     // false
        System.out.println(String.format("degreesToRadians(180): %.4f",
                MathUtils.degreesToRadians(180)));                                        // 3.1416

        // ═══ 3. Factory Methods ═══
        System.out.println("\n═══ 3. Factory Methods ═══");

        Color coral = Color.fromHex("#FF7F50");
        System.out.println("From hex #FF7F50: " + coral);
        // rgba(255, 127, 80, 1.0)

        Color skyBlue = Color.fromHSL(197, 71, 73);
        System.out.println("From HSL(197, 71, 73): " + skyBlue + " → " + skyBlue.toHex());

        System.out.println("Predefined red: " + Color.red());       // rgba(255, 0, 0, 1.0)
        System.out.println("Predefined blue: " + Color.blue().toHex()); // #0000ff

        // ═══ 4. Static + Instance Together ═══
        System.out.println("\n═══ 4. Static + Instance Together ═══");

        new ProductItem("Java Book", 29.99, "Books");
        new ProductItem("Mechanical Keyboard", 149.99, "Electronics");
        new ProductItem("JavaScript Book", 24.99, "Books");
        new ProductItem("Monitor", 399.99, "Electronics");
        new ProductItem("Clean Code", 34.99, "Books");

        System.out.println("All products:");
        for (ProductItem p : ProductItem.getAll()) {
            System.out.println("  " + p);
        }

        System.out.println("\nBooks only:");
        for (ProductItem p : ProductItem.findByCategory("Books")) {
            System.out.println("  " + p);
        }

        ProductItem cheapest = ProductItem.findCheapest();
        System.out.println("\nCheapest: " + (cheapest != null ? cheapest : "none"));
        System.out.println(String.format("Average price: $%.2f", ProductItem.getAveragePrice()));
        System.out.println("Total products: " + ProductItem.getCount());

        // ═══ 5. Static Constants ═══
        System.out.println("\n═══ 5. Static Constants ═══");

        System.out.println(HttpStatus.OK + ": " + HttpStatus.getMessage(HttpStatus.OK));
        // 200: OK
        System.out.println(HttpStatus.NOT_FOUND + ": " + HttpStatus.getMessage(HttpStatus.NOT_FOUND));
        // 404: Not Found
        System.out.println(HttpStatus.INTERNAL_SERVER_ERROR + ": "
                + HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR));
        // 500: Internal Server Error

        // HttpStatus.OK = 999;  // ❌ ERROR: cannot assign a value to final variable OK

        // ─────────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────────
        // ✅ `static` members belong to the CLASS, not to individual objects.
        // ✅ Access via `ClassName.member`, NOT `instance.member`.
        // ✅ Static fields are shared — great for counters, registries, configs.
        // ✅ Static methods = utility functions (no `this` for instance data).
        // ✅ Factory methods = static methods that return new instances.
        // ✅ `static final` = class-level constants (Java's version of `static readonly`).
        // ✅ Static and instance members can work together (e.g., auto-registering).
    }
}
