package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer categoryId;

	@Column(length = 32, nullable = false)
	private String name;

	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> children = new ArrayList<>();

	public Category() {
	}
	
	public Category(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public Category(String name) {
		this.name = name;
	}

	public Category(String name, Category parent) {
		this(name);
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + ", parent=" + parent + "]";
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public static Category copyFull(Category category) {
		Category copyCategory = new Category();
		copyCategory.setCategoryId(category.getCategoryId());
		copyCategory.setName(category.getName());
		copyCategory.setParent(category.getParent());
		return copyCategory;
	}

	public static Category copyFull(Category category, String name) {
		Category copyCategory = Category.copyFull(category);
		copyCategory.setName(name);
		return copyCategory;
	}

}