import java.util.*;
import java.io.FileInputStream;

/**

 */
class Solution {
	  public int[] solution(String[] info, String[] query) {
	        Map<String, List<Integer>> infoMap = new HashMap<>();

	        // 지원자 정보 파싱 및 저장
	        for (String i : info) {
	            String[] parts = i.split(" ");
	            String[] langs = {parts[0], "-"};
	            String[] jobs = {parts[1], "-"};
	            String[] exps = {parts[2], "-"};
	            String[] foods = {parts[3], "-"};
	            int score = Integer.parseInt(parts[4]);

	            for (String lang : langs) {
	                for (String job : jobs) {
	                    for (String exp : exps) {
	                        for (String food : foods) {
	                            String key = String.join(" ", lang, job, exp, food);
	                            infoMap.computeIfAbsent(key, k -> new ArrayList<>()).add(score);
	                        }
	                    }
	                }
	            }
	        }

	        // 각 조건별로 점수 리스트를 정렬
	        for (List<Integer> scores : infoMap.values()) {
	            Collections.sort(scores);
	        }

	        // 쿼리 처리
	        int[] answer = new int[query.length];
	        int idx = 0;

	        for (String q : query) {
	            String[] parts = q.replace(" and ", " ").split(" ");
	            String key = String.join(" ", parts[0], parts[1], parts[2], parts[3]);
	            int score = Integer.parseInt(parts[4]);

	            if (infoMap.containsKey(key)) {
	                List<Integer> scores = infoMap.get(key);
	                // 이진 탐색으로 score 이상의 인덱스 찾기
	                int pos = lowerBound(scores, score);
	                answer[idx] = scores.size() - pos;
	            } else {
	                answer[idx] = 0;
	            }
	            idx++;
	        }

	        return answer;
	    }

	    // 이진 탐색 구현
	    private int lowerBound(List<Integer> list, int target) {
	        int left = 0, right = list.size();
	        while (left < right) {
	            int mid = (left + right) / 2;
	            if (list.get(mid) < target) {
	                left = mid + 1;
	            } else {
	                right = mid;
	            }
	        }
	        return left;
	    }

	    public static void main(String[] args) {
	        Solution sol = new Solution();
	        String[] info = {
	            "java backend junior pizza 150",
	            "python frontend senior chicken 210",
	            "python frontend senior chicken 150",
	            "cpp backend senior pizza 260",
	            "java backend junior chicken 80",
	            "python backend senior chicken 50"
	        };
	        String[] query = {
	            "java and backend and junior and pizza 100",
	            "python and frontend and senior and chicken 200",
	            "cpp and - and senior and pizza 250",
	            "- and backend and senior and - 150",
	            "- and - and - and chicken 100",
	            "- and - and - and - 150"
	        };
	        System.out.println(Arrays.toString(sol.solution(info, query))); // [1, 1, 1, 1, 2, 4]
	    }
	}