package com.sbp_it_support.controller;

import com.sbp_it_support.dto.CreateTicketRequest;
import com.sbp_it_support.dto.UpdateTicketRequest;
import com.sbp_it_support.entity.Ticket;
import com.sbp_it_support.errorResponse.ErrorResponse;
import com.sbp_it_support.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody CreateTicketRequest request) {
        try {
            Ticket ticket = ticketService.createTicket(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody UpdateTicketRequest request) {
        Ticket ticket = ticketService.updateTicket(id, request);
        if (ticket == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Ticket.Status status
    ) {
        byte[] excelBytes = ticketService.exportToExcel(name, priority, department, status);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "tickets.xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
    }
}
