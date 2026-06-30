/*
 * ═══════════════════════════════════════════════════════════════════
 *  🏋️ EXERCISES: Abstract Classes
 *  Module 04 — Partial Implementations & Shared Behavior
 * ═══════════════════════════════════════════════════════════════════
 *
 *  Abstract classes sit BETWEEN interfaces and concrete classes.
 *  They can provide SOME implementation while forcing subclasses
 *  to fill in the rest.
 *
 *  ┌─────────────────────────────────────────────────────────────┐
 *  │  Interface  vs  Abstract Class  vs  Concrete Class         │
 *  ├─────────────────────────────────────────────────────────────┤
 *  │  No state       Has state            Has state             │
 *  │  All abstract   Mix abstract +       All concrete          │
 *  │                 concrete methods                           │
 *  │  Multiple OK    Single inheritance   Single inheritance    │
 *  │  "Can do"       "Is a (partially)"   "Is a (fully)"       │
 *  └─────────────────────────────────────────────────────────────┘
 *
 *  Complete each exercise by creating the abstract classes and
 *  concrete subclasses from scratch. Write EVERYTHING yourself!
 *
 *  Run: javac Exercise.java && java Exercise
 *  See ✅ for pass, ❌ for fail. Goal: all ✅!
 * ═══════════════════════════════════════════════════════════════════
 */


// ─────────────────────────────────────────────────────────────────
// 1️⃣  EXERCISE 1: Bank Account System
// ─────────────────────────────────────────────────────────────────
/*
 *  Build a bank account hierarchy using an abstract class:
 *
 *  🏦 BankAccount (abstract class):
 *     • Fields: String owner (protected), double balance (protected)
 *     • Constructor: BankAccount(String owner, double initialBalance)
 *     • Abstract methods:
 *         - String getAccountType()  — returns the type of account
 *         - double getInterestRate() — returns the annual interest rate
 *     • Concrete methods:
 *         - void deposit(double amount)
 *             → adds amount to balance
 *             → throws IllegalArgumentException if amount <= 0
 *         - boolean withdraw(double amount)
 *             → subtracts amount from balance, returns true
 *             → returns false if insufficient funds (balance < amount)
 *         - double getBalance() → returns balance
 *         - String getOwner()  → returns owner
 *
 *  💰 SavingsAccount (extends BankAccount):
 *     • Constructor: SavingsAccount(String owner, double initialBalance)
 *     • getAccountType() → "Savings"
 *     • getInterestRate() → 0.02
 *     • Override withdraw: fails if balance would drop below 100
 *       (minimum balance requirement)
 *
 *  💳 CurrentAccount (extends BankAccount):
 *     • Constructor: CurrentAccount(String owner, double initialBalance)
 *     • getAccountType() → "Current"
 *     • getInterestRate() → 0.0
 *     • Override withdraw: allows overdraft up to -5000
 *       (succeeds if balance - amount >= -5000)
 */

// TODO: Create your BankAccount abstract class here

// TODO: Create your SavingsAccount class here

// TODO: Create your CurrentAccount class here


// ─────────────────────────────────────────────────────────────────
// 2️⃣  EXERCISE 2: Media Player
// ─────────────────────────────────────────────────────────────────
/*
 *  Build a media player hierarchy using an abstract class:
 *
 *  🎵 MediaPlayer (abstract class):
 *     • Fields: String currentTrack (protected),
 *               boolean playing (protected, default false)
 *     • Abstract methods:
 *         - String getPlayerType()  — returns the type of player
 *         - String getMediaFormat() — returns supported formats
 *     • Concrete methods:
 *         - void play(String track)
 *             → sets currentTrack to track
 *             → sets playing to true
 *             → prints "Playing: [track]"
 *         - void pause()
 *             → sets playing to false
 *         - boolean isPlaying()  → returns playing
 *         - String getCurrentTrack() → returns currentTrack
 *
 *  🎧 AudioPlayer (extends MediaPlayer):
 *     • getPlayerType() → "Audio Player"
 *     • getMediaFormat() → "MP3/WAV/FLAC"
 *
 *  🎬 VideoPlayer (extends MediaPlayer):
 *     • getPlayerType() → "Video Player"
 *     • getMediaFormat() → "MP4/AVI/MKV"
 */

// TODO: Create your MediaPlayer abstract class here

// TODO: Create your AudioPlayer class here

// TODO: Create your VideoPlayer class here


// ─────────────────────────────────────────────────────────────────
// 3️⃣  EXERCISE 3: Restaurant Order System
// ─────────────────────────────────────────────────────────────────
/*
 *  Build a restaurant menu system using an abstract class:
 *
 *  🍽️ MenuItem (abstract class):
 *     • Fields: String name (protected), double basePrice (protected)
 *     • Constructor: MenuItem(String name, double basePrice)
 *     • Abstract methods:
 *         - String prepare()     — returns preparation description
 *         - String getCategory() — returns food category
 *     • Concrete methods:
 *         - double getPrice() → returns basePrice * 1.1 (10% tax)
 *         - String getName()  → returns name
 *
 *  🍕 Pizza (extends MenuItem):
 *     • Constructor: Pizza(String name, double basePrice, String size)
 *         - size is "small", "medium", or "large"
 *     • Override getPrice():
 *         - small  → basePrice * 1.0 * 1.1  (no multiplier + tax)
 *         - medium → basePrice * 1.5 * 1.1  (1.5x + tax)
 *         - large  → basePrice * 2.0 * 1.1  (2.0x + tax)
 *     • prepare() → "Preparing [size] pizza: [name]"
 *       (e.g. "Preparing medium pizza: Margherita")
 *     • getCategory() → "Main Course"
 *
 *  🥤 Drink (extends MenuItem):
 *     • Constructor: Drink(String name, double basePrice, boolean hasIce)
 *     • prepare() → "Pouring [name] with ice"   (if hasIce is true)
 *                  → "Pouring [name] without ice" (if hasIce is false)
 *     • getCategory() → "Beverage"
 *
 *  🍰 Dessert (extends MenuItem):
 *     • Constructor: Dessert(String name, double basePrice)
 *     • prepare() → "Plating dessert: [name]"
 *     • getCategory() → "Dessert"
 */

