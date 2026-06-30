/*
 * ═══════════════════════════════════════════════════════════════════
 *  ✅ SOLUTIONS: Abstract Classes
 *  Module 04 — Partial Implementations & Shared Behavior
 * ═══════════════════════════════════════════════════════════════════
 *
 *  These are the complete, working solutions for all 3 exercises.
 *  Compare your implementations against these to check your work.
 *
 *  Key Concepts Demonstrated:
 *    • Defining abstract classes with both abstract and concrete methods
 *    • Using protected fields for subclass access
 *    • Overriding concrete methods in subclasses
 *    • Constructors in abstract classes (called via super())
 *    • Combining shared behavior with forced specialization
 * ═══════════════════════════════════════════════════════════════════
 */


// ─────────────────────────────────────────────────────────────────
// 1️⃣  SOLUTION 1: Bank Account System
// ─────────────────────────────────────────────────────────────────

/*
 *  BankAccount — the abstract base class for all account types.
 *  It provides shared logic for deposit/withdraw/balance, but
 *  forces each subclass to declare its type and interest rate.
 *  Note: abstract classes CAN have constructors — they're called
 *  via super() from subclass constructors.
 */
abstract class BankAccount {
    protected String owner;
    protected double balance;

    BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    abstract String getAccountType();
    abstract double getInterestRate();

    void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    boolean withdraw(double amount) {
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        return true;
    }

    double getBalance() {
        return balance;
    }

    String getOwner() {
        return owner;
    }
}

/*
 *  SavingsAccount — earns 2% interest but enforces a minimum
 *  balance of 100. The withdraw() override adds this constraint
 *  on top of the base logic. This is a perfect example of WHY
 *  abstract classes are useful: shared deposit/getBalance logic
 *  is inherited, only the specialized parts are overridden.
 */
class SavingsAccount extends BankAccount {

    SavingsAccount(String owner, double initialBalance) {
        super(owner, initialBalance);
    }

    @Override
    String getAccountType() {
        return "Savings";
    }

    @Override
    double getInterestRate() {
        return 0.02;
    }

    @Override
    boolean withdraw(double amount) {
        if (balance - amount < 100) {
            return false;
        }
        balance -= amount;
        return true;
    }
}

/*
 *  CurrentAccount — no interest, but allows overdraft up to -5000.
 *  This override completely replaces the base withdraw() logic
 *  with a more permissive policy.
 */
class CurrentAccount extends BankAccount {

    CurrentAccount(String owner, double initialBalance) {
        super(owner, initialBalance);
    }

    @Override
    String getAccountType() {
        return "Current";
    }

    @Override
    double getInterestRate() {
        return 0.0;
    }

    @Override
    boolean withdraw(double amount) {
        if (balance - amount < -5000) {
            return false;
        }
        balance -= amount;
        return true;
    }
}


// ─────────────────────────────────────────────────────────────────
// 2️⃣  SOLUTION 2: Media Player
// ─────────────────────────────────────────────────────────────────

/*
 *  MediaPlayer — abstract base with shared playback state logic.
 *  The play/pause/isPlaying/getCurrentTrack methods are identical
 *  for ALL player types — only the type name and supported formats
 *  differ. This is the ideal use case for abstract classes:
 *  "same behavior, different identity."
 */
abstract class MediaPlayer {
    protected String currentTrack;
    protected boolean playing = false;

    abstract String getPlayerType();
    abstract String getMediaFormat();

    void play(String track) {
        this.currentTrack = track;
        this.playing = true;
        System.out.println("Playing: " + track);
    }

    void pause() {
        this.playing = false;
    }

    boolean isPlaying() {
        return playing;
    }

    String getCurrentTrack() {
        return currentTrack;
    }
}

/*
 *  AudioPlayer — handles audio formats.
 *  Inherits ALL playback logic; only provides identity.
 */
class AudioPlayer extends MediaPlayer {

    @Override
    String getPlayerType() {
        return "Audio Player";
    }

    @Override
    String getMediaFormat() {
        return "MP3/WAV/FLAC";
    }
}

/*
 *  VideoPlayer — handles video formats.
 *  Same pattern as AudioPlayer — different identity, same behavior.
 */
class VideoPlayer extends MediaPlayer {

    @Override
    String getPlayerType() {
        return "Video Player";
    }

    @Override
    String getMediaFormat() {
        return "MP4/AVI/MKV";
    }
}


// ─────────────────────────────────────────────────────────────────
// 3️⃣  SOLUTION 3: Restaurant Order System
// ─────────────────────────────────────────────────────────────────

/*
 *  MenuItem — abstract base for all menu items.
 *  Provides shared pricing logic (base price + 10% tax) and name.
 *  Subclasses must define how they're prepared and their category.
 *  Pizza overrides getPrice() to add size-based multipliers — 
 *  showing that concrete methods CAN be overridden too.
 */
abstract class MenuItem {
    protected String name;
    protected double basePrice;

    MenuItem(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    abstract String prepare();
    abstract String getCategory();

    double getPrice() {
        return basePrice * 1.1;
    }

    String getName() {
        return name;
    }
}

/*
 *  Pizza — overrides getPrice() to apply a size multiplier
 *  BEFORE adding tax. This demonstrates that subclasses can
 *  enhance or completely replace inherited concrete methods.
 *
 *  Pricing: basePrice × sizeMultiplier × 1.1 (tax)
 *    small  → 1.0x    medium → 1.5x    large → 2.0x
 */
class Pizza extends MenuItem {
    private final String size;

    Pizza(String name, double basePrice, String size) {
        super(name, basePrice);
        this.size = size;
    }

    @Override
    double getPrice() {
        double multiplier;
        switch (size) {
            case "medium": multiplier = 1.5; break;
            case "large":  multiplier = 2.0; break;
            default:       multiplier = 1.0; break; // "small" or anything else
        }
        return basePrice * multiplier * 1.1;
    }

    @Override
    String prepare() {
        return "Preparing " + size + " pizza: " + name;
    }

    @Override
    String getCategory() {
        return "Main Course";
    }
}

/*
 *  Drink — uses the default getPrice() from MenuItem (no override).
 *  The prepare() method varies based on the hasIce flag.
 */
class Drink extends MenuItem {
    private final boolean hasIce;

    Drink(String name, double basePrice, boolean hasIce) {
        super(name, basePrice);
        this.hasIce = hasIce;
    }

    @Override
    String prepare() {
        return "Pouring " + name + (hasIce ? " with ice" : " without ice");
    }

    @Override
    String getCategory() {
        return "Beverage";
    }
}

/*
 *  Dessert — simplest subclass. Uses default getPrice(),
 *  provides its own prepare() and category.
 */
class Dessert extends MenuItem {

    Dessert(String name, double basePrice) {
        super(name, basePrice);
    }

    @Override
    String prepare() {
        return "Plating dessert: " + name;
    }

    @Override
    String getCategory() {
        return "Dessert";
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
