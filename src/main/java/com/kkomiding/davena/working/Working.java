package com.kkomiding.davena.working;

public class Working {

	public static void main(String[] args) {
		
		int [][] schedule = new int[31][10];
		
		for(int i = 0; i < schedule.length; i++) {
			for(int j = 0; j < schedule[i].length; j++) {
				schedule[i][j] = 1;
				System.out.println(schedule[i][j]);
			}
		}

	}

}
