package snack;

public class Snack {
	private String name;
	private double price;
	private int quantity;

	public Snack(String name, double price, int quantity) {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setName(String name) {
		if (name == null || name.isBlank()) {
			System.err.println("Snack name cannot be blank");
			return;
		}

		this.name = name;
	}

	public void setPrice(double price) {
		if (price <= 0) {
			System.err.println("Snack price must be greater than 0");
			return;
		}

		this.price = price;
	}

	public void setQuantity(int quantity) {
		if (quantity < 0) {
			System.err.println("Snack quantity cannot be negative");
			return;
		}

		this.quantity = quantity;
	}

	// other methods
	@Override
	public String toString() {
		return String.format("%s - $%.2f (%d left)", name, price, quantity);
	}
}
