/*
 * ══════════════════════════════════════════════════════════════
 *  📘 LESSON 1: Basic Classes & Objects (Java)
 * ══════════════════════════════════════════════════════════════
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
 *  If "Car" is a class, then "my red Tesla Model 3" is an object.
 *
 *  KEY TERMINOLOGY:
 *  ─────────────────
 *  ┌────────────────┬───────────────────────────────────────────────────┐
 *  │  Term          │  Meaning                                          │
 *  ├────────────────┼───────────────────────────────────────────────────┤
 *  │  Class         │  The blueprint/template                           │
 *  │  Object        │  An instance created from a class                 │
 *  │  Field         │  Data stored inside an object (a.k.a. property)   │
 *  │  Method        │  Functions that belong to the object              │
 *  │  Constructor   │  Special method that runs when creating an object │
 *  │  this          │  Refers to the current object instance            │
 *  │  new           │  The keyword used to create an object from a class│
 *  └────────────────┴───────────────────────────────────────────────────┘
 *
 *  JAVA NOTE:
 *  ─────────────────
 *  In Java, every file must have ONE public class whose name matches the
 *  file name. Additional "helper" classes in the same file must be
 *  package-private (no access modifier). All lesson code runs inside
 *  the public class's main() method.
 *
 * ══════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────
// 1️⃣  Simplest Possible Class
// ─────────────────────────────────────────────
/*
 *  An Animal has two fields (name, sound) and one method (speak).
 *  The constructor takes both values and stores them using `this`.
 *
 *  In Java:
 *    - Every field must declare its type explicitly.
 *    - The constructor has the SAME name as the class (no "constructor" keyword).
 *    - Methods declare their return type before the method name.
 */

class Animal {
    // Fields — the data an Animal holds
    String name;
    String sound;

    // Constructor — runs automatically when you do `new Animal(...)`
    Animal(String name, String sound) {
        this.name = name;   // `this.name` is the field, `name` is the parameter
        this.sound = sound;
    }

