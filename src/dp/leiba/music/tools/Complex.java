package dp.leiba.music.tools;

// import be.tarsos.dsp.util.Complex;

/**
 * Complex.
 */
public class Complex
{
	
    private final double re;   // the real part
    private final double im;   // the imaginary part

    /**
     * Initializes a complex number.
     *
     * @param real The real part
     * @param imag The imaginary part
     */
    public Complex(double real, double imag)
    {
        re = real;
        im = imag;
    }
    
    /**
     * Real part of this complex number.
     *
     * @return Real part.
     */
    public double re() 
    {
        return re;
    }

    /**
     * Imaginary part of this complex number.
     *
     * @return Imaginary part.
     */
    public double im() 
    {
        return im;
    }
    
    /**
     * Modulus of this Complex number
     * (the distance from the origin in polar coordinates).
     *
     * @return |z| where z is this Complex number.
     */
    public double mod()
    {
        if (re != 0 || im != 0) {
            return Math.sqrt(re * re + im * im);
        } else {
            return 0d;
        }
    }

    /**
     * Argument of this Complex number
     * (the angle in radians with the x-axis in polar coordinates).
     *
     * @return arg(z) where z is this Complex number.
     */
    public double arg()
    {
        return Math.atan2(re, im);
    }

    /**
     * Absolute value of this complex number.
     * This quantity is also known as the modulus or magnitude.
     *
     * @return Absolute value of this complex number.
     */
    public double abs()
    {
        return Math.hypot(re, im);
    }

    /**
     * Phase of this complex number.
     * This quantity is also known as the ange or argument.
     *
     * @return Phase of this complex number, a real number between -pi and pi
     */
    public double phase() 
    {
        return Math.atan2(im, re);
    }

    /**
     * Sum of this complex number and the specified complex number.
     *
     * @param  Other complex number.
     * 
     * @return Complex number whose value is (this + that).
     */
    public Complex plus(Complex that) 
    {
        double real = this.re + that.re;
        double imag = this.im + that.im;
        
        return new Complex(real, imag);
    }

    /**
     * Result of subtracting the specified complex number from
     * this complex number.
     *
     * @param  Other complex number.
     * 
     * @return Complex number whose value is (this - that).
     */
    public Complex minus(Complex that) 
    {
        double real = this.re - that.re;
        double imag = this.im - that.im;
        
        return new Complex(real, imag);
    }

    /**
     * Product of this complex number and the specified complex number.
     *
     * @param  Other complex number.
     * 
     * @return Complex number whose value is (this * that).
     */
    public Complex times(Complex that) 
    {
        double real = this.re * that.re - this.im * that.im;
        double imag = this.re * that.im + this.im * that.re;
        
        return new Complex(real, imag);
    }

    /**
     * Returns the product of this complex number and the specified scalar.
     *
     * @param  alpha The scalar.
     * 
     * @return Complex number whose value is (alpha * this).
     */
    public Complex scale(double alpha) 
    {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * Product of this complex number and the specified scalar.
     *
     * @param alpha The scalar.
     * 
     * @return Complex number whose value is (alpha * this).
     * 
     * @deprecated Use {@link #scale(double)} instead.
     */
    public Complex times(double alpha) 
    {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * Complex conjugate of this complex number.
     *
     * @return Complex conjugate of this complex number.
     */
    public Complex conjugate() 
    {
        return new Complex(re, -im);
    }

    /**
     * Reciprocal of this complex number.
     *
     * @return Complex number whose value is (1 / this).
     */
    public Complex reciprocal() 
    {
        double scale = re * re + im * im;
        
        return new Complex(re / scale, -im / scale);
    }

    /**
     * Result of dividing the specified complex number into
     * this complex number.
     *
     * @param Other complex number.
     * 
     * @return Complex number whose value is (this / that).
     */
    public Complex divides(Complex that) 
    {
        return this.times(that.reciprocal());
    }

    /**
     * Complex exponential of this complex number.
     *
     * @return Complex exponential of this complex number.
     */
    public Complex exp() 
    {
        return new Complex(
        	Math.exp(re) * Math.cos(im),
        	Math.exp(re) * Math.sin(im)
        );
    }

    /**
     * Complex sine of this complex number.
     *
     * @return Complex sine of this complex number.
     */
    public Complex sin() 
    {
        return new Complex(
        	Math.sin(re) * Math.cosh(im),
        	Math.cos(re) * Math.sinh(im)
        );
    }

    /**
     * Complex cosine of this complex number.
     *
     * @return Complex cosine of this complex number.
     */
    public Complex cos() 
    {
        return new Complex(
        	Math.cos(re) * Math.cosh(im),
        	- Math.sin(re) * Math.sinh(im)
        );
    }

    /**
     * Complex tangent of this complex number.
     *
     * @return Complex tangent of this complex number.
     */
    public Complex tan() 
    {
        return sin().divides(cos());
    }
    
    /**
     * Representation of this complex number.
     *
     * @return Form 34 - 56i.
     */
    public String toString() {
        if (im == 0) {
        	return re + "";
        }
        
        if (re == 0) {
        	return im + "i";
        }
        if (im <  0) {
        	return re + " - " + (-im) + "i";
        }
        
        return re + " + " + im + "i";
    }
}
