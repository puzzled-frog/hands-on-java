# Text-Based RPG Battle System

## Description

The Text-Based RPG Battle System is a turn-based combat game featuring different character classes with unique abilities. Choose between Warrior, Mage, or Archer, each with distinct stats and special powers. Manage your inventory, use items strategically, and save your progress to continue later.

This challenge introduces you to inheritance and polymorphism—the cornerstones of object-oriented design. You'll create a class hierarchy where different character types share common behavior but implement their own unique abilities. The system demonstrates how interfaces define contracts, abstract classes provide shared functionality, and concrete classes bring specific implementations.

You'll also learn about game state management: tracking multiple entities, handling turn-based logic, and persisting complex object graphs to files. This is your first experience designing a system with multiple interacting components, teaching you how to structure larger applications.

## Features

- **Multiple character classes**: Warrior, Mage, and Archer with unique abilities
- **Turn-based combat**: Strategic decision-making each turn
- **Inventory system**: Health potions, mana potions, and stat-boosting items
- **Special abilities**: Class-specific powers with cooldowns or costs
- **Enemy AI**: Basic decision-making for opponents
- **Save/load functionality**: Resume your progress anytime
- **Combat feedback**: Clear action results and stat updates

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/rpg-battle-system.jar Main
```

## How to Test

Run all tests:

```bash
./gradlew test
```

View test report:

```bash
open build/reports/tests/test/index.html
```

## Usage Example

```
Choose your class:
1. Warrior (High HP, Strong attacks)
2. Mage (Magic attacks, Healing spells)
3. Archer (Quick attacks, Evasion)

Your choice: 2
You are now a Mage!

A wild Goblin appears!

Your turn:
1. Basic Attack
2. Fireball (Costs 20 mana)
3. Use Item
4. Flee

Your choice: 2
You cast Fireball! The Goblin takes 45 damage!
Goblin HP: 15/60

Enemy turn:
The Goblin attacks! You take 8 damage.
Your HP: 72/80
```

