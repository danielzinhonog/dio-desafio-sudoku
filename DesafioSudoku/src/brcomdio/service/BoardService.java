package brcomdio.service;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import brcomdio.model.Space;
import brcomdio.model.Board;
import brcomdio.model.GameStatusEnum;

public class BoardService{
    private final Board board;
    private final static int BOARD_LIMIT = 9;

    public BoardService(final Map<String, String> gameConfig){
        this.board = new Board(initBoard(gameConfig));
    }
    public List<List<Space>> getSpaces(){
        return board.getSpaces();
    }
    public void reset(){
        board.reset();
    }
    public boolean hasErros(){
        return board.hasErros();
    }
    public GameStatusEnum getStatus(){
        return board.getStatus();
    }
    public boolean gameIsFinished(){
        return board.gameIsFinished();
    }
    private List<List<Space>> initBoard(final Map<String, String> gameConfing){
        List<List<Space>> spaces = new ArrayList<>();
        for(int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_LIMIT; j++){
                var positionConig = gameConfing.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        return spaces;
    }
}