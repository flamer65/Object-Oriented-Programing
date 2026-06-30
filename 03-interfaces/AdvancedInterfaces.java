b/*
 * ══════════════════════════════════════════════════════════════════════
 *  📘 MODULE 03 — LESSON 2: ADVANCED INTERFACES
 * ══════════════════════════════════════════════════════════════════════
 *
 *  Java interfaces have evolved far beyond simple contracts.
 *  Starting with Java 8, interfaces gained powerful new features:
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  INTERFACE EVOLUTION                                         │
 *  ├──────────────┬───────────────────────────────────────────────┤
 *  │ Java 1.0     │ Abstract methods + constants only             │
 *  │ Java 8  (★)  │ + default methods, + static methods           │
 *  │ Java 8  (★)  │ + functional interfaces & lambda support      │
 *  │ Java 9       │ + private methods (helper methods)            │
 *  └──────────────┴───────────────────────────────────────────────┘
 *
 *  ANALOGY: Think of a modern interface like a TOOLKIT:
 *    • Required tools  → abstract methods (YOU must provide)
 *    • Built-in tools  → default methods (pre-built, override if needed)
 *    • Utility tools   → static methods (belong to the toolkit itself)
 *
 *  SECTIONS:
 *    1️⃣ Default Methods (Java 8+)
 *    2️⃣ Static Methods in Interfaces
 *    3️⃣ Functional Interfaces
 *    4️⃣ Interface Inheritance
 *    5️⃣ Real-World: Plugin System
 * ══════════════════════════════════════════════════════════════════════
 */

import java.util.ArrayList;
import java.util.List;


// ─────────────────────────────────────────────────────────────────────
// 1️⃣ DEFAULT METHODS (Java 8+)
// ─────────────────────────────────────────────────────────────────────
/*
 *  Before Java 8, adding a new method to an interface would BREAK
 *  every class that implements it (they'd all need to add the method).
 *
 *  Default methods solve this — they provide a method body directly
 *  in the interface using the `default` keyword.
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  interface Logger {                                          │
 *  │      void log(String msg);            ← MUST implement      │
 *  │      default void warn(String msg) {  ← CAN override        │
 *  │          log("WARNING: " + msg);      (has a body already)   │
 *  │      }                                                       │
 *  │  }                                                           │
 *  └──────────────────────────────────────────────────────────────┘
 *
 *  Implementing classes:
 *    • MUST implement abstract methods (log)
 *    • CAN override default methods (warn) — or use them as-is
 */

// Logger interface with one abstract method and one default method
interface Logger {
    // Abstract — every Logger MUST implement this
    void log(String msg);

    // Default — comes with a pre-built body; override if you want
    default void warn(String msg) {
        // A default method can contain ANY valid logic.
        // It does NOT have to call another interface method like log().
        // Here we use log() because it lets all Logger implementations
        // share the same warning behavior while still using their own log() implementation.
        log("⚠️  WARNING: " + msg);
    }

    // Another default method that builds on log()
    default void error(String msg) {
        log("❌ ERROR: " + msg);
    }
}

// ConsoleLogger: uses the default warn() as-is, only implements log()
class ConsoleLogger implements Logger {
    @Override
    public void log(String msg) {
        System.out.println("  [CONSOLE] " + msg);
    }
    // warn() and error() are inherited from Logger — no override needed!
}

// FileLogger: implements log() AND overrides warn() with custom behavior
class FileLogger implements Logger {
    @Override
    public void log(String msg) {
        System.out.println("  [FILE] Writing to log.txt: " + msg);
    }

    @Override
    public void warn(String msg) {
        // Custom override — adds timestamp-style prefix instead of default
        log("🔶 WARN [HIGH PRIORITY]: " + msg);
    }
    // error() still uses the default from Logger
}


