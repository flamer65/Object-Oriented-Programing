/*
 * ═══════════════════════════════════════════════════════════════════
 *  🏋️ EXERCISES: Interfaces
 *  Module 03 — Defining Contracts & Achieving Polymorphism
 * ═══════════════════════════════════════════════════════════════════
 *
 *  Interfaces define CONTRACTS — they specify WHAT an object can do,
 *  without dictating HOW it does it.
 *
 *  ┌─────────────────────────────────────────────────────────┐
 *  │  Interface  vs  Class                                   │
 *  ├─────────────────────────────────────────────────────────┤
 *  │  "Can do"       "Is a"                                  │
 *  │  No state       Has state (fields)                      │
 *  │  All abstract    Can have concrete methods              │
 *  │  Multiple OK     Single inheritance only                │
 *  └─────────────────────────────────────────────────────────┘
 *
 *  Complete each exercise by creating the interfaces and classes
 *  from scratch. Write EVERYTHING yourself — no prebuilt code!
 *
 *  Run: javac Exercise.java && java Exercise
 *  See ✅ for pass, ❌ for fail. Goal: all ✅!
 * ═══════════════════════════════════════════════════════════════════
 */


// ─────────────────────────────────────────────────────────────────
// 1️⃣  EXERCISE 1: Shapes with Interfaces
// ─────────────────────────────────────────────────────────────────
/*
 *  Build a shape system using two interfaces:
 *
 *  📐 Measurable interface:
 *     • double getArea()       — returns the area of the shape
 *     • double getPerimeter()  — returns the perimeter of the shape
 *
 *  🎨 Drawable interface:
 *     • String draw()          — returns a string describing the drawing
 *
 *  Implement TWO classes that implement BOTH interfaces:
 *
 *  🔵 Circle:
 *     • Constructor: Circle(double radius)
 *     • getArea()      → Math.PI * radius * radius
 *     • getPerimeter() → 2 * Math.PI * radius
 *     • draw()         → "Drawing circle with radius X"
 *                         (where X is the radius, e.g. "Drawing circle with radius 5.0")
 *
 *  🟦 Rectangle:
 *     • Constructor: Rectangle(double width, double height)
 *     • getArea()      → width * height
 *     • getPerimeter() → 2 * (width + height)
 *     • draw()         → "Drawing rectangle WxH"
 *                         (e.g. "Drawing rectangle 4.0x6.0")
 */

// TODO: Create your Measurable interface here
interface Measurable {
    double getArea();
    double getPerimeter();
}

// TODO: Create your Drawable interface here
interface Drawable {
    String draw();
}

// TODO: Create your Circle class here (implements Measurable, Drawable)
class Circle implements Measurable, Drawable {
    private double radius;
    Circle(double radius){
        this.radius = radius;
    }
    @Override
    public double getArea(){
        return Math.PI * radius * radius;
    }
    @Override
    public double getPerimeter(){
        return 2 * Math.PI * radius;
    }
    @Override
    public String draw(){
        return "Drawing circle with radius " + radius;
    }
}

// TODO: Create your Rectangle class here (implements Measurable, Drawable)
class Rectangle implements Measurable, Drawable{
    private double width;
    private double height;
    Rectangle(double height, double width)
    {
        this.width = width;
        this.height = height;
    }
    @Override
    public double getArea(){
        return width * height;
    }
    @Override
    public double getPerimeter(){
        return 2 * (width + height);
    }
    @Override
    public String draw(){
        return "Drawing rectangle " + height + "x" + width;
    }
}

// ─────────────────────────────────────────────────────────────────
// 2️⃣  EXERCISE 2: Payment System
// ─────────────────────────────────────────────────────────────────
/*
 *  Build a payment system with a common interface:
 *
 *  💳 PaymentMethod interface:
 *     • boolean pay(double amount) — attempt to pay; return true if successful
 *     • double getBalance()        — return the remaining balance/limit
 *     • String getName()           — return the name of the payment method
 *
 *  Implement THREE classes:
 *
 *  💳 CreditCard:
 *     • Constructor: CreditCard(double limit)
 *     • Has a credit limit (e.g., 5000). Tracks how much has been spent.
 *     • pay(amount)   → succeeds if amount <= remaining limit; deducts from limit
 *     • getBalance()  → returns remaining limit (limit - amountSpent)
 *     • getName()     → "Credit Card"
 *
 *  📱 DigitalWallet:
 *     • Constructor: DigitalWallet(double balance)
 *     • Has a balance that decreases with each payment.
 *     • pay(amount)   → succeeds if amount <= balance; deducts from balance
 *     • getBalance()  → returns current balance
 *     • getName()     → "Digital Wallet"
 *
 *  🏦 BankTransfer:
 *     • Constructor: BankTransfer(double balance)
 *     • Has a balance that decreases with each payment.
 *     • pay(amount)   → succeeds if amount <= balance; deducts from balance
 *     • getBalance()  → returns current balance
 *     • getName()     → "Bank Transfer"
 */

