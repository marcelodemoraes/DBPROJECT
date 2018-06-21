/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhobd;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 *
 * @author Marina Kako
 * @author Marcelo de Moraes
 * @author Ana Boba
 */
public class TrabalhoBD {
	/**
     * @param args the command line arguments
	 * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
    	
    	String user = "M9763151";
    	String pass = "123456";
    	String url = "jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl";
    	Statement stmt; 
        
    	//objeto que reliza conecao
    	Connect conexao = new Connect(user, pass, url);
        stmt = conexao.makeConnection();
        
        //verifica se conexao foi estabelecida
        if(stmt != null) {
        	System.out.println("Usuario " + user + " conectado em " + url + "\n");
        }
        else {
        	System.out.println("Conexao nao pode ser estabelecida.");
        	return;
        }
         
        //MENU
        Scanner ler = new Scanner(System.in);  
        System.out.println("Bem vindo ao seu gerenciador de eventos");
        while(true) {
             System.out.println("Escolha um dos eventos: \n"
             		+ "1-Festa\n"
             		+ "2-Confraternizacao");
             
             int evento = ler.nextInt();
         
             if(evento == 1){
            	 int a = 1;
            	 //loop na secao Festa
            	 while(a == 1) {
            		 System.out.println("Menu Festas\n");
            		 System.out.println("Escolha uma das opcoes abaixo:\n"
                      		+ "1-Listar tabelas\n"
                      		+ "2-Consultar\n"
                                + "3-Voltar\n"
                      		+ "4-Sair");
                      
                      int op = ler.nextInt();
                      
                      if(op == 1){
                          //listar tabelas
                      }else if(op == 2){
                          //consultar
                      }else if(op == 3){
                    	  a = 0;
                      }else{
                          //sair
                          conexao.close();
                          ler.close();
                          System.out.println("Sessao finalizada");
                          return;
                      }
            	 }
                 
             }
             else if(evento == 2){
            	 int a = 2;
            	 //Loop na secao Confraternizacao
            	 while(a == 2) {
            		 System.out.println("Menu Confraternizacao\n");
            		 System.out.println("Escolha uma das opcoes abaixo:\n"
		                 		+ "1-Listar tabelas\n"
		                 		+ "2-Consultar\n"
		                 		+ "3-Inserir tupla\n"
		                 		+ "4-Alterar tupla\n"
		                 		+ "5-Remover tupla\n"
		                 		+ "6-Voltar\n"
		                 		+ "7-Sair");
			          
			          int op = ler.nextInt();
			          
			          switch(op){
			              case 1: //listar todas as tabelas
			            	  //TODO Listar apenas tabelas pertinentes ao escopo de confraternizacao
			            	  ResultSet rs = stmt.executeQuery("SELECT table_name, owner FROM all_tables WHERE owner= '" + user + "' ORDER BY owner, table_name");			            	      	  
                                          while (rs.next()) {
			            		    System.out.print(rs.getString(1) + " ");
			            		    System.out.println();
			            		}
			            	  System.out.println();

			            	  rs.close();
			                  break;
                                          
			              case 2:
			                  //consultar
			                  break;
			              case 3:
			                  //inserir tupla
                                          System.out.println("Digite o nome da tabela onde deseja inserir um dado:");
                                          String tabela = ler.next();
                                          tabela = tabela.toUpperCase();
                                          
                                          ResultSet colunas = stmt.executeQuery("SELECT table_name, column_name, data_type, data_length FROM USER_TAB_COLUMNS WHERE table_name = '" + tabela + "'");
                                          String comando = new String(); 
                                          
                                          while(colunas.next()){
                                              System.out.println("Digite o seguinte dado no formato pedido:");
                                              System.out.println(colunas.getString(2) + " " + colunas.getString(3) + " de tamanho " + colunas.getString(4));
                                              
                                              
                                              if(colunas.getString(3).equals("DATE")){
                                                  System.out.println("Informe a data no formato DD/MM/YYYY");
                                                  String atributo = ler.next();
                                                  comando += "'" + atributo + "', ";
                                              } else if(colunas.getString(3).equals("FLOAT")){
                                                  System.out.println("Numeros decimais devem ser inseridos com ponto");
                                                  String atributo = ler.next(); 
                                                  comando += atributo + ", ";
                                              }else{
                                                  String atributo = ler.next();
                                                  comando += "'" + atributo + "', "; 
                                              } 
                                          }
                                          
                                          int tamanho = comando.length();
                                          comando = comando.substring(0, tamanho-2);
                                          //System.out.println(comando);
                                          
                                        try{
                                            stmt.executeUpdate("insert into " + tabela + " values (" + comando + ")");
                                            System.out.println("\nTupla inserida na tabela.\n");
                                        }catch(SQLException sqle){
                                            System.out.println("\nErro ao inserir tupla\n");
                                        }
			                  break;
			              case 4:
			                  //alterar tupla
			                  break;
			              case 5:
			                  //remover tupla
			                  break;
			              case 6:
			            	  a = 0;
                                          break;
			              case 7:
			                  //sair
			                  conexao.close();
			                  ler.close();
			                  System.out.println("Sessao finalizada.");
			                  return;
			          }
            	 }
                 
             }
        }
    }
}