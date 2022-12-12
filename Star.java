package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */

import java.awt.Color;
import java.awt.Graphics;

public class Star extends Circle {
	
   public Star(Point center, int radius) {
      super(center, radius);
   }

   @Override
   public void paint(Graphics brush, Color color) {
      brush.setColor(color);
      brush.fillOval((int)center.x, (int)center.y, radius*2, radius*2);
   }
	
   @Override
   public void move() {
      return;
   }

}
