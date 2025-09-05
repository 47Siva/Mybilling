package com.mybilling.billing.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BillRequest {
    @NotBlank(message = "Customer name required")
    private String customerName;

    @NotBlank(message = "Phone number required")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private LocalDate date = LocalDate.now();

    @NotNull(message = "Bill items required")
    private List<@Valid BillItemRequest> items;
}
