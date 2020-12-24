package igo;

public class Grid {
	private int x;
	private int y;
	private State state = State.VOID;
	
	public Grid(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setState(State s){
		state = s;
	}
	
	public void clearState(){
		state = State.VOID;
	}
	
	public State getState(){
		return this.state;
	}
	
	public boolean equalState(State s){
		if(this.state == s)
			return true;
		return false;
	}
}
