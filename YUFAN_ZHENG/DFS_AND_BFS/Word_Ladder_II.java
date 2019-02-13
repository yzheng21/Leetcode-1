package com.yzheng21.DFS_AND_BFS;

import java.util.*;

public class Word_Ladder_II {

    public List<List<String>> findLadders(String start, String end, Set<String> dict) {
        List<List<String>> ladders = new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        Map<String, Integer> distance = new HashMap<String, Integer>();

        dict.add(start);
        dict.add(end);
        bfs(map, distance, start, end, dict);
        List<String> path = new ArrayList<String>();
        dfs(ladders, path, end, start, distance, map);
        return ladders;
    }

    // 将得图从end -> start 得到所有结果
    private void dfs(List<List<String>> ladders, List<String> path, String cur, String start,
                Map<String, Integer> distance, Map<String, List<String>> map) {
        if (cur.equals(start)) {
            Collections.reverse(path);
            ladders.add(new ArrayList<String>(path));
            Collections.reverse(path);
        } else {
            for (String next: map.get(cur)) {
                if (distance.containsKey(next) && distance.get(cur) == distance.get(next) + 1) {
                    dfs(ladders, path, next, start, distance, map);
                }
            }
        }
        path.remove(path.size()-1);
    }

    // 构建图，通过bfs得到层次结构
    private void bfs(Map<String, List<String>> map, Map<String, Integer> distance, String start,
                     String end, Set<String> dict) {
        Queue<String> q = new LinkedList<String>();
        q.offer(start);
        distance.put(start, 0);
        for (String s : dict) {
            map.put(s, new ArrayList<String>());
        }
        while (!q.isEmpty()) {
            String cur = q.poll();
            List<String> nextList = expand(cur, dict);
            for (String next: nextList) {
                map.get(next).add(cur);
                if (!distance.containsKey(next)) {
                    distance.put(next, distance.get(cur) + 1);
                    q.offer(next);
                }
            }
        }
    }

    private List<String> expand(String cur, Set<String> dict) {
        List<String> expansion = new ArrayList<String>();
        for (int i=0; i < cur.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                if (ch != cur.charAt(i)) {
                    String expanded = cur.substring(0, i) + ch + cur.substring(i+1);
                    if (dict.contains(expanded)) {
                        expansion.add(expanded);
                    }
                }
            }
        }
        return expansion;
    }
}
