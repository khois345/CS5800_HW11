package machine;

import snack.*;

public class DispensingSnackState implements StateOfVendingMachine {

	@Override
	public void selectSnack(VendingMachine machine, String snackName) {
		System.out.println("Please wait, dispensing " + machine.getSelectedSnack().getName());
	}

	@Override
	public void insertMoney(VendingMachine machine, double amount) {
		System.out.printf("Please wait, dispensing in progress. Returned $%.2f%n", amount);
	}

	@Override
	public void dispenseSnack(VendingMachine machine) {
		Snack snack = machine.getSelectedSnack();

		if (machine.getSnackDispenser().dispense(snack.getName())) {
			machine.setBalance(machine.getBalance() - snack.getPrice());
		}

		machine.returnMoney();
		machine.setSelectedSnack(null);
		machine.setState(new IdleState());
	}
}
