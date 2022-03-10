package p0310;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SWEA2117 {
	
	static class House{
		int x;
		int y;
		static House valueOf(int x,int y) {
			House temp = new House();
			temp.x = x;
			temp.y = y;
			return temp;
		}
		
		public int getDistance(int x,int y){
			return Math.abs(this.x -x) + Math.abs(this.y-y);
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1 ; tc <= T ; tc++) {
			String temp[]  = br.readLine().split(" ");
			
			int N = Integer.parseInt(temp[0]); // 지도 크기
			int M = Integer.parseInt(temp[1]); //하나의 집이 내는 비용
			
			ArrayList<House> houstList = new ArrayList<>();
			
			for(int i = 0 ; i < N ;i++) {
				String[] k = br.readLine().split(" ");
				for(int j = 0; j < N ; j++) {
					if(k[j].equals("1")) {
						houstList.add(House.valueOf(j, i));
					}
				}
			}
			
			int max = 0;
			int count=0;
			for(int k = 1 ; k < N*(N/2 + 1) ;k++) {
			//이 포문의 조건식을 정확하게 계산하지 못하겠다. 
			//의도한 바는 배열 중앙에 위치했을 때 전체 배열(전체 위치, 모든 집 포함)을
			//위해 N * (N/2 +1)을 썻다. 잘 모르겠다. 최적화가 필요하다 . 
				
				// 비용이 k 일때, 
				//전체 맵들을 순회하면서
				//거리가 되는 집의 갯수를 계산
				for(int i = 0 ; i < N ; i++) {
					for(int j = 0 ; j < N; j++) {
						
						count = 0;
						for(House house : houstList) {
							//전체 집 리스트를 순회 현재 x,y에서 거리가 k 이하인 
							//집일 경우 count++;
							//k가 1일때 거리는 0 , k가 2 : 거리 1이하 .....
								if(house.getDistance(j, i) < k) {
									count++;
								}
						}
						
						int money = getSpend(k, M, count);//비용 - 이익 = 결과 반환
						if(money >= 0) { //손해는 안나고 최대한 많은 집에 서비스 제공
							if(max < count) {
								max = count;
							}
						}
					}
				}
			}
			System.out.println(String.format("#%d %d", tc,max));
		}
	}
	
	static int getSpend(int k, int m,int house) {
		int spendMoney = k * k + (k-1)*(k-1); //이 부분은 가장 상위 for 문에서
		//k가 결정될 때 고정으로 박으면 되는데, 귀찮다.
		int getMoney = m * house;
		return getMoney - spendMoney;
	}
}
