package se1.schiffeVersenken.ais.team3;

public class Position {//doesn't check bounds

	private int x;
	private int y;
	
	public Position(){
		super();
		this.x = 0;
		this.y = 0;
	}
	
	public Position(int horizontal, int vertical){
		this.x = horizontal;
		this.y = vertical;
	}
	
	public Position(Position pos){
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getHorizontal(){
		return this.x;
	}
	
	public int getVertical(){
		return this.y;
	}
	
	public boolean equals(Object arg0) {
		if(arg0 != null && getClass() == arg0.getClass()) {
			return ((this.getX() == ((Position)arg0).getX())&& (this.getY() == ((Position)arg0).getY()));
		}
		return false;
	}
	
	public Position random(){
		int rx = (int) Math.round((Math.random() * 9));
		int ry = (int) Math.round((Math.random() * 9));
		return new Position(rx, ry);
	}
	
	public Position random(int max){
		int rx = (int) Math.round((Math.random() * max));
		int ry = (int) Math.round((Math.random() * max));
		return new Position(rx, ry);
	}
	
	public Position random(int x, int y){
		int rx = (int) Math.round((Math.random() * x));
		int ry = (int) Math.round((Math.random() * y));
		return new Position(rx, ry);
	}
	
	public Position move(int deltaHorizontal, int deltaVertical){
		return new Position(this.x + deltaHorizontal, this.y + deltaVertical);
	}
	
	public Position move(Position pos){
		return this.add(pos);
	}
	
	public Position add(Position pos){
		return new Position(this.x + pos.getX(), this.y + pos.getY());
	}
	
	public Position add(int value){//add a value to both x and y
		return new Position(this.x + value, this.y + value);
	}
	
	public Position add(int dX, int dY){//add a value to both x and y
		return new Position(this.x + dX, this.y + dY);
	}
	
	public Position up(){//position above
		return new Position(this.x, this.y - 1);
	}
	
	public Position right(){//position to the right
		return new Position(this.x + 1, this.y);
	}
	
	public Position down(){//position below
		return new Position(this.x, this.y + 1);
	}
	
	public Position left(){//position to the left
		return new Position(this.x - 1, this.y);
	}
	
	public Position cycle(){//position at the beginning of next line
		return new Position(0, this.y + 1);
	}
	
	public String toString() {
		return new String("("+this.getX()+","+this.getY()+")");
	}
	
	public Position getSorrounder(int direction, int part){
		if(direction == 1 || direction == -1){
			if(part == 1){
				return this.up();
			}
			else{
				return this.down();
			}
		}
		else if(direction == 2 || direction == -2){
			if(part == 1){
				return this.left();
			}
			else{
				return this.right();
			}
		}
		else{
			return this;
		}
	}

	public Position moveToDirection(int direction){
		if(direction == 1){
			return this.right();
		}
		else if(direction == -1){
			return this.left();
		}
		else if(direction == 2){
			return this.down();
		}
		else if(direction == -2){
			return this.up();
		}
		else{
			return this;	
		}
	}
}