// ─────────────────────────────────────────────────────────────────────
// 2️⃣ STATIC METHODS IN INTERFACES
// ─────────────────────────────────────────────────────────────────────
/*
 *  Interfaces can have static utility methods — called directly
 *  on the interface, NOT on implementing classes.
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  Validator.isNotEmpty("Hello")  → true   ✅                 │
 *  │  Validator.isNotEmpty("")       → false  ❌                 │
 *  │  Validator.isValidEmail(...)    → true/false                │
 *  │                                                              │
 *  │  These are like utility methods that belong to the           │
 *  │  interface itself — no instance needed.                      │
 *  └──────────────────────────────────────────────────────────────┘
 *
 *  NOTE: Static interface methods are NOT inherited by implementing
 *  classes. You must call them on the interface: Validator.isNotEmpty()
 */

interface Validator {
    // Static utility: check if a string is not null and not empty
    static boolean isNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // Static utility: basic email validation (contains @ and .)
    static boolean isValidEmail(String email) {
        if (email == null || email.length() < 5) return false;
        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");
        return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
    }

    // Static utility: check if a number is in range [min, max]
    static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
}


// ─────────────────────────────────────────────────────────────────────
// 3️⃣ FUNCTIONAL INTERFACES
// ─────────────────────────────────────────────────────────────────────
/*
 *  A FUNCTIONAL INTERFACE has EXACTLY ONE abstract method.
 *  It can be used with LAMBDA EXPRESSIONS (concise anonymous functions).
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  @FunctionalInterface                                        │
 *  │  interface Transformer {                                     │
 *  │      String transform(String input);  ← exactly ONE abstract │
 *  │  }                                                           │
 *  │                                                              │
 *  │  // Traditional: anonymous class                             │
 *  │  Transformer t = new Transformer() {                         │
 *  │      public String transform(String s) { return s.toUpper; } │
 *  │  };                                                          │
 *  │                                                              │
 *  │  // Lambda: concise! ✨                                      │
 *  │  Transformer t = s -> s.toUpperCase();                       │
 *  └──────────────────────────────────────────────────────────────┘
 *
 *  The @FunctionalInterface annotation is optional but recommended —
 *  it tells the compiler to enforce the "exactly one abstract method" rule.
 */

@FunctionalInterface
interface Transformer {
    String transform(String input);
    // Adding a second abstract method here would cause a compile error!
    // Default and static methods are allowed (they don't count).
}

// Helper class to apply a Transformer
class TextProcessor {
    // Accepts ANY Transformer — works with classes OR lambdas
    static String process(String text, Transformer transformer) {
        return transformer.transform(text);
    }
}


// ─────────────────────────────────────────────────────────────────────
// 4️⃣ INTERFACE INHERITANCE
// ─────────────────────────────────────────────────────────────────────
/*
 *  Interfaces can EXTEND other interfaces using `extends`.
 *  An interface can extend MULTIPLE interfaces (unlike classes).
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  interface Readable {                                        │
 *  │      String read();                                          │
 *  │  }                                                           │
 *  │                                                              │
 *  │  interface Writable {                                        │
 *  │      void write(String data);                                │
 *  │  }                                                           │
 *  │                                                              │
 *  │  interface ReadWritable extends Readable, Writable {         │
 *  │      // Inherits read() from Readable                        │
 *  │      // Inherits write() from Writable                       │
 *  │      // Can add its own methods too                          │
 *  │  }                                                           │
 *  └──────────────────────────────────────────────────────────────┘
 *
 *  A class implementing ReadWritable must provide bodies for
 *  ALL methods from BOTH parent interfaces.
 */

interface Readable {
    String read();
}

interface Writable {
    void write(String data);
}

// ReadWritable combines both parent interfaces
interface ReadWritable extends Readable, Writable {
    // Inherits: String read()       from Readable
    // Inherits: void write(String)  from Writable
    // Can also add new methods:
    default String getAccessMode() {
        return "READ_WRITE";
    }
}

// DataFile implements ReadWritable — must provide read() AND write()
class DataFile implements ReadWritable {
    private String name;
    private String content;

    DataFile(String name) {
        this.name = name;
        this.content = "";
    }

    String getName() {
        return name;
    }

