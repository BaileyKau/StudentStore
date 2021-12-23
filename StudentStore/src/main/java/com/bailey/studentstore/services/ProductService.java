package com.bailey.studentstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bailey.studentstore.models.Product;
import com.bailey.studentstore.repositories.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository Repo;
	public ProductService(ProductRepository Repo) {
		this.Repo = Repo;
	}
	public List<Product> allProducts() {
		return Repo.findAll();
	}
	public Product createProduct(Product product) {
		return Repo.save(product);
	}
	public Product findProduct(Long id) {
		Optional<Product> optionalproduct = Repo.findById(id);
		if (optionalproduct.isPresent()) {
			return optionalproduct.get();
		} else {
			return null;
		}
	}
	public Product updateProduct(Product product) {
		return Repo.save(product);
	}
	public void deleteProduct(Long id) {
		Repo.deleteById(id);
	}
}