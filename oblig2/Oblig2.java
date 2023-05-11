import java.io.*;
import java.util.*;


public class Oblig2 {
    public static void main (String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();

        System.out.println("Oppgave 1\n");

        File movies = new File("movies.tsv");
        File actors = new File("actors.tsv");

        Scanner scanMovies = new Scanner(movies);
        Scanner scanActors = new Scanner(actors);

        HashMap<String, Movie> findMovie = new HashMap<>();
        HashMap<String, Actor> findActor = new HashMap<>();

        ArrayList<Actor> actorsList = new ArrayList<>();

        // Scans "movies.tsv" and stores information.
        while (scanMovies.hasNextLine()) {
            String line = scanMovies.nextLine();
            String[] info = line.strip().split("\t");

            String tt_id = info[0];
            String title = info[1];
            String rating = info[2];

            findMovie.put(tt_id, new Movie(tt_id, title, rating));
        }

        // Scans "actors.tsv" and stores information. 
        while (scanActors.hasNextLine()) {
            String line = scanActors.nextLine();
            String[] info = line.strip().split("\t");
            ArrayList<Movie> list = new ArrayList<>();

            String nm_id = info[0];
            String name = info[1];

            // Makes sure to ignore the titles not included in "movies.tsv".
            for (int i = 2; i < info.length; i ++) {
                Movie m = findMovie.get(info[i]);
                if (findMovie.get(info[i]) != null) {
                    list.add(m);
                }
            }
            Actor actor = new Actor(nm_id, name, list);
            actorsList.add(actor);
            findActor.put(nm_id, actor);

            // Adds the actor to the list inside of a movie object they star in.
            for (int i = 0; i < list.size(); i ++) {
                list.get(i).addActor(actor);
            }
        }

        // Targeted representation for graph using IDs: HashMap<String, ArrayList<String[]>>, (actor, [[co-actor, movie1], [co-actor, movie2], [co-actor, movie3]])
        HashMap<String, ArrayList<String[]>> graph = buildGraph(actorsList);

        // Calculates the number of nodes and edges.
        int nodeQuantity = graph.size();
        int edgeQuantity = 0;

        for (ArrayList<String[]> edges : graph.values()) {
            edgeQuantity += edges.size();
        }
        edgeQuantity = edgeQuantity/2;

        System.out.println("Nodes: " + nodeQuantity + "\nEdges: " + edgeQuantity);


        System.out.println("\n\nOppgave 2\n");

        // Donald Glover and Jeremy Irons.
        printShortestPath(graph, "nm2255973", "nm0000460", findMovie, findActor);
        // Scarlett Johansson and Denzel Washington.
        printShortestPath(graph, "nm0424060", "nm0000243", findMovie,  findActor);
        // Carrie Coon and Julie Delpy.
        printShortestPath(graph, "nm4689420", "nm0000365", findMovie,  findActor);
        // Christian Bale and Angelina Jolie.
        printShortestPath(graph, "nm0000288", "nm0001401", findMovie,  findActor);
        // Atle Antonsen and Michael K. Williams.
        printShortestPath(graph, "nm0031483", "nm0931324", findMovie,  findActor);


        System.out.println("\n\nOppgave 3\n");

        // Donald Glover and Jeremy Irons.
        printChillestPath(graph, "nm2255973", "nm0000460", findMovie, findActor);
        // Scarlett Johansson and Denzel Washington.
        printChillestPath(graph, "nm0424060", "nm0000243", findMovie,  findActor);
        // Carrie Coon and Julie Delpy.
        printChillestPath(graph, "nm4689420", "nm0000365", findMovie,  findActor);
        // Christian Bale and Angelina Jolie.
        printChillestPath(graph, "nm0000288", "nm0001401", findMovie,  findActor);
        // Atle Antonsen and Michael K. Williams.
        printChillestPath(graph, "nm0031483", "nm0931324", findMovie,  findActor);


        System.out.println("\n\nOppgave 4\n");

        printComponentsInformation(graph);


        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;

        System.out.println("\nTime elapsed: " + elapsedSeconds + "s");

        scanMovies.close();
        scanActors.close();

    }

