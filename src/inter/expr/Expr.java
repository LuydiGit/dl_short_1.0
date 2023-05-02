package inter.expr;

import lexer.Tag;
import lexer.Token;
import inter.Node;


public abstract class Expr extends Node{
    protected Token op;
    protected Tag type;

    public Expr(Token op, Tag type) {
        this.op = op;
        this.type = type;

    }

    public Token op() {return op;}

    public Tag type() {return type;}

    public String toString(){
        return op.tag().toString();
    }
}
