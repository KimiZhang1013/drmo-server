package uo.rocky.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public final class User implements EntityRelatesToJSON, EntityRelatesToSQLite {
    private static Connection connection = null;

    private String name;
    private int hashedpassword;
    private String email;
    private String phone;

    public User(String name, int hashedpassword, String email, String phone) {
        this.name = name;
        this.hashedpassword = hashedpassword;
        this.email = email;
        this.phone = phone;
    }

    public static User valueOf(JSONObject jsonObject) throws JSONException {
        return new User(
                jsonObject.getString("username"),
                jsonObject.getString("password").hashCode(),
                jsonObject.has("email") ? jsonObject.getString("email") : null,
                jsonObject.has("phone") ? jsonObject.getString("phone") : null
        );
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        User.connection = connection;
    }

    public static synchronized List<User> selectSQLite(Map<String, String> params) throws Exception {
        String query;
        Statement statement;
        ResultSet resultSet;
        List<User> results;
        switch (params.getOrDefault("QUERY", "QUERY KEY NOT FOUND").toUpperCase()) {
            case "USERNAME":
                query = "SELECT * FROM user WHERE USR_NAME = " + params.get("USERNAME") + ";";
                System.out.println(query);

                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(new User(
                            resultSet.getString("USR_NAME"),
                            resultSet.getInt("USR_HASHEDPASSWORD"),
                            resultSet.getString("USR_EMAIL"),
                            resultSet.getString("USR_PHONE")
                    ));
                }
                resultSet.close();
                statement.close();
                connection.commit();
                break;
            case "EMAIL":
                break;
            case "PHONE":
                break;
            case "QUERY KEY NOT FOUND":
                results = null;
                break;
            default:
                results = null;
                break;
        }
        return results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHashedpassword() {
        return hashedpassword;
    }

    public void setHashedpassword(int hashedpassword) {
        this.hashedpassword = hashedpassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("name=" + (null == name ? "null" : "'" + name + "'"))
                .add("hashedpassword=" + hashedpassword)
                .add("email=" + (null == email ? "null" : "'" + email + "'"))
                .add("phone=" + (null == phone ? "null" : "'" + phone + "'"))
                .toString();
    }

    @Override
    public String toJSONString() {
        return new StringJoiner(",", "{", "}")
                .add("\"username\"=" + (null == name ? "null" : "\"" + name + "\""))
                .add("\"hashedpassword\"=\"" + hashedpassword + "\"")
                .add("\"email\"=" + (null == email ? "null" : "\"" + email + "\""))
                .add("\"phone\"=" + (null == phone ? "null" : "\"" + phone + "\""))
                .toString();
    }

    @Override
    public synchronized boolean insertSQLite() throws Exception {
        // TODO
//        Class.forName("org.sqlite.JDBC");

        String query = String.format("INSERT INTO user" +
                        " (USR_NAME,USR_HASHEDPASSWORD,USR_EMAIL,USR_PHONE)" +
                        " VALUES ('%s',%s," + (null == email ? "%S" : "'%s'") + "," + (null == phone ? "%S" : "'%s'") + ");",
                EntityRelatesToSQLite.escapeSingleQuotes(name),
                hashedpassword,
                EntityRelatesToSQLite.escapeSingleQuotes(email),
                EntityRelatesToSQLite.escapeSingleQuotes(phone)
        );
        System.out.println(query);

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
        connection.commit();

        return true;
    }

    @Override
    public synchronized boolean deleteSQLite() throws Exception {
        // TODO
        return true;
    }

    @Override
    public synchronized boolean updateSQLite() throws Exception {
        // TODO
        return true;
    }
}