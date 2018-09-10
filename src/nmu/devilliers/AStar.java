package nmu.devilliers;
import javafx.util.Pair;

import java.util.*;
/**
 * Created by scruf on 05-Mar-18.
 */
public class AStar {
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    static class vertex{
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        vertex parent;

        vertex(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "["+this.j+", "+this.i+"]";
        }
    }
    //Blocked cells are just null Cell values in grid
    static vertex [][] grid = new vertex[5][5];

    static PriorityQueue<vertex> open;

    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;

    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }

    public static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }

    public static void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }

    static void checkAndUpdateCost(vertex current, vertex t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;

        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }

    public static void AStar(){

        //add the start location to open list.
        open.add(grid[startI][startJ]);

        vertex current;

        while(true){
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true;

            if(current.equals(grid[endI][endJ])){
                return;
            }

            vertex t;
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);

                /*if(current.j-1>=0){
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }*/
            }

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);

                /*if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }*/
            }
        }
    }

    /*
   Params :
   tCase = test case No.
   x, y = Board's dimensions
   si, sj = start location's x and y coordinates
   ei, ej = end location's x and y coordinates
   int[][] blocked = array containing inaccessible cell coordinates
   */
    public static ArrayList<Pair<Integer,Integer>> test(int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
        //System.out.println("\n\nTest Case #"+tCase);
        //Reset
        System.out.println("A* Search");
        long start = System.currentTimeMillis();
        ArrayList<Pair<Integer,Integer>> lpath = new ArrayList<Pair<Integer,Integer>>();


        grid = new vertex[x][y];
        closed = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            vertex c1 = (vertex)o1;
            vertex c2 = (vertex)o2;

            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
        });
        //Set start position
        setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part

        //Set End Location
        setEndCell(ei, ej);

        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                grid[i][j] = new vertex(i, j);
                grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//                  System.out.print(grid[i][j].heuristicCost+" ");
            }
//              System.out.println();
        }
        grid[si][sj].finalCost = 0;
           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
             setBlocked(i, j);
           */
       // System.out.println("4");
       // System.out.println("X : " + x);
       // System.out.println("Y : " + y);
        for(int i = 0; i <= x - 1; i++){
            //System.out.print("i" + i);
            for(int j = 0; j <= y-1; j++){
                //System.out.print(blocked[i][j]);
                if (blocked[i][j] == 0) {
                    //setBlocked(blocked[b][0], blocked[b][1]);
                    //System.out.println("");
                    setBlocked(i, j);
                }
            }
            //System.out.println("-----------------------------------------------------------------------------------------------");

        }

        //Display initial map
        //System.out.println("Grid: ");
       /* System.out.print("     ");
        for (int a = 0; a < y; ++a)
        {
            System.out.print(a + "    ");
        }
        System.out.println("");
        for(int i=0;i<x;++i){
            System.out.print(i + "    ");
            for(int j=0;j<y;++j){
                if(i==si&&j==sj)System.out.print("S    "); //Source
                else if(i==ei && j==ej)System.out.print("D    ");  //Destination
                else if(grid[i][j]!=null)System.out.print("0    ");
                else System.out.print("B    ");
            }
            System.out.println();
        }
        System.out.println();*/
AStar();
        /*System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){//May beed change
            for(int j=0;j<y;++j){
                if(grid[i][j]!=null)System.out.print(grid[i][j].finalCost + "    ");
                else System.out.print("B    ");
            }

            System.out.println();
        }
        System.out.println();
       /*System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();*/
        int icount = 0;
        //System.out.println("-----------------------------------------------------------------------------------------------");
        if(closed[endI][endJ]){
            //Trace back the path
            System.out.println("Path: ");
            vertex current = grid[endI][endJ];
            System.out.print(current);
            while(current.parent!=null){
                icount++;
                Pair<Integer, Integer> pairCur = new Pair<Integer, Integer>(current.parent.j, current.parent.i);
                lpath.add(pairCur);
                //System.out.print(" -> "+current.parent);
                current = current.parent;
            }
            System.out.println();
        }else System.out.println("No possible path");
        long end = System.currentTimeMillis();

        System.out.println("Time: " + ((end - start)) + " milliseconds");
        Collections.reverse(lpath);
    return lpath;
    }


}
