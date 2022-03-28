package com.revature.ERS.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dtos.requests.NewReimbursementRequest;
import com.revature.ERS.dtos.requests.UpdateReimbursementRequest;
import com.revature.ERS.dtos.responses.Principal;
import com.revature.ERS.dtos.responses.ResourceCreationResponse;
import com.revature.ERS.services.ReimbursementService;
import com.revature.ERS.services.TokenService;
import com.revature.ERS.util.exceptions.AuthenticationException;
import com.revature.ERS.util.exceptions.InvalidRequestException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReimbursementServlet extends HttpServlet {

    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;

    public ReimbursementServlet(TokenService tokenService, ReimbursementService reimbursementService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
    }

    // Create reimbursement request (employee)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {

            Principal principal = tokenService.extractRequesterDetails(req.getHeader("Authorization"));//authorize requester
            if (!principal.getRole_id().equals("3")) {
                throw new InvalidRequestException("Request denied, only registered employees can submit reimbursement request.");
            }

            NewReimbursementRequest newReimbursementRequest = mapper.readValue(req.getInputStream(), NewReimbursementRequest.class);
            newReimbursementRequest.setAuthor_id(principal.getUser_id());
            // send newReimRequest to reimService
            ResourceCreationResponse creationResp = reimbursementService.saveNewReimbursement(newReimbursementRequest);
            // get result and write it to the response body as a json string (using the mapper)
            String payload = mapper.writeValueAsString(creationResp);
            resp.setContentType("application/json");
            writer.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400);//Bad request(client side error)
        } catch (AuthenticationException e) {
            resp.setStatus(401); // Unauthorized (no user found with provided credentials)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }

    }
    //Update Reim
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        try {
            Principal principal = tokenService.extractRequesterDetails(req.getHeader("Authorization"));//authorize requester
            if (!principal.getRole_id().equals("2")) {
                throw new InvalidRequestException("You are not an authorized Financial manager, request failed.");
            }
            UpdateReimbursementRequest updateReimbursementRequest = mapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class);
            ResourceCreationResponse updatedReimbursement = reimbursementService.updateReimbursement(updateReimbursementRequest);

            String payload = mapper.writeValueAsString(updatedReimbursement);
            resp.setContentType("application/JSON");
            writer.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400);//BAD request(client side error)
        } catch (AuthenticationException e) {
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}