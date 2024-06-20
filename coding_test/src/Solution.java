import java.util.*;

/**
1
4
9 8 9 8
4 6 9 4
8 7 7 8
4 5 3 5

1
8
18 18 7 16 15 3 5 6
3 6 8 3 15 13 15 2
4 1 11 17 3 4 3 17
16 2 18 10 2 3 11 12
11 17 16 2 9 16 5 4
17 7 6 16 16 11 15 8
2 1 7 18 12 11 6 2
13 12 12 15 9 11 12 18

1
14
11 31 22 3 36 20 10 23 6 5 22 22 19 29
9 7 13 9 31 15 7 1 13 33 11 24 7 36
21 22 6 19 23 4 6 21 14 36 9 4 30 21
17 2 30 13 26 36 2 13 32 27 36 5 28 16
8 20 12 16 31 10 32 15 19 24 34 20 1 16
17 18 22 3 10 2 30 26 27 29 10 16 24 12
25 32 31 20 10 29 19 11 32 23 28 20 33 24
9 13 19 4 6 27 24 5 16 2 8 34 2 7
21 5 5 26 2 35 7 36 21 22 23 33 2 6
16 21 15 21 12 11 13 28 3 3 14 23 16 4
1 31 35 33 23 29 12 18 24 25 19 33 17 13
29 6 30 19 33 14 35 14 6 23 27 16 12 24
26 31 30 10 16 21 7 4 16 25 31 19 21 8
12 5 2 4 4 27 29 2 18 20 19 26 32 31

 */
class Solution {
	static	Scanner sc = new Scanner(System.in);
	static int result;
	static int N;
	static cafe[][] map;
	static boolean[][] visited;
	static cafe init_cafe;
	static int[][] dirs = new int[][] { {1, 1}, {-1, 1}, {-1, -1}, {1, -1} };
	
	static int[][] test;
	
	static void input() {
		result = 0;
		N = sc.nextInt();
		map = new cafe[N][N];
		visited = new boolean[N][N];
		test = new int[N][N];
		
		for(int i = 0; i < N ; i++) {
			for(int j = 0; j < N ; j++) {
				int num = sc.nextInt();
				map[i][j] = new cafe(i, j, num, 0);
			}
		}
	}
	
	static int check_dir(int xx, int yy, int nx, int ny) {
		if(xx < nx) {
			if(yy > ny) return 3;
			if(yy < ny) return 0;
		}else {
			if(yy > ny) return 2;
			if(yy < ny) return 1;
		}
		
		return -9;
	}
	
	// 왔던길 체크
	static boolean valid(List<cafe> traking, cafe next_cafe) {
		for(cafe c: traking) {
			if(c.type == next_cafe.type) return true;
		}
		
		return false;
	}
	
static int pre_cnt;
	static boolean last_valid(int[] dir_va,  cafe now_cafe, int pre_dir, int cnt_) {
//		if(!init_cafe.is(now_cafe.x, now_cafe.y)) return false;
		if(pre_cnt != cnt_) {
			pre_cnt = cnt_;
		}else {
			return false;
		}

		int dir = 0; int cnt =0;

		for(int i = 0; i < 4; i++) {
			if(dir_va[i] == 1) cnt++;
			if(dir_va[i] == 0) dir = i;
		}

		if(cnt < 3) return false;

		int nx = 0, ny = 0;
		if(cnt == 4) {
			nx = now_cafe.x + dirs[pre_dir][0];
			ny = now_cafe.y + dirs[pre_dir][1];
			
		}else if(cnt == 3) {
			nx = now_cafe.x + dirs[dir][0];
			ny = now_cafe.y + dirs[dir][1];
		}

		if(init_cafe.is(nx, ny)) {
			
			return true;
		}

		return false;
	}

