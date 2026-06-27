/*
 * ═════════════════════════════════════════════════════════════
 *  📘 LESSON 1: Basic Classes & Objects
 * ═════════════════════════════════════════════════════════════
 *
 *  WHAT IS A CLASS?
 *  ─────────────────
 *  A class is a **blueprint** (template) for creating objects.
 *  Think of it like an architectural plan for a house:
 *    - The plan defines WHAT the house has (rooms, doors, windows) → Fields
 *    - The plan defines WHAT the house can do (open door, turn on lights) → Methods
 *    - Each actual house built from that plan is an OBJECT (instance).
 *
 *  WHAT IS AN OBJECT?
 *  ─────────────────
 *  An object is a **concrete instance** created from a class.
 *  If "Car" is a class, then "my red Toyota Camry 2024" is an object.
 *
 *  KEY TERMINOLOGY:
 *  ─────────────────
 *  • Class        → The blueprint/template
 *  • Object       → An instance created from a class
 *  • Field        → Data stored inside an object (also called "property" or "member variable")
 *  • Method       → Functions that belong to the object
 *  • Constructor  → A special method that runs when creating an object
 *  • `this`       → Refers to the current object instance
 *  • `new`        → The keyword used to create an object from a class
 *
 *  JAVA vs TYPESCRIPT:
 *  ─────────────────────
 *  ┌─────────────────────────┬──────────────────────────────────────┐
 *  │  TypeScript             │  Java                                │
 *  ├─────────────────────────┼──────────────────────────────────────┤
 *  │  name: string           │  String name;  (explicit type)       │
 *  │  constructor()          │  ClassName()   (named after class)   │
 *  │  speak(): string        │  String speak()  (return type first) │
 *  │  console.log()          │  System.out.println()                │
 *  │  typeof / instanceof    │  getClass() / instanceof             │
 *  └─────────────────────────┴──────────────────────────────────────┘
 *
 * ═════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;

// ─────────────────────────────────────────────
// 1️⃣  Simplest Possible Class
// ─────────────────────────────────────────────
//
// An Animal has data (name, sound) and behavior (speak).
// The constructor runs automatically when you do `new Animal(...)`.

class Animal {
    // Fields — the data an Animal holds
    String name;
    String sound;

    // Constructor — runs automatically when you do `new Animal(...)`
    // In Java, the constructor name MUST match the class name (no "constructor" keyword).
    Animal(String name, String sound) {
        this.name = name;   // `this.name` is the field, `name` is the parameter
        this.sound = sound;
    }
    String getName() {
        return this.name;
    }
    // Method — an action the Animal can perform
    String speak() {
        return this.name + " says " + this.sound + "!";
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Class with Multiple Methods
// ─────────────────────────────────────────────
//
// A Calculator tracks history of operations.
// It demonstrates multiple methods and a private field
// initialized directly (no constructor needed for it).

class Calculator {
    // Field initialized directly — no need to set in constructor
    private List<String> history = new ArrayList<>();

    int add(int a, int b) {
        int result = a + b;
        history.add(a + " + " + b + " = " + result);
        return result;
    } 
    
    int subtract(int a, int b) {
        int result = a - b;
        history.add(a + " - " + b + " = " + result);
        return result;
    }

    int multiply(int a, int b) {
        int result = a * b;
        history.add(a + " × " + b + " = " + result);
        return result;
    }

    // Return a COPY of the history, not the original list
    List<String> getHistory() {
        return new ArrayList<>(history);
    }
}

// ─────────────────────────────────────────────
// 3️⃣  No Constructor Shorthand in Java
// ─────────────────────────────────────────────
//
// TypeScript has a handy shorthand:
//
//   constructor(public name: string, public email: string) {}
//
// This ONE line declares fields, creates them, AND assigns them.
//
// Java does NOT have this feature. You must always:
//   1. Declare each field explicitly
//   2. Write the constructor with parameters
//   3. Assign each parameter to its field with `this.fieldName = paramName`
//
// It's more verbose, but it makes everything explicit and clear.

class User {
    // Step 1: Declare fields explicitly
    String name;
    String email;
    int age;

    // Step 2: Constructor with parameters
    User(String name, String email, int age) {
        // Step 3: Assign each parameter to the field
        this.name = name;
        this.email = email;
        this.age = age;
    }

    String greet() {
        return "Hi, I'm " + this.name + " (" + this.age + "), reach me at " + this.email;
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Default Values in Properties
// ─────────────────────────────────────────────
//
// In Java, you can assign default values to fields directly
// at the declaration site. No constructor is needed if the
// defaults are all you need.

class Counter {
    int count = 0; // Default value — starts at 0

    void increment() {
        this.count++;
    }

    void decrement() {
        this.count--;
    }

    void reset() {
        this.count = 0;
    }

    int getCount() {
        return this.count;
    }
}

// ═════════════════════════════════════════════════════════════
// PUBLIC CLASS — File name must match: BasicClass.java
// Contains main() — the entry point where everything runs.
// ═════════════════════════════════════════════════════════════

public class BasicClass {
    public static void main(String[] args) {

        // ═══ 1. Simplest Possible Class ═══
        System.out.println("═══ 1. Basic Class & Objects ═══");

        // Creating objects (instances) from the class
        Animal dog = new Animal("Dog", "Woof");
        Animal cat = new Animal("Cat", "Meow");

        System.out.println(dog.speak());                    // Dog says Woof!
        System.out.println(cat.speak());                    // Cat says Meow!
        System.out.println("dog.name = \"" + dog.name + "\""); // dog.name = "Dog"
        System.out.println("cat.name = \"" + cat.name + "\""); // cat.name = "Cat"

        // ═══ 2. Calculator with Methods ═══
        System.out.println("\n═══ 2. Calculator with Methods ═══");

        Calculator calc = new Calculator();

        System.out.println("5 + 3 = " + calc.add(5, 3));         // 5 + 3 = 8
        System.out.println("10 - 4 = " + calc.subtract(10, 4));  // 10 - 4 = 6
        System.out.println("6 × 7 = " + calc.multiply(6, 7));    // 6 × 7 = 42
        System.out.println("History: " + calc.getHistory());      // [5 + 3 = 8, 10 - 4 = 6, 6 × 7 = 42]

        // ═══ 3. No Constructor Shorthand ═══
        System.out.println("\n═══ 3. No Constructor Shorthand ═══");

        User user1 = new User("Naman", "naman@example.com", 25);
        User user2 = new User("Alice", "alice@example.com", 30);

        System.out.println(user1.greet());
        // Hi, I'm Naman (25), reach me at naman@example.com
        System.out.println(user2.greet());
        // Hi, I'm Alice (30), reach me at alice@example.com
        System.out.println("📝 Java requires explicit field declaration + constructor assignment.");
        System.out.println("   No shorthand like TypeScript's constructor(public name: string).");

        // ═══ 4. Default Values ═══
        System.out.println("\n═══ 4. Default Values ═══");

        Counter counter = new Counter();

        System.out.println("Initial: " + counter.getCount());        // 0
        counter.increment();
        counter.increment();
        counter.increment();
        System.out.println("After 3 increments: " + counter.getCount()); // 3
        counter.decrement();
        System.out.println("After 1 decrement: " + counter.getCount());  // 2
        counter.reset();
        System.out.println("After reset: " + counter.getCount());         // 0

        // ═══ 5. Objects are Independent Instances ═══
        System.out.println("\n═══ 5. Independent Instances ═══");

        // Each object created from a class is INDEPENDENT.
        // Changing one does NOT affect another.
        Counter counterA = new Counter();
        Counter counterB = new Counter();

        counterA.increment();
        counterA.increment();
        counterB.increment();

        System.out.println("Counter A: " + counterA.getCount()); // 2
        System.out.println("Counter B: " + counterB.getCount()); // 1
        // They are completely separate!

        // ═══ 6. getClass() and instanceof ═══
        System.out.println("\n═══ 6. Type Checking ═══");

        // Java uses getClass() to inspect the runtime type of an object.
        // Java uses `instanceof` to check if an object is an instance of a class.
        System.out.println("dog.getClass(): " + dog.getClass().getSimpleName());
        // Animal
        System.out.println("dog instanceof Animal: " + (dog instanceof Animal));
        // true
        System.out.println("dog instanceof Object: " + (dog instanceof Object));
        // true  (everything in Java extends Object)

        // instanceof works across unrelated classes too:
        // In Java you can't do `dog instanceof Calculator` if they're in
        // unrelated hierarchies — it won't compile. But you CAN compare
        // classes at runtime:
        System.out.println("dog's class == Animal: " + (dog.getClass() == Animal.class));
        // true
        System.out.println("dog's class == Calculator: " + dog.getClass().equals(Calculator.class));
        // false

        // Check field types via getClass()
        System.out.println("dog.name class: " + dog.name.getClass().getSimpleName());
        // String

        // ─────────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────────
        // ✅ A class is a blueprint; an object is an instance of that blueprint.
        // ✅ In Java, the constructor name MUST match the class name (no "constructor" keyword).
        // ✅ Use `this` to access fields and methods of the current object.
        // ✅ Use `new ClassName()` to create objects.
        // ✅ Java does NOT have constructor parameter shorthand — declare fields explicitly.
        // ✅ Each instance is independent — changing one doesn't affect others.
        // ✅ Use `instanceof` and `getClass()` to check an object's type at runtime.
    }
}
