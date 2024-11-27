package io.invokegs.pta;

import java.util.*;

public class ConnectionSolver {
    private final int connectionLength;

    public ConnectionSolver(int connectionLength) {
        this.connectionLength = connectionLength;
    }

    /**
     * Find the longest sequence of fragments that can be joined together
     * such that each fragment is connected to the next fragment by the last
     *
     * @param fragments list of fragments
     * @return longest sequence of fragments
     */
    public Result findLongestSequence(List<String> fragments) {
        Map<String, List<String>> adjacencyList = buildGraph(fragments);
        List<String> longestPath = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();

        for (String fragment : fragments) {
            Set<String> visited = new HashSet<>();
            currentPath.add(fragment);
            visited.add(fragment);

            findLongestPath(adjacencyList, fragment, visited, currentPath, longestPath);

            currentPath.clear();
        }

        return new Result(joinSequence(longestPath), longestPath);
    }

    /**
     * Find the longest path in the graph with using DFS
     *
     * @param graph       adjacency list representation of the graph
     * @param current     current node being explored
     * @param visited     set of visited nodes to avoid cycles
     * @param currentPath current path being explored
     * @param longestPath longest path found so far
     */
    private void findLongestPath(Map<String, List<String>> graph, String current,
                                 Set<String> visited, List<String> currentPath,
                                 List<String> longestPath) {
        if (currentPath.size() > longestPath.size()) {
            longestPath.clear();
            longestPath.addAll(currentPath);
        }

        for (String neighbor : graph.getOrDefault(current, List.of())) {
            if (!visited.contains(neighbor)) {
                // Dive deeper in current neighbor
                visited.add(neighbor);
                currentPath.add(neighbor);

                findLongestPath(graph, neighbor, visited, currentPath, longestPath);

                // Backtrack to explore other paths
                visited.remove(neighbor);
                currentPath.removeLast();
            }
        }
    }

    /**
     * Build a graph where each fragment is a node and there is an edge between two nodes if the
     *
     * @param fragments list of fragments
     * @return adjacency list representation of the graph
     */
    private Map<String, List<String>> buildGraph(List<String> fragments) {
        Map<String, List<String>> graph = new HashMap<>();

        for (String fragment : fragments) {
            graph.putIfAbsent(fragment, new ArrayList<>());

            String suffix = fragment.substring(fragment.length() - connectionLength);
            for (String potentialNeighbor : fragments) {
                if (!fragment.equals(potentialNeighbor)) {
                    String prefix = potentialNeighbor.substring(0, connectionLength);
                    if (suffix.equals(prefix)) {
                        graph.get(fragment).add(potentialNeighbor);
                    }
                }
            }
        }

        return graph;
    }

    /**
     * Join the fragments to form a sequence
     *
     * @param fragments list of fragments
     * @return joined sequence excluding duplicate connections
     */
    private String joinSequence(List<String> fragments) {
        StringBuilder builder = new StringBuilder();
        if (!fragments.isEmpty()) {
            builder.append(fragments.getFirst());
            for (int i = 1; i < fragments.size(); i++) {
                String fragment = fragments.get(i);
                builder.append(fragment.substring(connectionLength));
            }
        }
        return builder.toString();
    }

    public record Result(String sequence, List<String> fragments) {
    }
}
