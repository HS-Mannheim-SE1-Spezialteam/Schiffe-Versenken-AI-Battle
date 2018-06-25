package se1.schiffeVersenken.ais.team2;

public class MyTile {
	
	private MyTileEnum status;
	
	public MyTile(){
		status = MyTileEnum.WATER;
	}
	
	public void setStatus(MyTileEnum newStatus){
		this.status = newStatus;
	}

	public void print() {
		if(status == MyTileEnum.WATER)
			System.out.print("*");
		if(status == MyTileEnum.SHIP)
			System.out.print("S");
		if(status == MyTileEnum.SHIP_KILL)
			System.out.print("K");
		if(status == MyTileEnum.SHIP_HIT)
			System.out.print("X");
		if(status == MyTileEnum.WATER_HIT)
			System.out.print("O");
	}
	
	public MyTileEnum getStatus(){
		return status;
	}
	
	
	
}
