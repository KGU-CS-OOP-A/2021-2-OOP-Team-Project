package org.corodiak.manager;

import org.corodiak.type.Product;

public class ProductManager extends Manager<Product> {
	
	private static ProductManager instance = null;
	
	public static ProductManager getInstance() {
		if(instance == null)
			instance = new ProductManager();
		return instance;
	}
	
}
