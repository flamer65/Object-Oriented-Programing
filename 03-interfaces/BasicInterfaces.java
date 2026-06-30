/*
 * ══════════════════════════════════════════════════════════════════════
 *  📘 MODULE 03 — LESSON 1: BASIC INTERFACES
 * ══════════════════════════════════════════════════════════════════════
 *
 *  An INTERFACE in Java is a CONTRACT — a promise that a class will
 *  provide certain behaviors. Think of it like a job description:
 *
 *    "Anyone hired for this role MUST be able to do X, Y, and Z."
 *
 *  The interface says WHAT must be done.
 *  The implementing class decides HOW to do it.
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  ANALOGY: Wall Outlet (Interface) vs Appliances (Classes)   │
 *  ├──────────────────────────────────────────────────────────────┤
 *  │  A wall outlet defines a CONTRACT:                          │
 *  │    • Two prongs, 120V, 60Hz                                 │
 *  │                                                              │
 *  │  ANY appliance that follows this contract can plug in:       │
 *  │    • Lamp        → provides light                           │
 *  │    • Toaster     → provides heat                            │
 *  │    • Phone charger → provides charging                      │
 *  │                                                              │
 *  │  The outlet doesn't care HOW the appliance works.           │
 *  │  It only cares that the appliance follows the contract.     │
 *  └──────────────────────────────────────────────────────────────┘
 *
 *  KEY TERMINOLOGY:
 *  ┌─────────────────┬────────────────────────────────────────────┐
 *  │ Term            │ Meaning                                    │
 *  ├─────────────────┼────────────────────────────────────────────┤
 *  │ interface       │ Defines a contract (method signatures)     │
 *  │ implements      │ A class agrees to fulfill the contract     │
 *  │ abstract method │ Method with no body (must be overridden)   │
 *  │ contract        │ The set of methods a class must provide    │
 *  │ polymorphism    │ Using interface type to refer to objects   │
 *  └─────────────────┴────────────────────────────────────────────┘
 *
 *  SECTIONS:
 *    1️⃣ What is an Interface?
 *    2️⃣ Multiple Interfaces
 *    3️⃣ Interface as a Type
 *    4️⃣ Interface with Constants
 *    5️⃣ Interfaces vs Inheritance
 * ══════════════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────────────────────────────
// 1️⃣ WHAT IS AN INTERFACE?
// ─────────────────────────────────────────────────────────────────────
/*
 *  An interface declares methods WITHOUT implementations.
 *  Any class that "implements" the interface MUST provide
 *  concrete bodies for ALL of its abstract methods.
 *
 *  Think of Speakable as a contract that says:
 *    "If you claim to be Speakable, you MUST have a speak() method."
 *
 *  Syntax:
 *    interface InterfaceName {
 *        ReturnType methodName(params);   // implicitly public & abstract
 *    }
 *
 *  NOTE: All methods in an interface are public and abstract by default.
 *        You don't need to write those keywords (but you can).
 */

// The contract: anything Speakable must know how to speak
interface Speakable {
    String speak();   // public abstract by default — no method body!
}

// Dog fulfills the Speakable contract its own way
class Dog implements Speakable {
    private String name;

    Dog(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public String speak() {
        return name + " says: Woof! Woof! 🐕";
    }
}

// Cat fulfills the same contract differently
class Cat implements Speakable {
    private String name;

    Cat(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public String speak() {
        return name + " says: Meow! 🐱";
    }
}


// ─────────────────────────────────────────────────────────────────────
// 2️⃣ MULTIPLE INTERFACES
// ─────────────────────────────────────────────────────────────────────
/*
 *  A class can implement MULTIPLE interfaces — unlike inheritance
 *  where Java allows only ONE superclass. This is one of the biggest
 *  advantages of interfaces.
 *
 *  Syntax:
 *    class MyClass implements InterfaceA, InterfaceB, InterfaceC {
 *        // must implement ALL methods from ALL interfaces
 *    }
 *
 *  Think of it like a person having multiple skills:
 *    A document CAN be printed AND CAN be saved.
 */

// Contract: anything Printable can be printed
interface Printable {
    void print();
}

// Contract: anything Saveable can be saved
interface Saveable {
    void save();
}

// Document implements BOTH contracts
class Document implements Printable, Saveable {
    private String title;
    private String content;

