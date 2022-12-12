package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */

/**
 * Shape.java
 * 
 * Generic Shape object
 */
import java.awt.*;
public interface Shape {
   public void paint(Graphics brush, Color color);
	
   public void move();
}