package sat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;


public class SATSolverTest_Derrick {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();



	
	// TODO: add the main method that reads the .cnf file and calls SATSolver.solve to determine the satisfiability
    public static void main(String[] args)
    {
        BufferedReader reader = null;
        int noOfVar = 0;
        ArrayList<Literal> tempLits = new ArrayList<>();
        ArrayList<Clause> clauseArray = new ArrayList<>();
        Formula formula = new Formula();
        
        try
        {
            File file = new File("Input .cnf filepath");
            System.out.println("File path as parameter provided");
            reader = new BufferedReader(new FileReader(file));
            System.out.println("File open successful!");

            String line;
            System.out.println("Printing file contents...");
            while ((line = reader.readLine()) != null) {
                //System.out.println(line)
                    String[] lits = line.split("\\s+");
                    for (String s : lits) {
                        if (s != "0") {
                            if ( s.charAt(0) == '-' ) {
                                String newS = s.substring(1);
                                tempLits.add(NegLiteral.make(newS));
                            }
                            else {
                                tempLits.add(PosLiteral.make(s));
                            }
                        }
                        else {
                            Literal[] literal = tempLits.toArray(new Literal[tempLits.size()]);
                            clauseArray.add(makeCl(literal));
                            tempLits.clear();
                        }
                    }
            }
            makeFm(clauseArray.toArray(new Clause[clauseArray.size()]));
        }
        catch (IOException exc){
            exc.printStackTrace();
            System.out.println("File I/O error!");
        }
    }

    
	
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())
    			|| Bool.TRUE == e.get(b.getVariable())	);

*/
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/    	
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    
    
}