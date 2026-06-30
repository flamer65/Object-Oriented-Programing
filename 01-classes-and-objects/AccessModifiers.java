/*
 * ══════════════════════════════════════════════════════════════
 *  📘 LESSON 2: Access Modifiers (Java)
 * ══════════════════════════════════════════════════════════════
 *
 *  Access modifiers control WHO can access a class's fields and methods.
 *  Think of it like security levels in a building:
 *
 *  ┌────────────────┬──────────────────────────────────────────────────────┐
 *  │  Modifier      │  Who Can Access?                                     │
 *  ├────────────────┼──────────────────────────────────────────────────────┤
 *  │  public        │  Anyone, anywhere                                    │
 *  │  private       │  Only inside the SAME class                          │
 *  │  protected     │  Same class + subclasses + same package              │
 *  │  (default)     │  Same package only (no keyword = "package-private")  │
 *  │  final         │  Can be set once (in declaration or constructor),    │
 *  │                │  then NEVER changed (Java's version of "readonly")   │
 *  └────────────────┴──────────────────────────────────────────────────────┘
 *
 *  JAVA vs TYPESCRIPT — KEY DIFFERENCES:
 *  ──────────────────────────────────────
 *  • TS default is `public`.  Java default is "package-private" (no keyword).
 *  • TS uses `readonly`.     Java uses `final`.
 *  • TS `private` is compile-time only.  Java `private` is enforced at RUNTIME.
 *  • TS has `#field` for runtime privacy. Java doesn't need it — `private` already works.
 *
 *  WHY USE ACCESS MODIFIERS?
 *  ─────────────────────────
 *  • Prevents accidental modification of internal data
 *  • Forces users of your class to go through controlled methods
 *  • Makes your code more maintainable and less buggy
 *  • This concept is called ENCAPSULATION (one of the 4 pillars of OOP)
 *
 * ══════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────
// 1️⃣  public — Accessible Everywhere
// ─────────────────────────────────────────────
/*
 *  When a field or method is `public`, ANY code can read it,
 *  write it, or call it — no restrictions at all.
 *
 *  NOTE: In Java, if you omit the modifier on a top-level class
 *  inside a file with another public class, it becomes "package-private"
 *  (accessible only within the same package). For fields inside a class,
 *  omitting the modifier also means package-private, NOT public.
 *  This is different from TypeScript where the default is public.
 */

 class Car { // package-private: accessible only within the same package, not from another package
    public String brand;    // explicitly public
    public String model;    // explicitly public (Java default is NOT public!)
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
/*
 *  `private` fields and methods are COMPLETELY hidden from outside.
 *  The only way to interact with them is through public methods
 *  that the class chooses to expose — these are called "getters",
 *  "setters", or more generally, the class's PUBLIC API.
 *
 *  This is the heart of ENCAPSULATION:
 *    → Hide the data, expose controlled methods.
 */

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
/*
 *  TypeScript uses `readonly`.  Java uses `final`.
 *
 *  A `final` field can be assigned:
 *    - At the declaration:     final String ISBN = "978-...";
 *    - OR in the constructor:  this.isbn = isbn;
 *  But NEVER again after that. Any later assignment is a compile error.
 *
 *  ┌────────────────────────────────────────────────────────┐
 *  │   TypeScript              →    Java                    │
 *  ├────────────────────────────────────────────────────────┤
 *  │   readonly isbn: string;  →    final String isbn;      │
 *  │   readonly title: string; →    final String title;     │
 *  └────────────────────────────────────────────────────────┘
 */

class Book {
    final String isbn;             // Can ONLY be set in constructor
    final String title;            // Can ONLY be set in constructor
    public String author;          // Can be changed freely
    public int pages;

    Book(String isbn, String title, String author, int pages) {
        this.isbn = isbn;          // ✅ Setting in constructor is allowed
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
/*
 *  In Java, you can combine access modifiers with `final`:
 *
 *  ┌────────────────────────────────────────────────────────────────┐
 *  │   Field                     │  Access     │  Mutable?          │
 *  ├────────────────────────────────────────────────────────────────┤
 *  │   public final int id       │  Everyone   │  No (set once)     │
 *  │   public String name        │  Everyone   │  Yes               │
 *  │   private double salary     │  Class only │  Yes (internally)  │
 *  │   protected String dept     │  Class +    │  Yes               │
 *  │                             │  subclasses │                    │
 *  └────────────────────────────────────────────────────────────────┘
 *
 *  NOTE: Unlike TypeScript, Java does NOT have parameter properties
 *  (constructor shorthand). We must declare each field separately and
 *  assign them in the constructor body.
 */

class Employee {
    // Each field is declared with its modifier(s)
    public final int id;              // public makes it accessible from other packages; final means it can only be assigned once
    public String name;               // public (changeable)
    private double salary;            // private (hidden from outside)
    protected String department;      // protected (accessible in subclasses)

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
        return "$" + String.format("%,.0f", this.salary);
    }

    // Private method — only usable inside this class
    private double calculateBonus() {
        return this.salary * 0.1;
    }

    public String getBonus() {
        // ✅ Can call private method from within the same class
        return "Bonus: $" + String.format("%,.0f", this.calculateBonus());
    }
}

// ─────────────────────────────────────────────
// 5️⃣  Java's Encapsulation — Already Runtime-Enforced
// ─────────────────────────────────────────────
/*
 *  In TypeScript:
 *    - `private` is only enforced at COMPILE TIME.
 *    - At runtime (plain JavaScript), private fields are still accessible!
 *    - That's why TS added `#field` syntax for TRUE runtime privacy.
 *
 *  In Java:
 *    - `private` is enforced at BOTH compile time AND runtime.
 *    - There is no way to access a private field from outside the class
 *      (unless you use advanced Reflection, which is intentionally difficult).
 *    - Java does NOT need a special `#` syntax — `private` already does the job!
 *
 *  ┌────────────────────────────────────────────────────────────────┐
 *  │   Feature             │  TypeScript         │  Java            │
 *  ├────────────────────────────────────────────────────────────────┤
 *  │   Compile-time        │  private keyword    │  private keyword │
 *  │   privacy             │                     │                  │
 *  ├────────────────────────────────────────────────────────────────┤
 *  │   Runtime privacy     │  # prefix needed    │  private keyword │
 *  │                       │                     │  (already works!)│
 *  ├────────────────────────────────────────────────────────────────┤
 *  │   Workaround to       │  Easy (just access  │  Only via        │
 *  │   bypass privacy      │  the JS property)   │  Reflection (hard│
 *  │                       │                     │  & discouraged)  │
 *  └────────────────────────────────────────────────────────────────┘
 */

class SecretVault {
    private String secretCode;
    private int attempts = 0;

    SecretVault(String code) {
        this.secretCode = code;
    }

    public String unlock(String guess) {
        this.attempts++;
        if (guess.equals(this.secretCode)) {
            return "✅ Unlocked on attempt #" + this.attempts + "!";
        }
        return "❌ Wrong code. Attempts: " + this.attempts;
    }

    // No getter for secretCode — it's truly hidden!
    // In Java, `private` means private. Period. No # needed.
}

// ══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (file name must be AccessModifiers.java)
// ══════════════════════════════════════════════════════════════

public class AccessModifiers {
    public static void main(String[] args) {

        // ═══ 1. public — Accessible Everywhere ═══
        Car myCar = new Car("Toyota", "Camry", 2024);

        System.out.println("═══ 1. public — Accessible Everywhere ═══");
        System.out.println(myCar.brand);        // Toyota     ✅ Can access from outside
        System.out.println(myCar.model);        // Camry      ✅ Can access from outside
        System.out.println(myCar.describe());   // 2024 Toyota Camry  ✅ Can call from outside
        myCar.brand = "Honda";                  // ✅ Can modify from outside
        System.out.println("Changed brand: " + myCar.brand);  // Honda

        // ═══ 2. private — Only Inside the Class ═══
        BankAccount account = new BankAccount("Naman", 1000, 1234);

        System.out.println("\n═══ 2. private — Only Inside the Class ═══");
        System.out.println("Holder: " + account.accountHolder);  // ✅ public
        // System.out.println(account.balance);    // ❌ COMPILE ERROR: balance has private access
        // System.out.println(account.pin);        // ❌ COMPILE ERROR: pin has private access

        account.deposit(500);                        // ✅ Deposited $500.0. New balance: $1500.0
        account.withdraw(200, 1234);                 // ✅ Withdrew $200.0. New balance: $1300.0
        account.withdraw(100, 9999);                 // ❌ Wrong PIN!
        System.out.println(account.getBalance(1234)); // 💰 Balance: $1300.0

        // ═══ 3. final — Set Once, Never Changed ═══
        Book book = new Book("978-0-13-468599-1", "The Pragmatic Programmer",
                             "David Thomas", 352);

        System.out.println("\n═══ 3. final — Set Once, Never Changed ═══");
        System.out.println(book.describe());
        System.out.println("ISBN: " + book.isbn);       // ✅ Can READ a final field
        // book.isbn = "000-000";                       // ❌ COMPILE ERROR: cannot assign a value to final variable isbn
        book.author = "David Thomas & Andrew Hunt";     // ✅ Not final, so can change
        System.out.println("Updated author: " + book.author);
        System.out.println("📝 In Java, `final` = TypeScript's `readonly`. Set once, read forever.");

        // ═══ 4. Combined Modifiers ═══
        Employee emp = new Employee(101, "Naman", 85000, "Engineering");

        System.out.println("\n═══ 4. Combined Modifiers ═══");
        System.out.println(emp.getInfo());            // [#101] Naman — Engineering
        System.out.println("Salary: " + emp.getSalary()); // Salary: $85,000
        System.out.println(emp.getBonus());           // Bonus: $8,500
        System.out.println("ID: " + emp.id);          // ✅ Can READ (public)
        // emp.id = 999;                              // ❌ COMPILE ERROR: final
        // System.out.println(emp.salary);            // ❌ COMPILE ERROR: private
        // System.out.println(emp.department);        // ❌ COMPILE ERROR: protected (only in subclasses/same package)

        System.out.println("\n--- Modifier Summary ---");
        System.out.println("  emp.id         → public final  → ✅ read, ❌ write");
        System.out.println("  emp.name       → public        → ✅ read, ✅ write");
        System.out.println("  emp.salary     → private       → ❌ read, ❌ write (use getSalary())");
        System.out.println("  emp.department → protected     → ❌ outside (✅ in subclasses)");

        // ═══ 5. Java's Encapsulation ═══
        SecretVault vault = new SecretVault("open-sesame");

        System.out.println("\n═══ 5. Java's Encapsulation — Runtime-Enforced ═══");
        System.out.println(vault.unlock("hello"));        // ❌ Wrong code. Attempts: 1
        System.out.println(vault.unlock("password"));     // ❌ Wrong code. Attempts: 2
        System.out.println(vault.unlock("open-sesame"));  // ✅ Unlocked on attempt #3!
        // System.out.println(vault.secretCode);          // ❌ COMPILE ERROR: secretCode has private access
        System.out.println();
        System.out.println("📝 In Java, `private` is enforced at RUNTIME — not just compile time.");
        System.out.println("   Unlike TypeScript, there's no need for a special # syntax.");
        System.out.println("   Java's `private` truly means private. Period.");
    }
}

// ─────────────────────────────────────────────
// 💡 KEY TAKEAWAYS
// ─────────────────────────────────────────────
// ✅ `public`     → Anyone can access. (But NOT the Java default!)
// ✅ `private`    → Only accessible inside the class itself. Enforced at runtime.
// ✅ `protected`  → Accessible inside the class + subclasses + same package.
// ✅ `final`      → Java's version of readonly. Set once (declaration or constructor), never again.
// ✅ (no keyword) → "Package-private": accessible only within the same package.
// ✅ Access modifiers = ENCAPSULATION = protecting internal data.
// ✅ Java's `private` is already runtime-enforced — no need for TS's `#field` workaround.
