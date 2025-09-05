package com.mybilling.billing.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BillItemRequest {
    @NotBlank(message = "Description is required")
    private String description;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int qty;

    @Min(value = 1, message = "Rate must be greater than 0")
    private double rate;
}
