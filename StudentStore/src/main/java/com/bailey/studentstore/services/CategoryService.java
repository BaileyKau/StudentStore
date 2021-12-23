package com.bailey.studentstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bailey.studentstore.models.Category;
import com.bailey.studentstore.repositories.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository Repo;
	public CategoryService(CategoryRepository Repo) {
		this.Repo = Repo;
	}
	public List<Category> allCategories() {
		return Repo.findAll();
	}
	public Category createCategory(Category category) {
		return Repo.save(category);
	}
	public Category findCategory(Long id) {
		Optional<Category> optionalproduct = Repo.findById(id);
		if (optionalproduct.isPresent()) {
			return optionalproduct.get();
		} else {
			return null;
		}
	}
	public Category updateCategory(Category category) {
		return Repo.save(category);
	}
	public void deleteCategory(Long id) {
		Repo.deleteById(id);
	}
}
