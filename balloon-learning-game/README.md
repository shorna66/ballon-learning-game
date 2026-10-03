# Balloon Learning Game

A Java Swing educational game. Balloons carrying letters (a–z, A–Z) and digits (0–9) fall from the top of the screen. Type the matching key to pop a balloon and score a point. Wrong keys and missed balloons cost a life.

## Status
- Level 1 (letters and digits): complete
- Level 2 and Level 3: planned

## How to run
1. Open the project in IntelliJ IDEA (JDK 17 or newer).
2. Make sure `src` is marked as Sources Root.
3. Run `src/Main.java`.

## OOP design
- `Balloon` → `BalloonLetter` (inheritance and method overriding)
- `GameLevel` → `LevelOne` (shared game loop, level-specific logic)
- `InterfaceLevel` (common level contract)
