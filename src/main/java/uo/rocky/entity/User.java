package uo.rocky.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.StringJoiner;

public final class User implements EntityRelatesToJSON, EntityRelatesToSQLite {
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

    private User(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("username");
        hashedpassword = jsonObject.getString("password").hashCode();
        email = jsonObject.has("email") ? jsonObject.getString("email") : null;
        phone = jsonObject.has("phone") ? jsonObject.getString("phone") : null;
    }

    public static User valueOf(JSONObject jsonObject) {
        return new User(jsonObject);
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
                .add("name='" + name + "'")
                .add("hashedpassword=" + hashedpassword)
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                .toString();
    }

    @Override
    public String toJSONString() {
        return new StringJoiner(",", "{", "}")
                .add("\"name\"=\"" + name + "\"")
                .add("\"hashedpassword\"=\"" + hashedpassword + "\"")
                .add(String.format("\"email\"=%s", null == email ? "null" : "\"" + email + "\""))
                .add(String.format("\"phone\"=%s", null == phone ? "null" : "\"" + phone + "\""))
                .toString();
    }

    @Override
    public synchronized boolean insertSQLite() throws Exception {
        // TODO
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

    @Override
    public synchronized boolean selectSQLite(List<EntityRelatesToSQLite> results) throws Exception {
        // TODO
        return true;
    }
}