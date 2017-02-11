class Particle {
  int x, y;
  PVector pos, prev, vel, acc;
  Particle(int x, int y) {
    pos = new PVector(x, y);
    prev = new PVector(x, y);
    vel = new PVector();
    acc = new PVector();
  }


  void setVelocity(PVector v){
    this.vel = v.copy();
  }

  void update() {
    this.vel.add(this.acc);
    
    this.pos.add(this.vel);
    this.acc.mult(0);
  }

  void show() {
    stroke(20);
    strokeWeight(10);
    line(this.pos.x, this.pos.y, this.prev.x, this.prev.y);

    this.prev.x = this.pos.x;
    this.prev.y = this.pos.y;
  }
  void gravity(){
    this.acc.add(VectorVisualisation.gravity);
    
      
    
  }
  void attracted(PVector target){
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