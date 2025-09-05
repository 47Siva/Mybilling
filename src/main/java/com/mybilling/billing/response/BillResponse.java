package com.mybilling.billing.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillResponse {
    private Long id;
	private String Customername;
	private String phone;
	private String email;
    private String billNumber;
    private LocalDate  date;
    private Double totalAmount;
    private List<BillItemResponse> items;
}
