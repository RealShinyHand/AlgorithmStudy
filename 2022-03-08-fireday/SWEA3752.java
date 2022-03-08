package p0308;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SWEA3752 {
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		//입력 대략 100개 스캐너 써도 OK
		int T = sc.nextInt();
		
		for(int tc = 1; tc <= T;tc++) {
			int pointCount = sc.nextInt(); //점수 종류들 갯수
			int[] pointArr = new int[pointCount];
			
			for(int i = 0 ; i < pointCount;i++) {
				pointArr[i] = sc.nextInt();
			}
			//ㅠㅠ 부분집합 하니깐 안된다 ....시간초과 난다. 
			int depth = 0;
			Set<Integer> sumPointSet = new HashSet<>();
			int curSumPoint = 0;
			
			powerSet(pointArr, depth, pointCount, sumPointSet, curSumPoint);
			System.out.println(sumPointSet.size());
		}
	}
	
	/**
	 * 
	 * @param pointArr 점수 저장 배열
	 * @param depth	인덱스
	 * @param pointCount 점수 갯수
	 * @param sumPointSet 총합 점수 저장
	 * @param curSumPoint 현재까지 더해진 점수
	 */
	private static void powerSet(int[] pointArr, int depth, int pointCount, Set<Integer> sumPointSet, int curSumPoint) {
		
		if(sumPointSet.contains(curSumPoint)) {
			return;
		}
		if(depth == pointCount) {
			sumPointSet.add(curSumPoint);
			return;
		}
		sumPointSet.add(curSumPoint);
		
		//현재 인덱스(뎁스)의 엘리먼트를 더한 경우
		powerSet(pointArr, depth+1, pointCount, sumPointSet, curSumPoint+pointArr[depth]);
		
		//더하지 않은 경우
		powerSet(pointArr, depth+1, pointCount, sumPointSet, curSumPoint);
	}
}
