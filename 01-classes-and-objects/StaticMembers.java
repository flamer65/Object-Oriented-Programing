/*
 * ═══════════════════════════════════════════════════════════════
 *  📘 LESSON 4: Static Members
 * ═══════════════════════════════════════════════════════════════
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
 *  JAVA vs TYPESCRIPT DIFFERENCES:
 *  ───────────────────────────────
 *  ┌───────────────────────────┬──────────────────────────────────┐
 *  │  TypeScript               │  Java                            │
 *  ├───────────────────────────┼──────────────────────────────────┤
 *  │  static totalDogs = 0     │  static int totalDogs = 0        │
 *  │  static readonly PI       │  static final double PI          │
 *  │  static fromHex(hex)      │  static Color fromHex(String h)  │
 *  └───────────────────────────┴──────────────────────────────────┘
 *
 *  WHEN TO USE STATIC?
 *  ───────────────────
 *  • Utility/helper methods (Math.random(), Math.max())
 *  • Tracking class-level data (count of all instances created)
 *  • Factory methods (alternative constructors)
 *  • Constants that belong to the class concept
 *
 * ═══════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
// 1️⃣  Static Properties — Shared Across All Instances
// ─────────────────────────────────────────────
/*
 *  Player has a static counter `totalPlayers` that is shared
 *  across ALL Player objects. Each new Player increments it
 *  and gets a unique auto-incrementing ID.
 *
 *  Key rule: access static members via the CLASS name:
 *     p1.totalPlayers      ❌ (compiles in Java, but is misleading!)
 *     Player.totalPlayers  ✅
 */

class Player {
    private static int totalPlayers = 0;  // Shared counter across ALL Player objects
    private final int id;
    private String name;
    private int score;

    // Two constructors provide a convenient default and a customizable option:
    // - Player(String name) lets you create a player with score 0 by default.
    // - Player(String name, int score) lets you set an initial score explicitly.
    //
    // The first constructor delegates to the second using `this(name, 0)` so the
    // shared setup logic (name, score, ID assignment, and totalPlayers increment)
    // exists in only one place.
    Player(String name) {
        // `this(name, 0)` calls the OTHER constructor in this same class.
        // This is called constructor chaining.
        //
        // Here, the second argument is the starting score, not the player ID.
        // So `new Player("Alice")` becomes `new Player("Alice", 0)`.
        //
        // The ID is assigned later inside the two-argument constructor:
        //   Player.totalPlayers++;
        //   this.id = Player.totalPlayers;
        this(name, 0);
    }

    Player(String name, int score) {
        this.name = name;
        this.score = score;
        Player.totalPlayers++;            // Increment the shared counter
        this.id = Player.totalPlayers;    // Assign unique ID
    }

    void addScore(int points) {
        this.score += points;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    static int getTotalPlayers() {
        return totalPlayers;
    }

    @Override
    public String toString() {
        return "Player #" + id + ": " + name + " (Score: " + score + ")";
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Static Methods — Utility Functions on the Class
// ─────────────────────────────────────────────
/*
 *  MathUtils is a pure utility class — it has ONLY static methods.
 *  You never create an instance: no `new MathUtils()`.
 *
 *  Java convention: make the constructor private to prevent
 *  accidental instantiation of utility classes.
 *
 *  Static methods cannot access `this` (there's no instance).
 *  They work purely with their parameters.
 */

class MathUtils {
    // Private constructor prevents instantiation
    private MathUtils() {}

    // Clamp a value within [min, max]
    static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    // Linear interpolation: blend between start and end by factor t (0.0–1.0)
    static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    // Random integer in range [min, max] (inclusive)
    static int randomInt(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min + 1))) + min;
    }

