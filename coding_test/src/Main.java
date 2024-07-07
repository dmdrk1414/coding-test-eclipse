import java.util.*;
import java.io.*;

/**
7
9
1 2 4
1 3 2
1 4 3
2 6 3
2 7 5
3 5 1
4 6 4
5 6 2
6 7 5
1 7

 */
public class Main {
	static Scanner sc = new Scanner(System.in);
	static int N, M;
	static List<Integer>[] adj;
	static int[] ingree;
	static int[][] map;
	static StringBuilder sb;
	static Queue<Integer> q;
	static int[] maxValue;
	static Node[] nodes;
	static int start, end;

	private static void input() {
		N = sc.nextInt();
		M = sc.nextInt();
		adj = new ArrayList[N + 1];
		ingree = new int[N + 1];
		map = new int[N + 1][N + 1];
		for(int i = 0; i <= N ; i++) adj[i] = new ArrayList<>();
		q = new LinkedList<>();
		sb = new StringBuilder();
		maxValue = new int[N + 1];
		nodes = new Node[N + 1];
		
		
		for(int i = 0; i < M ; i++) {
			int one = sc.nextInt();
			int two = sc.nextInt();
			int value = sc.nextInt();
			
			map[one][two] = value;
			adj[one].add(two);
			ingree[two]++;
		}
		
		start = sc.nextInt();
		end = sc.nextInt();

		for(int i = 1; i <= N ; i++) {
			if(ingree[i] == 0) q.add(i);
			nodes[i] = new Node(0, 0);
		}
		
		// 시작 경로 카운트 ++
		nodes[start].cnt++;
		
	}

	private static void pro() {
		bfs();

//		Out.print("확인", map);
//		Out.print("확인", nodes);
//		Out.print("확인", maxValue);

		System.out.println(maxValue[end]);
		System.out.println(nodes[end].cnt);
	}

	private static void bfs() {
		while(!q.isEmpty()) {
			int value = q.poll();
			// ?
			
			for(int child : adj[value]) {
				// 최대 거리 시간 들록
				maxValue[child] = Math.max(maxValue[child], maxValue[value] + map[value][child]);
				nodes[child].cnt = Math.max(nodes[child].cnt, nodes[value].cnt + 1);
				
				if(--ingree[child] == 0) {
					q.add(child);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		input();
		pro();
	}
	
	static class Node{
		int value, cnt;
		
		public Node(int value, int cnt) {
			this.value = value;
			this.cnt = cnt;
		}

		@Override
		public String toString() {
			return "[value=" + value + ", cnt=" + cnt + "]";
		}
	}

	private static void print(String string, int[] in2) {
		System.out.print(string + ": "); System.out.println(Arrays.toString(in2));
	}

	private static void print(String string, List<Integer> in2) {
		System.out.print(string + ": "); System.out.println(in2);
	}
}