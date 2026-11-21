package machine;

import java.util.ArrayList;
import java.util.List;

import snack.*;

public class VendingMachine {
	private List<Snack> snacks;
	private SnackDispenseHandler snackDispenser;
	private StateOfVendingMachine state;
	private Snack selectedSnack;
	private double balance;

	public VendingMachine(List<Snack> snacks) {
		this.snacks = new ArrayList<>(snacks);
		this.state = new IdleState();

		// build the chain backwards so it follows the order of the snack list
		for (int i = snacks.size() - 1; i >= 0; i--) {
			SnackDispenseHandler handler = new SnackDispenseHandler(snacks.get(i));
			handler.setNext(snackDispenser);
			snackDispenser = handler;
		}
	}

	public List<Snack> getSnacks() {
		return new ArrayList<>(snacks);
	}

	public SnackDispenseHandler getSnackDispenser() {
		return snackDispenser;
	}

	public StateOfVendingMachine getState() {
		return state;
	}

	public Snack getSelectedSnack() {
		return selectedSnack;
	}

	public double getBalance() {
		return balance;
	}

	public void setState(StateOfVendingMachine state) {
		if (state == null) {
			System.err.println("Vending machine state cannot be empty");
			return;
		}

		this.state = state;
	}

	public void setSelectedSnack(Snack selectedSnack) {
		this.selectedSnack = selectedSnack;
	}

	public void setBalance(double balance) {
		if (balance < 0) {
			System.err.println("Balance cannot be negative");
			return;
		}

		this.balance = balance;
	}

	// other methods
	public Snack findSnack(String snackName) {
		for (Snack snack : snacks) {
			if (snack.getName().equals(snackName)) {
				return snack;
			}
		}

		return null;
	}

	public void selectSnack(String snackName) {
	}

	public void insertMoney(double amount) {
	}

	public void dispenseSnack() {
		state.dispenseSnack(this);
	}

	public double returnMoney() {
		double returned = balance;
		balance = 0;

		if (returned > 0) {
			System.out.printf("Returned $%.2f%n", returned);
		}

		return returned;
	}
}
