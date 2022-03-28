package com.revature.ERS.services;
import com.revature.ERS.dtos.requests.LoginRequest;
import com.revature.ERS.dtos.requests.NewUserRequest;
import com.revature.ERS.dtos.responses.AppUserResponse;
import com.revature.ERS.models.Users;
import com.revature.ERS.daos.UsersDAO;
import com.revature.ERS.util.exceptions.AuthenticationException;
import com.revature.ERS.util.exceptions.InvalidRequestException;
import com.revature.ERS.util.exceptions.ResourceConflictException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
public class Userservices {

        private UsersDAO userDAO; // a dependency of UserService

        // Constructor injection
        public Userservices(UsersDAO userDAO) {
            this.userDAO = userDAO;
        }


        //Get all users
        public List<AppUserResponse> getAllUsers() {

            return userDAO.getAll()
                    .stream()
                    .map(AppUserResponse::new)
                    .collect(Collectors.toList());
        }

        public Users register(NewUserRequest newUserRequest) throws IOException {

            Users newUser = newUserRequest.extractUser();

            if (!isUserValid(newUser)) {
                throw new InvalidRequestException("Invalid registration information");
            }

            boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
            boolean emailAvailable = isEmailAvailable(newUser.getEmail());

            if (!usernameAvailable || !emailAvailable) {
                String taken = "This registration information is already being used: ";
                if (!usernameAvailable) taken += "username ";
                if (!emailAvailable) taken += "email";
                throw new ResourceConflictException(taken);
            }

            return newUser;
        }

        public Users login(LoginRequest loginRequest) {

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (!isUsernameValid(username) || !isPasswordValid(password)) {
                throw new InvalidRequestException("Invalid credentials provided!");
            }


            Users authUser = userDAO.findUserByUsernameAndPassword(username, password);

            if (authUser == null) {
                throw new AuthenticationException();
            }

            return authUser;

        }

        private boolean isUserValid(Users user) {


            if (user.getGiven_name().trim().equals("") || user.getSurname().trim().equals("")) {
                return false;
            }


            if (!isUsernameValid(user.getUsername())) {
                return false;
            }


            if (!isPasswordValid(user.getPassword())) {
                return false;
            }

            return isEmailValid(user.getEmail());

        }

        public boolean isEmailValid(String email) {
            if (email == null) return false;
            return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
        }

        public boolean isUsernameValid(String username) {
            if (username == null) return false;
            return username.matches("^[a-zA-Z0-9]{8,25}");
        }

        public boolean isPasswordValid(String password) {
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        }

        public boolean isUsernameAvailable(String username) {
            return userDAO.findUserByUsername(username) == null;
        }

        public boolean isEmailAvailable(String email) {
            return userDAO.findUserByEmail(email) == null;
        }

    }

