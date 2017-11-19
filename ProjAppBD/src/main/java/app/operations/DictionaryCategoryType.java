package app.operations;


public enum DictionaryCategoryType {
	
	COUNTRY("Kraj"), PRODUCT_CATEGORY("Kategoria Produktu");
	
	public String name;
	
	DictionaryCategoryType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
