import java.util.Set;

public class BaseballEliminationTest {

    public static void main(String[] args) {
        BaseballElimination baseballElimination = new BaseballElimination(args[0]);

        testTeams(baseballElimination);
        testStatistics(baseballElimination);
        testMatchesAgainst(baseballElimination);
        testEliminations(baseballElimination);
    }

    private static void testTeams(BaseballElimination baseballElimination) {
        Set<String> expectedTeams = Set.of("New_York", "Baltimore", "Boston", "Toronto", "Detroit");
        Set<String> teams = (Set<String>) baseballElimination.teams();
        assertThat(teams.size() == 5);
        assertThat(baseballElimination.numberOfTeams() == 5);
        assertThat(expectedTeams.containsAll(teams));
    }

    private static void testStatistics(BaseballElimination baseballElimination) {
        Exception exception = null;
        try {
            baseballElimination.wins("no-team-name");
        } catch (Exception e) {
            exception = e;
        }
        assertThat(exception != null);
        assertThat(exception.getClass() == IllegalArgumentException.class);

        assertThat(baseballElimination.wins("New_York") == 75);
        assertThat(baseballElimination.wins("Baltimore") == 71);
        assertThat(baseballElimination.wins("Boston") == 69);
        assertThat(baseballElimination.wins("Toronto") == 63);
        assertThat(baseballElimination.wins("Detroit") == 49);

        assertThat(baseballElimination.losses("New_York") == 59);
        assertThat(baseballElimination.losses("Baltimore") == 63);
        assertThat(baseballElimination.losses("Boston") == 66);
        assertThat(baseballElimination.losses("Toronto") == 72);
        assertThat(baseballElimination.losses("Detroit") == 86);

        assertThat(baseballElimination.remaining("New_York") == 21);
        assertThat(baseballElimination.remaining("Baltimore") == 16);
        assertThat(baseballElimination.remaining("Boston") == 10);
        assertThat(baseballElimination.remaining("Toronto") == 14);
        assertThat(baseballElimination.remaining("Detroit") == 7);
    }

    private static void testMatchesAgainst(BaseballElimination baseballElimination) {
        assertThat(baseballElimination.against("New_York", "New_York") == 0);
        assertThat(baseballElimination.against("New_York", "Baltimore") == 3);
        assertThat(baseballElimination.against("New_York", "Boston") == 8);
        assertThat(baseballElimination.against("New_York", "Toronto") == 7);
        assertThat(baseballElimination.against("New_York", "Detroit") == 3);

        assertThat(baseballElimination.against("Baltimore", "New_York") == 3);
        assertThat(baseballElimination.against("Baltimore", "Baltimore") == 0);
        assertThat(baseballElimination.against("Baltimore", "Boston") == 2);
        assertThat(baseballElimination.against("Baltimore", "Toronto") == 7);
        assertThat(baseballElimination.against("Baltimore", "Detroit") == 4);

        assertThat(baseballElimination.against("Boston", "New_York") == 8);
        assertThat(baseballElimination.against("Boston", "Baltimore") == 2);
        assertThat(baseballElimination.against("Boston", "Boston") == 0);
        assertThat(baseballElimination.against("Boston", "Toronto") == 0);
        assertThat(baseballElimination.against("Boston", "Detroit") == 0);

        assertThat(baseballElimination.against("Toronto", "New_York") == 7);
        assertThat(baseballElimination.against("Toronto", "Baltimore") == 7);
        assertThat(baseballElimination.against("Toronto", "Boston") == 0);
        assertThat(baseballElimination.against("Toronto", "Toronto") == 0);
        assertThat(baseballElimination.against("Toronto", "Detroit") == 0);

        assertThat(baseballElimination.against("Detroit", "New_York") == 3);
        assertThat(baseballElimination.against("Detroit", "Baltimore") == 4);
        assertThat(baseballElimination.against("Detroit", "Boston") == 0);
        assertThat(baseballElimination.against("Detroit", "Toronto") == 0);
        assertThat(baseballElimination.against("Detroit", "Detroit") == 0);
    }

    private static void testEliminations(BaseballElimination baseballElimination) {
        assertThat(!baseballElimination.isEliminated("New_York"));
        assertThat(!baseballElimination.isEliminated("Baltimore"));
        assertThat(!baseballElimination.isEliminated("Boston"));
        assertThat(!baseballElimination.isEliminated("Toronto"));
        assertThat(baseballElimination.isEliminated("Detroit"));
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}
