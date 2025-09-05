
package com.mybilling.billing.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
            title = "SmartBill - Billing API Documentation",
            version = "1.0.0",
            description = "SmartBill provides REST APIs for managing Customers, Bills, and Bill Items with PDF report generation.",
            contact = @Contact(
                name = "Support Team",
                email = "support@smartbill.com",
                url = "https://smartbill.com"
            ),
            license = @License(
                name = "Proprietary License",
                url = "https://smartbill.com/license"
            ),
            termsOfService = "https://smartbill.com/terms"
        )
//    servers = {
//        @Server(description = "Local Development", url = "http://localhost:8080"),
//        @Server(description = "Demo Server", url = "https://mybilling-demo.com")
//    }
)
//@SecurityScheme(
//		name = "bearAuth",
//		description = "jwt Auth Descrption",
//		scheme = "Bearer",
//		type = SecuritySchemeType.HTTP,
//		bearerFormat = "JWT",
//		in = SecuritySchemeIn.HEADER
//)
public class SwaggerConfiguration {
}
