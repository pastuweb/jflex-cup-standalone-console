package net.appuntivari.jflexcup.standalone;
class Comando {
			public String tipoComando;
			public String data;
			public String cognome;
			public String nome;
			public String classe;
			public String sezione;
			public String indirizzo;
			public String operazione;
			public String ora;
			
			Comando(String tipoComando, String data,  String cognome, String nome, String classe, String sezione, String indirizzo, String operazione, String ora){
					this.tipoComando = tipoComando.toString();
					this.data = data.toString();
					this.cognome = cognome.toString();
					this.nome = nome.toString();
					this.classe = classe.toString();
					this.sezione = sezione.toString();
					this.indirizzo = indirizzo.toString();
					this.operazione = operazione.toString();
					this.ora = ora.toString();
			}
			
			Comando(String tipoComando, String data,  String cognome, String nome){
					this.tipoComando = tipoComando.toString();
					this.data = data.toString();
					this.cognome = cognome.toString();
					this.nome = nome.toString();
			}
}