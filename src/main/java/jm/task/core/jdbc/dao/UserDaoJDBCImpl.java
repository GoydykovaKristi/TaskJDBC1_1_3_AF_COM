package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {


    }
    public void createUsersTable() {

        try(Connection connection = Util.getUtilConnection();
            Statement statement= connection.createStatement();)
        {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users "+
                    "(id BIGINT(19) NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NOT NULL," +
                    "lastname VARCHAR(45) NOT NULL, " +
                    "age TINYINT(3) NOT NULL, " +
                    "PRIMARY KEY (id));");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropUsersTable() {

        try(Connection connection = Util.getUtilConnection();

            Statement statement = connection.createStatement()) {

            statement.executeUpdate("DROP TABLE IF EXISTS users;");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try(Connection connection = Util.getUtilConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (name, lastname, age) Values (?, ?, ?);")) {


            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try(Connection connection = Util.getUtilConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> arrayUsers = new ArrayList<>();
        try(Connection connection = Util.getUtilConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users;")) {



            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                String name= resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                Byte age = resultSet.getByte("age");

                arrayUsers.add(new User(name, lastname, age));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return arrayUsers;
    }

    public void cleanUsersTable() {

        try(Connection connection = Util.getUtilConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE users;")) {

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
