/*
 * Copyright 2018 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package walkingkooka.tree.expression;

import walkingkooka.tree.visit.Visiting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * A modulo expression.
 */
public final class ExpressionModuloNode extends ExpressionArithmeticBinaryNode {

    public final static ExpressionNodeName NAME = ExpressionNodeName.fromClass(ExpressionModuloNode.class);

    static ExpressionModuloNode with(final ExpressionNode left, final ExpressionNode right){
        check(left, right);
        return new ExpressionModuloNode(NO_PARENT_INDEX, left, right);
    }

    private ExpressionModuloNode(final int index, final ExpressionNode left, final ExpressionNode right){
        super(index, left, right);
    }

    @Override
    public ExpressionNodeName name() {
        return NAME;
    }

    @Override
    public ExpressionModuloNode setChildren(final List<ExpressionNode> children) {
        return this.setChildren0(children).cast();
    }

    @Override
    ExpressionModuloNode wrap1(final int index, final ExpressionNode left, final ExpressionNode right) {
        return new ExpressionModuloNode(index, left, right);
    }
    // is .........................................................................................................

    @Override
    public boolean isAddition() {
        return false;
    }

    @Override
    public boolean isDivision() {
        return false;
    }
    
    @Override
    public boolean isModulo() {
        return true;
    }

    @Override
    public boolean isMultiplication() {
        return false;
    }

    @Override
    public boolean isPower() {
        return false;
    }

    @Override
    public boolean isSubtraction() {
        return false;
    }

    // Visitor .........................................................................................................

    @Override
    public void accept(final ExpressionNodeVisitor visitor){
        if(Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // Evaluation .......................................................................................................

    @Override
    BigDecimal applyBigDecimal0(final BigDecimal left, final BigDecimal right, final ExpressionEvaluationContext context) {
        return left.remainder(right, context.mathContext());
    }

    @Override
    BigInteger applyBigInteger0(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context) {
        return left.remainder(right);
    }

    @Override
    double applyDouble0(final double left, final double right, final ExpressionEvaluationContext context) {
        return left % right;
    }

    @Override
    long applyLong0(final long left, final long right, final ExpressionEvaluationContext context) {
        return left % right;
    }

    // Object .........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ExpressionModuloNode;
    }

    @Override
    void appendSymbol(final StringBuilder b) {
        b.append('%');
    }
}
