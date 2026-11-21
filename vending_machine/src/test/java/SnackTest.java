import snack.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnackTest {
	private final Snack snack = new Snack("Coke", 1.50, 5);

	@Test
	public void getName() {
		assertEquals("Coke", snack.getName());
	}

	@Test
	public void getPrice() {
		assertEquals(1.50, snack.getPrice());
	}

	@Test
	public void getQuantity() {
		assertEquals(5, snack.getQuantity());
	}

	@Test
	public void setNameBlank() {
		snack.setName("");

		assertEquals("Coke", snack.getName());
	}

	@Test
	public void setNameNull() {
		snack.setName(null);

		assertEquals("Coke", snack.getName());
	}

	@Test
	public void setPriceZero() {
		snack.setPrice(0);

		assertEquals(1.50, snack.getPrice());
	}

	@Test
	public void setPriceNegative() {
		snack.setPrice(-1.00);

		assertEquals(1.50, snack.getPrice());
	}

	@Test
	public void setQuantityNegative() {
		snack.setQuantity(-1);

		assertEquals(5, snack.getQuantity());
	}

	@Test
	public void setQuantityZero() {
		snack.setQuantity(0);

		assertEquals(0, snack.getQuantity());
	}

	@Test
	public void setName() {
		snack.setName("Pepsi");

		assertEquals("Pepsi", snack.getName());
	}

	@Test
	public void setPrice() {
		snack.setPrice(2.00);

		assertEquals(2.00, snack.getPrice());
	}

	@Test
	public void setQuantity() {
		snack.setQuantity(3);

		assertEquals(3, snack.getQuantity());
	}

	@Test
	public void toStringFormat() {
		assertEquals("Coke - $1.50 (5 left)", snack.toString());
	}
}
