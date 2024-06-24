import java.util.*;
import java.io.FileInputStream;

/**

1
12 10
1B3B3B81F75E


16 2
F53586D76286B2D8

 */
class Solution {
	static Scanner sc = new Scanner(System.in);
	static int result, N, K;
	static String input;
	static List<String> set;
	static int sub_limit;
	
	static void input() {
		result = 0;
		N = sc.nextInt();
		K = sc.nextInt();
		input = sc.next();
		set = new ArrayList<>();
		sub_limit = N / 4;
		
		 input += input;
		
		Out.print("q", input);
		int start_idx = 0;

		for(int j = 0; j< 4; j++) {
			for(int i = 0; i< 4; i++) {
				int start = start_idx + (i * sub_limit) + j;
				Out.print("q", new int[] {start, sub_limit , start+sub_limit});
				set.add(input.substring(start, start + sub_limit));
			}
		}
		
		Out.print("확인", set);
	}
	
	static void pro() {
		
	}

	public static void main(String args[]) throws Exception {
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			input();
			pro();
		
			System.out.printf("#%d, %d\n", test_case, result);
		}
	}
}