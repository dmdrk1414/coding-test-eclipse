import java.util.*;
import java.io.FileInputStream;

/**

9 5 3
1 3
4 3
5 4
5 6
6 7
2 3
9 6
6 8
5
4
8

 */
class Solution {
	static Scanner sc = new Scanner(System.in);
	static StringBuilder sb = new StringBuilder();
	static int N, R, Q;
	static List<Integer>[] adj;
	static int[] dp;
	static int[] que;
	static boolean[] visited;
	
	static void input() {
		N = sc.nextInt();
		R = sc.nextInt();
		Q = sc.nextInt();
		adj = new ArrayList[N + 1];
		visited = new boolean[N + 1];
		for(int i = 0; i < N + 1; i++) adj[i] = new ArrayList<>();
		

		dp = new int[N + 1];
		que = new int[Q];
		Arrays.fill(dp, 1);
		
		for(int i = 0; i < N - 1; i++) {
			int one = sc.nextInt(); 
			int two = sc.nextInt(); 
			adj[one].add(two);
			adj[two].add(one);
		}
		
		for(int i = 0; i < Q; i++) {
			que[i] = sc.nextInt();
		}

	}
	
	static void recur(int value) {
		if(adj[value].isEmpty()) {
			return;
		}

		for(Integer child : adj[value]) {
			if(visited[child]) continue;
			visited[child] = true;

			recur(child);

			dp[value] += dp[child];
		}
	}
	
	static void pro() {
		visited[R] = true;
		recur(R);
		
		for(int i = 0; i < Q; i++) {
			sb.append(dp[que[i]]).append("\n");
		}
	}

	public static void main(String args[]) throws Exception {
		input();
		pro();
		
		System.out.print(sb);
	}
}