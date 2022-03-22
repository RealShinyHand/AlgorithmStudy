package p0322;

import java.util.Scanner;

public class bj16637 {
	static int NUM ;
	static int Result = Integer.MIN_VALUE;
	public static void main(String[] args) {
		//실제로 괄호 넣고 계산하면 시간 넘을거 같음 
		
		Scanner sc = new Scanner(System.in);
		
		NUM = sc.nextInt();
		sc.nextLine();
		String str = sc.nextLine();
		char[] calChars = str.toCharArray();
		
		int total = calChars[0] - '0';
		//첫번쟤 숫자는 무조건 더해짐
		int idx = 2; //1번째는 연산자임. 이과정은 괄호가 전혀 상관없음
		//idx는 항상 숫자를 가리킬 거임 
		process(calChars,idx,total);
		
		System.out.println(Result);
	}
	
	private static void process(char[] calChars, int idx, int total) {
		
		if(idx >= NUM) {//끝에 도달한 경우,
			//아직 맨끝 숫자하고 연산자는 계산안됬음 
			if(Result < total) {
				Result = total;
			}
			return;
		}
		//이번에 괄호가 전혀 관계 없다면
		process(calChars, idx+2, calc(total,calChars[idx-1],calChars[idx]));
		
		
		if(idx + 2 < NUM) {
			//아하 괄호안에는 연산자 하나니깐 현재 토탈하고 괄호안에꺼 정해주면됨 
			//괄호가 있다고 치면 일단 무조건 n1,op,n2는 먼저됨 중복괄호가 없으니깐 
			int temp = calc(calChars[idx]-'0',calChars[idx+1],calChars[idx+2]);
			total = calc(total,calChars[idx-1],temp);
			// 3+ (8 * 7) - 9 * 2,,, 괄호 시작이전은 이미 계산되서 total 에 포함 
			// 3+ 8 * 7 - (9 * 2)
			process(calChars, idx+4, total);
			//여기서 만약 *2 가 없다 치면 괄호있는 경우 계산시 오류가 날거기 때문에 위에 조건문이있어야한다. 
		}
		
	}

	static int calc(int total, char op,char ch2) {
		//문제의 연산자는 3개 밖에없다.
		//그리고 숫자는 전부 양수
		int num2 = ch2-'0';
		
		return  calc( total,  op, num2) ;
	}
	
	static int calc(int total, char op,int num2) {
		
		if(op == '+') {
			return total +num2;
		}else if(op=='-'){
			return total - num2;
		}else {
			return total * num2;
		}
	}
}
