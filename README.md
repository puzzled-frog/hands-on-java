# Hands-On Java

A progressive learning track for Java through building real applications.

## Overview

This repository contains a sequence of hands-on coding challenges designed to teach Java concepts by solving practical problems. Each challenge increases in difficulty and introduces new language features, design patterns, and best practices.

You'll find my solutions in each project's `src/` directory, though I strongly encourage you to attempt your own implementation first. The real learning comes from wrestling with the problem yourself, not from reading someone else's answer.

Keep in mind that my solutions are by no means the definitive "right" way—there are always multiple valid approaches to any problem. I welcome contributions through pull requests whenever you spot mistakes or see opportunities for improvement.

## Structure

- **`curriculum.json`** - Master curriculum file consumed by the learning website
- **Challenge directories** - Each contains:
  - `README.md` - Project overview and description
  - `docs/` - Requirements, design notes, and review content
  - `src/` - Solution code
  - Build configuration (Gradle)

## Philosophy

You don't learn to program by reading about programming—you learn by programming. The only way to develop intuition for code is to write it, watch it fail, understand why, and try again. This repository is built on that principle: practical challenges that make you build real applications where concepts emerge from necessity rather than abstraction.

Each challenge is designed to stretch you just beyond your comfort zone—difficult enough that solutions aren't immediately obvious, yet grounded enough in what you already know that progress remains within reach. Learning happens in this space between confusion and clarity.

## How to Approach Each Challenge

>“Software development is a learning process; working code is a side effect“ - Alberto Brandolin

I suggest you treat each challenge as a real software project. Here's a framework that will help you develop solid problem-solving habits:

### 1. Understand the Problem

Start by reading the challenge description and requirements thoroughly. What is the program supposed to do? What are the inputs and outputs? What constraints or edge cases should you consider? Don't rush this step. A clear understanding of the problem is half the solution.

### 2. Design Before Coding

Before you write a single line of code, sketch out your solution on paper (or a whiteboard, or a text file). This is where software design happens:

- **Identify the nouns** in your domain—these often become your classes or data structures
- **Identify the verbs**—these guide what methods you'll need
- **Map the flow**—how does data move through your program? What happens first, second, third?
- **Choose your data structures**—will you need a List? A Map? Just simple variables?

Draw a class diagram or a sequence diagram if that helps.

This planning phase might feel slow at first, but it saves hours of confused coding later.

### 3. Start Simple

Don't try to build everything at once. Start with the simplest version that does *something*:

- Get a basic "hello world" running
- Implement one feature at a time
- Hard-code values first, then add flexibility
- Skip error handling initially—just make the happy path work

A working simple program beats an elegant program that doesn't exist yet.

### 4. Run Early, Run Often

Compile and run your code constantly—every few lines if possible. Don't write 100 lines and *then* see if it works. Each small success confirms you're on the right track. Each small failure is easier to debug than a mountain of broken code.

### 5. Iterate and Refactor

Once it works, make it better:

- Add error handling
- Extract repeated code into methods
- Rename variables to be clearer
- Handle edge cases you skipped earlier
- Add input validation

Code is never "done" on the first pass. Experienced developers revisit and improve constantly.

### 6. Test Thoroughly

Consider writing tests as you develop—many experienced developers write a test first, then write just enough code to make it pass, then refactor. This test-driven approach helps you think about what your code should do before worrying about how it does it.

Think about what types of tests you need: **unit tests** verify individual methods or classes in isolation, while **integration tests** check that multiple components work together correctly. Use unit tests for business logic and algorithms; use integration tests when multiple parts need to coordinate (like database operations or API calls).

Whether you write tests first or after, make sure to try breaking your program. Use weird inputs. Test boundary conditions. What happens with empty data? Negative numbers? Very large values? Good software expects the unexpected.

### 7. Compare and Learn

I suggest that only after your work is done you look at my solution in the `src/` directory. Don't judge yours or mine in terms of "worse" or "better." The world needs less winners and more learners. Instead, ask:

- What approach did I take that differs from yours?
- Are there language features you hadn't considered?
- Could you explain why I made certain design choices?

Then read the design notes in `docs/design-notes.md` to see the reasoning behind key decisions I made in my solution.

If something I did doesn't make sense or you've discovered a better approach, please share it through a pull request. Teaching is one of the best ways to solidify learning, and your insights help everyone.

## Challenges

See `curriculum.json` for the complete list of challenges and their metadata.
