package p0308;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SWEA3752 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 입력 대략 100개 스캐너 써도 OK
		int T = sc.nextInt();

		for (int tc = 1; tc <= T; tc++) {
			int pointCount = sc.nextInt(); // 점수 종류들 갯수
			int[] pointArr = new int[pointCount]; //점수 저장용

			for (int i = 0; i < pointCount; i++) {
				pointArr[i] = sc.nextInt();
			}
			// ㅠㅠ 부분집합 하니깐 안된다 ....시간초과 난다.

			Set<Integer> sumPointSet = new HashSet<>();
			sumPointSet.add(0);
			ArrayList<Integer> tempArr = new ArrayList<>();
			//Set에 바로 저장하고 싶었으나, 그러면 저장되면서 Set이 늘어나는 기이한 현상발생
			//
			
			// powerSet(pointArr, depth, pointCount, sumPointSet, curSumPoint);
			for (int i = 0; i < pointCount; i++) {
				for (int item : sumPointSet) {
					tempArr.add(item + pointArr[i]);
					//현재까지 모두 구해진 값에 대해 이번 점수를 더함
				}
				sumPointSet.addAll(tempArr);
				tempArr.clear();
			}

			System.out.println(String.format("#%d %d", tc, sumPointSet.size()));
		}
	}

}
