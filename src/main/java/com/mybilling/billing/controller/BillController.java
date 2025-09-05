package com.mybilling.billing.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybilling.billing.request.BillRequest;
import com.mybilling.billing.response.BillResponse;
import com.mybilling.billing.service.BillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Bill API", description = "APIs for creating, retrieving, updating, and deleting customer bills")
public class BillController {

    private final BillService billService;

    // 🔹 Create Bill
    @Operation(
            summary = "Create a new bill",
            description = "Creates a new bill for a customer with items and auto-calculated total",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bill details request body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BillRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bill created successfully",
                            content = @Content(schema = @Schema(implementation = BillResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PostMapping
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody BillRequest request) {
        return ResponseEntity.ok(billService.createBill(request));
    }

    // 🔹 Get Bill by ID
    @Operation(
            summary = "Get bill by ID",
            description = "Fetch a bill details using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bill fetched successfully",
                            content = @Content(schema = @Schema(implementation = BillResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Bill not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBill(
            @Parameter(description = "Bill ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(billService.getBill(id));
    }

    // 🔹 Update Bill
    @Operation(
            summary = "Update existing bill",
            description = "Update an existing bill and its items by bill ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bill updated successfully",
                            content = @Content(schema = @Schema(implementation = BillResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Bill not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BillResponse> updateBill(
            @Parameter(description = "Bill ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody BillRequest request) {
        return ResponseEntity.ok(billService.updateBill(id, request));
    }

    // 🔹 Delete Bill
    @Operation(
            summary = "Delete a bill",
            description = "Delete an existing bill by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bill deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Bill not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(
            @Parameter(description = "Bill ID", example = "1") @PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.ok("Bill deleted successfully");
    }

    // 🔹 Get All Bills
    @Operation(
            summary = "Get all bills",
            description = "Fetch all customer bills stored in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bills fetched successfully",
                            content = @Content(schema = @Schema(implementation = BillResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }
}
