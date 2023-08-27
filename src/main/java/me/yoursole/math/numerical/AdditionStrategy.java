package me.yoursole.math.numerical;

import me.yoursole.math.common.NumericalTransformer;
import me.yoursole.math.numerical.complex.NumericalBase;
import me.yoursole.math.numerical.matrix.Matrix;

import java.util.Arrays;


public enum AdditionStrategy {
    BASE_MATRIX(new NumericalTransformer<NumericalBase, Matrix>() {
        @Override
        public Numerical operate(NumericalBase base, Matrix matrix) {
            int[] dimension = matrix.getDimension();
            Numerical[][] newMatrix = new Numerical[dimension[0]][dimension[1]];

            for (int i = 0; i < dimension[0]; i++) {
                for (int j = 0; j < dimension[1]; j++) {
                    newMatrix[i][j] = matrix.getValues()[i][j].add(base);
                }
            }

            return new Matrix(newMatrix);
        }

        @Override
        public Class<?> getTypeA() {
            return NumericalBase.class;
        }

        @Override
        public Class<?> getTypeB() {
            return Matrix.class;
        }
    }),

    MATRIX_BASE(new NumericalTransformer<Matrix, NumericalBase>() {
        @Override
        public Numerical operate(Matrix matrix, NumericalBase base) {
            return AdditionStrategy.BASE_MATRIX.getOperation().operate(base, matrix);
        }

        @Override
        public Class<?> getTypeA() {
            return Matrix.class;
        }

        @Override
        public Class<?> getTypeB() {
            return NumericalBase.class;
        }
    }),

    BASE_BASE(new NumericalTransformer<NumericalBase, NumericalBase>() {
        @Override
        public Numerical operate(NumericalBase baseA, NumericalBase baseB) {
            return new NumericalBase(baseA.getReal() + baseB.getReal(), baseA.getImaginary() + baseB.getImaginary());
        }

        @Override
        public Class<?> getTypeA() {
            return NumericalBase.class;
        }

        @Override
        public Class<?> getTypeB() {
            return NumericalBase.class;
        }
    }),

    MATRIX_MATRIX(new NumericalTransformer<Matrix, Matrix>() {
        @Override
        public Numerical operate(Matrix baseA, Matrix baseB) {
            if (!Arrays.equals(baseA.getDimension(), baseB.getDimension())) {
                throw new IllegalArgumentException("Addition of two matrices with different dimensions");
            }

            int[] dimension = baseA.getDimension();
            Numerical[][] newMatrix = new Numerical[dimension[0]][dimension[1]];

            for (int i = 0; i < dimension[0]; i++) {
                for (int j = 0; j < dimension[1]; j++) {
                    newMatrix[i][j] = baseA.getValues()[i][j].add(baseB.getValues()[i][j]);
                }
            }

            return new Matrix(newMatrix);
        }

        @Override
        public Class<?> getTypeA() {
            return Matrix.class;
        }

        @Override
        public Class<?> getTypeB() {
            return Matrix.class;
        }
    });

    private final NumericalTransformer operation;

    AdditionStrategy(NumericalTransformer operation) {
        this.operation = operation;
    }

    public static AdditionStrategy fetch(Numerical left, Numerical right) {
        Class<? extends Numerical> leftType = left.getClass();
        Class<? extends Numerical> rightType = right.getClass();

        for (AdditionStrategy strategy : AdditionStrategy.values()) {
            NumericalTransformer<? extends Numerical, ? extends Numerical> operation = strategy.getOperation();
            if (leftType == operation.getTypeA() && rightType == operation.getTypeB()) {
                return strategy;
            }
        }

        throw new IllegalStateException("No Addition Strategy for given set: " + left.getClass() + " + " + right.getClass());
    }

    public NumericalTransformer getOperation() {
        return this.operation;
    }

}
