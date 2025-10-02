package com.sbp_it_support.repository;

import com.sbp_it_support.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByName(String name);
    List<Ticket> findByPriority(String priority);
    List<Ticket> findByStatus(Ticket.Status status);
    List<Ticket> findByNameAndPriority(String name, String priority);
    List<Ticket> findByNameAndPriorityAndDepartmentAndStatus(String name, String priority, String department, Ticket.Status status);
    List<Ticket> findByNameAndPriorityAndDepartmentAndStatusAndDate(String name, String priority, String department, Ticket.Status status, LocalDate date);
}
