package com.sbp_it_support.dto;

import com.sbp_it_support.entity.Ticket;

public class UpdateTicketRequest {
    private String assignee;
    private Ticket.Status status;

    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }

    public Ticket.Status getStatus() { return status; }
    public void setStatus(Ticket.Status status) { this.status = status; }
}
