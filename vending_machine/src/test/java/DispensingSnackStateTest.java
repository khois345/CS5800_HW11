import machine.*;
import snack.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DispensingSnackStateTest {
	private final Snack dietCoke = new Snack("Diet Coke", 1.75, 2);
	private final Snack snickers = new Snack("Snickers", 1.25, 0);
	private final VendingMachine machine = new VendingMachine(List.of(dietCoke, snickers));
	private final DispensingSnackState dispensing = new DispensingSnackState();

	@BeforeEach
	public void payForDietCoke() {
		machine.setSelectedSnack(dietCoke);
		machine.setBalance(1.75);
		machine.setState(dispensing);
	}

	@Test
	public void selectSnack() {
		String output = getPrintedOutput(() -> dispensing.selectSnack(machine, "Snickers"));

		assertTrue(output.contains("Please wait"));
		assertEquals(dietCoke, machine.getSelectedSnack());
		assertEquals(dispensing, machine.getState());
	}

	@Test
	public void insertMoney() {
		String output = getPrintedOutput(() -> dispensing.insertMoney(machine, 1.00));

		assertTrue(output.contains("Returned $1.00"));
		assertEquals(1.75, machine.getBalance());
		assertEquals(dispensing, machine.getState());
	}

	@Test
	public void dispenseSnack() {
		dispensing.dispenseSnack(machine);

		assertEquals(1, dietCoke.getQuantity());
		assertEquals(0, machine.getBalance());
		assertNull(machine.getSelectedSnack());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void dispenseSnackReturnsChange() {
		machine.setBalance(2.00);

		String output = getPrintedOutput(() -> dispensing.dispenseSnack(machine));

		assertTrue(output.contains("Returned $0.25"));
		assertEquals(0, machine.getBalance());
	}

	@Test
	public void dispenseSnackOutOfStockReturnsMoney() {
		machine.setSelectedSnack(snickers);
		machine.setBalance(1.25);

		String output = getPrintedOutput(() -> dispensing.dispenseSnack(machine));

		assertTrue(output.contains("Returned $1.25"));
		assertEquals(0, snickers.getQuantity());
		assertEquals(0, machine.getBalance());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	private String getPrintedOutput(Runnable action) {
		PrintStream original = System.out;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		try {
			action.run();
		} finally {
			System.setOut(original);
		}

		return output.toString();
	}
}
