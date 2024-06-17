package window;

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

class  점심_식사시간{
	static int FIRST = 0;
	static int SECOND = 1;
	static Scanner sc = new Scanner(System.in);
	static int[] result;
	static int N;
	static List<person> person_q;

	static PriorityQueue<person> per_stair_q; // 목적하는 계단을 나눈다 
	static stair[] stairs; // 계단 정보
	static Queue<person> wait_q; // 기달리는 사람들
	static Queue<person> stair_q; // 계단에 있는 사람들

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
	}


	static void cal_time(int[] stair_infos, int TYPE) {
		result[TYPE] = 0;
		per_stair_q = new PriorityQueue<>();
		wait_q = new LinkedList<>(); // 기달리는 사람
		stair_q = new LinkedList<>(); // 계단에 있는 사람

		// 목적을 하는 계단의 정보에 따른 계산
		for(int i = 0; i < person_q.size(); i++) {
			person per = person_q.get(i).clone();
			if(stair_infos[i] == TYPE) {
				per.live = distance(per, stairs[TYPE]); // 거리 계산
				per_stair_q.add(per);
			}
		}
		

		int time = 0;
		while(true) {
			// 첫번째 계단 관리
			cal_stair(time, TYPE);

			// 모든 큐가 비어있다면
			if(valid(per_stair_q, wait_q, stair_q))
				break;

			time++;
		}
	}


	static void cal_stair(int time, int TYPE) {
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
			if(stair_per.stair == stairs[TYPE].num) {
				// 시간 계산
				int temp = stair_per.live + stair_per.waite + stair_per.stair;
				result[TYPE] = Math.max(result[TYPE], temp);
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

	private static boolean valid(PriorityQueue<person> per_stair_q2,
			Queue<person> wait_q2, Queue<person> stair_q2) {
		
		if(!per_stair_q2.isEmpty()) return false;
		if(!wait_q2.isEmpty()) return false;
		if(!stair_q2.isEmpty()) return false;
		
		return true;
	}


	static int distance(person p, stair s) {
		return Math.abs(p.x - s.x) + Math.abs(p.y - s.y);
	}
	
	static int cnt = 0;
	
	static void recul(int idx, int[] stair_infos) {
		if(idx == person_q.size()) {
			cal_time(stair_infos, FIRST);
			cal_time(stair_infos, SECOND);

			// 계산
			int max_result = Math.max(result[FIRST] , result[SECOND]);
			result[2] = Math.min(result[2], max_result);
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