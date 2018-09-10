package nmu.devilliers;

import java.util.*;

/**
 * Created by scruf on 03-Mar-18.
 */
public class UniformCostSearch {
    private HashSet<ek> explored;  // explored[v] = is there an s-v path?
    private boolean[] marked;
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    public class ek{
        public Integer key;
        public Integer value;

        public ek(Integer k, Integer v){
            this.key = k;
            this.value = v;
        }

        @Override
        public String toString(){
            String s = key + " : " + value;
            return s;
        }
    }

    public class EntryComparator implements Comparator<ek>{
        @Override
        public int compare(ek o1, ek o2) {
            if(o1.value == o2.value)
                return 0;
            else if(o1.value < o2.value)
                return -1;
            else
                return 1;
        }
    }
    public class keyComparator implements Comparator<ek>{
        @Override
        public int compare(ek o1, ek o2) {
            if(o1.key == o2.key)
                return 0;
            else if(o1.key < o2.key)
                return -1;
            else
                return 1;
        }
    }

    /**
     * Computes the vertices connected to the source vertex {@code s} in the graph {@code G}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public UniformCostSearch(Graph G, int s, int f) {
        explored = new HashSet<ek>(G.V());
        marked = new boolean[G.V()];
        EntryComparator comparator = new EntryComparator();
        keyComparator keyCompare = new keyComparator();
        List<ek> frontier = new LinkedList<ek>();//treat like pq
        this.s = s;
        edgeTo = new int[G.V()];
        validateVertex(s);
        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
        }

        // depth-first search using an explicit stack
        //Stack<Integer> Frontier = new Stack<Integer>();
        explored.add(new ek(s, G.getmassofVertex(s)));
        System.out.println(s);
        marked[s] = true;
        frontier.add(new ek(s, G.getmassofVertex(s)));
        Collections.sort(frontier, comparator);

        while (!frontier.isEmpty()) {//while frontier has things
            ek curV = frontier.remove(0);
            System.out.println(curV.key);
            int v = curV.key;
            if (v == f)
            {
                edgeTo[v] = v;
                marked[v] = true;
                break;
            }
            explored.add(new ek(v, G.getmassofVertex(v)));       // add cur to explored
            marked[v] = true;
            while(adj[v].hasNext()) {
                int w = adj[v].next();
                ek curNeigh = new ek(w, G.getmassofVertex(w));

                // StdOut.printf("check %d\n", w);
                //if (!explored.contains(curNeigh)) {
                if (marked[v] == true){
                    // discovered vertex w for the first time
                    if (!frontier.contains(curNeigh)) {
                        frontier.add(curNeigh);
                        Collections.sort(frontier, comparator);
                        edgeTo[w] = w;
                    }
                    else {
                        Collections.sort(frontier, keyCompare);
                        int ipos = Collections.binarySearch(frontier, curNeigh, keyCompare);
                        ek ekPos = frontier.get(ipos);
                        if (ekPos.value < curNeigh.value )
                        {
                            frontier.remove(ekPos);
                            frontier.add(curNeigh);
                            Collections.sort(frontier, comparator);
                        }
                    }
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a path between the source vertex
     * {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    /**
     * Is vertex {@code v} connected to the source vertex {@code s}?
     *
     * @param v the vertex
     * @return {@code true} if vertex {@code v} is connected to the source vertex {@code s},
     * and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}
