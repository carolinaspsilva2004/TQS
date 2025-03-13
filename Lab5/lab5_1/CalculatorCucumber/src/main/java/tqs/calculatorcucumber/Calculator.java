/*
 * (C) Copyright 2017 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package tqs.calculatorcucumber;

import static java.util.Arrays.asList;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Calculator {
    private final Deque<Number> stack = new LinkedList<Number>();
    private static final List<String> OPS = asList("-", "+", "*", "/");

    public void push(Object arg) {
        if (arg instanceof String && OPS.contains(arg)) {
            Number y = stack.removeLast();
            Number x = stack.isEmpty() ? 0 : stack.removeLast();
            Double val = null;
            switch ((String) arg) {
                case "-":
                    val = x.doubleValue() - y.doubleValue();
                    break;
                case "+":
                    val = x.doubleValue() + y.doubleValue();
                    break;
                case "*":
                    val = x.doubleValue() * y.doubleValue();
                    break;
                case "/":
                    if (y.doubleValue() == 0) {
                        throw new ArithmeticException("Divisão por zero não permitida.");
                    }
                    val = x.doubleValue() / y.doubleValue();
                    break;
                default:
                    throw new IllegalArgumentException("Operação inválida: " + arg);
            }
            push(val);
        } else if (arg instanceof Number) {
            stack.add((Number) arg);
        } else {
            throw new IllegalArgumentException("Entrada inválida: " + arg);
        }
    }
    

    public Number value() {
        return stack.getLast();
    }
}
