/*
 * ═══════════════════════════════════════════════════════════════════
 *  ✅ SOLUTIONS: Interfaces
 *  Module 03 — Defining Contracts & Achieving Polymorphism
 * ═══════════════════════════════════════════════════════════════════
 *
 *  These are the complete, working solutions for all 3 exercises.
 *  Compare your implementations against these to check your work.
 *
 *  Key Concepts Demonstrated:
 *    • Defining interfaces with method signatures
 *    • Implementing multiple interfaces in a single class
 *    • Using interfaces as parameter/return types (polymorphism)
 *    • instanceof checks against interface types
 *    • Static utility methods that operate on interface types
 * ═══════════════════════════════════════════════════════════════════
 */


// ─────────────────────────────────────────────────────────────────
// 1️⃣  SOLUTION 1: Shapes with Interfaces
// ─────────────────────────────────────────────────────────────────

/*
 *  Measurable — the "measurement" contract.
 *  Any class implementing this promises to provide area and perimeter.
 */
interface Measurable {
    double getArea();
    double getPerimeter();
}

/*
 *  Drawable — the "drawing" contract.
 *  Any class implementing this can describe how it would be drawn.
 */
interface Drawable {
    String draw();
}

/*
 *  Circle implements BOTH Measurable and Drawable.
 *  This is Java's way of supporting multiple inheritance of behavior.
 *  The class must provide concrete implementations for ALL methods
 *  from BOTH interfaces.
 */
class Circle implements Measurable, Drawable {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String draw() {
        return "Drawing circle with radius " + radius;
    }
}

/*
 *  Rectangle also implements both interfaces.
 *  A Circle and Rectangle are unrelated by class hierarchy,
 *  but they share the same contracts — that's the power of interfaces!
 */
class Rectangle implements Measurable, Drawable {
    private final double width;
    private final double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String draw() {
        return "Drawing rectangle " + width + "x" + height;
    }
}


// ─────────────────────────────────────────────────────────────────
// 2️⃣  SOLUTION 2: Payment System
// ─────────────────────────────────────────────────────────────────

/*
 *  PaymentMethod — the contract for any payment mechanism.
 *  By programming to this interface, code can accept ANY payment type
 *  without knowing the specific implementation.
 */
interface PaymentMethod {
    boolean pay(double amount);
    double getBalance();
    String getName();
}

/*
 *  CreditCard tracks spending against a fixed credit limit.
 *  The "balance" here means remaining available credit.
 */
class CreditCard implements PaymentMethod {
    private final double limit;
    private double spent;

    CreditCard(double limit) {
        this.limit = limit;
        this.spent = 0;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= (limit - spent)) {
            spent += amount;
            return true;
        }
        return false;
    }

    @Override
    public double getBalance() {
        return limit - spent;
    }

    @Override
    public String getName() {
        return "Credit Card";
    }
}

/*
 *  DigitalWallet has a simple balance that decreases with payments.
 */
class DigitalWallet implements PaymentMethod {
    private double balance;

    DigitalWallet(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getName() {
        return "Digital Wallet";
    }
}

/*
 *  BankTransfer works just like DigitalWallet — same contract,
 *  different identity. In a real system, the implementation
 *  details (API calls, verification, etc.) would differ.
 */
class BankTransfer implements PaymentMethod {
    private double balance;

    BankTransfer(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getName() {
        return "Bank Transfer";
    }
}


// ─────────────────────────────────────────────────────────────────
// 3️⃣  SOLUTION 3: Sortable & Displayable Students
// ─────────────────────────────────────────────────────────────────

/*
 *  Displayable — anything that can produce a display string.
 *  This is a great example of a "capability" interface.
 */
interface Displayable {
    String getDisplayString();
}

/*
 *  Rankable — anything that has a numeric rank.
 *  Useful for finding "top" items in a collection.
 */
interface Rankable {
    int getRank();
}

/*
 *  Student implements both Displayable and Rankable.
 *  The grade serves double duty: it's the data AND the rank.
 *  getDisplayString() provides a human-readable format.
 */
class Student implements Displayable, Rankable {
    private final String name;
    private final int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String getDisplayString() {
        return "Name: " + name + ", Grade: " + grade;
    }

    @Override
    public int getRank() {
        return grade;
    }
}


// ═══════════════════════════════════════════════════════════════════
//  🧪 TEST SUITE
// ═══════════════════════════════════════════════════════════════════

public class Solution {

    static int testCount = 0;
    static int passCount = 0;

    static void test(String description, boolean condition) {
        testCount++;
        if (condition) {
            passCount++;
            System.out.println("  ✅ " + description);
        } else {
            System.out.println("  ❌ " + description);
        }
    }

