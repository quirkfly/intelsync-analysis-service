package com.cogneworx.intelsync.analysis.model;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document("persons")
public class PersonEntity {

    @Id
    private String id;
    private String name;
    private String caseId;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
