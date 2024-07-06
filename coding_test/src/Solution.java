import java.util.*;
import java.io.FileInputStream;
class Solution {
	static Scanner sc = new Scanner(System.in);
	static List<Integer>[] adj;
	static int N, result;
	static int[][] map;
	static boolean[] visited;

	public static void main(String args[]) throws Exception {
		N = sc.nextInt();
		adj = new ArrayList[N];
		visited = new boolean[N];
		result = Integer.MAX_VALUE;
		map = new int[N][N];

		for(int i = 0; i < N; i++) {
			adj[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int value = sc.nextInt();
				if(value != 0) {
					map[i][j] = value;
					adj[i].add(j);
					adj[j].add(i);
				}
			}
		}
		
		visited[0] = true;
		recur(1, 0, 0, 0);
		visited[0] = false;
		
		System.out.print(result);
	}
	
	static void recur(int idx, int value, int money, int init) {
		if(result <= money) return;
		if(idx == N) {
			int total = money + map[value][init];
			result = Math.min(result, total);
		}
		else {
			for(int next = 0; next < N; next++) {
				if(visited[next]) continue;
				if(map[value][next] == 0) continue;

				visited[next] = true;
				recur(idx + 1, next, money + map[value][next], init);
				visited[next] = false;
			}
		}
	}
}