    // Builds a graph using information stored in the Actor objects.
    public static HashMap<String, ArrayList<String[]>> buildGraph (ArrayList<Actor> actorsList) {
        HashMap<String, ArrayList<String[]>> graph = new HashMap<>();

        for (int i = 0; i < actorsList.size(); i ++) {
            Actor actor1 = actorsList.get(i);
            String nm_id = actor1.getId();
            ArrayList<String[]> a1 = new ArrayList<>();
            
            // Adds any other actors that has played in a movie with actor1.
            for (int j = 0; j < actor1.getMovies().size(); j ++) {
                Movie movie = actor1.getMovies().get(j);
                ArrayList<Actor> a2 = movie.getActors();

                for (int k = 0; k < a2.size(); k ++) {
                    Actor actor2 = a2.get(k);

                    if (actor2 != actor1) {
                        String[] list = {actor2.getId(), movie.getId()};
                        a1.add(list);
                    }
                }
            }
            graph.put(nm_id, a1);
        }
        return graph;
    }

    // Prints the shortest path from one actor to another.
    public static void printShortestPath (HashMap<String, ArrayList<String[]>> graph, String startActor, String endActor, HashMap<String, Movie> findMovie, HashMap<String, Actor> findActor) {
        
        // Gathers the shortest path to all nodes using BFS algorithm.
        HashMap<String, String> paths = BFS_withHashMap(graph, startActor);
        ArrayList<String> path = new ArrayList<>();
        
        // Finds the path from endactor to startactor, and reverses it.
        String nextActor = endActor;
        while (nextActor != null) {
            path.add(nextActor);
            nextActor = paths.get(nextActor);
        }
        Collections.reverse(path);

        // Makes a representation for the path with corresponding movies.
        ArrayList<String[]> path2 = new ArrayList<>();

        for (int i = 0; i < path.size()-1; i ++) {
            ArrayList<String[]> coActors = graph.get(path.get(i));

            inner: for (int j = 0; j < coActors.size(); j ++) {
                String[] information = coActors.get(j);

                if (information[0].equals(path.get(i+1))) {
                    path2.add(information);
                    break inner;
                }
            }
        }

        System.out.println(findActor.get(startActor).getName());
        for (String[] info : path2) {
            System.out.println("===[ " + findMovie.get(info[1]).getTitle() + " (" + findMovie.get(info[1]).getRating() + ") ] ===>  " + findActor.get(info[0]).getName());
        }
        System.out.println();
    }

    // Here i created an AlmostDeque for a faster adding time to the back of the queue.
    // It doesn't have all the necessary functions of a Deque implementation, but it does it's job here.
    public static HashMap<String, String> BFS_withHashMap (HashMap<String, ArrayList<String[]>> graph, String startActor) {
        
        HashMap<String, String> parents = new HashMap<>();
        parents.put(startActor, null);
        
        AlmostDeque queue = new AlmostDeque();

        queue.push_front(startActor);

        while (queue.size() != 0) {
            String v = queue.remove_front();
            for (int i = 0; i < graph.get(v).size(); i ++) {
                String u = graph.get(v).get(i)[0];
                if (!parents.containsKey(u)) {
                    parents.put(u, v);
                    queue.push_back(u);
                }
            }
        }
        return parents;
    }

    // Prints the chillest path from one actor to another.
    public static void printChillestPath (HashMap<String, ArrayList<String[]>> graph, String startActor, String endActor, HashMap<String, Movie> findMovie, HashMap<String, Actor> findActor) {
        
        // Gathers the chillest path to all nodes using Dijkstra's algorithm.
        ArrayList<HashMap<String, String>> paths = Dijkstra_withHashMap(graph, startActor, findMovie, findActor);
        ArrayList<String> path = new ArrayList<>();
        
        // Finds the path from endactor to startactor, and reverses it.
        String nextActor = endActor;
        while (nextActor != null) {
            path.add(nextActor);
            nextActor = paths.get(0).get(nextActor);
        }
        Collections.reverse(path);

        // Makes a representation for the path with corresponding movies.
        ArrayList<String[]> path2 = new ArrayList<>();

        for (int i = 1; i < path.size(); i ++) {
            String actor = path.get(i);
            String movie = paths.get(1).get(actor);

            String[] pair = {actor, movie};
            path2.add(pair);
        }

        float counter = 0;
        System.out.println(findActor.get(startActor).getName());
        for (String[] info : path2) {
            System.out.println("===[ " + findMovie.get(info[1]).getTitle() + " (" + findMovie.get(info[1]).getRating() + ") ] ===>  " + findActor.get(info[0]).getName());
            counter += 10 - findMovie.get(info[1]).getRating();
        }
        System.out.println("Total weight: " + counter + "\n");
    }

