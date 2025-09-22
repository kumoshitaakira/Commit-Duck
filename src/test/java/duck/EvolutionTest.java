package duck;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {

    @Test
    public void testDecideStage() {
        assertEquals(Evolution.Stage.EGG, Evolution.decideStage(0, Evolution.Stage.EGG));
    }
}