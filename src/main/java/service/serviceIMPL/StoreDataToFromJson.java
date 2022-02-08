package service.serviceIMPL;

import com.google.gson.Gson;
import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreDataToFromJson {

    public static void store() throws SQLException {
        Gson gson = new Gson();

        List<Category> categories = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(
                "src/main/resources/category.json")))) {
            Category[] categories1 = gson.fromJson(bufferedReader, Category[].class);
            categories.addAll(Arrays.asList(categories1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.saveAll(categories);


        /**
         *  CATEGORY FROM PRODUCTS ADDED
         */
        // TODO: 1/9/2022
        List<Product> products = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(
                "src/main/resources/product.json")))) {
            Product[] products1 = gson.fromJson(bufferedReader, Product[].class);
            products.addAll(Arrays.asList(products1));

        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductService productService = new ProductServiceImpl();
        productService.saveAll(products);
    }
    }

