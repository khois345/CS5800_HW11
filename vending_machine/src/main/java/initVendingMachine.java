import java.util.List;

import machine.*;
import snack.*;

public class initVendingMachine {

	public static void main(String[] args) {
		VendingMachine machine = new VendingMachine(List.of(
				new Snack("Coke", 1.50, 5),
				new Snack("Pepsi", 1.50, 5),
				new Snack("Cheetos", 1.25, 4),
				new Snack("Doritos", 1.25, 4),
				new Snack("KitKat", 1.00, 3),
				new Snack("Snickers", 1.25, 2)
		));

		System.out.println("[ Snacks in the machine ] " + "-".repeat(50));
		printSnacks(machine);
		System.out.println();

		System.out.println("[ Buy with exact money ] " + "-".repeat(50));
		machine.selectSnack("Coke");
		machine.insertMoney(1.50);
		System.out.println();

		System.out.println("[ Pay in parts ] " + "-".repeat(50));
		machine.selectSnack("KitKat");
		machine.insertMoney(0.50);
		machine.insertMoney(0.50);
		System.out.println();

		System.out.println("[ Pay more and get change ] " + "-".repeat(50));
		machine.selectSnack("Doritos");
		machine.insertMoney(2.00);
		System.out.println();

		System.out.println("[ Actions out of order ] " + "-".repeat(50));
		machine.insertMoney(1.00);
		machine.dispenseSnack();
		machine.selectSnack("Water");
		machine.selectSnack("Pepsi");
		machine.selectSnack("Cheetos");
		machine.dispenseSnack();
		machine.insertMoney(1.50);
		System.out.println();

		System.out.println("[ Snickers runs out ] " + "-".repeat(50));
		for (int i = 0; i < 3; i++) {
			machine.selectSnack("Snickers");
			machine.insertMoney(1.25);
		}
		System.out.println();

		System.out.println("[ Snacks left in the machine ] " + "-".repeat(50));
		printSnacks(machine);
	}

	private static void printSnacks(VendingMachine machine) {
		for (Snack snack : machine.getSnacks()) {
			System.out.println("  " + snack);
		}
	}
}
