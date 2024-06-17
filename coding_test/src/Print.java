import java.util.*;

public class Print{
	public static void print() {
		System.out.println();
	}

	public static void print(String str) {
		System.out.println(str);
		print();
	} 

	public static<T> void print(String str, T a) {
		System.out.println(str + ": " + a);
		print();
	} 

	public static<T> void print(String str, T[][] arr) {
		print(str);
		for(int i = 0; i < arr.length ; i++	) {
			System.out.print(i + ": ");
			for(int j = 0; j < arr[0].length ; j++	) {
				System.out.print(arr[i][j] + " ");
			}
			print();
		}
		print();
	}
	
	public static void print(String str, int[] arr) {
		print(str);
		System.out.println(Arrays.toString(arr));
		print();
	} 
	
	public static<T> void print(String a, PriorityQueue<T> pq) {
		print(a);
		for(T value: pq) {
			System.out.println(value);
		}
		print();
	}

	public static<T> void print(String a, Queue<T> q) {
		print(a);
		for(T value: q) {
			System.out.println(value);
		}
		print();
	}

	public static<T> void print(String a, List<T> list) {
		print(a);
		for(T value: list) {  
			System.out.println(value);
		}
		print();
	}
	
	public static<T> void print(String a, Set<T> set) {
		print(a);
		for(T value: set) {
			System.out.println(value);
		}
		print();
	}

	public static<T, D> void print(String a, Map<T, D> map) {
		print(a);
		for(Map.Entry<T, D> entry: map.entrySet()) {
			T key = entry.getKey();
			D value = entry.getValue();
			System.out.println(key + ": " + value);
		}
		print();
	}	
}
