import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class VectorVisualisation extends PApplet {

Particle[] ps = new Particle[4];
static PVector gravity = new PVector();
PFont font;
int scale = 100;
int coordScale = 10;
public void setup() {
  
  //frameRate(4);

  ps[0] = new Particle(0, 0);
  ps[1] = new Particle(0, 0);
  ps[2] = new Particle(0, 0);
  ps[3] = new Particle(0, 0);
  font = createFont("Arial", 16, true);
  gravity.add(0, 0.25f);
  ps[0].setVelocity(new PVector(10, -10));
  ps[1].setVelocity(new PVector(20, -10));
  ps[2].setVelocity(new PVector(10, -7));
  ps[3].setVelocity(new PVector(20, -7));
}

public void keyPressed() {
  loop();
}
public void mousePressed() {
  noLoop();
}

public void draw() {
  background(255);
  textFont(font, 20);
  pushMatrix();
  translate(width/4, height/1.5f);
  for(Particle p : ps){
  p.gravity();
  p.update();
  p.show();
  
  text("("+(int)(p.pos.x/scale*coordScale)+"/"+(int)(p.pos.y/scale*-coordScale)+")", p.pos.x, p.pos.y-10);
  }
  drawCoordinateSystem();
  popMatrix();
  fill(255);
  //noStroke();
  rect(0, 0, 12*coordScale, 3*coordScale);
  fill(0);
  textAlign(LEFT);
  text("Gravity: "+gravity.mag(), 0, 20);
  saveFrame("frames/frame_######.tif");
}


public void drawCoordinateSystem() {
  fill(0);
  stroke(0);
  strokeWeight(5);
  line(0, 0, 0, height);
  line(0, 0, width, 0);
  line(0, 0, 0, -height);
  line(0, 0, -width, 0);
  for (int x = -width; x < width; x+=scale) { 
    line(x, 10, x, -10);
    fill(0);
    textAlign(CENTER);
    if (x != 0) text(String.valueOf(x/scale*coordScale), x, 30);
  }
  for (int y = -height; y < height; y+=scale) {
    line(10, y, -10, y);
    fill(0);
    textAlign(CENTER);
    if (y != 0) text(String.valueOf(y/scale * -coordScale), -30, y);
  }

  stroke(20, 20, 20, 50);
  strokeWeight(1);
  int prevX = -width;
  int prevY = -height;

  for (int x = -width; x < width; x+=scale/coordScale) { 

    for (int y = -height; y < height; y+=scale/coordScale) {
      line(x, y, x, prevY);
      prevY = y;
    }
    for (int y = -height; y < height; y+=scale/coordScale) line(x, y, prevX, y);

    prevX = x;
  }
}
class Particle {
  int x, y;
  PVector pos, prev, vel, acc;
  Particle(int x, int y) {
    pos = new PVector(x, y);
    prev = new PVector(x, y);
    vel = new PVector();
    acc = new PVector();
  }


  public void setVelocity(PVector v){
    this.vel = v.copy();
  }

  public void update() {
    this.vel.add(this.acc);
    
    this.pos.add(this.vel);
    this.acc.mult(0);
  }

  public void show() {
    stroke(20);
    strokeWeight(10);
    line(this.pos.x, this.pos.y, this.prev.x, this.prev.y);

    this.prev.x = this.pos.x;
    this.prev.y = this.pos.y;
  }
  public void gravity(){
    this.acc.add(VectorVisualisation.gravity);
    
      
    
  }
  public void attracted(PVector target){
    // var dir = target - this.pos
    PVector force = target.sub(this.pos);
    float d = force.mag();
    d = constrain(d, 5, 25);
    int G = 500;
    float strength = G / (d * d);
    force.setMag(strength);
    this.acc.add(force);
  }
}
  public void settings() {  size(1900, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "VectorVisualisation" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
