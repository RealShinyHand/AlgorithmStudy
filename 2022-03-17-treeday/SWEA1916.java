package p0317;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Node{
	int end;
	int cost;
	Node next;
	public Node(int end,int cost,Node next) {
		this.end = end;
		this.cost = cost;
		this.next = next;
	}
}

public class SWEA1916 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int cityNum = Integer.parseInt(br.readLine());
		int busNum = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < busNum; i++) {
			sb.append(br.readLine()+"\n");
		}
		String[] temp = br.readLine().split(" ");
		int start = Integer.parseInt(temp[0]);
		int end = Integer.parseInt(temp[1]);
		
		StringTokenizer stn = new StringTokenizer(sb.toString(),"\n");
		//↑↑↑↑↑ 이 위에는 입출력 부분입니다.
		
		Node[] nodes = new Node[cityNum +1]; // 0은 제외
		
		int vin;
		int vout;
		int cost;
		for(int i = 0 ; i < busNum;i++) { //그래프 구성
			//다른 방식으로는 ArrayList<Node> or ArrayList<ArrayList<Integer>>
			temp = stn.nextToken().split(" ");
			vin = Integer.parseInt(temp[0]);
			vout = Integer.parseInt(temp[1]);
			cost = Integer.parseInt(temp[2]);
			
			nodes[vin] = new Node(vout,cost,nodes[vin]);
		}
		
		int result = shortest_path(nodes,start,end);
		//int result2 = shortest_path2(nodes,start,end);
		//int result3 =shortest_path3(nodes,start,end);
		//System.out.println(result);
		System.out.println(result);
	}

	private static int shortest_path3(Node[] nodes, int start, int end) {
		boolean visited[] = new boolean[nodes.length];
		
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;
			}
		});
		
		pq.add(new Node(start,0,null));
		
		Node cur;
		
		while(!pq.isEmpty()) {
			cur = pq.poll();
			//pq에서 나오는 것은 무조건 최적 경로가 결정된 버텍스일 것이다.
			
			if(cur.end == end)
				return cur.cost;
			if(visited[cur.end]) {
				continue;
			}
			
			visited[cur.end] = true;
			
			for(Node node = nodes[cur.end]; node != null ;node = node.next) {
				if(!visited[node.end]) {
					pq.add(new Node(node.end,cur.cost+node.cost,null));
					//최적이 결정된 버텍스까지의 비용 + 다른 버텍스 가능 비용
				}
			}
		}
		// 범용적 다익스트라에서 못 써먹는 이유 : 
		// 한 정점에서 부터 모든 정점까지의 최적경로를 구하는 건데 , 이거는 그럴라면 
		// 모든 정점이 선택됬는지 계속 확인해야함 . 
		return -3;
	}

	private static int shortest_path2(Node[] nodes, int start, int end) {
		
		int[] distance = new int[nodes.length];
		boolean visited[] = new boolean[nodes.length];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[start] = 0;
		PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;
			}
		});
		
		pq.add(new Node(start,0,null));
		Node cur;
		
		while(!pq.isEmpty()) {
			cur = pq.poll();
			
			if(visited[cur.end]) {
				continue;
			}
			
			visited[cur.end] = true;
			
			for(Node node = nodes[cur.end]; node != null ;node = node.next) {
				if(!visited[node.end]) {
					if(distance[node.end] > distance[cur.end] + node.cost) {
						distance[node.end] = distance[cur.end] + node.cost;
						pq.add(new Node(node.end,distance[node.end],null));
					}
				}
			}
		}
		
		return distance[end];
		
	}

	private static int shortest_path(Node[] nodes, int start, int end) {
		// 최대 정점 1000 이고 최대 가중치가 100000 이니깐 대략 최대 값이 1억정도되니깐
		// int형 써도 문제 없다.
		
		int[] distance = new int[nodes.length];
		boolean visited[] = new boolean[nodes.length];
		
		Arrays.fill(distance, Integer.MAX_VALUE);
		
		distance[start] = 0; //시작점 초기화
		
		while(true) { //종료 조건이 없는 순수 다익스트라이면 for(int i = 0 ; i < nodes.length -2; i++)
						//최적점이 시작점으로 하나 선택되고 , V-1
						//마지막으로 선택된 놈은 자동으로 최적점 V-2
			//여기서는 최적점이 도착점으로 선택되면 종료이다.
			
			int minIndex = -1; //vertext로 0을 안쓰지만 0보다는 -1로해서 차라리 오류가나게하는 게 debug에 좋다.
			int minValue = Integer.MAX_VALUE;
			
			for(int i = 1 ; i < nodes.length;i++) {
				//방문하지 않은 정점 중 거리 최솟값을 찾는다.
				if( !visited[i] && distance[i] < minValue) {
					minIndex = i;
					minValue = distance[i];
				}
			}
			
			visited[minIndex] = true;
			if(minIndex == end) { //도착지에서 더 짧은 경로를 찾을 필요없음
				break; // 경로 검색에 사용된 vertext는 나중의 시퀀스에서 비용이 업데이트 되지않는다.
						//인터넷 찾아보면 증명하는 거 나오는데 난 모름 
			}
			
			for(Node node = nodes[minIndex]; node!= null;node =node.next) {
				if(!visited[node.end]) { //최적점으로 선택되지 않았고
					if(distance[minIndex] + node.cost < distance[node.end]) {
						//이전에 선택된 비용보다 작은 경우 . 비용을 작은 걸로 업데이트 
						distance[node.end] = distance[minIndex] + node.cost;
					}
				}
			}
			
		}

		return distance[end];
	}
}
