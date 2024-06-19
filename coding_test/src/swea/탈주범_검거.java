package swea;
import java.util.*;
import java.io.FileInputStream;

/*
1
5 6 2 1 3
0 0 5 3 6 0
0 0 2 0 2 0
3 3 1 3 7 0
0 0 0 0 0 0
0 0 0 0 0 0

 */
class 탈주범_검거 {
	static Scanner sc = new Scanner(System.in);
	static int N, M, R, C, L;
	static int result;
	static int[][] visited;
	static int[][][] map;
	static int[][]dirs = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }, };
	static int[][] opens = new int [][]{
		{ 0, 0, 0, 0}, // 빈곳
		{ 1, 1, 1, 1}, // TYPE 1
		{ 0, 1, 0, 1}, // TYPE 2
		{ 1, 0, 1, 0}, // TYPE 3
		{ 1, 0, 0, 1}, // TYPE 4
		{ 1, 1, 0, 0}, // TYPE 5
		{ 0, 1, 1, 0}, // TYPE 6
		{ 0, 0, 1, 1}, // TYPE 7
	};

	static void input() {
		N = sc.nextInt();
		M = sc.nextInt();
		R = sc.nextInt();
		C = sc.nextInt();
		L = sc.nextInt();
		result = 1;
		
		visited = new int[N][M];
		map = new int[N][M][4];
		
		for(int i = 0; i < N ; i++) {
			for(int j = 0; j < M ; j++) {
				int num = sc.nextInt();
				
				for(int d = 0; d < 4; d++) {
					map[i][j][d] = opens[num][d];
				}
			}
		}
		

	}

	static void pro() {
		// 초기 
		visited[R][C] = 1;
		Queue<info> q = new LinkedList<>();
		q.add(new info(R, C, 1));

//		Out.print_line();
//		for(int i = 0; i < N ; i++) {
//			Out.print("확인", i);
//			Out.print("확인", map[i]);
//		}

		while(!q.isEmpty()) {
			info now_info = q.poll();
			int xx = now_info.x;
			int yy = now_info.y;
			if(now_info.time == L) break;
			
			for(int d = 0; d < 4; d++) {
				int nx = xx + dirs[d][0]; int ny = yy + dirs[d][1];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
				if(visited[nx][ny] == 1) continue;
				// 다음 파이프의 열림 확인
				int open_nd = (d + 2) % 4; 
				// 다음 파이프와 현재 파이프의 체결 확인 
				if(map[xx][yy][d] == 0 || map[nx][ny][open_nd] == 0	) continue;
				// time이 확인 시간보다 크다면
				result++;
				visited[nx][ny] = 1;
				q.add(new info(nx, ny, now_info.time + 1));
			}
		}
		
	}
	
	static class info{
		int x, y, time;
		
		public info(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}

	public static void main(String args[]) throws Exception
	{

		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			input();
			pro();
		
			System.out.printf("#%d %d\n", test_case, result);
		}
	}
}