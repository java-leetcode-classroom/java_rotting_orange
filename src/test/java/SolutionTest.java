import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    final private Solution sol = new Solution();
    @Test
    void orangesRottingExample1() {
        assertEquals(4, sol.orangesRotting(new int[][]{
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        }));
    }
    @Test
    void orangesRottingExample2() {
        assertEquals(-1, sol.orangesRotting(new int[][]{
                {2, 1, 1},
                {0, 1, 0},
                {1, 0, 1}
        }));
    }
    @Test
    void orangesRottingExample3() {
        assertEquals(0, sol.orangesRotting(new int[][]{
                {0,2},
        }));
    }
}