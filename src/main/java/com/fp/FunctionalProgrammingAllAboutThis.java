package com.fp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

interface F<A, B> {

    B apply( A a );
}

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface Policy {

    String value() default "";

    String ruleNo();
}

public class FunctionalProgrammingAllAboutThis {

    /**
     * Pure Function.
     * 
     * The function should not affect the execution of the program other than the computation of the given input, and it should have no side effects.
     * <pre><code>
    function add(x, y) {
        return x + y;
    }
    
    function add(x, y) {
        return x + y;
    }
    console.log(add(1, 2)); // prints 3
    console.log(add(1, 2)); // still prints 3
    console.log(add(1, 2)); // WILL ALWAYS print 3
    
    function justTen() {
        return 10;
    }
    
    
    function addNoReturn(x, y) {
        var z = x + y
    }
    
    Above "addNoReturn" function is not pure function.
    because that does not only return any value but also we don't know how procedure data in the body of it.  
    </code></pre>
     * 
     */
    @Policy(ruleNo = "1", value = "Pure Function")
    public Integer add( int x, int y ) {
        return x + y;
    }

    /**
     * There is no variable in functional programming.
    <pre><code>
    var x = 1;
    x = x + 1;
    
    the first value of x is 1. but it changed the value with added 1 of it.
    As an above, x can reassign something value for a purpose. we call it('x') variable.
    
    but in the functional programming, we can not use a variable like 'x', but we can process data with following technique.
    addOneToSum y z =
    let
        x = 1
    in
        x + y + z
        
    Carefully, x is a constant. It is not a variable.
    
    
    Case 1: Correct approaching.
    function add( value ) {
        return value + 1;
    }
    
    Case 2: Incorrect approaching. because value is changed something.
    function add( value ) {
        return value += 1;
    }
    </code></pre>
     */
    @Policy(ruleNo = "2", value = "Immutable")
    public Integer addOneCorrect( Integer value ) {
        return value + 1; // value is not changed.
    }

    @Policy(ruleNo = "2", value = "Immutable")
    public Integer addOneInCorrect( Integer value ) {
        return value = +1; // value is changed.
    }

    /**
     * Functional programming has not Loop statements.
    <pre><code>
    // MutabilityLoop: simple loop construct
    var acc = 0;
    for (var i = 1; i <= 10; ++i)
    acc += i;
    console.log(acc); // prints 55
    
    
    // ImMutabilityLoop: without loop construct or variables (recursion)
    function sumRange(start, end, acc) {
    if (start > end)
        return acc;
    return sumRange(start + 1, end, acc + start)
    }
    console.log(sumRange(1, 10, 0)); // prints 55
    
    
    Following is more simple( ELM ).
    sumRange start end acc =
    if start > end then
        acc
    else
        sumRange (start + 1) end (acc + start) 
    </code></pre>
     */
    @Policy(ruleNo = "3", value = "MutabilityLoop")
    public Integer MutabilityLoop( Integer value ) {
        int sum = 0;
        for ( int i = 1; i <= 50; i++ ) {
            sum += i;
        }
        return sum;
    }

    @Policy(ruleNo = "3", value = "ImMutabilityLoop")
    public Integer recursiveLoop( Integer value, Integer from, Integer to ) {
        if ( from > to ) {
            return value;
        }
        return recursiveLoop( value + 1, 1, 50 );
    }

