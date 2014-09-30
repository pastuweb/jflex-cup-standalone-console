package net.appuntivari.jflexcup.standalone;


import java.sql.*;
import java.util.Vector;
/**
 * Classe che rappresenta una connessione al database. 
 * Contiene i metodi per eseguire query di consultazione o di aggiornamento.
 * Contiene inoltre i metodi per connettersi/disconnettersi al database.
 * 
 */
public class Database {

	private String DBname;
	private String passwd;
	private String user;
	private String host;
	private boolean connected;
	private Connection conn;
	
	/**
	 * Costruttore di classe
	 * 
	 * @param DBname	il nome del database
	 * @param passwd	la password di accesso
	 * @param user		il nome utente
	 * @param host		l'indirizzo del server
	 */
	public Database(String DBname, String passwd, String user,String host)
	{
		this.DBname = DBname;
		this.passwd = passwd;
		this.user = user;
		this.host = host;
		connected = false;
	
}

/**
 * Permette di connettersi al database selezionato
 * 
 * @return	true se la connessione ha successo
 */
public boolean connect() {
   connected = false;
   try {

      Class.forName("com.mysql.jdbc.Driver");

      if (!DBname.equals("")) {
         if (user.equals("")) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DBname);
         } else {

            if (passwd.equals("")) {
               conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DBname + "?user=" + user);
            } else {
               conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DBname + "?user=" + user + "&password=" + passwd);
            }
         }
         connected = true;
      } else {
         System.out.println("Manca il nome del database!!");
         System.out.println("Scrivere il nome del database da utilizzare all'interno del file \"config.xml\"");
         System.exit(0);
      }
   } catch (Exception e) {e.getMessage(); }
   return connected;
}

/**
 * Esegue Query per la consultazione dei dati presenti nel database
 * 
 */
public boolean executeQuery(String query, String tabella, String metodo) {
    
	boolean result = false;
    try {
       Statement stmt = conn.createStatement();    
       ResultSet rs = stmt.executeQuery(query);   
       
       if(tabella.equalsIgnoreCase("alunni")){
    	   
    	   if(metodo.equalsIgnoreCase("viewIdNomeCognome")){
    	   
	    	   while (rs.next()) {
	    		   String id = rs.getString("id");
	    		   String nome = rs.getString("nome");
	    		   String cognome = rs.getString("cognome");
	    		   System.out.printf("alunno: %s %s %s \n", id, nome, cognome);
	    	   }
	    	 
    	   }else if(metodo.equalsIgnoreCase("viewId")){
    		   
    		   while (rs.next()) {
	    		   String id = rs.getString("id");
	    		   System.out.printf("id_alunno: %s \n", id);
	    	   }
    		   
    	   }

       }else if(tabella.equalsIgnoreCase("scuola")){
    	   
    	   if(metodo.equalsIgnoreCase("viewIdClasseSezione")){
        	   
	    	   while (rs.next()) {
	    		   String id = rs.getString("id");
	    		   String classe = rs.getString("classe");
	    		   String sezione = rs.getString("sezione");
	    		   System.out.printf("info scuola: %s %s %s \n", id, classe, sezione);
	    	   }
	    	 
    	   }else if(metodo.equalsIgnoreCase("viewId")){
    		   
    		   while (rs.next()) {
	    		   String id = rs.getString("id");
	    		   System.out.printf("id_scuola: %s \n", id);
	    	   }
    		   
    	   }
    	   
       }else if(tabella.equalsIgnoreCase("operazioni")){
    	   
    	   if(metodo.equalsIgnoreCase("viewIdOperazioneDataOra")){
        	   
	    	   while (rs.next()) {
	    		   String id = rs.getString("id");
	    		   String operazione = rs.getString("operazione");
	    		   String data = rs.getString("data");
	    		   String ora = rs.getString("ora");
	    		   System.out.printf("operazione: %s %s %s %s \n", id, operazione, data, ora);
	    	   }
	    	 
    	   }else if(metodo.equalsIgnoreCase("viewId")){
    		   
    		   while (rs.next()) {
	    		   String id = rs.getString("id");
	    		   System.out.printf("id_operazione %s \n", id);
	    	   }
    		   
    	   }
    	   
       }
       
       result = true;
       rs.close();    
       stmt.close();   
    } catch (Exception e) { e.printStackTrace(); e.getMessage(); }

    return result;
 }


/**
 * Esegue altre Query ad-hoc
 * 
 */

