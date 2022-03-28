package com.revature.ERS.services;

import com.revature.ERS.daos.ReimbursementDAO;
import com.revature.ERS.dtos.requests.NewReimbursementRequest;
import com.revature.ERS.dtos.requests.UpdateReimbursementRequest;
import com.revature.ERS.dtos.responses.ResourceCreationResponse;
import com.revature.ERS.models.ReimbursementStatuses;
import com.revature.ERS.models.Reimbursements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO; // a dependency of ReimbursementService

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    //new reimbursement
    public ResourceCreationResponse saveNewReimbursement(NewReimbursementRequest newReimbursementRequest) throws IOException {

        Reimbursements newReimbursement = newReimbursementRequest.extractReimbursement();


        reimbursementDAO.save(newReimbursement);

        return new ResourceCreationResponse(newReimbursement.getReim_id());
    }

    //get all reimbursements
    public List<Reimbursements> getAllReimbursements() {
        return reimbursementDAO.getAll().stream().collect(Collectors.toList());
    }

    //Update Reimbursement Request
    public ResourceCreationResponse updateReimbursement(UpdateReimbursementRequest updateReimbursementRequest) throws IOException {

        Reimbursements reimbursementToBeUpdated = reimbursementDAO.getById(updateReimbursementRequest.getReim_id());
        ReimbursementStatuses reimbursementStatus = new ReimbursementStatuses(updateReimbursementRequest.getStatus(), updateReimbursementRequest.getStatus());

        reimbursementToBeUpdated.setStatus(reimbursementStatus);//set reimbursement to new status

        reimbursementDAO.update(reimbursementToBeUpdated);

        return new ResourceCreationResponse(reimbursementToBeUpdated.getReim_id());
    }
}
