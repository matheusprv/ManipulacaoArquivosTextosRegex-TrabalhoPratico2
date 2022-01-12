/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopratico2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Matheus
 */
public class TrabalhoPratico2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Caminho de teste
            C:\\Users\\Matheus\\Desktop\\Escola\\Terceiro Ano\\Trabalhos\\Topicos\\3 - Trabalho Pratico 2\\Arquivos
        */
        Scanner teclado = new Scanner(System.in);
        Scanner tecladoNumerico = new Scanner(System.in);
        
        File diretorio = null;
        File arquivos [] =null;
        
        //Pede ao usuário um diretório e fica no loop até encontrar um verdadeiro.
        while(true){
            System.out.print("Digite o caminho do diretório: ");
            String caminho = teclado.nextLine();

            diretorio = new File(caminho);
             
            if(diretorio.exists()){
                 arquivos = diretorio.listFiles();
                break;
            }
            else{
                System.out.println("Diretório não encontrado ou inexistente");
            }
        }
        
        int opcao = 0;
        while(opcao != 3){
            do{
                System.out.print("1 - Palavra que mais se repete com um número de caracteres\n2 - Encontrar palavras específica\n3 - Sair\nEscolha: ");
                opcao = tecladoNumerico.nextInt();
            }while(opcao>3 && opcao<1);
            
            if(opcao==1){
                palavraMaisComumCaracterLimitado(arquivos);
            }
            else if(opcao==2){
                buscarPalavra(arquivos);
            }
            else if(opcao==3){
                break;
            }
        }
        teclado.close();
        tecladoNumerico.close();
        System.out.println("Finalizando Sistema");
    }
    
    public static void palavraMaisComumCaracterLimitado(File arquivos[]){
        //Porucra a palavra mais comum com um número de caracteres limitasdos pelo usuário
        Scanner tecladoMetodo = new Scanner(System.in);
        System.out.print("Digite o número de caracteres da palavra: ");
        int numCaracteres = tecladoMetodo.nextInt();

        
        ArrayList<String>palavrasEncontradas = new ArrayList<String>();
        
        for(int i=0;i<arquivos.length;i++){
            try{
                FileReader arquivoLeitura = new FileReader(arquivos[i].getAbsolutePath());
                BufferedReader bufferLeitura = new BufferedReader(arquivoLeitura);
                String linha;
                do{
                    //Ler a linha do arquivo
                    linha=bufferLeitura.readLine();
                    //Dividir a palavra pelo espaço
                    if(linha != null){
                        String palavras[] = linha.split(" ");
                        //Verificar o vetor à procura de palavras com o mesmo tamanho que foi solicitado
                        for(int cont=0; cont<palavras.length; cont++){
                            if(palavras[cont].matches("[A-Za-z]{"+numCaracteres+"}")){
                            //if(palavras[cont].matches("[a-zA-Z]{"+numCaracteres+"}")){
                                palavrasEncontradas.add(palavras[cont]);
                            }
                        }
                    }
                }while(linha!=null);
                
                bufferLeitura.close();
                arquivoLeitura.close();
                
            }catch(Exception e){
                System.out.println("Erro com o arquivo");
            }
        }
        
        //Organiza o Array em ordem alfabetica
        Collections.sort(palavrasEncontradas);
        
        //Os ArrayList pegam a palavra uma vez e armazena-a, assim como as vezes que se repete. Tudo no mesmo índice
        ArrayList<String>palavrasRepetidas=new ArrayList<String>();
        ArrayList<Integer>vezesRepetidas=new ArrayList<Integer>();
        
        int repeticoes=0;
        String palavraVerificar = palavrasEncontradas.get(0);

        //O sistema compara a variavel da posicao i com a próxima variavel
        for(int i=0; i<palavrasEncontradas.size()-1; i++){
            if(palavraVerificar.equals(palavrasEncontradas.get(i+1))){
                repeticoes++;
            }
            else{
                palavrasRepetidas.add(palavraVerificar);
                vezesRepetidas.add(repeticoes+1);
                repeticoes=0;
                palavraVerificar=palavrasEncontradas.get(i+1);
            }
        }

        int posicaoMaiorRepeticao = 0;
        int maiorRepeticao=0;
        
        
        if(vezesRepetidas.size()>1){
            //Verifica qual palavra mais se repetiu
            for(int i=0; i < vezesRepetidas.size(); i++){
                if(vezesRepetidas.get(i)>maiorRepeticao){
                    maiorRepeticao=vezesRepetidas.get(i);
                    posicaoMaiorRepeticao = i;
                }
            }
            System.out.println("Palavra mais repetida: "+palavrasRepetidas.get(posicaoMaiorRepeticao)+" -- Número de vezes que aparece: "+maiorRepeticao+"\n");
        }
        else{
            System.out.println("Não há palavras com "+numCaracteres+" caracteres que se repete.\n");
        }
    }
    
    
    public static void buscarPalavra(File arquivos[]){
        Scanner teclado = new Scanner(System.in);
        
        System.out.print("Digite a palavra que deseja buscar: ");
        String palavraPesquisar = teclado.next();        
        palavraPesquisar = palavraPesquisar.toUpperCase();
                
        for(int i=0;i<arquivos.length;i++){
            ArrayList<Integer>linhas=new ArrayList<Integer>();
            try{
                FileReader arquivoLeitura = new FileReader(arquivos[i].getAbsolutePath());
                BufferedReader bufferLeitura = new BufferedReader(arquivoLeitura);
                String linha;
                int numeroDaLinha=1;
                do{
                    linha=bufferLeitura.readLine();
                    
                    if(linha != null){
                        String palavrasNaLinha[] = linha.split(" ");
                        for(int cont=0; cont<palavrasNaLinha.length; cont++){
                            palavrasNaLinha[cont]=palavrasNaLinha[cont].toUpperCase();
                            //Verifica se a palavra da vez possui algumas das seguintes variações e se é a palavra que se está à procura
                            if(palavrasNaLinha[cont].contains(palavraPesquisar) && palavrasNaLinha[cont].matches("[A-Za-z]{"+palavraPesquisar.length()+"}") || //Ex: Charles
                                    palavrasNaLinha[cont].matches("[\\W][A-Za-z]{"+palavraPesquisar.length()+"}") && palavrasNaLinha[cont].contains(palavraPesquisar) || //Ex: (Charles 
                                    palavrasNaLinha[cont].matches("[\\W][A-Za-z]{"+palavraPesquisar.length()+"}[\\W][\\D][\\W]") && palavrasNaLinha[cont].contains(palavraPesquisar) || //Ex: "Charles's.
                                    palavrasNaLinha[cont].matches("[\\W][A-Za-z]{"+palavraPesquisar.length()+"}[\\W][\\D]") && palavrasNaLinha[cont].contains(palavraPesquisar) || //Ex: "Charles's
                                    palavrasNaLinha[cont].matches("[\\W][A-Za-z]{"+palavraPesquisar.length()+"}[\\W]") && palavrasNaLinha[cont].contains(palavraPesquisar) || //Ex: "Charles,
                                    palavrasNaLinha[cont].matches("[A-Za-z]{"+palavraPesquisar.length()+"}[\\W][\\D][\\W]") && palavrasNaLinha[cont].contains(palavraPesquisar) || //Ex: Charles's.
                                    palavrasNaLinha[cont].matches("[A-Za-z]{"+palavraPesquisar.length()+"}[\\W][\\D]") && palavrasNaLinha[cont].contains(palavraPesquisar) || //Ex: Charles's
                                    palavrasNaLinha[cont].matches("[A-Za-z]{"+palavraPesquisar.length()+"}[\\W]") && palavrasNaLinha[cont].contains(palavraPesquisar)){ //Ex: Charles,
                                linhas.add(numeroDaLinha);
                            }
                        }
                    }
                    numeroDaLinha++;
                }while(linha!=null);
                
                bufferLeitura.close();
                arquivoLeitura.close();
                
                if(linhas.size()>0){
                    System.out.println("A palavra foi encontrada "+(linhas.size())+" vezes no arquivo: "+arquivos[i].getName());
                    for(Integer palavrasNasLinhas: linhas){
                        System.out.println("Linha: "+palavrasNasLinhas);
                    }
                    System.out.println("");
                }

            }catch(Exception e){
                System.out.println("Erro com o arquivo");
            }
            
        }
    }
    
    
}
