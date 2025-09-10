package com.thilshan.dream_shops.controller;

import com.thilshan.dream_shops.dto.ProductDto;
import com.thilshan.dream_shops.model.Product;
import com.thilshan.dream_shops.request.AddProductRequest;
import com.thilshan.dream_shops.request.ProductUpdateRequest;
import com.thilshan.dream_shops.response.ApiResponse;
import com.thilshan.dream_shops.service.exception.ProductNotFoundException;
import com.thilshan.dream_shops.service.exception.ResourceNotFoundException;
import com.thilshan.dream_shops.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController  {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", convertedProducts));
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve products", e.getMessage()));
        }
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductsById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to add product",null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
        try {
            Product theProduct = productService.updateProduct(request, productId);
            ProductDto productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Failed to update product", null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to delete product", null));
        }

    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found with name", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
//
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
//
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), e.getMessage()));
        }
    }
//
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), e.getMessage()));
        }
    }
//
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), e.getMessage()));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String category){
        try {
            var productCount = productService.countProductsByBrandAndName(brand, category);

            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), e.getMessage()));
        }
    }

}
