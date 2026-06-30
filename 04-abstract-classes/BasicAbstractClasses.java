/*
 * ═══════════════════════════════════════════════════════════════════════════
 * 📘 MODULE 04 — LESSON 1: BASIC ABSTRACT CLASSES
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * An ABSTRACT CLASS is a class that cannot be instantiated on its own.
 * It serves as a blueprint for other classes, providing:
 *   - A mix of ABSTRACT methods (no body — subclasses MUST implement)
 *   - And CONCRETE methods (has a body — subclasses inherit for free)
 *
 * Think of it like an architect's blueprint:
 *   🏗️  Blueprint says "there MUST be a door" (abstract)
 *   🏗️  Blueprint already includes the foundation (concrete)
 *   🏗️  You can't live in a blueprint — you need to BUILD a house first!
 *
 * ┌──────────────────────────────────────────────────────────────────────┐
 * │  Concept            │  Description                                  │
 * ├──────────────────────────────────────────────────────────────────────┤
 * │  abstract class     │  Cannot be instantiated; may have abstract    │
 * │                     │  and concrete methods                         │
 * │  abstract method    │  Declared with no body; subclass MUST override│
 * │  concrete method    │  Has a body; subclass inherits it for free    │
 * │  extends            │  Subclass inherits from abstract class        │
 * │  super()            │  Calls the abstract class's constructor       │
 * │  Template Method    │  Abstract class defines algorithm skeleton    │
 * └──────────────────────────────────────────────────────────────────────┘
 *
 * KEY RULES:
 *   • Mark a class `abstract` → it CANNOT be instantiated with `new`
 *   • Mark a method `abstract` → it has NO body, ends with `;`
 *   • If a class has ANY abstract method, the class MUST be abstract
 *   • First CONCRETE subclass MUST implement ALL abstract methods
 *   • Abstract classes CAN have constructors (called via super())
 *   • Abstract classes CAN have fields, static methods, everything
 *     a normal class can — except you can't do `new AbstractClass()`
 *
 * ═══════════════════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────────────────────────────────
// 1️⃣  WHAT IS AN ABSTRACT CLASS?
// ─────────────────────────────────────────────────────────────────────────
/*
 * An abstract class is a class you CANNOT instantiate directly.
 * It exists solely to be extended by concrete subclasses.
 *
 * It can contain:
 *   - Abstract methods: declared with `abstract`, no body, ends with `;`
 *     → Subclasses MUST provide an implementation.
 *   - Concrete methods: regular methods with a body
 *     → Subclasses inherit them for free.
 *
 * Here, `Shape` is abstract:
 *   - abstract double getArea()   → every shape calculates area differently
 *   - concrete String describe()  → shared behavior, uses getArea() result
 *
 * Attempting `new Shape()` causes a COMPILE ERROR.
 */

abstract class Shape {
    // Abstract method — no body, subclasses MUST implement
    abstract double getArea();

    // Concrete method — has a body, subclasses inherit this
    String describe() {
        return "A shape with area: " + String.format("%.2f", getArea());
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double getArea() {
        return Math.PI * radius * radius;
    }

    double getRadius() {
        return radius;
    }
}

class RectangleShape extends Shape {
    private final double width;
    private final double height;

