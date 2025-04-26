package brcomdio.uicustom.input;

import java.awt.Font;
import java.awt.Dimension;
import brcomdio.model.Space;
import javax.swing.JTextField;
import static java.awt.Font.PLAIN;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberText extends JTextField{
    private final Space space;

    public NumberText(final Space space){
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if(space.isFixed()){
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(final DocumentEvent e){
                changeSpace();
            }
            public void removeUpdate(final DocumentEvent e){
                changeSpace();
            }
            public void changedUpdate(final DocumentEvent e){
                changeSpace();
            }
            private void changeSpace(){
                if(getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));
            }
        });
    }
}