package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.User;

import java.sql.Connection;

public class UserDaoImpl extends GenericDao<User> implements UserDao {

    private final static String TABLENAME = "USER";

    public UserDaoImpl(final Connection connection) {
        super(connection, TABLENAME);
    }

    @Override
    public User findByEmail(final String email) {
        //TODO
        return null;
    }

    @Override
    public boolean insert(final User user) {
        //TODO
        return false;
    }

    @Override
    public boolean delete(final User user) {
        //TODO
        return false;
    }

    @Override
    public boolean login(final String email, final String password) {
        //TODO
        return false;
    }
}
