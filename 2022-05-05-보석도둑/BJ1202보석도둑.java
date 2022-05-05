package p20220505;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BJ1202보석도둑 {

	static class Product {
		int weight;
		int value;

		Product(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] temps = br.readLine().split(" ");
		int ThingNum = Integer.parseInt(temps[0]);
		int BagNum = Integer.parseInt(temps[1]);

		ArrayList<Product> products = new ArrayList<>();
		int bags[] = new int[BagNum];

		int weight, value;

		for (int i = 0; i < ThingNum; i++) {
			temps = br.readLine().split(" ");
			weight = Integer.parseInt(temps[0]);
			value = Integer.parseInt(temps[1]);

			products.add(new Product(weight, value));
		}

		for (int i = 0; i < BagNum; i++) {
			bags[i] = Integer.parseInt(br.readLine());
		}

		products.sort(new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				return Integer.compare(o1.weight,o2.weight);
			}
		}); //물건들을 무게 오름차순으로 정렬 .... 
		
		Arrays.sort(bags); //가방을 담을 수 있는 무게순으로 정렬

		PriorityQueue<Product> pq = new PriorityQueue<>(new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				return Integer.compare(o2.value,o1.value );
			}
		}); //현재 무게의 가방에서 담을 수 있는 물건들을 가격 내림차순으로 정렬한 힙이다.
		
		
		int leftBound = 0; // productList 시작하는 곳 , 이미 빼낸 거는 안쓰기
		long result = 0;
		for(int i = 0 ; i < BagNum ; i++) {
			int maxWeight = bags[i];//현재까지의 가방 시퀀스에서 넣을 수 있는 무게 맥스
			
			for(;leftBound < products.size();leftBound++) {
				Product prd = products.get(leftBound);
				if(prd.weight > maxWeight) {
					break;
				}
				pq.add(prd);
				//arrayList에서 빼낸 product 주소하고 pq에 들어간 주소하고 겹치는데 , 
				//가상적으로 arrayList에 있는 product는 더 이상 접근할 리 없다!.
			}//현재 선택된 가방이 담을 수 있는 무게들을 프로덕트 리스트에서 계속 빼내서 
			//가격 내림차순 queue에 계속 저장한다. 
			
			int currentAbleMaxValue = 0;
			if(!pq.isEmpty()) { //현재 가방에 넣을 수 있는 보석이 없을 수 있다.
				currentAbleMaxValue = pq.poll().value;
			}
			result +=currentAbleMaxValue;
			
		}
		System.out.println(result);
	}
}
