import java.util.*;

/**
1
20 3
2 2 3 2 2 2 2 3 3 4 4 3 2 2 3 3 3 2 2 3
4 4 1 4 4 1 4 4 1 1 1 4 1 4 3 3 3 3 3 3
4 4 1 100
7 10 3 40
6 3 2 70

1
60 4
0 3 3 3 0 1 2 2 2 1 2 2 3 3 4 4 0 3 0 1 1 2 2 3 2 2 3 2 2 0 3 0 1 1 1 4 1 2 3 3 3 3 3 1 1 4 3 2 0 4 4 4 3 4 0 3 3 0 3 4 
1 1 4 1 1 1 1 1 1 4 4 1 2 2 3 2 4 0 0 0 4 3 3 4 3 3 0 1 0 4 3 0 4 3 2 3 2 1 2 2 3 4 0 2 2 1 0 0 1 3 3 1 4 4 3 0 1 1 1 1 
6 9 1 180
9 3 4 260
1 4 1 500
1 3 1 230

 */

class Solution {
	static Scanner sc = new Scanner(System.in);
	static int M, A, result, ans;
	static int[][] moves;
	static charger[] bc;
	static int USER_A = 0, USER_B = 1;
	static int[][] dirs = new int[][] { { 0, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0 } };
	static user[] users;
	static int[][] check;
	static int[] result_arr;
	
	static void input() {
		result = 0;
		ans = 0;
		M = sc.nextInt(); // 사람 걷는 수
		A = sc.nextInt(); // bc의 갯수
		moves = new int[2][M + 1];
		bc = new charger[A];
		users = new user[2];
		users[USER_A] = new user(1, 1);
		users[USER_B] = new user(10, 10);
		check = new int[M + 1][M + 1];
		result_arr = new int[M + 1];
		
		
		// 초기 위치 추가
		moves[USER_A][0] = 0;
		moves[USER_B][0] = 0;

		for(int j = 0; j < 2; j++) {
			for(int i = 1; i < M + 1 ; i++) {
				moves[j][i] = sc.nextInt();
			}
		}
		
		for(int i = 0; i < A ; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			int dir = sc.nextInt();
			int value = sc.nextInt();
			bc[i] = new charger(x, y, dir, value);
		}
		
//		Out.print("입력 이동", moves);
//		Out.print("입력 사람", users);
//		Out.print("입력 bc", bc);
	}
	
	static int distance( user u , charger c) {
		return Math.abs(u.x - c.x) + Math.abs(u.y - c.y);
	}

	static void pro() {
		// 사용자 초기부터 충전 확인
		// M + 1으로 시작
		for(int i = 0; i < M + 1; i++) {
			// 유저 이동
			for(int user_num = 0; user_num < 2; user_num++) {
				users[user_num].move(moves[user_num][i]);
			}
			
//			Out.print_line();
//			Out.print("사람 이동 확인", users);
			ans+= count();
		}
		
//		result = Arrays.stream(result_arr).sum();
	}
	
	static int count() {
		result = 0;
		recur(0, 0);
		return result;
	}

	// bc의 수만큼 유저들을 확인
	private static void recur(int user, int ans) {
		// TODO Auto-generated method stub
		// 사람수 만큼 탐색
		if(user == 2) {
			result = Math.max(ans, result);
//			result_arr[move_idx] = Math.max(result_arr[move_idx], ans);
		}else {
			// bc의 갯수 만큼 탐색
				// 유저당 bc포함 여부 확인
			for(int bc_num = 0 ; bc_num < A; bc_num++) {
				int dis = distance(users[user], bc[bc_num]); // 유저와 bc의 거리
				if(bc[bc_num].used) continue;

				if(dis <= bc[bc_num].d) {
					bc[bc_num].used = true;
					recur(user + 1, ans + bc[bc_num].value);
					bc[bc_num].used = false;
				}
					// 1명 이 포함이 안되는 경우
					recur(user + 1, ans);
			}
		}
	}

	public static void main(String args[]) throws Exception {

		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
			input();
			pro();
			
			System.out.printf("#%d %d\n", test_case, ans);
		}
	}
	
	static class charger{
		int x, y;
		int d;
		int value;
		boolean used;

		public charger(int x, int y, int d, int value) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
			this.value = value;
			this.used = false;
		}

		@Override
		public String toString() {
			return "[x=" + x + ", y=" + y + ", d=" + d + ", value=" + value + ", used=" + used + "]\n";
		}
	}
	
	static class user{
		int x, y;

		public user(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public void move(int dir) {
			this.x = this.x + dirs[dir][0];
			this.y = this.y + dirs[dir][1];
		}

		@Override
		public String toString() {
			return "[x=" + x + ", y=" + y + "]";
		}
	}
}