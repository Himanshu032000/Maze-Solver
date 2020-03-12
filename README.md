# Maze-Solver
This project provides Java implementation of Flood Fill Algorithm which is used for solving mazes and mazes consists in a matrix of square.
## Description of Flood Fill Algorithm
As the name suggest we can use the concept of Flood means we first divide the maze in the square grid and then assign the number in increasing order from destination to starting point just like water flow from higher level to lower level, we can use this property to reach to the destination from starting point.
As shown in figure(1) first we divide the maze in square grid and then number the destination to 0 and then increase by 1 and assign it to every adjacent block which has not assign any number yet, and do the same for rest of the part of maze assuming that there is no wall except the boundary.  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;![Image Not Available](/Images/Flood_Fill1.png)&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;![Image Not Available](/Images/Flood_Fill2.png)&ensp;<br>
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;**Figure 1** 
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;**Figure 2**                   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;![Image Not Available](/Images/Flood_Fill3.png)&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;![Image Not Available](/Images/Flood_Fill4.png)&ensp;<br>
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;**Figure 3**
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;**Figure 4**<br>
We can only encounter the boundary if we have visited the block around the boundary which is shown by blue colour in above figures. We have to choose the next move to the block which has lower value than the current one and if the boundary is encountered between these two block then we’ll update the value of the current block by adding two into the value of previous visited block and then check the consistency and update the value of the required block to maintain consistency(figure(2) & figure(4)).
Maintain the consistency means we have to make sure that every block has the next block(no wall in between) which has the lower value then itself.    
## Running Instructions
For Running the code first we have to give the image path in Gui.java file which is present at MazeSolver/src/mazesolver/Gui.java .
Give following path of maze image which is at line 79 of Gui.java file .
        
                                image = ImageIO.read(new File(".\\Images\\maze-20x20.png"));
Then give the path of mouseL and mouseR image present at line number 83 and 84 in Gui.java file.

                                mouseL = ImageIO.read(new File(".\\Images\\mouseL.jpg"));
                                mouseR = ImageIO.read(new File(".\\Images\\mouseR.jpg"));

## Implementation of Flood Fill Algorithm
For the implementation of this algorithm we have created following methods:

                                public void intialize(int des_x, int des_y)
                                
This method initialize each and every cell with a number starting from destination coordinates as already explained in flood fill algorithm. This method takes x and y coordinates of the destination as its parameters.  

                                private int min(MazeSolver Maze, int i, int j)
                                
This method gives the minimum value from the given available direction from the current position which is very useful in finding the correct destination. 

                                private void Update(MazeSolver Maze, int i, int j)
                                
This method updates the values of each cell at every step whenever the consistency of the algorithm breaks. The consistency of the flood fill should be maintained at each and every step. This consistency is maintained by the update() method. 

                                public void path(MazeSolver Maze, int i, int j)
                                
This method gives the path from source to destination using min() and update() method and also this method is the actual implementation of flood fill algorithm.

                                public void findwall()
                                
This method scan the image of maze and gives if the wall is present or not. This can be done by comparing RGB values of the path and RGB value of point at which wall is to be detected.

                                public void paint(Graphics g)
                                
This method shows how actual graphical representation of trajectory from source to destination.                                
