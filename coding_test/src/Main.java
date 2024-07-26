import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
3 4 5
2 3
2 1
3 3

 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	static int[][] dp_1;
	static int N, X, Y;
	static int c_x, c_y;
	static Custom[] customs;
	static StringBuilder sb = new StringBuilder();

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());

		customs = new Custom[N + 1];
		dp_1 = new int[X + 1][Y + 1];
		c_x = 0;
		c_y = 0;

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			customs[i] = new Custom(x, y);
			c_x += x;
			c_y += y;
		}
	}	

	private static void pro() {
		// 없으면 -1 출력
		if(c_x < X || c_y < Y) {
			System.out.println(-1);
			return;
		}
		
		for (int i = 0; i <= X; i++) {
			Arrays.fill(dp_1[i], 1);
		}

		// 최소고객수
		// 마지막판매에 성공하는 고객의 번호
		for (int x = 1; x <= X; x++) {
			for (int y = 1; y <= Y; y++) {
				for (int i = 1; i <= N; i++) {
					Custom custom = customs[i];
					
					if(custom.x >= x && custom.y >= y) {
						dp_1[x][y] = 1;
						break;
					}else if(custom.x < x || custom.y < y) {
						int x_custom_x = x - custom.x;
						int y_custom_y = y - custom.y;

						if(x_custom_x <= 0) x_custom_x = custom.x;
						if(y_custom_y <= 0) y_custom_y = custom.y;
						dp_1[x][y] = dp_1[x][y] + dp_1[x_custom_x][y_custom_y];
					}
				}
			}
		}
		
		Out.print("확인", dp_1);
	}

    public static void main(String[] args) throws NumberFormatException, IOException {
		input();
		pro();
    	
    	bw.write(sb.toString());
    	bw.flush();
    	bw.close();
    	br.close();
    }

    static class Custom{
    	int x, y;

		public Custom(int x, int y) {
			super();
			this.x = x;
			this.y = y;
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