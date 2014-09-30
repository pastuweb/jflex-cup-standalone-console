import java_cup.runtime.*;

%%

%class scanner
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

stringa = [A-Za-z]+
classe = "anno di corso "[1-5]
sezione = "sezione "[a-z]
operazione = "ingresso in ritardo" | "uscita anticipata"
indirizzo = "Indirizzo di studi "{stringa}
ora = (((0|1)[0-9])|(2[0-3]))":"([0-5][0-9])
data = ((0[1-9])|([1-2][0-9])|(3(0|1)))"/"((0[1-9])|(1(0|1|2)))"/"(2[0-9][0-9][0-9])

%%

"User"              {return symbol(sym.USER);}
"u"                 {return symbol(sym.U);}
"Login"             {return symbol(sym.LOGIN);}
"Logout"            {return symbol(sym.LOGOUT);}
"0"                 {return symbol(sym.ZERO);}
"1"                 {return symbol(sym.UNO);}
"2"                 {return symbol(sym.DUE);}
"#"                 {return symbol(sym.SEP);}

{data}              {return symbol(sym.DATA, new String(yytext()));}
{ora}               {return symbol(sym.ORA, new String(yytext()));}
{operazione}        {return symbol(sym.OPERAZIONE, new String(yytext()));}
{classe}            {return symbol(sym.CLASSE, new String(yytext()));}
{sezione}           {return symbol(sym.SEZIONE, new String(yytext()));}
{indirizzo}         {return symbol(sym.INDIRIZZO, new String(yytext()));}
{stringa}           {return symbol(sym.STRINGA, new String(yytext()));}

[ \n\r\t]       {;}
.               {System.out.println("SCANNER ERROR: "+yytext());}

