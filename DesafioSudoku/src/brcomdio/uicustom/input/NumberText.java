package brcomdio.uicustom.input;

import java.awt.Font;
import java.awt.Dimension;
import brcomdio.model.Space;
import javax.swing.JTextField;
import brcomdio.service.EventEnum;
import static java.awt.Font.PLAIN;
import brcomdio.service.EventListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import static brcomdio.service.EventEnum.CLEAR_SPACE;

public class NumberText extends JTextField implements EventListener{
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
    public void update(final EventEnum eventType){
        if(eventType.equals(CLEAR_SPACE) && (this.isEnabled())){
            this.setText("");
        }
    }
}