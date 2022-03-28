package com.revature.ERS.dtos.requests;
import com.revature.ERS.models.Reimbursements;
import java.util.Objects;

public class UpdateReimbursementRequest {
    private String reim_id;
    private String resolver_id;
    private String status;

    public UpdateReimbursementRequest() {
        super();
    }

    public UpdateReimbursementRequest(String reim_id, String resolver_id, String status) {
        this.reim_id = reim_id;
        this.resolver_id = resolver_id;
        this.status = status;
    }

    public String getReim_id() {
        return reim_id;
    }

    public void setReim_id(String reim_id) {
        this.reim_id = reim_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateReimbursementRequest that = (UpdateReimbursementRequest) o;
        return Objects.equals(reim_id, that.reim_id) && Objects.equals(resolver_id, that.resolver_id) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reim_id, resolver_id, status);
    }

    @Override
    public String toString() {
        return "UpdateReimbursementRequest{" +
                "reim_id='" + reim_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}


