import machine.*;
import snack.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
	private final Snack coke = new Snack("Coke", 1.50, 2);
	private final Snack pepsi = new Snack("Pepsi", 1.50, 2);
	private final Snack snickers = new Snack("Snickers", 1.25, 1);
	private final VendingMachine machine = new VendingMachine(List.of(coke, pepsi, snickers));

	@Test
	public void getSnacks() {
		assertEquals(List.of(coke, pepsi, snickers), machine.getSnacks());
	}

	@Test
	public void getSnackDispenserFollowsSnackOrder() {
		SnackDispenseHandler dispenser = machine.getSnackDispenser();

		assertEquals(coke, dispenser.getSnack());
		assertEquals(pepsi, dispenser.getNext().getSnack());
		assertEquals(snickers, dispenser.getNext().getNext().getSnack());
		assertNull(dispenser.getNext().getNext().getNext());
	}

	@Test
	public void getStateIdleAtStart() {
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void getSelectedSnackNullAtStart() {
		assertNull(machine.getSelectedSnack());
	}

	@Test
	public void getBalanceZeroAtStart() {
		assertEquals(0, machine.getBalance());
	}

	@Test
	public void setStateNull() {
		machine.setState(null);

		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void setBalanceNegative() {
		machine.setBalance(1.00);

		machine.setBalance(-1.00);

		assertEquals(1.00, machine.getBalance());
	}

	@Test
	public void setState() {
		IdleState idle = new IdleState();

		machine.setState(idle);

		assertEquals(idle, machine.getState());
	}

	@Test
	public void setSelectedSnack() {
		machine.setSelectedSnack(coke);

		assertEquals(coke, machine.getSelectedSnack());
	}

	@Test
	public void setBalance() {
		machine.setBalance(1.00);

		assertEquals(1.00, machine.getBalance());
	}

	@Test
	public void changingReturnedListDoesNotChangeSnacks() {
		machine.getSnacks().add(new Snack("Water", 1.00, 1));

		assertEquals(List.of(coke, pepsi, snickers), machine.getSnacks());
	}

	@Test
	public void findSnack() {
		assertEquals(pepsi, machine.findSnack("Pepsi"));
	}

	@Test
	public void findSnackUnknown() {
		assertNull(machine.findSnack("Water"));
	}

	@Test
	public void selectSnack() {
		machine.selectSnack("Coke");

		assertEquals(coke, machine.getSelectedSnack());
		assertInstanceOf(WaitingForMoneyState.class, machine.getState());
	}

	@Test
	public void insertMoney() {
		machine.selectSnack("Coke");

		machine.insertMoney(1.50);

		assertEquals(1, coke.getQuantity());
		assertEquals(0, machine.getBalance());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void snickersQuantityHitsZero() {
		machine.selectSnack("Snickers");
		machine.insertMoney(1.25);

		machine.selectSnack("Snickers");
		machine.insertMoney(1.25);

		assertEquals(0, snickers.getQuantity());
		assertEquals(0, machine.getBalance());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void dispenseSnack() {
		machine.setSelectedSnack(pepsi);
		machine.setBalance(1.50);
		machine.setState(new DispensingSnackState());

		machine.dispenseSnack();

		assertEquals(1, pepsi.getQuantity());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void returnMoney() {
		machine.setBalance(2.00);

		assertEquals(2.00, machine.returnMoney());
		assertEquals(0, machine.getBalance());
	}
}