    // Method — an action the Animal can perform
    String speak() {
        return this.name + " says " + this.sound + "!";
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Class with Multiple Methods
// ─────────────────────────────────────────────
/*
 *  A Calculator tracks a history of operations.
 *  In Java we use java.util.ArrayList instead of TypeScript's array.
 *  We return a COPY of the history list to protect internal state.
 */

class Calculator {
    // Field initialized directly — no need to set in constructor
    private java.util.ArrayList<String> history = new java.util.ArrayList<>();

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

    java.util.ArrayList<String> getHistory() {
        return new java.util.ArrayList<>(history); // return a copy, not the original
    }
}

// ─────────────────────────────────────────────
// 3️⃣  No Constructor Shorthand in Java
// ─────────────────────────────────────────────
/*
 *  TypeScript has a shorthand where you write:
 *
 *      constructor(public name: string, public email: string) {}
 *
 *  This AUTOMATICALLY declares the fields AND assigns them.
 *
 *  Java does NOT have this feature. You must always do THREE things
 *  explicitly:
 *
 *      ┌──────────────────────────────────────────────────────┐
 *      │  Step 1:  Declare the field        → String name;    │
 *      │  Step 2:  Accept constructor param → (String name)   │
 *      │  Step 3:  Assign in the body       → this.name=name; │
 *      └──────────────────────────────────────────────────────┘
 *
 *  It's more verbose, but crystal clear about what is happening.
 *  Many IDEs (IntelliJ, VS Code) can auto-generate constructors for you.
 */

class User {
    // Step 1: Declare each field with its type
    String name;
    String email;
    int age;

    // Step 2 & 3: Constructor accepts parameters and assigns them
    User(String name, String email, int age) {
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
/*
 *  In Java, you can assign default values directly at the field
 *  declaration — just like TypeScript.  The Counter class below
 *  starts `count` at 0 without needing a constructor at all.
 *
 *  If you don't write ANY constructor, Java provides a default
 *  no-argument constructor automatically.
 */

class Counter {
    int count = 0;  // Default value — starts at 0

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

// ─────────────────────────────────────────────
// 5️⃣  Objects are Independent Instances
// ─────────────────────────────────────────────
/*
 *  Each object created from a class is INDEPENDENT.
 *  Changing one object does NOT affect another.
 *  counterA and counterB each have their OWN `count` field.
 */

// (Uses Counter class defined in 4️⃣ above)

// ─────────────────────────────────────────────
// 6️⃣  getClass() and instanceof
// ─────────────────────────────────────────────
/*
 *  TypeScript/JavaScript:          Java equivalent:
 *  ───────────────────────         ─────────────────────────
 *  typeof dog          →          dog.getClass().getSimpleName()
 *  dog instanceof Animal  →       dog instanceof Animal
 *
 *  In Java:
 *    - `instanceof` checks if an object is an instance of a class
 *      (or any of its parent classes).
 *    - `getClass()` returns the runtime Class object.
 *    - `getClass().getSimpleName()` gives you the class name as a String.
 *    - `getClass().getName()` gives you the fully-qualified name.
 */

// ══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (file name must be BasicClass.java)
// ══════════════════════════════════════════════════════════════

public class BasicClass {
    public static void main(String[] args) {

        // ═══ 1. Simplest Possible Class ═══
        Animal dog = new Animal("Dog", "Woof");
        Animal cat = new Animal("Cat", "Meow");

        System.out.println("═══ 1. Basic Class & Objects ═══");
        System.out.println(dog.speak());                // Dog says Woof!
        System.out.println(cat.speak());                // Cat says Meow!
        System.out.println("dog.name = \"" + dog.name + "\""); // dog.name = "Dog"
        System.out.println("cat.name = \"" + cat.name + "\""); // cat.name = "Cat"

        // ═══ 2. Calculator with Methods ═══
        Calculator calc = new Calculator();

        System.out.println("\n═══ 2. Calculator with Methods ═══");
        System.out.println("5 + 3 = " + calc.add(5, 3));           // 5 + 3 = 8
        System.out.println("10 - 4 = " + calc.subtract(10, 4));    // 10 - 4 = 6
        System.out.println("6 × 7 = " + calc.multiply(6, 7));      // 6 × 7 = 42
        System.out.println("History: " + calc.getHistory());        // [5 + 3 = 8, 10 - 4 = 6, 6 × 7 = 42]

        // ═══ 3. No Constructor Shorthand ═══
        User user1 = new User("Naman", "naman@example.com", 25);
        User user2 = new User("Alice", "alice@example.com", 30);

        System.out.println("\n═══ 3. No Constructor Shorthand ═══");
        System.out.println(user1.greet());  // Hi, I'm Naman (25), reach me at naman@example.com
        System.out.println(user2.greet());  // Hi, I'm Alice (30), reach me at alice@example.com
        System.out.println("📝 Java requires explicit field declaration + constructor assignment.");
        System.out.println("   No shorthand like TS's constructor(public name: string).");

        // ═══ 4. Default Values ═══
        Counter counter = new Counter();

        System.out.println("\n═══ 4. Default Values ═══");
        System.out.println("Initial: " + counter.getCount());           // 0
        counter.increment();
        counter.increment();
        counter.increment();
        System.out.println("After 3 increments: " + counter.getCount()); // 3
        counter.decrement();
        System.out.println("After 1 decrement: " + counter.getCount());  // 2
        counter.reset();
        System.out.println("After reset: " + counter.getCount());        // 0

        // ═══ 5. Independent Instances ═══
        Counter counterA = new Counter();
        Counter counterB = new Counter();

        counterA.increment();
        counterA.increment();
        counterB.increment();

        System.out.println("\n═══ 5. Independent Instances ═══");
        System.out.println("Counter A: " + counterA.getCount());  // 2
        System.out.println("Counter B: " + counterB.getCount());  // 1
        System.out.println("✅ They are completely separate objects!");

        // ═══ 6. getClass() and instanceof ═══
        System.out.println("\n═══ 6. Type Checking ═══");

        // getClass() — returns the runtime class of an object
        System.out.println("dog.getClass().getSimpleName(): " + dog.getClass().getSimpleName());   // Animal
        System.out.println("cat.getClass().getSimpleName(): " + cat.getClass().getSimpleName());   // Animal
        System.out.println("calc.getClass().getSimpleName(): " + calc.getClass().getSimpleName()); // Calculator

        // instanceof — checks if an object is an instance of a given class
        System.out.println("dog instanceof Animal: " + (dog instanceof Animal));   // true

        // NOTE: In Java, `dog instanceof Calculator` won't even compile because
        //       Animal and Calculator are unrelated types! Java catches this at compile time.
        //       To test instanceof on unrelated types, we use an Object reference:
        Object mysteryObj = dog;
        System.out.println("\n--- Using Object reference for cross-type checks ---");
        System.out.println("mysteryObj instanceof Animal: " + (mysteryObj instanceof Animal));           // true
        System.out.println("mysteryObj instanceof Calculator: " + (mysteryObj instanceof Calculator));   // false
        System.out.println("mysteryObj.getClass().getName(): " + mysteryObj.getClass().getName());        // Animal
        System.out.println("mysteryObj.getClass().getSimpleName(): " + mysteryObj.getClass().getSimpleName()); // Animal
    }
}

// ─────────────────────────────────────────────
// 💡 KEY TAKEAWAYS
// ─────────────────────────────────────────────
// ✅ A class is a blueprint; an object is an instance of that blueprint.
// ✅ In Java, the constructor has the SAME name as the class (no "constructor" keyword).
// ✅ Use `this` to distinguish between fields and constructor parameters with the same name.
// ✅ Use `new ClassName()` to create objects.
// ✅ Java has NO constructor shorthand — you must declare fields + assign them explicitly.
// ✅ Each instance is independent — changing one doesn't affect others.
// ✅ Use `instanceof` to check types and `getClass().getSimpleName()` to get the class name.
