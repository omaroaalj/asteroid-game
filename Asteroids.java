package asteroid;

/* Omar Al-Jaafari
 * 4/23/2021
 * CMPT 202
 * Homework 5 (Milestone 4)
 */

/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */

import java.awt.*;
import java.util.*;

public class Asteroids extends Game {
   public static final int SCREEN_WIDTH = 800;
   public static final int SCREEN_HEIGHT = 600;

   private boolean collision = false;
   private static int collisionCounter = 100;
   static int counter = 0;
	
   private boolean displayStar = false;
   private Random r = new Random();
   private static final int MAX_INTERVAL = 15;
   private int colorSwitchInterval = r.nextInt(MAX_INTERVAL);

   private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
   private Star[] stars;
	
   private Ship ship;
   private ArrayList<Ball> balls = new ArrayList<Ball>();
   private ArrayList<Ball> ballsToRemove = new ArrayList<Ball>();
   private ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();

   public Asteroids() {
      super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
      this.setFocusable(true);
      this.requestFocus();
   
   	// create a number of random asteroid objects
      randomAsteroids = createRandomAsteroids(10,60,30);
   	
   	// create the ship
      ship = createShip();
      this.addKeyListener(ship);
   	
   	// create stars
      stars = createStars(100, 3);
   }
	
	// private helper method to create the Ship
   private Ship createShip() {
        // Look of ship
      Point[] shipShape = {
                new Point(0, 0),
                new Point(Ship.SHIP_WIDTH/3.5, Ship.SHIP_HEIGHT/2),
                new Point(0, Ship.SHIP_HEIGHT),
                new Point(Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT/2)
         };
        // Set ship at the middle of the screen
      Point startingPosition = new Point((width -Ship.SHIP_WIDTH)/2, (height - Ship.SHIP_HEIGHT)/2);
      int startingRotation = 0;
        
      return new Ship(shipShape, startingPosition, startingRotation);
   }

	//  Create an array of random asteroids
   private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
   		int minAsteroidWidth) {
      java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);
   
      for(int i = 0; i < numberOfAsteroids; ++i) {
      	// Create random asteroids by sampling points on a circle
      	// Find the radius first.
         int radius = (int) (Math.random() * maxAsteroidWidth);
         if(radius < minAsteroidWidth) {
            radius += minAsteroidWidth;
         }
      	// Find the circles angle
         double angle = (Math.random() * Math.PI * 1.0/2.0);
         if(angle < Math.PI * 1.0/5.0) {
            angle += Math.PI * 1.0/5.0;
         }
      	// Sample and store points around that circle
         ArrayList<Point> asteroidSides = new ArrayList<Point>();
         double originalAngle = angle;
         while(angle < 2*Math.PI) {
            double x = Math.cos(angle) * radius;
            double y = Math.sin(angle) * radius;
            asteroidSides.add(new Point(x, y));
            angle += originalAngle;
         }
      	// Set everything up to create the asteroid
         Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
         Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
         double inRotation = Math.random() * 360;
         asteroids.add(new Asteroid(inSides, inPosition, inRotation));
      }
      return asteroids;
   }

   public void paint(Graphics brush) {
      brush.setColor(Color.black);
      brush.fillRect(0,0,width,height);
   
   	// sample code for printing message for debugging
   	// counter is incremented and this message printed
   	// each time the canvas is repainted
      counter++;
      brush.setColor(Color.white);
      brush.drawString("Counter is " + counter,10,10);
   	
   	// display ship lives
      brush.drawString("Ship Lives: " + ship.getLives(), 10, 25);
   
   	// display the random asteroids
      for (Asteroid asteroid : randomAsteroids) {
         asteroid.paint(brush,Color.white);
         asteroid.move();
         if (!collision) {
            collision = asteroid.collision(ship);
         }
      }
   	
      for (Star star : stars) {
         colorSwitchInterval--;
         if (colorSwitchInterval <= 0) {
            if (displayStar) {
               displayStar = false;
               colorSwitchInterval = r.nextInt(MAX_INTERVAL);
            } else {
               displayStar = true;
               colorSwitchInterval = r.nextInt(MAX_INTERVAL);
            }
         }
      	
         if (displayStar)
            star.paint(brush, Color.white);
         else
            star.paint(brush, Color.black);
      }
   	
   	/**
   	 * The above for loop (known as a "for each" loop)
   	 * is equivalent to what is shown below.
   	 */
   
   	/**
   	for (int i = 0; i < randomAsteroids.size(); i++) {
   		randomAsteroids.get(i).paint(brush, Color.white);
   		randomAsteroids.get(i).move();
   
   	}
   	*/
   	
   	// have the ship appear on the screen
      if (collision) {
         ship.paint(brush, Color.yellow);
         collisionCounter--;
         if (collisionCounter <= 0) {
            ship.loseLife();
            collision = false;
            collisionCounter = 100;
         }
      }
      else {
         ship.paint(brush, Color.red);
      }
   	
      ship.move();
   	
      balls = ship.getBalls();
   	
      for (Ball ball : balls) {
         ball.paint(brush, Color.red);
         ball.move();
         if (ball.outOfBounds())
            ballsToRemove.add(ball);
         for (Asteroid asteroid : randomAsteroids) {
            if (asteroid.contains(ball.getCenter())) {
               asteroidsToRemove.add(asteroid);
               ballsToRemove.add(ball);
            }
         }
      }
   	
      if (!asteroidsToRemove.isEmpty()) {
         randomAsteroids.removeAll(asteroidsToRemove);
         asteroidsToRemove.clear();
      }
   	
      if (!ballsToRemove.isEmpty()) {
         balls.removeAll(ballsToRemove);
         ballsToRemove.clear();
      }
   	
   	// WIN/LOSE SCREENS
      if (randomAsteroids.isEmpty()) {
      	
         brush.setColor(Color.black);
         brush.fillRect(0,0,width,height);
      	
         brush.setColor(Color.yellow);
         brush.drawString("YOU WIN!",SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
      	
         on = false;
      } else if (ship.getLives() == 0) {
      	
         brush.setColor(Color.black);
         brush.fillRect(0,0,width,height);
      	
         brush.setColor(Color.red);
         brush.drawString("YOU LOSE!",SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
      	
         on = false;
      }
   	
   }
	
   public Star[] createStars(int numberOfStars, int maxRadius) {
   
      Star[] stars = new Star[numberOfStars];
      for(int i = 0; i < numberOfStars; ++i) {
         Point center = new Point
            (Math.random() * Asteroids.SCREEN_WIDTH, Math.random() * Asteroids.SCREEN_HEIGHT);
         int radius = (int) (Math.random() * maxRadius);
         if(radius < 1) {
            radius = 1;
         }
         stars[i] = new Star(center, radius);
      }
      return stars;
   
   }

   public static void main (String[] args) {
      Asteroids a = new Asteroids();
      a.repaint();
   }
}