import java.util.*;

/**
1
8 3
0 0 0 0 0 1 0 0
0 1 0 1 0 0 0 1
0 0 0 0 0 0 0 0
0 0 0 1 0 1 0 0
0 0 1 1 0 0 0 0
0 0 0 0 0 0 0 0
0 0 0 0 1 0 1 0
1 0 0 0 0 0 0 0

1
5 1
0 0 0 0 0
0 0 0 0 0
0 0 1 0 0
0 0 0 0 0
0 0 0 0 0

1
5 1
0 0 0 0 0
0 0 1 0 0
0 1 1 1 0
0 0 1 0 0
0 0 0 0 0

 */
class Solution {
	static	Scanner sc = new Scanner(System.in);
	static int MAX_RESULT;
	static int N, M;
	static int[][] map, test;
	static int[][] visited;
	static List<home> home_list;
	static int LIMIT_MONEY;
	static int VISI = 1, HOME = 2;
	static int[][] dirs = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
	
	static void input() {
		MAX_RESULT = Integer.MIN_VALUE;
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][N];
		
		int home_cnt = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int num = sc.nextInt();
				map[i][j] = num;
				
				if(num == 1) {
					home_cnt++;
				}
			}
		}
		LIMIT_MONEY = (home_cnt * M);
		
//		Out.print("입력", map);
//		Out.print("최대", LIMIT_MONEY);
	}
	
	static void bfs(int x, int y) {
		int k = 1; // 
		int money = 1; // 회사의 운영 비용
		PriorityQueue<point> q = new PriorityQueue<>(); // 탐사의 범위 관리
		int pre_turn = 0;
		q.add(new point(x, y, pre_turn));
		if(map[x][y] == 1) {
			visited[x][y] = HOME;
			home_list.add(new home(x, y));
			test[x][y] = HOME;
			MAX_RESULT = Math.max(MAX_RESULT, home_list.size()); // 초기 집 
		}else {
			visited[x][y] = VISI; 
			test[x][y] = VISI;
		}

		
		// 운영비용이 같은 경우도 배제 => 향후 틀리면 수정
//		while(money < LIMIT_MONEY && !q.isEmpty()) {
		while(!q.isEmpty()) {
			point now_point = q.poll();
			int xx = now_point.x; int yy = now_point.y;
						
			// k의 사각형이 완성이 될시
			if(now_point.turn > pre_turn) {
				k++; // while 시작할시 ++,
				money =( k * k ) + ( (k -1) * (k - 1) ); // 운영 비용 계산
				if(( (home_list.size() * M) - money  ) >= 0 ){
//				Out.print_line();
//				Out.print("초기 집 시작", new int[] {x, y});
//				Out.print("확인", test);
//				Out.print("집 갯수", home_list.size());
//				Out.print("집 내는 비용", (home_list.size() * M));
//				Out.print("운영비용", money);
					MAX_RESULT = Math.max(MAX_RESULT, home_list.size());
				}
				pre_turn = now_point.turn;
			}
			
			for(int[] dir : dirs) {
				int nx = xx + dir[0]; int ny = yy + dir[1];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				if(visited[nx][ny] != 0) continue;

				// 방문 체크
				if(map[nx][ny] == 1) {
					visited[nx][ny] = HOME;
					home_list.add(new home(nx, ny));
					test[nx][ny] = HOME;
				}
				if(map[nx][ny] == 0) {
					visited[nx][ny] = VISI;
					test[nx][ny] = VISI;
				}
				
				q.add(new point(nx, ny, now_point.turn + 1));
			}
			// 현제 탐사을 진행한후,  운영 비용 - (집 * 집비용) >= 0 인경우 체크
		}
	}
	
	static void pro() {
//		for(int i = 0; i < N ; i++) {
//			for(int j = 0; j < N ; j++) {
//				test = new int[N][N];
//				home_list = new ArrayList<>();
//				visited = new int[N][N];
//				bfs(4, 3);
//			}
//		}
		for(int i = 0; i < N ; i++) {
			for(int j = 0; j < N ; j++) {
				test = new int[N][N];
				home_list = new ArrayList<>();
				visited = new int[N][N];
				bfs(i, j);
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
			
			if(MAX_RESULT == Integer.MIN_VALUE) {
				MAX_RESULT = 0;
			}
			System.out.printf("#%d %d\n", test_case, MAX_RESULT );
		}
	}
	
	static class point implements Comparable<point>{
		int x, y, turn;
		public point(int x, int y, int turn) {
			this.x = x;
			this.y = y;
			this.turn = turn;
		}
		@Override
		public int compareTo(point p) {
			if(this.turn != p.turn) {
				return Integer.compare(this.turn, p.turn);
			}
			return 0;
		}
	}
	static class home{
		int x, y;
		public home(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}