/*
 * ═══════════════════════════════════════════════════════════════
 *  📘 MODULE 02 — LESSON 1: Basic Inheritance
 * ═══════════════════════════════════════════════════════════════
 *
 *  WHAT IS INHERITANCE?
 *  ────────────────────
 *  Inheritance lets one class (the CHILD / SUBCLASS) acquire all
 *  the properties and methods of another class (the PARENT / SUPERCLASS).
 *
 *  Think of it like genetics:
 *    - A "Dog" IS AN "Animal" → Dog inherits everything Animal has
 *    - A "SportsCar" IS A "Car" → SportsCar inherits everything Car has
 *    - The child can also ADD new features or CHANGE inherited ones
 *
 *  WHY USE INHERITANCE?
 *  ────────────────────
 *  • Avoid duplicating code — write common logic once in the parent
 *  • Create logical hierarchies — "Dog is a type of Animal"
 *  • Extend existing classes without modifying them
 *  • One of the 4 pillars of OOP (along with Encapsulation,
 *    Polymorphism, Abstraction)
 *
 *  SYNTAX:
 *  ───────
 *    class Child extends Parent {
 *        Child(...) {
 *            super(...);  // ← MUST call parent constructor first
 *        }
 *    }
 *
 *  KEY TERMINOLOGY:
 *  ────────────────
 *  ┌──────────────────────────────┬──────────────────────────────────┐
 *  │  Term                        │  Meaning                         │
 *  ├──────────────────────────────┼──────────────────────────────────┤
 *  │  Parent / Base / Superclass  │  The class being inherited FROM  │
 *  │  Child / Derived / Subclass  │  The class that INHERITS         │
 *  │  extends                     │  Keyword to inherit from a parent│
 *  │  super()                     │  Calls the parent's constructor  │
 *  │  super.method()              │  Calls a parent's method         │
 *  │  "IS-A" relationship         │  Child IS A type of Parent       │
 *  └──────────────────────────────┴──────────────────────────────────┘
 *
 * ═══════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────
// 1️⃣  Simplest Inheritance — Parent & Child
// ─────────────────────────────────────────────

/*
 * PARENT class — defines shared properties & behavior.
 * Every Animal has a name, a sound, and can speak() and move().
 */
class Animal {
    String name;
    String sound;

    Animal(String name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    String speak() {
        return this.name + " says \"" + this.sound + "\"";
    }

    String move() {
        return this.name + " is moving";
    }
}

/*
 * CHILD class — inherits everything from Animal + adds its own features.
 * Dog IS-A Animal. It automatically gets name, sound, speak(), move().
 * Then it adds breed and fetch().
 */
class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        // super() calls the PARENT constructor — MUST be called FIRST
        super(name, "Woof");   // passes name and "Woof" to Animal's constructor
        this.breed = breed;     // then set child-specific properties
    }

    // New method — only exists on Dog, not on Animal
    String fetch(String item) {
        return this.name + " fetches the " + item + "! 🎾";
    }
}

/*
 * Another CHILD class — Cat IS-A Animal too.
 * Adds isIndoor and purr().
 */
class Cat extends Animal {
    boolean isIndoor;

    Cat(String name, boolean isIndoor) {
        super(name, "Meow");
        this.isIndoor = isIndoor;
    }

