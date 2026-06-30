/*
 * ═══════════════════════════════════════════════════════════════
 *  📘 LESSON 3: Getters and Setters
 * ═══════════════════════════════════════════════════════════════
 *
 *  WHAT ARE GETTERS & SETTERS?
 *  ───────────────────────────
 *  Getters and setters are methods that control how a field is
 *  READ (get) and WRITTEN (set).
 *
 *  In TypeScript/C#, you can define `get` and `set` keywords that
 *  make them look like properties:
 *      obj.fullName          // calls the getter
 *      obj.temperature = 50  // calls the setter
 *
 *  In Java, there is NO property syntax. Instead, we use explicit
 *  methods following the JavaBean naming convention:
 *      obj.getFullName()       // getter method
 *      obj.setTemperature(50)  // setter method
 *
 *  ┌──────────────────────┬────────────────────────────────────┐
 *  │  TypeScript           │  Java                             │
 *  ├──────────────────────┼────────────────────────────────────┤
 *  │  get fullName()       │  public String getFullName()      │
 *  │  set celsius(val)     │  public void setCelsius(double v) │
 *  │  obj.fullName         │  obj.getFullName()                │
 *  │  obj.celsius = 100    │  obj.setCelsius(100)              │
 *  └──────────────────────┴────────────────────────────────────┘
 *
 *  WHY USE THEM?
 *  ─────────────
 *  • Validate data before storing it (e.g., age must be > 0)
 *  • Compute values on-the-fly (e.g., fullName = first + last)
 *  • Log or trigger side effects when a field changes
 *  • Make fields read-only by providing only a getter
 *
 * ═══════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
// 1️⃣  Basic Getter — Computed Value
// ─────────────────────────────────────────────
/*
 *  In TypeScript:  get fullName(): string { ... }
 *                  person.fullName  ← looks like a property
 *
 *  In Java:        public String getFullName() { ... }
 *                  person.getFullName()  ← explicit method call
 *
 *  Java has no "computed property" syntax. Every getter is a
 *  regular method — you MUST use parentheses to call it.
 */

class Person {
    private String firstName;
    private String lastName;

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter — returns a computed value (no backing field needed)
    String getFullName() {
        return firstName + " " + lastName;
    }

    // Simple setter so we can update firstName
    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Getter + Setter with Validation
// ─────────────────────────────────────────────
/*
 *  Temperature class stores Celsius internally.
 *  - getCelsius() / setCelsius() with validation (not below -273.15)
 *  - getFahrenheit() / setFahrenheit() convert on the fly
 *  - getKelvin() computes from Celsius
 *
 *  The setter validates BEFORE storing — invalid data never
 *  enters the object. This is the #1 reason to use setters.
 */

class Temperature {
    private double celsius;  // Internal storage — always in Celsius

    Temperature(double celsius) {
        this.celsius = celsius;
    }

    // Getter for Celsius
    double getCelsius() {
        return celsius;
    }

    // Setter with VALIDATION
    void setCelsius(double value) {
        if (value < -273.15) {
            throw new IllegalArgumentException(
                "Temperature cannot be below absolute zero (-273.15°C)!");
        }
        this.celsius = value;
    }

    // Computed getter — converts on the fly
    double getFahrenheit() {
        return (celsius * 9.0) / 5.0 + 32.0;
    }

    // Setter that accepts Fahrenheit and stores as Celsius
    void setFahrenheit(double value) {
        setCelsius((value - 32.0) * 5.0 / 9.0);  // Calls setCelsius (with validation!)
    }

    // Read-only computed getter
    double getKelvin() {
        return celsius + 273.15;
    }

