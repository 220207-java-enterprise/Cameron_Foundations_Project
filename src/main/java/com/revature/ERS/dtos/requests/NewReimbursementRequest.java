package com.revature.ERS.dtos.requests;

import com.revature.ERS.util.Bytea;
import com.revature.ERS.models.*;

import java.sql.Timestamp;
import java.util.UUID;


public class NewReimbursementRequest {


    private double amount;
    private String description;
    private Bytea receipt;
    private String author_id;
    private String type;

    public NewReimbursementRequest() {
        super();
    }

    public NewReimbursementRequest(double amount, String description, Bytea receipt, String author_id, String type) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.author_id = author_id;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reimbursements extractReimbursement() {

        String reim_id = UUID.randomUUID().toString();//sets random UUID to new reimbursement id
        ReimbursementStatuses status = new ReimbursementStatuses("0", "PENDING"); // All new reimbursement requests set status_id to 0 and status to pending

        ReimbursementTypes type = new ReimbursementTypes(this.type, this.type);
        return new Reimbursements(
                reim_id,
                this.amount,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(0),
                this.description,
                this.receipt,
                null,
                this.author_id,
                null,
                status,
                type);

    }

    @Override
    public String toString() {
        return "NewReimbursementRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", author_id='" + author_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}