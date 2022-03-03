package p0303;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ2573 {

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] temp = br.readLine().split(" ");
		int row = Integer.parseInt(temp[0]);
		int col = Integer.parseInt(temp[1]);
		int[][] map = new int[row][col];
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < row;i++) {
			sb.append(br.readLine()).append(" ");
		}
		br.close();
		
		StringTokenizer stn= new StringTokenizer(sb.toString());
		
		for(int i = 0; i < row;i++ ) {
			for(int j = 0 ; j < col;j++) {
				map[i][j]=Integer.parseInt(stn.nextToken());
			}
		}
		
		
		
		
		int count = 1;
		int year = 0;
		
		while((count=getIndependentIland(map,row,col)) ==1 ) {

			year++;
			yearLeave(map,row,col);
			
			for(int i = 0 ; i < row;i++) {
				for(int j = 0 ; j < col;j++) {
					if(map[i][j] < 0) {
						map[i][j] = 0;
					}
				}
			}
			
			/*
			for(int i = 0; i < row;i++) {
				for(int j = 0 ; j < col;j++) {
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
			*/
		}
		
		if(count >= 2) {
			System.out.println(year);
		}else {
			System.out.println(0);
		}
	}
	
	//30000 * 4 = ? 120000byte  120000/2^10 =? 대강 1000으로 하면 
	//128 MB 안넘네 
	private static int getIndependentIland(int[][] map, int row, int col) {
		
		boolean[][] visited = new boolean[row][col];
		int count = 0;
		
		for(int i = 0; i < row;i++) {
			for(int j = 0; j < col;j++) {
				if(map[i][j] != 0 && !visited[i][j]) {
					count++;
					dfs(map,visited,i,j,row,col);
				}
			}
		}
		return count;
	}
	
	private static void dfs(int[][] map, boolean[][] visited, int i, int j,int row,int col) {
		int nx;
		int ny;
		visited[i][j] = true;
		//주어진 좌표에 대해서 깊이 탐색하면서 visited 바꾸기 
		for(int dir = 0 ; dir < 4;dir++) {
		//visited가 트루가 되면서 탈출하게 될 것이다.
			nx = j + dx[dir];
			ny = i + dy[dir];
			if(nx >= col || nx < 0 || ny >=row || ny <0) {
				continue;
			}else if(map[ny][nx] ==0|| visited[ny][nx]) {
				continue;
			}
			dfs(map, visited, ny, nx, row, col);
		}
		
	}

	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	
	private static void yearLeave(int[][] map, int row, int col) {
		
		int nx;
		int ny;
		
		int count = 0;
		for(int i = 0 ; i < row;i++) {
			
			for(int j = 0 ; j < col;j++) {
				if(map[i][j] != 0) {
					count = 0;
					for(int dir = 0 ; dir < 4;dir++) {
						nx = j + dx[dir];
						ny = i + dy[dir];
						if(nx >= col || nx < 0 || ny >=row || ny <0) {
							count++;
						}else if(map[ny][nx] ==0) {
							count++;
						}
					}
					map[i][j] -=count;
					if(map[i][j] == 0) {
						map[i][j] = -1;
					}
				}
			}
		}
		
	}
}