    Document(String title, String content) {
        this.title = title;
        this.content = content;
    }

    String getTitle() {
        return title;
    }

    @Override
    public void print() {
        System.out.println("  🖨️  Printing: \"" + title + "\"");
        System.out.println("     Content: " + content);
    }

    @Override
    public void save() {
        System.out.println("  💾 Saving: \"" + title + "\" to disk...");
    }
}


// ─────────────────────────────────────────────────────────────────────
// 3️⃣ INTERFACE AS A TYPE
// ─────────────────────────────────────────────────────────────────────
/*
 *  An interface can be used as a TYPE for:
 *    • Variable declarations:    Speakable s = new Dog("Rex");
 *    • Method parameters:        void demo(Speakable s) { ... }
 *    • Arrays:                   Speakable[] animals = { ... };
 *    • Return types:             Speakable getAnimal() { ... }
 *
 *  This is POLYMORPHISM via interfaces — you can treat any
 *  implementing class uniformly through the interface type.
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  Speakable s;          ← variable of interface type          │
 *  │  s = new Dog("Rex");   ← holds a Dog (which IS Speakable)    │
 *  │  s = new Cat("Luna");  ← holds a Cat (which IS Speakable)    │
 *  │  s.speak();            ← works for both!                     │
 *  └──────────────────────────────────────────────────────────────┘
 */

// Helper class used in main() for Section 3 demonstrations
class SpeakableDemo {
    // Method that accepts an INTERFACE type — works with any Speakable
    static void makeAllSpeak(Speakable[] speakers) {
        for (Speakable s : speakers) {
            System.out.println("  🎤 " + s.speak());
        }
    }
}


// ─────────────────────────────────────────────────────────────────────
// 4️⃣ INTERFACE WITH CONSTANTS
// ─────────────────────────────────────────────────────────────────────
/*
 *  Interfaces can contain CONSTANTS — fields that are implicitly:
 *    public + static + final
 *
 *  You don't need to write all three keywords; Java adds them
 *  automatically. But writing them explicitly is good practice
 *  for clarity.
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  interface MyConstants {                                     │
 *  │      int VALUE = 42;          ← automatically public         │
 *  │  }                               static final                │
 *  │                                                              │
 *  │  // Access: MyConstants.VALUE                                │
 *  └──────────────────────────────────────────────────────────────┘
 *
 *  Use Case: Group related constants that multiple classes share.
 */

// Game configuration constants shared across the game engine
interface GameConstants {
    // All fields in an interface are public static final by default
    int MAX_LEVEL = 100;
    int MAX_HP = 9999;
    int MAX_MP = 999;
    int MAX_INVENTORY_SLOTS = 64;
    String GAME_TITLE = "Dragon Quest OOP";
    double CRITICAL_HIT_MULTIPLIER = 2.5;
}

// A game character that uses the shared constants
class GameCharacter {
    private String name;
    private int level;
    private int hp;

    GameCharacter(String name, int level, int hp) {
        // Clamp values to the max allowed by GameConstants
        this.name = name;
        this.level = Math.min(level, GameConstants.MAX_LEVEL);
        this.hp = Math.min(hp, GameConstants.MAX_HP);
    }

