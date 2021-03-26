package com.nnamdi.transaction.model;

import javax.persistence.*;

@Entity
@Table(name = "processTypes")
public class ProcessTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processTypesId;

    @Enumerated(EnumType.STRING)
    private AccountProcessTypes name;

    public ProcessTypes() {

    }

    public ProcessTypes(AccountProcessTypes name) {
        this.name = name;
    }

    public Long getProcessTypesId() {
        return processTypesId;
    }

    public void setProcessTypesId(Long processTypesId) {
        this.processTypesId = processTypesId;
    }

    public AccountProcessTypes getName() {
        return name;
    }

    public void setName(AccountProcessTypes name) {
        this.name = name;
    }
}
