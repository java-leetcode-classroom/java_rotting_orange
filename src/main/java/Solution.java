import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Solution {
    static class Pair {
        private int row;
        private int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return row == pair.row && col == pair.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
    public int orangesRotting(int[][] grid) {
        int ROW = grid.length;
        int COL = grid[0].length;
        int elapsedTime = 0;
        int fresh = 0;
        Queue<Pair> queue = new LinkedList<>();
        Pair[] directions = new Pair[]{
                new Pair(1,0),
                new Pair(-1, 0),
                new Pair(0, -1),
                new Pair(0, 1)};
        // collect rotten orange and fresh number
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                int orange = grid[row][col];
                switch (orange) {
                    case 1:
                        fresh++;
                        break;
                    case 2:
                        queue.add(new Pair(row, col));
                        break;
                }
            }
        }
        while (queue.size() > 0 && fresh != 0) {
            int qLen = queue.size();
            for (int idx = 0; idx < qLen; idx++) {
                Pair top = queue.poll();
                for (Pair direction : directions) {
                    int shiftedRow = top.row + direction.row;
                    int shiftedCol = top.col + direction.col;
                    if (shiftedRow < 0 || shiftedRow >= ROW || shiftedCol < 0 || shiftedCol >= COL ||
                        grid[shiftedRow][shiftedCol] != 1
                    ) {
                        continue;
                    }
                    grid[shiftedRow][shiftedCol] = 2;
                    queue.add(new Pair(shiftedRow, shiftedCol));
                    fresh--;
                }
            }
            elapsedTime++;
        }
        return (fresh == 0)? elapsedTime: -1;
    }
}
