/*
 * ═════════════════════════════════════════════════════════════
 *  📘 LESSON 2: Access Modifiers
 * ═════════════════════════════════════════════════════════════
 *
 *  Access modifiers control WHO can access a class's fields and methods.
 *  Think of it like security levels:
 *
 *  ┌───────────────┬──────────────────────────────────────────────────────┐
 *  │  Modifier     │  Who Can Access?                                     │
 *  ├───────────────┼──────────────────────────────────────────────────────┤
 *  │  public       │  Anyone, anywhere                                    │
 *  │  (default)    │  Classes in the same package (package-private)       │
 *  │  protected    │  Same package + child classes (subclasses)           │
 *  │  private      │  Only inside the SAME class                          │
 *  │  final        │  Anyone can READ, but nobody can WRITE after init    │
 *  └───────────────┴──────────────────────────────────────────────────────┘
 *
 *  JAVA vs TYPESCRIPT:
 *  ─────────────────────
 *  ┌──────────────────────────┬────────────────────────────────────────┐
 *  │  TypeScript              │  Java                                  │
 *  ├──────────────────────────┼────────────────────────────────────────┤
 *  │  public (default)        │  package-private (default)             │
 *  │  private (compile-time)  │  private (RUNTIME enforced! ✅)        │
 *  │  readonly                │  final                                 │
 *  │  #field (runtime priv.)  │  private (already runtime-enforced)    │
 *  └──────────────────────────┴────────────────────────────────────────┘
 *
 *  WHY USE ACCESS MODIFIERS?
 *  ─────────────────────────
 *  • Prevents accidental modification of internal data
 *  • Forces users of your class to go through controlled methods
 *  • Makes your code more maintainable and less buggy
 *  • This concept is called ENCAPSULATION (one of the 4 pillars of OOP)
 *
 * ═════════════════════════════════════════════════════════════
 */

import java.text.NumberFormat;
import java.util.Locale;

// ─────────────────────────────────────────────
// 1️⃣  public — Accessible Everywhere
// ─────────────────────────────────────────────
//
// Public fields and methods can be accessed from any other class.
// NOTE: In Java, the default access level is "package-private" (no keyword),
// which means accessible only within the same package. We use `public`
// explicitly to allow access from anywhere.

class Car {
    public String brand;      // explicitly public
    String model;             // package-private (default in Java — accessible in same package)
    public int year;

    Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String describe() {
        return this.year + " " + this.brand + " " + this.model;
    }
}

// ─────────────────────────────────────────────
// 2️⃣  private — Only Inside the Same Class
// ─────────────────────────────────────────────
//
// Private fields/methods CANNOT be accessed from outside the class.
// You must provide public methods as controlled gateways (getters, etc.).
// Unlike TypeScript where `private` is compile-time only, Java's `private`
// is enforced at RUNTIME too — it's truly hidden.

class BankAccount {
    public String accountHolder;
    private double balance;          // ❌ Cannot access from outside
    private int pin;                 // ❌ Cannot access from outside

    BankAccount(String holder, double initialBalance, int pin) {
        this.accountHolder = holder;
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Public methods act as CONTROLLED GATEWAYS to private data
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("  ❌ Deposit amount must be positive!");
            return;
        }
        this.balance += amount;
        System.out.println("  ✅ Deposited $" + amount + ". New balance: $" + this.balance);
    }

    public void withdraw(double amount, int enteredPin) {
        if (enteredPin != this.pin) {
            System.out.println("  ❌ Wrong PIN!");
            return;
        }
        if (amount > this.balance) {
            System.out.println("  ❌ Insufficient funds!");
            return;
        }
        this.balance -= amount;
        System.out.println("  ✅ Withdrew $" + amount + ". New balance: $" + this.balance);
    }

    public String getBalance(int enteredPin) {
        if (enteredPin != this.pin) {
            return "  ❌ Wrong PIN!";
        }
        return "  💰 Balance: $" + this.balance;
    }
}

// ─────────────────────────────────────────────
// 3️⃣  final — Set Once, Never Changed (Java's readonly)
// ─────────────────────────────────────────────
//
// `final` in Java is equivalent to `readonly` in TypeScript.
// A final field can ONLY be assigned once — either at declaration
// or in the constructor. After that, it can never be changed.

class Book {
    final String isbn;            // Can ONLY be set in constructor
    final String title;
    public String author;         // Can be changed later
    public int pages;

