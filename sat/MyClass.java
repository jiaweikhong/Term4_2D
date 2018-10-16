package sat;

import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;

import static sat.SATSolver.solve;

public class MyClass {

    public static void main(String[] args) {
        Literal a = PosLiteral.make("a");
        Literal b = PosLiteral.make("b");
        Literal c = PosLiteral.make("c");
        Literal d = PosLiteral.make("d");
        Literal na = a.getNegation();
        Literal nb = b.getNegation();
        Literal nc = c.getNegation();
        Literal nd = d.getNegation();

        Clause c1 = new Clause();
        c1 = (c1.add(a));
        c1 = c1.add(c);
        c1 = c1.add(nd);

        Clause c2 = new Clause();
        c2 = c2.add(d);

        Clause c3 = new Clause();
        c3 = c3.add(b);
        c3 = c3.add(nc);


        Formula f = new Formula();
        f = f.addClause(c1);
        f = f.addClause(c2);
        f = f.addClause(c3);

        System.out.println("SAT solver starts!");
        long started = System.nanoTime();

        System.out.println(SATSolver.solve(f));

        long time = System.nanoTime();
        long timeTaken = time - started;
        System.out.println("time" + timeTaken/1000000.0);

        //System.out.println(a);
        //System.out.println(c1);
        //System.out.println(f);



    }
}
