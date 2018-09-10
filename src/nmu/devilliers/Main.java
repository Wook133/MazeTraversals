package nmu.devilliers;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static nmu.devilliers.AStar.test;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        ArrayList<String> arrInput = new ArrayList<String>();
        ArrayList<Pair<Integer,Integer>> apath = new ArrayList<Pair<Integer,Integer>>();

        int[][] map2d;

        //test(1, 5, 6, 0, 0, 3, 2, new int[][]{{0,4},{2,2},{3,1},{3,3}});

//i is y
        // j is x
        try {
            arrInput = readTF("D:\\University\\2018\\Semester 1\\Capita Selecta\\Assignment1&2\\src\\nmu\\devilliers\\Maze7x10.txt");



           // System.out.println(ewdg1.toString());
            Graph g1 = new Graph(70);
            EdgeWeightedDigraph ewdg1 = new EdgeWeightedDigraph(70);
            ewdg1 = makeEdgeWeightedDigraph(arrInput);
            g1 = makeGraph(arrInput);
            //System.out.println(ewdg1);
            lPath = makePathBFS(3,66, g1);
            System.out.println();
            lPath = makePathDFS(3,66, g1);
            System.out.println();
            lPath = makePathNonRecursiveDFS(3,66, g1) ;
            System.out.println();
            lPath = makePathUCS(3,66, g1) ;
            System.out.println();
            lPath = makeDijkstraSP(3,66, ewdg1, g1) ;
            System.out.println();
            lPath = makePathNonDLS(3,66,3000, g1) ;
            System.out.println();
            int[][] map = blockedCoords(arrInput);
            apath = test(70, 10,7,0,3, 9, 3, map);
            map2d = mapper(7, 10);
            lPath.clear();
            lPath = aStarPathCost(apath, map2d);
            aPathCost(lPath, g1, 66);
            lPath.clear();

            arrInput = readTF("D:\\University\\2018\\Semester 1\\Capita Selecta\\Assignment1&2\\src\\nmu\\devilliers\\Maze11x20.txt");
            Graph g2 = new Graph(220);
            EdgeWeightedDigraph ewdg2 = new EdgeWeightedDigraph(220);
            g2 = makeGraph(arrInput);
            ewdg2 = makeEdgeWeightedDigraph(arrInput);
            lPath = makePathBFS(5,214, g2);
            System.out.println();
            lPath = makePathDFS(5,214, g2);
            System.out.println();
            lPath = makePathNonRecursiveDFS(5,214, g2);
            System.out.println();
            lPath = makePathUCS(5,214, g2) ;
            System.out.println();
            lPath = makeDijkstraSP(5,214, ewdg2, g2) ;
            System.out.println();
            lPath = makePathNonDLS(5,214,3000, g2) ;
            System.out.println();
            System.out.println();
           /* map = blockedCoords(arrInput);
            apath = test(220, 11,20,0,5, 19, 5, map);
            map2d = mapper(11, 20);
            lPath.clear();
            lPath = aStarPathCost(apath, map2d);
            aPathCost(lPath, g1, 214);
            lPath.clear();*/
            /*int[][] map = blockedCoords(arrInput);
            apath = test(220, 20,11,0,5, 19, 5, map);
            map2d = mapper(11, 20);
            lPath.clear();
            lPath = aStarPathCost(apath, map2d);
            aPathCost(lPath, g2, 214);
            lPath.clear();*/

            arrInput = readTF("D:\\University\\2018\\Semester 1\\Capita Selecta\\Assignment1&2\\src\\nmu\\devilliers\\Maze101x100.txt");
            //D:\University\2018\Semester 1\Capita Selecta\Assignment1&2\src\nmu\devilliers
            Graph g3 = new Graph(10100);
            g3 = makeGraph(arrInput);
            EdgeWeightedDigraph ewdg3 = new EdgeWeightedDigraph(10100);
            ewdg3 = makeEdgeWeightedDigraph(arrInput);
            //System.out.println(g);
            lPath = makePathBFS(50,10049, g3);
            System.out.println();
            lPath = makePathDFS(50,10049, g3);
            System.out.println();
            lPath = makePathNonRecursiveDFS(50,10049, g3);
            System.out.println();
            lPath = makePathUCS(50,10049, g3) ;
            System.out.println();
            lPath = makeDijkstraSP(50,10049, ewdg3, g3) ;
            System.out.println();
            //lPath = makePathNonDLS(50,10049,3000, g3) ;
            System.out.println();
            map = blockedCoords(arrInput);
            apath = test(10100, 100,101,0,50, 99, 50, map);
            map2d = mapper(101, 100);
            lPath.clear();
            lPath = aStarPathCost(apath, map2d);
            aPathCost(lPath, g3, 10049);
            lPath.clear();

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public static ArrayList<Integer> makePathBFS(int s, int f, Graph g)
    {
        System.out.println("BFS");
        long start = System.currentTimeMillis();
        BreadthFirstPaths bfs = new BreadthFirstPaths(g, s);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - start)) + " milliseconds");
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        if (bfs.hasPathTo(f)) {
            System.out.printf("%d to %d :  ", s, f);
            for (int x : bfs.pathTo(f))
            {
                lPath.add(x);
                if (x == s)
                {
                    System.out.print(x);
                }
                else
                {
                    System.out.print("-" + x);
                }
            }
            System.out.println();
            System.out.println("Length: " + (lPath.size() -1));
            System.out.println("Cost: " + pathCost(g, lPath));
        }
        System.out.println("________________________________________________________________________________________________________________________________");
        return lPath;
    }

    public static ArrayList<Integer> makePathDFS(int s, int f, Graph g)
    {
        System.out.println("DFS");
        long start = System.currentTimeMillis();
        DepthFirstPaths dfs = new DepthFirstPaths(g, s);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - start)) + " milliseconds");
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        if (dfs.hasPathTo(f)) {
            System.out.printf("%d to %d :  ", s, f);
            for (int x : dfs.pathTo(f))
            {
                lPath.add(x);
                if (x == s)
                {
                    System.out.print(x);
                }
                else
                {
                    System.out.print("-" + x);
                }
            }
            System.out.println("");
            System.out.println("Length: " + (lPath.size() -1));
            System.out.println("Cost: " + pathCost(g, lPath));
        }
        System.out.println("________________________________________________________________________________________________________________________________");
        return lPath;
    }

    public static ArrayList<Integer> makePathNonRecursiveDFS(int s, int f, Graph g)
    {
        System.out.println("Non-Recursive DFS");
        long start = System.currentTimeMillis();
        NonrecursiveDFS dfs = new NonrecursiveDFS(g, s);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - start)) + " milliseconds");
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        if (dfs.hasPathTo(f)) {
            System.out.printf("%d to %d :  ", s, f);
            for (int x : dfs.pathTo(f))
            {
                lPath.add(x);
                if (x == s)
                {
                    System.out.print(x);
                }
                else
                {
                    System.out.print("-" + x);
                }
            }
            System.out.println("");
            System.out.println("Length: " + (lPath.size() -1));
            System.out.println("Cost: " + pathCost(g, lPath));
        }
        System.out.println("________________________________________________________________________________________________________________________________");
        return lPath;
    }

    public static void aPathCost(ArrayList<Integer> lPath, Graph g, int f)
    {
        lPath.add(f);
        System.out.println("Cost: " + pathCost(g, lPath));
        System.out.println("Length: " + (lPath.size() -1));

        for (int i = 0; i <= lPath.size() - 1; i++)
        {
            if (lPath.get(i) == f)
            {
                System.out.print(lPath.get(i));
            }
            else
            {
                System.out.print(lPath.get(i) + "-");
            }

        }
        System.out.println();
        System.out.println("________________________________________________________________________________________________________________________________");
    }


    public static ArrayList<Integer> makePathNonDLS(int s, int f, int t, Graph g)
    {
        System.out.println("Depth Limited Search");
        long start = System.currentTimeMillis();
        int i = 0;
        bls dls = new bls(g, s, i);
        while ((i < t) || (dls.hasPathTo(f) == false)){
             dls = new bls(g, s, i);
             i++;
        }

        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - start)) + " milliseconds");
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        if (dls.hasPathTo(f)) {
            System.out.printf("%d to %d :  ", s, f);
            for (int x : dls.pathTo(f)) {
                lPath.add(x);
                if (x == s) {
                    System.out.print(x);
                } else {
                    System.out.print("-" + x);
                }
            }
            System.out.println("");
            System.out.println("Length: " + (lPath.size() - 1));
            System.out.println("Cost: " + pathCost(g, lPath));
        }
        System.out.println("________________________________________________________________________________________________________________________________");
        return lPath;
    }

    public static ArrayList<Integer> makePathUCS(int s, int f, Graph g)
    {
        System.out.println("Uniform Cost Search");
        long start = System.currentTimeMillis();
        Dijkstra ucs = new Dijkstra(g, s, f);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - start)) + " milliseconds");
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        if (ucs.hasPathTo(f)) {
            System.out.printf("%d to %d :  ", s, f);
            for (int x : ucs.pathTo(f))
            {
                lPath.add(x);
                if (x == s)
                {
                    System.out.print(x);
                }
                else
                {
                    System.out.print("-" + x);
                }
            }
            System.out.println("");
            System.out.println("Length: " + (lPath.size() -1));
            System.out.println("Cost: " + pathCost(g, lPath));
        }
        System.out.println("________________________________________________________________________________________________________________________________");
        return lPath;
    }

    public static ArrayList<Integer> makeDijkstraSP(int s, int f, EdgeWeightedDigraph g, Graph Normal)
    {
        System.out.println("DijkstraSP");
        long start = System.currentTimeMillis();
        DijkstraSP dsp = new DijkstraSP(g, s);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + ((end - start)) + " milliseconds");
        ArrayList<Integer> lPath = new ArrayList<Integer>();
        for (int t = f; t <= f; t++) {
            if (dsp.hasPathTo(f)) {
                System.out.printf("%d to %d : ", s, t);
                for (DirectedEdge e : dsp.pathTo(t)) {
                    lPath.add(e.from());
                    System.out.print(e.from()+ "-");
                }
                System.out.print(f);
                //System.out.println("");
            }
            else {
                System.out.printf("%d to %d         no path\n", s, t);
            }
            lPath.add(f);
        }
            System.out.println("");
            System.out.println("Cost DSP: " + dsp.distTo(f));
            System.out.println("Length: " + (lPath.size() -1));
            System.out.println("Cost: " + pathCostED(Normal, lPath));
        System.out.println("________________________________________________________________________________________________________________________________");
        return lPath;
    }

    public static int pathCost(Graph g, ArrayList<Integer> Path)
    {
        int icost = 0;

        for (int i = 0; i <= Path.size() - 1; i++)
        {
            icost = icost + g.getmassofVertex(Path.get(i));
            //System.out.println(g.getmassofVertex(Path.get(i)));
        }


      /*  for (int j = 0; j <= g.V - 1; j++)
        {
            System.out.println(j + " : " + g.getmassofVertex(j));
        }*/

        return icost;
    }

    public static int pathCostED(Graph g, ArrayList<Integer> Path)
    {
        int icost = 0;

        for (int i = 0; i <= Path.size() - 1; i++)
        {
            icost = icost + g.getmassofVertex(Path.get(i));
            //System.out.println(g.getmassofVertex(Path.get(i)));
        }


      /*  for (int j = 0; j <= g.V - 1; j++)
        {
            System.out.println(j + " : " + g.getmassofVertex(j));
        }*/

        return icost;
    }

    public static ArrayList<String> readTF(String sin) throws FileNotFoundException {
        String sout = "";
        //sin = "X:\\University\\2018\\Semester 1\\Capita Selecta\\src\\nmu\\wrap302\\devilliers\\Maze7x10.txt";
        ArrayList<String> arrS = new ArrayList<String>();
        // pass the path to the file as a parameter
        try {
            File file = new File(sin);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                sout = sc.nextLine();
                //System.out.println(sout);
                arrS.add(sout);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return arrS;
    }

    public static int[][] weights(ArrayList<String> arrIN, int x, int y)
    {
        int[][] TwoDWeights = new int[x][y];
        int icount = 0;
        for (int i = 0; i <= arrIN.size() - 1; i ++)
        {
            String scur = arrIN.get(i);
            for (int j = 0; j <= scur.length() - 1; j++) {
                Character cur = scur.charAt(j);
                TwoDWeights[j][i] = Character.getNumericValue(cur);
               // System.out.print(TwoDWeights[j][i] );
            }
             // System.out.println();
        }
        return TwoDWeights;
    }
    public static int[][] mapper(int x, int y)
    {
        int[][] TwoDimensions = new int[x][y];
        int icount = 0;
        for (int i = 0; i <= y - 1; i++)
        {
            for (int j = 0; j <= x - 1; j++)
            {
                TwoDimensions[j][i] = icount;
                icount++;
                //System.out.print(TwoDimensions[j][i] + " ");
            }
             // System.out.println();
        }
        return TwoDimensions;
    }
    public static Graph makeGraph(ArrayList<String> arrS)
    {
        int iheight = 0;
        iheight = arrS.size();
        int iwidth = 0;
        //System.out.println(arrS.get(0).charAt(3));
        iwidth = arrS.get(0).length();
        System.out.println("X: " + iwidth);
        System.out.println("Y: " + iheight);
        int icount =iwidth * iheight;
        Graph g = new Graph(icount);
        int k = 0;

        int[][] TwoDimensions = mapper(iwidth, iheight);
        int[][] Weights = weights(arrS, iwidth, iheight) ;
        g.addEdge((iwidth / 2), ((iwidth/2)+iwidth));
        g.addEdge((icount-1) - ((iwidth / 2)), (icount-1) - ((iwidth/2)+iwidth));
        // g.addEdge()//9950 - > 10050
        g.setmassofVertex((iwidth / 2), 1);
        g.setmassofVertex((icount-1) - ((iwidth / 2)), 1);


        for (int y = 1; y <= iheight - 2; y++)
        {
            String scur = arrS.get(y);
            String sUp = arrS.get(y-1);
            String sDown = arrS.get(y+1);
            //System.out.println(scur);
            for (int x = 1; x <= iwidth - 2; x++)
            {
                char cUp = sUp.charAt(x);
                char cDown = sDown.charAt(x);

                char cLeft = scur.charAt(x-1);
                char cRight = scur.charAt(x+1);

                char ccur = scur.charAt(x);

                int ipos = TwoDimensions[x][y];
                //int iup = TwoDimensions[x][y-1];
                int idown = TwoDimensions[x][y+1];
                //int ileft = TwoDimensions[x-1][y];
                int iright = TwoDimensions[x+1][y];

                g.setmassofVertex(ipos, Character.getNumericValue(ccur));

                if (ccur != '0') {
                    /*if (cUp != '0')
                    {
                        g.addEdge(ipos, iup);
                        //g.addEdge(iup, ipos);
                    }*/
                    if (cDown != '0')
                    {
                        g.addEdge(ipos, idown);


                        //g.addEdge(iup, ipos);

                    }
                    /*if (cLeft != '0')
                    {
                        g.addEdge(ipos, ileft);
                        //g.addEdge(ileft, ipos);
                    }*/
                    if (cRight != '0')
                    {
                        g.addEdge(ipos, iright);
                        //g.addEdge(iright, ipos);
                    }
                }
            }
        }
        return g;
    }

    public static ArrayList<Integer> aStarPathCost(ArrayList<Pair<Integer,Integer>> apath, int[][] TwoDmap)
    {
        ArrayList<Integer> arrOut = new ArrayList<Integer>();
        for (int i = 0; i <= apath.size() - 1; i++) {
            Pair<Integer, Integer> pairCur = apath.get(i);
            int x = pairCur.getKey();
            int y = pairCur.getValue();
            arrOut.add(TwoDmap[x][y]);
        }
        return arrOut;
    }


    public static EdgeWeightedDigraph makeEdgeWeightedDigraph(ArrayList<String> arrS)
    {
        int iheight = 0;
        iheight = arrS.size();
        int iwidth = 0;
        iwidth = arrS.get(0).length();
        System.out.println("X: " + iwidth);
        System.out.println("Y: " + iheight);
        int icount =iwidth * iheight;
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(icount);
        int k = 0;
        int[][] TwoDimensions = mapper(iwidth, iheight);
        int[][] Weights = weights(arrS, iwidth, iheight);


        g.addEdge(new DirectedEdge((iwidth / 2), ((iwidth/2)+iwidth), 1));
        //DirectedEdge de = new
        g.addEdge((new DirectedEdge((icount-1) - ((iwidth / 2)), (icount-1) - ((iwidth/2)+iwidth), 1)));

        for (int y = 1; y <= iheight - 2; y++)
        {
            String scur = arrS.get(y);
            String sUp = arrS.get(y-1);
            String sDown = arrS.get(y+1);
            //System.out.println(scur);
            for (int x = 1; x <= iwidth - 2; x++)
            {
                char cUp = sUp.charAt(x);
                char cDown = sDown.charAt(x);
                char cLeft = scur.charAt(x-1);
                char cRight = scur.charAt(x+1);
                char ccur = scur.charAt(x);
                int ipos = TwoDimensions[x][y];
                int iup = TwoDimensions[x][y-1];
                int idown = TwoDimensions[x][y+1];
                int ileft = TwoDimensions[x-1][y];
                int iright = TwoDimensions[x+1][y];

                if (ccur != '0') {
                    if (cUp != '0')
                    {
                        g.addEdge(new DirectedEdge(ipos, iup, Character.getNumericValue(cUp)));
                    }
                    if (cDown != '0')
                    {
                        g.addEdge(new DirectedEdge(ipos, idown, Character.getNumericValue(cDown)));
                    }
                    if (cLeft != '0')
                    {
                        g.addEdge(new DirectedEdge(ipos, ileft, Character.getNumericValue(cLeft)));
                        //g.addEdge(ileft, ipos);
                    }
                    if (cRight != '0')
                    {
                        g.addEdge(new DirectedEdge(ipos, iright, Character.getNumericValue(cRight)));
                    }
                }
            }
        }
        return g;
    }

    public static int[][] Blockedmapper(int x, int y, ArrayList<String> arrS)
    {
        int[][] TwoDimensions = new int[y][x];
        int icount = 0;
        int inum= 0;
        //System.out.println("X: " + x);
        //System.out.println("Y: " + y);
        for (int i = 0; i <= y - 1; i++)
        {
            for (int j = 0; j <= x - 1; j++)
            {
                char scur = arrS.get(i).charAt(j);
                //System.out.print(scur);
                if (scur == '0')
                {
                    TwoDimensions[i][j] = 0;
                }
                else
                {
                    TwoDimensions[i][j] = 1;
                }
                //icount++;
                //System.out.print(TwoDimensions[i][j] + " ");
            }
             //System.out.println();
        }
        return TwoDimensions;
    }

    public static int[][] blockedCoords(ArrayList<String> arrS)
    {
        int iheight = 0;
        iheight = arrS.size();
        int iwidth = 0;
        //System.out.println(arrS.get(0).charAt(3));
        iwidth = arrS.get(0).length();
       // System.out.println(arrS);
        //System.out.println("X: " + iwidth);
        //System.out.println("Y: " + iheight);
        int icount =iwidth * iheight;
        //Graph g = new Graph(icount);
        int k = 0;
        int[][] blocked = Blockedmapper(iwidth, iheight, arrS);


        return blocked;
    }







}
