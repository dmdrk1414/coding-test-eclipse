import java.util.*;
import java.io.*;

/**
1
5 6 2 1 3
0 0 5 3 6 0
0 0 2 0 2 0
3 3 1 3 7 0
0 0 0 0 0 0
0 0 0 0 0 0

1
5 6 2 2 6      
3 0 0 0 0 3
2 0 0 0 0 6
1 3 1 1 3 1
2 0 2 0 0 2
0 0 4 3 1 1

 */

class Solution {
	static Scanner sc = new Scanner(System.in);
	static int result;
	static int N, M, R, C, L;
	static int[][] map;
	static int[][] visited;
	
	public static void input() {
		result = 0;
		N = sc.nextInt();
		M = sc.nextInt();
		R = sc.nextInt();
		C = sc.nextInt();
		L = sc.nextInt();
		map = new int[N][M];
		visited = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
//		Out.print("초기 파이트", map);
	}

	public static void pro() {
		// 초기 맨홀에 떨어질때
		visited[R][C] = 1;
		Queue<info> q = new LinkedList<>();
		q.add(new info(R, C, 1));
		
//		for(int time = 1 ; time <= L ; time++) {
		while(!q.isEmpty()) {
			info now_info = q.poll();
			// 시간 확인

//			Out.print("거리 계산 확인", visited);
//			Out.print("시간", now_info.cnt);
			if(now_info.cnt == L) {
				break;
			}

			int xx = now_info.x;
			int yy = now_info.y;

			// 파이프 관에따른 방향
			int[][] dirs = dir(xx, yy);
			if(dirs == null) break;
			
//			Out.print_line();
//			Out.print("초기 파이트", map);
//			Out.print("xx", xx);
//			Out.print("yy", yy);
//			Out.print("map[xx][yy]", map[xx][yy]);
//			Out.print("파이프 관에 따른 방향", dirs);
//			
			
			// 파이프에 따른 거리
			for(int[] dir : dirs) {
				int nx = xx + dir[0]; int ny = yy + dir[1];
				if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
				if(map[nx][ny] == 0) continue;
				if(visited[nx][ny] != 0) continue;
				if(valid(xx, yy, nx, ny)) continue;

//				Out.print("xx", xx);
//				Out.print("yy", yy);
//				Out.print("nx", nx);
//				Out.print("ny", ny);
//				Out.print("파이프 관에 따른 방향", dirs);
//				if(nx == 4 && ny == 2) {
//				Out.print("nx", nx);
//				Out.print("ny", ny);
//				Out.print("파이프 관에 따른 방향", dirs);
//				}

				visited[nx][ny] = 1;
				q.add(new info(nx, ny, now_info.cnt + 1));
			}
		}
		
		
		for(int i = 0 ; i < N ; i++) {
			for(int j = 0 ; j < M ; j++) {
				if(visited[i][j] == 1) {
					result++;
				}
			}
		}
	}
	
	static class info{
		int x, y, cnt;
		
		public info(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}
	
	public static boolean valid(int xx, int yy, int nx, int ny) {
		int dir = 0; // 방향 검증
		int xxyy_map = map[xx][yy];
		int nxny_map = map[nx][ny];

		if(xx == nx) {
			// L -> R
			if(yy < ny) {
				dir = 0;
			}
			// L <- R
			else if(yy > ny){
				dir = 1;
			}
		}else if (yy == ny) {
			// H -> L
			if(xx < nx) {
				dir = 2;
			}
			// H <- L
			else if(xx > nx) {
				dir = 3;
			}
		}
		int[][] pose = { {1, 3, 6, 7}, {1, 3, 4, 5}, {1, 2, 4, 7}, {1, 2, 5, 6} };
		// true이면 안되는거
		boolean is = true;

		for(int po: pose[dir]) {
			if(nxny_map == po) {
				is = false;
			}
		}
		
		return is;
	}
	
	// 파이프에 따른 갈수 있는 거리 주기
	public static int[][] dir(int x, int y){
		// 동, 서, 남, 북
		int[][] dirs = new int[][] { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
		int[] EAST = new int[] {0, 1};
		int[] WEST = new int[] {0, -1};
		int[] SORTH = new int[] {1, 0};
		int[] NORTH = new int[] {-1, 0};
		
		int temp = map[x][y];

		if(temp == 1) {
			return new int[][] {EAST, WEST, NORTH, SORTH};
		} else if(temp == 2) {
			return new int[][] {NORTH, SORTH};
		}else if(temp == 3) {
			return new int[][] {EAST, WEST};
		}else if(temp == 4) {
			return new int[][] {NORTH, EAST};
		}else if(temp == 5) {
			return new int[][] {EAST, SORTH};
		}else if(temp == 6) {
			return new int[][] {WEST, SORTH};
		}else if(temp == 7) {
			return new int[][] {WEST, NORTH};
		}
		
		return null;
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