    String getStatus() {
        return String.format("%s [Lv.%d | HP: %d/%d]",
                name, level, hp, GameConstants.MAX_HP);
    }
}


// ─────────────────────────────────────────────────────────────────────
// 5️⃣ INTERFACES VS INHERITANCE — "CAN-DO" vs "IS-A"
// ─────────────────────────────────────────────────────────────────────
/*
 *  Inheritance (extends)  → "IS-A" relationship
 *  Interface (implements) → "CAN-DO" relationship
 *
 *  A Dog IS-A Animal      → extends Animal
 *  A Dog CAN speak        → implements Speakable
 *  A Dog CAN be trained   → implements Trainable
 *
 *  You can use BOTH together:
 *    class Dog extends Animal implements Speakable, Trainable
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  COMPARISON TABLE                                            │
 *  ├───────────────────┬──────────────────┬───────────────────────┤
 *  │ Feature           │ extends (class)  │ implements (interface)│
 *  ├───────────────────┼──────────────────┼───────────────────────┤
 *  │ Relationship      │ IS-A             │ CAN-DO                │
 *  │ How many?         │ Only ONE         │ Multiple allowed      │
 *  │ Methods           │ Inherited as-is  │ Must be implemented   │
 *  │ Fields            │ Inherited        │ Constants only        │
 *  │ Constructor       │ Yes              │ No                    │
 *  │ When to use       │ Shared structure │ Shared behavior       │
 *  └───────────────────┴──────────────────┴───────────────────────┘
 */

// Base class — shared structure for all animals
class Animal {
    private String species;
    private int legs;

    Animal(String species, int legs) {
        this.species = species;
        this.legs = legs;
    }

    String getSpecies() {
        return species;
    }

    int getLegs() {
        return legs;
    }

    String describe() {
        return species + " with " + legs + " legs";
    }
}

// Contract: anything Trainable can learn tricks
interface Trainable {
    String learnTrick(String trick);
}

// SmartDog IS-A Animal, CAN speak, CAN be trained
// (Using SmartDog to avoid conflict with the Dog class above)
class SmartDog extends Animal implements Speakable, Trainable {
    private String name;

    SmartDog(String name) {
        super("Canine", 4);          // call Animal constructor
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public String speak() {
        return name + " barks: Woof! 🐕";
    }

    @Override
    public String learnTrick(String trick) {
        return name + " learned: " + trick + "! 🎓";
    }
}


// ══════════════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (class name matches file name)
// ══════════════════════════════════════════════════════════════════════

public class BasicInterfaces {

