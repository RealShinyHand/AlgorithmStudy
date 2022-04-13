package p20220413;

import java.util.Scanner;

public class SWEA6026 {
	static final long MODULE_VALUE = 1_000_000_007;
	//약 10억 
	//long은 약 10경 
	
	static long fatorial[];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		

		
		
		fatorial = new long[101];
		genrateFatorial(100);
		
		for(int tc=1 ; tc<=T ; tc++) {
			int M = sc.nextInt(); //지문 갯수 
			int N = sc.nextInt(); //비밀번호 자릿수
			
			//너무 어려운데? 
			//일단 1<=M <= N<=100이다. 
			//전사함수 맞다. 아니라고 생각한 이유는 비밀번호 말고도 딴 거 친줄 알음 
			//따라서 M개는 무조건 사용되야함
			//지문이 있는 키는 다 써야한다. 
			//교재 38p를 보면
			// 전사의 갯수 = 전체함수 갯수 - (전사가 아닐 때)
			//3c3*3^4 - 3C2*2^4 + 3C1*1^4
			//와 같이 + 와 -가 반복된다. 왜인지는 모른다. 외우자 . 
			
			long flag = 1;
			long result = 0;
			int cnt = 0;
			for(int i = M ; i > 0; i--) {
				//맨처음에는 MCi*i^N
				//i--
				//그다음은 -MCi*i^N
				//그다음은 부호가 또 바뀌고 계속해서 반복 until i>=1
				//외우자  외우자 외우자 전사함수 갯수 공식 외우자 
				
				long nCrValue = combinationCount(M,i);
				result+= flag*((nCrValue * fastExponent(i, N))%MODULE_VALUE);
				flag = -flag;

				if(result < 0) { //result가 음수가 될때가 있더라 ... 그럼 한 41tc에서 틀림
					result = MODULE_VALUE + result;
				}else {
					result= (result ) % MODULE_VALUE;
				}
				
				
			}
			System.out.println(String.format("#%d %d", tc,result) );
		}
	}
	
	private static void genrateFatorial(int m) {
		fatorial[0] = 1;
		fatorial[1] = 1;
		fatorial[2] = 2;
		for(int i = 3; i <= m;i++) {
			fatorial[i] = (fatorial[i-1] * i)%MODULE_VALUE;
		}
	}

	private static long combinationCount(int n, int r) {
		// nCr 하는 곳 
		//nCr = n! / (n-r)!*r!
		long up = fatorial[n];
		long bottom = (fatorial[n-r] * fatorial[r])%MODULE_VALUE;
		//bottom 역수를 취해야함 
		//bottom^p (mod p)= bottom (mod p)
		//nCr (mod P)를 구하는 거니깐 , 모듈러스연산을 분자 분모에 나눠주면 이래됨 .
		// n! (mod p) / ((n-r)! * r!)(mod p) =>
		// n! (mod p) / ((n-r)! * r!)^p-2 (mod p)
		//bottom^(p-2) mod p = 1/bottom (mode p)
		bottom = feramaSmallLaw(bottom,MODULE_VALUE-2,MODULE_VALUE);
		
		return (up * bottom) % MODULE_VALUE;
	}

	private static long feramaSmallLaw(long bottom, long seung, long moduleValue) {
		
		bottom = fastExponent(bottom, seung);
		return bottom;
	}

	public static  long fastExponent(long x,long seung) {
		
		long result = 1;
		x = x % MODULE_VALUE;
		while(seung > 0) {
			if((seung & 1) == 1) {
				result = (result * x)% MODULE_VALUE;
			}
			x = (x*x)%MODULE_VALUE;
			seung >>=1;
		}
		return result;
	}
}
