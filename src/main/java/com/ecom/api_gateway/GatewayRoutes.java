package com.ecom.api_gateway;

import com.ecom.api_gateway.filter.UserIdHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class GatewayRoutes {

    private final UserIdHeaderFilter userIdHeaderFilter;

    public GatewayRoutes(UserIdHeaderFilter userIdHeaderFilter) {
        this.userIdHeaderFilter = userIdHeaderFilter;
    }


    // =========================
    // Product Service :8082
    // =========================
    @Bean
    public RouterFunction<ServerResponse> productRoute() {

        return route("product-service")
                .GET("/products/**", http())
                .POST("/products/**", http())
                .PUT("/products/**", http())
                .DELETE("/products/**", http())
                .filter(lb("PRODUCT-SERVICE"))
                .build();
    }


    // =========================
    // Order Service :8083
    // =========================
    @Bean
    public RouterFunction<ServerResponse> orderRoute() {

        return route("order-service")
                .GET("/orders/**", http())
                .POST("/orders/**", http())
                .PUT("/orders/**", http())
                .DELETE("/orders/**", http())

                // Add logged-in user's ID
                .filter(userIdHeaderFilter.filter())

                .filter(lb("ORDER-SERVICE"))
                .build();
    }


    // =========================
    // Cart Service :8086
    // =========================
    @Bean
    public RouterFunction<ServerResponse> cartRoute() {

        return route("cart-service")
                .GET("/cart/**", http())
                .POST("/cart/**", http())
                .PUT("/cart/**", http())
                .DELETE("/cart/**", http())

                // Add logged-in user's ID
                .filter(userIdHeaderFilter.filter())

                .filter(lb("CART-SERVICE"))
                .build();
    }


    // =========================
    // Wishlist Service :8087
    // =========================
    @Bean
    public RouterFunction<ServerResponse> wishlistRoute() {

        return route("wishlist-service")
                .GET("/wishlist/**", http())
                .POST("/wishlist/**", http())
                .PUT("/wishlist/**", http())
                .DELETE("/wishlist/**", http())

                // Add logged-in user's ID
                .filter(userIdHeaderFilter.filter())

                .filter(lb("WISHLIST-SERVICE"))
                .build();
    }
    // =========================
    // Address Service :8088
    // =========================
    @Bean
    public RouterFunction<ServerResponse> addressRoute() {

        return route("address-service")
                .GET("/addresses/**", http())
                .POST("/addresses/**", http())
                .PUT("/addresses/**", http())
                .PATCH("/addresses/**", http())
                .DELETE("/addresses/**", http())

                // Add logged-in user's ID
                .filter(userIdHeaderFilter.filter())

                .filter(lb("ADDRESS-SERVICE"))
                .build();
    }

    // =========================
    // Inventory Service :8084
    // =========================
    @Bean
    public RouterFunction<ServerResponse> inventoryRoute() {

        return route("inventory-service")
                .GET("/inventory/**", http())
                .POST("/inventory/**", http())
                .PUT("/inventory/**", http())
                .DELETE("/inventory/**", http())
                .filter(lb("INVENTORY-SERVICE"))
                .build();
    }


    // =========================
    // Payment Service :8085
    // =========================
    @Bean
    public RouterFunction<ServerResponse> paymentRoute() {

        return route("payment-service")
                .GET("/payments/**", http())
                .POST("/payments/**", http())
                .PUT("/payments/**", http())
                .DELETE("/payments/**", http())
                .filter(lb("PAYMENT-SERVICE"))
                .build();
    }

    // =========================
    // Auth Service :8081
    // =========================
    @Bean
    public RouterFunction<ServerResponse> authRoute() {

        return route("auth-service")
                .GET("/auth/**", http())
                .POST("/auth/**", http())
                .PUT("/auth/**", http())
                .DELETE("/auth/**", http())

                // User profile APIs
                .GET("/users/**", http())
                .PUT("/users/**", http())

                // Add logged-in user's ID
                .filter(userIdHeaderFilter.filter())

                .filter(lb("AUTH-SERVICE"))
                .build();
    }
   
}