    @Override
    public String read() {
        if (content.isEmpty()) {
            return "[" + name + " is empty]";
        }
        return content;
    }

    @Override
    public void write(String data) {
        this.content = data;
        System.out.println("  📝 Wrote to " + name + ": \"" + data + "\"");
    }

    // getAccessMode() is inherited from ReadWritable's default — no override needed
}


// ─────────────────────────────────────────────────────────────────────
// 5️⃣ REAL-WORLD: PLUGIN SYSTEM
// ─────────────────────────────────────────────────────────────────────
/*
 *  A Plugin System is a classic use case for interfaces.
 *  The system defines a Plugin contract, and anyone can create
 *  new plugins without modifying existing code.
 *
 *  ┌──────────────────────────────────────────────────────────────┐
 *  │  Plugin Interface (contract):                                │
 *  │    • getName()       → abstract (required)                   │
 *  │    • execute(input)  → abstract (required)                   │
 *  │    • getVersion()    → default (optional override)           │
 *  │                                                              │
 *  │  PluginManager:                                              │
 *  │    • register(Plugin p)  → add plugin to the list            │
 *  │    • runAll(input)       → execute every registered plugin   │
 *  │    • listPlugins()       → display all registered plugins    │
 *  └──────────────────────────────────────────────────────────────┘
 */

// The Plugin contract
interface Plugin {
    // Every plugin MUST have a name
    String getName();

    // Every plugin MUST define what it does
    String execute(String input);

    // Optional — plugins get version "1.0" by default
    default String getVersion() {
        return "1.0";
    }
}

// Plugin 1: Checks spelling (simplified simulation)
class SpellChecker implements Plugin {
    @Override
    public String getName() {
        return "SpellChecker";
    }

    @Override
    public String execute(String input) {
        // Simulated spell check — just report word count
        int words = input.trim().split("\\s+").length;
        return "✅ Checked " + words + " words — no errors found!";
    }

    @Override
    public String getVersion() {
        return "2.1";    // Override the default version
    }
}

// Plugin 2: Counts words in the input
class WordCounter implements Plugin {
    @Override
    public String getName() {
        return "WordCounter";
    }

    @Override
    public String execute(String input) {
        int words = input.trim().split("\\s+").length;
        int chars = input.length();
        return "📊 Words: " + words + " | Characters: " + chars;
    }
    // Uses default getVersion() → "1.0"
}

// Plugin 3: Converts input to uppercase
class UpperCaseFormatter implements Plugin {
    @Override
    public String getName() {
        return "UpperCaseFormatter";
    }

    @Override
    public String execute(String input) {
        return "🔠 " + input.toUpperCase();
    }
    // Uses default getVersion() → "1.0"
}

// The Plugin Manager — registers and runs plugins
class PluginManager {
    private final List<Plugin> plugins;

    PluginManager() {
        this.plugins = new ArrayList<>();
    }

    // Register a new plugin
    void register(Plugin plugin) {
        plugins.add(plugin);
        System.out.println("  📦 Registered: " + plugin.getName()
                + " (v" + plugin.getVersion() + ")");
    }

    // List all registered plugins
    void listPlugins() {
        System.out.println("  ┌─────────────────────────────────────┐");
        System.out.println("  │ Registered Plugins                  │");
        System.out.println("  ├─────────────────────┬───────────────┤");
        for (Plugin p : plugins) {
            System.out.printf("  │ %-19s │ v%-12s │%n",
                    p.getName(), p.getVersion());
        }
        System.out.println("  └─────────────────────┴───────────────┘");
    }

    // Run all plugins with the given input
    void runAll(String input) {
        System.out.println("  ▶️  Running all plugins on: \"" + input + "\"");
        System.out.println("  ─────────────────────────────────────────");
        for (Plugin p : plugins) {
            String result = p.execute(input);
            System.out.println("  [" + p.getName() + "] " + result);
        }
    }
}


// ══════════════════════════════════════════════════════════════════════
//  PUBLIC CLASS — Entry point (class name matches file name)
// ══════════════════════════════════════════════════════════════════════

