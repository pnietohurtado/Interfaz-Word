

package controller;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.segundoevaluableinterfaces.vista.Sound;
import com.mycompany.segundoevaluableinterfaces.vista.Vista;
import java.awt.Color;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import modelo.DocumentoModelo;


public class DocumentoController {

    private Vista vista; 
    private DocumentoModelo document; 
    private Sound sonido; 
    private int numero; 
   
    public DocumentoController(Vista vista, Sound sonido){
         
        this.vista = vista; 
        this.sonido = sonido; 
        this.numero = 0; 
    }
    
    
     public DocumentoController(DocumentoModelo doc, Vista vista ){
        this.document = doc; 
        this.vista = vista; 
        this.numero = 0; 
    }
    
    
    
    public void changeTheme(){
        if(vista.getToggleButton().isSelected()){
            try{
                vista.getToggleButton().setIcon(new ImageIcon(getClass().getResource("/Theme/Sol.png")));
                UIManager.setLookAndFeel(new FlatDarkLaf());
                FlatLaf.updateUI();
            }catch(Exception e){
                
            }
        }else{
            try{
                vista.getToggleButton().setIcon(new ImageIcon(getClass().getResource("/Theme/Luna.png")));
                UIManager.setLookAndFeel(new FlatLightLaf());
                FlatLaf.updateUI();
            }catch(Exception e){
                    
            }
        }
    }
    
    // Guardar el documento 
    
    public void saveDocumment(){
        if(this.document == null){
            document = new DocumentoModelo(this); 
        }
        JFileChooser file = new JFileChooser(); 
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resultado = file.showOpenDialog(this.vista); 
        
        if(resultado == JFileChooser.APPROVE_OPTION){
            java.io.File archivo = file.getSelectedFile(); 
            
            File documento = new File(archivo.getAbsolutePath() + ".txt");     
            this.document.setContent(this.vista.getText());
            this.document.saveData(documento.getAbsolutePath());
              
        }
    }
    
    // Cargar el documento
    
    public void loadDocument(){
        if(this.document == null){
            document = new DocumentoModelo(this); 
        }
        JFileChooser file = new JFileChooser(); 
        int resultado = file.showOpenDialog(this.vista); 
        
        if(resultado == JFileChooser.APPROVE_OPTION){
            java.io.File archivo = file.getSelectedFile(); 
            String message = ""; 
            
            message = this.document.loadData(archivo.getAbsolutePath()); 
            this.vista.setText(message);
            
        }
    }
    
    
    // Conseguir el color 
    
    public void chooseColor(){
        if(this.document == null){
            document = new DocumentoModelo(this); 
        }
        JColorChooser color = new JColorChooser(); 
        int resultado = JOptionPane.showConfirmDialog(null, color, "Selecciona un color", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if(resultado == JOptionPane.OK_OPTION){
            Color c = color.getColor(); 
            StyleConstants.setFontSize(this.vista.getAttri(), (int) this.document.getSize()); 
            StyleConstants.setForeground(this.vista.getAttri(), c);
            this.vista.getTextElement().setCharacterAttributes(this.vista.getAttri(), false);
        }
    }
    
    
    
    // Cambiar a negrita
    
    public void pickBold(){
        if(this.document == null){
            document = new DocumentoModelo(this); 
        }
        if(this.document.getNegrita()){
            this.document.setNegrita(false);
        }else if(!this.document.getNegrita()){
            this.document.setNegrita(true);
            StyleConstants.setFontSize(this.vista.getAttri(), (int) this.document.getSize()); 
            StyleConstants.setBold(this.vista.getAttri(), true);
            this.vista.getTextElement().setCharacterAttributes(this.vista.getAttri(), false);
        }
    }
    
    
    
    // Información 
    
    public void showInformation(String message, String tipo){
        Icon icon= null;  
        if(tipo.equals("autor")){
            icon = new ImageIcon(getClass().getResource("/Icons/Toy.png")); 
            JOptionPane.showConfirmDialog(null, message, "Autor/a", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,icon );
        }else if(tipo.equals("controles")){
            icon = new ImageIcon(getClass().getResource("/Icons/Duda.png")); 
            JOptionPane.showConfirmDialog(null, message, "Controles", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,icon );
        }
    }
    
    
    // Size 
    
    public void sizeLetter(){
        if(this.document == null){
            document = new DocumentoModelo(this); 
        }
        this.document.setSize(this.vista.letterSize());
        StyleConstants.setFontSize(this.vista.getAttri(), (int) this.document.getSize()); 
        this.vista.getTextElement().setCharacterAttributes(this.vista.getAttri(), false);
    }
   
    
    // Music 
    
    public void musicaWord(String palabra){
        switch(palabra){
            case "candy": {
                if(numero!=0)
                    sonido.stop(numero); 
                sonido.play(1, false, "music");
                this.numero = 1; 
                break; 
            }
            case "aplauso": {
                if(numero!=0)
                    sonido.stop(numero);
                sonido.play(2, false, "music");
                this.numero = 2; 
                break; 
            }
            case "gargola" : {
                if(numero!=0)
                    sonido.stop(numero);
                sonido.play(3,false, "music"); 
                this.numero = 3; 
                break; 
            }
            case "diabla" : {
                if(numero!=0)
                    sonido.stop(numero);
                sonido.play(4,false, "music"); 
                this.numero = 4; 
                break; 
            }
            default: 
                
                break; 
        }
    }
    
    
    // Cambiar fuente 
    public void cambiarFuente(String font){
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, font);
        this.vista.getTextElement().setCharacterAttributes(attrs, false);
    }
}
