package p20220428;

import java.util.Arrays;
import java.util.Scanner;

public class BJ2098_외판원 {

	final static int INF = 0x0fffffff;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int cityCount = sc.nextInt();// 도시 갯수

		int[][] adj = getAdjMatrix(cityCount, sc);
		// 인접 행렬을 가져온다.
		// 이해는 되지 않지만 , 임의의 출발점 선택시 아무곳이나 선택해도 최소 탐색 비용은 같다고 한다.
		//  .난 0번 정점으로 하겠음
		int allVisited = (1 << cityCount) - 1;

		int dp[][] = new int[cityCount][allVisited];
		for (int i = 0; i < cityCount; i++) {
			Arrays.fill(dp[i], -1);
		}

		// dp[i][j] = i에서 j(집합)을 거쳐 돌아온 최소 비용
		// ex) j = 3 .. 이진수로 011 ,, 0번과 1번 정점을 거침
		// if cityCount = 10 .. 필요한 비트는 10개 .
		// 이진수(100 0000 0000) -1하면 필요한 비트만큼 배열 잡힙

		// 어느 정점 선택해서 순회하든 같음
		int curVertext = 0;
		int visited = 1; // 0000 0001 .. 0번 정점 방문했다는 의미

		// 출발점,방문 기록 , 비용, 최적해, 모두 방문했다는 플래그
		int result = tsp(curVertext, visited, adj, dp, allVisited);
		System.out.println(result);
	}

	private static int tsp(int curVertext, int visited, int[][] adj, int[][] dp, int allVisited) {

		if (visited == allVisited) { // 모든 정점을 방문함
			if (adj[curVertext][0] == 0) { // ? -> 0, 임의의 정점에서 0에 못가는 경우 무한.
				return INF;
			}

			return adj[curVertext][0]; //마지막으로 선택된 정점에서 0으로 가능 경우
		}

		if (dp[curVertext][visited] != -1) {
			// 이미 구한 값이라면
			// 어느 정점에서 ,, visited(비트 1인 곳이 방문 정점) 순회한 최적해 반환
			return dp[curVertext][visited];
		}

		//
		dp[curVertext][visited] = INF;
		
		for (int i = 0; i < adj.length; i++) {
			//현재 정점에서 다음 정점 집합들로 가능 경우들을 봄 
			// ex) 1 -> {2,3,4,5,6,7,8,9,10} 
			if (adj[curVertext][i] == 0) { // 현재 정점에서 다음 정점을 못가는 경우
				continue;
			}
			
			if ((visited & (1 << i)) == (1 << i)) {
				// i번째 비트가 1.. 방문한 경우
				continue;
			}
			//어느 한 정점에서 정점 집합 순회 최적해 = MIN(원래값,현재점에서 다음점 + 다음점에서 도착지까지 순회 비용) 
			dp[curVertext][visited] = Math.min(dp[curVertext][visited],
					adj[curVertext][i] + tsp(i, visited | 1 << i, adj, dp, allVisited));
		}

		return dp[curVertext][visited];
	}

	public static int[][] getAdjMatrix(int cityCount, Scanner sc) {
		int[][] map = new int[cityCount][cityCount];

		for (int i = 0; i < cityCount; i++) {
			for (int j = 0; j < cityCount; j++) {
				map[i][j] = sc.nextInt();
			}
		}

		return map;
	}
}
