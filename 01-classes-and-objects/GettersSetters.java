/*
 * ═════════════════════════════════════════════════════════════
 *  📘 LESSON 3: Getters and Setters
 * ═════════════════════════════════════════════════════════════
 *
 *  WHAT ARE GETTERS & SETTERS?
 *  ───────────────────────────
 *  Getters and setters are methods that control how fields are
 *  READ (get) and WRITTEN (set).
 *
 *  WHY USE THEM?
 *  ─────────────
 *  • Validate data before storing it (e.g., age must be > 0)
 *  • Compute values on-the-fly (e.g., fullName = firstName + lastName)
 *  • Log or trigger side effects when a field changes
 *  • Make fields read-only by defining only a getter
 *
 *  JAVA vs TYPESCRIPT:
 *  ─────────────────────
 *  ┌─────────────────────────────────────┬─────────────────────────────────────┐
 *  │  TypeScript                         │  Java                               │
 *  ├─────────────────────────────────────┼─────────────────────────────────────┤
 *  │  get fullName(): string { ... }     │  String getFullName() { ... }       │
 *  │  set celsius(v: number) { ... }     │  void setCelsius(double v) { ... }  │
 *  │  person.fullName  (no parens)       │  person.getFullName()  (with ())    │
 *  │  temp.celsius = 100  (like field)   │  temp.setCelsius(100)  (method)     │
 *  └─────────────────────────────────────┴─────────────────────────────────────┘
 *
 *  Java does NOT have property syntax like TypeScript/C#.
 *  Instead, Java uses a naming convention:
 *    - getXxx() → getter method
 *    - setXxx() → setter method
 *    - isXxx()  → getter for boolean fields
 *
 *  This is called the "JavaBean convention" and is used everywhere
 *  in Java frameworks (Spring, Hibernate, Jackson, etc.).
 *
 * ═════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
// 1️⃣  Basic Getter — Computed Value
// ─────────────────────────────────────────────
//
// A Person stores firstName and lastName separately, but
// getFullName() computes and returns the combined value.
// In Java, this is just a regular method — no special syntax.

class Person {
    private String firstName;
    private String lastName;

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter for firstName
    String getFirstName() {
        return this.firstName;
    }

    // Setter for firstName
    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter for lastName
    String getLastName() {
        return this.lastName;
    }

    // Computed getter — no backing field, computes on the fly
    String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Getter + Setter with Validation
// ─────────────────────────────────────────────
//
// Temperature stores Celsius internally but provides
// getters and setters for Fahrenheit and Kelvin too.
// The setter validates that temperature can't go below absolute zero.

class Temperature {
    private double celsius;  // The single backing field

    Temperature(double celsius) {
        this.celsius = celsius;
    }

    // Getter for celsius
    double getCelsius() {
        return this.celsius;
    }

    // Setter with VALIDATION
    void setCelsius(double value) {
        if (value < -273.15) {
            throw new IllegalArgumentException(
                "Temperature cannot be below absolute zero (-273.15°C)!");
        }
        this.celsius = value;
    }

    // Computed getter — converts Celsius to Fahrenheit on the fly
    double getFahrenheit() {
        return (this.celsius * 9.0) / 5.0 + 32.0;
    }

    // Setter that accepts Fahrenheit and stores as Celsius
    void setFahrenheit(double value) {
        setCelsius((value - 32.0) * 5.0 / 9.0); // Calls setCelsius (with validation!)
    }

    // Computed getter — Celsius to Kelvin
    double getKelvin() {
        return this.celsius + 273.15;
    }

    @Override
    public String toString() {
        return String.format("%.1f°C | %.1f°F | %.1fK",
                this.celsius, getFahrenheit(), getKelvin());
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Read-Only Property (Getter Only, No Setter)
// ─────────────────────────────────────────────
//
// Circle has a mutable radius, but area, circumference, and
// diameter are computed from it. They only have getters —
// you cannot set them directly.

class Circle {
    private double radius;

    Circle(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("Radius must be positive!");
        this.radius = radius;
    }

    double getRadius() { 
        return this.radius;
    }

    void setRadius(double value) {
        if (value <= 0) throw new IllegalArgumentException("Radius must be positive!");
        this.radius = value;
    }

    // Read-only computed values — only getters, NO setters
    double getArea() {
        return Math.PI * this.radius * this.radius;
    }

    double getCircumference() {
        return 2 * Math.PI * this.radius;
    }

    double getDiameter() {
        return this.radius * 2;
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Setter with Side Effects (Logging)
// ─────────────────────────────────────────────
//
// Product tracks a history of all price changes.
// Every time setPrice() is called, it logs the change
// and records the old/new price in a history list.

class Product {
    private String name;
    private double price;
    private List<String> priceHistory = new ArrayList<>();

    Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.priceHistory.add(String.format("$%.2f (initial)", price));
    }

    String getName() {
        return this.name;
    }

    double getPrice() {
        return this.price;
    }

    void setPrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        System.out.printf("  📝 Price changed: $%.2f → $%.2f%n", this.price, newPrice);
        this.price = newPrice;
        this.priceHistory.add(String.format("$%.2f", newPrice));
    }

    String getFormattedPrice() {
        return String.format("$%.2f", this.price);
    }

    List<String> getPriceHistory() {
        return new ArrayList<>(this.priceHistory); // Return a copy
    }
}

// ─────────────────────────────────────────────
// 5️⃣  Real-World Example: Form Validation
// ─────────────────────────────────────────────
//
// UserProfile validates all data through setters:
//  - email must match a pattern and is normalized to lowercase
//  - username must be 3-20 chars, alphanumeric + underscores only
//  - age must be between 13 and 120

class UserProfile {
    private String email = "";
    private String username = "";
    private int age = 0;

    String getEmail() {
        return this.email;
    }

    void setEmail(String value) {
        if (!value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Invalid email: \"" + value + "\"");
        }
        this.email = value.toLowerCase(); // Normalize to lowercase
    }

    String getUsername() {
        return this.username;
    }

    void setUsername(String value) {
        if (value.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters!");
        }
        if (value.length() > 20) {
            throw new IllegalArgumentException("Username must be at most 20 characters!");
        }
        if (!value.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException(
                "Username can only contain letters, numbers, and underscores!");
        }
        this.username = value;
    }

    int getAge() {
        return this.age;
    }

    void setAge(int value) {
        if (value < 13 || value > 120) {
            throw new IllegalArgumentException("Age must be an integer between 13 and 120!");
        }
        this.age = value;
    }

    @Override
    public String toString() {
        return "@" + this.username + " (" + this.email + ", age " + this.age + ")";
    }
}

// ═════════════════════════════════════════════════════════════
// PUBLIC CLASS — File name must match: GettersSetters.java
// ═════════════════════════════════════════════════════════════

public class GettersSetters {
    public static void main(String[] args) {

        // ═══ 1. Basic Getter ═══
        System.out.println("═══ 1. Basic Getter ═══");

        Person person = new Person("Naman", "Garg");

        System.out.println(person.getFullName());  // Naman Garg — note the () parentheses!
        person.setFirstName("John");
        System.out.println(person.getFullName());  // John Garg — automatically updated
        System.out.println("📝 Java uses getXxx()/setXxx() methods — no property syntax like TS.");

        // ═══ 2. Getter + Setter with Validation ═══
        System.out.println("\n═══ 2. Getter + Setter with Validation ═══");

        Temperature temp = new Temperature(100);

        System.out.println("Boiling water: " + temp);                 // 100.0°C | 212.0°F | 373.2K

        temp.setCelsius(0);
        System.out.println("Freezing point: " + temp);                // 0.0°C | 32.0°F | 273.2K

        temp.setFahrenheit(98.6);
        System.out.println("Body temperature: " + temp);              // 37.0°C | 98.6°F | 310.2K

        // Try invalid temperature
        try {
            temp.setCelsius(-300); // Below absolute zero!
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Caught: " + e.getMessage());
        }

        // ═══ 3. Read-Only Computed Properties ═══
        System.out.println("\n═══ 3. Read-Only Computed Properties ═══");

        Circle circle = new Circle(5);

        System.out.println("Radius: " + circle.getRadius());                                  // 5.0
        System.out.println("Diameter: " + circle.getDiameter());                               // 10.0
        System.out.println(String.format("Area: %.2f", circle.getArea()));                     // 78.54
        System.out.println(String.format("Circumference: %.2f", circle.getCircumference()));   // 31.42

        circle.setRadius(10); // ✅ Setter allows this
        System.out.println("\nNew radius: " + circle.getRadius());                             // 10.0
        System.out.println(String.format("New area: %.2f", circle.getArea()));                  // 314.16

        // circle.setArea(50);  → ❌ No such method — area is read-only (getter only)

        // ═══ 4. Setter with Side Effects ═══
        System.out.println("\n═══ 4. Setter with Side Effects ═══");

        Product product = new Product("Java Course", 49.99);
        System.out.println(product.getName() + ": " + product.getFormattedPrice());
        // Java Course: $49.99

        product.setPrice(39.99); // Triggers setter → logs change + records history
        product.setPrice(29.99);
        product.setPrice(19.99);

        System.out.println("\nPrice history:");
        List<String> history = product.getPriceHistory();
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + history.get(i));
        }

        // ═══ 5. Form Validation with Setters ═══
        System.out.println("\n═══ 5. Form Validation with Setters ═══");

        UserProfile profile = new UserProfile();

        // Valid data
        profile.setUsername("naman_dev");
        profile.setEmail("Naman@Example.COM");
        profile.setAge(25);
        System.out.println("✅ Valid profile: " + profile);
        // @naman_dev (naman@example.com, age 25)

        // Invalid data — each one throws IllegalArgumentException
        String[] testLabels = {
            "short username", "bad email", "too young", "special chars in username"
        };

        Runnable[] invalidTests = {
            () -> profile.setUsername("ab"),           // Too short
            () -> profile.setEmail("not-an-email"),   // Invalid format
            () -> profile.setAge(5),                   // Too young
            () -> profile.setUsername("hello world!"), // Special characters
        };

        for (int i = 0; i < invalidTests.length; i++) {
            try {
                invalidTests[i].run();
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }

        // ─────────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────────
        // ✅ Java uses getXxx()/setXxx() methods — no `get`/`set` property syntax.
        // ✅ Convention: private backing field `name`, public getter `getName()`.
        // ✅ Use getters for computed/derived values (getArea(), getFullName()).
        // ✅ Use setters for validation, normalization, and side effects.
        // ✅ Getter-only = read-only computed value (no setter method exists).
        // ✅ This pattern is called the "JavaBean convention" — used everywhere in Java.
        // ✅ Use `isXxx()` instead of `getXxx()` for boolean getters.
    }
}
