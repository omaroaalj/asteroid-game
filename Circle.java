package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */

/**
 * Simple representation of a Circle.
 * 
 */
import java.awt.*;

public abstract class Circle implements Shape {
   protected Point center;
   protected int radius;

   public Circle(Point center, int radius) {
      this.center = center;
      this.radius = radius;
   }

   public abstract void paint(Graphics brush, Color color);

   public abstract void move();
}
