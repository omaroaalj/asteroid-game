package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */
// Balls that appear once spacebar is pressed and can make asteroids disappear
import java.awt.Color;
import java.awt.Graphics;

public class Ball extends Circle {

   private double rotation;
   private static final int RADIUS = 10;
   public boolean fireBall = false;
	
   public Ball(Point center, double rotation) {
      super(center, RADIUS);
      this.rotation = rotation;
   }

   @Override
   public void paint(Graphics brush, Color color) {
      brush.setColor(color);
      brush.fillOval((int)center.x, (int)center.y, RADIUS, RADIUS);
   }

   @Override
   public void move() {
      center.x += 2 * Math.cos(Math.toRadians(rotation));
      center.y += 2 * Math.sin(Math.toRadians(rotation));
   }
	
   public boolean outOfBounds() {
      if (center.y < 0 || center.y > Asteroids.SCREEN_HEIGHT || center.x < 0 || center.x > Asteroids.SCREEN_WIDTH)
         return true;
      else
         return false;
   }
	
   public Point getCenter() {
      return center;
   }

}
