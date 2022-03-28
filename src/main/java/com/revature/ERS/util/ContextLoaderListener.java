package com.revature.ERS.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.daos.ReimbursementDAO;
import com.revature.ERS.daos.UsersDAO;
import com.revature.ERS.services.ReimbursementService;
import com.revature.ERS.services.TokenService;
import com.revature.ERS.services.Userservices;
import com.revature.ERS.servlets.AuthServlet;
import com.revature.ERS.servlets.ReimbursementServlet;
import com.revature.ERS.servlets.UserServlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing ERS web application");

        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        UsersDAO usersDAO = new UsersDAO();
        Userservices userService = new Userservices(usersDAO);
        UserServlets usersServlet = new UserServlets(tokenService, userService, mapper);

        AuthServlet authServlet = new AuthServlet(tokenService, userService, mapper);

        ReimbursementDAO aReimbursementDAO = new ReimbursementDAO();
        ReimbursementService aReimbursementService = new ReimbursementService(aReimbursementDAO);
        ReimbursementServlet reimbursementServlet = new ReimbursementServlet(tokenService, aReimbursementService, mapper);

        // Programmatic Servlet Registration
        ServletContext context = sce.getServletContext();
        context.addServlet("UsersServlet", usersServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
        context.addServlet("ReimbursementServlet", reimbursementServlet).addMapping("/reimbursements/*");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down ERS web application");
    }

}
