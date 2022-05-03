package p20220503;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_14567_선수과목 {

	static class Node {

		Node(int to, Node next) {
			this.to = to;
			this.next = next;
		}

		int to;
		Node next;
	}

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//버퍼를 사용하는 인풋 스트림을 시스템에서 제공하는 기본 출력에서 가져온다.
		StringBuilder sb = new StringBuilder();
		
		
		String temp;
		String[] temps;

		temps = br.readLine().split(" ");
		//과목 수와 조건 수를 스플릿하여 가져온다.
		int subjectNum = Integer.parseInt(temps[0]);
		//과목 수 
		int conditionNum = Integer.parseInt(temps[1]);
		//선수 과목 조건 수
		int allProcessAxis = 0;
		//모든 과목을 탐색했다면 종료조건을 걸게 , 몇개의 과목이 통과됬는지 체크하는 변수
		
		
		//1부터 시작이기에 0을 제외 , 따라서 +1 
		int[] inDegreeArr = new int[subjectNum + 1];
		Node[] nodeHead = new Node[subjectNum + 1];

		for (int i = 0; i < conditionNum; i++) {
			sb.append(br.readLine()).append("#");
		}//버퍼드 인풋 스트림리더를 통해 읽어오는데 구분하기 위해 #을 붙임

		StringTokenizer stn = new StringTokenizer(sb.toString(), "#");

		int from; //선수 과목
		int to;  //선수과목을 들어야 들을 수 있는 과목

		for (int i = 0; i < conditionNum; i++) {
			temps = stn.nextToken().split(" ");

			from = Integer.parseInt(temps[0]);
			to = Integer.parseInt(temps[1]);
			
			inDegreeArr[to]--; //선수과목 조건이 걸린 과목의 선수 조건 수를
			// - 음수로 저장한다. 
			
			//간단하게 인접리스트 만드는 법 
			nodeHead[from] = new Node(to, nodeHead[from]);
		}

		Queue<Integer> canStartVertext = new LinkedList<Integer>();
		//이번학기에 들을 수 있는 과목을 저장하는 큐 
		int hacki = 1; //학기. 

		
		for (int i = 1; i <= subjectNum; i++) {
			//선수 과목이 더 이상 없는 과목을 queue에 추가한다. 그리고 
			//선수 과목조건 걸린 갯수 배열을 활용해 , 몇학기만에 들은 건지 기록한다. 
			//이를 위해 전에 음수로 저장함 
			if (inDegreeArr[i] == 0) {
				canStartVertext.add(i);
				inDegreeArr[i] = hacki;
				allProcessAxis++;
				//들은 과목 ++
			}
		}
		
		while (allProcessAxis < subjectNum) {
			//들은 과목과 총과목이 같아지면 탈출

			while (!canStartVertext.isEmpty()) {
				//들을 수 있는 과목을 듣고 , 선수과목 걸린 과목의 조건을 ++해준다. 음수로 되있으니간
				// 0으로 가기 위함이다.
				int nodeName = canStartVertext.poll();
				for (Node now = nodeHead[nodeName]; now != null; now = now.next) {
					inDegreeArr[now.to]++;
				}
			}

			hacki++;
			for (int i = 1; i <= subjectNum; i++) {
				//다시 선수과목 조건들이 다풀린 과목들을 큐에 채워 넣는다. 
				//선수과목 수로 사용했던 배열에는 몇학기가 걸린지 들어감
				if (inDegreeArr[i] == 0) {
					canStartVertext.add(i);
					inDegreeArr[i] = hacki;
					allProcessAxis++;
				}
			}
		}
		
		//걍 이렇게 하고 싶었음 
		Arrays.stream(inDegreeArr,1,inDegreeArr.length).forEach((item)->{
			System.out.print(item+" ");
		});
	}
}
