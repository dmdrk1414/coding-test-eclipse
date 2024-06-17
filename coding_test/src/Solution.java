import java.util.*;
import java.io.*;

/**
1
5
0 1 1 0 0
0 0 1 0 3
0 1 0 1 0
0 0 0 0 0
1 0 5 0 0

1
9
0 0 0 1 0 0 0 0 0
0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 8
7 0 0 0 0 1 0 0 0
0 0 0 0 0 1 1 0 0
0 0 0 0 0 0 0 0 0
1 0 0 0 0 1 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0

 */

class Solution {
	static int FIRST = 0;
	static int SECOND = 1;
	static Scanner sc = new Scanner(System.in);
	static int[] result;
	static int N;
	static List<person> person_q;

	static PriorityQueue<person>[] per_stair_q; // 목적하는 계단을 나눈다 
	static stair[] stairs; // 계단 정보
	static Queue<person>[] wait_q; // 기달리는 사람들
	static Queue<person>[] stair_q; // 계단에 있는 사람들

	static void input() {
		N = sc.nextInt();
		result = new int[3];
		person_q = new ArrayList<>();
		stairs = new stair[2];
		result[2] = 99999;



		int per_num = 1;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {

				int num = sc.nextInt();
				if(num == 1) {
					person_q.add(new person(i, j, per_num++));
				}
				if(num > 1) {
					if(stairs[0] == null) {
						stairs[0] = new stair(i, j, num);
					}else {
						stairs[1] = new stair(i, j, num);
					}
				}
			}
		}
//	    - 1번, 2번, 3번, 4번 사람은 길이가 2인 1번 계단을 통해 이동
//	    - 5번, 6번 사람은 길이가 5인 2번 계단을 통해 이동
//		print("확인 배열", new int[] {0, 1, 0, 0, 0, 1, 1 });
//		cal_time(new int[] {0, 1, 0, 0, 0, 1, 1 });
	}


	static void cal_time(int[] stair_infos) {
		result[FIRST] = 0;
		result[SECOND] = 0;
		per_stair_q = new PriorityQueue[2];
		wait_q = new LinkedList[2]; // 기달리는 사람
		stair_q = new LinkedList[2]; // 계단에 있는 사람
		for(int i = 0; i < 2; i++) {
			per_stair_q[i] = new PriorityQueue<>();
			wait_q[i] = new LinkedList<>();
			stair_q[i] = new LinkedList<>();
		}

		// 목적을 하는 계단의 정보에 따른 계산
		for(int i = 0; i < person_q.size(); i++) {
			person per = person_q.get(i).clone();
			if(stair_infos[i] == FIRST) {
				per.live = distance(per, stairs[FIRST]); // 거리 계산
				per_stair_q[FIRST].add(per);
			}
			else if(stair_infos[i] == SECOND) {
				per.live = distance(per, stairs[SECOND]); // 거리 계산
				per_stair_q[SECOND].add(per);
			}
		}
		
//		print("목적하는 계단과 사람관리 q", per_stair_q);

		int time = 0;
		while(true) {
			// 첫번째 계단 관리
			cal_stair(time, per_stair_q[FIRST], wait_q[FIRST], stair_q[FIRST], stairs[FIRST], FIRST);

			// 두번째 계단 관리
			cal_stair(time, per_stair_q[SECOND], wait_q[SECOND], stair_q[SECOND], stairs[SECOND], SECOND);

			// 모든 큐가 비어있다면
			if(valid(per_stair_q, wait_q, stair_q))
				break;
//			print("--시간 확인", time);
//			print("--목포 확인", per_stair_q);
//			print("--대기 확인", wait_q);
//			print("--계단 확인" + stairs[0].num+ " " + stairs[1].num, stair_q);
//			print();
			time++;
		}
		
		int max_result = Math.max(result[FIRST] , result[SECOND]);
		result[2] = Math.min(result[2], max_result);

	}



	static void cal_stair(int time, PriorityQueue<person> per_stair_q, Queue<person> wait_q
			, Queue<person> stair_q, stair stair, int stair_type) {
		// 대기하는 사람들 관리
		for(person waite_per: wait_q) {
			waite_per.waite++;
		}
		
		// 계단에 있는 사람 관리
		for(person stair_per: stair_q) {
			stair_per.stair++;
		}

		int stair_q_size = stair_q.size();
		for(int i = 0; i < stair_q_size; i++) {
			person stair_per = stair_q.poll();
			
			// 계단을 다타면?
			if(stair_per.stair == stair.num) {
				// 시간 계산
				int temp = stair_per.live + stair_per.waite + stair_per.stair;
				result[stair_type] = Math.max(result[stair_type], temp);
			}else {
				stair_q.add(stair_per);
			}
			
		}
		
		// 계단에 사람 넣기
		while(stair_q.size() < 3) {
			if(wait_q.isEmpty()) break;
			stair_q.add(wait_q.poll());
		}

		// 도착한 사람들이 있는가?
		int per_stair_q_size = per_stair_q.size();
		for(int i = 0; i < per_stair_q_size; i++) {
			person check_per = per_stair_q.peek();
			
			// 도착한 사람이있으면
			if(check_per.live == time) {
				person live_per = per_stair_q.poll();
				wait_q.add(live_per);
			}		
		}
	}

	private static boolean valid(PriorityQueue<person>[] per_stair_q2,
			Queue<person>[] wait_q2, Queue<person>[] stair_q2) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < 2 ; i++) {
			if(!per_stair_q2[i].isEmpty()) return false;
			if(!wait_q2[i].isEmpty()) return false;
			if(!stair_q2[i].isEmpty()) return false;
		}
		
		return true;
	}


	static int distance(person p, stair s) {
		return Math.abs(p.x - s.x) + Math.abs(p.y - s.y);
	}
	
	static int cnt = 0;
	
	static void recul(int idx, int[] stair_infos) {
		if(idx == person_q.size()) {
			cal_time(stair_infos);
//			Print.print("순열 배열 확인", stair_infos);
//			print("첫번째 계단 최대시간 확인", result[FIRST]);
//			print("두번째 계단 최대시간 확인", result[SECOND]);
//			print("결과 계단 최대시간 확인", result[2]);
//			print();
//			if(++cnt == 4) System.exit(0);
		}else {
			for(int i = 0; i < 2; i++) {
				stair_infos[idx] = i;
				recul(idx + 1, stair_infos);
				stair_infos[idx] = 0;
			}
		}
	}



	static void pro() {
		recul(0, new int[person_q.size()]);
	}

	public static void main(String args[]) throws Exception
	{
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			input();
			pro();

			System.out.printf("#%d %d\n", test_case, result[2]);
		}
	}
	
	static class person implements Comparable<person>{
		int x, y;
		int status; // 0: 걷기, 1: 대기, 2: 계단
		int live; // 도착 , 오른 차
		int waite; // 대기 , 오른 
		int stair; // 계단
		int num;

		public person(int x, int y,int num) {
			this.x = x;
			this.y = y;
			this.status = 0;
			this.live = 0;
			this.waite = 0;
			this.stair = 0;
			this.num = num;
		}
		
		public person clone() {
			person clone = new person(x, y, num);
			clone.status = status;
			clone.live = live;
			clone.waite = waite;
			clone.stair = stair;

			return clone;
		}
		
		@Override
		public int compareTo(person p) {
			if(this.live != p.live) {
				return Integer.compare(this.live, p.live);
			}
			
			if(this.waite != p.waite) {
				return Integer.compare(p.waite, this.waite);
			}
			
			if(this.stair != p.stair) {
				return Integer.compare(p.stair, this.stair);
			}

			return 0;
		}

		@Override
		public String toString() {
			return "person [x=" + x + ", y=" + y + ", status=" + status + ", live=" + live + ", waite=" + waite
					+ ", stair=" + stair + ", num=" + num + "]";
		}

		
		
	}
	
	static class stair{
		int x, y;
		int num;

		public stair(int x, int y, int num) {
		this.x = x;
		this.y = y;
		this.num = num;
		}

		@Override
		public String toString() {
			return "stair [x=" + x + ", y=" + y + ", num=" + num + "]";
		}
		
	}
}