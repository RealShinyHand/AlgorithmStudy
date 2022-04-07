package algo;

import java.util.Arrays;
import java.util.Scanner;

public class SWEA4013 {
	
	static class Topni{
		private int top; //톱니를 직접 queue 같은 걸로 돌릴 필요없이 top만 이동 시켜서 계산하면 된다. 
		private int petchCount = 8; //톱니의 갯수+1 , 톱니 배열 사이즈 
		private int petch[] =null; //톱니는 8개고 배열 끝 idx = 7
		
		public Topni(int[] petch,int petchCount) {
			this.top = 0;
			this.petch = petch;
			this.petchCount = petchCount;
		}
		
		public int getTopValue() { //톱니 top의 값 반환 
			return petch[top];
		}
		
		private void rotateRight() { //오른쪽으로 톱니를 회전시키면 top을 왼쪽으로 이동시키면 된다. 
			top = (top-1)%petchCount; // 파이썬 음수 모듈러 연산을 지원하는데 자바는 안하더라 ... 
			if(top < 0) {//음수가 되면 나머지 연산하는 값에 그 걸 더해주면 나머지가 되더라 .
				top = petchCount +top;
			}
		}
		
		private void rotateLeft() { //왼쪽으로 돌리면 top이 오른쪽으로 이동 만약 8이면 0을 가리키는 것 
			top = (top+1)%petchCount;

		}
		
		public void rotate(int op) { // op가 1이면 오른쪽, op -1이면 왼쪽으로 회전한다. 
			if(op == 1) {
				rotateRight();
			}else if(op== -1) {
				rotateLeft();
			}else {
				System.out.println("operation code 심각한 오류");
			}
		}
		
		public int getRightJunction() { //top 에서 +2 한 곳을 반환하면 오른쪽 톱니랑 맞물리는 값
			return petch[(top+2)%petchCount];
		}
		
		public int getLeftJunction() { //top 에서 +6 하면 왼쪽톱니랑 맞 물리는 값 
			return petch[(top+6)%petchCount];
		}
		
		public String toString() {
			return String.format("배열 상태 : %s , top : %d, leftJunction : %d, rightJunction : %d",
					Arrays.toString(petch),top,getLeftJunction(),getRightJunction());
					
		}
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for(int tc = 1; tc<=T;tc++) {
			int K = sc.nextInt();
			int topniCount = 4;
			int petchCount = 8;
			Topni[] topnis = new Topni[topniCount];
			
			for(int i = 0 ; i < topniCount ; i++) {
				int temp[] = new int[petchCount];
				for(int j = 0 ; j < 8;j++) {
					temp[j] = sc.nextInt();
				}
				topnis[i] = new Topni(temp, petchCount); //톱니 정보들을 받아와 톱니 클래스 인스턴스에 넣어줌 
			}
			
			int[] op = new int[2]; // op 코드를 받아 넣을 공간 , 0idx: 톱니 번호 , 1idx operation
			for(int opSquence = 0; opSquence < K;opSquence++) {
				op[0] = sc.nextInt()-1; //입력은 톱니번호 1,2,3,4 로 들어오는데 나는 0,1,2,3 임으로
				op[1] = sc.nextInt();
				
				topniMoveAction(topnis,op[0],op[1],topniCount); //명령에 따라 톱니를 작동시킨다.
			}
			
			int sum = 0; 
			for(int i = 0 ; i <topniCount;i++) {
				//System.out.println(i+1 +"번째 톱니 탑 값 : " + topnis[i].getTopValue());
				
				//N = 0, S =1
				sum += topnis[i].getTopValue() << i;
			}
			
			System.out.println(String.format("#%d %d", tc,sum));
			
		}
		
	}


	private static void topniMoveAction(Topni[] topnis, int topniNumber, int topniAct, int topniCount) {
		

		
		int leftValue = topnis[topniNumber].getLeftJunction(); //왼쪽 톱니 값을 가져옴 
		int rightValue = topnis[topniNumber].getRightJunction(); //오른쪽 톱니 값을 가져옴 
		
		//작동 톱니 -1 번쨰 톱니 , 액션을 -하여 반전 , 왼쪽 놈은 나의 왼쪽 톱니값이랑 비교함 
		leftDfs(topnis,topniNumber-1,-topniAct,topniCount,leftValue); //왼쪽 톱니에 영향 
		rightDfs(topnis,topniNumber+1,-topniAct,topniCount,rightValue); //오른쪽 톱니에 영향 
		
		topnis[topniNumber].rotate(topniAct); 
	}

	private static void rightDfs(Topni[] topnis, int topniNumber, int topniAct, int topniCount, int rightValue) {
		
		if(topniNumber >= topniCount) {
			//오른쪽에  톱니 없음 
			return;
		}
		
		int leftValue = topnis[topniNumber].getLeftJunction();
		if(rightValue == leftValue) {//자석의 극이 같음으로 종료 
			return;
		}
		
		rightDfs(topnis, topniNumber+1, -topniAct, topniCount, topnis[topniNumber].getRightJunction());
		
		
		topnis[topniNumber].rotate(topniAct);
	}


	//왼쪽 톱니
	private static void leftDfs(Topni[] topnis, int topniNumber, int topniAct, int topniCount, int leftValue) {
		if(topniNumber < 0) {
			//톱니 없음 
			return;
		}
		
		//자신의 오른쪽 놈이 넘겨준 왼쪽 톱니 값이랑 ---  자신의 오른쪽 톱니값  
		int rightValue = topnis[topniNumber].getRightJunction();
		if(rightValue == leftValue) {//자석의 극이 같음으로 종료 
			return;
		}
		
		leftDfs(topnis, topniNumber-1, -topniAct, topniCount, topnis[topniNumber].getLeftJunction());
		
		
		topnis[topniNumber].rotate(topniAct);
	}
}
