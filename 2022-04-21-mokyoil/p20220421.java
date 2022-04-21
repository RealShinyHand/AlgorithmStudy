import java.io.BufferedReader;
import java.io.InputStreamReader;

//백준 16234 인구이동
public class p20220421 {

	static int L; //최소선
	static int R; //최대선
	static int N; //정사각형 크기 

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] temp = br.readLine().split(" ");

		N = Integer.parseInt(temp[0]);
		L = Integer.parseInt(temp[1]); // 최소선
		R = Integer.parseInt(temp[2]); // 최대선

		int map[][] = new int[N][N];
		int visited[][] = new int[N][N];

		for (int i = 0; i < N; i++) {
			temp = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
			}
		}

		int day = -1;
		int alience = 0;
		boolean isAlience = true;
		while (isAlience) {
			day++;
			isAlience = false; //전부 독립적인 연합인 경우 false이다.
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {// 최대 50*50 ct
					if (visited[i][j] != 0) { //0이 아니면 방문 한거다. 
						continue;
					}
					++alience;
					// r, c, map, visited
					// maybe 최대값 == 100 * 50
					// 평균 필터 안써도됨, 오히려 쓰면 소수점 손실
					count = 0;//연합의 갯수 
					//dfs의 반환은 연합 총합
					//같은 연합의 갯수 구함, 총합 구함 , visited에 연합 번호 매김
					int totalSum = dfs(i, j, map, visited, alience);
					
					if(count == 1) {
						continue; //연합은 자기혼자인 상황 
						//괜히 밑에 배열 전체 탐색하지말자.
					}
					isAlience = true;// 연합인 국가가 있었음으로 true
					//
					
					int avg = totalSum/ count;//정수 나누기
					
					//같은 연합인거 visited로 알고 , map값 갱신 
					populationMove(map,visited,alience,N,avg);

				}
			}
			
			
			//초기화 어떻게든 안하려고 해봤는데 못하겠더라.
			for(int i = 0 ; i < N ; i ++) { //방문배열 초기화
				for(int j = 0 ; j < N ; j++) {
					visited[i][j] = 0;
				}
			}
			
			alience = 1;//연합번호 초기화
		}
		
		System.out.println(day);
	}

	private static void populationMove(int[][] map, int[][] visited, int alience, int n2, int avg) {
		
		for(int i = 0 ; i < N ; i ++) {
			for(int j = 0 ; j < N ; j++) {
				if(visited[i][j] == alience) {
					map[i][j] = avg;
				}
			}
		}
		
	}

	static int dx[] = { 1, 0, -1, 0 };
	static int dy[] = { 0, -1, 0, 1 };
	static int count = 0;
	private static int dfs(int row, int col, int[][] map, int[][] visited, int alience) {
		
		visited[row][col] = alience;
		count++;
		
		
		int nr;
		int nc;
		int differ;
		int sum = map[row][col];

		for (int i = 0; i < 4; i++) {
			nr = row + dy[i];
			nc = col + dx[i];
			if (nr < 0 || nc < 0 || nr >= N || nc >= N) {
				continue; // 맵을 벗어남
			}
			differ = Math.abs(map[row][col] - map[nr][nc]);

			if (differ > R || differ < L) {
				continue; // 최대선 보다 크거나 , 최소선 보다 작으면 국경 개방안함
			}
			if (visited[nr][nc] != 0) {
				continue; // 이미 방문 == 다른 연합임
			}

			sum += dfs(nr, nc, map, visited, alience);
		}

		return sum;
	}
}
