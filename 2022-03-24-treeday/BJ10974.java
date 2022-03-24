package temp;

import java.util.Arrays;
import java.util.Scanner;

public class BJ10974 {
	static int[] arr;
	static int[] input;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		arr = new int[N];
		input = new int[N];
		
		for(int i = 1; i <= N;i++) {
			arr[i-1] = i;
		}
		
		int idx = 0;
		boolean[] visited = new boolean[N];
		//permutation(idx,visited,N);
		//nextPermutation( N); // 이거는 잘 몰라서 SSAFY 교재 보고 했습니다. 
		
		//순열 해봣으면 조합도 해봐야죠?
		//NC2
		//combination(idx,0,2);
		
		//부분집합도 해봐야지
		subSet(idx,N);
	}
	
	private static void subSet(int idx, int N) {
		
		if(idx ==N) {
			System.out.print("{");
			for(int i = 0 ; i < N;i++) {
				if(input[i] == 1) {
					System.out.print(arr[i]+",");
				}
				
			}
			System.out.println("}");
			return;
		}
		
		input[idx] = 0;
		subSet(idx+1,N);
		
		input[idx] = 1;
		subSet(idx+1,N);
		
		
	}

	private static void combination(int idx,int start, int N) {
		if(idx == N) {
			for(int i = 0 ; i <N; i++) {
				System.out.print(input[i]+" ");
			}
			System.out.println();
			return;
		}
		
		for(int i = start; i < arr.length;i++) {
			input[idx] = arr[i];
			combination(idx+1,i+1,N);
		}
		
	}

	public static void nextPermutation(int[] arr,int N) {
		//arr 은 이미 정렬이 되있다. 
		do {
			for(int i = 0 ; i <N; i++) {
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		}while(np(arr,N));
	}
	
	private static boolean np(int[] arr, int n) {
		int i = n-1;
		int j = n-1;
		int k;
		
		while(i > 0&& arr[i-1] > arr[i]) {
			//i 요소 값보다 작은 i-1요소를 구한다.
			i--;
		}
		
		if(i == 0) {
			return false;
		}
		
		while(arr[i-1] > arr[j] ) {
			j--;
		}//끝에서 부터 i-1보다 큰 요소를 구하자, 무조건 걸림 
		swap(arr,i-1,j);
		
		k = n-1;
		while(i < k) {
			swap(arr,i,k);
			i++;
			k--;
			
		}
		
		return true;
	}
	private static void swap(int[] arr,int i , int j ) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void permutation( int idx, boolean[] visited, int n) {
		if(idx == n) {
			for(int i = 0 ; i < n;i++) {
				System.out.print(input[i]+" ");
			}
			System.out.println();
			return;
		}
		
		for(int i = 0 ; i < n ;i++) {
			if(visited[i]) continue;
			visited[i] = true;
			
			input[idx] = arr[i];
			permutation( idx+1, visited, n);
			visited[i] = false;
		}
	}
}
