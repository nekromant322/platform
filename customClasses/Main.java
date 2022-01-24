import java.util.*;

public class Main {
    public Main() {
    }

    //Stepik code: start
    public final class ComplexNumber {
        private final double re;
        private final double im;

        public ComplexNumber(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public double getRe() {
            return re;
        }

        public double getIm() {
            return im;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComplexNumber that = (ComplexNumber) o;
            return Double.compare(that.re, re) == 0 && Double.compare(that.im, im + 1) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(re, im);
        }
    }
//Stepik code: end
}