public class AdvancedInterfaces {

    public static void main(String[] args) {

        System.out.println("══════════════════════════════════════════════════════");
        System.out.println(" 📘 MODULE 03 — LESSON 2: ADVANCED INTERFACES");
        System.out.println("══════════════════════════════════════════════════════");

        // ═══════════════════════════════════════════════════════════
        // 1️⃣ DEFAULT METHODS
        // ═══════════════════════════════════════════════════════════
        System.out.println("═══ 1. Default Methods (Java 8+) ═══");

        ConsoleLogger console = new ConsoleLogger();
        FileLogger file = new FileLogger();

        // ConsoleLogger — uses DEFAULT warn() and error() as-is
        System.out.println("  --- ConsoleLogger ---");
        console.log("Application started");         // [CONSOLE] Application started
        console.warn("Memory usage is high");        // [CONSOLE] ⚠️  WARNING: Memory usage is high
        console.error("Connection failed");          // [CONSOLE] ❌ ERROR: Connection failed

        // FileLogger — uses custom OVERRIDDEN warn(), default error()
        System.out.println("  --- FileLogger ---");
        file.log("User logged in");                  // [FILE] Writing to log.txt: User logged in
        file.warn("Disk space low");                 // [FILE] 🔶 WARN [HIGH PRIORITY]: Disk space low
        file.error("File not found");                // [FILE] ❌ ERROR: File not found

        System.out.println("  📝 ConsoleLogger uses default warn(); FileLogger overrides it!");

        // ═══════════════════════════════════════════════════════════
        // 2️⃣ STATIC METHODS IN INTERFACES
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 2. Static Methods in Interfaces ═══");

        // Call static methods directly on the interface — no instance needed!
        System.out.println("  --- isNotEmpty() ---");
        System.out.println("  \"Hello\"  → " + Validator.isNotEmpty("Hello"));   // true
        System.out.println("  \"\"       → " + Validator.isNotEmpty(""));        // false
        System.out.println("  \"  \"     → " + Validator.isNotEmpty("  "));      // false
        System.out.println("  null     → " + Validator.isNotEmpty(null));        // false

        System.out.println("  --- isValidEmail() ---");
        System.out.println("  \"a@b.com\"    → " + Validator.isValidEmail("a@b.com"));     // true
        System.out.println("  \"invalid\"    → " + Validator.isValidEmail("invalid"));     // false
        System.out.println("  \"no@dot\"     → " + Validator.isValidEmail("no@dot"));      // false
        System.out.println("  \"@.\"         → " + Validator.isValidEmail("@."));          // false

        System.out.println("  --- isInRange() ---");
        System.out.println("  50 in [1,100] → " + Validator.isInRange(50, 1, 100));   // true
        System.out.println("  150 in [1,100] → " + Validator.isInRange(150, 1, 100)); // false
        System.out.println("  0 in [0,10]   → " + Validator.isInRange(0, 0, 10));     // true

        // ═══════════════════════════════════════════════════════════
        // 3️⃣ FUNCTIONAL INTERFACES
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 3. Functional Interfaces ═══");

        String sample = "Hello, Interfaces!";
        System.out.println("  Original: \"" + sample + "\"");

        // Lambda: convert to uppercase
        Transformer upper = s -> s.toUpperCase();
        System.out.println("  Upper:    \"" + TextProcessor.process(sample, upper) + "\"");
        // HELLO, INTERFACES!

        // Lambda: convert to lowercase
        Transformer lower = s -> s.toLowerCase();
        System.out.println("  Lower:    \"" + TextProcessor.process(sample, lower) + "\"");
        // hello, interfaces!

        // Lambda: reverse the string
        Transformer reverse = s -> new StringBuilder(s).reverse().toString();
        System.out.println("  Reverse:  \"" + TextProcessor.process(sample, reverse) + "\"");
        // !secafretnI ,olleH

        // Lambda: replace vowels with *
        Transformer noVowels = s -> s.replaceAll("[aeiouAEIOU]", "*");
        System.out.println("  No vowels: \"" + TextProcessor.process(sample, noVowels) + "\"");
        // H*ll*, *nt*rf*c*s!

        // Inline lambda — no variable needed
        System.out.println("  Excited:  \""
                + TextProcessor.process(sample, s -> s + "!!!") + "\"");
        // Hello, Interfaces!!!!

        System.out.println("  📝 Each lambda is a different implementation of transform()!");

        // ═══════════════════════════════════════════════════════════
        // 4️⃣ INTERFACE INHERITANCE
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 4. Interface Inheritance ═══");

        DataFile dataFile = new DataFile("config.dat");

        // Reading before writing
        System.out.println("  📖 Read: " + dataFile.read());   // [config.dat is empty]

        // Writing data
        dataFile.write("host=localhost;port=8080");              // 📝 Wrote to config.dat

        // Reading after writing
        System.out.println("  📖 Read: " + dataFile.read());   // host=localhost;port=8080

        // Default method from ReadWritable interface
        System.out.println("  🔐 Access Mode: " + dataFile.getAccessMode());  // READ_WRITE

        // Type checks — DataFile satisfies ALL ancestor interfaces
        System.out.println("  --- Type hierarchy checks ---");
        System.out.println("  Is Readable?     " + (dataFile instanceof Readable));      // true
        System.out.println("  Is Writable?     " + (dataFile instanceof Writable));      // true
        System.out.println("  Is ReadWritable? " + (dataFile instanceof ReadWritable));  // true

        // Polymorphism: use different interface views
        Readable reader = dataFile;
        Writable writer = dataFile;
        System.out.println("  As Readable: " + reader.read());

        writer.write("updated=true");
        System.out.println("  After update: " + reader.read());   // updated=true

        // ═══════════════════════════════════════════════════════════
        // 5️⃣ REAL-WORLD: PLUGIN SYSTEM
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n═══ 5. Real-World: Plugin System ═══");

        PluginManager manager = new PluginManager();

        // Register plugins — the manager doesn't know or care what
        // specific class each plugin is. It only uses the Plugin interface.
        System.out.println("  --- Registering plugins ---");
        manager.register(new SpellChecker());         // SpellChecker v2.1
        manager.register(new WordCounter());          // WordCounter v1.0
        manager.register(new UpperCaseFormatter());   // UpperCaseFormatter v1.0

        // Display all registered plugins in a neat table
        System.out.println();
        manager.listPlugins();

        // Run all plugins on sample text
        System.out.println();
        manager.runAll("Java interfaces are powerful and flexible");

        // Adding a new plugin is EASY — no changes to PluginManager needed!
        System.out.println("\n  --- Adding a plugin at runtime ---");
        // We can even use a lambda since Plugin has default getVersion()!
        // (But Plugin has 2 abstract methods, so we can't use a lambda directly.
        //  Instead, we create a quick anonymous class.)
        manager.register(new Plugin() {
            @Override
            public String getName() {
                return "CharCounter";
            }

            @Override
            public String execute(String input) {
                return "🔢 Character count: " + input.length();
            }
        });

        System.out.println();
        manager.runAll("Extensibility is key");

        // ═══════════════════════════════════════════════════════════
        //  💡 KEY TAKEAWAYS
        // ═══════════════════════════════════════════════════════════
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println(" 💡 KEY TAKEAWAYS");
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println(" ✅ default methods let interfaces provide method bodies (Java 8+)");
        System.out.println(" ✅ Classes CAN override default methods or use them as-is");
        System.out.println(" ✅ static methods in interfaces are utility methods (called on the interface)");
        System.out.println(" ✅ A @FunctionalInterface has exactly ONE abstract method → works with lambdas");
        System.out.println(" ✅ Interfaces can extend other interfaces (even multiple!) using extends");
        System.out.println(" ✅ Plugin systems showcase interfaces: add new behavior without changing old code");
        System.out.println(" ✅ Interfaces enable the Open/Closed Principle: open for extension, closed for modification");
    }
}
