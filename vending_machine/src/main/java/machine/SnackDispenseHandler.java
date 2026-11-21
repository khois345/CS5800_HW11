package machine;

import snack.*;

public class SnackDispenseHandler {
	private Snack snack;
	private SnackDispenseHandler next;		// next handler in the chain

	public SnackDispenseHandler(Snack snack) {
		this.snack = snack;
	}

	public Snack getSnack() {
		return snack;
	}

	public SnackDispenseHandler getNext() {
		return next;
	}

	public SnackDispenseHandler setNext(SnackDispenseHandler next) {
		this.next = next;
		return next;
	}

	// other methods
	public boolean dispense(String snackName) {
		if (!snack.getName().equals(snackName)) {
			if (next == null) {
				System.out.println(snackName + " is not sold in this machine");
				return false;
			}

			return next.dispense(snackName);
		}

		if (snack.getQuantity() == 0) {
			System.out.println(snack.getName() + " is out of stock");
			return false;
		}

		snack.setQuantity(snack.getQuantity() - 1);
		System.out.println("Dispensed " + snack.getName() + " (" + snack.getQuantity() + " left)");
		return true;
	}
}
