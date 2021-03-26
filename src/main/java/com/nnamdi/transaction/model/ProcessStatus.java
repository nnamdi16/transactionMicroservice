package com.nnamdi.transaction.model;

import javax.persistence.*;

@Entity
@Table(name = "processStatus")
public class ProcessStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processStatusId;

    @Enumerated(EnumType.STRING)
    private AccountProcessStatus name;

    public ProcessStatus() {

    }

    public Long getProcessStatusId() {
        return processStatusId;
    }

    public void setProcessStatusId(Long processStatusId) {
        this.processStatusId = processStatusId;
    }

    public AccountProcessStatus getName() {
        return name;
    }

    public void setName(AccountProcessStatus name) {
        this.name = name;
    }
}
