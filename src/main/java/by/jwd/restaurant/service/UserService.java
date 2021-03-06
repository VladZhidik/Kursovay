package by.jwd.restaurant.service;

import by.jwd.restaurant.entity.RegistrationInfo;
import by.jwd.restaurant.entity.User;
import by.jwd.restaurant.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    User authorization(String login, String password) throws ServiceException;
    User registration(RegistrationInfo user) throws ServiceException;
    String getUserPassword(String email) throws ServiceException;
    User getPersonalAccount(String email) throws ServiceException;
    List<User> findAll() throws ServiceException;
    void banUser(String userEmail) throws ServiceException;
    void banAdmin(String userEmail) throws ServiceException;
    void appointToAdmin(String userEmail) throws ServiceException;

    void setAvatarPath(String email, String fileName);

    void setRating(Double rating, String userEmail);
    Double recalculateRating() throws ServiceException;
    void leftUserFeedback(String userEmail, String feedback) throws ServiceException;
}
