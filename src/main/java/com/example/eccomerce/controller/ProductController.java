package com.example.eccomerce.controller;

import com.example.eccomerce.model.Product;
import com.example.eccomerce.repository.ProductRepository;
import com.example.eccomerce.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final CurrencyService currencyService;

    public ProductController(ProductRepository productRepository, CurrencyService currencyService) {
        this.productRepository = productRepository;
        this.currencyService = currencyService;
    }
//    @GetMapping("/")
//    public String index() {
//        return "login";
//    }
    @GetMapping("/products")
    public String listProducts(@RequestParam(required = false, defaultValue = "USD") String currency, Model model) {
        System.out.println("INFO: Getting all products for currency: " + currency);
        List<Product> products = productRepository.findAll();
        double rate = currencyService.getRate(currency);

        // ⭐️ CONCEPT: Java Streams
        List<ProductDto> productDtos = products.stream()
                // ⭐️ CONCEPT: Stream Map (Transformation)
                .map(product -> {
                    double convertedPrice = product.getPriceUsd() * rate;
                    return new ProductDto(
                            product.getId(),
                            product.getName(),
                            String.format("%.2f %s", convertedPrice, currency) // Format output string
                    );
                })
                // ⭐️ CONCEPT: Stream Collect (Collections)
                .collect(Collectors.toList());

        model.addAttribute("products", productDtos);
        model.addAttribute("currency", currency);
        return "product_list";
    }

    // Simple DTO for displaying converted data
    private record ProductDto(Long id, String name, String price){}
}
