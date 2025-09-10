package com.thilshan.dream_shops.service.product;

import com.thilshan.dream_shops.dto.ProductDto;
import com.thilshan.dream_shops.model.Product;
import com.thilshan.dream_shops.request.AddProductRequest;
import com.thilshan.dream_shops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    void deleteProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}
