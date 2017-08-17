import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

/**
 * Created by Memo on 30.06.2017.
 */
@SuppressWarnings("unused")
public class TicTacToe implements ImmutableBoard<Move> {
    private final int[] board;
    private final int turn;// Server ist 1, client ist -1
    private final List<Move> history;
    private final TicTacToe parent;
    private final int depth;
    public Network network;


    public TicTacToe() {
        board = new int[9];
        history = new ArrayList<>();
        parent = null;
        turn = +1;
        depth = 0;
        network = new Network();
    }

    public TicTacToe(TicTacToe b, Move move) {
        this.board = Arrays.copyOf(b.board, 9);
        this.history = b.history.stream().collect(Collectors.toList());
        this.network = b.network;
        this.turn = -b.turn;
        this.board[move.getT3()] = -turn;// hier minus weil die turn zuweisung jetzt vorher geschieht
        this.depth = b.depth + 1;
        this.parent = b;
        this.history.add(move);
    }

    public int getTurn() {
        return turn;
    }

    @Override
    public ImmutableBoard makeMove(Move move) {
        TicTacToe b = new TicTacToe(this, move);
        return b;
    }

    @Override
    public List moves() {
        List<Move> z = new ArrayList<>();
        IntStream.range(0, 9).filter(i -> board[i] == 0).forEach(el -> z.add(new Move(el)));
        return z;
    }

    @Override
    public List getHistory() {
        return history;
    }

    @Override
    public boolean isWin() {
        int[][] rows = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}};
        if (depth < 5) return false;
        return IntStream.range(0, 8).
                map(row -> Arrays.stream(rows[row]).
                        map(pos -> board[pos]).
                        sum()).
                map(Math::abs).
                filter(r -> r == 3).
                findAny().
                isPresent();
    }

    @Override
    public boolean isDraw() {
        return depth == 9;
    }

    @Override
    public String toString() {
        char[] repr = {'O', '.', 'X'};
        String s = "\n";
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) s += repr[board[i * 3 + j] + 1] + " ";
            s += "\n";
        }
        s += "\nGib gültigen Zug ein (1-9 + <ENTER>)" + "\n[0: Computer zieht, ?: Hilfe]: _";
        return s;
    }

}
