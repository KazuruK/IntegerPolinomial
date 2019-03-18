import java.util.Arrays;
import java.util.List;

public class Polynomial {
    private int[] array;
    private int degree;

    public Polynomial(int[] array) {
        this.array = new int[array.length];
        this.array = array;
        this.degree = array.length - 1;
    }

    private Polynomial(int degree) {
        this.degree = degree;
        this.array = new int[this.degree + 1];
    }

    public Polynomial(Polynomial p) {
        this.degree = p.degree;
        this.array = p.array.clone();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        boolean isZero = true;
        for (int i = this.degree; i >= 0; i--) {
            if (i == 0) {
                if (this.array[i] > 0 && !isZero) s.append("+");
                if (this.array[i] != 0) s.append(this.array[i]);
                break;
            }
            if (this.array[i] == 0) continue;
            if (this.array[i] < 0) {
                if (this.array[i] == -1) {
                    s.append("-");
                } else s.append(this.array[i]);
            }
            if (this.array[i] > 0) {
                if (!isZero) s.append("+");
                if (this.array[i] != 1) s.append(this.array[i]);
            }
            if (this.array[i] != 0) isZero = false;
            if (i == 1) s.append("x");
            else s.append("x^" + i);
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        if (!(other instanceof Polynomial)) return false;
        Polynomial p = (Polynomial) other;

        return Arrays.equals(array, p.array);
    }

    @Override
    public int hashCode() {
        int result = degree;
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    //сложить
    public Polynomial summarize(Polynomial p2) {
        int max = Math.max(this.degree, p2.degree);
        int min = Math.min(this.degree, p2.degree);
        Polynomial res = new Polynomial(max);
        for (int i = 0; i <= max; i++) {
            if (i <= min) res.array[i] = this.array[i] + p2.array[i];
            else {
                if (max == this.degree) res.array[i] = this.array[i];
                else res.array[i] = p2.array[i];
            }
        }
        res.optimize();
        return res;
    }

    //вычесть
    public Polynomial subtract(Polynomial p2) {
        Polynomial nP = new Polynomial(p2);
        for (int i = 0; i <= nP.degree; i++) {
            nP.array[i] = -nP.array[i];
        }
        return this.summarize(nP);
    }

    public Polynomial multiply(Polynomial p2) {
        Polynomial res = new Polynomial(this.degree + p2.degree);
        for (int i = this.degree; i >= 0; i--) {
            for (int j = p2.degree; j >= 0; j--) {
                res.array[i + j] += this.array[i] * p2.array[j];
            }
        }
        res.optimize();
        return res;
    }

    //поделить
    public Polynomial divide(Polynomial p2) {
        return divideWithRemainder(p2).get(0);
    }

    //найти остаток
    public Polynomial getRemainder(Polynomial p2) {
        return divideWithRemainder(p2).get(1);
    }

    //деление с остатком
    private List<Polynomial> divideWithRemainder(Polynomial p2) {
        if (this.equals(p2)) {
            Polynomial quotient = new Polynomial(1);
            Polynomial remainder = new Polynomial(1);
            quotient.array[0] = 1;
            remainder.array[0] = 0;
            return Arrays.asList(quotient, remainder);
        }
        this.optimize();
        p2.optimize();
        if (p2.array[p2.degree] == 0) throw new IllegalArgumentException();
        Polynomial dividend = new Polynomial(this);
        Polynomial quotient = new Polynomial(this.degree);
        Polynomial multiply;
        int k;
        while (dividend.degree >= p2.degree) {
            k = dividend.array[dividend.degree] / p2.array[p2.degree];
            quotient.array[dividend.degree - p2.degree] = k;
            Polynomial res = new Polynomial(this.degree);
            Arrays.fill(res.array, 0);
            res.array[dividend.degree - p2.degree] = k;
            multiply = p2.multiply(res);
            dividend = dividend.subtract(multiply);
        }
        quotient.optimize();
        dividend.optimize();
        return Arrays.asList(quotient, dividend);
    }

    private void optimize() {
        if (this.degree != 0) {
            for (int i = this.degree; i >= 0; i--) {
                if (this.array[i] != 0){
                    if (i != this.degree) {
                        int[] nArray = Arrays.copyOfRange(this.array, 0, this.degree + 1);
                        this.degree = i;
                        this.array = Arrays.copyOfRange(nArray, 0, this.degree + 1);
                        break;
                    }
                }
            }

        }
    }
}

