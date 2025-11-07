

package modelo;

import controller.DocumentoController;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class DocumentoModelo {

    private final int DEFAULT_SIZE = 12; 
    
    private boolean negrita; 
    private Font font; 
    private String tipo; 
    private int size;
    
    private String contenido; 
    
    private DocumentoController documentController; 
    
    public DocumentoModelo(DocumentoController documentController){
        this.documentController = documentController; 
        this.contenido = ""; 
        this.negrita = false; 
        this.size = DEFAULT_SIZE; 
    }
    
    public DocumentoModelo(Font fuente, String tipo_fuente, int tamano_fuente, DocumentoController dc){
        this(dc); 
        this.font = fuente; 
        this.tipo = tipo_fuente; 
        
    }

    public boolean isNegrita() {
        return negrita;
    }

    public void setNegrita(boolean negrita) {
        this.negrita = negrita;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public int getDefaultSize(){return this.DEFAULT_SIZE;}
    public boolean getNegrita(){return this.negrita; }
    
    
    
    
    // Funciones útiles 
    
    public void setContent(String m){
        this.contenido = m; 
    }
    
    
    // Save Data
    
    public void saveData(String url){
        try{
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(url)); 
            bw.write(this.contenido);
            bw.close(); 
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    // Load Data 
    
    public String  loadData(String url){
        
        String message = ""; 
        
        try{
            
             BufferedReader bf = new BufferedReader(new FileReader(url));
                String linea; 
                
                while((linea = bf.readLine()) != null){
                    
                    message = message + linea + "\n"; 
                    
                }
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return message; 
    }
    

    @Override
    public String toString() {
        return "DocumentoModelo{" + "DEFAULT_SIZE=" + DEFAULT_SIZE + ", negrita=" + negrita + ", font=" + font + ", tipo=" + tipo + ", size=" + size + '}';
    }
    
    
    
    
    
}
