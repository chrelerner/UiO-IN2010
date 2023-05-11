import java.util.*;

public class Movie {

    private String tt_id;
    private String title;
    private double rating;
    private ArrayList<Actor> actors = new ArrayList<>();

    public Movie (String tt_id, String title, String rating) {
        this.tt_id = tt_id;
        this.title = title;
        this.rating = Double.parseDouble(rating);
    }

    public void addActor (Actor actor) {
        actors.add(actor);
    }

    public String getId () {
        return this.tt_id;
    }

    public String getTitle () {
        return this.title;
    }

    public double getRating () {
        return this.rating;
    } 

    public ArrayList<Actor> getActors () {
        return this.actors;
    }
}
