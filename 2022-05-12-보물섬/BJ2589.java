package p20220512;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BJ2589 {
	static class Loc {
		int x;
		int y;

		Loc(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		sc.nextLine();// nextInt임으로 마지막에 \n이 남아 있음으로 제거
		char[][] map = new char[row][col];
		String temp;

		for (int i = 0; i < row; i++) {
			temp = sc.nextLine();
			for (int j = 0; j < col; j++) {
				map[i][j] = temp.charAt(j);
			}
		}
		int maxDist = 0;
		int tempDist = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (map[i][j] == 'L') { // 만약 현재 위치가 육지라면
					boolean visited[][] = new boolean[row][col];

					Queue<Loc> queue = new LinkedList<Loc>();
					queue.add(new Loc(j, i));
					visited[i][j] = true;
					while (!queue.isEmpty()) {
						for (int size = queue.size(); size > 0 ; size--) {
							//굳이 위에 포문 쓰는 이유 : 클래스에 거리 저장하기 싫어서 ...
							Loc cur = queue.poll();
							int nr, nc;
							for (int dir = 0; dir < 4; dir++) {
								nc = cur.x + dx[dir];
								nr = cur.y + dy[dir];
								if (nr < 0 || nc < 0 || nr >= row || nc >= col) {
									continue; // 맵을 벗어남
								}
								if (map[nr][nc] == 'W') {
									continue;// 바다 못감
								}
								if (visited[nr][nc]) {
									continue;// 방문한 곳이면 못감
								}
								visited[nr][nc] = true;
								queue.add(new Loc(nc, nr));
							}
						}
						tempDist++; //마지막에 -1 해줘야함
					}
					
					maxDist = Math.max(maxDist, tempDist-1);
					tempDist = 0;
				}
			}
		}
		System.out.println(maxDist);

	}

	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };

// 뭔가 있을 거 같아서 상태 저장하게 dfs로 하려고 했는데 , 안되는 것 같다.
//	private static int dfs(int curR, int curC, char[][] map, int maxRow, int maxCol,int dist, int[][] visited) {
//		
//		int maxDist = dist;
//		int nr,nc;
//		visited[curR][curC] = dist;
//		
//		for(int i = 0 ; i < 4;i++) {
//			nr = curR+dy[i];
//			nc = curC+dx[i];
//			if(nr < 0 || nc < 0 || nr>=maxRow||nc >= maxCol) {
//				continue; //맵을 벗어남
//			}
//			if(map[nr][nc] == 'W') {
//				continue;//바다 못감
//			}
//			
//			if(visited[nr][nc]!= 0 &&dist+1 >= visited[nr][nc] ) {
//				continue;//현재 거리 + 다음 위치로 가는거리(1)이 누군가 이미가서 
//				//거리 기록한거 와 같거나 크면 갈 필요가 없다. 더 크거나 같게 나올테니깐
//			}
//			
//			int k = dfs(nr,nc,map,maxRow,maxCol,dist+1,visited);
//			System.out.println(k);
//		}
//		
//		return maxDist;
//	}
}
