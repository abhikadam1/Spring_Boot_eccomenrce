package com.example.eccomerce.controller;

import com.example.eccomerce.model.Order;
import com.example.eccomerce.repository.OrderRepository;
import com.example.eccomerce.repository.ProductRepository;
import com.example.eccomerce.service.NotificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    // ⭐️ CONCEPT: Authorization (Method Level)
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/order/place")
    public String placeOrder(@RequestParam Long productId, Authentication authentication, RedirectAttributes ra) {

        String email = authentication.getName(); // Get email of logged-in user

        return productRepository.findById(productId).map(product -> {
            // 1. Save Order
            Order newOrder = new Order(null, email, product.getName(), product.getPriceUsd(), "PLACED");
            orderRepository.save(newOrder);

            // 2. ⭐️ CONCEPT: Multithreading/Async Call
            // This runs in a background thread, preventing the user from waiting 5 seconds.
            String orderDetails = String.format("%s (USD %.2f)", product.getName(), product.getPriceUsd());
            notificationService.sendOrderConfirmationEmail(email, orderDetails);

            ra.addFlashAttribute("message", "Order placed successfully! Confirmation email sent (check console for 5s delay).");
            return "redirect:/";

        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "Product not found.");
            return "redirect:/products";
        });
    }
}