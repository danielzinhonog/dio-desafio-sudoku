package brcomdio.uicustom.screen;

import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import brcomdio.service.BoardService;
import brcomdio.uicustom.frame.MainFrame;
import brcomdio.uicustom.panel.MainPanel;
import brcomdio.uicustom.button.ResetButton;
import brcomdio.uicustom.button.FinishGameButton;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import brcomdio.uicustom.button.CheckGameStatusButton;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class MainScreen{
    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;
    private JButton checkGameStatusButton;
    private JButton resetButton;
    private JButton finishGameButton;

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
    }
    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void addResetButton(final JPanel mainPanel){
        resetButton = new ResetButton(e ->{
            var dialogResult = JOptionPane.showConfirmDialog(
                null, 
                "Deseja realmente reiniciar o jogo?", 
                "Limpar o jogo",
                YES_NO_OPTION,
                QUESTION_MESSAGE
            );
            if(dialogResult == 0){
                boardService.reset();
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
            JOptionPane.showMessageDialog(null, message);
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