    @Override
    public String toString() {
        return String.format("%.1f°C | %.1f°F | %.1fK",
            celsius, getFahrenheit(), getKelvin());
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Read-Only Property (Getter Only, No Setter)
// ─────────────────────────────────────────────
/*
 *  Circle exposes area, circumference, and diameter as
 *  read-only computed values — only getters, NO setters.
 *
 *  In TypeScript:   circle.area        ← looks assignable but isn't
 *  In Java:         circle.getArea()   ← clearly read-only (no setArea method exists)
 *
 *  Java's explicit method naming actually makes the read-only
 *  intent MORE obvious than TypeScript's property syntax!
 */

class Circle {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double getRadius() {
        return radius;
    }

    void setRadius(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = value;
    }

    // Read-only computed getters — no corresponding setters
    double getArea() {
        return Math.PI * radius * radius;
    }

    double getCircumference() {
        return 2 * Math.PI * radius;
    }

    double getDiameter() {
        return radius * 2;
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Setter with Side Effects (Logging)
// ─────────────────────────────────────────────
/*
 *  Product tracks every price change in a history list.
 *  Each call to setPrice():
 *    1. Validates (price >= 0)
 *    2. Logs the change to console
 *    3. Records the new price in priceHistory
 *
 *  This pattern is common in real apps: auditing, undo/redo,
 *  event sourcing, analytics tracking, etc.
 */

class PriceEntry {
    final double price;
    final long timestamp;

    PriceEntry(double price) {
        this.price = price;
        this.timestamp = System.currentTimeMillis();
    }
}

class Product {
    private String name;
    private double price;
    private final List<PriceEntry> priceHistory = new ArrayList<>();

    Product(String name, double price) {
        this.name = name;
        this.price = price;
        priceHistory.add(new PriceEntry(price));
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        System.out.printf("  📝 Price changed: $%.2f → $%.2f%n", this.price, newPrice);
        this.price = newPrice;
        priceHistory.add(new PriceEntry(newPrice));
    }

    String getFormattedPrice() {
        return String.format("$%.2f", price);
    }

    List<String> getPriceHistory() {
        List<String> history = new ArrayList<>();
        for (int i = 0; i < priceHistory.size(); i++) {
            history.add(String.format("  %d. $%.2f", i + 1, priceHistory.get(i).price));
        }
        return history;
    }
}

// ─────────────────────────────────────────────
// 5️⃣  Real-World: Form Validation
// ─────────────────────────────────────────────
/*
 *  UserProfile validates every field in its setter:
 *    - setEmail()    → regex check, normalize to lowercase
 *    - setUsername()  → length 3-20, alphanumeric + underscores only
 *    - setAge()      → integer between 13 and 120
 *
 *  Each setter throws IllegalArgumentException on bad input.
 *  The object can NEVER hold invalid data — this is the
 *  "always valid" design pattern.
 */

class UserProfile {
    private String email = "";
    private String username = "";
    private int age = 0;

    String getEmail() {
        return email;
    }

    void setEmail(String value) {
        // Simple email regex: non-whitespace @ non-whitespace . non-whitespace
        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Invalid email: \"" + value + "\"");
        }
        this.email = value.toLowerCase();  // Normalize to lowercase
    }

    String getUsername() {
        return username;
    }

    void setUsername(String value) {
        if (value.length() < 3) {
            throw new IllegalArgumentException(
                "Username must be at least 3 characters!");
        }
        if (value.length() > 20) {
            throw new IllegalArgumentException(
                "Username must be at most 20 characters!");
        }
        if (!value.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException(
                "Username can only contain letters, numbers, and underscores!");
        }
        this.username = value;
    }

    int getAge() {
        return age;
    }

    void setAge(int value) {
        if (value < 13 || value > 120) {
            throw new IllegalArgumentException(
                "Age must be an integer between 13 and 120!");
        }
        this.age = value;
    }

    @Override
    public String toString() {
        return "@" + username + " (" + email + ", age " + age + ")";
    }
}

// ═══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Contains main() entry point
// ═══════════════════════════════════════════════════════════════

public class GettersSetters {
    public static void main(String[] args) {

        // ═══ 1. Basic Getter ═══
        System.out.println("═══ 1. Basic Getter ═══");
        Person person = new Person("Naman", "Garg");
        System.out.println(person.getFullName());     // Naman Garg
        person.setFirstName("John");
        System.out.println(person.getFullName());     // John Garg — automatically updated

        // ═══ 2. Getter + Setter with Validation ═══
        System.out.println("\n═══ 2. Getter + Setter with Validation ═══");
        Temperature temp = new Temperature(100);
        System.out.println("Boiling water: " + temp);                    // 100.0°C | 212.0°F | 373.1K

        temp.setCelsius(0);
        System.out.println("Freezing point: " + temp);                   // 0.0°C | 32.0°F | 273.1K

        temp.setFahrenheit(98.6);
        System.out.println("Body temperature: " + temp);                 // 37.0°C | 98.6°F | 310.2K

        // Try invalid temperature
        try {
            temp.setCelsius(-300);  // Below absolute zero!
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Caught: " + e.getMessage());
        }

        // ═══ 3. Read-Only Computed Properties ═══
        System.out.println("\n═══ 3. Read-Only Computed Properties ═══");
        Circle circle = new Circle(5);
        System.out.println("Radius: " + circle.getRadius());                             // 5.0
        System.out.println("Diameter: " + circle.getDiameter());                          // 10.0
        System.out.printf("Area: %.2f%n", circle.getArea());                              // 78.54
        System.out.printf("Circumference: %.2f%n", circle.getCircumference());            // 31.42

        circle.setRadius(10);  // ✅ Setter allows this
        System.out.println("\nNew radius: " + circle.getRadius());                        // 10.0
        System.out.printf("New area: %.2f%n", circle.getArea());                          // 314.16

        // circle.setArea(50);  // ❌ No such method — area is read-only (getter only)

        // ═══ 4. Setter with Side Effects ═══
        System.out.println("\n═══ 4. Setter with Side Effects ═══");
        Product product = new Product("Java Course", 49.99);
        System.out.println(product.getName() + ": " + product.getFormattedPrice());       // Java Course: $49.99

        product.setPrice(39.99);  // Triggers setter → logs change + records history
        product.setPrice(29.99);
        product.setPrice(19.99);

        System.out.println("\nPrice history:");
        for (String entry : product.getPriceHistory()) {
            System.out.println(entry);
        }

        // ═══ 5. Form Validation with Setters ═══
        System.out.println("\n═══ 5. Form Validation with Setters ═══");
        UserProfile profile = new UserProfile();

        // Valid data
        profile.setUsername("naman_dev");
        profile.setEmail("Naman@Example.COM");
        profile.setAge(25);
        System.out.println("✅ Valid profile: " + profile);       // @naman_dev (naman@example.com, age 25)

        // Invalid data — each one throws
        Runnable[] invalidTests = {
            () -> profile.setUsername("ab"),               // Too short
            () -> profile.setEmail("not-an-email"),        // Invalid format
            () -> profile.setAge(5),                       // Too young
            () -> profile.setUsername("hello world!"),      // Special characters
        };

        for (Runnable test : invalidTests) {
            try {
                test.run();
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }

        // ─────────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────────
        // ✅ Java uses explicit getXxx()/setXxx() methods — no property syntax.
        // ✅ Convention: private field + public getter/setter = encapsulation.
        // ✅ Use getters for computed/derived values (getArea(), getFullName()).
        // ✅ Use setters for validation, normalization, and side effects.
        // ✅ Getter-only = read-only (no setArea() method exists).
        // ✅ Throw IllegalArgumentException in setters for invalid input.
        // ✅ The caller always uses method calls: obj.getX() and obj.setX(val).
    }
}
