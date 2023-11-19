import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int numberOfTeams = in.readInt();

        for (int i = 0; i < numberOfTeams; i++) {
            // read teamname
            in.readString();
            // 8 times read int
            in.readInt();
        }
    }

    // number of teams
    public int numberOfTeams() {
        return -1;
    }

    // all teams
    public Iterable<String> teams() {
        return null;
    }

    // number of wins for given team
    public int wins(String team) {
        return -1;
    }

    // number of losses for given team
    public int losses(String team) {
        return -1;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return -1;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return -1;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
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
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
