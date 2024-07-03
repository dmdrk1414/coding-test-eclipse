import java.util.*;
import java.io.FileInputStream;


/**
1
5 6
......
.939..
.3428.
.9393.
......

1
10 10
..........
.99999999.
.9.323239.
.91444449.
.91444449.
.91444449.
.91444449.
.91232329.
.99999999.
..........


 */
class Solution {
	static Scanner sc = new Scanner(System.in);
	static int result = 0;
	static int N, M;
	static int BLANK = 0;
	static int[][] map;
	static Queue<Pos> q;
    static int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

	static void input() {
		result = 0;
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][M];
		q = new LinkedList<>();
		
		for(int i = 0; i < N ; i++) {
			String in = sc.next();
			for(int j = 0; j < M ; j++) {
				if(in.charAt(j) == '.') {
					q.add(new Pos(i, j));
					map[i][j] = BLANK;
				}else {
					map[i][j] = in.charAt(j) - '0';
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();

		sb.isEmpty(); sb.isEmpty();
		
		// Out.print("확인", map);
	}
	
	// 빈 공간의 q의 사이즈을 준다. 
	// q의 사이즈가 없으면 다음의 과의 변화을 할 것이 없다는 뜻이다. 
	static int bfs() {
		int q_size = q.size();

		for(int i = 0; i < q_size ; i++) {
			Pos now = q.poll();
			int xx = now.x; int yy = now.y;
			
			for(int[] dir: dirs) {
				int nx = xx + dir[0]; int ny = yy + dir[1];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
				if(map[nx][ny] != BLANK) {
					map[nx][ny]--;
					
					if(map[nx][ny] == 0) {
						q.add(new Pos(nx, ny));
					}
				}
			}
		}

//		Out.print("BFS이후 확인", map);
		
		return q.size();
	}

	static void pro() { 
		
		while(true) {
			if(bfs() == 0) break;
			result++;
		}
	}
	
    // 이진 탐색 구현
    private static int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
            Out.print("확인", new int[] {left, right, mid});
        }
        return left;
    }
    
	public static void main(String args[]) throws Exception {
		int test = lowerBound(List.of(1, 2, 3, 5, 6, 7, 8, 9, 10), 4);
		Out.print("sdf", test);
		
		Integer.parseInt(null)
	}
	
	static class Pos{
		int x, y;

		public Pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
}