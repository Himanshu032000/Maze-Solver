/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Himanshu
 */
public class MazeSolver {

    int size;
    public int MBlockV[][];
    public Boolean MBlockLW[][], MBlockRW[][], MBlockUW[][], MBlockDW[][], MBVisit[][];
    int i, j, l, r, u, d, temp;
    int value = 0;

    public MazeSolver(int size) {
        this.size = size;
        MBlockV = new int[size][size];
        MBlockLW = new Boolean[size][size];
        MBlockRW = new Boolean[size][size];
        MBlockUW = new Boolean[size][size];
        MBlockDW = new Boolean[size][size];
        MBVisit = new Boolean[size][size];
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                MBlockV[i][j] = -1;
                MBVisit[i][j] = false;
                if (i == 0 & j == 0) {
                    MBlockLW[i][j] = true;
                    MBlockUW[i][j] = true;
                    MBlockRW[i][j] = false;
                    MBlockDW[i][j] = false;
                } else if (i == 0 & j == size - 1) {
                    MBlockRW[i][j] = true;
                    MBlockUW[i][j] = true;
                    MBlockLW[i][j] = false;
                    MBlockDW[i][j] = false;
                } else if (j == 0 & i == size - 1) {
                    MBlockLW[i][j] = true;
                    MBlockDW[i][j] = true;
                    MBlockRW[i][j] = false;
                    MBlockUW[i][j] = false;
                } else if (i == size - 1 & j == size - 1) {
                    MBlockRW[i][j] = true;
                    MBlockDW[i][j] = true;
                    MBlockLW[i][j] = false;
                    MBlockUW[i][j] = false;
                } else if (i == 0) {
                    MBlockUW[i][j] = true;
                    MBlockLW[i][j] = false;
                    MBlockRW[i][j] = false;
                    MBlockDW[i][j] = false;
                } else if (j == 0) {
                    MBlockLW[i][j] = true;
                    MBlockDW[i][j] = false;
                    MBlockRW[i][j] = false;
                    MBlockUW[i][j] = false;
                } else if (i == size - 1) {
                    MBlockDW[i][j] = true;
                    MBlockLW[i][j] = false;
                    MBlockRW[i][j] = false;
                    MBlockUW[i][j] = false;
                } else if (j == size - 1) {
                    MBlockRW[i][j] = true;
                    MBlockLW[i][j] = false;
                    MBlockDW[i][j] = false;
                    MBlockUW[i][j] = false;
                } else {
                    MBlockLW[i][j] = false;
                    MBlockRW[i][j] = false;
                    MBlockUW[i][j] = false;
                    MBlockDW[i][j] = false;
                }
            }
        }
    }

    //initialize n x n grid to it's default value
    public void intialize(int des_x, int des_y) {
        Queue<Integer> q = new LinkedList<>();
        l = des_y;
        r = des_y;
        u = des_x;
        d = des_x;
        temp = value;
        while (u >= 0 | d < size) {
            while (l >= 0 | r < size) {
                i = l;
                j = u;
                if (l >= 0 & u >= 0) {
                    MBlockV[i][j] = value;
                }
                i = r;
                j = u;
                if (r < size & u >= 0) {
                    MBlockV[i][j] = value;
                }
                value++;
                l--;
                r++;
            }
            value = temp;
            l = des_y;
            r = des_y;
            while (l >= 0 | r < size) {
                i = l;
                j = d;
                if (l >= 0 & d < size) {
                    MBlockV[i][j] = value;
                }
                i = r;
                j = d;
                if (r < size & d < size) {
                    MBlockV[i][j] = value;
                }
                value++;
                l--;
                r++;
            }
            u--;
            d++;
            l = des_y;
            r = des_y;
            value = ++temp;
        }
    }

    public ArrayList<Integer> pathx = new ArrayList<>();
    public ArrayList<Integer> pathy = new ArrayList<>();
    int nexti, nextj;
    int nextv, prev;
    Boolean destination = false;

    //find minimum adjacent cell for next step
    private int min(MazeSolver Maze, int i, int j) {
        int next = 9999;
        int l, r, u, d;
        if (Maze.MBlockLW[i][j] == true) {
            l = 9999;
        } else {
            l = Maze.MBlockV[i][j - 1];
        }
        if (Maze.MBlockRW[i][j] == true) {
            r = 9999;
        } else {
            r = Maze.MBlockV[i][j + 1];
        }
        if (Maze.MBlockUW[i][j] == true) {
            u = 9999;
        } else {
            u = Maze.MBlockV[i - 1][j];
        }
        if (Maze.MBlockDW[i][j] == true) {
            d = 9999;
        } else {
            d = Maze.MBlockV[i + 1][j];
        }
        if (l < next) {
            next = l;
            nexti = i;
            nextj = j - 1;
        }
        if (u < next) {
            next = u;
            nexti = i - 1;
            nextj = j;
        }
        if (r < next) {
            next = r;
            nexti = i;
            nextj = j + 1;
        }
        if (d < next) {
            next = d;
            nexti = i + 1;
            nextj = j;
        }
        return next;
    }

    //update cell values to it's new 
    private void Update(MazeSolver Maze, int i, int j) {
        int count = 0;
        int r, c;
        int min;
        Stack<Integer> x = new Stack<>();
        Stack<Integer> y = new Stack<>();
        if (Maze.MBlockLW[i][j] == false) {
            x.add(i);
            y.add(j - 1);
            Maze.MBVisit[i][j - 1] = true;
            count++;
        }
        if (Maze.MBlockRW[i][j] == false) {
            x.add(i);
            y.add(j + 1);
            Maze.MBVisit[i][j + 1] = true;
            count++;
        }
        if (Maze.MBlockUW[i][j] == false) {
            x.add(i - 1);
            y.add(j);
            Maze.MBVisit[i - 1][j] = true;
            count++;
        }
        if (Maze.MBlockDW[i][j] == false) {
            x.add(i + 1);
            y.add(j);
            Maze.MBVisit[i + 1][j] = true;
            count++;
        }
        while (count != 0) {
            r = x.pop();
            c = y.pop();
            count--;
            min = min(Maze, r, c);
            if (min >= Maze.MBlockV[r][c]) {
                Maze.MBlockV[r][c] = min + 1;
                if (Maze.MBlockLW[r][c] == false && Maze.MBVisit[r][c - 1] != true) {
                    x.add(r);
                    y.add(c - 1);
                    Maze.MBVisit[r][c - 1] = true;
                    count++;
                }
                if (Maze.MBlockRW[r][c] == false && Maze.MBVisit[r][c + 1] != true) {
                    x.add(r);
                    y.add(c + 1);
                    Maze.MBVisit[r][c + 1] = true;
                    count++;
                }
                if (Maze.MBlockUW[r][c] == false && Maze.MBVisit[r - 1][c] != true) {
                    x.add(r - 1);
                    y.add(c);
                    Maze.MBVisit[r - 1][c] = true;
                    count++;
                }
                if (Maze.MBlockDW[r][c] == false && Maze.MBVisit[r + 1][c] != true) {
                    x.add(r + 1);
                    y.add(c);
                    Maze.MBVisit[r + 1][c] = true;
                    count++;
                }
            }
        }

    }

    //find path from source to destination
    public void path(MazeSolver Maze, int i, int j) {
        int temp, no = 0;

        prev = Maze.MBlockV[i][j];
        pathx.add(i);
        pathy.add(j);
        while (!destination) {

            nextv = min(Maze, i, j);
            i = nexti;
            j = nextj;
            if (prev < nextv) {
                Maze.MBlockV[i][j] = prev + 2;
                Maze.MBVisit[i][j] = true;
                Update(Maze, i, j);
                for (int a = 0; a < Maze.size; a++) {
                    for (int b = 0; b < Maze.size; b++) {
                        Maze.MBVisit[a][b] = false;
                    }
                }
            }

            pathx.add(i);
            pathy.add(j);
            prev = nextv;
            no = 0;
            if (Maze.MBlockDW[i][j] == true) {
                no++;
            }
            if (Maze.MBlockUW[i][j] == true) {
                no++;
            }
            if (Maze.MBlockLW[i][j] == true) {
                no++;
            }
            if (Maze.MBlockRW[i][j] == true) {
                no++;
            }
            temp = pathx.size() - 2;
            if (no == 3 && nextv != 0) {
                i = pathx.get(temp);
                j = pathy.get(temp);
                pathx.add(i);
                pathy.add(j);
                no = min(Maze, i, j);
                if (nexti > i) {
                    Maze.MBlockDW[i][j] = true;
                } else if (nexti < i) {
                    Maze.MBlockUW[i][j] = true;
                } else if (nextj > j) {
                    Maze.MBlockRW[i][j] = true;
                } else if (nextj < j) {
                    Maze.MBlockLW[i][j] = true;
                }

                prev = Maze.MBlockV[i][j];
            }
            if (nextv == 0) {
                destination = true;
            }
        }

    }

    //print path find by algorithm
    public void PrintPath() {
        for (int i = 0; i < pathx.size(); i++) {
            System.out.println("(" + pathx.get(i) + "," + pathy.get(i) + ")");
        }
    }

}
