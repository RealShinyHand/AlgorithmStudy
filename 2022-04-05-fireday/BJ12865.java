package p20220405;

import java.util.Scanner;

public class BJ12865 {
	static class Product{
		
		int weight;
		int value;
		
		public Product(int weight, int value) {
			super();
			this.weight = weight;
			this.value = value;
		}
		
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int productNum = sc.nextInt();
		int maxWeight = sc.nextInt();
		
		Product[] products = getProduct(productNum,sc);
		
		int[] prev = new int[maxWeight+1];
		int[] cur = new int[maxWeight +1];
		
		
		for(int i =1 ; i <= productNum;i++) {
			Product pd = products[i];
			for(int w = 1; w <= maxWeight;w++) {
				if(w < pd.weight) { //현재 물건을 담지 못함 ..
					cur[w] = prev[w]; //현재 무게 최대 가치는 이전에 구한 최대가치 
				}else {
					//현재 무게 최대 가치는, 이전 무게 최대가치 or 현재 물건 가치 + 남은 무게 최대가치 
					cur[w] = Math.max(prev[w], prev[w-pd.weight] + pd.value);
				}
			}
			int[] temp = null;
			temp = prev;
			prev = cur; //다음 시퀀스에서는 이전 가치가 현재 구한 가치가 되야하니깐.
			cur = temp; 
		}
		
		System.out.println(prev[maxWeight]);
	}
	private static Product[] getProduct(int productNum, Scanner sc) {
		Product[] temp = new Product[productNum+1];
		
		for(int i = 1 ; i <= productNum;i++) {
			temp[i] = new Product(sc.nextInt(), sc.nextInt());
		}
		
		return temp;
	}
}
