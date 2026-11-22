import machine.*;
import snack.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WaitingForMoneyStateTest {
	private final Snack dietCoke = new Snack("Diet Coke", 1.75, 2);
	private final Snack snickers = new Snack("Snickers", 1.25, 0);
	private final VendingMachine machine = new VendingMachine(List.of(dietCoke, snickers));
	private final WaitingForMoneyState waiting = new WaitingForMoneyState();

	@BeforeEach
	public void selectDietCoke() {
		machine.setSelectedSnack(dietCoke);
		machine.setState(waiting);
	}

	@Test
	public void selectSnack() {
		String output = getPrintedOutput(() -> waiting.selectSnack(machine, "Snickers"));

		assertTrue(output.contains("Diet Coke is already selected"));
		assertEquals(dietCoke, machine.getSelectedSnack());
		assertEquals(waiting, machine.getState());
	}

	@Test
	public void insertMoneyNotEnough() {
		String output = getPrintedOutput(() -> waiting.insertMoney(machine, 1.00));

		assertTrue(output.contains("Received $1.00. Please insert $0.75 more"));
		assertEquals(1.00, machine.getBalance());
		assertEquals(waiting, machine.getState());
	}

	@Test
	public void insertMoneyNegativeIsIgnored() {
		waiting.insertMoney(machine, -1.00);
		waiting.insertMoney(machine, 1.75);

		assertEquals(1, dietCoke.getQuantity());
	}

	@Test
	public void insertMoneyNaNIsIgnored() {
		waiting.insertMoney(machine, Double.NaN);

		assertEquals(2, dietCoke.getQuantity());
		assertEquals(0, machine.getBalance());
	}

	@Test
	public void insertMoneyInfinityIsIgnored() {
		waiting.insertMoney(machine, Double.POSITIVE_INFINITY);

		assertEquals(2, dietCoke.getQuantity());
		assertEquals(0, machine.getBalance());
	}

	@Test
	public void insertMoneyExact() {
		String output = getPrintedOutput(() -> waiting.insertMoney(machine, 1.75));

		assertTrue(output.contains("Received $1.75. Dispensing Diet Coke..."));
		assertEquals(1, dietCoke.getQuantity());
		assertEquals(0, machine.getBalance());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void insertMoneyAddsUp() {
		waiting.insertMoney(machine, 1.00);
		waiting.insertMoney(machine, 0.75);

		assertEquals(1, dietCoke.getQuantity());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void insertMoneyMoreReturnsChange() {
		String output = getPrintedOutput(() -> waiting.insertMoney(machine, 2.00));

		assertTrue(output.contains("Returned $0.25"));
		assertEquals(0, machine.getBalance());
	}

	@Test
	public void insertMoneyOutOfStockReturnsMoney() {
		machine.setSelectedSnack(snickers);

		String output = getPrintedOutput(() -> waiting.insertMoney(machine, 1.25));

		assertTrue(output.contains("Returned $1.25"));
		assertEquals(0, snickers.getQuantity());
		assertInstanceOf(IdleState.class, machine.getState());
	}

	@Test
	public void dispenseSnack() {
		String output = getPrintedOutput(() -> waiting.dispenseSnack(machine));

		assertTrue(output.contains("Received $0.00. Please insert $1.75 more"));
		assertEquals(2, dietCoke.getQuantity());
		assertEquals(waiting, machine.getState());
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
