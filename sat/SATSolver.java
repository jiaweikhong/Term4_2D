package sat;

import immutable.EmptyImList;
import immutable.ImList;
import java.util.Iterator;

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
//        System.out.println("solve(formula) has begun");
        return solve(formula.getClauses(), new Environment());      //calls solve(clauses, env)

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
//        System.out.println("solve(clauses, env) has begun");


        // Data:
        Clause emptyClause = new Clause();      // used to check for an empty clause
        int i = 1;          // a counter to check for size of clause
        boolean contloop = true;

        // Case 1:
        if (clauses.isEmpty()){         // checking if there are no clauses left.
//            System.out.println("Case 1: There are no clauses left (yay!), returning environment");
            return env;
        }

        // Case 2:
        else if (clauses.contains(emptyClause)){        // checking if there are any clauses which are empty.
//            System.out.println("Case 2: There is an empty clause (aka failure), trying to backtrack now.");
            //insert backtracking code here.
            return null;    // returning null cause this Problem can't be solved.
        }

        // Case 3:
        else {
//            System.out.println("Case 3: this should happen pretty often");

            while (contloop) {

                Iterator<Clause> clauseIter = clauses.iterator();   // creates an iterator for ImList<Clause>

                while (clauseIter.hasNext()){

//                    System.out.println("Now checking if a clause is size " + i);
                    Clause currentClause = clauseIter.next();

                    if (currentClause.size() == i){         // checks if the size of the clause is i.

//                        System.out.println("I found a clause whose size is " + i + " which is " + currentClause);

                        contloop = false;       // prevent this loop from happening again.

                        Literal chosenLit = currentClause.chooseLiteral();      // chooses a random literal from the clause

                        if (chosenLit instanceof PosLiteral) {      // if the chosen lit is positive, put itself into the environment.
                            env = env.putTrue(chosenLit.getVariable());
//                            System.out.println("Environment has been updated1!");
                        }
                        else {                                      // if the chosen lit is negative, put its negation into the environment.
                            env = env.putFalse(chosenLit.getVariable());
//                            System.out.println("Environment has been updated2!");
                        }

//                        System.out.println("Current env: " + env);

//                        System.out.println("Calling substitute now");
                        ImList<Clause> newclauses = substitute(clauses, chosenLit);     // calls substitute
//                        System.out.println("newclauses: " + newclauses);

                        i = 1;

                        Environment output = solve(newclauses, env);

                        if ((output == null) && (env.get(chosenLit.getVariable())==Bool.TRUE)){         //chosenLit instanceof PosLiteral
                            Literal negatedChosenLit = chosenLit.getNegation();
                            if (chosenLit instanceof PosLiteral) {
                                env = env.putFalse(chosenLit.getVariable());
//                                System.out.println("Environment has been swapped1!");
                            }
                            else {
                                env = env.putTrue(chosenLit.getVariable());
//                                System.out.println("Environment has been swapped2!");
                            }

//                            System.out.println("Current env: " + env);

//                            System.out.println("Calling substitute now");
                            ImList<Clause> newerclauses = substitute(clauses, negatedChosenLit);
                            return solve(newerclauses, env);
                        }
                        else if ((output == null) && (env.get(chosenLit.getVariable())==Bool.FALSE)){        //chosenLit instanceof NegLiteral
                            return null;
                        }

                        else {
                            return output;
                        }
                    }
                }
                i += 1;
//                System.out.println("value of i: " + i);
            }
        }



//        System.out.println("this shldnt happen at all");
        return null;
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

        //System.out.println(clauses);
        // declare ImList as output first
        ImList<Clause> output = new EmptyImList<>();
        Iterator<Clause> iterCl = clauses.iterator();

        // iterate on Clauses
        while (iterCl.hasNext()) {
            Clause eachC = iterCl.next();
            boolean containL = false;
            boolean containNotL = false;
            Iterator<Literal> iterLi = eachC.iterator();

            // iterate on literal
            while (iterLi.hasNext()) {
                Literal eachL = iterLi.next();
                if (eachL == l) {
                    containL = true;
                } else if (eachL.negates(l)) {
                    containNotL = true;
                }
            }

            // adding Clause or not
            if (containL) {
                ;
            } else {
                if (containNotL) {
                    eachC = eachC.reduce(l);
                    //System.out.println(eachC);
                }
                output = output.add(eachC);
            }
        }
        return output;

        //throw new RuntimeException("not yet implemented.");
    }

}
