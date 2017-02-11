Particle p;
static PVector gravity = new PVector();
PFont font;
int scale = 100;
int coordScale = 10;
void setup() {
  size(800, 600);
  //frameRate(4);

  p = new Particle(0, 0);
  font = createFont("Arial", 16, true);
  gravity.add(0, 0.25);
  if(gravity.mag() > 5) exit();
  p.setVelocity(new PVector(10, 10*-2));
}

void keyPressed() {
  loop();
}
void mousePressed() {
  noLoop();
}

void draw() {
  background(255);
  textFont(font, 20);
  pushMatrix();
  translate(width/4, height/1.5);

  p.gravity();
  p.update();
  p.show();
  text("("+(int)(p.pos.x/scale*coordScale)+"/"+(int)(p.pos.y/scale*-coordScale)+")", p.pos.x, p.pos.y-10);
  drawCoordinateSystem();
  popMatrix();
  fill(255);
  //noStroke();
  rect(0, 0, 12*coordScale, 3*coordScale);
  fill(0);
  textAlign(LEFT);
  text("Gravity: "+gravity.mag(), 0, 20);
  if (p.pos.y > coordScale*10 | p.pos.x > coordScale*60) {

    setup();
  }
  saveFrame("frames/frame_######.tif");
}


void drawCoordinateSystem() {
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