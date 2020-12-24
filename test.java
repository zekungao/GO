package igo;

import java.util.Scanner;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board = new Board();
		boolean exit = false;
		Scanner s = new Scanner(System.in);
		State state = State.BLACK;
		int x = 0;
		int y = 0;
		
		while(!exit){
			System.out.println(board.showBoard());
			switch(state){
			case BLACK:
				System.out.println("it's BLACK's turn");
				break;
			case WHITE:
				System.out.println("it's WHITE's turn");
				break;
			}
			System.out.println("please int x:");
			x = s.nextInt();
			System.out.println("please int y:");
			y = s.nextInt();
			if(x<0 || x>=Board.width || y<0 || y>=Board.length){
				System.out.println("wrong input, exit");
				exit = true;
				break;
			}
			
			if(board.drop(x,y,state)){
				switch(state){
				case BLACK:
					state = State.WHITE;
					break;
				case WHITE:
					state = State.BLACK;
					break;
				}
			}
			else{
				System.out.println("can not drop here" +
						"");
			}
		}
		
		s.close();
	}

}