    /**
     * Funtion is just first-citizen, in other word funciton is just another variable in functional programming.
     * Because function is just simple value, so it can pass as argument another function.
     * 
     * The higher order function is a function that accepts another function as an argument and returns a function as a result.
     * 
     * <pre><code>
    Example 1: 
    
    function validateValueWithFunc(value, parseFunc, type) {
    if (parseFunc(value))
        console.log('Invalid ' + type);
    else
        console.log('Valid ' + type);
    }
    
    function makeRegexParser(regex) {
    return regex.exec;
    }
    
    var parseSsn = makeRegexParser(/^\d{3}-\d{2}-\d{4}$/);
    var parsePhone = makeRegexParser(/^\(\d{3}\)\d{3}-\d{4}$/);
    
    validateValueWithFunc('123-45-6789', parseSsn, 'SSN');
    validateValueWithFunc('(123)456-7890', parsePhone, 'Phone');
    validateValueWithFunc('123 Main St.', parseAddress, 'Address');
    validateValueWithFunc('Joe Mama', parseName, 'Name');
    
    
    Example 2: 
    function makeAdder(constantValue) {
    return function adder(value) {
        return constantValue + value;
    };
    }
    
    var add10 = makeAdder(10);
    console.log(add10(20)); // prints 30
    console.log(add10(30)); // prints 40
    console.log(add10(40)); // prints 50
    
    
    Example(Closer) 3:
    function grandParent(g1, g2) {
    var g3 = 3;
    return function parent(p1, p2) {
        var p3 = 33;
        return function child(c1, c2) {
            var c3 = 333;
            return g1 + g2 + g3 + p1 + p2 + p3 + c1 + c2 + c3;
        };
    };
    }       
    
    var parentFunc = grandParent(1, 2); // returns parent()
    var childFunc = parentFunc(11, 22); // returns child()
    console.log(childFunc(111, 222)); // prints 738
    // 1 + 2 + 3 + 11 + 22 + 33 + 111 + 222 + 333 == 738
    * 
     * </code></pre>
     */
    @Policy(ruleNo = "4", value = "higher-order function")
    public Integer higherOrderFunction( Integer value, Integer from, Integer to ) {
        return null;
    }

    /**
     * Combine two or more functions into a single function.
     * 
     * If you break your code into small, reusable pieces, the code can become a more complex piece of functionality. 
     * It's like a building brick. That is why we need a balance between the two, including the case above.
     * In functional programming, functions are like bricks. 
     * 
     * We write functions for very specific functions. Then we combine the functions like a LEGO block.
     * This is called a composite function.
     * 
     * <pre><code>
    var add10 = function(value) {
    return value + 10;
    };
    var mult5 = function(value) {
    return value * 5;
    };
    
    If the above function is expressed in lambda format: 
    var add10 = value => value + 10;
    var mult5 = value => value * 5;
    
    
    Suppose you need a new function that accepts a single value, adds 10 to that value, and returns a value multiplied by 5.
    
    var result = value => 5 * (value + 10);
    But the case of above, a brace  is very important.  programmers should be careful write coding about to not exclude brace.
    
    Following statement is the better statement for above.
    var mult5AfterAdd10 = value => mult5(add10(value));
    
    
    Following statement is the double better statement for above.
    mult5AfterAdd10 = (mult5 << add10)
    
    Wow, It's a wonderful programming.
    Input argument "x" 
    pass to function "t", 
    and then pass to function "r",  
       and then pass to function "s",
          and then pass to function "h",
             and then pass to function "g".
    finally, getting value x.
    f x = (g << h << s << r << t) x
    
    But following statement has big problem. "add" function has two arguments so can not composition together.
    var mult5AfterAdd10 = y => mult5(add(10, y));
    
    
    So, we need to consider another way. (It's calling currying) 
    
     * </code></pre>
     * 
     */
    @Policy(ruleNo = "5", value = "composition function")
    public Integer compositionFunction() {
        return null;
    }

    /**
     * A curling function is a function that takes only one parameter at a time.
     * 
     * The "currying function" enables a "composition function" which is converting a function 
     *   with two or more arguments into a technique that takes one argument.
     *   
    following statement has big problem. "add" function has two arguments so can not composition together.
     
    var mult5AfterAdd10 = y => mult5(add(10, y));
    
    To solve this problem, how about passing x, passing y, and calculating x + y?
    
    var add = x => y => x + y
    
    var compose = (f, g) => x => f(g(x));
    var mult5AfterAdd10 = compose(mult5, add(10));
    
    add x y = x + y
    mult5AfterAdd10 = (mult5 << add 10)
    
     * </code></pre>
     * 
     */
    @Policy(ruleNo = "6", value = "currying function")
    public Integer curryingFunction() {
        return null;
    }

    //function composition
    /**
     * @param args
     */
    public static void main( String[] args ) {
        F<Integer, String> tos = n -> Integer.toString( n );
        System.out.printf( "FP Result: %s", tos.apply( 3 ) );
    }
}
