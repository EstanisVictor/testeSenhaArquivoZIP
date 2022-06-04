package testegeradorsenha;

public class FindPasswordFile {
    
    public static void main(String[] args) {
        
        PasswordGenerator generates = new PasswordGenerator();
        generates.takeAsciiValues();
        
        //Pegando o total de threads da máquina
        int numbersThreads = Runtime.getRuntime().availableProcessors();
        
        /*Pegando total de probabilidades contando com:
        26 letras maiusculas
        26 letras minusculas
        0 - 9
        Total = 62
        E a quantidade de caracteres a ser gerados (7)
        62 - 7 = 55
        */
        int probabilityASCII = generates.getNumerosASCII().size() - generates.getResultado().length;
        
        //quantidade de senhas que cada thread irá gerar/testar
        int quantThreadExec = probabilityASCII/numbersThreads;
        
        for (int indexThreads = 0; indexThreads < numbersThreads; indexThreads++) {
            //Cria thread
            Thread myThreads = new Thread(new Runnable(){
                //Método que será executado pela thread
                public void run(){
                    int indiceThread = Integer.parseInt(Thread.currentThread().getName());
                    
                    int initialValue = quantThreadExec*indiceThread;
                    
                    int finalValue = quantThreadExec*indiceThread+quantThreadExec;
                    
                    int restoSenha = probabilityASCII%numbersThreads;
                    
                    if(indiceThread == (numbersThreads-1)){
                        generates.passwordTest(initialValue, finalValue+restoSenha);
                    }else{
                        generates.passwordTest(initialValue, finalValue);
                    }
                }
                //Colocando o indice (indexThreads) como nome da thread
            }, String.valueOf(indexThreads));
            
            myThreads.start();
            
        }
    }
    
}
