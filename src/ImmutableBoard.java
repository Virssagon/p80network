import java.io.Serializable;
import java.util.List;

/**
 * Created by Thomas on 30.06.2017.
 */
public interface ImmutableBoard<Move>{
    ImmutableBoard<Move> makeMove(Move move);
    default ImmutableBoard<Move> makeMove(Move... moves) {
        ImmutableBoard<Move> b = this;
        for(Move move : moves) b = b.makeMove(move);
        return b;
    }
    List<Move> moves();
    List<Move> getHistory(); // last move dis list = recent move

    boolean isWin();
    boolean isDraw();
    default boolean isBeginnersTurn() {
        return getHistory().size() % 2 == 0;
    }


    String toString();
}

