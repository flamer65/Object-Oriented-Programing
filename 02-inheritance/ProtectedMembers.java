/*
 * ═══════════════════════════════════════════════════════════════
 *  📘 MODULE 02 — LESSON 2: Protected Members in Inheritance
 * ═══════════════════════════════════════════════════════════════
 *
 *  QUICK RECAP — ACCESS MODIFIERS IN INHERITANCE:
 *  ──────────────────────────────────────────────
 *
 *  ┌──────────────┬───────────┬─────────────┬────────────-┬───────────┐
 *  │  Modifier    │ Same Class│ Same Package│ Child Class │  Outside  │
 *  ├──────────────┼───────────┼─────────────┼────────────-┼───────────┤
 *  │  public      │    ✅     │     ✅      │     ✅     │    ✅     │
 *  │  protected   │    ✅     │     ✅      │     ✅     │    ❌      │
 *  │  (default)   │    ✅     │     ✅      │     ❌*     │    ❌      │
 *  │  private     │    ✅     │     ❌       │     ❌       │    ❌      │
 *  └──────────────┴───────────┴─────────────┴────────────┴───────────┘
 *
 *  * (default) = package-private, accessible in same package only.
 *
 *  NOTE: Java has an extra level — "package-private" (no modifier).
 *  TypeScript doesn't have this concept. In Java, `protected` also
 *  grants access within the same package, not just child classes.
 *
 *  `protected` is the MIDDLE GROUND:
 *    - Hidden from the outside world (like private)
 *    - But VISIBLE to child classes (unlike private)
 *
 *  WHEN TO USE PROTECTED?
 *  ──────────────────────
 *  When a parent class has internal data/methods that:
 *    ✅ Should NOT be accessible from outside the hierarchy
 *    ✅ But SHOULD be accessible by child classes
 *
 * ═══════════════════════════════════════════════════════════════
 */

// ─────────────────────────────────────────────
// 1️⃣  protected vs private — The Difference
// ─────────────────────────────────────────────

/*
 * Account class — demonstrates the three access levels.
 *   public owner        → everyone can access
 *   protected balance   → children CAN access, outside CANNOT
 *   private accountNumber → ONLY this class can access
 */
class Account {
    public String owner;
    protected double balance;           // ✅ Children CAN access
    private String accountNumber;       // ❌ Children CANNOT access

    Account(String owner, double balance, String accountNumber) {
        this.owner = owner;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return this.balance;
    }

    // Protected method — children can call it, outsiders cannot
    protected String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
}

/*
 * SavingsAccount — demonstrates accessing protected members.
 * Can access `balance` and `formatCurrency()`, but NOT `accountNumber`.
 */
class SavingsAccount extends Account {
    private double interestRate;

    SavingsAccount(String owner, double balance, String accountNumber, double interestRate) {
        super(owner, balance, accountNumber);
        this.interestRate = interestRate;
    }

    String applyInterest() {
        // ✅ Can access `this.balance` — it's PROTECTED
        double interest = this.balance * this.interestRate;
        this.balance += interest;

        // ✅ Can call `this.formatCurrency()` — it's PROTECTED
        return "Interest applied: " + this.formatCurrency(interest)
             + ". New balance: " + this.formatCurrency(this.balance);

        // ❌ Cannot access `this.accountNumber` — it's PRIVATE to Account
        // System.out.println(this.accountNumber); // ERROR!
    }
}

/*
 * CheckingAccount — another child that uses protected balance for overdraft logic.
 */
class CheckingAccount extends Account {
    private double overdraftLimit;

    CheckingAccount(String owner, double balance, String accountNumber, double overdraftLimit) {
        super(owner, balance, accountNumber);
        this.overdraftLimit = overdraftLimit;
    }

    String withdraw(double amount) {
        // ✅ Can access protected `this.balance`
        if (amount > this.balance + this.overdraftLimit) {
            return "❌ Exceeds overdraft limit! Balance: " + this.formatCurrency(this.balance)
                 + ", Limit: " + this.formatCurrency(this.overdraftLimit);
        }
        this.balance -= amount;
        return "✅ Withdrew " + this.formatCurrency(amount)
             + ". Balance: " + this.formatCurrency(this.balance);
    }
}

// ─────────────────────────────────────────────
// 2️⃣  Protected Constructor — Abstract-like Pattern
// ─────────────────────────────────────────────
//
// A protected constructor prevents direct instantiation from outside.
// Only child classes can call `super()`.

/*
 * BaseLogger — has a protected constructor.
 * You cannot do `new BaseLogger("test")` from outside this package.
 * Only child classes (AppLogger, DbLogger) can call super().
 *
 * NOTE: In Java, a truly "un-instantiable" base class is usually
 * declared `abstract`. Protected constructors work when all classes
 * are in the same package (like this file).
 */
