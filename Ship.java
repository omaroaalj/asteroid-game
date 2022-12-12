package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/* Omar Al-Jaafari
 * 4/14/2021
 * CMPT 202
 * Homework 5 (Milestone 2)
 */

//import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;

public class Ship extends Polygon implements KeyListener {

   public static final int SHIP_WIDTH = 40;
   public static final int SHIP_HEIGHT = 25;
   private boolean forward, left, right, fireBall, oneBallFired = false;
   private ArrayList<Ball> balls = new ArrayList<Ball>();
   public int shipLives = 5;
	
   public Ship(Point[] inShape, Point inPosition, double inRotation) {
      super(inShape, inPosition, inRotation);
   }
	
   @Override
   public void paint(Graphics brush, Color color) {
      Point[] points = getPoints();
      int[] xPoints = new int[points.length];
      int[] yPoints = new int[points.length];
   
      for (int i = 0; i < points.length; i++) {
         xPoints[i] = (int)points[i].x;
         yPoints[i] = (int)points[i].y;
      }
   
      brush.setColor(color);
      brush.fillPolygon(xPoints, yPoints, points.length);
   }
	
   @Override
   public void move() {
      if (position.x < Asteroids.SCREEN_WIDTH && position.y < Asteroids.SCREEN_HEIGHT && position.x > 0 && position.y > 0) {
         if (forward) {
            position.x += 3 * Math.cos(Math.toRadians(rotation));
            position.y += 3 * Math.sin(Math.toRadians(rotation));
         }
         if (left) {
            rotate(-3);
         }
         if (right) {
            rotate(3);
         }
         if (fireBall && !oneBallFired) {
            balls.add(new Ball(getPoints()[3], rotation));
            oneBallFired = true;
         }
      } else if (position.x >= Asteroids.SCREEN_WIDTH) {
         position.x = 1;
      } else if (position.y >= Asteroids.SCREEN_HEIGHT) {
         position.y = 1;
      } else if (position.x <= 0) {
         position.x = Asteroids.SCREEN_WIDTH - 1;
      } else if (position.y <= 0) {
         position.y = Asteroids.SCREEN_HEIGHT - 1;
      }
   	
   
   }
   public int getLives() {
      return shipLives;
   }
	
   public void loseLife() {
      shipLives--;
   }
	
   public ArrayList<Ball> getBalls() {
      return balls;
   }

   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyPressed(KeyEvent e) {
   	
      if (e.getKeyCode() == KeyEvent.VK_UP) {
         forward = true;
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
         left = true;
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
         right = true;
      }
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
         fireBall = true;
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {
   	
      if (e.getKeyCode() == KeyEvent.VK_UP) {
         forward = false;
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
         left = false;
      }
      if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
         right = false;
      }
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
         fireBall = false;
         oneBallFired = false;
      	
      }
   
   }

}
