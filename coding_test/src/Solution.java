import java.util.*;
import java.io.FileInputStream;

/**
1
2 5
adbfc
dcghi

1
1 1
a

1
5 5
bbbbb
bbbbb
bazbb
bzbbb
bbbbb

1
4 5
ponoc
ohoho
hlepo
mirko

 */

class Solution {
	static Scanner sc = new Scanner(System.in);
	static int N, M;
	static int[][] dirs = new int[][] { {0, 1}, {1, 0} };
	static String[] map;
	static Queue<int[]> q;
	static int MIN;
	static StringBuilder answer;
	static char min, before_min;
	static int[] now;
	static int qsize;
	static boolean[][] visited;


	static void input() { 
		answer = new StringBuilder();
		MIN = 0;
		N = sc.nextInt();
		M = sc.nextInt();
		q = new PriorityQueue<>();
		map = new String[N];
		visited = new boolean[N][M];
		
		for(int i = 0; i< N; i++) {
			map[i] = sc.next();
		}
	}

	static void bfs(int x, int y) {
		q.add(new int[] {x, y});
		min = map[x].charAt(y);
		before_min = map[x].charAt(y);
		visited[x][y] = true;
		
		while(!q.isEmpty()) {
			now =q.poll();
			int xx = now[0]; int yy = now[1];
			answer.append(min);
			before_min = min;
			
			for(int i = 0; i < 2; i++) {
				int nx = xx + dirs[i][0]; int ny = yy + dirs[i][1];

			}
		}
	}

	static void pro() {
		bfs(0, 0);
    }

	public static void main(String args[]) throws Exception {
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
			input();
			pro();
		
			System.out.printf("#%d %s\n", test_case, answer.toString());
		}
	}
	
	static class Name implements Comparable<Name>{
		String name;
		
		public Name(String name) {
			this.name  = name;
		}
		
		@Override
		public int compareTo(Name n) {
			return this.name.compareTo(n.name);
		}

		@Override
		public String toString() {
			return "Name [name=" + name + "]";
		}
		
		
	}
}