package se1.schiffeVersenken.ais.PlayerNumber9;

import java.util.Stack;

public class SurroundingsProbe extends Probe{
	private Stack<Position> surroundings;//stack will possibly have repeat positions, AI should make sure they're rejected
	private boolean probeEnd;
	
	public SurroundingsProbe() {
		this(new Position());
	}
	
	public SurroundingsProbe(Position base) {
		super(base);
		this.probeEnd = false;
		this.surroundings = new Stack<Position>();
	}
	
	private boolean probeEnded() {
		return this.probeEnd;
	}
	
	@Override
	public Position probe() {//probe for next position
		if(probeEnded()) {//check surroundings (beruhren)
			if(this.surroundings.size() > 0) {
				return this.surroundings.pop();
			} else {
				return null;//TODO find proper end for probe
			}
		} else {
			return super.probe();
		}
	}
	
	@Override
	public void checkResult(int result, Position pos) {
		switch (result){
		case 0://water
			if(!probeEnded()) {//probe DIDNT END
				if(isGuided()) {
					flipProbe();//look in other direction
				} else {
					this.probing++;//technically shouldn't reach 4
				}
			}		
			break;
		case 1://ship
			if(!probeEnded()) {//probe DIDNT END
				if(!isGuided()) {
					setDirection();//set direction
				}
				updateLimits(pos);//update limits
				//keep probing same direction
			}
			updateSurroundings(pos);//add new surroundings
			break;
		default:
			updateLimits(pos);
			this.probeEnd = true;//ship_kill, probe is over
			updateSurroundings(this.first);
			updateSurroundings(this.last);//add new surroundings since ship-kill is a ship hit
			return;		
		}
	}
	
	@Override
	protected void flipProbe() {//limit reached, try other side
		this.probing += 2;// 0[up] goes to 2[down], 1[right] goes to 3[left], 2 and 3 go over limit
		//but, again, this shouldn't happen ever
		
		updateSurroundings(this.first);
		updateSurroundings(this.last);
	}
	
	private void updateSurroundings(Position pos) {//called after a direction is set
		if(probeEnded()) {//add all directions, probe has ended so direction is useless
			this.surroundings.push(pos.up());
			this.surroundings.push(pos.down());
			this.surroundings.push(pos.left());
			this.surroundings.push(pos.right());
		} else {
			switch(this.direction) {//if probe is in a direction, surroundings are on the other
			case HORIZONTAL:
				this.surroundings.push(pos.up());
				this.surroundings.push(pos.down());
				break;
			case VERTICAL:
				this.surroundings.push(pos.left());
				this.surroundings.push(pos.right());
				break;
			default:
			}
		}
	}
	
	@Override
	public boolean probingOver() {
		if(probeEnded()) {
			return (this.surroundings.size() < 1);//no more checks
		} else {
			return (this.probing > 3);//invalid probing value
		}
	}

}
