package by.jwd.restaurant.service.impl;

import by.jwd.restaurant.entity.RegistrationInfo;
import by.jwd.restaurant.dao.DAOProvider;
import by.jwd.restaurant.dao.UserDAO;
import by.jwd.restaurant.entity.User;
import by.jwd.restaurant.dao.exception.DAOException;
import by.jwd.restaurant.service.exception.ServiceException;
import by.jwd.restaurant.service.UserService;
import by.jwd.restaurant.service.validation.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final int USER_ROLE_ID = 2;

    @Override
    public User authorization(String login, String password) throws ServiceException {

        if(!validateAuthorizationInfo(login, password)){
            throw new ServiceException("wrong authorization");
        }

        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        User user;

        try {
            user = userDAO.authorization(login, password);
        } catch (DAOException e){
            throw new ServiceException("Log In exception", e);
        }

        return user;
    }

    @Override
    public User registration(RegistrationInfo regInfo) throws ServiceException {
        if(regInfo == null || !validateRegistrationInfo(regInfo) || !freeLogin(regInfo.getEmail())){
            throw new ServiceException("wrong registration");
        }

        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        regInfo.setRoleId(USER_ROLE_ID);

        User user;

        try {
            userDAO.registration(regInfo);
            user = userDAO.getUser(regInfo.getEmail());
        } catch (DAOException e) {
            throw new ServiceException("Registration exception", e);
        }

        return user;
    }

    @Override
    public String getUserPassword(String email) throws ServiceException {
        String password;

        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        try {
            password = userDAO.getPassword(email);
        } catch (DAOException e) {
            throw new ServiceException("get password exception", e);
        }

        return password;
    }

    @Override
    public User getPersonalAccount(String email) throws ServiceException {
        User user;

        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        try {
            user = userDAO.getUser(email);
        } catch (DAOException e) {
            throw new ServiceException("get password exception", e);
        }

        return user;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> users;
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        try {
            users = userDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("users findALl exception", e);
        }
        return users;
    }

    @Override
    public void banUser(String userEmail) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        try {
           if(userDAO.banUser(userEmail)) {
               System.out.println("User is banned");
           }
        } catch (DAOException e) {
            throw new ServiceException("Error during banning user", e);
        }
    }

    @Override
    public void banAdmin(String userEmail) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        try {
            if(userDAO.banAdmin(userEmail)){
                System.out.println("Admin is banned and as user");
            }
        } catch (DAOException e) {
            throw new ServiceException("Error during banning admin", e);
        }
    }

    @Override
    public void appointToAdmin(String userEmail) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        try {
            if(userDAO.appointToAdmin(userEmail)) {
                System.out.println("User is assigned as an admin");
            }
        } catch (DAOException e) {
            throw new ServiceException("Error during banning appoint to admin", e);
        }
    }

    @Override
    public void setAvatarPath(String email, String fileName) {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();
        try {
            userDAO.uploadAvatarPath(email, fileName);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRating(Double rating, String email) {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();
        try {
            userDAO.setRating(rating, email);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double recalculateRating() throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();
        try {
            List<Double> allUsersRating = userDAO.getAllUsersRating();
            double allValue = 0.0;
            double size = 0;
            for (Double rating : allUsersRating) {
                if (rating > 0) {
                    allValue += rating;
                    size++;
                }
            }
            return allValue / size;
        } catch (DAOException e) {
            throw new ServiceException("Error in recalculating rating", e);
        }
    }

    @Override
    public void leftUserFeedback(String userEmail, String feedback) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();
        try{
            userDAO.setUserFeedback(userEmail, feedback);
        } catch (DAOException e) {
           throw new ServiceException("Error in set user feedback", e);
        }
    }

    private boolean freeLogin(String login) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserDAO();

        Integer id;

        try {
            id = userDAO.findId(login);
        } catch (DAOException e) {
            throw new ServiceException("Id search error", e);
        }

        return id == null;
    }

    public boolean validateAuthorizationInfo(String login, String password) throws ServiceException {
        if(!UserValidator.validateEmail(login)){
            throw new ServiceException("wrong email");
        }
        if(!UserValidator.validatePassword(password)){
            throw new ServiceException("wrong password");
        }

        return true;
    }

    public boolean validateRegistrationInfo(RegistrationInfo user) throws ServiceException {
        if(!UserValidator.validateName(user.getName())){
            throw new ServiceException("wrong name");
        }
        if(!UserValidator.validateSurname(user.getSurname())){
            throw new ServiceException("wrong surname");
        }
        if(!UserValidator.validatePhone(user.getPhone())){
            throw new ServiceException("wrong phone");
        }
        if(!UserValidator.validateEmail(user.getEmail())){
            throw new ServiceException("wrong email");
        }
        if(!UserValidator.validatePassword(user.getPassword())){
            throw new ServiceException("wrong password");
        }
        if(!UserValidator.validateRepeatPassword(user.getRepeatPassword(), user.getPassword())){
            throw new ServiceException("wrong passwords not repeat");
        }

        return true;
    }
}
