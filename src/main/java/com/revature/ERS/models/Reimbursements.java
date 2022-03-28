package com.revature.ERS.models;

import com.revature.ERS.util.Bytea;

import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursements {

    private String reim_id;
    private double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private Bytea receipt;
    private String payment_id;
    private String author_id;
    private String resolver_id;
    private ReimbursementStatuses status;
    private ReimbursementTypes type;

    public Reimbursements() {
        super();
    }

    public Reimbursements(String reim_id,
                          double amount,
                          Timestamp submitted,
                          Timestamp resolved,
                          String description,
                          Bytea receipt,
                          String payment_id,
                          String author_id,
                          String resolver_id,
                          ReimbursementStatuses status,
                          ReimbursementTypes type) {
        this.reim_id = reim_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.status = status;
        this.type = type;
    }

    public String getReim_id() {
        return reim_id;
    }

    public void setReim_id(String reim_id) {
        this.reim_id = reim_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bytea getReceipt() {
        return receipt;
    }

    public void setReceipt(Bytea receipt) {
        this.receipt = receipt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    public ReimbursementStatuses getStatus() {
        return status;
    }

    public void setStatus(ReimbursementStatuses status) {
        this.status = status;
    }

    public ReimbursementTypes getType() {
        return type;
    }

    public void setType(ReimbursementTypes type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursements that = (Reimbursements) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(reim_id, that.reim_id) && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved) && Objects.equals(description, that.description) && Objects.equals(receipt, that.receipt) && Objects.equals(payment_id, that.payment_id) && Objects.equals(author_id, that.author_id) && Objects.equals(resolver_id, that.resolver_id) && Objects.equals(status, that.status) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reim_id, amount, submitted, resolved, description, receipt, payment_id, author_id, resolver_id, status, type);
    }

    @Override
    public String toString() {
        return "Reimbursements{" +
                "reim_id='" + reim_id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", payment_id='" + payment_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
