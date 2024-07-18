import java.util.*;
import java.io.*;

/*
6
10 20 10 30 20 50

 */
public class Main {
    static Scanner sc = new Scanner(System.in);
    static int N;
    static int[] arr;
    static int[] LIS; // 최장 부분수열
    static int[] parent; // arr의 idx에 따른 부모의 idx
    static int[] idx; // LIS의 index에 따른 arr의 idx
    static int len; // LIS의 길이
    static int[] result;

    // 입력을 처리하는 메소드
    private static void input() {
    	N = sc.nextInt();
    	arr = new int[N];
    	LIS = new int[N];
    	parent = new int[N];
    	idx = new int[N];
    	result = new int[N];
    	len = 0;
    	
    	for (int i = 0; i < N; i++) {
			arr[i] = sc.nextInt();
		}
    }

    // LIS를 찾고 결과를 출력하는 메소드
    private static void pro() {
    	Arrays.fill(parent, -1);
    	
    	LIS[0] = arr[0];
    	
    	for (int i = 0; i < N; i++) {
			int target = arr[i];
			int pos = lowerBound(target);
			LIS[pos] = arr[i];
			idx[pos] = i; // LIS의 각 원소의 idx중 arr의 idx
			if(pos > 0) {
				parent[i] = idx[pos - 1]; // arr의 idx의 부모 idx
			}
			
			if(pos == len) {
				len++;
			}
		}
    	
    	System.out.println(len);
    	
    	int k = idx[len - 1];
    	for (int i = len - 1; i >= 0; i--) {
			result[i] = arr[k];
    		k = parent[k];
		}
    	
    	for (int i = 0; i < len; i++) {
			System.out.print(result[i] + " ");
		}
    }

    private static int lowerBound(int target) {
    	int L = 0, R = len;
    	
    	while(L < R) {
    		int mid = L + (R - L) / 2;
    		
    		if(LIS[mid] < target) {
    			L = mid + 1;
    		}else {
    			R = mid;
    		}
    	}

		return L;
	}

	// 메인 메소드
    public static void main(String[] args) {
        input(); // 입력 처리
        pro(); // LIS 계산 및 출력
    }
}
