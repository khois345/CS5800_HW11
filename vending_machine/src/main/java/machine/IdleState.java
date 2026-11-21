package machine;

import snack.*;

public class IdleState implements StateOfVendingMachine {

	@Override
	public void selectSnack(VendingMachine machine, String snackName) {
		Snack snack = machine.findSnack(snackName);

		if (snack == null) {
			System.out.println(snackName + " is not sold in this machine");
			return;
		}

		machine.setSelectedSnack(snack);
		machine.setState(new WaitingForMoneyState());
		System.out.printf("Selected %s. Please insert $%.2f%n", snack.getName(), snack.getPrice());
	}

	@Override
	public void insertMoney(VendingMachine machine, double amount) {
		System.out.printf("Please select a snack first. Returned $%.2f%n", amount);
	}

	@Override
	public void dispenseSnack(VendingMachine machine) {
		System.out.println("Please select a snack first");
	}
}