    public static void main(String[] args) {

        System.out.println("══════════════════════════════════════════════════════");
        System.out.println(" 📘 MODULE 03 — LESSON 1: BASIC INTERFACES");
        System.out.println("══════════════════════════════════════════════════════");

        // ═══════════════════════════════════════════════════════════
        // 1️⃣ WHAT IS AN INTERFACE?
        // ═══════════════════════════════════════════════════════════
        System.out.println("═══ 1. What is an Interface? ═══");

        // Both Dog and Cat implement Speakable, but each speaks differently
        Dog dog = new Dog("Buddy");
        Cat cat = new Cat("Whiskers");

        System.out.println("  " + dog.speak());       // Buddy says: Woof! Woof! 🐕
        System.out.println("  " + cat.speak());       // Whiskers says: Meow! 🐱

        // The interface defines WHAT (speak), the class defines HOW
        System.out.println("  📝 Same contract, different implementations!");

        // ═══════════════════════════════════════════════════════════
        // 2️⃣ MULTIPLE INTERFACES
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 2. Multiple Interfaces ═══");

        Document doc = new Document("Java Notes", "Interfaces are contracts!");

        // Document fulfills BOTH the Printable and Saveable contracts
        doc.print();                                   // 🖨️  Printing: "Java Notes"
        doc.save();                                    // 💾 Saving: "Java Notes" to disk...

        // Verify it is both types using instanceof
        System.out.println("  Is Printable? " + (doc instanceof Printable));  // true
        System.out.println("  Is Saveable?  " + (doc instanceof Saveable));   // true

        // ═══════════════════════════════════════════════════════════
        // 3️⃣ INTERFACE AS A TYPE
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 3. Interface as a Type ═══");

        // Using the interface type for a variable
        Speakable s1 = new Dog("Rex");       // Dog stored as Speakable
        Speakable s2 = new Cat("Luna");      // Cat stored as Speakable

        System.out.println("  " + s1.speak());         // Rex says: Woof! Woof! 🐕
        System.out.println("  " + s2.speak());         // Luna says: Meow! 🐱

        // Array of interface type — holds any Speakable
        System.out.println("  --- Making all speak via Speakable[] ---");
        Speakable[] speakers = {
            new Dog("Max"),
            new Cat("Cleo"),
            new Dog("Rocky"),
            new Cat("Mittens")
        };

        // Polymorphism in action — one method handles all types!
        SpeakableDemo.makeAllSpeak(speakers);

        // ═══════════════════════════════════════════════════════════
        // 4️⃣ INTERFACE WITH CONSTANTS
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 4. Interface with Constants ═══");

        // Access constants directly from the interface
        System.out.println("  🎮 Game: " + GameConstants.GAME_TITLE);
        System.out.println("  ⬆️  Max Level: " + GameConstants.MAX_LEVEL);
        System.out.println("  ❤️  Max HP: " + GameConstants.MAX_HP);
        System.out.println("  🔵 Max MP: " + GameConstants.MAX_MP);
        System.out.println("  🎒 Inventory Slots: " + GameConstants.MAX_INVENTORY_SLOTS);
        System.out.println("  ⚔️  Critical Multiplier: " + GameConstants.CRITICAL_HIT_MULTIPLIER + "x");

        // Using constants in a class
        GameCharacter hero = new GameCharacter("Warrior", 50, 8500);
        System.out.println("  🧙 " + hero.getStatus());   // Warrior [Lv.50 | HP: 8500/9999]

        // What happens if we exceed the max?
        GameCharacter overLevel = new GameCharacter("Hacker", 999, 99999);
        System.out.println("  🧙 " + overLevel.getStatus()); // Hacker [Lv.100 | HP: 9999/9999] (clamped!)

        // ═══════════════════════════════════════════════════════════
        // 5️⃣ INTERFACES VS INHERITANCE
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 5. Interfaces vs Inheritance ═══");

        SmartDog smartDog = new SmartDog("Einstein");

        // IS-A Animal (inherited behavior)
        System.out.println("  📋 " + smartDog.describe());   // Canine with 4 legs
        System.out.println("  🦴 Species: " + smartDog.getSpecies()); // Canine

        // CAN-DO Speakable (interface behavior)
        System.out.println("  🔊 " + smartDog.speak());      // Einstein barks: Woof! 🐕

        // CAN-DO Trainable (interface behavior)
        System.out.println("  " + smartDog.learnTrick("Sit"));       // Einstein learned: Sit! 🎓
        System.out.println("  " + smartDog.learnTrick("Roll Over")); // Einstein learned: Roll Over! 🎓

        // instanceof checks — SmartDog satisfies ALL of these types
        System.out.println("  --- Type checks ---");
        System.out.println("  Is Animal?    " + (smartDog instanceof Animal));     // true
        System.out.println("  Is Speakable? " + (smartDog instanceof Speakable));  // true
        System.out.println("  Is Trainable? " + (smartDog instanceof Trainable));  // true

        // Polymorphism: same object, different reference types
        Animal asAnimal = smartDog;        // view as Animal
        Speakable asSpeakable = smartDog;  // view as Speakable
        Trainable asTrainable = smartDog;  // view as Trainable

        System.out.println("  --- Same object, different views ---");
        System.out.println("  As Animal:    " + asAnimal.describe());
        System.out.println("  As Speakable: " + asSpeakable.speak());
        System.out.println("  As Trainable: " + asTrainable.learnTrick("Shake"));

        // ═══════════════════════════════════════════════════════════
        //  💡 KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println(" 💡 KEY TAKEAWAYS");
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println(" ✅ An interface is a CONTRACT — it defines WHAT, not HOW");
        System.out.println(" ✅ All interface methods are public & abstract by default");
        System.out.println(" ✅ A class can implement MULTIPLE interfaces (unlike extends)");
        System.out.println(" ✅ Interface types enable polymorphism (Speakable s = new Dog())");
        System.out.println(" ✅ Interface fields are automatically public static final constants");
        System.out.println(" ✅ extends = IS-A relationship, implements = CAN-DO relationship");
        System.out.println(" ✅ A class can use BOTH extends and implements together");
    }
}
