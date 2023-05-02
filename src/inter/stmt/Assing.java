package inter.stmt;

import inter.expr.Id;
import inter.expr.Expr;
import lexer.Tag;

public class Assing extends Stmt{
    protected Id id;
    protected Expr expr;

    public Assing (Id i, Expr e){
        id = i;
        expr = e;
        addChild(id);
        addChild(expr);
    }

    @Override
    public String toString(){
        return Tag.ASSIGN.toString();
    }
    
}
