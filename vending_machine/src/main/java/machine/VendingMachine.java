package machine;

import java.util.List;

import snack.*;

public class VendingMachine {

	public VendingMachine(List<Snack> snacks) {
	}

	public List<Snack> getSnacks() {
		return null;
	}

	public SnackDispenseHandler getSnackDispenser() {
		return null;
	}

	public StateOfVendingMachine getState() {
		return null;
	}

	public Snack getSelectedSnack() {
		return null;
	}

	public double getBalance() {
		return 0;
	}

	public void setState(StateOfVendingMachine state) {
	}

	public void setSelectedSnack(Snack selectedSnack) {
	}

	public void setBalance(double balance) {
	}

	// other methods
	public Snack findSnack(String snackName) {
		return null;
	}

	public double returnMoney() {
		return 0;
	}
}
