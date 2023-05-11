import java.util.*;

public class Actor implements Comparable<Actor> {
    private String nm_id;
    private String name;
    private ArrayList<Movie> movies;
    private int cost = 0;

    public Actor (String nm_id, String name, ArrayList<Movie> movies) {
        this.nm_id = nm_id;
        this.name = name;
        this.movies = movies;
    }

    public String getId () {
        return this.nm_id;
    }

    public String getName () {
        return this.name;
    }

    public ArrayList<Movie> getMovies () {
        return this.movies;
    }

    public void setCost (int number) {
        this.cost = number;
    }

    public int getCost () {
        return this.cost;
    }

    @Override
    public int compareTo(Actor actor2) {
        return this.cost - actor2.getCost();
    }

    
}
