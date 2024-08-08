import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

/**
6 3 15
0 0 1 0 0 0
0 0 1 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 1 0
0 0 0 1 0 0
6 5
2 2 5 6
5 4 1 6
4 2 3 5

6 3 13
0 0 1 0 0 0
0 0 1 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 1 0
0 0 0 1 0 0
6 5
2 2 5 6
5 4 1 6
4 2 3 5

6 3 100
0 0 1 0 0 0
0 0 1 0 0 0
0 0 0 1 0 0
0 0 0 1 0 0
0 0 0 0 1 0
0 0 0 1 0 0
6 5
2 2 5 6
5 4 1 6
4 2 3 5

 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	static int N, M, energy;
	static int[][] map;
	static CustomInfo[][] customMap;
	static List<CustomInfo> customList;
	static int[] car;
	static int[][] visited;
//	static int[][] dirs = new int[][] {  {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
    static int[][] dirs = { {1, 0}, {-1, 0}, {0, -1}, {0, 1} };
	static int result;

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		customList = new LinkedList<>();
		result = 0;

		energy = Integer.parseInt(st.nextToken());
		
		map = new int[N + 1][N + 1];
		car = new int[2];
		customMap = new CustomInfo[N + 1][N + 1];
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		car[0] = Integer.parseInt(st.nextToken());
		car[1] = Integer.parseInt(st.nextToken());

	}	
	

	private static void pro() throws IOException {
		// 손님의 정보
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int s_x = Integer.parseInt(st.nextToken());
			int s_y = Integer.parseInt(st.nextToken());

			int e_x = Integer.parseInt(st.nextToken());
			int e_y = Integer.parseInt(st.nextToken());
			
			cusBfs(s_x, s_y, e_x, e_y);
			customMap[s_x][s_y] = new CustomInfo(true, s_x, s_y, e_x, e_y, visited[e_x][e_y], 0, 0);
//			Out.print("처음에 손님 출발, 목적지 거리", new int[] {s_x, s_y, e_x, e_y, visited[e_x][e_y]});
			customList.add(new CustomInfo(true, s_x, s_y, e_x, e_y, 0, s_x, s_y));
			if(visited[e_x][e_y] == -1) {
				result = -1;
				return;
			}
		}


		while(!customList.isEmpty()) {
			// 자동차에서 현재 고객까지의 거리계산
			for(CustomInfo cus: customList) {
				cusBfs(car[0], car[1], cus.s_x, cus.s_y);
				cus.d = visited[cus.s_x][cus.s_y];
				
				if(cus.d == -1) {
					result = -1;
					break;
				}
			}
			
			Collections.sort(customList);
//			for(CustomInfo cus: customList) {
//				System.out.println(cus);
//			}
			
			// 현제 택시와 손님중 거리가 짧은거
//			CustomInfo nowCus = customList.removeFirst();
			CustomInfo nowCus = customList.remove(0);
//			System.out.println(nowCus);
//			System.out.println();
			
			// 택시가 손님까지 도착
			car[0] = nowCus.s_x; car[1] = nowCus.s_y;

			// 태시가 손님에게 도착할때 연료부족확인
			if(energy - nowCus.d <= 0) {
				result = -1;
				break;
			}
			
			// 택시가 손님이 원하는 목표 도착
			car[0] = nowCus.e_x; car[1] = nowCus.e_y;
			// 손님을 태우로 출발 + 목표에 도착
			int useEnergy = nowCus.d + customMap[nowCus.s_x][nowCus.s_y].d;
			// 연료 확인
			if(energy - useEnergy < 0) {
				result = -1;
				break;
			}

//			Out.print("이전 에너지", energy);
//			Out.print("손님태우로 출발", nowCus.d);
//			Out.print("손님 목적 도착", customMap[nowCus.s_x][nowCus.s_y].d );
			
			// 에너지  +=  |손님태우로 출발 - 손님 도착 * 2|
			energy -= (nowCus.d  + customMap[nowCus.s_x][nowCus.s_y].d);
			energy += customMap[nowCus.s_x][nowCus.s_y].d * 2;
//			Out.print("이후 에너지", energy);
		}
		

	}
	
	private static void initVisited() {
		visited = new int[N + 1][N + 1];
		
		for(int[] vi: visited) {
			Arrays.fill(vi, -1);
		}
	}
	
	private static void cusBfs(int s_x, int s_y, int e_x, int e_y) {
		PriorityQueue<Pos> q = new PriorityQueue<>();      
	    q.add(new Pos(s_x, s_y));
		initVisited();
		visited[s_x][s_y] = 0;
		
		while(!q.isEmpty()) {
			Pos now = q.poll();
			int xx = now.x; int yy = now.y;

			if(xx == e_x && yy == e_y) break;
			
			for(int[] dir : dirs) {
				int nx = xx + dir[0]; int ny = yy + dir[1];
				
				if(nx < 1 || ny < 1 || nx > N || ny > N) continue;
				if(visited[nx][ny] != -1) continue;
				if(map[nx][ny] == 0) {
					visited[nx][ny] = visited[xx][yy] + 1;
					q.add(new Pos(nx, ny));
				}
			}
		}
	}
	

    public static void main(String[] args) throws NumberFormatException, IOException {
    	input();
    	pro();

		if(result == -1) {
			bw.write(result + "");
		}else {
			bw.write(energy + "");
		}
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
    static class CustomInfo implements Comparable<CustomInfo>{
    	boolean custom;
    	int s_x, s_y, e_x, e_y, d;
    	int num_x, num_y;

		public CustomInfo() {
			super();
			this.custom = false;
			this.s_x = 0;
			this.s_y = 0;
			this.e_x = 0;
			this.e_y = 0;
			this.d = 0;
		}

		public CustomInfo(boolean custom, int s_x, int s_y, int e_x, int e_y, int d, int num_x, int num_y) {
			super();
			this.custom = custom;
			this.s_x = s_x;
			this.s_y = s_y;
			this.e_x = e_x;
			this.e_y = e_y;
			this.d = d;
			this.num_x = num_y;
			this.num_y = num_x;
		}
		
		@Override
		public int compareTo(CustomInfo c) {
			if(this.d != c.d) {
				return Integer.compare(this.d, c.d);
			}
			
			if(this.num_x != c.num_x) {
				return Integer.compare(this.num_x, c.num_x);
			}

			if(this.num_y != c.num_y) {
				return Integer.compare(this.num_y, c.num_y);
			}
			
			return 0;
		}

		@Override
		public String toString() {
			return "[custom=" + custom + ", s_x=" + s_x + ", s_y=" + s_y + ", e_x=" + e_x + ", e_y=" + e_y
					+ ", d=" + d + ", num_x=" + num_x + ", num_y=" + num_y + "]";
		}


		
    }
    
    static class Pos implements Comparable<Pos>{
    	int x, y;

		public Pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Main.Pos o) {
			if(this.x != o.x) {
				return Integer.compare(this.x, o.x);
			}
			
			if(this.y != o.y) {
				return Integer.compare(this.y, o.y);
			}
			
			return 0;
		}
    }
}

//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
//StringTokenizer st = new StringTokenizer(br.readLine());
//
//
//StringBuilder sb = new StringBuilder();
//for (int i = 0; i < M + K; i++) {
//  st = new StringTokenizer(br.readLine());
//
//  int a = Integer.parseInt(st.nextToken());
//  int b = Integer.parseInt(st.nextToken());
//  long c = Long.parseLong(st.nextToken());
//}
//
//bw.write(sb.toString());
//bw.flush();
//bw.close();
//br.close();