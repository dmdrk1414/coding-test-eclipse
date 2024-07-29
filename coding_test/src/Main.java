import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
4 5
1 1 3 2

5 5
1 1 3 2 5

5 7
1 1 1 1 1

 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	static int[] dp;
	static int N, K;
	static int check;
	static StringBuilder sb = new StringBuilder();
	static int[] coffes;

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		check = 0;
		
		dp = new int[K + 1];
		coffes = new int[N + 1];
		Arrays.fill(dp, Integer.MAX_VALUE - 1);

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			coffes[i] = Integer.parseInt(st.nextToken());
			check += coffes[i];
		}
	}	

	private static void pro() {
		if(check < K) {
		  System.out.println(-1);
		  return;
		}
		
		
		dp[0] = 0;
		
		for (int i = 1; i <= N; i++) {
			int coffe = coffes[i];
//			for (int co = coffe;  co <= K; co++) {
			for (int co = K;  co >= coffe; co--) {
		          if (dp[co - coffe] != Integer.MAX_VALUE) {
	                    dp[co] = Math.min(dp[co], dp[co - coffe] + 1);
	                }
			}
		}
		
		  System.out.println(dp[K]);
		}
	

    public static void main(String[] args) throws NumberFormatException, IOException {
		input();
		pro();
    	bw.write(sb.toString());
    	bw.flush();
    	bw.close();
    	br.close();
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