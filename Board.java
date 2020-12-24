package igo;

import java.lang.String;
import java.lang.StringBuilder;

public class Board {
	static final int length = 19; 
	static final int width  = 19;
	
	private Grid[][] board = new Grid[length][width];
	
	private int originX = -1;
	private int originY = -1;
	
	public Board(){
		for(int i = 0; i<width; i++){
			for(int j = 0; j< length; j++){
				board[i][j] = new Grid(i,j);
			}
		}
	}
	
	public void setGO(int x, int y, State state){
		board[x][y].setState(state);
	}
	

	
	private boolean judgeDA(int x, int y, State originState, BanVector ban){
		if(x == originX && y == originY)
			return false;
		if(board[x][y].equalState(State.VOID))
			return true;
		if(!board[x][y].equalState(originState))
			return false;
		return deathAlive(x,y,originState, ban);
	}
	private boolean deathAlive(int x, int y, State originState, BanVector ban){
		//true:alive, false:death
		if(x != 0 			&& ban != BanVector.LEFT)
			if(judgeDA(x-1,y,originState, BanVector.RIGHT))
				return true;
		if(x != width - 1 	&& ban != BanVector.RIGHT)
			if(judgeDA(x+1,y,originState, BanVector.LEFT))
				return true;
		if(y != 0			&& ban != BanVector.UP)
			if(judgeDA(x,y-1,originState, BanVector.DOWN))
				return true;
		if(y != length -1	&& ban != BanVector.DOWN)
			if(judgeDA(x,y+1,originState, BanVector.UP))
				return true;
		
		return false;
	}
	private enum BanVector{UP,DOWN,LEFT,RIGHT,VOID}

	private void resetOrigin(){
		originX = -1;
		originY = -1;
	}
	public boolean canEat(int x, int y, State originState){
		originX = x;
		originY = y;
		if(x != 0 			&& !board[x-1][y].equalState(originState))
			if(!deathAlive(x-1,y,board[x-1][y].getState(),BanVector.RIGHT)){
				resetOrigin();
				return true;
			}
		if(x != width - 1 	&& !board[x+1][y].equalState(originState))
			if(!deathAlive(x+1,y,board[x+1][y].getState(),BanVector.LEFT)){
				resetOrigin();
				return true;
			}
		if(y != 0	 		&& !board[x][y-1].equalState(originState))
			if(!deathAlive(x,y-1,board[x][y-1].getState(),BanVector.DOWN)){
				resetOrigin();
				return true;
			}
		if(y != length - 1	&& !board[x][y+1].equalState(originState))
			if(!deathAlive(x,y+1,board[x][y+1].getState(),BanVector.UP)){
				resetOrigin();
				return true;
			}
		
		resetOrigin();
		return false;
	}

	
	private boolean canDrop(int x, int y, State originState){
		if(canEat(x,y,originState))
			return true;
		if(deathAlive(x,y,originState,BanVector.VOID))
			return true;
		
		return false;
	}
	

	private void eating(int x, int y, BanVector ban){
		State originState = board[x][y].getState();
		
		if(x != 0 			&& board[x-1][y].equalState(originState) && ban != BanVector.LEFT)
			eating(x-1, y, BanVector.RIGHT);
		if(x != width - 1 	&& board[x+1][y].equalState(originState) && ban != BanVector.RIGHT)
			eating(x+1, y, BanVector.LEFT);
		if(y != 0	 		&& board[x][y-1].equalState(originState) && ban != BanVector.DOWN)
			eating(x, y-1, BanVector.UP);
		if(y != length - 1	&& board[x][y+1].equalState(originState) && ban != BanVector.UP)
			eating(x, y+1, BanVector.DOWN);
		
		board[x][y].clearState();
	}
	private void eat(int x, int y){
		State originState = board[x][y].getState();
		if(!canEat(x,y,originState))
			return;
		
		if(x != 0 			&& !board[x-1][y].equalState(originState))
			if(!deathAlive(x-1,y,board[x-1][y].getState(),BanVector.RIGHT))
				eating(x-1, y, BanVector.RIGHT);
		if(x != width - 1 	&& !board[x+1][y].equalState(originState))
			if(!deathAlive(x+1,y,board[x+1][y].getState(),BanVector.LEFT))
				eating(x+1, y, BanVector.LEFT);
		if(y != 0	 		&& !board[x][y-1].equalState(originState))
			if(!deathAlive(x,y-1,board[x][y-1].getState(),BanVector.DOWN))
				eating(x, y-1, BanVector.UP);
		if(y != length - 1	&& !board[x][y+1].equalState(originState))
			if(!deathAlive(x,y+1,board[x][y+1].getState(),BanVector.UP))
				eating(x, y+1, BanVector.DOWN);
	}
	
	public boolean drop(int x, int y, State state){
		if(!canDrop(x,y,state))
			return false;
		if(board[x][y].getState() != State.VOID)
			return false;
		board[x][y].setState(state);
		eat(x,y);
		return true;
	}
	
	public String showBoard(){
		StringBuilder sb = new StringBuilder();
		for(int j = 0; j<length; j++){
			for(int i = 0; i<width; i++){
				switch(board[i][j].getState()){
				case BLACK:
					sb.append("*");
					break;
				case WHITE:
					sb.append("o");
					break;
				case VOID:
					sb.append("+");
					break;
				default:
					break;
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