    public static ArrayList<HashMap<String, String>> Dijkstra_withHashMap (HashMap<String, ArrayList<String[]>> graph, String startActor, HashMap<String, Movie> findMovie, HashMap<String, Actor> findActor) {
        
        // Defines a hashmap with actors referring to previous actors, and sets
        // startActor to having no actors before them.
        HashMap<String, String> parents = new HashMap<>();
        parents.put(startActor, null);

        // Defines a hashmap with actors referring to the correct movie they played in. 
        HashMap<String, String> actorMoviePairs = new HashMap<>();

        // Defines a hashmap with actors referring to a cost they've acquired, and sets
        // startActor to having a cost of 0.
        HashMap<Actor, Integer> D = new HashMap<>();
        D.put(findActor.get(startActor), 0);

        // Compares Actors with a cost-variable inside the Actor class, compatible with Comparable implementation.
        PriorityQueue<Actor> queue = new PriorityQueue<>();
        queue.offer(findActor.get(startActor));

        while (queue.size() != 0) {
            Actor v = queue.poll();
            
            // Iterates through every coActor u for actor v, and compares old cost to new cost.
            for (int i = 0; i < graph.get(v.getId()).size(); i ++) {
                String[] actorMoviePair = graph.get(v.getId()).get(i);
                Actor u = findActor.get(actorMoviePair[0]);
                Movie movie = findMovie.get(actorMoviePair[1]);
                int movieRating = (int) (100-(movie.getRating()*10));

                int c = v.getCost() + movieRating;

                // If c is lower, creates copy of actor with id intact, and puts this object on the queue.
                if (c < D.getOrDefault(u, Integer.MAX_VALUE)) {

                    // Creates a copy-object of actor u with only the ID intact.
                    // This prevents problems from having multiple of the same actor with different costs in the queue.
                    Actor copy = new Actor(u.getId(), null, null);
                    D.put(u, c);
                    copy.setCost(c);
                    queue.offer(copy);
                    parents.put(u.getId(), v.getId());

                    // Makes a relation between the actor and the movie they played in.
                    actorMoviePairs.put(u.getId(), movie.getId());
                }
            }
        }

        ArrayList<HashMap<String, String>> results = new ArrayList<>();
        results.add(parents);
        results.add(actorMoviePairs);
        return results;
    }

    public static void printComponentsInformation (HashMap<String, ArrayList<String[]>> graph) {

        // A hashmap that if given an actor nm_id, returns true if they've been visited.
        HashMap<String, Boolean> visited = new HashMap<>();

        // A hashmap that sorts components by their size. If a component C with size k is created inside the DFS_search,
        // this component is added to an arrayList referred to by the size of that component C in the hashmap.
        HashMap<Integer, ArrayList<ArrayList<String>>> findComponents = new HashMap<>();

        for (String key : graph.keySet()) {
            if (!visited.getOrDefault(key, false)) {
                ArrayList<String> component = DFS_search(graph, key, visited);

                // Adds the component to the findComponents hashmap.
                if (findComponents.get(component.size()) != null) {
                    findComponents.get(component.size()).add(component);
                }
                else {
                    ArrayList<ArrayList<String>> components = new ArrayList<>();
                    components.add(component);
                    findComponents.put(component.size(), components);
                }
            }
        }

        // Arranges the sizes of the components in a convenient order.
        ArrayList<Integer> order = new ArrayList<>();
        for (Integer key : findComponents.keySet()) {
            order.add(key);
        }
        Collections.sort(order);
        Collections.reverse(order);

        // Prints the results.
        for (Integer size : order) {
            int quantity = findComponents.get(size).size();

            System.out.println("There are " + quantity + " components of size " + size);
        }
    }

    public static ArrayList<String> DFS_search (HashMap<String, ArrayList<String[]>> graph, String startNode, HashMap<String, Boolean> visited) {

        Stack<String> stack = new Stack<>();
        stack.add(startNode);

        // Makes a list holding all the nodes from the result.
        ArrayList<String> component = new ArrayList<>();

        while (stack.size() != 0) {
            String v = stack.pop();

            if (!visited.getOrDefault(v, false)) {
                component.add(v);
                visited.put(v, true);

                for (int i = 0; i < graph.get(v).size(); i ++) {
                    stack.add(graph.get(v).get(i)[0]);
                }
            }
        }
        return component;
    }
}