class BaseLogger {
    protected String prefix;

    // Protected constructor — cannot do `new BaseLogger()` from outside
    protected BaseLogger(String prefix) {
        this.prefix = prefix;
    }

    protected String formatMessage(String level, String message) {
        // Simulate a timestamp (use a fixed format for demo)
        String timestamp = java.time.LocalTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        return "[" + timestamp + "] [" + level + "] " + this.prefix + ": " + message;
    }

    void log(String message) {
        System.out.println(this.formatMessage("INFO", message));
    }
}

/*
 * AppLogger — extends BaseLogger, adds warn() and error().
 */
class AppLogger extends BaseLogger {
    AppLogger(String appName) {
        super("APP:" + appName);  // ✅ Children CAN call protected constructor
    }

    void warn(String message) {
        System.out.println(this.formatMessage("WARN", message));
    }

    void error(String message) {
        System.out.println(this.formatMessage("ERROR", message));
    }
}

/*
 * DbLogger — extends BaseLogger, adds query().
 */
class DbLogger extends BaseLogger {
    DbLogger(String dbName) {
        super("DB:" + dbName);
    }

    void query(String sql) {
        System.out.println(this.formatMessage("QUERY", sql));
    }
}

// ─────────────────────────────────────────────
// 3️⃣  Multi-Level Protected Access
// ─────────────────────────────────────────────
//
// Protected members flow DOWN through the entire chain.
// Grandchild can access grandparent's protected members.

/*
 * LivingThing → Pet → DogPet
 * Protected members are visible at every level below.
 */
class LivingThing {
    protected String species;
    protected int age;

    LivingThing(String species, int age) {
        this.species = species;
        this.age = age;
    }

    protected String getLifeStage() {
        if (this.age < 1) return "baby";
        if (this.age < 5) return "young";
        if (this.age < 10) return "adult";
        return "senior";
    }
}

class Pet extends LivingThing {
    protected String ownerName;

    Pet(String species, int age, String ownerName) {
        super(species, age);
        this.ownerName = ownerName;
    }

    protected String getOwnerInfo() {
        return "Owned by " + this.ownerName;
    }
}

class DogPet extends Pet {
    String breed;  // package-private (public within this file)

    DogPet(int age, String ownerName, String breed) {
        super("Canis lupus familiaris", age, ownerName);
        this.breed = breed;
    }

    String profile() {
        // ✅ Can access grandparent's protected: species, age, getLifeStage()
        // ✅ Can access parent's protected: ownerName, getOwnerInfo()
        return "🐕 " + this.breed + "\n"
             + "   Species: " + this.species + "\n"
             + "   Age: " + this.age + " years (" + this.getLifeStage() + ")\n"
             + "   " + this.getOwnerInfo();
    }
}

// ─────────────────────────────────────────────
// 4️⃣  Real-World Example: UI Component Hierarchy
// ─────────────────────────────────────────────

/*
 * UIComponent — base class with protected layout fields.
 * Children (Button, TextInput) use getBounds() for rendering.
 */
class UIComponent {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible = true;

    UIComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Protected helper — children use this for rendering
    protected String getBounds() {
        return "(" + this.x + ", " + this.y + ") " + this.width + "×" + this.height;
    }

    void show() { this.visible = true; }
    void hide() { this.visible = false; }

    String render() {
        if (!this.visible) return "[hidden]";
        return "UIComponent at " + this.getBounds();
    }
}

/*
 * Button — a clickable UI component with a label.
 * Uses protected getBounds() and visible from UIComponent.
 */
class Button extends UIComponent {
    String label;
    private String onClickMessage;  // Simulates a callback with a message

    Button(int x, int y, int width, int height, String label, String onClickMessage) {
        super(x, y, width, height);
        this.label = label;
        this.onClickMessage = onClickMessage;
    }

    void click() {
        if (!this.visible) {
            System.out.println("  Cannot click hidden button!");
            return;
        }
        System.out.println("  Button \"" + this.label + "\" clicked!");
        System.out.println("  → " + this.onClickMessage);
    }

    @Override
    String render() {
        if (!this.visible) return "[hidden button]";
        // ✅ Uses protected getBounds() from parent
        return "🔘 Button \"" + this.label + "\" at " + this.getBounds();
    }
}

/*
 * TextInput — a text field UI component.
 * Has a placeholder and a value that can be set.
 */
class TextInput extends UIComponent {
    String placeholder;
    private String value = "";

    TextInput(int x, int y, int width, int height, String placeholder) {
        super(x, y, width, height);
        this.placeholder = placeholder;
    }

    String getValue() { return this.value; }
    void setValue(String v) { this.value = v; }

