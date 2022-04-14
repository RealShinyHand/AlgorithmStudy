package p20220414;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SWEA7699수지여행 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1 ; tc <= T; tc++) {
			String temp[] = br.readLine().split(" ");
			int R = Integer.parseInt(temp[0]); //행
			int C = Integer.parseInt(temp[1]); //열 
			char map[][] =new char[R][];
			for(int i = 0;i < R;i++) {
				map[i] = br.readLine().toCharArray();
			}
			//알파벳 대문자 ...
			//A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z
			//26개 
			visited = 0;
			result=0;
			dfs(map,0,0,0,R,C);
			System.out.println(String.format("#%d %d", tc,result));
		}
		
	}
	static int[][] dd  = {{1,0},{-1,0},{0,1},{0,-1}};
	static int visited;
	static int result;
	private static void dfs(char[][] map, int count, int r, int c, int R, int C) {
		// TODO Auto-generated method stub
		visited |= (1<< map[r][c] - 'A');//단산쉬관비논 ... 단항, 산술, 쉬프트 , 관계 , 비트,논리
		count++;
		//System.out.println(map[r][c]);
		result = Math.max(result, count);
		int nx;
		int ny;
		
		for(int i = 0 ; i < 4 ;i++) {
			nx = c +dd[i][0];
			ny = r + dd[i][1];
			if(ny>=R||nx>=C||nx<0||ny <0) {
				continue; //맵 벗어남 
			}

			if((visited & 1 << map[ny][nx]-'A') !=  0) {
				
				continue; //이미 방문한 알파벳
				//(이미 방문한 곳) C (이미 방문한 알파벳).. 부분 집합이라는 소리
			}
			
			dfs(map, count, ny, nx, R, C);
		}
		
		visited ^= (1<< map[r][c] - 'A');
		
	}
}
