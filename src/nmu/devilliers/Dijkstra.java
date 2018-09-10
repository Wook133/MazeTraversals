package nmu.devilliers;

import java.util.*;

/**
 * Created by scruf on 04-Mar-18.
 */
public class Dijkstra {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] explored;  // explored[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private final int s;// source vertex

    public class ek
    {
        public Integer key;//v
        public Integer value;//mass

        public ek(Integer k, Integer v)
        {
            this.key = k;
            this.value = v;
        }

        @Override
        public String toString()
        {
            String s = key + " : " + value;
            return s;
        }
    }

    public class massComparator implements Comparator<ek>
    {
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
    public class keyComparator implements Comparator<ek>
    {
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


    public Dijkstra(Graph G, int s, int f)
    {
        this.s = s;
        explored = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(s);
        dijk(G, s, f);
        assert check(G, s);
    }

    public void dij(Graph G, int s, int f)
    {
        ArrayList<ek> Q = new ArrayList<ek>();
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++){
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        explored[s] = true;
        Q.add(new ek(s, G.getmassofVertex(s)));
        massComparator mc = new massComparator();
        while (!Q.isEmpty() || (explored[f] == false)) {
            Collections.sort(Q, mc);
            ek ekvQ = Q.remove(0);
            for (int w : G.adj(ekvQ.key)) {
                if (!explored[w])
                {
                    edgeTo[w] = ekvQ.key;
                    distTo[w] = distTo[ekvQ.key] + 1;
                    explored[w] = true;
                    ek ekw = new ek(w, G.getmassofVertex(w));
                    Q.add(ekw);
                }
            }
        }
    }

    public void dijk(Graph G, int s, int f)
    {
        /*List<ek> QQ = new ArrayList<ek>();
        keyComparator kc = new keyComparator();
        ek ek1 = new ek(2, 1);
        ek ek2 = new ek(2, 3);
        ek ek3 = new ek(3, 1);
        ek ek4 = new ek(5, 1);
        ek ek5 = new ek(5, 3);
        QQ.add(ek1);
        QQ.add(ek2);
        QQ.add(ek3);
        QQ.add(ek4);
        int i = Collections.binarySearch(QQ, new ek(254,45), kc);
        System.out.println("********" + i);*/

        keyComparator kc = new keyComparator();
        List<ek> Q = new ArrayList<ek>();
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++){
            if (v != s) {
                distTo[v] = INFINITY;
            }
        }
        distTo[s] = 0;
        explored[s] = true;
        Q.add(new ek(s, G.getmassofVertex(s)));
        massComparator mc = new massComparator();
        while (!Q.isEmpty() || (explored[f] == false)) {
            Collections.sort(Q, mc);
            //Collections.reverse(Q);
            ek ekvQ = Q.remove(0);
            for (int w : G.adj(ekvQ.key)) {
                if (!explored[w])
                {
                   // System.out.println(Q.toString());
                    ek ekw = new ek(w, G.getmassofVertex(w));
                    int m = G.getmassofVertex(w);
                    //System.out.println(m);
                    int i1 = Collections.binarySearch(Q, new ek(w,1), kc);
                   // System.out.println(i1);
                    int i2 = Collections.binarySearch(Q, new ek(w,2), kc);
                    //System.out.println(i2);
                    int i3 = Collections.binarySearch(Q, new ek(w,3), kc);
                   // System.out.println(i3);
                    int i4 = Collections.binarySearch(Q, new ek(w,4), kc);
                    //System.out.println(i4);
                    if (i1 >= 0)
                    {
                        if (m < 1)
                        {
                            Q.remove(i1);
                            edgeTo[w] = ekvQ.key;
                            int iwm = distTo[ekvQ.key] + ekvQ.value;
                            distTo[w] = iwm;
                            explored[w] = true;
                            Q.add(ekw);
                        }
                    }
                    else if (i2 >= 0)
                    {
                        if (m < 2)
                        {
                            Q.remove(i2);
                            edgeTo[w] = ekvQ.key;
                            int iwm = distTo[ekvQ.key] + ekvQ.value;
                            distTo[w] = iwm;
                            explored[w] = true;
                            Q.add(ekw);
                        }

                    }
                    else if (i3 >= 0)
                    {
                        if (m < 3)
                        {
                            Q.remove(i3);
                            edgeTo[w] = ekvQ.key;
                            int iwm = distTo[ekvQ.key] + ekvQ.value;
                            distTo[w] = iwm;
                            explored[w] = true;
                            Q.add(ekw);
                        }

                    }
                    else if (i4 >= 0)
                    {
                        if (m < 4)
                        {
                            Q.remove(i4);
                            edgeTo[w] = ekvQ.key;
                            int iwm = distTo[ekvQ.key] + ekvQ.value;
                            distTo[w] = iwm;
                            explored[w] = true;
                            Q.add(ekw);
                        }
                    }
                    else
                    {
                        edgeTo[w] = ekvQ.key;
                        int iwm = distTo[ekvQ.key] + ekvQ.value;
                        distTo[w] = iwm;
                        explored[w] = true;
                        Q.add(ekw);
                    }
                    /*ek ek1 = new ek(w, 1);
                    ek ek2 = new ek(w, 2);
                    ek ek3 = new ek(w, 3);
                    ek ek4 = new ek(w, 4);
                    if (Q.contains(ek4))
                    {
                        System.out.println("ek2");
                    }*/

                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return explored[v];
    }
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = explored.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
            }
        }
    }
    private void validateVertex(int v) {
        int V = explored.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    private boolean check(Graph G, int s) {
        if (distTo[s] != 0) {
            System.out.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("distTo[" + v + "] = " + distTo[v]);
                    System.out.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }
        for (int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                System.out.println("shortest path edge " + v + "-" + w);
                System.out.println("distTo[" + v + "] = " + distTo[v]);
                System.out.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }
        return true;
    }
}
