package machine;

public class WaitingForMoneyState implements StateOfVendingMachine {

	@Override
	public void selectSnack(VendingMachine machine, String snackName) {
		System.out.println(machine.getSelectedSnack().getName() + " is already selected");
	}

	@Override
	public void insertMoney(VendingMachine machine, double amount) {
		if (amount <= 0) {
			System.err.println("Inserted money must be greater than 0");
			return;
		}

		machine.setBalance(machine.getBalance() + amount);

		if (machine.getBalance() < machine.getSelectedSnack().getPrice()) {
			printAmountDue(machine);
			return;
		}

		System.out.printf("Received $%.2f. Dispensing %s...%n",
				machine.getBalance(), machine.getSelectedSnack().getName());
		machine.setState(new DispensingSnackState());
		machine.dispenseSnack();
	}

	@Override
	public void dispenseSnack(VendingMachine machine) {
		printAmountDue(machine);
	}

	private void printAmountDue(VendingMachine machine) {
		double amountDue = machine.getSelectedSnack().getPrice() - machine.getBalance();
		System.out.printf("Received $%.2f. Please insert $%.2f more%n", machine.getBalance(), amountDue);
	}
}
