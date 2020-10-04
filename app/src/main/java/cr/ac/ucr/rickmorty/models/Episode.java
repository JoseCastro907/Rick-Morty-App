package cr.ac.ucr.rickmorty.models;

import java.util.ArrayList;

public class Episode {

    private int id;
    private String name;
    private String air_date;
    private String episode;
   // private Character characters;
    private ArrayList<String> character;
    private String url;

    public Episode() {
    }

    public Episode(int id, String name, String air_date, String episode, ArrayList<String> character, String url) {
        this.id = id;
        this.name = name;
        this.air_date = air_date;
        this.episode = episode;
        //this.characters = characters;
        this.character = character;
        this.url = url;
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

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    /*public Character getCharacters() {
        return characters;
    }

    public void setCharacters(Character characters) {
        this.characters = characters;
    }*/

    public ArrayList<String> getCharacter() {
        return character;
    }

    public void setCharacter(ArrayList<String> character) {
        this.character = character;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", air_date='" + air_date + '\'' +
                ", episode='" + episode + '\'' +
                ", character=" + character +
                ", url='" + url + '\'' +
                '}';
    }
}
