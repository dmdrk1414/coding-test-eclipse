import java.util.*;
import java.io.*;

/**
 * 7 7
 * #######
 * #...RB#
 * #.#####
 * #.....#
 * #####.#
 * #O....#
 * #######
 * <p>
 * 6 7
 * #######
 * #B....#
 * #R....#
 * #.....#
 * #.....#
 * #######
 */
public class Main {
    static int N, M, K;
    static Scanner sc = new Scanner(System.in);
    static long[] tree, arr;

    private static void input() {
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        arr = new long[N + 1];
        tree = new long[N * 4];


        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextLong();
        }

        initTree(1, 1, N);

        for (int i = 0; i < M + K; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            if (a == 1) {
                long d = sc.nextLong();

                // a가 1인 경우 b번째 수부터 c번째 수에 d를 더하고
                update(b, c, d, 1, 1, N);
            } else if (a == 2) {
                // a가 2인 경우에는 b(1 ≤ b ≤ N)번째 수부터 c(b ≤ c ≤ N)번째 수까지의 합을 구하여 출력하면 된다.
                long result = sumRec(b, c, 1, 1, N);
                System.out.println(result);
            }
        }
    }

    private static long update(final int left, final int right, final long diff, final int node, final int nodeLeft, final int nodeRight) {
        if (right < nodeLeft || nodeRight < left)
            return tree[node];

        if (nodeLeft == nodeRight)
            return tree[node] += diff;

        if (left <= nodeLeft && nodeRight <= right) {
            lazyExist[node] = true;
            arr[node] += diff;
            return tree[node] = mergeBlock(newValue, nodeRight - nodeLeft + 1);
        }

        int mid = nodeLeft + (nodeRight - nodeLeft) / 2;
        if (lazyExist[node]) {
            lazyExist[node] = false;
            pushDown(lazyValue[node], node * 2, nodeLeft, mid);
            pushDown(lazyValue[node], node * 2 + 1, mid + 1, nodeRight);
            lazyValue[node] = DEFAULT_VALUE;
        }

        int leftVal = updateRec(left, right, newValue, node * 2, nodeLeft, mid);
        int rightVal = updateRec(left, right, newValue, node * 2 + 1, mid + 1, nodeRight);
        return tree[node] = merge(leftVal, rightVal);
    }

    private static long sumRec(final int left, final int right, final int node, final int nodeLeft, final int nodeRight) {
        if (nodeRight < left || right < nodeLeft) {
            return 0;
        }

        if (left <= nodeLeft && nodeRight <= right) {
            return tree[node];
        }

        int mid = nodeLeft + (nodeRight - nodeLeft) / 2;
        long leftVal = sumRec(left, right, node * 2, nodeLeft, mid);
        long rightVal = sumRec(left, right, node * 2 + 1, mid + 1, nodeRight);
        // 값의 변화가 되면 안된다 그냥 더한값을 출력
//        return tree[node] = leftVal + rightVal;
        return  leftVal + rightVal;
    }

    private static long initTree(final int node, final int left, final int right) {
        if (left == right) {
            return tree[node] = arr[left];
        }

        int mid = left + (right - left) / 2;
        long leftVal = initTree(node * 2, left, mid);
        long rightVal = initTree(node * 2 + 1, mid + 1, right);

        return tree[node] = leftVal + rightVal;
    }

    private static void pro() {

    }

    public static void main(String[] args) {
        input();
        pro();
    }
}
