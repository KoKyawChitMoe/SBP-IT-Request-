package com.sbp_it_support.dto;

public class CreateTicketRequest {
    private String name;
    private String priority;
    private String department;
    private String description;
    private String providerName;
    private String solutionStatus;
    private String errorSolution;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getSolutionStatus() { return solutionStatus; }
    public void setSolutionStatus(String solutionStatus) { this.solutionStatus = solutionStatus; }

    public String getErrorSolution() { return errorSolution; }
    public void setErrorSolution(String errorSolution) { this.errorSolution = errorSolution; }
}
