package com.mybilling.billing.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mybilling.billing.entity.Bill;
import com.mybilling.billing.entity.BillItem;
import com.mybilling.billing.entity.Customer;
import com.mybilling.billing.repository.BillRepository;
import com.mybilling.billing.repository.CustomerRepository;
import com.mybilling.billing.request.BillRequest;
import com.mybilling.billing.response.BillItemResponse;
import com.mybilling.billing.response.BillResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;

    // 🔹 Auto-generate bill number
    private String generateBillNumber() {
        Long count = billRepository.count();
        return "BILL-" + String.format("%04d", count + 1);  // BILL-0001, BILL-0002
    }

    // 🔹 Create new Bill
    public BillResponse createBill(BillRequest request) {
        // ✅ Customer reuse or create
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setName(request.getCustomerName());
                    c.setMobileNo(request.getPhone());
                    c.setEmail(request.getEmail());
                    return customerRepository.save(c);
                });

        Bill bill = new Bill();
        bill.setBillNumber(generateBillNumber());
        bill.setDate(LocalDate.now());
        bill.setCustomer(customer);

        // ✅ Map items & calculate amount
        List<BillItem> items = request.getItems().stream().map(i -> {
            BillItem item = new BillItem();
            item.setDescription(i.getDescription());
            item.setQty(i.getQty());
            item.setRate(i.getRate());
            item.setAmount(i.getQty() * i.getRate()); // auto calculate
            item.setBill(bill);
            return item;
        }).collect(Collectors.toList());

        bill.setItems(items);
        bill.setTotalAmount(items.stream().mapToDouble(BillItem::getAmount).sum());

        Bill saved = billRepository.save(bill);

        return toResponse(saved);
    }

    // 🔹 Get bill by ID
    public BillResponse getBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return toResponse(bill);
    }

    // 🔹 Update existing Bill
    public BillResponse updateBill(Long id, BillRequest request) {
        Bill existing = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        Customer customer = existing.getCustomer();
        customer.setName(request.getCustomerName());
        customer.setEmail(request.getEmail());
        customer.setMobileNo(request.getPhone());

        List<BillItem> items = request.getItems().stream().map(i -> {
            BillItem item = new BillItem();
            item.setDescription(i.getDescription());
            item.setQty(i.getQty());
            item.setRate(i.getRate());
            item.setAmount(i.getQty() * i.getRate());
            item.setBill(existing);
            return item;
        }).collect(Collectors.toList());

        // ✅ clear old items then add new
        existing.getItems().clear();
        existing.getItems().addAll(items);

        existing.setTotalAmount(items.stream().mapToDouble(BillItem::getAmount).sum());
        existing.setDate(LocalDate.now());

        Bill updated = billRepository.save(existing);
        return toResponse(updated);
    }

    // 🔹 Delete bill
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    // 🔹 Get all bills
    public List<BillResponse> getAllBills() {
        return billRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Helper: Entity → Response DTO
    private BillResponse toResponse(Bill bill) {
        return new BillResponse(
                bill.getId(),
                bill.getCustomer().getName(),
                bill.getCustomer().getMobileNo(),
                bill.getCustomer().getEmail(),
                bill.getBillNumber(),
                bill.getDate(),
                bill.getTotalAmount(),
                bill.getItems().stream()
                        .map(item -> new BillItemResponse(
                                item.getId(),
                                item.getDescription(),
                                item.getQty(),
                                item.getRate(),
                                item.getAmount()
                        )).collect(Collectors.toList())
        );
    }
}
