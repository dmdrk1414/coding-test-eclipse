import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
3 15
1
5
12

 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static StringTokenizer st;
	static int[] dp;
	static int N, K;
	static StringBuilder sb = new StringBuilder();
	static int[] coins;

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		dp = new int[K + 1];
		coins = new int[N + 1];
		Arrays.fill(dp, Integer.MAX_VALUE - 1);

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			coins[i] = Integer.parseInt(st.nextToken());
		}
	}	

	private static void pro() {
		dp[0] = 0;
		for (int i = 1; i <= N; i++) {
			int coin = coins[i];
			for (int money = coin; money <= K; money++) {
				
					dp[money] = Math.min( dp[money], dp[money - coin] + 1);
				}
			}
		  if(dp[K]==Integer.MAX_VALUE-1)
			  System.out.println(-1);
		  else
			  System.out.println(dp[k]);
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