// TODO: Create your MenuItem abstract class here

// TODO: Create your Pizza class here

// TODO: Create your Drink class here

// TODO: Create your Dessert class here


// ═══════════════════════════════════════════════════════════════════
//  🧪 TEST SUITE — Do NOT modify below this line
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

    public static void main(String[] args) {

        System.out.println("═══ 1. Bank Account System ═══");

        SavingsAccount sa = new SavingsAccount("Alice", 1000);
        CurrentAccount ca = new CurrentAccount("Bob", 500);

        test("SavingsAccount getAccountType() returns 'Savings'",
            "Savings".equals(sa.getAccountType()));
        test("SavingsAccount getInterestRate() returns 0.02",
            Math.abs(sa.getInterestRate() - 0.02) < 0.001);
        test("CurrentAccount getAccountType() returns 'Current'",
            "Current".equals(ca.getAccountType()));
        test("CurrentAccount getInterestRate() returns 0.0",
            Math.abs(ca.getInterestRate() - 0.0) < 0.001);
        sa.deposit(500);
        test("Deposit adds to balance (1000 + 500 = 1500)",
            Math.abs(sa.getBalance() - 1500) < 0.001);
        test("Savings withdraw fails if balance would drop below 100",
            !sa.withdraw(1450));
        test("Savings withdraw succeeds if min balance maintained",
            sa.withdraw(1000) && Math.abs(sa.getBalance() - 500) < 0.001);
        test("Current account allows overdraft up to -5000",
            ca.withdraw(5400) && Math.abs(ca.getBalance() - (-4900)) < 0.001);

        boolean threwException = false;
        try {
            sa.deposit(-100);
        } catch (IllegalArgumentException e) {
            threwException = true;
        }
        test("Deposit with negative amount throws IllegalArgumentException",
            threwException);


        System.out.println("\n═══ 2. Media Player ═══");

        AudioPlayer ap = new AudioPlayer();
        VideoPlayer vp = new VideoPlayer();

        test("AudioPlayer getPlayerType() returns 'Audio Player'",
            "Audio Player".equals(ap.getPlayerType()));
        test("AudioPlayer getMediaFormat() returns 'MP3/WAV/FLAC'",
            "MP3/WAV/FLAC".equals(ap.getMediaFormat()));
        test("VideoPlayer getPlayerType() returns 'Video Player'",
            "Video Player".equals(vp.getPlayerType()));
        test("VideoPlayer getMediaFormat() returns 'MP4/AVI/MKV'",
            "MP4/AVI/MKV".equals(vp.getMediaFormat()));
        test("Initial state is not playing",
            !ap.isPlaying() && ap.getCurrentTrack() == null);
        ap.play("Bohemian Rhapsody");
        test("play() sets track and playing state",
            ap.isPlaying() && "Bohemian Rhapsody".equals(ap.getCurrentTrack()));
        ap.pause();
        test("pause() sets playing to false",
            !ap.isPlaying());


        System.out.println("\n═══ 3. Restaurant Order System ═══");

        Pizza pizza = new Pizza("Margherita", 10.0, "medium");
        Drink drink = new Drink("Cola", 3.0, true);
        Drink drinkNoIce = new Drink("Water", 2.0, false);
        Dessert dessert = new Dessert("Tiramisu", 7.0);

        test("Pizza getCategory() returns 'Main Course'",
            "Main Course".equals(pizza.getCategory()));
        test("Drink getCategory() returns 'Beverage'",
            "Beverage".equals(drink.getCategory()));
        test("Dessert getCategory() returns 'Dessert'",
            "Dessert".equals(dessert.getCategory()));
        test("Pizza prepare() returns correct format",
            "Preparing medium pizza: Margherita".equals(pizza.prepare()));
        test("Pizza medium getPrice() = 10.0 * 1.5 * 1.1 = 16.5",
            Math.abs(pizza.getPrice() - 16.5) < 0.001);
        test("Drink with ice prepare() includes 'with ice'",
            "Pouring Cola with ice".equals(drink.prepare()));
        test("Drink without ice prepare() includes 'without ice'",
            "Pouring Water without ice".equals(drinkNoIce.prepare()));
        test("Dessert prepare() returns correct format",
            "Plating dessert: Tiramisu".equals(dessert.prepare()));
        test("Drink getPrice() applies 10% tax (3.0 * 1.1 = 3.3)",
            Math.abs(drink.getPrice() - 3.3) < 0.001);
        test("MenuItem getName() returns the name",
            "Margherita".equals(pizza.getName()));


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
