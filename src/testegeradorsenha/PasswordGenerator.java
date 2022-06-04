package testegeradorsenha;

import java.io.File;
import java.util.List;
import java.util.Vector;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import static org.paukov.combinatorics.CombinatoricsFactory.createVector;
import static org.paukov.combinatorics.CombinatoricsFactory.createSimpleCombinationGenerator;

public class PasswordGenerator {

    private static Vector<Integer> numbersASCII = new Vector<>();
    private static Vector<Integer> execThread = new Vector<>();
    private static int[] result = new int[7];

    private static String password = "";
    
    private static boolean stop = false;

    public static void passwordTest(int initialValue, int finalValue) {
        
        //Passando apenas as combiações que cada thread irá gerar
        for (int indexExThread = initialValue; indexExThread < finalValue; indexExThread++) {
            execThread.add(numbersASCII.get(indexExThread));
        }
        
        //Criando uma estrutura de armazenamento da própria biblioteca
        ICombinatoricsVector<Integer> combinations = createVector(execThread);
        
        //Biblioteca que gera todas as combinações possiveis
        Generator<Integer> geraCombinacoes = createSimpleCombinationGenerator(combinations,
                7);
        
        //percorrendo o vetor de combinações
        for (ICombinatoricsVector<Integer> comb : geraCombinacoes) {
            //criando vetor de char, cada posição é um caractere da senha
            char symbols[] = new char[result.length];
            //convertendo de inteiro da biblioteca para char
            for (int inndexSymbols = 0; inndexSymbols < result.length; inndexSymbols++) {
                result[inndexSymbols] = comb.getValue(inndexSymbols);
                symbols[inndexSymbols] = (char) result[inndexSymbols];
            }
            //gerando a senha
            password = new String(symbols);
            
            try {
                //passando o arquivo com senha
                ZipFile zipFile = new ZipFile(new File("C:\\Users\\T-GAMER\\Desktop\\teste.zip"));
                //verificando se o arquivo tem senha
                if (zipFile.isEncrypted()) {
                    //setando e fazendo o teste da senha criada
                    zipFile.setPassword(password.toCharArray());
                }
                List fileHeaderList = zipFile.getFileHeaders();
                //percorrendo se a senha for correta para descompactar o arquivo
                for (int i = 0; i < fileHeaderList.size(); i++) {
                    FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);

                    zipFile.extractFile(fileHeader, "C:\\Users\\T-GAMER\\Desktop\\NovaPasta");
                    stop = true;
                }
                
            } catch (Exception e) {
                //senha incorreta
                System.err.println(password);
            }
            //senha encontrada, finaliza o laço de repetição
            if(stop){
                break;
            }
        }
        //senha encontrada, termina o código
        if(stop){
            return;
        }
    }

    //metodo que pega apenas os caracteres da tabela ASCII precisos para gerar a senha
    //letra maiuscula e minuscula, e também numeros
    public static void takeAsciiValues() {

        for (int i = 48; i < 58; i++) {
            numbersASCII.add(i);
        }
        for (int i = 65; i < 91; i++) {
            numbersASCII.add(i);
        }
        for (int i = 97; i < 123; i++) {
            numbersASCII.add(i);
        }

    }
    
    public Vector<Integer> getNumerosASCII() {
        return numbersASCII;
    }

    public static int[] getResultado() {
        return result;
    }
}
