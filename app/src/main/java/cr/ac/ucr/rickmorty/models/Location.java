package cr.ac.ucr.rickmorty.models;

import java.util.ArrayList;

public class Location {

    private int id;
    private String name;
    private String url;
    private String type;
    private String dimension;
    private ArrayList<String> residents;


    public Location() {
    }

    public Location(int id, String name, String url, String type, String dimension, ArrayList<String> residents) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.dimension = dimension;
        this.residents = residents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public ArrayList<String> getResidents() {
        return residents;
    }

    public void setResidents(ArrayList<String> residents) {
        this.residents = residents;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", dimension='" + dimension + '\'' +
                ", residents=" + residents +
                '}';
    }
}
