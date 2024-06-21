import java.util.*;

class Solution {
    static Scanner sc = new Scanner(System.in);
    static int result;
    static int MOV = 2000; // 좌표를 0 ~ 2000으로 매핑하기 위해서 사용
    static int N;
    static point[] points;
    static int LIVE = 0, DEAD = 1, OUT = 2;
    static int[][] dirs = { {0, 1}, {0, -1}, {-1, 0}, {1, 0} }; // 상, 하, 좌, 우 방향
    static PriorityQueue<Collision> collisions = new PriorityQueue<>(); // 충돌 이벤트를 관리하는 우선순위 큐

    // 입력을 받아 초기화하는 함수
    static void input() {
        result = 0;
        N = sc.nextInt();
        points = new point[N];
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int dir = sc.nextInt();
            int energy = sc.nextInt();
            // 원자의 초기 좌표를 (0, 0)을 중심으로 매핑하기 위해 +1000을 더함
            points[i] = new point(i, x + 1000, y + 1000, dir, energy);
        }
    }

    // 살아있는 원자가 있는지 확인하는 함수
    static boolean valid() {
        for (point p : points) {
            if (p.live == LIVE)
                return true;
        }
        return false;
    }

    // 충돌 시뮬레이션을 진행하는 함수
    static void pro() {
        // 충돌 후보를 우선순위 큐에 추가
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                addCollision(i, j);
            }
        }

        // 우선순위 큐에서 충돌 이벤트를 하나씩 처리
        while (!collisions.isEmpty()) {
            Collision c = collisions.poll();
            // 이미 죽은 원자가 포함된 경우나 시간이 맞지 않는 경우 무시
            if (points[c.a].live != LIVE || points[c.b].live != LIVE)
                continue;
            if (c.time != points[c.a].time || c.time != points[c.b].time)
                continue;

            // 충돌한 원자를 DEAD 상태로 변경하고 에너지를 결과에 더함
            points[c.a].live = DEAD;
            points[c.b].live = DEAD;
            result += points[c.a].energy + points[c.b].energy;
        }
    }

    // 두 원자 간의 충돌 이벤트를 우선순위 큐에 추가하는 함수
    static void addCollision(int i, int j) {
        point a = points[i];
        point b = points[j];

        // 상 vs 하 충돌 확인
        if (a.dir == 0 && b.dir == 1 && a.x == b.x && a.y < b.y) {
            int time = (b.y - a.y) / 2;
            collisions.add(new Collision(time, i, j));
        } 
        // 하 vs 상 충돌 확인
        else if (a.dir == 1 && b.dir == 0 && a.x == b.x && a.y > b.y) {
            int time = (a.y - b.y) / 2;
            collisions.add(new Collision(time, i, j));
        } 
        // 좌 vs 우 충돌 확인
        else if (a.dir == 2 && b.dir == 3 && a.y == b.y && a.x > b.x) {
            int time = (a.x - b.x) / 2;
            collisions.add(new Collision(time, i, j));
        } 
        // 우 vs 좌 충돌 확인
        else if (a.dir == 3 && b.dir == 2 && a.y == b.y && a.x < b.x) {
            int time = (b.x - a.x) / 2;
            collisions.add(new Collision(time, i, j));
        }
    }

    // 메인 함수: 테스트 케이스를 읽고 처리
    public static void main(String args[]) throws Exception {
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            input(); // 입력 처리
            pro(); // 충돌 시뮬레이션
            System.out.printf("#%d %d\n", test_case, result);
        }
    }

    // 원자 정보를 담는 클래스
    static class point {
        int num;
        int x, y, time;
        int dir;
        int energy;
        int live;

        public point(int num, int x, int y, int dir, int energy) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.energy = energy;
            this.time = 0;
            this.live = LIVE;
        }

        @Override
        public String toString() {
            return "[num" + num + ", x=" + x + ", y=" + y + ", time=" + time + ", dir=" + dir + ", energy=" + energy + ", live=" + live + "]\n";
        }
    }

    // 충돌 이벤트를 담는 클래스
    static class Collision implements Comparable<Collision> {
        int time; // 충돌 시간
        int a, b; // 충돌하는 두 원자의 인덱스

        public Collision(int time, int a, int b) {
            this.time = time;
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Collision other) {
            return Integer.compare(this.time, other.time);
        }
    }
}
