package com.mybilling.billing.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillItemResponse {
	private long id;
    private String description;
    private int qty;
    private double rate;
    private double amount;
}
