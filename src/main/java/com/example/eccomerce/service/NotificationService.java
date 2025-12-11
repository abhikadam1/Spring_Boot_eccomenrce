package com.example.eccomerce.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ⭐️ CONCEPT: Multithreading/Async Processing
    @Async
    public void sendOrderConfirmationEmail(String toEmail, String orderDetails) {
        // Simulates the time taken to contact an external SMTP server
        System.out.println("--- Starting Async Email Thread for: " + toEmail + " ---");
        try {
            // Simulate network latency
            Thread.sleep(5000);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@secureshop.com");
            message.setTo(toEmail);
            message.setSubject("Your SecureShop Order Confirmation");
            message.setText("Thank you for your order!\nDetails: " + orderDetails);

            // mailSender.send(message); // Uncomment in a real environment
            System.out.println("SUCCESS: Async Email sent to " + toEmail);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("ERROR: Async Email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("ERROR sending email: " + e.getMessage());
        }
    }

    // ⭐️ CONCEPT: Third-Party SMS/API Simulation
    @Async
    public void sendSmsNotification(String phoneNumber, String message) {
        // In a real project, this would call a service like Twilio or Vonage
        System.out.println("SUCCESS: Async SMS sent to " + phoneNumber + " with message: " + message);
    }
}