    /*
     *  findLargest — iterates over an array of Measurable objects
     *  and returns the one with the greatest area.
     *  This method doesn't know or care whether it's a Circle,
     *  Rectangle, or any future shape — only that it's Measurable.
     */
    static Measurable findLargest(Measurable[] shapes) {
        Measurable largest = shapes[0];
        for (int i = 1; i < shapes.length; i++) {
            if (shapes[i].getArea() > largest.getArea()) {
                largest = shapes[i];
            }
        }
        return largest;
    }

    /*
     *  findTopStudent — iterates over an array of Rankable objects
     *  and returns the one with the highest rank.
     */
    static Rankable findTopStudent(Rankable[] students) {
        Rankable top = students[0];
        for (int i = 1; i < students.length; i++) {
            if (students[i].getRank() > top.getRank()) {
                top = students[i];
            }
        }
        return top;
    }

    /*
     *  displayAll — prints the display string for each Displayable.
     *  Works with Students, or ANY class that implements Displayable.
     */
    static void displayAll(Displayable[] items) {
        for (Displayable item : items) {
            System.out.println("    " + item.getDisplayString());
        }
    }

    public static void main(String[] args) {

        System.out.println("═══ 1. Shapes with Interfaces ═══");

        Circle c = new Circle(5.0);
        Rectangle r = new Rectangle(4.0, 6.0);

        test("Circle area is π * r²",
            Math.abs(c.getArea() - Math.PI * 25) < 0.001);
        test("Circle perimeter is 2πr",
            Math.abs(c.getPerimeter() - 2 * Math.PI * 5) < 0.001);
        test("Rectangle area is w * h",
            Math.abs(r.getArea() - 24.0) < 0.001);
        test("Rectangle perimeter is 2(w+h)",
            Math.abs(r.getPerimeter() - 20.0) < 0.001);
        test("Circle draw() returns correct string",
            "Drawing circle with radius 5.0".equals(c.draw()));
        test("Rectangle draw() returns correct string",
            "Drawing rectangle 4.0x6.0".equals(r.draw()));
        test("Circle is instanceof Measurable and Drawable",
            c instanceof Measurable && c instanceof Drawable);
        test("findLargest returns the shape with biggest area",
            findLargest(new Measurable[]{c, r}) == c); // circle area ≈78.5 > rect area 24


        System.out.println("\n═══ 2. Payment System ═══");

        CreditCard cc = new CreditCard(5000);
        DigitalWallet dw = new DigitalWallet(200);
        BankTransfer bt = new BankTransfer(1000);

        test("CreditCard getName() returns 'Credit Card'",
            "Credit Card".equals(cc.getName()));
        test("CreditCard pay succeeds within limit",
            cc.pay(1500));
        test("CreditCard balance reflects remaining limit",
            Math.abs(cc.getBalance() - 3500) < 0.001);
        test("CreditCard pay fails when exceeding remaining limit",
            !cc.pay(4000));
        test("DigitalWallet pay succeeds within balance",
            dw.pay(50));
        test("DigitalWallet balance updates after payment",
            Math.abs(dw.getBalance() - 150) < 0.001);
        test("DigitalWallet pay fails with insufficient balance",
            !dw.pay(300));
        test("BankTransfer getName() returns 'Bank Transfer'",
            "Bank Transfer".equals(bt.getName()));


        System.out.println("\n═══ 3. Sortable & Displayable Students ═══");

        Student s1 = new Student("Alice", 95);
        Student s2 = new Student("Bob", 82);
        Student s3 = new Student("Charlie", 91);

        test("Student getDisplayString() format is correct",
            "Name: Alice, Grade: 95".equals(s1.getDisplayString()));
        test("Student getRank() returns the grade",
            s1.getRank() == 95);
        test("Student getName() returns the name",
            "Bob".equals(s2.getName()));
        test("Student getGrade() returns the grade",
            s2.getGrade() == 82);
        test("Student is instanceof Displayable and Rankable",
            s1 instanceof Displayable && s1 instanceof Rankable);
        test("findTopStudent returns student with highest rank",
            findTopStudent(new Rankable[]{s1, s2, s3}) == s1);

        System.out.println("  📝 displayAll output:");
        displayAll(new Displayable[]{s1, s2, s3});
        test("displayAll doesn't crash (visual check above)",
            true);


        // ─────────────────────────────────────────────────────────
        //  Results
        // ─────────────────────────────────────────────────────────
        System.out.println("\nResults: " + passCount + "/" + testCount + " tests passed");
        if (passCount == testCount) {
            System.out.println("🎉 All tests passed! Outstanding work!");
        } else {
            System.out.println("💪 Keep going! Review the failing tests and try again.");
        }
    }
}
