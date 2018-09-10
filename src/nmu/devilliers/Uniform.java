package nmu.devilliers;

import java.util.*;

/**
 * Created by scruf on 03-Mar-18.
 */
public class Uniform {
    private boolean[] explored;  // explored[v] = is there an s-v path?
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

    public class EntryComparator implements Comparator<ek> {
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
    public String UniformSearch(Graph G, Integer s, Integer f)
    {
        ///this.s = s;
        edgeTo = new int[G.V()];
        validateVertex(s);
        String sPath = "";
        EntryComparator comparator = new EntryComparator();
        List<ek> Frontier = new LinkedList<ek>();//priority q
        Frontier.add(new ek(s, G.getmassofVertex(s)));
        HashSet<Integer> Explored = new HashSet<Integer>();
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        Collections.sort(Frontier, comparator);
        Set<ek> setFront = new HashSet<ek>();
        setFront.addAll(Frontier);
        Frontier.clear();
        Frontier.addAll(setFront);
        Collections.sort(Frontier, comparator);




        while (adj[s].hasNext()) {
            Integer iad = adj[s].next();
            Integer iw = G.getmassofVertex(iad);
            ek q = new ek(iad, iw);
            //System.out.println(q.toString());

            Frontier.add(q);
            edgeTo[iad] = s;
            Collections.sort(Frontier, comparator);
            setFront = new HashSet<ek>();
            setFront.addAll(Frontier);
            Frontier.clear();
            Frontier.addAll(setFront);
            Collections.sort(Frontier, comparator);
        }//add initial state to frontier


        Collections.sort(Frontier, comparator);

        setFront = new HashSet<ek>();
        setFront.addAll(Frontier);
        Frontier.clear();
        Frontier.addAll(setFront);
        Collections.sort(Frontier, comparator);

        while (Frontier.isEmpty() == false)
        {
            ek cur = Frontier.remove(0);

            if (Explored.contains(cur.key) == false) {
                sPath = sPath + " " + cur.key;
                //System.out.println(cur.toString());//print frontier if not explored
            }
            Collections.sort(Frontier, comparator);

            setFront = new HashSet<ek>();
            setFront.addAll(Frontier);
            Frontier.clear();
            Frontier.addAll(setFront);
            Collections.sort(Frontier, comparator);

            Integer ivCur = cur.key;
            Integer iwCur = cur.value;
            //Frontier Dequeued
            if (ivCur == f)
            {

                //System.out.println("Goal: " + ivCur);
                //Your Goal has been reached
                Explored.add(ivCur);
                break;
            }
            Explored.add(ivCur);
            //System.out.println(ivCur);
            //add cur to explored
           // Iterator<Integer> iter = adj[ivCur].listIterator();
            Iterator<Integer>[] iter = (Iterator<Integer>[]) new Iterator[G.V()];
            for (int v = 0; v < G.V(); v++)
                iter[v] = G.adj(v).iterator();


            while (iter[ivCur].hasNext())//iterate through adjacency list
            {
                Integer iAdjV = iter[ivCur].next();
                Boolean binExp = Explored.contains(iAdjV);
                if (binExp == false)
                {
                    ek searchVertex = new ek( iAdjV, -1);
                    Integer inFront = Collections.binarySearch(Frontier, searchVertex, comparator);
                    Boolean binFront = false;
                    binFront = inFront >= 0;
                    if (binFront == false)
                    {
                        ek adder = new ek(iAdjV, G.getmassofVertex(iAdjV));
                        Frontier.add(adder);
                        edgeTo[iAdjV] = ivCur;
                        Collections.sort(Frontier, comparator);

                        setFront = new HashSet<ek>();
                        setFront.addAll(Frontier);
                        Frontier.clear();
                        Frontier.addAll(setFront);
                        Collections.sort(Frontier, comparator);

                    }
                    else
                    {
                        Integer iw = Frontier.get(inFront).value;
                        if (G.getmassofVertex(iAdjV) < iw)
                        {

                            Frontier.remove(inFront);
                            Collections.sort(Frontier, comparator);
                            ek adder = new ek(iAdjV, G.getmassofVertex(iAdjV));
                            Collections.sort(Frontier, comparator);
                            setFront = new HashSet<ek>();
                            setFront.addAll(Frontier);
                            Frontier.clear();
                            Frontier.addAll(setFront);
                            Collections.sort(Frontier, comparator);
                            edgeTo[iAdjV] = ivCur;

                        }
                    }
                }
            }
        }
        System.out.println(sPath);
        return sPath;
    }






