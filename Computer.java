public class Computer {
	private String brand, model;
	private double price;
	private long serialNumber;
	private static int serialNumberCounter = 0;
	// Default
	public Computer() {
		this.serialNumber = generateSerialNumber();
	}
	
	public Computer(String brand, String model, double price) {
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.serialNumber = generateSerialNumber();
	}
	
	public Computer(Computer c) {
		brand = c.brand;
		model = c.model;
		price = c.price;
		serialNumber = generateSerialNumber();
	}
	
	public static int findNumberOfComputersCreated() {
		return serialNumberCounter;
	}
	
	//Print computer details
	public static void displayComputer(Computer computer, int indexInInventory) {
		System.out.printf("\tComputer #: %d", indexInInventory);
		System.out.printf("\n\tBrand: %s", computer.getBrand());
		System.out.printf("\n\tModel: %s", computer.getModel());
		System.out.printf("\n\tSN: %d", computer.getSerialNumber());
		System.out.printf("\n\tPrice: %.2f\n\n", computer.getPrice());
	}
	
	private long generateSerialNumber() {
		return ++serialNumberCounter;
	}

	@Override
	public String toString() {
		return "[brand=" + brand + ", model=" + model + ", price=" + price + "]";
	}

	public String toString(boolean withSerial) {
		if(withSerial)
			return "[brand=" + brand + ", model=" + model + ", price=" + price + ", serialNumber=" + serialNumber + "]";
		else
			return toString();
	}
	
	public boolean equals(Computer c) {
		return toString().equals(c.toString());
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
}
