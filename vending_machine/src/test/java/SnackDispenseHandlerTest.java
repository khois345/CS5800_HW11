import machine.*;
import snack.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnackDispenseHandlerTest {
	private final Snack coke = new Snack("Coke", 1.50, 2);
	private final Snack pepsi = new Snack("Pepsi", 1.50, 2);
	private final Snack snickers = new Snack("Snickers", 1.25, 0);
	private final SnackDispenseHandler cokeHandler = new SnackDispenseHandler(coke);
	private final SnackDispenseHandler pepsiHandler = new SnackDispenseHandler(pepsi);
	private final SnackDispenseHandler snickersHandler = new SnackDispenseHandler(snickers);

	@Test
	public void getSnack() {
		assertEquals(coke, cokeHandler.getSnack());
	}

	@Test
	public void getNextNullAtStart() {
		assertNull(cokeHandler.getNext());
	}

	@Test
	public void setNext() {
		cokeHandler.setNext(pepsiHandler);

		assertEquals(pepsiHandler, cokeHandler.getNext());
	}

	@Test
	public void setNextReturnsNextForChaining() {
		cokeHandler.setNext(pepsiHandler).setNext(snickersHandler);

		assertEquals(snickersHandler, pepsiHandler.getNext());
	}

	@Test
	public void dispenseFirstInChain() {
		cokeHandler.setNext(pepsiHandler);

		assertTrue(cokeHandler.dispense("Coke"));
		assertEquals(1, coke.getQuantity());
		assertEquals(2, pepsi.getQuantity());
	}

	@Test
	public void dispensePassesToNext() {
		cokeHandler.setNext(pepsiHandler);

		assertTrue(cokeHandler.dispense("Pepsi"));
		assertEquals(2, coke.getQuantity());
		assertEquals(1, pepsi.getQuantity());
	}

	@Test
	public void dispenseUntilQuantityHitsZero() {
		assertTrue(cokeHandler.dispense("Coke"));
		assertTrue(cokeHandler.dispense("Coke"));
		assertFalse(cokeHandler.dispense("Coke"));

		assertEquals(0, coke.getQuantity());
	}

	@Test
	public void dispenseOutOfStockAtEndOfChain() {
		cokeHandler.setNext(pepsiHandler).setNext(snickersHandler);

		assertFalse(cokeHandler.dispense("Snickers"));
		assertEquals(0, snickers.getQuantity());
	}

	@Test
	public void dispenseUnknownSnack() {
		cokeHandler.setNext(pepsiHandler);

		assertFalse(cokeHandler.dispense("Water"));
		assertEquals(2, coke.getQuantity());
		assertEquals(2, pepsi.getQuantity());
	}

	@Test
	public void dispenseNull() {
		assertFalse(cokeHandler.dispense(null));
		assertEquals(2, coke.getQuantity());
	}
}