    /**
     * Computes the vertices connected to the source vertex {@code s} in the graph {@code G}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    /*public Uniform(Graph G, int s) {
        explored = new boolean[G.V()];
        this.s = s;
        edgeTo = new int[G.V()];
        validateVertex(s);
        EntryComparator comparator = new EntryComparator();
        keyComparator keyCompare = new keyComparator();

        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // depth-first search using an explicit stack
        //Stack<Integer> Frontier = new Stack<Integer>();
        List<ek> frontier = new ArrayList<ek>();
        explored[s] = true;
        frontier.add(new ek(s, G.getmassofVertex(s)));
        //Frontier.push(s);
        while (!frontier.isEmpty()) {//while frontier has things
            frontier.sort(comparator);
            int v = frontier.get(0).key;
            int m = frontier.get(0).value;

            if (adj[v].hasNext()) {
                int w = adj[v].next();
                // StdOut.printf("check %d\n", w);
                if (!explored[w]) {
                    // discovered vertex w for the first time
                    if (!(frontier.contains(new ek(w, G.getmassofVertex(w)))))
                    {
                        frontier.add(new ek(w, G.getmassofVertex(w)));

                        explored[w] = true;
                        edgeTo[w] = v;
                    }
                    else
                    {
                        int cm = G.getmassofVertex(w);
                        if (cm < m)
                        {
                            int ipos = frontier.lastIndexOf(v);
                            frontier.remove(ipos);
                            frontier.add(new ek(cm, G.getmassofVertex(cm)));
                            explored[w] = true;
                            edgeTo[w] = v;
                        }
                    }
                }
            }
            frontier.remove(0);
        }
    }*/


    public Uniform(Graph G, int s) {
        explored = new boolean[G.V()];
        this.s = s;
        edgeTo = new int[G.V()];
        validateVertex(s);

        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // depth-first search using an explicit stack
        ArrayList<Integer> Frontier = new ArrayList<Integer>();
        explored[s] = true;
        Frontier.add(s);
        //System.out.print(s + " ");
        sort(Frontier, G);
        while (!Frontier.isEmpty()) {//while frontier has things
            int v = Frontier.remove(0);
            System.out.print(v + "+");
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                //System.out.print(w + " - ");
                // StdOut.printf("check %d\n", w);
                if (!explored[w]) {
                    Boolean bfound = false;
                    int ipos = -1;

                    for (int i = 0; i <= Frontier.size() - 1; i++) {
                        int a = Frontier.get(i);
                        if (a == w) {
                            bfound = true;
                            ipos = i;
                        }
                    }
                    if ((bfound == false) && (explored[w] == false)) {
                        Frontier.add(w);
                        sort(Frontier, G);
                        explored[w] = true;
                        edgeTo[w] = v;
                    } else if (bfound && explored[w]) {
                        if (G.getmassofVertex(w) > G.getmassofVertex(Frontier.get(ipos))) {
                            if (G.getmassofVertex(w) > G.getmassofVertex(Frontier.get(ipos))) {
                                Frontier.remove(ipos);
                                Frontier.add(ipos, w);
                                sort(Frontier, G);
                                explored[w] = false;
                                explored[Frontier.get(ipos)] = false;
                                edgeTo[Frontier.get(ipos)] = v;
                            }
                        }
                    }
                } else {
                    // StdOut.printf("%d done\n", v);
                    Frontier.remove(0);
                }
            }
        }
    }

    void sort(ArrayList<Integer> arr, Graph G)
    {
        int n = arr.size();
        for (int i=1; i<n; ++i)
        {
            int key = G.getmassofVertex(arr.get(i));
            int j = i-1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j>=0 && G.getmassofVertex(arr.get(j)) > key)
            {
                arr.set(j+1, arr.get(j));

                j = j-1;
            }
            arr.set(j+1, key);
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
        return explored[v];
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
        return explored[v];
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = explored.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
