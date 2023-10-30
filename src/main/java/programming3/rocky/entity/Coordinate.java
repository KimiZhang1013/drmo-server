package programming3.rocky.entity;

import programming3.rocky.entity.enumdata.Danger;

public class Coordinate {
    private long id;
    private double longitude;
    private double latitude;
    private double datetime;
    private Danger danger;
    private String description;
    private String usrName;

    public Coordinate(long id, double longitude, double latitude, double datetime, Danger danger, String usrName) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.datetime = datetime;
        this.danger = danger;
        this.usrName = usrName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDatetime() {
        return datetime;
    }

    public void setDatetime(double datetime) {
        this.datetime = datetime;
    }

    public Danger getDanger() {
        return danger;
    }

    public void setDanger(Danger danger) {
        this.danger = danger;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }
}