    String purr() {
        return this.name + " purrs... 😺";
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Multi-Level Inheritance Chain
// ─────────────────────────────────────────────
//
//   Vehicle → Car → ElectricCar
//   Each level adds more specific features

/*
 * GRANDPARENT: Vehicle — the most general level.
 * Every vehicle has a make, model, year.
 */
class Vehicle {
    String make;
    String model;
    int year;

    Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    String describe() {
        return this.year + " " + this.make + " " + this.model;
    }

    String start() {
        return this.describe() + " — Engine started 🔑";
    }
}

/*
 * PARENT: Car extends Vehicle — adds doors and fuelType.
 */
class Car extends Vehicle {
    int doors;
    String fuelType;

    Car(String make, String model, int year, int doors, String fuelType) {
        super(make, model, year);
        this.doors = doors;
        this.fuelType = fuelType;
    }

    String honk() {
        return this.describe() + " goes HONK! 📯";
    }
}

/*
 * CHILD: ElectricCar extends Car — adds battery and range.
 * fuelType is always "Electric".
 */
class ElectricCar extends Car {
    double batteryCapacityKWh;
    int rangeKm;

    ElectricCar(String make, String model, int year, int doors,
                double batteryCapacityKWh, int rangeKm) {
        super(make, model, year, doors, "Electric"); // fuelType is always "Electric"
        this.batteryCapacityKWh = batteryCapacityKWh;
        this.rangeKm = rangeKm;
    }

    String chargingInfo() {
        return "🔋 " + this.batteryCapacityKWh + " kWh battery, " + this.rangeKm + " km range";
    }
}

// ─────────────────────────────────────────────
// 4️⃣  No Constructor Shorthand in Inheritance
// ─────────────────────────────────────────────

/*
 * In TypeScript, you can write:
 *
 *     class Shape {
 *         constructor(public color: string, public filled: boolean = true) {}
 *     }
 *
 * This declares fields, assigns them, and makes them public — all in one line!
 *
 * Java does NOT have this shorthand. You must always:
 *     1. Declare each field explicitly
 *     2. Accept them as constructor parameters
 *     3. Assign them manually with this.field = parameter
 *
 * This is more verbose but very explicit — there's no magic happening.
 */
class Shape {
    String color;
    boolean filled;

    // Step 1: Constructor accepts parameters
    Shape(String color, boolean filled) {
        // Step 2: Assign each parameter to a field
        this.color = color;
        this.filled = filled;
    }

    // Overloaded constructor — default filled = true
    Shape(String color) {
        this(color, true);  // calls the other constructor
    }

    String describe() {
        return "A " + (this.filled ? "filled" : "outlined") + " " + this.color + " shape";
    }
}

class Circle extends Shape {
    double radius;

    Circle(String color, double radius) {
        super(color);  // uses default filled = true
        this.radius = radius;
    }

    Circle(String color, double radius, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }

    double getArea() {
        return Math.PI * this.radius * this.radius;
    }

    double getCircumference() {
        return 2 * Math.PI * this.radius;
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

    RectangleShape(String color, double width, double height, boolean filled) {
        super(color, filled);
        this.width = width;
        this.height = height;
    }

    double getArea() {
        return this.width * this.height;
    }

    double getPerimeter() {
        return 2 * (this.width + this.height);
    }
}

// ═══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (must match file name)
// ═══════════════════════════════════════════════════════════════
public class BasicInheritance {
    public static void main(String[] args) {

        // ─────────────────────────────────────────
        // 1️⃣  Simplest Inheritance — Parent & Child
        // ─────────────────────────────────────────
        System.out.println("═══ 1. Simplest Inheritance ═══");

        Dog dog = new Dog("Buddy", "Golden Retriever");
        Cat cat = new Cat("Whiskers", true);

        // Inherited methods from Animal — work on both Dog and Cat
        System.out.println(dog.speak());    // Buddy says "Woof"
        System.out.println(dog.move());     // Buddy is moving
        System.out.println(cat.speak());    // Whiskers says "Meow"
        System.out.println(cat.move());     // Whiskers is moving

        // Inherited properties from Animal
        System.out.println("Dog name: " + dog.name + ", sound: " + dog.sound);
        // Dog name: Buddy, sound: Woof

        // Child-specific features
        System.out.println(dog.fetch("ball"));  // Buddy fetches the ball! 🎾
        System.out.println(cat.purr());          // Whiskers purrs... 😺
        System.out.println("Breed: " + dog.breed + ", Indoor: " + cat.isIndoor);
        // Breed: Golden Retriever, Indoor: true

        // ─────────────────────────────────────────
        // 2️⃣  instanceof — Checking the Inheritance Chain
        // ─────────────────────────────────────────
        System.out.println("\n═══ 2. instanceof Checks ═══");

        System.out.println("dog instanceof Dog:    " + (dog instanceof Dog));       // true
        System.out.println("dog instanceof Animal: " + (dog instanceof Animal));    // true  ← Dog IS an Animal!
        System.out.println("cat instanceof Cat:    " + (cat instanceof Cat));       // true
        System.out.println("cat instanceof Animal: " + (cat instanceof Animal));    // true  ← Cat IS an Animal!

        // To check sibling types, we use Animal references.
        // Java's compiler won't allow `dog instanceof Cat` directly
        // because it KNOWS Dog can never be Cat (they're siblings).
        // But if the reference type is Animal, the check is valid:
        Animal animalRef1 = dog;
        Animal animalRef2 = cat;
        System.out.println("animalRef1 instanceof Cat: " + (animalRef1 instanceof Cat));  // false ← Dog is NOT a Cat
        System.out.println("animalRef2 instanceof Dog: " + (animalRef2 instanceof Dog));  // false ← Cat is NOT a Dog

        // ─────────────────────────────────────────
        // 3️⃣  Multi-Level Inheritance Chain
        // ─────────────────────────────────────────
        System.out.println("\n═══ 3. Multi-Level Inheritance ═══");

        ElectricCar tesla = new ElectricCar("Tesla", "Model 3", 2024, 4, 75, 450);

        // Methods from Vehicle (grandparent)
        System.out.println(tesla.describe());       // 2024 Tesla Model 3
        System.out.println(tesla.start());          // 2024 Tesla Model 3 — Engine started 🔑

        // Methods from Car (parent)
        System.out.println(tesla.honk());           // 2024 Tesla Model 3 goes HONK! 📯

        // Methods from ElectricCar (own)
        System.out.println(tesla.chargingInfo());   // 🔋 75.0 kWh battery, 450 km range

        // Properties from ALL levels
        System.out.println("Make: " + tesla.make + ", Doors: " + tesla.doors + ", Fuel: " + tesla.fuelType);
        // Make: Tesla, Doors: 4, Fuel: Electric
        System.out.println("Battery: " + tesla.batteryCapacityKWh + " kWh");
        // Battery: 75.0 kWh
    
        // instanceof checks — true for ALL ancestors
        System.out.println("\ninstanceof checks:");
        System.out.println("  ElectricCar: " + (tesla instanceof ElectricCar));  // true
        System.out.println("  Car:         " + (tesla instanceof Car));           // true
        System.out.println("  Vehicle:     " + (tesla instanceof Vehicle));       // true

        // ─────────────────────────────────────────
        // 4️⃣  No Constructor Shorthand in Inheritance
        // ─────────────────────────────────────────
        System.out.println("\n═══ 4. No Constructor Shorthand ═══");

        Circle circle = new Circle("red", 5);
        RectangleShape rect = new RectangleShape("blue", 10, 4, false);

        System.out.println(circle.describe());      // A filled red shape
        System.out.println(String.format("Circle: radius=%.0f, area=%.2f", circle.radius, circle.getArea()));
        // Circle: radius=5, area=78.54

        System.out.println(rect.describe());         // A outlined blue shape
        System.out.println("Rectangle: " + rect.width + "x" + rect.height + ", area=" + rect.getArea());
        // Rectangle: 10.0x4.0, area=40.0

        System.out.println("\n📝 Java requires explicit field declarations:");
        System.out.println("   1. Declare each field:    String color;");
        System.out.println("   2. Constructor parameter: Shape(String color) { ... }");
        System.out.println("   3. Manual assignment:     this.color = color;");
        System.out.println("   TypeScript's `public color: string` in constructor does all 3!");

        // ─────────────────────────────────────────
        // 5️⃣  When NOT to Use Inheritance
        // ─────────────────────────────────────────
        //
        // Inheritance models an "IS-A" relationship.
        // Ask yourself: "Is [Child] truly a type of [Parent]?"
        //
        // ✅ GOOD — "IS-A" makes sense:
        //    Dog IS AN Animal
        //    ElectricCar IS A Car
        //    SavingsAccount IS A BankAccount
        //
        // ❌ BAD — "IS-A" doesn't make sense:
        //    Engine IS A Car?        → No! Engine is PART OF a Car (use composition)
        //    Stack IS AN Array?      → No! Stack uses an array internally
        //    Logger IS A FileSystem? → No! Logger uses a file system
        //
        // RULE OF THUMB:
        //    "IS-A" → Inheritance (extends)
        //    "HAS-A" → Composition (use as a field)
        //
        // We'll cover Composition vs Inheritance in Module 09!

        System.out.println("\n═══ 5. IS-A vs HAS-A ═══");
        System.out.println("✅ Dog IS-A Animal → use inheritance");
        System.out.println("✅ ElectricCar IS-A Car → use inheritance");
        System.out.println("❌ Engine IS-A Car → NO! Use composition (Engine is PART OF Car)");
        System.out.println("❌ Stack IS-A Array → NO! Stack USES an array internally");

        // ─────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────
        System.out.println("\n💡 KEY TAKEAWAYS");
        System.out.println("✅ `extends` makes a child class inherit from a parent class.");
        System.out.println("✅ `super()` MUST be called in the child constructor before using `this`.");
        System.out.println("✅ Children inherit ALL public/protected properties and methods.");
        System.out.println("✅ Children can ADD new properties and methods.");
        System.out.println("✅ `instanceof` checks the ENTIRE inheritance chain.");
        System.out.println("✅ Inheritance can be multi-level (grandparent → parent → child).");
        System.out.println("✅ Only use inheritance for true \"IS-A\" relationships.");
    }
}
