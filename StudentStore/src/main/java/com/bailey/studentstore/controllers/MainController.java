package com.bailey.studentstore.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bailey.studentstore.models.Category;
import com.bailey.studentstore.models.LoginUser;
import com.bailey.studentstore.models.Product;
import com.bailey.studentstore.models.User;
import com.bailey.studentstore.services.CategoryService;
import com.bailey.studentstore.services.ProductService;
import com.bailey.studentstore.services.UserService;

@Controller
public class MainController {
	@Autowired
	private ProductService prodServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private CategoryService cateServ;
	
	//Dashboard
	@GetMapping("/")
	public String dashboard(HttpSession session, Model model) {
		List<Product> allProducts = prodServ.allProducts();
		Long user_id = (Long) session.getAttribute("user_id");
		if (user_id != null) {
    		User currUser = userServ.findOne(user_id);
    		model.addAttribute("currUser", currUser);
    	}
		model.addAttribute("productList", allProducts);
		return "dashboard.jsp";
	}
	//Search 
	@GetMapping("/search")
	public String search(@RequestParam("query") String query, HttpSession session, Model model) {
		List<Product> allProducts = prodServ.allProducts();
		Long user_id = (Long) session.getAttribute("user_id");
		if (user_id != null) {
    		User currUser = userServ.findOne(user_id);
    		model.addAttribute("currUser", currUser);
    	}
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < allProducts.size(); i++) {
			if (allProducts.get(i).getName().contains(query)) {
				productList.add(allProducts.get(i));
			} else if (allProducts.get(i).getCategories().get(0).getName().contains(query)) {
				productList.add(allProducts.get(i));
			}
		}
		model.addAttribute("query", query);
		model.addAttribute("productList", productList);
		return "dashboard.jsp";
	}
	
	//Search - PathVariable
	@GetMapping("/{query}")
	public String pathSearch(@PathVariable("query") String query, HttpSession session, Model model) {
		List<Product> allProducts = prodServ.allProducts();
		Long user_id = (Long) session.getAttribute("user_id");
		if (user_id != null) {
    		User currUser = userServ.findOne(user_id);
    		model.addAttribute("currUser", currUser);
    	}
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < allProducts.size(); i++) {
			if (allProducts.get(i).getName().contains(query)) {
				productList.add(allProducts.get(i));
			} else if (allProducts.get(i).getCategories().get(0).getName().contains(query)) {
				productList.add(allProducts.get(i));
			}
		}
		model.addAttribute("query", query);
		model.addAttribute("productList", productList);
		return "dashboard.jsp";
	}
	
	//Create Form 
	@GetMapping("/category/new")
	public String categoryForm(@ModelAttribute("newCategory") Category category, HttpSession session, Model model) {
		Long user_id = (Long) session.getAttribute("user_id");
		User user = userServ.findOne(user_id);
		model.addAttribute("currUser", user);
		return "create_category.jsp";
	}
	
	//Create
	@PostMapping("/category/create")
	public String newCategory(@Valid @ModelAttribute("newCategory") Category category, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			Long user_id = (Long) session.getAttribute("user_id");
			User user = userServ.findOne(user_id);
			model.addAttribute("currUser", user);
			return "create_category.jsp";
		} else {
			cateServ.createCategory(category);
			return "redirect:/";
		}
	}
	
	
	//Create Form 
	@GetMapping("/product/new")
	public String productForm(@ModelAttribute("newProduct") Product product, HttpSession session, Model model) {
		Long user_id = (Long) session.getAttribute("user_id");
		User user = userServ.findOne(user_id);
		model.addAttribute("currUser", user);
		List<Category> categories = cateServ.allCategories();
		model.addAttribute("categoryList", categories);
		return "create.jsp";
	}
	
	//Create
	@PostMapping("/product/create")
	public String newProduct(@Valid @ModelAttribute("newProduct") Product product, 
			BindingResult result, 
			@RequestParam(value="category") Long category_id, 
			HttpSession session, Model model) {
		if (result.hasErrors()) {
			Long user_id = (Long) session.getAttribute("user_id");
			User user = userServ.findOne(user_id);
			model.addAttribute("currUser", user);
			return "create.jsp";
		} else {
			prodServ.createProduct(product);
			Category addedCategory = cateServ.findCategory(category_id);
			addedCategory.getProducts().add(product);
			cateServ.updateCategory(addedCategory);
			return "redirect:/";
		}
	}
	
	//Read
	@GetMapping("/product/{product_id}")
	public String moreInfo(@PathVariable("product_id") Long product_id, HttpSession session, Model model) {
		Product oneProduct = prodServ.findProduct(product_id);
		model.addAttribute("product", oneProduct);
		Long user_id = (Long) session.getAttribute("user_id");
		if (user_id == null) {
			model.addAttribute("currUser", null);
		} else {
			User user = userServ.findOne(user_id);
			model.addAttribute("currUser", user);
		}
		return "moreinfo.jsp";
	}
	
	//Cart 
	@GetMapping("/product/user")
	public String cart(HttpSession session, Model model) {
		Long user_id = (Long) session.getAttribute("user_id");
		User user = userServ.findOne(user_id);
		model.addAttribute("currUser", user);
		List<Product> cart = user.getCart();
		model.addAttribute("cart", cart);
		return "cart.jsp";
	}
	
	//Add to Cart 
	@GetMapping("/cart/add/{product_id}")
	public String addToCart(@PathVariable("product_id") Long product_id, HttpSession session) {
		Long user_id = (Long) session.getAttribute("user_id");
		User user = userServ.findOne(user_id);
		Product product = prodServ.findProduct(product_id);
		product.setConsumer(user);
		prodServ.updateProduct(product);
		return "redirect:/";
	}
	
	//Remove From Cart
	@GetMapping("/cart/remove/{product_id}")
	public String removeFromCart(@PathVariable("product_id") Long product_id) {
		Product product = prodServ.findProduct(product_id);
		product.setConsumer(null);
		prodServ.updateProduct(product);
		return "redirect:/";
	}
	
	//Edit Form
	@GetMapping("/product/edit/{product_id}")
	public String editForm(@ModelAttribute("editedProduct") Product product, BindingResult result, @PathVariable("product_id") Long product_id, HttpSession session, Model model) {
		Product productData = prodServ.findProduct(product_id);
		model.addAttribute("product", productData);
		Long user_id = (Long) session.getAttribute("user_id");
		User user = userServ.findOne(user_id);
		model.addAttribute("currUser", user);
		List<Category> categories = cateServ.allCategories();
		model.addAttribute("categoryList", categories);
		return "edit.jsp";
	}
	
	//Edit
	@RequestMapping(value="/product/edit/{id}", method=RequestMethod.PUT)
	public String edit(@Valid @ModelAttribute("editedProduct") Product product, 
			BindingResult result, 
			@RequestParam(value="category") Long category_id, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", product);
			return "edit.jsp";
		} else {
			prodServ.updateProduct(product);
			Category addedCategory = cateServ.findCategory(category_id);
			addedCategory.getProducts().add(product);
			cateServ.updateCategory(addedCategory);
			return "redirect:/";
		}
	}
	
	//Delete
	@RequestMapping(value="/product/delete/{product_id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("product_id") Long product_id) {
		prodServ.deleteProduct(product_id);
		return "redirect:/";
	}
	
	//Register Form
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "register.jsp";
	}
	
	//Register
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {
	userServ.register(newUser, result);
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
	        return "register.jsp";
	    }
	    session.setAttribute("user_id", newUser.getId());
	    return "redirect:/";
	}
	
	//Login Form
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("newLogin", new LoginUser());
		model.addAttribute("newUser", new User());
	    return "login.jsp";
	}
	
	//Login
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model, HttpSession session) {
		User user = userServ.login(newLogin, result);
	    if(result.hasErrors()) {
	    	model.addAttribute("newUser", new User());
	        return "login.jsp";
	    }
	    session.setAttribute("user_id", user.getId());
	    return "redirect:/";
	}
	
	//Logout
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
