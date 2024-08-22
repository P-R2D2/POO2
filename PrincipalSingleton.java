import java.util.ArrayList;

class ClasseSingleton {
    protected static ClasseSingleton instancia=null;
    protected String texto;
    
    private ClasseSingleton (String texto) {
        this.texto = texto; 
    }

    public static ClasseSingleton getInstance(String texto){
        if (instancia == null) {
            instancia = new ClasseSingleton(texto);
        } 
        return instancia;
         
    }

    public String getTexto(){
        return texto;
    }
}

class OutraClasse {
    public OutraClasse(){
        ClasseSingleton cs = ClasseSingleton.getInstance("Terceira Frase");
        System.out.println("\n\nTexto da instancia OutraClasse:" + cs.getTexto());
    }
}


public class PrincipalSingleton {
    public static void main(String args[]){

        

        ClasseSingleton cs = ClasseSingleton.getInstance("Primeira Frase");
        System.out.println("\n\nTexto da instancia:" + cs.getTexto());

        cs = ClasseSingleton.getInstance("Segunda Frase");
        System.out.println("\n\nTexto da instancia:" + cs.getTexto());

        OutraClasse oc = new OutraClasse();

    }
}
