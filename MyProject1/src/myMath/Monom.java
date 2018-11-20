package myMath;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author David Tover
 *
 */
public class Monom implements function{
	/** 
	 * This is constuctor to build a Monom 
	 * @param a the coefficient of the Monom
	 * @param b the power of the Monom
	 */
	public Monom(double a, int b)throws RuntimeException{
		if (b<0){
			throw new java.lang.RuntimeException("Error: Power must be a positive number");
		}
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * This is a copy constructor to build a Monom 
	 * @param ot the other Monom to copy
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	/**
	 * This is a constructor to build a Monom from a String
	 * @param s the string on which you want to make a Monom
	 */
	public Monom(String s) {
		if(s.indexOf('x')==-1 && s.indexOf('X')==-1)
			this.set_coefficient(Double.parseDouble(s));
		else if(s.indexOf('x')!=-1) {
			int iX=s.indexOf('x');
			if(iX==0) {
				this.set_coefficient(1);
			}
			else if(s.charAt(iX-1)=='-')
				this.set_coefficient(-1);
			else	
				this.set_coefficient(Double.parseDouble(s.substring(0,iX)));
			if(iX!=s.length()-1)
				this.set_power(Integer.parseInt(s.substring(iX+2, s.length())));
			else if(iX==(s.length()-1))
				this.set_power(1);	
		}
		else if(s.indexOf('X')!=-1) {
			int iX=s.indexOf('X');
			if(iX==0) {
				this.set_coefficient(1);
			}
			else if(s.charAt(iX-1)=='-')
				this.set_coefficient(-1);
			else	
				this.set_coefficient(Double.parseDouble(s.substring(0,iX)));
			if(iX!=s.length()-1)
				this.set_power(Integer.parseInt(s.substring(iX+2, s.length())));
			else if(iX==(s.length()-1))
				this.set_power(1);
		}


	}
	/**
	 * This is a default constructor 
	 */
	public Monom() {
		this._coefficient=0;
		this._power=0;
	}
	/**
	 * 
	 * @return the coeefficient of the Monom
	 */
	public double get_coefficient() {
		return _coefficient;
	}
	/**
	 * 
	 * @return the power of the Monom
	 */
	public int get_power() {
		return _power;
	}
	/**
	 * To set a coefficient
	 * @param a the coefficient in which you want to set
	 */
	public void set_coefficient(double a){
		this._coefficient = a;
	}
	/**
	 * To set a power
	 * @param p the power in which you want to set
	 */
	public void set_power(int p) {
		this._power = p;
	}


	private double _coefficient; // 
	private int _power;
	@Override
	/** 
	 * This is a function of f(x) for the Monom
	 * @param x the value in which you want to calculate in the Monom
	 * @return The value of the Monom in respect to X
	 */
	public double f(double x) {
		// TODO Auto-generated method stub
		return (Math.pow(x, this._power))*this._coefficient;

	}
	/**
	 * This is a function to do a derivative of the Monom
	 */
	public void derivative() {
		if(this._power>0) {
			set_coefficient(this._coefficient*this._power);
			this._power--;
		}
		else 
			this._coefficient=0;
	}
	/**
	 * This is a function to multiply the Monom with an input of a coefficient and a power
	 * @param coefficient the coefficient of the Monom in which you want to multiply by
	 * @param power the power of the Monom in which you want to multiply by
	 */
	public void multiply(double coefficient,int power) {
		set_coefficient(this._coefficient*coefficient);
		set_power(this._power+power);
	}
	/**
	 * This is a function to do addition with an input of a coefficient and a power
	 * @param coefficient the coefficient of the Monom in which you want to add
	 * @param power the power of the Monom in which you want to add
	 */
	public void add(double coefficient,int power) {
		if(this._power==power)
			set_coefficient(this._coefficient+coefficient);
		else
			throw new java.lang.RuntimeException("Error: Can't add Monoms with different powers");
	}
	/**
	 * This is a function so you could print out a Monom
	 * @return the string of the Monom
	 */
	public String toString() {
		return this._coefficient+"X^"+this._power;
	}

}

