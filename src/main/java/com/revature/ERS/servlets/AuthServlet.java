package com.revature.ERS.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dtos.requests.LoginRequest;
import com.revature.ERS.dtos.responses.Principal;
import com.revature.ERS.services.TokenService;
import com.revature.ERS.services.Userservices;
import com.revature.ERS.util.exceptions.AuthenticationException;
import com.revature.ERS.util.exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private final TokenService tokenService;
    private final Userservices userService;
    private final ObjectMapper mapper;

    public AuthServlet(TokenService tokenService, Userservices userService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getServletContext().getInitParameter("programmaticParam"));
    }

    // Login endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {

            LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
            Principal principal = new Principal(userService.login(loginRequest));
            String payload = mapper.writeValueAsString(principal);



            String token = tokenService.generateToken(principal);
            resp.setHeader("Authorization", token);
            resp.setContentType("application/json");
            writer.write(payload);


        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); //Bad request on client end
        } catch (AuthenticationException e) {
            e.printStackTrace();
            resp.setStatus(401); // No user with authorized credentials
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); //internal server error
        }
    }

}