/*
 * ═══════════════════════════════════════════════════════════════════════════
 * 📘 MODULE 04 — LESSON 2: ADVANCED ABSTRACT CLASSES
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Now that you know the basics of abstract classes, let's explore
 * advanced patterns that make them truly powerful in real-world Java.
 *
 * We'll cover:
 *   🔗 Multi-level abstract hierarchies (abstract extends abstract)
 *   🏭 Static factory methods in abstract classes
 *   🤝 Combining abstract classes with interfaces
 *   🎮 A full real-world example: Game Character System
 *
 * ┌──────────────────────────────────────────────────────────────────────┐
 * │  Pattern                  │  What It Does                           │
 * ├──────────────────────────────────────────────────────────────────────┤
 * │  Multi-Level Hierarchy    │  Abstract → Abstract → Concrete chain  │
 * │  Static Factory           │  Abstract class creates its own        │
 * │                           │  subclass instances via a static method│
 * │  Abstract + Interface     │  Abstract class partially implements   │
 * │                           │  an interface, subclasses finish it    │
 * │  Game Character System    │  Real-world modeling with abstract     │
 * │                           │  base, polymorphic array, interactions │
 * └──────────────────────────────────────────────────────────────────────┘
 *
 * ═══════════════════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────────────────────────────────
// 1️⃣  MULTI-LEVEL ABSTRACT HIERARCHY
// ─────────────────────────────────────────────────────────────────────────
/*
 * An abstract class can extend another abstract class!
 * Each level can add more fields, more concrete methods, and
 * defer its own abstract methods to the next level.
 *
 *   Animal (abstract)          ← Level 1: most general
 *     │  name
 *     │  abstract getSound()
 *     │  concrete describe()
 *     │
 *     └── Pet (abstract)       ← Level 2: adds pet-specific state
 *           │  ownerName
 *           │  concrete getOwnerInfo()
 *           │  still abstract getSound()  ← still not implemented!
 *           │
 *           └── Dog (concrete)  ← Level 3: finally implements everything
 *                 getSound() → "Woof!"
 *
 * Only the FIRST CONCRETE class must implement ALL remaining
 * abstract methods from the entire chain.
 */

abstract class AnimalBase {
    private final String name;

    AnimalBase(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    // Abstract — deferred to subclasses
    abstract String getSound();

    // Concrete — available to all subclasses
    String describe() {
        return getName() + " says " + getSound();
    }
}

// Abstract class extending abstract class — perfectly valid!
abstract class Pet extends AnimalBase {
    private final String ownerName;

    Pet(String name, String ownerName) {
        super(name);    // Calls AnimalBase constructor
        this.ownerName = ownerName;
    }

    String getOwnerName() {
        return ownerName;
    }

    // Concrete method added at this level
    String getOwnerInfo() {
        return getName() + " belongs to " + ownerName;
    }

    // NOTE: getSound() is still abstract — we don't implement it here!
    // The next concrete subclass MUST implement it.
}

// First CONCRETE class — must implement ALL abstract methods
class Dog extends Pet {
    private final String breed;

    Dog(String name, String ownerName, String breed) {
        super(name, ownerName);  // Calls Pet constructor → AnimalBase constructor
        this.breed = breed;
    }

    String getBreed() {
        return breed;
    }

    @Override
    String getSound() {
        return "Woof!";
    }

    @Override
    String describe() {
        return getName() + " the " + breed + " says " + getSound();
    }
}

class Cat extends Pet {
    Cat(String name, String ownerName) {
        super(name, ownerName);
    }

