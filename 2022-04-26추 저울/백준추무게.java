package algo;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 백준추무게 {

	static int availArr[]; //idx= 가능한 무게 

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		int chuNum = sc.nextInt();// 추 갯수
		int[] chus = new int[chuNum];
		int chuSumMax = 0;// 모든 추의 합을 구함

		for (int i = 0; i < chuNum; i++) {
			chus[i] = sc.nextInt(); //추의 무게를 저장하고
			chuSumMax += chus[i];	//전체 총합 무게를 저장
		}

		availArr = new int[chuSumMax + 1];// idx = 가능한 무게
		Arrays.fill(availArr, -1); // -1은 못하는 것 ....

		int tamaNum = sc.nextInt(); // 구슬 갯수
		int[] tamas = new int[tamaNum];
		
		for (int i = 0; i < tamaNum; i++) {
			tamas[i] = sc.nextInt(); //구슬 무게들을 저장함
		}

		availArr[0] = 0;// 아무 것도 선택하지 않은 것은 0 무조건 됨
		Queue<Integer> queue = new LinkedList<>(); //해당 시퀀스에서 가능한 무게 저장
		//사용하지 않을 시, 이상현상 ,, ex) 0 +1 로 1 idx가 가능한 무게가 되었을 때 , 2가 되버리는 현상
		
		for (int i = 0; i < chuNum; i++) { //각 구슬마다.
			for (int num = 0; num < chuSumMax; num++) {//가능한 무게 배열을 순회하면서
				if (availArr[num] != -1) {	//-1이 아닌 엘리먼트에 구슬의 무게를 빼거나 더한 값을 갱신
					int value = availArr[num]; 
					int chu = chus[i];
					queue.add(value + chu);
					queue.add(Math.abs(value - chu));
				}
			}
			
			while(!queue.isEmpty()) { //idx에 접근해 값을 1로 바꿔 주면 되는데 , 그냥 무게값 저장함
				int val = queue.poll();
				availArr[val] = val;
			}
		}

		for (int i = 0; i < tamaNum; i++) {
			if (tamas[i] > chuSumMax || availArr[ tamas[i]] == -1) {
				System.out.print("N "); //구슬이 전체 추 무게를 넘거나 , 가능하지 않은 무게일 경우
			} else {
				System.out.print("Y ");

			}
		}

	}

}
