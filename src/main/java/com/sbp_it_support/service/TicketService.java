package com.sbp_it_support.service;

import com.sbp_it_support.dto.CreateTicketRequest;
import com.sbp_it_support.dto.UpdateTicketRequest;
import com.sbp_it_support.entity.Ticket;
import com.sbp_it_support.repository.TicketRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(CreateTicketRequest request) {
        Ticket ticket = new Ticket(
                request.getName(),
                request.getPriority(),
                request.getDepartment(),
                request.getDescription(),
                request.getProviderName(),
                request.getSolutionStatus(),
                request.getErrorSolution()
        );

        try {
            ticket.setStatus(Ticket.Status.valueOf(request.getSolutionStatus()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid solutionStatus: " + request.getSolutionStatus());
        }
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Long id, UpdateTicketRequest request) {
        return ticketRepository.findById(id).map(ticket -> {
            if (request.getAssignee() != null) {
                ticket.setAssignee(request.getAssignee());
            }
            if (request.getStatus() != null) {
                ticket.setStatus(request.getStatus());
            }
            return ticketRepository.save(ticket);
        }).orElse(null);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public byte[] exportToExcel(String name, String priority, String department, Ticket.Status status, String startDateStr, String endDateStr) {
        LocalDateTime startDate = startDateStr != null ? LocalDate.parse(startDateStr).atStartOfDay() : null;
        LocalDateTime endDate = endDateStr != null ? LocalDate.parse(endDateStr).atTime(23, 59, 59) : null;
        List<Ticket> tickets = filterTickets(name, priority, department, status, startDate, endDate);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Tickets");

            // Header
            String[] columns = {"ID", "Date Time", "Name", "Priority", "Department", "Description",
                    "Provider Name", "Solution Status", "Error Solution", "Status", "Assignee"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }

            // Data
            int rowNum = 1;
            for (Ticket ticket : tickets) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(ticket.getId());
                row.createCell(1).setCellValue(ticket.getCreatedDate().toString());
                row.createCell(2).setCellValue(ticket.getName());
                row.createCell(3).setCellValue(ticket.getPriority());
                row.createCell(4).setCellValue(ticket.getDepartment());
                row.createCell(5).setCellValue(ticket.getDescription());
                row.createCell(6).setCellValue(ticket.getProviderName());
                row.createCell(7).setCellValue(ticket.getSolutionStatus());
                row.createCell(8).setCellValue(ticket.getErrorSolution());
                row.createCell(9).setCellValue(ticket.getStatus().name());
                row.createCell(10).setCellValue(ticket.getAssignee() != null ? ticket.getAssignee() : "");
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error exporting to Excel", e);
        }
    }

    private List<Ticket> filterTickets(String name, String priority, String department, Ticket.Status status, LocalDateTime startDate, LocalDateTime endDate) {
        // Use repository method with date range - add this method to repo
        if (startDate != null && endDate != null) {
            if (name != null && priority != null && department != null && status != null) {
                return ticketRepository.findByNameAndPriorityAndDepartmentAndStatusAndCreatedDateBetween(name, priority, department, status, startDate, endDate);
            } // Add more combinations if needed, or simplify to date only for now
            return ticketRepository.findByCreatedDateBetween(startDate, endDate);
        }
        // Existing filters without date...
        if (name != null && priority != null && department != null && status != null) {
            return ticketRepository.findByNameAndPriorityAndDepartmentAndStatus(name, priority, department, status);
        } else if (name != null && priority != null) {
            return ticketRepository.findByNameAndPriority(name, priority);
        } else if (priority != null) {
            return ticketRepository.findByPriority(priority);
        } else if (status != null) {
            return ticketRepository.findByStatus(status);
        } else {
            return ticketRepository.findAll();
        }
    }
}