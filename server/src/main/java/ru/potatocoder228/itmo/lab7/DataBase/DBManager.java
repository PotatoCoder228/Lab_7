package ru.potatocoder228.itmo.lab7.DataBase;

import ru.potatocoder228.itmo.lab7.data.Coordinates;
import ru.potatocoder228.itmo.lab7.data.Dragon;
import ru.potatocoder228.itmo.lab7.data.DragonCave;
import ru.potatocoder228.itmo.lab7.data.DragonType;
import ru.potatocoder228.itmo.lab7.exceptions.ConnectionException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class DBManager {
    private Connection connection = null;
    private LinkedList<Dragon> collection;
    private String login;
    private String password;

    public boolean connect(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден!");
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://pg/studs");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public void setCollection(LinkedList<Dragon> list){
        collection=list;
    }
    public short initializeCollection(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DRAGONS;");
            short found=0;
            while (resultSet.next()){
                found=1;
                try {
                    collection.add(readPerson(resultSet));
                }catch (NullPointerException e){
                    System.out.println("Объект не добавлен");
                } catch (ConnectionException e) {
                    System.out.println("Поправь тут, ошибка соединения...");
                }
            }
            return found;
        } catch (SQLException e) {
            return -1;
        }
    }

    public synchronized Dragon readPerson(ResultSet resultSet) throws ConnectionException {
        int id;
        String name;
        long coordinatesX;
        Double coordinatesY;
        LocalDateTime date;
        int age;
        String description;
        Boolean speaking;
        DragonType type;
        DragonCave cave;
        try {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            coordinatesX = resultSet.getInt("coordinatesX");
            coordinatesY = resultSet.getDouble("coordinatesY");
            Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);
            date = LocalDateTime.parse(resultSet.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            age = resultSet.getInt("age");
            description = resultSet.getString("description");
            speaking = Boolean.parseBoolean(resultSet.getString("speaking"));
            type = DragonType.valueOf(resultSet.getString("type"));
            cave = new DragonCave(Float.parseFloat(resultSet.getString("cave")));
            Dragon newDragon = new Dragon(name, coordinates, age, description, speaking, type, cave);
            newDragon.setId(id);
            newDragon.setCreationDate(date);
            return newDragon;
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }
    public void setUser(String login1,String password1) {
        this.login=login1;
        this.password=password1;
    }
    public Dragon addPerson(Dragon dragon) throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DRAGON VALUES(nextval('ID'),?,?,?,current_timestamp,?,?,?,?,?,?);");
            preparedStatement.setString(1,dragon.getName());
            preparedStatement.setLong(2,dragon.getCoordinates().getX());
            preparedStatement.setDouble(3,dragon.getCoordinates().getY());
            preparedStatement.setInt(4,dragon.getAge());
            preparedStatement.setString(5,dragon.getDescription());
            preparedStatement.setBoolean(6,dragon.getSpeaking());
            preparedStatement.setString(7,dragon.getType().toString());
            preparedStatement.setFloat(8,dragon.getCave());
            preparedStatement.setString(9,login);
            preparedStatement.execute();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DRAGONS WHERE id = currval('id');");
            resultSet.next();
            return readPerson(resultSet);
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }
    public synchronized boolean deleteDragon(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DRAGONS WHERE id =? and userName=?;");
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,login);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка доступа к БД...");
            return false;//TODO
        }
    }
    public boolean clear() throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DRAGONS WHERE userName=?;");
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            LinkedList<Dragon> dragons = new LinkedList<>();
            while (resultSet.next()){
                dragons.add(readPerson(resultSet));
            }
            if (!dragons.isEmpty()) {
                dragons.stream().forEach(dragon -> {
                    deleteDragon(dragon.getId());
                    collection.remove(collection.stream().filter(dragon1 -> dragon1.getId()==dragon.getId()).findFirst().orElse(null));
                });
                return true;
            }else return false;
        }catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа БД");
        }
    }
    public Dragon deleteFirstDragon() throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DRAGONS WHERE userName=?;");
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            LinkedList<Dragon> dragons = new LinkedList<>();
            while (resultSet.next()){
                dragons.add(readPerson(resultSet));
            }
            if (!dragons.isEmpty()) {
                deleteDragon(dragons.peek().getId());
                collection.remove(collection.stream().filter(dragon -> dragon.getId()==dragons.peek().getId()).findFirst().orElse(null));
                return dragons.peek();
            }else return null;
        }catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа БД");
        }
    }
    public boolean updatePerson(int id,Dragon dragon) throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DRAGONS SET name=?,coordinatesX=?,coordinatesY=?, date = ?, age = ?, description = ?, speaking = ?, type = ?, cave = ? WHERE id = ? and userName=?;");
            preparedStatement.setString(1,dragon.getName());
            preparedStatement.setLong(2,dragon.getCoordinates().getX());
            preparedStatement.setDouble(3,dragon.getCoordinates().getY());
            preparedStatement.setInt(4,dragon.getAge());
            preparedStatement.setString(5,dragon.getDescription());
            preparedStatement.setBoolean(6,dragon.getSpeaking());
            preparedStatement.setString(7,dragon.getType().toString());
            preparedStatement.setFloat(8,dragon.getCave());
            preparedStatement.setInt(9,id);
            preparedStatement.setString(10,login);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }
    public boolean checkLogin(String login) throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE userName = ?;");
            preparedStatement.setString(1,login);
            preparedStatement.execute();
            return (preparedStatement.getResultSet().next());
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }
    public String hashPass(String password) {
        String pass=null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0"+(hashtext);
            }
            pass = hashtext;
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
        return pass;
    }
    public boolean checkPass(String login, String  password) throws ConnectionException {
        password = hashPass(password);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE userNAme = ? and password = ?;");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.execute();
            return (preparedStatement.getResultSet().next());
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }
    public synchronized short checkUser(String login, String password) throws ConnectionException {
        if (checkLogin(login)) {
            if (checkPass(login,password)) {
                this.login = login;
                this.password=password;
                return 1;
            } else return -1;
        }  else {
            if (addUser(login,password)) {
                this.login = login;
                this.password = password;
                return 0;
            }else return -2;
        }
    }
    public boolean addUser(String login,String password) throws ConnectionException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS VALUES(?,?);");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,hashPass(password));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }
}