    // Check if a number is prime
    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // Convert degrees to radians
    static double degreesToRadians(double degrees) {
        return (degrees * Math.PI) / 180.0;
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Factory Methods — Alternative Constructors
// ─────────────────────────────────────────────
/*
 *  Java constructors must be named after the class.
 *  What if you want to create a Color from hex? Or from HSL?
 *
 *  Static "factory methods" solve this — they are named methods
 *  that return a new instance:
 *
 *      Color.fromHex("#FF7F50")     ← descriptive name!
 *      Color.fromHSL(197, 71, 73)   ← different input format
 *      Color.red()                  ← predefined constant
 *
 *  In TypeScript: same pattern — static fromHex(hex): Color
 *  In Java:       static Color fromHex(String hex)
 */

class Color {
    private final int r;
    private final int g;
    private final int b;
    private final double a;

    // This constructor is separate from the private 4-argument constructor.
    // Calling a private constructor with `this(...)` is allowed because the call
    // happens inside the same class.
    //
    // Important: privacy does NOT "flow" through constructor chaining.
    // This 3-argument constructor remains package-private because it has no
    // access modifier, even though it delegates to a private constructor.
    Color(int r, int g, int b) {
        this(r, g, b, 1.0);
    }
    private Color(int r, int g, int b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    // Factory method: from hex string like "#FF7F50"
    static Color fromHex(String hex) {
    String cleaned = hex.replace("#", "");
    int r = Integer.parseInt(cleaned.substring(0, 2), 16);
    int g = Integer.parseInt(cleaned.substring(2, 4), 16);
    int b = Integer.parseInt(cleaned.substring(4, 6), 16);
    return new Color(r, g, b);
}

    // Factory method: from HSL values (h: 0-360, s: 0-100, l: 0-100)
    static Color fromHSL(double h, double s, double l) {
        s /= 100.0;
        l /= 100.0;
        double a = s * Math.min(l, 1 - l);

        int rVal = (int) Math.round(hslComponent(0, h, l, a) * 255);
        int gVal = (int) Math.round(hslComponent(8, h, l, a) * 255);
        int bVal = (int) Math.round(hslComponent(4, h, l, a) * 255);
        return new Color(rVal, gVal, bVal);
    }

    // Helper for HSL conversion
    private static double hslComponent(double n, double h, double l, double a) {
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
        return String.format("#%02x%02x%02x", r, g, b);
    }

    int getR() { return r; }
    int getG() { return g; }
    int getB() { return b; }
    double getA() { return a; }

    @Override
    public String toString() {
        return String.format("rgba(%d, %d, %d, %.1f)", r, g, b, a);
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Static + Instance Working Together
// ─────────────────────────────────────────────
/*
 *  ProductItem auto-registers itself into a static registry
 *  when constructed. The class provides static methods to
 *  query the entire collection:
 *
 *    ProductItem.findByCategory("Books")   ← static (works on ALL products)
 *    product.toString()                    ← instance (works on ONE product)
 *
 *  This pattern is sometimes called the "Active Record" or
 *  "Self-Registering" pattern.
 */

class ProductItem {
    private static final List<ProductItem> allProducts = new ArrayList<>();
    private static int nextId = 1;

    private final int id;
    private final String name;
    private final double price;
    private final String category;

    ProductItem(String name, double price, String category) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
        this.category = category;
        allProducts.add(this);  // Auto-register on construction
    }

    // Static method: get a copy of all products
    static List<ProductItem> getAll() {
        return new ArrayList<>(allProducts);
    }

    // Static method: filter by category
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

    // Static method: calculate the average price
    static double getAveragePrice() {
        if (allProducts.isEmpty()) return 0;
        double total = 0;
        for (ProductItem p : allProducts) {
            total += p.price;
        }
        return total / allProducts.size();
    }

    // Static method: count of all products
    static int getCount() {
        return allProducts.size();
    }

    int getId() { return id; }
    String getName() { return name; }
    double getPrice() { return price; }
    String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("[#%d] %s - $%.2f (%s)", id, name, price, category);
    }
}

// ─────────────────────────────────────────────
// 5️⃣  Static Constants
// ─────────────────────────────────────────────
/*
 *  In TypeScript:  static readonly OK = 200;
 *  In Java:        static final int OK = 200;
 *
 *  `static final` fields are constants — set once, never changed.
 *  Convention: name them in UPPER_SNAKE_CASE.
 *
 *  This is extremely common in Java: think of
 *  Integer.MAX_VALUE, Math.PI, HttpURLConnection.HTTP_OK, etc.
 */

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
        switch (code) {
            case OK:                    return "OK";
            case CREATED:               return "Created";
            case BAD_REQUEST:           return "Bad Request";
            case UNAUTHORIZED:          return "Unauthorized";
            case FORBIDDEN:             return "Forbidden";
            case NOT_FOUND:             return "Not Found";
            case INTERNAL_SERVER_ERROR: return "Internal Server Error";
            default:                    return "Unknown Status";
        }
    }
}

// ═══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Contains main() entry point
// ═══════════════════════════════════════════════════════════════

public class StaticMembers {
    public static void main(String[] args) {

        // ═══ 1. Static Properties ═══
        System.out.println("═══ 1. Static Properties ═══");
        System.out.println("Total players before: " + Player.getTotalPlayers());  // 0

        // Constructor chaining still ends up running the two-argument constructor,
        // so the static counter is updated there exactly once.
        Player p1 = new Player("Alice");
        System.out.println("After creating p1 via Player(String), total players: " + Player.getTotalPlayers());
        Player p2 = new Player("Bob");
        Player p3 = new Player("Charlie");
        p1.addScore(10);
        System.out.println(p1);                                                    // Player #1: Alice (Score: 10)
        System.out.println(p2);                                                    // Player #2: Bob (Score: 0)
        System.out.println(p3);                                                    // Player #3: Charlie (Score: 0)
        System.out.println("Total players after: " + Player.getTotalPlayers());    // 3

        // Note: You access static members via the CLASS, not the object:
        // Player.getTotalPlayers() ✅
         p1.getTotalPlayers();    // ⚠️ Compiles but is misleading — avoid this!
         // Java lets you CALL a static method through an object reference,
         // but the method still belongs to the class, not the object.
         // This line compiles, but Java resolves it as Player.getTotalPlayers().
         // So you're not getting a per-object value here — you're accessing
         // the one shared class-level counter through a misleading syntax.
         System.out.println(p1.getTotalPlayers());
         System.out.println(Player.getTotalPlayers());
        // ═══ 2. Static Methods ═══
        System.out.println("\n═══ 2. Static Methods ═══");
        // No `new MathUtils()` needed — call directly on the class!
        System.out.println("clamp(150, 0, 100): " + MathUtils.clamp(150, 0, 100));          // 100.0
        System.out.println("lerp(0, 100, 0.5): " + MathUtils.lerp(0, 100, 0.5));            // 50.0
        System.out.println("randomInt(1, 10): " + MathUtils.randomInt(1, 10));               // (random 1-10)
        System.out.println("isPrime(17): " + MathUtils.isPrime(17));                          // true
        System.out.println("isPrime(15): " + MathUtils.isPrime(15));                          // false
        System.out.printf("degreesToRadians(180): %.4f%n", MathUtils.degreesToRadians(180));  // 3.1416

        // ═══ 3. Factory Methods ═══
        System.out.println("\n═══ 3. Factory Methods ═══");
        Color coral = Color.fromHex("#FF7F50");
        System.out.println("From hex #FF7F50: " + coral);                                     // rgba(255, 127, 80, 1.0)

        Color skyBlue = Color.fromHSL(197, 71, 73);
        System.out.println("From HSL(197, 71, 73): " + skyBlue + " → " + skyBlue.toHex());   // rgba(...) → #87ceeb

        System.out.println("Predefined red: " + Color.red());                                 // rgba(255, 0, 0, 1.0)
        System.out.println("Predefined blue: " + Color.blue().toHex());                       // #0000ff

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
        System.out.printf("Average price: $%.2f%n", ProductItem.getAveragePrice());
        System.out.println("Total products: " + ProductItem.getCount());

        // ═══ 5. Static Constants ═══
        System.out.println("\n═══ 5. Static Constants ═══");
        System.out.println(HttpStatus.OK + ": " + HttpStatus.getMessage(HttpStatus.OK));
        // 200: OK
        System.out.println(HttpStatus.NOT_FOUND + ": " + HttpStatus.getMessage(HttpStatus.NOT_FOUND));
        // 404: Not Found
        System.out.println(HttpStatus.INTERNAL_SERVER_ERROR + ": " + HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR));
        // 500: Internal Server Error

        // ─────────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────────
        // ✅ `static` members belong to the CLASS, not to individual objects.
        // ✅ Access via `ClassName.member`, NOT `instance.member`.
        // ✅ Static fields are shared — great for counters, registries, configs.
        // ✅ Static methods = utility functions (no `this` for instance data).
        // ✅ Factory methods = static methods that return new instances.
        // ✅ `static final` = class-level constants (UPPER_SNAKE_CASE by convention).
        // ✅ Static and instance members can work together (e.g., auto-registering).
    }
}
