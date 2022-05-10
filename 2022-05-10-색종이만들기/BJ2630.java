package p20220510;

import java.util.Scanner;

public class BJ2630 {
	
	
	static int whitePaperNum = 0;
	static int bluePaperNum = 0;
	
	
	public static void main(String[] args) {
		Scanner sc  =new Scanner(System.in);
		int N = sc.nextInt();
		int map[][] = new int[N][N];
		for(int i = 0 ; i < N ; i++) {
			for(int j = 0 ; j < N; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		//설명이 잘 이해되지는 않지만 색종이 크기가 (2x2)*n 만 되나 보다 ?
		// 자르는 방식 떄문에 .
		// 무조건 4등분이 생김 , 그리고 그 4등분에서 또 4등분 또 4등분
		//음...문제 선정당시.. 이런지 몰랐어요.. 
		cuttinPaper(map,0,N,0,N);
	
		System.out.println(whitePaperNum);
		System.out.println(bluePaperNum);
	}

	//4등분하고 다 같은 색인지 검사하는 방법 , 
	//또는 한개로 다 만든다음에 이어주는 방법 ?? 안될듯 이건 되긴할듯 .. 난 안함.
	private static void cuttinPaper(int[][] map, int startRow,int endRow , int startCol,int endCol) {
		
		//우선은 가장 큰 색종이
		
		int kkk = map[startRow][startCol];
		for(int i = startRow; i < endRow;i++) {
			for(int j = startCol;j<endCol;j++) {
				if((kkk ^ map[i][j]) == 1) {
					//둘의 값이 다른 경우 1이 나온다.
					kkk = -1;
					break;
				}
			}
		}
		
		if(kkk == -1) {
			int rowMid = (startRow + endRow) /2;
			int colMid = (startCol+endCol)/2; //입력 보면은 2로 딱 나뉘어진다. 8이라 카면 4가 되야함 , 0,1,2,3 || 4,5,6,7
		
			//4등분 하고 검사해야함
			cuttinPaper(map, startRow, rowMid, colMid, endCol); //1사분면
			cuttinPaper(map, startRow, rowMid, startCol, colMid); //2사분면
			cuttinPaper(map, rowMid, endRow, startCol, colMid); //3사분면
			cuttinPaper(map, rowMid, endRow, colMid, endCol); //4사분면
		}else if(kkk == 0) {
			whitePaperNum++;
		}else if(kkk==1){
			bluePaperNum++;
		}else {
			System.out.println("에러");
		}
		
	}
	
	private static void cuttinPaper2(int[][] map, int startRow,int endRow , int startCol,int endCol) {
		
	}
		
}
