# 🎓 OOP Learning with Java

A hands-on, progressive course to master **Object-Oriented Programming** using **Java**.

## 📋 Prerequisites

- **JDK 17+** installed ([Download](https://adoptium.net/))
- Basic knowledge of Java syntax (variables, loops, conditionals)

## 🚀 How to Run

Each lesson is a single `.java` file with its own `main()` method.

```bash
# Compile and run any lesson:
cd 01-classes-and-objects
javac BasicClass.java && java BasicClass

# Or with JDK 11+ single-file execution:
java BasicClass.java
```

## 📚 Module Roadmap

| #  | Module                       | Status |
|----|------------------------------|--------|
| 01 | Classes & Objects            | ✅     |
| 02 | Inheritance                  | ✅     |
| 03 | Interfaces                   | ✅     |
| 04 | Abstract Classes             | ✅     |
| 05 | Type System & Generics       | 🔜     |
| 06 | Encapsulation                | 🔜     |
| 07 | Polymorphism                 | 🔜     |
| 08 | Composition vs Inheritance   | 🔜     |
| 09 | Design Patterns              | 🔜     |
| 10 | SOLID Principles             | 🔜     |

## 📂 Structure

```
oop/
├── README.md
├── 01-classes-and-objects/
│   ├── BasicClass.java          ← Lesson 1: Classes & Objects
│   ├── AccessModifiers.java     ← Lesson 2: public, private, final
│   ├── GettersSetters.java      ← Lesson 3: Getters & Setters
│   ├── StaticMembers.java       ← Lesson 4: Static Members
│   ├── Exercise.java            ← Practice exercises (TODO stubs)
│   └── Solution.java            ← Reference solutions
│
└── 02-inheritance/
    ├── BasicInheritance.java    ← Lesson 1: extends & super
    ├── ProtectedMembers.java    ← Lesson 2: protected access
    ├── MethodOverriding.java    ← Lesson 3: @Override & super
    ├── Exercise.java            ← Practice exercises (TODO stubs)
│
├── 03-interfaces/
│   ├── BasicInterfaces.java    ← Lesson 1: Interface basics
│   ├── AdvancedInterfaces.java ← Lesson 2: Default, static, functional
│   ├── Exercise.java           ← Practice exercises (TODO stubs)
│   └── Solution.java           ← Reference solutions
│
├── 04-abstract-classes/
│   ├── BasicAbstractClasses.java    ← Lesson 1: Abstract class fundamentals
│   ├── AdvancedAbstractClasses.java ← Lesson 2: Hierarchies, factories, patterns
│   ├── Exercise.java                ← Practice exercises (write from scratch!)
│   └── Solution.java                ← Reference solutions
```

## 🧭 Learning Path

For each lesson file:

1. **Read** the block comment at the top — it explains the concept
2. **Read** the code examples — each section builds on the previous
3. **Run** the file — see the output and match it with inline comments
4. **Try** `Exercise.java` — fill in the `// TODO:` stubs
5. **Check** `Solution.java` — compare your solution

## 🔑 Java vs TypeScript Key Differences

| Concept             | TypeScript                          | Java                              |
|---------------------|-------------------------------------|-----------------------------------|
| Constructor shorthand | `constructor(public name: string)` | Must declare field + assign in constructor |
| Getters/Setters     | `get name()` / `set name(v)`       | `getName()` / `setName(v)` methods |
| Read-only           | `readonly`                          | `final`                           |
| Override keyword    | `override` (TS 4.3+)               | `@Override` annotation            |
| Private (runtime)   | `#field` (ES2022)                   | `private` (always runtime-enforced) |
| String templates    | `` `Hello ${name}` ``              | `"Hello " + name` or `String.format()` |
| Run command         | `bun run file.ts`                   | `javac File.java && java File`    |
