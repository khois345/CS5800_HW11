import machine.*;
import snack.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdleStateTest {
	private final Snack dietCoke = new Snack("Diet Coke", 1.75, 2);
	private final VendingMachine machine = new VendingMachine(List.of(dietCoke));
	private final IdleState idle = new IdleState();

	@BeforeEach
	public void startIdle() {
		machine.setState(idle);
	}

	@Test
	public void selectSnack() {
		String output = getPrintedOutput(() -> idle.selectSnack(machine, "Diet Coke"));

		assertTrue(output.contains("Selected Diet Coke. Please insert $1.75"));
		assertEquals(dietCoke, machine.getSelectedSnack());
		assertInstanceOf(WaitingForMoneyState.class, machine.getState());
	}

	@Test
	public void selectSnackUnknown() {
		String output = getPrintedOutput(() -> idle.selectSnack(machine, "Water"));

		assertTrue(output.contains("Water is not sold in this machine"));
		assertNull(machine.getSelectedSnack());
		assertEquals(idle, machine.getState());
	}

	@Test
	public void insertMoney() {
		String output = getPrintedOutput(() -> idle.insertMoney(machine, 1.50));

		assertTrue(output.contains("Please select a snack first. Returned $1.50"));
		assertEquals(0, machine.getBalance());
		assertEquals(idle, machine.getState());
	}

	@Test
	public void dispenseSnack() {
		String output = getPrintedOutput(() -> idle.dispenseSnack(machine));

		assertTrue(output.contains("Please select a snack first"));
		assertEquals(2, dietCoke.getQuantity());
		assertEquals(idle, machine.getState());
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
