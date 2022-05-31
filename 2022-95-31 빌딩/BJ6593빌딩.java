package p20220531;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Objects;

public class BJ6593빌딩 {
	static class Loc {
		//이 객체는 동일성 비교에서 count 필드가 제외됩니다.
		int l;
		int r;
		int c;
		int count;

		public Loc() {
		}

		public Loc(int l, int r, int c, int count) {
			super();
			this.l = l;
			this.r = r;
			this.c = c;
			this.count = count;
		}


		@Override
		public String toString() {
			return "Loc [l=" + l + ", r=" + r + ", c=" + c + ", count=" + count + "]";
		}

		@Override
		public int hashCode() { // 이퀄 재정의하면 , 해시코드도 같게 나와야 한다는 뭐시기가 있으니깐 놔두겠음
			final int prime = 31;
			int result = 1;
			result = prime * result + c;
			result = prime * result + l;
			result = prime * result + r;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass()) // 클래스명 검사
				return false;
			Loc other = (Loc) obj;
			if (c != other.c)
				return false;
			if (l != other.l)
				return false;
			if (r != other.r)
				return false;
			return true;
		}

	}

	static int[][] ddd = { { 1, 0, 0 }, // l,r,c .. 윗층 ㄱ
			{ 0, 1, 0 }, // 윗쪽 ㄱ
			{ -1, 0, 0 }, // 아랫층으로 ㄱ
			{ 0, -1, 0 }, // 아랫쪽 ㄱ
			{ 0, 0, 1 }, // 오른쪽
			{ 0, 0, -1 } }; // 왼쪽

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String buf;
		String[] buff;
		char ch;

		while (true) {

			buff = br.readLine().split(" ");
			int L = Integer.parseInt(buff[0]);// 층수
			int R = Integer.parseInt(buff[1]);//
			int C = Integer.parseInt(buff[2]);//

			if ((L | R | C) == 0) {
				break;
			}

			char[][][] map3d = new char[L][R][C];
			// 방문배열 만들지 말고
			// 기존 배열 이동가능하던 공간을 벽으로 만들어 뿌자.

			Loc start, end;
			start = end = null;
			for (int l = 0; l < L; l++) {
				for (int r = 0; r < R; r++) {
					buf = br.readLine();
					// 시작점, 끝점 찾아야해서 안됨 map3d[l][r] = buf.toCharArray();
					for (int c = 0; c < C; c++) {
						ch = buf.charAt(c);
						if (ch == 'E') {
							end = new Loc(l, r, c, 0);
							ch = '.';
						} else if (ch == 'S') {
							start = new Loc(l, r, c, 0);
							ch = '.';
						}
						map3d[l][r][c] = ch;
					}
				}
				br.readLine(); // 층 간에 입력 예제를 보면 뭔가 개행이 존재함
			}

			if (start == null || end == null) {
				System.err.println("에러 시작점 혹은 끝점 NULL");
			}

			LinkedList<Loc> queue = new LinkedList<>();
			queue.add(start);
			makeToWall(map3d, start);
			start = null; // 혹시 모르니간 참조 끊고

			Loc cur = null;
			int curL, curR, curC;
			int nextL, nextR, nextC;

			while (!queue.isEmpty()) {
				cur = queue.poll();
				curL = cur.l;
				curR = cur.r;
				curC = cur.c;
				if (cur.equals(end)) {
					break;
				}
				for (int d = 0; d < ddd.length; d++) {
					nextL = curL + ddd[d][0];
					nextR = curR + ddd[d][1];
					nextC = curC + ddd[d][2];

					if (!rangeValidate(nextL, nextR, nextC, L, R, C)) {
						continue; // 층, 행, 열 넘으면
					}
					if (map3d[nextL][nextR][nextC] == '#') {
						continue; // 벽이면
					}
					makeToWall(map3d, nextL, nextR, nextC);
					queue.add(new Loc(nextL, nextR, nextC, cur.count + 1));

				}
			}

			if (cur.equals(end)) { // 왜 또 검사하냐?, cause : flag만들기 싫어서
				System.out.println(String.format("Escaped in %d minute(s).", cur.count));
			} else {
				System.out.println("Trapped!");
			}
		}
	}

	private static boolean rangeValidate(int nextL, int nextR, int nextC, int l, int r, int c) {

		if (nextL < 0 || nextL >= l) {
			return false;
		}
		if (nextR < 0 || nextR >= r) {
			return false;
		}
		if (nextC < 0 || nextC >= c) {
			return false;
		}
		return true;
	}

	private static void makeToWall(char[][][] map, int l, int r, int c) {
		map[l][r][c] = '#'; // 안길어지네 ...
	}

	private static void makeToWall(char[][][] map, Loc loc) {
		map[loc.l][loc.r][loc.c] = '#'; // 안길어지네 ...
	}
}
