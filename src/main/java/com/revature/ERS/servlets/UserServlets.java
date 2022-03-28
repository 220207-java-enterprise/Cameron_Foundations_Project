package com.revature.ERS.servlets;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dtos.requests.NewUserRequest;
import com.revature.ERS.dtos.responses.AppUserResponse;
import com.revature.ERS.dtos.responses.Principal;
import com.revature.ERS.dtos.responses.ResourceCreationResponse;
import com.revature.ERS.services.TokenService;
import com.revature.ERS.models.Users;
import com.revature.ERS.services.Userservices;
import com.revature.ERS.util.exceptions.InvalidRequestException;
import com.revature.ERS.util.exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
public class UserServlets extends HttpServlet {

    private final TokenService tokenService;
    private final Userservices userService;
    private final ObjectMapper mapper;

    public UserServlets(TokenService tokenService, Userservices userService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mapper = mapper;
    }

    // Get all users (admin)


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] reqFrags = req.getRequestURI().split("/");
        if (reqFrags.length == 4 && reqFrags[3].equals("availability")) {
            checkAvailability(req, resp);
            return; // necessary, otherwise we end up doing more work than was requested
        }


        // get users (all, by id, by w/e)
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            resp.setStatus(401);
            return;
        }

        if (!requester.getRole_id().equals("1")) {
            resp.setStatus(403); // FORBIDDEN(client side)
            return;
        }
        List<AppUserResponse> users = userService.getAllUsers();
        String payload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
        resp.setStatus(200);//OK(success)

    }

    // Create new user (admin)

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();

        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
        if (!requester.getRole_id().equals("1")) {
            resp.setStatus(403); // FORBIDDEN(client side)
            return;
        }

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            Users newUser = userService.register(newUserRequest);
            resp.setStatus(201); // CREATED(success)
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUser_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); // BAD REQUEST error(client side)
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // CONFLICT error(client side)
        } catch (Exception e) {
            e.printStackTrace();//
            resp.setStatus(500);//INTERNAL SERVER ERROR
        }

    }

    //username and email availability check

    protected void checkAvailability(HttpServletRequest req, HttpServletResponse resp) {
        String usernameValue = req.getParameter("username");
        String emailValue = req.getParameter("email");
        if (usernameValue != null) {
            if (userService.isUsernameAvailable(usernameValue)) {
                resp.setStatus(204);
            } else {
                resp.setStatus(409);
            }
        } else if (emailValue != null) {
            if (userService.isEmailAvailable(emailValue)) {
                resp.setStatus(204);
            } else {
                resp.setStatus(409);
            }
        }
    }
}