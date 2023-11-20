import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {

    private final Map<String, Integer> teams = new HashMap<>();
    private final String[] teamNames;
    private final int[] wins;
    private final int[] losses;
    private final int[] remainingMatches;
    private final int[][] matchesAgainst;
    private final Map<String, FlowNetworkWithFordFulkerson> cache = new HashMap<>();

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int numberOfTeams = in.readInt();
        teamNames = new String[numberOfTeams];
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remainingMatches = new int[numberOfTeams];
        matchesAgainst = new int[numberOfTeams][numberOfTeams];

        populateData(in, numberOfTeams);
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validate(team);

        return wins[teams.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validate(team);

        return losses[teams.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validate(team);

        return remainingMatches[teams.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validate(team1);
        validate(team2);

        return matchesAgainst[teams.get(team1)][teams.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validate(team);

        for (int i = 0; i < numberOfTeams(); i++)
            if (wins[i] > wins(team) + remaining(team)) return true;

        if (!cache.containsKey(team))
            cache.put(team, new FlowNetworkWithFordFulkerson(createFlowNetwork(team)));

        FlowNetwork flowNetwork = cache.get(team).getFlowNetwork();

        for (FlowEdge e : flowNetwork.adj(0)) {
            if (e.capacity() > e.flow())
                return true;
        }

        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validate(team);

        for (int i = 0; i < numberOfTeams(); i++)
            if (wins[i] > wins(team) + remaining(team)) return Set.of(teamNames[i]);

        if (!cache.containsKey(team))
            cache.put(team, new FlowNetworkWithFordFulkerson(createFlowNetwork(team)));

        FlowNetwork flowNetwork = cache.get(team).getFlowNetwork();
        FordFulkerson ff = cache.get(team).getFordFulkerson();
        Set<String> teamsToEliminate = new HashSet<>();

        for (int i = 0; i < numberOfTeams(); i++) {
            if (ff.inCut(flowNetwork.V() - 1 - i - 1))
                teamsToEliminate.add(teamNames[i]);
        }

        return teamsToEliminate.isEmpty() ? null : teamsToEliminate;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    private void validate(String team) {
        if (teams.get(team) == null)
            throw new IllegalArgumentException("No team exists with the given name");
    }

    private void populateData(In in, int numberOfTeams) {
        for (int i = 0; i < numberOfTeams; i++) {
            teamNames[i] = in.readString();
            teams.put(teamNames[i], i);
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remainingMatches[i] = in.readInt();

            for (int k = 0; k < numberOfTeams; k++) {
                matchesAgainst[i][k] = in.readInt();
            }
        }
    }

    private FlowNetwork createFlowNetwork(String team) {
        int n = numberOfTeams() - 1;
        int numberOfVertexes = 1 + (n * n - n) / 2 + numberOfTeams() + 1;
        FlowNetwork flowNetwork = new FlowNetwork(numberOfVertexes);
        int s = 0, t = numberOfVertexes - 1, currentTeamIndex = teams.get(team), index = 1;

        for (int i = 0; i < numberOfTeams(); i++) {
            if (i == currentTeamIndex) continue;

            for (int j = i + 1; j < numberOfTeams(); j++) {
                if (j == currentTeamIndex) continue;

                flowNetwork.addEdge(new FlowEdge(s, index, matchesAgainst[i][j]));
                flowNetwork.addEdge(new FlowEdge(index, t - i - 1, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(index++, t - j - 1, Double.POSITIVE_INFINITY));
            }

            flowNetwork.addEdge(new FlowEdge(t - i - 1, t, wins[currentTeamIndex] + remainingMatches[currentTeamIndex] - wins[i]));
        }

        return flowNetwork;
    }

    private static class FlowNetworkWithFordFulkerson {
        private final FlowNetwork flowNetwork;
        private final FordFulkerson fordFulkerson;

        FlowNetworkWithFordFulkerson(FlowNetwork flowNetwork) {
            this.flowNetwork = flowNetwork;
            fordFulkerson = new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
        }

        public FlowNetwork getFlowNetwork() {
            return flowNetwork;
        }

        public FordFulkerson getFordFulkerson() {
            return fordFulkerson;
        }
    }
}
