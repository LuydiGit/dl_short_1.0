package inter.stmt;

import lexer.Tag;
import inter.expr.Id;

public class Write  extends Stmt{
    private Id id;

    public Write (Id i ){
        id = i;
        addChild(id);
    }

    @Override
    public String toString(){
        return Tag.WRITE.toString();
    }
    
}
