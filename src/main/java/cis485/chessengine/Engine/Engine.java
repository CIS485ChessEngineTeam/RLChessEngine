package cis485.chessengine.Engine;

import cis485.chessengine.Engine.Search.MCTS;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class Engine {
    private int secondsPerMove;
    private Side side;
    private MultiLayerNetwork model;
    private MCTS mcts;

    public Engine(MultiLayerNetwork model) {
        this.secondsPerMove = 10; // default
        this.model = model;
    }

    /**
     * Run the engine on the position.
     * @param position The position in FEN.
     * @return Move
     */
    public Move run(String position) {
        mcts = new MCTS(model, side, position);
        long start = System.nanoTime();
        while (System.nanoTime() - start < 1_000_000_000L * secondsPerMove) {
            mcts.step();
        }
        return mcts.getBest();
    }

    public int getVisits() {
        if (mcts == null) {
            return 0;
        }
        return mcts.getVisits();
    }

    public int getSecondsPerMove() {
        return secondsPerMove;
    }

    public void setSecondsPerMove(int secondsPerMove) {
        this.secondsPerMove = secondsPerMove;
    }

    public MultiLayerNetwork getModel() {
        return model;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