	static void recur 
	(cafe now_cafe, List<cafe> traking, int[] dir_va, int pre_dir, int cnt) {
		int base_cnt = 2;
		// 초기 조건
//		if(last_valid(dir_va, now_cafe, pre_dir, cnt)
		if(init_cafe.is(now_cafe.x, now_cafe.y) && cnt > base_cnt) {

			result = Math.max(traking.size(), result);
		}
		else {
			for(int d = 0; d < 4; d++) {
				int nx = now_cafe.x + dirs[d][0]; int ny = now_cafe.y + dirs[d][1];
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

				if(!visited[nx][ny]) {
					if(valid(traking, map[nx][ny])) continue;
					int now_next_dir = check_dir(now_cafe.x, now_cafe.y, nx, ny);
	//				Out.print("now", now_cafe);
	//				Out.print("netx", map[nx][ny]);
	//				Out.print("방향확인", now_next_dir);
					cafe next_cafe = map[nx][ny];
					List<cafe> new_traking = new ArrayList<>(traking);
					new_traking.add(next_cafe);

					// 이전 방향과 현재_다음_방향이 다르면
					if(pre_dir != now_next_dir) {
						// 이전에 방향을 가지 않았다면?
						if(dir_va[now_next_dir] == 0) {
							dir_va[now_next_dir] = 1; // 방향 체크
							test[nx][ny] = 1;
							for(int[] dir : dirs) {
								int nnx = nx + dir[0]; int nny = ny + dir[1];

								if(nnx == init_cafe.x && nny == init_cafe.y)
									visited[init_cafe.x][init_cafe.y] = false;
							}
							visited[nx][ny] = true;
							Out.print_line();
							Out.print("그래프확인 이전 방향과 != 다음 방향 다른경우", test);
							Out.print("현재, 다음", new int[] {now_cafe.x, now_cafe.y, nx, ny});
							Out.print("nx, ny visited", visited[init_cafe.x][init_cafe.y]);
							recur(map[nx][ny], new_traking, dir_va, now_next_dir , cnt + 1);
								visited[init_cafe.x][init_cafe.y] = true;
							visited[nx][ny] = false;
							dir_va[now_next_dir] = 0;
							test[nx][ny] = 0;
						}
					}
					// 이전 방향과 현재_방향이 같다면?  
					else { 
						test[nx][ny] = 1;
//						Out.print("그래프확인 이전 방향과 == 방향 다른경우", test);
//							Out.print("현재, 다음", new int[] {now_cafe.x, now_cafe.y, nx, ny});
						for(int[] dir : dirs) {
							int nnx = nx + dir[0]; int nny = ny + dir[1];

							if(nnx == init_cafe.x && nny == init_cafe.y)
								visited[init_cafe.x][init_cafe.y] = false;
						}
						dir_va[now_next_dir] = 1; // 방향 체크
						visited[nx][ny] = true;
						recur(map[nx][ny], new_traking, dir_va, now_next_dir, cnt + 1 );
						visited[nx][ny] = false;
								visited[init_cafe.x][init_cafe.y] = true;
						dir_va[now_next_dir] = 0;
						test[nx][ny] = 0;
					}
				}
			}
		}
	}

	private static void print(String string, Solution.cafe[][] map2) {
		System.out.println(string);
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) { 
			System.out.print(map[i][j].type + " ");
			}
			System.out.println();
		}
		
	}

	static void pro() {
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				visited[i][j] = true;

				init_cafe = map[i][j];
				List<cafe> traking = new ArrayList<>();
				traking.add(map[i][j]);
				test[i][j] = 9;
				recur(map[i][j], traking, new int[4], -1, 0);
				test[i][j] = 0;
				init_cafe = null;
				visited[i][j] = false;
			}
		}
	}

	public static void main(String args[]) throws Exception {
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			input();
			pro();

			if(result == 0 ) result = -1;
			System.out.printf("#%d %d\n", test_case, result );
		}
	}
	
	static class cafe{
		int  x, y, type, cnt;
		
		public cafe(int x, int y, int type, int cnt) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.cnt = cnt;
		}
		
		public boolean is(int nx, int ny) {
			return this.x == nx && this.y == ny ;
		}
		
		@Override
		public String toString() {
			return "[type=" + type + ", x=" + x + ", y=" + y + "]";
		}
	}
}