    @Override
    String render() {
        if (!this.visible) return "[hidden input]";
        String display = this.value.isEmpty() ? this.placeholder : this.value;
        return "📝 Input [" + display + "] at " + this.getBounds();
    }
}

// ═══════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (must match file name)
// ═══════════════════════════════════════════════════════════════
public class ProtectedMembers {
    public static void main(String[] args) {

        // ─────────────────────────────────────────
        // 1️⃣  protected vs private — The Difference
        // ─────────────────────────────────────────
        System.out.println("═══ 1. protected vs private ═══");

        SavingsAccount savings = new SavingsAccount("Naman", 10000, "SAV-001", 0.05);
        System.out.println("Balance: " + savings.getBalance());       // Balance: 10000.0
        System.out.println(savings.applyInterest());
        // Interest applied: $500.00. New balance: $10500.00

        CheckingAccount checking = new CheckingAccount("Naman", 500, "CHK-001", 200);
        System.out.println(checking.withdraw(600));   // ✅ Withdrew $600.00. Balance: -$100.00
        System.out.println(checking.withdraw(200));   // ❌ Exceeds overdraft limit!

        // From OUTSIDE — neither protected nor private is accessible:
        // savings.balance;            // ❌ protected — not accessible outside
        // savings.formatCurrency(50); // ❌ protected method — not accessible outside
        // savings.accountNumber;      // ❌ private — not accessible outside

        // ─────────────────────────────────────────
        // 2️⃣  Protected Constructor — Abstract-like Pattern
        // ─────────────────────────────────────────
        System.out.println("\n═══ 2. Protected Constructor ═══");

         BaseLogger logger = new BaseLogger("test");  // ❌ Would fail from outside package
        AppLogger appLog = new AppLogger("MyApp");
        DbLogger dbLog = new DbLogger("PostgreSQL");

        appLog.log("Application started");        // [HH:mm:ss] [INFO] APP:MyApp: ...
        appLog.warn("Memory usage high");          // [HH:mm:ss] [WARN] APP:MyApp: ...
        appLog.error("Connection timeout");        // [HH:mm:ss] [ERROR] APP:MyApp: ...
        dbLog.query("SELECT * FROM users");        // [HH:mm:ss] [QUERY] DB:PostgreSQL: ...

        // ─────────────────────────────────────────
        // 3️⃣  Multi-Level Protected Access
        // ─────────────────────────────────────────
        System.out.println("\n═══ 3. Multi-Level Protected Access ═══");

        DogPet myDog = new DogPet(3, "Naman", "Labrador");
        System.out.println(myDog.profile());
        // 🐕 Labrador
        //    Species: Canis lupus familiaris
        //    Age: 3 years (young)
        //    Owned by Naman

        // From outside — NONE of the protected members are accessible:
        // myDog.species;         // ❌ protected (from LivingThing)
        // myDog.ownerName;       // ❌ protected (from Pet)
        // myDog.getLifeStage();  // ❌ protected method
        // But public/package-private members work:
        System.out.println("Breed: " + myDog.breed);  // ✅ accessible

        // ─────────────────────────────────────────
        // 4️⃣  Real-World Example: UI Component Hierarchy
        // ─────────────────────────────────────────
        System.out.println("\n═══ 4. UI Component Hierarchy ═══");

        Button btn = new Button(10, 20, 120, 40, "Submit", "Form submitted!");
        TextInput input = new TextInput(10, 70, 200, 30, "Enter your name...");

        System.out.println(btn.render());
        // 🔘 Button "Submit" at (10, 20) 120×40
        System.out.println(input.render());
        // 📝 Input [Enter your name...] at (10, 70) 200×30

        input.setValue("Naman");
        System.out.println(input.render());
        // 📝 Input [Naman] at (10, 70) 200×30

        btn.click();
        //   Button "Submit" clicked!
        //   → Form submitted!

        btn.hide();
        System.out.println("After hide: " + btn.render());
        // After hide: [hidden button]

        // ─────────────────────────────────────────
        // 💡 KEY TAKEAWAYS
        // ─────────────────────────────────────────
        System.out.println("\n💡 KEY TAKEAWAYS");
        System.out.println("✅ `protected` = accessible in same class + ALL descendant classes.");
        System.out.println("✅ `private` = accessible ONLY in the same class (not even children).");
        System.out.println("✅ Use `protected` for internal data that children need to access.");
        System.out.println("✅ Protected flows down: grandchildren can access grandparent's protected members.");
        System.out.println("✅ Protected constructors prevent direct instantiation — only children can be created.");
        System.out.println("✅ From outside the class hierarchy, protected acts like private.");
        System.out.println("✅ Java also has package-private (no modifier) — accessible within the same package.");
    }
}
