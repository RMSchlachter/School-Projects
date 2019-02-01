import junit.framework.JUnit4TestAdapter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestTerritory.class,
        TestBoard.class,
        TestCard.class,
        TestContinent.class,
        TestPlayer.class,
        TestDice.class,
        TestGame.class,
        TestCombat.class
})

public class TestSuite {

}



