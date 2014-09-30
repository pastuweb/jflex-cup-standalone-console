package net.appuntivari.jflexcup.standalone;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;


public class Main {
    static public void main(String argv[]) throws InterruptedException, IOException {    
        
    	 String intestazione = 	"################################# Progetto JFlex/CUP/Java/JDBC #############################################################\n\n"+
    			"Comandi di Autenticazione:\n\n"+
				">>>Login <utente> <passwd> or u <utente> <password> or User <utente> <password>\n"+
				">>>Logout\n\n"+
				"Comandi per interagire con il database:\n"+
				">>>0#[<data>]#<cognomealunno>#[<nomealunno>]#<classe>#<sezione>#<indirizzo>#<operazione>#[<ora>] ----> INSERT su DB\n"+
				">>>1#[<data>]#[<cognomealunno>]#[<nomealunno>]#[<classe>]#[<sezione>]#[<indirizzo>]#[<operazione>]#[<ora>] ----> SELECT su DB\n"+
				">>>2#<data>#<cognomealunno>#<nomealunno> ----> DELETE su DB\n\n"+
				"Importante: descrizione dei parametri dei comandi e valori possibili:\n"+
				"[<campo>]: opzionalità\n"+
				"<campo>: obbligatorio\n"+
				"classe = \"anno di corso <numero tra 1 e 5>\" \n"+
				"sezione = \"sezione <carattere tra a e z>\" \n"+
				"indirizzo = \"Indirizzo di studi <stringa>\" \n"+
				"operazione = \"ingresso in ritardo\" | \"uscita anticipata\" \n"+
				"orario nel formato hh:mm \n"+
				"data nel formato gg/mm/aaaa \n\n"+
				"##########################################################################################################";
    	 
    	 Scanner input = new Scanner(System.in);
    	 String strComando=null;
    	 scanner l = null;
    	 parser p = null;
    	 int login = 0;
    	 int logout = 0;
    	 
    	 System.out.println(intestazione);
    	 
    	 //connessione al Database
    	 Database db = new Database("school_management","","root","localhost");
    	 if(db.connect()){
    		 	System.out.println("Connessione al Database ESEGUITA.");
    		 	System.out.println("TEST Query:");
    		 	db.executeQuery("SELECT id, nome, cognome FROM alunni", "alunni", "viewIdNomeCognome");
    		 	
    	 }
    	 
    	 //PRIMA FASE: Autenticazione
    	 do{
    		 System.out.println("Digita comando di login:");
    		 System.out.print(">>> ");
    		 strComando = input.nextLine();
    		 	
    		 	try {
    				
		        	l = new scanner(new java.io.StringReader(strComando));
		        	        	 
		            p = new parser(l);
		            Object result = p.parse();//avvio il parser
		
		    	 } catch (Exception e) {
		            e.printStackTrace();
		    	 }
    		 	
    		 	if(parser.flagLogin == 1){
    		 		login = 1;
    		 	}
    		 
    	 }while(login==0);
    	 
    	 //SECONDA FASE: codice SQL o Logout
    	 do {
        	 System.out.println("Digita un comando 0# | 1# | 2# | Logout:");
        	 System.out.print(">>> ");
        	 strComando = input.nextLine();
        	 
        	 try {
 				
		        	l = new scanner(new java.io.StringReader(strComando));
		        	        	 
		            p = new parser(l);
		            Object result = p.parse();//avvio il parser
		
		    	 } catch (Exception e) {
		            e.printStackTrace();
		    	 }
 		 	
 		 	if(parser.flagLogout == 1){
 		 		logout = 1;
 		 	}else{
 		 		//comandi #0, #1, #2
 		 		//FASE TRE: interazione con il DB
 		 		System.out.println("SQL generato:"+p.codCom);
 		 		
 		 		if(p.isCorrect == 1 && p.comandoDigitato.tipoComando.equals("SELECT") ){
 		 			System.out.println("SELECT");
 		 			System.out.println(""+p.comandoDigitato.data+"\n"+p.comandoDigitato.cognome+"\n"+p.comandoDigitato.nome+"\n"+p.comandoDigitato.classe+"\n"+p.comandoDigitato.sezione+"\n"+p.comandoDigitato.indirizzo+"\n"+p.comandoDigitato.operazione+"\n"+p.comandoDigitato.ora);

 		 			//es: vedi tutte le operazioni di un utente
 		 			if(!p.comandoDigitato.data.equalsIgnoreCase("none") && !p.comandoDigitato.ora.equalsIgnoreCase("none") && !p.comandoDigitato.nome.equalsIgnoreCase("none") && !p.comandoDigitato.cognome.equalsIgnoreCase("none")){
 		 				db.viewAllOperazioni(p.comandoDigitato.data.toString(), null, p.comandoDigitato.ora.toString(), p.comandoDigitato.cognome.toString(), p.comandoDigitato.nome.toString());
 		 			}
 		 			
 		 			
 		 		}else if(p.isCorrect == 1 && p.comandoDigitato.tipoComando.equals("INSERT") ){
 		 			System.out.println("INSERT");
 		 			System.out.println(""+p.comandoDigitato.data+"\n"+p.comandoDigitato.cognome+"\n"+p.comandoDigitato.nome+"\n"+p.comandoDigitato.classe+"\n"+p.comandoDigitato.sezione+"\n"+p.comandoDigitato.indirizzo+"\n"+p.comandoDigitato.operazione+"\n"+p.comandoDigitato.ora);
 		 				
 		 			/* TO DO */
 		 			
 		 		}else if(p.isCorrect == 1 && p.comandoDigitato.tipoComando.equals("DELETE") ){
 		 			
 		 			System.out.println("DELETE");
 		 			System.out.println(""+p.comandoDigitato.data+"\n"+p.comandoDigitato.cognome+"\n"+p.comandoDigitato.nome+"\n"+p.comandoDigitato.classe+"\n"+p.comandoDigitato.sezione+"\n"+p.comandoDigitato.indirizzo+"\n"+p.comandoDigitato.operazione+"\n"+p.comandoDigitato.ora);
 		 			/*
 		 			String operation = new String();
 		 			operation = "DELETE FROM alunni WHERE nome = '"+p.comandoDigitato.nome.toString()+"' and cognome = '"+p.comandoDigitato.cognome.toString()+"'";
 		 			System.out.println(operation);
 		 			db.executeUpdate(operation);
 		 			*/
 		 		}
 		 		 
 		 		System.out.println("Esecuzione SQL ...");
 		 		Thread.sleep(5000);
 		 		System.out.println("\nSQL eseguito!\n");
 		 	}

	         
    	 }while(logout == 0);
        
    	 System.out.println("FINE PROGRAMMA.");
    	 System.exit(0);
    	 
    }
}