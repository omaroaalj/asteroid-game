package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */

/*
CLASS: Point
DESCRIPTION: Ah, if only real-life classes were this straight-forward. We'll
             use 'Point' throughout the program to store and access 
             coordinates.
Original code by Dan Leyzberg and Art Simon
 */

public class Point implements Cloneable {
   double x,y;
   public Point(double inX, double inY) { x = inX; y = inY; }

   public Point clone() {
      return new Point(x, y);
   }
}
