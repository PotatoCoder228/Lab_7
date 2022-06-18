package ru.potatocoder228.itmo.lab7.database;

import ru.potatocoder228.itmo.lab7.exceptions.DatabaseException;
import ru.potatocoder228.itmo.lab7.log.Log;

import java.sql.*;

public class DatabaseHandler {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private final String user;
    private final String password;
    private final String url;
    private Connection connection;

    public DatabaseHandler(String url, String u, String p) throws DatabaseException {
        user = u;
        this.url = url;
        password = p;
        connectToDataBase();
    }

    private void connectToDataBase() throws DatabaseException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException exception) {
            throw new DatabaseException("Ошибка подключения к базе данных.");
        } catch (ClassNotFoundException exception) {
            throw new DatabaseException("Драйвер не найден.");
        }
    }

    public PreparedStatement getPreparedStatement(String sqlStatement, boolean generateKeys) throws DatabaseException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            return preparedStatement;
        } catch (SQLException exception) {
            throw new DatabaseException("Ошибка отправки SQL-запроса.");
        }
    }

    public Statement getStatement() throws DatabaseException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new DatabaseException("Невозможно отправить SQL-запрос.");
        }
    }


    public PreparedStatement getPreparedStatement(String sql) throws DatabaseException {
        return getPreparedStatement(sql, false);
    }

    public void closeConnection() {
        if (connection == null) return;
        try {
            connection.close();
            Log.logger.info("Соединение с базой данных прекращено.");
        } catch (SQLException exception) {
            Log.logger.error("Ошибка при прерывании соединения с базой данных...");
        }
    }

    /**
     * Set commit mode of database.
     */
    public void setCommitMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            Log.logger.error("Произошла ошибка при установлении режима транзакции базы данных!");
        }
    }

    /**
     * Set normal mode of database.
     */
    public void setNormalMode() {
        try {
            if (connection == null) throw new SQLException();
            connection.setAutoCommit(true);
        } catch (SQLException exception) {
            Log.logger.error("Произошла ошибка при установлении нормального режима базы данных!");
        }
    }

    /**
     * Commit database status.
     */
    public void commit() {
        try {
            if (connection == null) throw new SQLException();
            connection.commit();
        } catch (SQLException exception) {
            Log.logger.error("Произошла ошибка при подтверждении нового состояния базы данных!");
        }
    }

    /**
     * Roll back database status.
     */
    public void rollback() {
        try {
            if (connection == null) throw new SQLException();
            connection.rollback();
        } catch (SQLException exception) {
            Log.logger.error("Произошла ошибка при возврате исходного состояния базы данных!");
        }
    }

    /**
     * Set save point of database.
     */
    public void setSavepoint() {
        try {
            if (connection == null) throw new SQLException();
            connection.setSavepoint();
        } catch (SQLException exception) {
            Log.logger.error("Произошла ошибка при сохранении состояния базы данных!");
        }
    }
}