// TODO: Create your PaymentMethod interface here
interface PaymentMethod {
    boolean pay(double amount);
    double getBalance();
    String getName();
}

// TODO: Create your CreditCard class here (implements PaymentMethod)
class CreditCard implements PaymentMethod{
   public  double limit;
   private double spent;
    CreditCard(double limit){
        this.limit = limit;
        this.spent = 0;
    }
    @Override
    public boolean pay(double amount){
        if (amount <= limit) {
            this.spent += amount;
            this.limit -= this.spent;
            return true;
        }
        return false;
    }
    @Override
    public double getBalance(){
        return this.limit;
    }
    @Override
    public String getName(){
        return "Credit Card";
    }
}

// TODO: Create your DigitalWallet class here (implements PaymentMethod)
class DigitalWallet implements PaymentMethod{
    private double  balance;
    DigitalWallet(double balance){
        this.balance = balance;
    }
    @Override
    public boolean pay(double amount){
        if(amount <= balance){
            balance -= amount;
            return true;
        }
        return false;
    }
    @Override
    public double getBalance(){
        return balance;
    }
    @Override
    public String getName(){
        return "Digital Wallet";
    }
}

// TODO: Create your BankTransfer class here (implements PaymentMethod)
class BankTransfer implements PaymentMethod{
    private double balance;
    public BankTransfer(double balance){
        this.balance = balance;
    }
    @Override
    public boolean pay(double amount){
        if(amount <= balance){
            balance -= amount;
            return true;
        }
        return false;
    }
    @Override
    public double getBalance(){
        return balance;
    }
    @Override
    public String getName(){
        return "Bank Transfer";
    }
}

// ─────────────────────────────────────────────────────────────────
// 3️⃣  EXERCISE 3: Sortable & Displayable Students
// ─────────────────────────────────────────────────────────────────
/*
 *  Build a student system using two interfaces:
 *
 *  📋 Displayable interface:
 *     • String getDisplayString() — returns a formatted display string
 *
 *  🏆 Rankable interface:
 *     • int getRank() — returns a numeric rank value
 *
 *  Implement a Student class:
 *
 *  🎓 Student:
 *     • Constructor: Student(String name, int grade)
 *       - grade is 0–100
 *     • Implements BOTH Displayable and Rankable
 *     • getDisplayString() → "Name: [name], Grade: [grade]"
 *                             (e.g. "Name: Alice, Grade: 95")
 *     • getRank()          → returns the grade
 *     • getName()          → returns the student's name
 *     • getGrade()         → returns the student's grade
 *
 *  Also implement TWO static methods in Exercise class (below in the test suite):
 *
 *     static Rankable findTopStudent(Rankable[] students)
 *     — returns the student with the highest rank
 *
 *     static void displayAll(Displayable[] items)
 *     — prints each item's display string using System.out.println
 */

// TODO: Create your Displayable interface here
interface Displayable {
    String getDisplayString();
}

// TODO: Create your Rankable interface here
interface Rankable {
    int getRank();
}

// TODO: Create your Student class here (implements Displayable, Rankable)
class Student implements Displayable, Rankable{
    private String name;
    private int grade;
    public Student(String name, int grade){
        this.name = name;
        this.grade = grade;
        
    }
    @Override
    public String getDisplayString(){
        return "Name: " + name + ", Grade: " + grade;
    }
    @Override
    public int getRank(){
        return this.grade;
    }
    public String getName(){
        return this.name;
    }
    public int getGrade(){
        return this.grade;
    }
}

// ═══════════════════════════════════════════════════════════════════
//  🧪 TEST SUITE — Do NOT modify below this line
//  (except for the 3 TODO methods: findLargest, findTopStudent, displayAll)
// ═══════════════════════════════════════════════════════════════════

public class Exercise {

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

    // TODO: Implement findLargest — returns the Measurable with the biggest area
    static Measurable findLargest(Measurable[] shapes) {
        if (shapes == null || shapes.length == 0) {
            return null;
        }
        Measurable largest = shapes[0];
        for (int i = 1; i < shapes.length; i++) {
            if (shapes[i].getArea() > largest.getArea()) {
                largest = shapes[i];
            }
        }
        return largest;
    }

    // TODO: Implement findTopStudent — returns the Rankable with the highest rank
    static Rankable findTopStudent(Rankable[] students) {
       if (students == null || students.length == 0) {
           return null;
       }
       Rankable top = students[0];
       for (int i = 1; i < students.length; i++) {
           if (students[i].getRank() > top.getRank()) {
               top = students[i];
           }
       }
       return top;
    }

    // TODO: Implement displayAll — prints each Displayable's display string
    static void displayAll(Displayable[] items) {
        if(items == null || items.length == 0){
            return;
        }
        for (Displayable item: items){
            if (item != null) {
                System.out.println(item.getDisplayString());
            }
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
            findLargest(new Measurable[]{c, r}) == c);


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
        System.out.println(cc.getBalance());
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