public int getIdAlunno(String nome, String cognome){
	int id = -1;
	
	 Statement stmt;
	 ResultSet rs;
	 String query = null;
	try {
		stmt = conn.createStatement();

		 if(nome != null && cognome != null){
			 query = "SELECT id FROM alunni WHERE nome = \""+nome+"\" and cognome = \""+cognome+"\"";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		  id = rs.getInt("id");
			 }
		 }else{
			 id = -1;
		 }
	     
	     
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    

	return id;
}

public int getIdScuola(String classe, String sezione){
	int id = -1;
	
	Statement stmt;
	ResultSet rs;
	String query = null;
	try {
		stmt = conn.createStatement();

		 if(classe != null && sezione != null){
			 query = "SELECT id FROM scuola WHERE classe = \""+classe+"\" and sezione = \""+sezione+"\"";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		  id = rs.getInt("id");
			 }
		 }else{
			 id = -1;
		 }
	     
	     
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    

	return id;
}

public void viewAllOperazioni(String data, String operazione, String ora, String cognome, String nome){
	
	Statement stmt;
	ResultSet rs;
	String query = null;
	try {
		stmt = conn.createStatement();
		 
		 if(data != null && operazione != null && ora != null && cognome != null && nome != null ){
			 query = "SELECT operazioni.id, nome, cognome, data, ora, operazione FROM operazioni, alunni WHERE operazioni.id_alunno=alunni.id and data = \""+data+"\" and ora = \""+ora+"\" and nome = \""+nome+"\" and cognome = \""+cognome+"\" and operazione = \""+operazione+"\"";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		 
				 System.out.printf("operazione: %s %s %s %s %s %s \n", rs.getString("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data"), rs.getString("ora"), rs.getString("operazione"));

			 }
		 }else if(data != null && operazione != null && ora != null && cognome != null && nome == null ){
			 query = "SELECT operazioni.id, nome, cognome, data, ora, operazione FROM operazioni, alunni WHERE operazioni.id_alunno=alunni.id and data = \""+data+"\" and ora = \""+ora+"\" and cognome = \""+cognome+"\" and operazione = \""+operazione+"\"";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		 
				 System.out.printf("operazione: %s %s %s %s %s %s \n", rs.getString("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data"), rs.getString("ora"), rs.getString("operazione"));

			 }
		 }else if(data != null && operazione != null && ora != null ){
			 query = "SELECT operazioni.id, nome, cognome, data, ora, operazione FROM operazioni, alunni WHERE operazioni.id_alunno=alunni.id and data = \""+data+"\" and ora = \""+ora+"\" and operazione = \""+operazione+"\"";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		 
				 System.out.printf("operazione: %s %s %s %s %s %s \n", rs.getString("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data"), rs.getString("ora"), rs.getString("operazione"));

			 }
		 }else if(data != null && ora != null ){
			 query = "SELECT operazioni.id, nome, cognome, data, ora, operazione FROM operazioni, alunni WHERE operazioni.id_alunno=alunni.id and data = \""+data+"\" and ora = \""+ora+"\" ";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		 
				 System.out.printf("operazione: %s %s %s %s %s %s \n", rs.getString("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data"), rs.getString("ora"), rs.getString("operazione"));

			 }
		 }else if(data != null && ora != null && cognome != null && nome != null ){
			 query = "SELECT operazioni.id, nome, cognome, data, ora, operazione FROM operazioni, alunni WHERE operazioni.id_alunno=alunni.id and data = \""+data+"\" and ora = \""+ora+"\" and nome = \""+nome+"\" and cognome = \""+cognome+"\" ";
			 rs = stmt.executeQuery(query);
			 while (rs.next()) {
	    		 
				 System.out.printf("operazione: %s %s %s %s %s %s \n", rs.getString("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data"), rs.getString("ora"), rs.getString("operazione"));

			 }
		 }
	     
	     
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}    

}





/**
 * Esegue una query di aggiornamento del database
 */
public boolean executeUpdate(String query) {
   int num = 0;
   boolean result = false;
   try {
      Statement stmt = conn.createStatement();
      num = stmt.executeUpdate(query);
      result = true;
      stmt.close();
   } catch (Exception e) {
      e.printStackTrace();
      e.getMessage();
      result = false;
   }
   return result;
}

/**
 * Esegue la disconnessione dal database
 */
public void disconnect() {
   try {
      conn.close();
      connected = false;
   } catch (Exception e) { e.printStackTrace(); }
}

/**
 * Ottiene l'oggetto Connection associato a questo database
 */
public Connection getConnection()
{	
	return conn;
}

/**
 * Indica se la connessione se è attiva
 */
public boolean isConnected()
{
return this.connected;	
}

}
