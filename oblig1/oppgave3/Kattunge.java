import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Kattunge {
    
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int node = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<Integer>> tree = new ArrayList<>();

        String[] line = br.readLine().split(" ");

        while (!line[0].equals("-1")) {
            ArrayList<Integer> nodes = new ArrayList<>();

            for (int i = 0; i < line.length; i ++) {
                nodes.add(Integer.parseInt(line[i]));
            }

            tree.add(nodes);
            line = br.readLine().split(" ");
        }

        findPath(node, tree);
    }

    public static void findPath(int node, ArrayList<ArrayList<Integer>> tree) {
        ArrayList<Integer> path = new ArrayList<>();

        findPath(path, node, tree);

        for (int element : path) {
            System.out.print(element + " ");
        }
    }

    public static void findPath(ArrayList<Integer> path, int node, ArrayList<ArrayList<Integer>> tree) {
        if (node == 0) {
            return;
        }

        path.add(node);

        findPath(path, findParent(tree, node), tree);
    }

    public static int findParent(ArrayList<ArrayList<Integer>> tree, int node) {

        for (ArrayList<Integer> i : tree) {
            if (i.contains(node) && i.get(0) != node) {
                return i.get(0);
            }
        }
        return 0;
    }
}
