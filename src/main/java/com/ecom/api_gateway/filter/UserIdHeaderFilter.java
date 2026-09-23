package com.ecom.api_gateway.filter;

import com.ecom.api_gateway.security.JwtService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class UserIdHeaderFilter {

    private final JwtService jwtService;

    public UserIdHeaderFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public HandlerFilterFunction<ServerResponse, ServerResponse> filter() {

        return (request, next) -> {

            String authHeader = request.headers()
                    .firstHeader("Authorization");

            // No JWT → continue request as it is
            if (authHeader == null ||
                    !authHeader.startsWith("Bearer ")) {

                return next.handle(request);
            }

            String token = authHeader.substring(7);

            Long userId = jwtService.extractUserId(token);

            // Create a modified ServerRequest
            ServerRequest modifiedRequest =
                    ServerRequest.from(request)
                            .header(
                                    "X-User-Id",
                                    String.valueOf(userId)
                            )
                            .build();

            return next.handle(modifiedRequest);
        };
    }
}