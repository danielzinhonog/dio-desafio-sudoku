package brcomdio.uicustom.panel;

import java.util.List;
import javax.swing.JPanel;
import java.awt.Dimension;
import static java.awt.Color.black;
import javax.swing.border.LineBorder;
import brcomdio.uicustom.input.NumberText;

public class SudokuSector extends JPanel{
    public SudokuSector(final List<NumberText> textFields){
        var dimension = new Dimension(170, 170);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
        textFields.forEach(this::add);
    }
}