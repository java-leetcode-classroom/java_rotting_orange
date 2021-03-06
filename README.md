# java_rotting_orange

You are given an `m x n` `grid` where each cell can have one of three values:

- `0` representing an empty cell,
- `1` representing a fresh orange, or
- `2` representing a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** to a rotten orange becomes rotten.

Return *the minimum number of minutes that must elapse until no cell has a fresh orange*. If *this is impossible, return* `-1`.

## Examples

**Example 1:**

![https://assets.leetcode.com/uploads/2019/02/16/oranges.png](https://assets.leetcode.com/uploads/2019/02/16/oranges.png)

```
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4

```

**Example 2:**

```
Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.

```

**Example 3:**

```
Input: grid = [[0,2]]
Output: 0
Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.

```

**Constraints:**

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 10`
- `grid[i][j]` is `0`, `1`, or `2`.

## 解析

題目 給定一個 m by n 矩陣 grid,

grid 內的每個值 grid[r][c] 有三種值,

0 代表該 cell 沒有 orange

1 代表該 cell 有 fresh orange

2 代表該 cell 有 rotten orange

假設 每分鐘 值是 2 的 cell 會感染 水平方向以及垂直方向相鄰 值是 1 的 cell 變成 2

要求實作一個演算法算出 所以 orange 都變成 rotten 的時間

假設 有一個 fresh orange 不與 rotten cell 水平及垂直相鄰 

代表無法感染所有 fresh orange 所以回傳 -1

直覺的作法是先蒐集所有 rotten orange 座標 還有所有 fresh orange 個數

然後 從每個 rotten orange 做 BFS

BFS 透過一個 queue 來儲存要處理的座標

每次處理完就把時間加一

每感染一個 fresh orange 就把 fresh orange 總數減一

當queue 為 0 

如果 fresh orange 不為 0

則累計的時間就是感染時間，否則就是 -1

![](https://i.imgur.com/JA9R0nE.png)

## 程式碼
```java
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

```
## 困難點

1. 思考出如何紀錄還 fresh 的個數
2. 想出如何初始化 rotten orange 起點
3. 理解 BFS

## Solve Point

- [x]  設計一個結構紀錄 rotten orange 的起始位置
- [x]  透過 BFS 逐步計算 感染的 orange