    Book(String isbn, String title, String author, int pages) {
        this.isbn = isbn;         // ✅ Setting in constructor is allowed
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    String describe() {
        return "\"" + this.title + "\" by " + this.author
                + " (ISBN: " + this.isbn + ", " + this.pages + " pages)";
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Combining Modifiers
// ─────────────────────────────────────────────
//
// In Java, you combine access modifiers with `final` to create
// different levels of access control. Unlike TypeScript, Java
// doesn't have constructor parameter shorthand, so we declare
// each field explicitly and assign in the constructor.

class Employee {
    public final int id;             // public + final (readable but immutable)
    public String name;              // public (changeable)
    private double salary;           // private (hidden from outside)
    protected String department;     // protected (accessible in subclasses)

    Employee(int id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public String getInfo() {
        return "[#" + this.id + "] " + this.name + " — " + this.department;
    }

    public String getSalary() {
        return "$" + NumberFormat.getNumberInstance(Locale.US).format(this.salary);
    }

    // Private method — only usable inside this class
    private double calculateBonus() {
        return this.salary * 0.1;
    }

    public String getBonus() {
        // ✅ Can call private method from within the same class
        return "Bonus: $" + NumberFormat.getNumberInstance(Locale.US).format(this.calculateBonus());
    }
}

// ═════════════════════════════════════════════════════════════
// PUBLIC CLASS — File name must match: AccessModifiers.java
// ═════════════════════════════════════════════════════════════

public class AccessModifiers {
    public static void main(String[] args) {

        // ═══ 1. public — Accessible Everywhere ═══
        System.out.println("═══ 1. public — Accessible Everywhere ═══");

        Car myCar = new Car("Toyota", "Camry", 2024);

        System.out.println(myCar.brand);         // ✅ Can access from outside → Toyota
        System.out.println(myCar.model);          // ✅ Can access (same package) → Camry
        System.out.println(myCar.describe());     // ✅ Can call from outside → 2024 Toyota Camry
        myCar.brand = "Honda";                    // ✅ Can modify from outside
        System.out.println("Changed brand: " + myCar.brand); // Honda

        // ═══ 2. private — Only Inside the Class ═══
        System.out.println("\n═══ 2. private — Only Inside the Class ═══");

        BankAccount account = new BankAccount("Naman", 1000, 1234);

        System.out.println("Holder: " + account.accountHolder); // ✅ public
        // System.out.println(account.balance);                  // ❌ ERROR: balance has private access
        // System.out.println(account.pin);                      // ❌ ERROR: pin has private access

        account.deposit(500);                     // ✅ Deposited $500.0. New balance: $1500.0
        account.withdraw(200, 1234);              // ✅ Withdrew $200.0. New balance: $1300.0
        account.withdraw(100, 9999);              // ❌ Wrong PIN!
        System.out.println(account.getBalance(1234));  // 💰 Balance: $1300.0

        // ═══ 3. final — Set Once, Never Changed ═══
        System.out.println("\n═══ 3. final — Set Once, Never Changed ═══");

        Book book = new Book("978-0-13-468599-1", "The Pragmatic Programmer", "David Thomas", 352);

        System.out.println(book.describe());
        // "The Pragmatic Programmer" by David Thomas (ISBN: 978-0-13-468599-1, 352 pages)
        System.out.println("ISBN: " + book.isbn); // ✅ Can READ
        // book.isbn = "000-000";                  // ❌ ERROR: cannot assign a value to final variable isbn
        book.author = "David Thomas & Andrew Hunt"; // ✅ Not final, so can change
        System.out.println("Updated author: " + book.author);

        // ═══ 4. Combined Modifiers ═══
        System.out.println("\n═══ 4. Combined Modifiers ═══");

        Employee emp = new Employee(101, "Naman", 85000, "Engineering");

        System.out.println(emp.getInfo());        // [#101] Naman — Engineering
        System.out.println("Salary: " + emp.getSalary()); // Salary: $85,000
        System.out.println(emp.getBonus());       // Bonus: $8,500
        System.out.println("ID: " + emp.id);      // ✅ Can READ (public)
        // emp.id = 999;                           // ❌ ERROR: final — cannot reassign
        // System.out.println(emp.salary);         // ❌ ERROR: private
        // System.out.println(emp.department);     // ❌ ERROR: protected (only in subclasses / same package)

        // ═══ 5. Java's Encapsulation — True Runtime Privacy ═══
        System.out.println("\n═══ 5. Java's Encapsulation ═══");

        // In TypeScript, `private` is only a COMPILE-TIME guard.
        // At runtime (in JavaScript), you can still access private fields.
        // That's why TypeScript also offers `#privateField` for runtime privacy.
        //
        // In Java, `private` is ALREADY enforced at RUNTIME. ✅
        // There is no equivalent of `#field` because it's not needed!
        // Java's access control is built into the JVM itself.
        //
        // The only way to bypass it is reflection (an advanced feature),
        // which is intentionally difficult and can be disabled with security managers.

        System.out.println("📝 TypeScript's `private` = compile-time only (can be bypassed at runtime)");
        System.out.println("📝 TypeScript's `#field` = true runtime privacy (enforced in JS engine)");
        System.out.println("📝 Java's `private` = ALREADY runtime-enforced (no # syntax needed!) ✅");
        System.out.println("📝 Java's access control is built into the JVM — it's always real.");

        // Demonstrate: you simply cannot access private fields from outside
        BankAccount vault = new BankAccount("Secret", 999999, 0000);
        // vault.balance    ← This line would not even compile!
        // vault.pin        ← This line would not even compile!
        System.out.println("\n✅ vault.accountHolder (public): " + vault.accountHolder);
        System.out.println("❌ vault.balance → compile error: balance has private access in BankAccount");
        System.out.println("❌ vault.pin     → compile error: pin has private access in BankAccount");
        System.out.println("🔒 Java's private is a real lock, not just a warning label.");

        // ─────────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────────
        // ✅ `public`    → Anyone can access.
        // ✅ `private`   → Only accessible inside the class itself (runtime-enforced!).
        // ✅ `protected` → Accessible inside the class, same package, + subclasses.
        // ✅ `final`     → Can be read anywhere but only set once (in constructor or declaration).
        // ✅ (default)   → Package-private: accessible only within the same package.
        // ✅ Java's `private` is true runtime privacy — no need for # syntax like TypeScript.
        // ✅ Access modifiers = ENCAPSULATION = protecting internal data.
    }
}
