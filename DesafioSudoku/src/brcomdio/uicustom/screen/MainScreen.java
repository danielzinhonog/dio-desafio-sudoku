package brcomdio.uicustom.screen;

import java.util.Map;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import java.util.ArrayList;
import brcomdio.model.Space;
import javax.swing.JOptionPane;
import brcomdio.service.BoardService;
import brcomdio.service.NotifierService;
import brcomdio.uicustom.frame.MainFrame;
import brcomdio.uicustom.panel.MainPanel;
import brcomdio.uicustom.input.NumberText;
import brcomdio.uicustom.button.ResetButton;
import brcomdio.uicustom.panel.SudokuSector;
import brcomdio.uicustom.button.FinishGameButton;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static brcomdio.service.EventEnum.CLEAR_SPACE;
import brcomdio.uicustom.button.CheckGameStatusButton;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class MainScreen{
    private JButton resetButton;
    private JButton finishGameButton;
    private JButton checkGameStatusButton;
    private final BoardService boardService;
    private final static NotifierService notifierService;
    private final static Dimension dimension = new Dimension(600, 600);

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }
    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for(int r = 0; r < 9; r+=3){
            var endRow = r + 2;
            for(int c = 0; r < 9; c+=3){
                var endCol = r + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endCol, r, endRow);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private List<Space> getSpacesFromSector(final List<List<Space>> spaces, final int initCol, final int endCol, final int initRow, final int endRow){
        List<Space> spaceSector = new ArrayList<>();
        for(int r = initRow; r <= endRow; r++){
            for(int c = initCol; c <= endCol; c++){
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;
    }
    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscriber(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }
    private void addResetButton(final JPanel mainPanel){
        resetButton = new ResetButton(e ->{
            var dialogResult = showConfirmDialog(
                null, 
                "Deseja realmente reiniciar o jogo?", 
                "Limpar o jogo",
                YES_NO_OPTION,
                QUESTION_MESSAGE
            );
            if(dialogResult == 0){
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }
    private void addCheckGameStatusButton(final JPanel mainPanel){
        checkGameStatusButton = new CheckGameStatusButton(e ->{
            var hasErros = boardService.hasErros();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo não foi iniciado.";
                case INCOMPLETE -> "O jogo está incompleto.";
                case COMPLETE -> "O jogo está completo.";
            };
            message += hasErros ? " e contém erros." : " e não contém erros.";
            showMessageDialog(null, message);
        });
        mainPanel.add(checkGameStatusButton);
    }
    private void addFinishGameButton(final JPanel mainPanel){
        finishGameButton = new FinishGameButton(e ->{
            if(boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null,"Parabéns, você concluiu o jogo!");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null,"Seu jogo tem alguma inconsistência. Ajuste e tente novamente.");
            }
        });
        mainPanel.add(checkGameStatusButton);
    }
}