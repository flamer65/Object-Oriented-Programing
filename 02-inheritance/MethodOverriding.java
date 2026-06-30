/*
 * ═══════════════════════════════════════════════════════════════
 *  📘 MODULE 02 — LESSON 3: Method Overriding
 * ═══════════════════════════════════════════════════════════════
 *
 *  WHAT IS METHOD OVERRIDING?
 *  ──────────────────────────
 *  When a child class defines a method with the SAME NAME and
 *  SAME SIGNATURE as a parent method, the child's version
 *  REPLACES (overrides) the parent's version for objects of
 *  the child type.
 *
 *  WHY OVERRIDE METHODS?
 *  ─────────────────────
 *  • Customize inherited behavior for the child's specific needs
 *  • The parent defines WHAT to do, the child defines HOW to do it
 *  • Example: Every Shape has area(), but Circle and Rectangle
 *    calculate area differently
 *
 *  USING super.method():
 *  ─────────────────────
 *  Sometimes you don't want to FULLY replace the parent method —
 *  you want to EXTEND it. Use super.method() to call the parent's
 *  version, then add your own logic.
 *
 *  JAVA vs TypeScript:
 *  ───────────────────
 *  • Java uses the @Override annotation (compile-time safety)
 *  • TypeScript uses the `override` keyword (TS 4.3+)
 *  • Both catch typos — if the parent doesn't have the method,
 *    the compiler gives an error
 *
 * ═══════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────
// 1️⃣  Basic Method Overriding
// ─────────────────────────────────────────────

/*
 * Parent class — defines generic animal behavior.
 * makeSound() returns a generic message.
 * Each child will override it with its own specific sound.
 */
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    // Parent defines a generic behavior
    String makeSound() {
        return this.name + " makes a generic sound";
    }

    String describe() {
        return "Animal: " + this.name;
    }
}

/*
 * Dog OVERRIDES both makeSound() and describe().
 * The child's version completely replaces the parent's.
 */
class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    // OVERRIDE — replaces parent's makeSound() entirely
    @Override
    String makeSound() {
        return this.name + " barks: Woof! Woof! 🐕";
    }

    // OVERRIDE — replaces parent's describe()
    @Override
    String describe() {
        return "Dog: " + this.name + " (" + this.breed + ")";
    }
}

class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    @Override
    String makeSound() {
        return this.name + " purrs: Prrrr... 🐱";
    }

    @Override
    String describe() {
        return "Cat: " + this.name;
    }
}

class Snake extends Animal {
    Snake(String name) {
        super(name);
    }

    @Override
    String makeSound() {
        return this.name + " hisses: Ssssss... 🐍";
    }
    // Snake does NOT override describe() — it uses Animal's version
}

// ─────────────────────────────────────────────
// 2️⃣  Using super.method() — Extending Parent Behavior
// ─────────────────────────────────────────────

/*
 * Logger chain: Logger → PrefixedLogger → ColorLogger
 * Each level calls super.log() to EXTEND (not replace) the parent.
 */
class Logger {
    String log(String message) {
        String timestamp = java.time.LocalTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        return "[" + timestamp + "] " + message;
    }
}

class PrefixedLogger extends Logger {
    private String prefix;

    PrefixedLogger(String prefix) {
        this.prefix = prefix;
    }

    // OVERRIDE + EXTEND — calls parent's log() then adds prefix
    @Override
    String log(String message) {
        String baseLog = super.log(message);  // ← Call parent's version
        return this.prefix + " " + baseLog;
    }
}

class ColorLogger extends PrefixedLogger {
    private String color;

    ColorLogger(String prefix, String color) {
        super(prefix);
        this.color = color;
    }

