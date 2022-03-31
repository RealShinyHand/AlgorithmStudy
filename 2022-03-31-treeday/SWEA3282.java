package p20220331;

import java.util.Scanner;

public class SWEA3282 {

	static class Thing {
		int weight;
		int money;

		public Thing(int weight, int money) {
			super();
			this.weight = weight;
			this.money = money;
		}

		public Thing() {
			super();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();

		for (int tc = 1; tc <= T; tc++) {
			int thingNum = sc.nextInt();// 물건 갯수
			int bagVolume = sc.nextInt(); // 가방에 담을 수 있는 무게

			int map[][] = new int[thingNum + 1][bagVolume + 1];

			Thing[] things = new Thing[thingNum + 1];

			for (int i = 1; i <= thingNum; i++) {
				things[i] = new Thing();
				things[i].weight = sc.nextInt();
				things[i].money = sc.nextInt();
			}

			for (int no = 1; no < thingNum; no++) { // 물건 루프
				for (int weight = 1; weight <= bagVolume; weight++) {// 무게 루프
					if (things[no].weight > weight) {// 현재no의 물건을 담을 수 없으면
						map[no][weight] = map[no - 1][weight]; // 현재 허용 무게에서 이전까지 담아와진 최적
					} else {// 현재 no 의 물건을 담을 수 있으면
						map[no][weight] = Math.max(map[no - 1][weight],
								map[no - 1][weight - things[no].weight] + things[no].money);
						// 현재 허용 무게에서 이전까지 담아와진 최적 OR 현재 물건 가격+현재 물건 무게를 뺀 거에서의 최적
					}
				}
			}
			
			// 마지막 품목에 대해서는 사실상 최대 무게에 대해서만 구하면된다. 
			//하지만 시간차도 별로 없을꺼고 괜히 복잡해지니 하지말자. 
			if (things[thingNum].weight > bagVolume) {
				map[thingNum][bagVolume] = map[thingNum - 1][bagVolume]; 
			} else {
				map[thingNum][bagVolume] = Math.max(map[thingNum - 1][bagVolume],
						map[thingNum - 1][bagVolume - things[thingNum].weight] + things[thingNum].money);
			}
			

			System.out.println("#"+tc+" "+map[thingNum][bagVolume]);
		}
	}
}
