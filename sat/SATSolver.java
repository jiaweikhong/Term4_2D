package sat;

import com.sun.org.apache.xpath.internal.operations.Neg;

import immutable.ImList;
import sat.env.Bool;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        // TODO: implement this.
        System.out.println("Solve(1) has begun");
        return solve(formula.getClauses(), new Environment());
        //throw new RuntimeException("not yet implemented.");
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        // TODO: implement this.

        System.out.println("Solve(2) has begun");
        if (clauses.isEmpty()) {
            System.out.println("List of clauses is empty");
            return env;
        }
        else {
            System.out.println("Now we iterate through the clauses in the list of clauses");
            java.util.Iterator<Clause> iter = clauses.iterator();
            while (iter.hasNext()) {
                Clause innerClause = iter.next();
                System.out.println(innerClause);

                if (innerClause.size() == 1) {
                    System.out.println("I found a clause whose size is 1.");
                    Literal innerLiteral = innerClause.chooseLiteral();
                    ImList<Clause> newClauses = substitute(clauses, innerLiteral);
                    if (innerLiteral instanceof PosLiteral) {
                        env.putTrue(innerLiteral.getVariable());
                    }
                    else {
                        env.putFalse(innerLiteral.getVariable());
                    }
                    System.out.println(env);
                    return solve(newClauses, env);
                }


            System.out.println("Couldn't find a clause whose size is 1. Continuing to find min size.");
            boolean continueloop = true;
            for (int i = 2; continueloop; i++) {


                java.util.Iterator<Clause> iter2 = clauses.iterator();
                while (iter2.hasNext()) {
                    if (innerClause.size() == i){
                        System.out.println("I found a clause with size " + i);
                        Literal newLiteral = innerClause.chooseLiteral();   // defines newLiteral as a random literal in the innerclause.
                        System.out.println("I am going to call substitute now");
                        ImList<Clause> newClauses = substitute(clauses, newLiteral);
                        System.out.println(newClauses);
                        for (Clause aClause: newClauses) {
                            if (aClause.isEmpty()){
                                newClauses = substitute(clauses, newLiteral.getNegation());
                                for (Clause bClause: newClauses){
                                    if (bClause.isEmpty()){
                                        env = null;
                                        return env;
                                    }
                                    else {
                                        if (newLiteral instanceof PosLiteral) {
                                            env.putFalse(newLiteral.getVariable());
                                        }
                                        else {
                                            env.putTrue(newLiteral.getVariable());
                                        }
                                        continueloop = false;
                                        return solve(newClauses, env);
                                    }
                                }
                            }
                            else {
                                if (newLiteral instanceof PosLiteral) {
                                    env.putTrue(newLiteral.getVariable());
                                }
                                else {
                                    env.putFalse(newLiteral.getVariable());
                                }
                                continueloop = false;
                                System.out.println(env);
                                return solve(newClauses, env);
                            }
                        }
                    }
                }
            }
            }
            return env; // this shld not happen.
        }
        //throw new RuntimeException("not yet implemented.");
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        // TODO: implement this.

        if (clauses.isEmpty()){
            return null;
        }

        else{
            while (clauses.iterator().hasNext()){
                Clause currentClause = clauses.iterator().next();

                while (currentClause.iterator().hasNext()){
                    Literal currentLiteral = currentClause.iterator().next();

                    if (currentLiteral.negates(l)){    //checks if the literal being checked is the negation of l.
                        currentClause.reduce(l.getNegation());  //not 100% sure   //supposed to remove the literal itself.
                    }
                    else if (currentLiteral.equals(l)){    //checks if the literal being checked is equal to l.
                        clauses.remove(currentClause);  //removes the whole "clauses".
                    }
                }
            }
        }

        return clauses;

        //throw new RuntimeException("not yet implemented.");
    }

}