    @Override
    String getSound() {
        return "Meow!";
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 2️⃣  ABSTRACT CLASS WITH STATIC FACTORY
// ─────────────────────────────────────────────────────────────────────────
/*
 * A static factory method lives on the abstract class itself and
 * returns concrete subclass instances based on input.
 *
 * The caller doesn't need to know WHICH concrete class is created —
 * they just call Notification.create("email") and get back the
 * right type. This is encapsulation at its finest!
 *
 *   Notification (abstract)
 *     │  static create(type) ──┬──→ EmailNotification
 *     │  abstract send()       └──→ SMSNotification
 *     │  concrete getType()
 *     │
 *     ├── EmailNotification
 *     └── SMSNotification
 */

abstract class Notification {
    private final String type;

    Notification(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

    // Abstract — each notification type sends differently
    abstract void send(String message);

    // Static factory — creates the right subclass based on type
    static Notification create(String type) {
        switch (type.toLowerCase()) {
            case "email":
                return new EmailNotification();
            case "sms":
                return new SMSNotification();
            default:
                throw new IllegalArgumentException("Unknown notification type: " + type);
        }
    }
}

class EmailNotification extends Notification {
    EmailNotification() {
        super("Email");
    }

    @Override
    void send(String message) {
        System.out.println("   📧 Sending EMAIL: " + message);
    }
}

class SMSNotification extends Notification {
    SMSNotification() {
        super("SMS");
    }

    @Override
    void send(String message) {
        System.out.println("   📱 Sending SMS: " + message);
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 3️⃣  COMBINING ABSTRACT CLASSES AND INTERFACES
// ─────────────────────────────────────────────────────────────────────────
/*
 * An abstract class can implement an interface, and it doesn't
 * have to implement ALL the interface methods — it can leave some
 * for its concrete subclasses!
 *
 *   Interface: Loggable
 *     │  void log(String msg)
 *     │
 *     ▼
 *   AbstractLogger (abstract, implements Loggable)
 *     │  prefix field
 *     │  concrete formatMessage()  ← helper for subclasses
 *     │  log() still abstract!     ← deferred to subclasses
 *     │
 *     ├── FileLogger      → log() writes to "file"
 *     └── ConsoleLogger   → log() prints to console
 *
 * This pattern gives you:
 *   ✅ Interface contract (Loggable)
 *   ✅ Shared state and helpers (AbstractLogger)
 *   ✅ Custom implementations (FileLogger, ConsoleLogger)
 */

interface Loggable {
    void log(String msg);
}

abstract class AbstractLogger implements Loggable {
    protected String prefix;

    AbstractLogger(String prefix) {
        this.prefix = prefix;
    }

    // Concrete helper method — subclasses use this
    String formatMessage(String msg) {
        return "[" + prefix + "] " + msg;
    }

    // NOTE: log() from Loggable is still abstract here!
    // We don't implement it — subclasses must.
}

class FileLogger extends AbstractLogger {
    FileLogger() {
        super("FILE");
    }

    @Override
    public void log(String msg) {
        System.out.println("   💾 " + formatMessage(msg) + " → written to log file");
    }
}

class ConsoleLogger extends AbstractLogger {
    ConsoleLogger() {
        super("CONSOLE");
    }

    @Override
    public void log(String msg) {
        System.out.println("   🖥️  " + formatMessage(msg));
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 4️⃣  REAL-WORLD: GAME CHARACTER SYSTEM
// ─────────────────────────────────────────────────────────────────────────
/*
 * Let's build a game character system that showcases abstract classes
 * in a real-world scenario.
 *
 *   GameCharacter (abstract)
 *     │  name, hp, maxHp        ← shared state
 *     │  abstract attack()       ← each class attacks differently
 *     │  abstract specialAbility() ← unique special move
 *     │  concrete takeDamage()   ← shared damage logic
 *     │  concrete isAlive()      ← shared alive check
 *     │  concrete getStatus()    ← shared status display
 *     │
 *     ├── Warrior  (⚔️  high HP, sword attack, shield bash)
 *     ├── Mage     (🔮 low HP, staff attack, fireball)
 *     └── Archer   (🏹 mid HP, arrow attack, rain of arrows)
 *
 * We'll create an array of GameCharacter[] and make them interact —
 * showcasing polymorphism with abstract classes.
 */

abstract class GameCharacter {
    private final String name;
    private int hp;
    private final int maxHp;

    GameCharacter(String name, int maxHp) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    String getName() {
        return name;
    }

    int getHp() {
        return hp;
    }

    int getMaxHp() {
        return maxHp;
    }

    // Abstract — each character attacks and uses abilities differently
    abstract String attack();
    abstract String specialAbility();

    // Concrete — shared damage logic
    void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
        if (hp == 0) {
            System.out.println("      💀 " + name + " has been defeated!");
        } else {
            System.out.println("      💥 " + name + " takes " + dmg
                    + " damage! HP: " + hp + "/" + maxHp);
        }
    }

    // Concrete — shared alive check
    boolean isAlive() {
        return hp > 0;
    }

    // Concrete — shared status display
    String getStatus() {
        String bar = "";
        int filled = (int) ((double) hp / maxHp * 10);
        for (int i = 0; i < 10; i++) {
            bar += (i < filled) ? "🟩" : "🟥";
        }
        return name + " " + bar + " " + hp + "/" + maxHp + " HP";
    }

    @Override
    public String toString() {
        return name + " (" + hp + "/" + maxHp + " HP)";
    }
}

class Warrior extends GameCharacter {
    Warrior(String name) {
        super(name, 150);   // High HP
    }

    @Override
    String attack() {
        return "⚔️  " + getName() + " swings a mighty sword!";
    }

    @Override
    String specialAbility() {
        return "🛡️ " + getName() + " performs SHIELD BASH! Stuns the enemy!";
    }
}

class Mage extends GameCharacter {
    Mage(String name) {
        super(name, 80);    // Low HP
    }

    @Override
    String attack() {
        return "🔮 " + getName() + " strikes with a magic staff!";
    }

    @Override
    String specialAbility() {
        return "🔥 " + getName() + " casts FIREBALL! Massive damage!";
    }
}

class Archer extends GameCharacter {
    Archer(String name) {
        super(name, 100);   // Mid HP
    }

    @Override
    String attack() {
        return "🏹 " + getName() + " fires a precise arrow!";
    }

    @Override
    String specialAbility() {
        return "🌧️ " + getName() + " unleashes RAIN OF ARROWS! Area damage!";
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// 🎯 MAIN CLASS — RUN ALL DEMOS
// ═══════════════════════════════════════════════════════════════════════════

public class AdvancedAbstractClasses {
    public static void main(String[] args) {

        // ═══ 1. Multi-Level Abstract Hierarchy ═══
        System.out.println("═══ 1. Multi-Level Abstract Hierarchy ═══");

        Dog buddy = new Dog("Buddy", "Alice", "Golden Retriever");
        Cat whiskers = new Cat("Whiskers", "Bob");

        System.out.println("   " + buddy.describe());         // Buddy the Golden Retriever says Woof!
        System.out.println("   " + whiskers.describe());      // Whiskers says Meow!
        System.out.println("   " + buddy.getOwnerInfo());     // Buddy belongs to Alice
        System.out.println("   " + whiskers.getOwnerInfo());  // Whiskers belongs to Bob
        System.out.println("   Breed: " + buddy.getBreed());  // Breed: Golden Retriever

        // Polymorphism — using the most abstract type
        AnimalBase[] pets = { buddy, whiskers };
        System.out.println("   🐾 All pets:");
        for (AnimalBase a : pets) {
            System.out.println("      → " + a.describe());
        }

        // ═══ 2. Abstract Class with Static Factory ═══
        System.out.println("\n═══ 2. Abstract Class with Static Factory ═══");

        // Caller doesn't know which concrete class is created!
        Notification emailNotif = Notification.create("email");
        Notification smsNotif = Notification.create("sms");

        System.out.println("   Created: " + emailNotif.getType() + " notification");   // Email
        System.out.println("   Created: " + smsNotif.getType() + " notification");     // SMS

        emailNotif.send("Your order has shipped!");  // 📧 Sending EMAIL: Your order has shipped!
        smsNotif.send("Verification code: 1234");    // 📱 Sending SMS: Verification code: 1234

        // Error handling for unknown types
        try {
            Notification unknown = Notification.create("pigeon");
        } catch (IllegalArgumentException e) {
            System.out.println("   ❌ Error: " + e.getMessage());
        }

        // Polymorphic array
        String[] types = { "email", "sms", "email" };
        System.out.println("   📨 Sending batch notifications:");
        for (String type : types) {
            Notification n = Notification.create(type);
            n.send("System maintenance at midnight");
        }

        // ═══ 3. Combining Abstract Classes and Interfaces ═══
        System.out.println("\n═══ 3. Combining Abstract Classes and Interfaces ═══");

        FileLogger fileLogger = new FileLogger();
        ConsoleLogger consoleLogger = new ConsoleLogger();

        fileLogger.log("Application started");       // 💾 [FILE] Application started → written to log file
        consoleLogger.log("User logged in");          // 🖥️  [CONSOLE] User logged in

        // Both are Loggable — interface polymorphism
        Loggable[] loggers = { fileLogger, consoleLogger };
        System.out.println("   📝 Logging to all destinations:");
        for (Loggable logger : loggers) {
            logger.log("Database connection established");
        }

        // Both are AbstractLogger — abstract class polymorphism
        AbstractLogger[] abstractLoggers = { fileLogger, consoleLogger };
        System.out.println("   🔧 Formatted messages:");
        for (AbstractLogger al : abstractLoggers) {
            System.out.println("      → " + al.formatMessage("test message"));
        }

        // ═══ 4. Real-World: Game Character System ═══
        System.out.println("\n═══ 4. Real-World: Game Character System ═══");

        // Create characters
        Warrior thorin = new Warrior("Thorin");
        Mage gandalf = new Mage("Gandalf");
        Archer legolas = new Archer("Legolas");

        // Polymorphic array — all are GameCharacter
        GameCharacter[] party = { thorin, gandalf, legolas };

        // Show all characters and their abilities
        System.out.println("   ⚔️  Party Members:");
        for (GameCharacter gc : party) {
            System.out.println("      " + gc.getStatus());
        }

        // Each character attacks
        System.out.println("\n   🗡️ Attack Phase:");
        for (GameCharacter gc : party) {
            System.out.println("      " + gc.attack());
        }

        // Special abilities
        System.out.println("\n   ✨ Special Abilities:");
        for (GameCharacter gc : party) {
            System.out.println("      " + gc.specialAbility());
        }

        // Combat simulation
        System.out.println("\n   💥 Combat Simulation:");
        System.out.println("   A dragon attacks the party!");
        thorin.takeDamage(30);     // Warrior takes some damage
        gandalf.takeDamage(50);    // Mage takes heavy damage
        legolas.takeDamage(20);    // Archer takes light damage

        System.out.println("\n   The dragon attacks again!");
        thorin.takeDamage(40);
        gandalf.takeDamage(35);    // Mage is in trouble!
        legolas.takeDamage(45);

        // Status check after combat
        System.out.println("\n   📊 Status After Combat:");
        for (GameCharacter gc : party) {
            System.out.println("      " + gc.getStatus()
                    + (gc.isAlive() ? " ✅ Alive" : " 💀 Defeated"));
        }

        // Count survivors
        int survivors = 0;
        for (GameCharacter gc : party) {
            if (gc.isAlive()) {
                survivors++;
            }
        }
        System.out.println("   🏆 Survivors: " + survivors + "/" + party.length);

        // ═══════════════════════════════════════════════════════════════
        // 💡 KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("💡 KEY TAKEAWAYS");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  ✅ Abstract classes can extend other abstract classes");
        System.out.println("     (multi-level hierarchy)");
        System.out.println("  ✅ Only the first CONCRETE class must implement all");
        System.out.println("     remaining abstract methods");
        System.out.println("  ✅ Static factory methods on abstract classes hide");
        System.out.println("     concrete class details from callers");
        System.out.println("  ✅ Abstract classes can implement interfaces partially,");
        System.out.println("     deferring some methods to concrete subclasses");
        System.out.println("  ✅ Combine abstract class (shared state) + interface");
        System.out.println("     (contract) for maximum flexibility");
        System.out.println("  ✅ Use polymorphic arrays (GameCharacter[]) to treat");
        System.out.println("     different subclasses uniformly");
        System.out.println("  ✅ Abstract classes shine when you have shared state +");
        System.out.println("     partial behavior + enforced contracts");
    }
}
