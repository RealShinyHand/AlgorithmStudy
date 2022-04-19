package p20220419;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj1520 {
	
	static int[] dy= {-1,1,0,0};
	static int[] dx= {0,0,-1,1};
	
	static int M;//행
	static int N;//열
	static int[][] dp;
	static int[][] map;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String temp[] = br.readLine().split(" ");
		
		  M = Integer.parseInt(temp[0]); //행 길이
		  N = Integer.parseInt(temp[1]); //열 길이
		
		 map = new int[M][N];//맵
		 dp = new int[M][N]; //방문 배열
		
		StringTokenizer stn;
		for(int i = 0 ; i < M ; i++) {
			stn = new StringTokenizer(br.readLine()," ");
			for(int j = 0 ; j < N ; j++) {
				map[i][j] = Integer.parseInt(stn.nextToken());
				dp[i][j] = -1;//아직 가지 않았다는 표시
			}
		}
		
		
		dfs(0,0);
		System.out.println(dp[0][0]);
	}

	private static int dfs(int x, int y) {
		
		if(x == N-1 && y == M-1) {
			//도착지 도착 시
			return 1;
		}
		
		dp[y][x] = 0;//방문한 곳을 -1 -> 0으로 바꾼다.
		
		int nx,ny;
		

		for(int i = 0 ; i < 4;i++) {
			nx = x+ dx[i];
			ny = y + dy[i];
			
			if(nx <0 || ny <0 || nx >=N || ny>=M ) {
				continue; //맵을 벗어나 가지 못한다. 
			}
			
			if(map[ny][nx] >= map[y][x]) {
				//높거나 같아서 못감
				continue;
			}
			
			if(map[ny][nx] == 0) { //방문을 한 곳이고 
				//도착지에 못가는 장소라고 확정남
				continue;
			}
			
			if(dp[ny][nx] != -1) { //0도 아니고, 아직 방문하지 못한 곳도
				//아니니깐 그칸에서 갈 수 있는 가짓수를 가져옴,현재 칸에
				//도착지로 갈 수 있는 경로로 들어옴 .
				dp[y][x] += dp[ny][nx];
				//다음경로에서 갈 수 있었던 경로를 자신에게 더함 
			}else { //처음 보는 경로이면 가봐야지
				dp[y][x] += dfs(nx,ny);//함 가봐야함
				// 현재칸에서 도착지로 갈 수 있는 가짓 수 = 오른쪽,왼쪽,위 ,아래 칸에서 갈 수 있는 
				//도착지 경우의 수
			}
			
		}

		return dp[y][x];
		
	}
}
