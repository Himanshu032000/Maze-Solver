# Maze-Solver
This project provides Java implementation of Flood Fill Algorithm which is used for solving mazes and mazes consists in a matrix of square.
## Description of Flood Fill Algorithm
As the name suggest we can use the concept of Flood means we first divide the maze in the square grid and then assign the number in increasing order from destination to starting point just like water flow from higher level to lower level, we can use this property to reach to the destination from starting point.
As shown in figure(1) first we divide the maze in square grid and then number the destination to 0 and then increase by 1 and assign it to every adjacent block which has not assign any number yet, and do the same for rest of the part of maze assuming that there is no wall except the boundary.  
![Image Not Available](/Images/Flood_Fill1.png)<br> 
      **Figure 1**
![Image Not Available](/Images/Flood_Fill2.png)<br>
      **Figure 2**
![Image Not Available](/Images/Flood_Fill3.png)<br> 
      **Figure 3**
![Image Not Available](/Images/Flood_Fill4.png)<br> 
      **Figure 4**
We can only encounter the boundary if we have visited the block around the boundary which is shown by blue colour in above figures. We have to choose the next move to the block which has lower value than the current one and if the boundary is encountered between these two block then we’ll update the value of the current block by adding two into the value of previous visited block and then check the consistency and update the value of the required block to maintain consistency(figure(2) & figure(4)).
Maintain the consistency means we have to make sure that every block has the next block(no wall in between) which has the lower value then itself.    
