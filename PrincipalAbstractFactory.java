interface Emissor { 
    void enviar(String mensagem);
}

 
class EmissorVisa implements Emissor {
    public void enviar(String mensagem) { 
        System.out.println("Enviando dados para servidor Visa.");
        System.out.println(mensagem);
    }
}

class EmissorMasterCard implements Emissor {
    public void enviar(String mensagem) { 
        System.out.println("Enviando dados para servidor MasterCard");
        System.out.println(mensagem);
    }
}

interface Receptor {
    String receber();
}

class ReceptorVisa implements Receptor { 
    public String receber() {
        return "Recebendo Servidor Visa";        
    } 
}

class ReceptorMasterCard implements Receptor { 
    public String receber () {
        return "Recebendo Servidor MasterCard";
    } 
}

class EmissorCreator { 
    public static final int VISA = 0;
    public static final int MASTERCARD = 1; 
    
    public Emissor create(int tipoDeEmissor) {
        if(tipoDeEmissor == EmissorCreator.VISA) { 
            return new EmissorVisa();
        } 
        else if(tipoDeEmissor == EmissorCreator.MASTERCARD) { 
                return new EmissorMasterCard();
             } else {
                        throw new IllegalArgumentException("Tipo de emissor não suportado");
                    } 
    }
}

class ReceptorCreator { 
    public static final int VISA = 0;
    public static final int MASTERCARD = 1; 
    
    public Receptor create(int tipoDeReceptor) {
        if(tipoDeReceptor == ReceptorCreator.VISA) { 
            return new ReceptorVisa();
        } 
        else if(tipoDeReceptor == ReceptorCreator.MASTERCARD) { 
                return new ReceptorMasterCard();
             } else {
                        throw new IllegalArgumentException("Tipo de receptor não suportado");
                    } 
    }
}

//========================================================

interface ComunicadorFactory {
    Emissor createEmissor(); 
    Receptor createReceptor();
}

class VisaComunicadorFactory implements ComunicadorFactory {
    private EmissorCreator emissorCreator = new EmissorCreator(); 
    private ReceptorCreator receptorCreator = new ReceptorCreator();
    
    public Emissor createEmissor() {
        return this.emissorCreator.create(EmissorCreator.VISA); 
    }
    
    public Receptor createReceptor() {
        return this.receptorCreator.create(ReceptorCreator.VISA); 
    }
}

class MasterCardComunicadorFactory implements ComunicadorFactory {
    private EmissorCreator emissorCreator = new EmissorCreator(); 
    private ReceptorCreator receptorCreator = new ReceptorCreator();
    
    public Emissor createEmissor() {
        return this.emissorCreator.create(EmissorCreator.MASTERCARD); 
    }
    
    public Receptor createReceptor() {
        return this.receptorCreator.create(ReceptorCreator.MASTERCARD); 
    }
}

//========================================================
class Cartao {
    protected String numero;
    protected String senha;
    protected String bandeira;

    Cartao(String numero, String senha){
        this.numero = numero;
        this.senha = senha;
    }

    String getNumero() { return numero;}
    String getSenha() { return senha;}
    String getBandeira() { return bandeira;}
}

class CartaoVisa extends Cartao {
    CartaoVisa(String numero, String senha){
        super(numero, senha);
        bandeira = "Visa";
    }
}

class CartaoMasterCard extends Cartao {
    CartaoMasterCard(String numero, String senha){
        super(numero, senha);
        bandeira = "MasterCard";
    }
}


//========================================================
class MaquinaCartao {
    
    static void realizarCompra(Cartao cartao, double valor, String senha){
        
        if (cartao.getSenha().equals(senha)){
            ComunicadorFactory factory = getComunicadorFactory(cartao);
            String transacao = "{cartao:"+cartao.getNumero()+"}-{valor:"+valor+"}";
            factory.createEmissor().enviar(transacao);
            String resposta = factory.createReceptor().receber();
            System.out.println(resposta);
        } else {
            System.out.println("Senha incorreta");
        }
    }

    static private ComunicadorFactory getComunicadorFactory(Cartao cartao) {
        String bandeira = cartao.getBandeira();
        Class clazz;
        ComunicadorFactory aux=null;

        try {
            clazz = Class.forName(bandeira + "ComunicadorFactory");
            aux = (ComunicadorFactory) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return aux;
    }
}


//========================================================

public class PrincipalAbstractFactory {
    public static void main(String args[]) {
        System.out.println();
        System.out.println("==========");

        Cartao cartao = new CartaoVisa("1111", "t");
        MaquinaCartao.realizarCompra(cartao, 50.00, "t");

        System.out.println();
        System.out.println("==========");
        
        cartao = new CartaoMasterCard("2222", "t");
        MaquinaCartao.realizarCompra(cartao, 240.00, "t");

        System.out.println();
        System.out.println("==========");

    }
}