    RectangleShape(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double getArea() {
        return width * height;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 2️⃣  ABSTRACT VS CONCRETE METHODS
// ─────────────────────────────────────────────────────────────────────────
/*
 * Abstract classes can mix both kinds of methods:
 *
 *   ABSTRACT METHODS (no body):
 *     → Force each subclass to provide its own implementation.
 *     → Declared with `abstract` keyword, ends with `;`
 *
 *   CONCRETE METHODS (has body):
 *     → Provide shared behavior that all subclasses inherit.
 *     → Subclasses can still @Override them if needed.
 *
 * Here, `Vehicle` has:
 *   - abstract String startEngine() → each vehicle starts differently
 *   - concrete String honk()       → all vehicles honk the same way
 */

abstract class Vehicle {
    // Abstract — subclasses MUST implement
    abstract String startEngine();

    // Concrete — inherited by all subclasses for free
    String honk() {
        return "HONK!";
    }
}

class Car extends Vehicle {
    @Override
    String startEngine() {
        return "Car engine purring...";
    }
}

class Motorcycle extends Vehicle {
    @Override
    String startEngine() {
        return "Motorcycle roaring...";
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 3️⃣  ABSTRACT CLASS WITH CONSTRUCTOR
// ─────────────────────────────────────────────────────────────────────────
/*
 * "Wait — if you can't instantiate an abstract class, why can it have
 *  a constructor?"
 *
 * Great question! The constructor is called via super() from subclasses.
 * This lets the abstract class initialize shared fields.
 *
 *   Abstract class       Subclass
 *   ┌──────────┐        ┌──────────────────┐
 *   │ Employee │◄───────│ SalariedEmployee │
 *   │──────────│        │──────────────────│
 *   │ name     │        │ monthlySalary    │
 *   │ id       │        │ calculatePay()   │
 *   │──────────│        └──────────────────┘
 *   │ toString │
 *   │ calcPay  │◄───────┌──────────────────┐
 *   └──────────┘        │ HourlyEmployee   │
 *                       │──────────────────│
 *                       │ hourlyRate       │
 *                       │ hoursWorked      │
 *                       │ calculatePay()   │
 *                       └──────────────────┘
 *
 * The Employee constructor initializes name and id.
 * Subclasses call super(name, id) and then set their own fields.
 */

abstract class Employee {
    private final String name;
    private final int id;

    // Constructor — called via super() from subclasses
    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    String getName() {
        return name;
    }

    int getId() {
        return id;
    }

    // Abstract — each employee type calculates pay differently
    abstract double calculatePay();

    // Concrete — shared toString using abstract calculatePay()
    @Override
    public String toString() {
        return String.format("Employee[name=%s, id=%d, pay=$%.2f]",
                name, id, calculatePay());
    }
}

class SalariedEmployee extends Employee {
    private final double monthlySalary;

    SalariedEmployee(String name, int id, double monthlySalary) {
        super(name, id);    // Calls Employee's constructor
        this.monthlySalary = monthlySalary;
    }

    @Override
    double calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private final double hourlyRate;
    private final double hoursWorked;

    HourlyEmployee(String name, int id, double hourlyRate, double hoursWorked) {
        super(name, id);    // Calls Employee's constructor
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 4️⃣  TEMPLATE METHOD PATTERN
// ─────────────────────────────────────────────────────────────────────────
/*
 * The Template Method Pattern is a classic use of abstract classes.
 *
 * The ABSTRACT class defines the skeleton of an algorithm in a
 * `final` method (so subclasses can't change the order), but
 * delegates individual steps to abstract methods that subclasses fill in.
 *
 *   Algorithm skeleton (final — can't override):
 *   ┌──────────────────────────────────────────┐
 *   │  process()                               │
 *   │    1. data = readData()       ← abstract │
 *   │    2. result = transformData(data) ← abs │
 *   │    3. writeData(result)       ← abstract │
 *   └──────────────────────────────────────────┘
 *
 * Each subclass fills in the HOW for each step, but the
 * SEQUENCE (read → transform → write) is locked in.
 */

abstract class DataProcessor {
    // Template method — defines the algorithm skeleton
    // Marked `final` so subclasses can't change the order
    final void process() {
        String data = readData();
        String transformed = transformData(data);
        writeData(transformed);
    }

    // Abstract steps — subclasses fill these in
    abstract String readData();
    abstract String transformData(String data);
    abstract void writeData(String data);
}

class CsvProcessor extends DataProcessor {
    @Override
    String readData() {
        return "name,age,city";
    }

    @Override
    String transformData(String data) {
        return data.toUpperCase();
    }

    @Override
    void writeData(String data) {
        System.out.println("   📄 CSV Output: " + data);
    }
}

class JsonProcessor extends DataProcessor {
    @Override
    String readData() {
        return "{\"name\": \"Alice\"}";
    }

    @Override
    String transformData(String data) {
        return data.replace("Alice", "Bob");
    }

    @Override
    void writeData(String data) {
        System.out.println("   📄 JSON Output: " + data);
    }
}

// ─────────────────────────────────────────────────────────────────────────
// 5️⃣  WHEN TO USE ABSTRACT CLASS VS INTERFACE
// ─────────────────────────────────────────────────────────────────────────
/*
 * ┌───────────────────────┬───────────────────────────────────────────────┐
 * │      Feature          │   Abstract Class     │    Interface           │
 * ├───────────────────────┼──────────────────────┼────────────────────────┤
 * │ Instantiate?          │ ❌ No                │ ❌ No                  │
 * │ Fields (state)?       │ ✅ Yes               │ ❌ No (only constants) │
 * │ Constructors?         │ ✅ Yes               │ ❌ No                  │
 * │ Concrete methods?     │ ✅ Yes               │ ✅ default methods     │
 * │ Abstract methods?     │ ✅ Yes               │ ✅ Yes (implicitly)    │
 * │ Multiple inheritance? │ ❌ Single extends    │ ✅ Multiple implements │
 * │ Relationship?         │ "IS-A" (identity)    │ "CAN-DO" (capability) │
 * │ Use when?             │ Shared state + code  │ Contract / capability  │
 * └───────────────────────┴──────────────────────┴────────────────────────┘
 *
 * RULE OF THUMB:
 *   → Abstract class: "This IS A kind of [X]" + shared state/logic
 *   → Interface: "This CAN DO [Y]" + just a contract
 *
 * Example:
 *   Duck IS-A Animal (extends Animal)
 *   Duck CAN swim   (implements Swimmable)
 */

abstract class Animal {
    private final String name;
    private final int age;

    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }

    // Abstract — each animal speaks differently
    abstract String speak();

    @Override
    public String toString() {
        return name + " (age " + age + ")";
    }
}

interface Swimmable {
    String swim();  // Contract only — no state, no constructor
}

class Duck extends Animal implements Swimmable {
    Duck(String name, int age) {
        super(name, age);
    }

    @Override
    String speak() {
        return "Quack!";
    }

    @Override
    public String swim() {
        return getName() + " paddles across the pond 🏊";
    }
}

class DogAnimal extends Animal {
    DogAnimal(String name, int age) {
        super(name, age);
    }

    @Override
    String speak() {
        return "Woof!";
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// 🎯 MAIN CLASS — RUN ALL DEMOS
// ═══════════════════════════════════════════════════════════════════════════

public class BasicAbstractClasses {
    public static void main(String[] args) {

        // ═══ 1. What is an Abstract Class? ═══
        System.out.println("═══ 1. What is an Abstract Class? ═══");

        // Shape shape = new Shape();  // ❌ COMPILE ERROR — cannot instantiate abstract class!
        System.out.println("   // Shape shape = new Shape();  ❌ Cannot instantiate abstract class!");

        Circle circle = new Circle(5.0);
        RectangleShape rect = new RectangleShape(4.0, 6.0);

        System.out.println("   Circle area: " + String.format("%.2f", circle.getArea()));          // Circle area: 78.54
        System.out.println("   Rectangle area: " + String.format("%.2f", rect.getArea()));         // Rectangle area: 24.00
        System.out.println("   " + circle.describe());   // A shape with area: 78.54
        System.out.println("   " + rect.describe());     // A shape with area: 24.00

        // Polymorphism — abstract class type as reference
        Shape[] shapes = { circle, rect };
        System.out.println("   📐 All shapes:");
        for (Shape s : shapes) {
            System.out.println("      → " + s.describe());
        }

        // ═══ 2. Abstract vs Concrete Methods ═══
        System.out.println("\n═══ 2. Abstract vs Concrete Methods ═══");

        Car car = new Car();
        Motorcycle moto = new Motorcycle();

        System.out.println("   " + car.startEngine());   // Car engine purring...
        System.out.println("   " + moto.startEngine());  // Motorcycle roaring...

        // Concrete method inherited from abstract class
        System.out.println("   Car honks: " + car.honk());   // HONK!
        System.out.println("   Moto honks: " + moto.honk()); // HONK!

        // Polymorphism with abstract type
        Vehicle[] vehicles = { car, moto };
        System.out.println("   🚗 Starting all vehicles:");
        for (Vehicle v : vehicles) {
            System.out.println("      → " + v.startEngine() + " | Honk: " + v.honk());
        }

        // ═══ 3. Abstract Class with Constructor ═══
        System.out.println("\n═══ 3. Abstract Class with Constructor ═══");

        SalariedEmployee alice = new SalariedEmployee("Alice", 101, 5000.00);
        HourlyEmployee bob = new HourlyEmployee("Bob", 102, 25.00, 160);

        System.out.println("   " + alice);  // Employee[name=Alice, id=101, pay=$5000.00]
        System.out.println("   " + bob);    // Employee[name=Bob, id=102, pay=$4000.00]

        // Polymorphism — array of abstract type
        Employee[] staff = { alice, bob };
        double totalPayroll = 0;
        for (Employee e : staff) {
            totalPayroll += e.calculatePay();
        }
        System.out.println("   💰 Total payroll: $" + String.format("%.2f", totalPayroll));

        // ═══ 4. Template Method Pattern ═══
        System.out.println("\n═══ 4. Template Method Pattern ═══");

        System.out.println("   Processing CSV:");
        DataProcessor csvProcessor = new CsvProcessor();
        csvProcessor.process();  // Calls readData → transformData → writeData

        System.out.println("   Processing JSON:");
        DataProcessor jsonProcessor = new JsonProcessor();
        jsonProcessor.process();  // Calls readData → transformData → writeData

        System.out.println("   ✅ Same algorithm skeleton, different implementations!");

        // ═══ 5. Abstract Class vs Interface ═══
        System.out.println("\n═══ 5. Abstract Class vs Interface ═══");

        Duck donald = new Duck("Donald", 3);
        DogAnimal rex = new DogAnimal("Rex", 5);

        // Duck IS-A Animal (abstract class — has state: name, age)
        System.out.println("   " + donald + " says: " + donald.speak());       // Donald (age 3) says: Quack!
        System.out.println("   " + rex + " says: " + rex.speak());             // Rex (age 5) says: Woof!

        // Duck CAN-DO Swimmable (interface — just a contract)
        System.out.println("   " + donald.swim());                              // Donald paddles across the pond 🏊
        // rex.swim();  // ❌ DogAnimal doesn't implement Swimmable!
        System.out.println("   // rex.swim();  ❌ DogAnimal doesn't implement Swimmable!");

        // Polymorphism with both abstract class and interface
        Animal[] animals = { donald, rex };
        System.out.println("   🐾 All animals:");
        for (Animal a : animals) {
            System.out.print("      → " + a + " says " + a.speak());
            if (a instanceof Swimmable) {
                System.out.println(" and can swim! ✅");
            } else {
                System.out.println(" but can't swim ❌");
            }
        }

        // ═══════════════════════════════════════════════════════════════
        // 💡 KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("💡 KEY TAKEAWAYS");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  ✅ Abstract classes CANNOT be instantiated with `new`");
        System.out.println("  ✅ Abstract methods have NO body — subclasses MUST implement them");
        System.out.println("  ✅ Concrete methods in abstract classes are inherited for free");
        System.out.println("  ✅ Abstract classes CAN have constructors (called via super())");
        System.out.println("  ✅ Template Method: abstract class defines algorithm skeleton,");
        System.out.println("     subclasses fill in the steps");
        System.out.println("  ✅ Abstract class = shared state + \"IS-A\" relationship");
        System.out.println("  ✅ Interface = contract only + \"CAN-DO\" capability + multiple ok");
    }
}
