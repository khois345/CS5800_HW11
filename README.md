# CS5800 HW11 – Vending Machine

A snack vending machine using the State and Chain of Responsibility patterns.

State diagram: state_diagram/Vending_Machine_State.png

You pick a snack, put money in, and get your snack plus change. If it's sold out, you get your money back. Doing things out of order (like paying before picking) just gets refused.

The snacks are checked in this order: Coke → Pepsi → Cheetos → Doritos → KitKat → Snickers.

## How to run

- Demo: run `initVendingMachine`
- Tests: run `CompleteUnitTest`
