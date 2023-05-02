package parser;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

import inter.stmt.Program;
import inter.stmt.Stmt;
import inter.stmt.Block;
import inter.stmt.Decl;

public class Parser {
    private Lexer lexer;
    private Token look;
    private Node root;

    public Parser (Lexer lex) {
        lexer = lex;
        move ();
    }

    private Token move () {
        Token save = look;
        look = lexer.nextToken();
        return save;
    }

    private void error (String s) {
        System.err.println("linha"
                + Lexer.line ()
                + ":" + s);
                System.exit(0);
    }
   
    private Token match (Tag t) {
        if (look.tag () == t)
            return move ();
            error ("Símbolo inesperado");
            return null;
    }
    
    public void parse() {
        root = program();
    }
    
    private void program () {
        match (Tag.PROGRAM);
        match (Tag.ID);
        block ();
        match (Tag.DOT);
        match (Tag.EOF);
    }
    
    private void block (){
        match(Tag.BEGIN);
        while (look.tag() != Tag.END) {
                stmt ();
                match (Tag.SEMI);
        }
        match(Tag.END);
    }
    
    private void stmt (){
        switch (look.tag()) {
            case BEGIN: block (); break;
            case INT: case REAL:
                case BOOL: decl(); break;
            case ID: assign(); break;
            case IF: ifStmt(); break;
            case WRITE: writeStmt(); break;
            default: error ("Comando inválido");
        }
    }
    
    private void decl() {
        move();
        match(Tag.ID);
    }

    private void assign(){
        match(Tag.ID);
        match (Tag.ASSIGN);
        expr();
    }

    private void expr(){
        rel();
        while (look.tag() == Tag.OR){
            move();
            rel();
        }
    }

    private void rel (){
        arith();
        while (look.tag() == Tag.LT ||
                look.tag() == Tag.LE ||
                look.tag() == Tag.GT) {
                    move ();
                    arith();
                }
    }

    private void arith () {
        term();
        while ( look.tag() == Tag.SUM ||
                look.tag() == Tag.SUB) {
                    move();
                    term();
                }
    }

    private void term (){
        factor();
        while( look.tag() == Tag.MUL){
            move();
            factor();
        }
    }

    private void factor (){
        switch( look.tag() ) {
        case LPAREN: move(); expr();
            match(Tag.RPAREN); break;
        case LIT_INT: move(); break;
        case LIT_REAL: move(); break;
        case TRUE: case FALSE:
            move (); break;
        case ID: match(Tag.ID); break;
        default:
            error("expressão inválida");
        }
    }

    private void ifStmt (){
        match(Tag.IF);
        match(Tag.LPAREN);
        expr();
        match(Tag.RPAREN);
        stmt();
    }

    private void writeStmt(){
        move();
        match(Tag.LPAREN);
        match(Tag.ID);
        match(Tag.RPAREN);
    }
    

    private Program program (){
        match(Tag.PROGRAM);
        Token tokId = match(Tag.ID);
        Stmt b = block();
        match(Tag.DOT);
        match(Tag.EOF);
        return new Program(tokId, (Block)b);
    }

    private Stmt block(){
        Blcok b = new Block();
        match(Tag.BEGIN);
        while (look.tag() != Tag.END) {
            b.addStmt(stmt());
            match(Tag.SEMI);
        }

        match(Tag.END);
        return b;
    }

    private Stmt stmt (){
        switch (look.tag()){
            case BEGIN: return block();
            case INT: case REAL:
                case BOLL: return decl();
            case WRITE: return writeStmt();
            case ID: return assign();
            case IF: return ifStmt();
            default: error ("Comando inválido");
        }
        return null;
    }

    private Stmt decl() {
        Token type = move();
        Token tokId = match(Tag.ID);
        Id id =  new Id(tokId, type.tag());
        return new Decl(id);
    }

    private Stmt assign() {
        Token tok = match(Tag.ID);
        Id id = new Id(Tok, null);
        match(Tag.ASSIGN);
        Expr e = expr();
        return new Assign(id, e);
    }

}