    // OVERRIDE + EXTEND — calls parent's log() which calls grandparent's log()
    @Override
    String log(String message) {
        String prefixedLog = super.log(message);  // ← Call PrefixedLogger's version
        return "[" + this.color + "] " + prefixedLog;
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Override toString() — Built-in Method
// ─────────────────────────────────────────────
//
// Every object in Java inherits toString() from java.lang.Object.
// Override it to customize how your objects display as strings.
// println() and string concatenation automatically call toString().

class Money {
    private double amount;
    private String currency;

    Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    Money(double amount) {
        this(amount, "USD");
    }

    // Override the built-in toString()
    @Override
    public String toString() {
        String symbol;
        switch (this.currency) {
            case "USD": symbol = "$"; break;
            case "EUR": symbol = "€"; break;
            case "GBP": symbol = "£"; break;
            case "INR": symbol = "₹"; break;
            case "JPY": symbol = "¥"; break;
            default:    symbol = this.currency; break;
        }
        return symbol + String.format("%.2f", this.amount);
    }

    Money add(Money other) {
        return new Money(this.amount + other.amount, this.currency);
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Real-World: Notification System
// ─────────────────────────────────────────────

/*
 * Notification — base class using the Template Method pattern.
 * send() and format() are overridden by children.
 * deliver() calls both — children customize behavior without
 * changing the delivery algorithm.
 */
class Notification {
    String title;
    String message;
    String recipient;

    Notification(String title, String message, String recipient) {
        this.title = title;
        this.message = message;
        this.recipient = recipient;
    }

    // Base method — children override this
    String send() {
        return "📨 Sending to " + this.recipient + ": \"" + this.title + "\"";
    }

    // Format method — children can override for different formats
    String format() {
        return this.title + "\n" + this.message;
    }

    // Template method pattern — calls overrideable methods
    String deliver() {
        String formatted = this.format();
        String result = this.send();
        return result + "\n   Content: " + formatted.replace("\n", " | ");
    }
}

class EmailNotification extends Notification {
    private String[] cc;

    EmailNotification(String title, String message, String recipient, String[] cc) {
        super(title, message, recipient);
        this.cc = cc;
    }

    EmailNotification(String title, String message, String recipient) {
        this(title, message, recipient, new String[0]);
    }

    @Override
    String send() {
        String ccInfo = "";
        if (this.cc.length > 0) {
            ccInfo = " (CC: " + String.join(", ", this.cc) + ")";
        }
        return "📧 Email to " + this.recipient + ccInfo + ": \"" + this.title + "\"";
    }

    @Override
    String format() {
        return "Subject: " + this.title + " | Dear User, | " + this.message + " | Best regards.";
    }
}

class SMSNotification extends Notification {
    SMSNotification(String title, String message, String recipient) {
        super(title, message, recipient);
    }

    @Override
    String send() {
        return "📱 SMS to " + this.recipient + ": \"" + this.title + "\"";
    }

    // SMS has a character limit — override format to truncate
    @Override
    String format() {
        String content = this.title + ": " + this.message;
        if (content.length() > 160) {
            return content.substring(0, 157) + "...";
        }
        return content;
    }
}

class PushNotification extends Notification {
    private String icon;

    PushNotification(String title, String message, String recipient, String icon) {
        super(title, message, recipient);
        this.icon = icon;
    }

    PushNotification(String title, String message, String recipient) {
        this(title, message, recipient, "🔔");
    }

    @Override
    String send() {
        return this.icon + " Push to " + this.recipient + ": \"" + this.title + "\"";
    }
}

// ─────────────────────────────────────────────
// 5️⃣  Constructor Call Order — super() chain
// ─────────────────────────────────────────────

class Base {
    int value;

    Base(int value) {
        System.out.println("  Base constructor: value = " + value);
        this.value = value;
    }
}

class Middle extends Base {
    int multiplier;

    Middle(int value, int multiplier) {
        super(value);  // Parent constructor runs HERE
        System.out.println("  Middle constructor: after super, multiplier = " + multiplier);
        this.multiplier = multiplier;
    }
}

class Child extends Middle {
    String label;

    Child(int value, int multiplier, String label) {
        super(value, multiplier);  // Parent constructor runs HERE (which calls grandparent)
        System.out.println("  Child constructor: after super, label = \"" + label + "\"");
        this.label = label;
    }

    String result() {
        return this.label + ": " + this.value + " × " + this.multiplier
             + " = " + (this.value * this.multiplier);
    }
}

// ─────────────────────────────────────────────
// 6️⃣  @Override Annotation — Compile-Time Safety
// ─────────────────────────────────────────────

/*
 * In Java, @Override is an ANNOTATION that tells the compiler:
 *   "I intend to override a method from the parent class."
 *
 * If the parent does NOT have a matching method, you get a
 * COMPILE ERROR — catching typos before runtime!
 *
 * This is equivalent to TypeScript 4.3+'s `override` keyword.
 */
class BaseWidget {
    String render() {
        return "BaseWidget";
    }

    void update() {
        // base update logic
    }
}

class FancyWidget extends BaseWidget {
    // @Override tells the compiler: "I intend to override render()"
    // If BaseWidget doesn't have render(), this is a compile ERROR
    @Override
    String render() {
        return "✨ FancyWidget ✨";
    }

    @Override
    void update() {
        // custom update logic
    }

    // This would give a COMPILE ERROR if uncommented:
    // @Override
    // String rendr() {       // ❌ Typo! BaseWidget has no "rendr" method
    //     return "oops";
    // }
}

// ═══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (must match file name)
// ═══════════════════════════════════════════════════════════════
public class MethodOverriding {
    public static void main(String[] args) {

        // ─────────────────────────────────────────
        // 1️⃣  Basic Method Overriding
        // ─────────────────────────────────────────
        System.out.println("═══ 1. Basic Method Overriding ═══");

        Animal[] animals = new Animal[] {
            new Dog("Buddy", "Golden Retriever"),
            new Cat("Whiskers"),        
            new Snake("Slinky"),
            new Animal("Unknown Creature")
        };

        // Each animal uses ITS OWN version of makeSound() and describe()
        for (Animal animal : animals) {
            System.out.println(animal.describe());
            System.out.println("  → " + animal.makeSound());
        }
        // Dog: Buddy (Golden Retriever)
        //   → Buddy barks: Woof! Woof! 🐕
        // Cat: Whiskers
        //   → Whiskers purrs: Prrrr... 🐱
        // Animal: Slinky          ← Snake didn't override describe()
        //   → Slinky hisses: Ssssss... 🐍
        // Animal: Unknown Creature
        //   → Unknown Creature makes a generic sound

        // ─────────────────────────────────────────
        // 2️⃣  Using super.method() — Extending Parent Behavior
        // ─────────────────────────────────────────
        System.out.println("\n═══ 2. super.method() — Extending Behavior ═══");

        Logger basicLogger = new Logger();
        PrefixedLogger appLogger = new PrefixedLogger("[APP]");
        ColorLogger errorLogger = new ColorLogger("[ERROR]", "🔴");

        System.out.println(basicLogger.log("Server started"));
        // [HH:mm:ss] Server started
        System.out.println(appLogger.log("User logged in"));
        // [APP] [HH:mm:ss] User logged in
        System.out.println(errorLogger.log("Connection failed"));
        // [🔴] [ERROR] [HH:mm:ss] Connection failed

        // ─────────────────────────────────────────
        // 3️⃣  Override toString() — Built-in Method
        // ─────────────────────────────────────────
        System.out.println("\n═══ 3. Overriding toString() ═══");

        Money price = new Money(99.99, "USD");
        Money tax = new Money(8.50, "USD");
        Money total = price.add(tax);

        // toString() is called automatically in string concatenation
        System.out.println("Price: " + price);     // Price: $99.99
        System.out.println("Tax: " + tax);          // Tax: $8.50
        System.out.println("Total: " + total);      // Total: $108.49
        System.out.println("INR: " + new Money(7500, "INR"));  // INR: ₹7500.00

        // ─────────────────────────────────────────
        // 4️⃣  Real-World: Notification System
        // ─────────────────────────────────────────
        System.out.println("\n═══ 4. Notification System ═══");

        Notification[] notifications = {
            new EmailNotification("Welcome!", "Thanks for signing up.",
                    "naman@email.com", new String[]{"admin@email.com"}),
            new SMSNotification("Verify", "Your code is 123456", "+91-9876543210"),
            new PushNotification("New Message", "You have 3 new messages", "user_naman", "💬"),
        };

        for (Notification notif : notifications) {
            System.out.println(notif.deliver());
            System.out.println();
        }
        // 📧 Email to naman@email.com (CC: admin@email.com): "Welcome!"
        //    Content: Subject: Welcome! | Dear User, | Thanks for signing up. | Best regards.
        //
        // 📱 SMS to +91-9876543210: "Verify"
        //    Content: Verify: Your code is 123456
        //
        // 💬 Push to user_naman: "New Message"
        //    Content: New Message | You have 3 new messages

        // ─────────────────────────────────────────
        // 5️⃣  Constructor Call Order — super() chain
        // ─────────────────────────────────────────
        System.out.println("═══ 5. Constructor Call Order ═══");
        Child child = new Child(5, 3, "Test");
        System.out.println("Result: " + child.result());
        // Notice the order:
        // 1. Child constructor calls super() → Middle constructor starts
        // 2. Middle constructor calls super() → Base constructor runs
        // 3. Base constructor finishes
        // 4. Middle constructor finishes
        // 5. Child constructor finishes
        // In Java, super() must be FIRST — so parent always runs before child!

        // ─────────────────────────────────────────
        // 6️⃣  @Override Annotation — Compile-Time Safety
        // ─────────────────────────────────────────
        System.out.println("\n═══ 6. @Override Annotation ═══");
        FancyWidget widget = new FancyWidget();
        System.out.println(widget.render());  // ✨ FancyWidget ✨

        System.out.println("\n📝 @Override is Java's compile-time safety for method overriding:");
        System.out.println("   - Marks a method as intentionally overriding a parent method");
        System.out.println("   - If the parent doesn't have the method → COMPILE ERROR");
        System.out.println("   - Catches typos like rendr() instead of render()");
        System.out.println("   - Equivalent to TypeScript 4.3+'s `override` keyword");
        System.out.println("   - Best practice: ALWAYS use @Override when overriding!");

        // ─────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────
        System.out.println("\n💡 KEY TAKEAWAYS");
        System.out.println("✅ Override = child defines same method signature as parent → replaces it.");
        System.out.println("✅ super.method() calls the PARENT's version of an overridden method.");
        System.out.println("✅ Override toString() to customize string representation.");
        System.out.println("✅ Constructor order: child calls super() → parent constructor runs first.");
        System.out.println("✅ Use @Override annotation to catch typos and make intent explicit.");
        System.out.println("✅ Overriding enables POLYMORPHISM — same method name, different behavior.");
        System.out.println("✅ In Java, super() must be the FIRST statement in a constructor.");
    }
}
