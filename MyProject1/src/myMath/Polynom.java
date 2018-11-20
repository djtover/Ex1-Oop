package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author David Tover
 *
 */
public class Polynom implements Polynom_able{
	private ArrayList<Monom> AL= new ArrayList<Monom>();
	/** 
	 * This is a constructor to build a Polynom by using a string
	 * @param s the input string for the Polynom
	 */
	public Polynom(String s) {
		int begin=0;
		//		Polynom p1=new Polynom();
		Monom m1= new Monom();
		for(int i=1;i<s.length();i++) {
			if(s.charAt(i)=='+') {
				m1= new Monom(s.substring(begin, i));
				AL.add(m1);
				begin=i+1;
			}
			else if(s.charAt(i)=='-') {
				m1= new Monom(s.substring(begin, i));
				AL.add(m1);
				begin=i;
			}
			else if(i==s.length()-1) {
				m1= new Monom(s.substring(begin,i+1));
				AL.add(m1);
			}
		}
		this.sort();
		this.duplicates();
	}
	/**
	 * This is function to swap values in order to sort the input function 
	 * by order of power from high to low
	 * @param i index in the arraylist
	 * @param j index in the arraylist
	 */
	private void swap(int i, int j) {
		AL.add(i, AL.get(j));
		AL.add(j,AL.get(i+1));
		AL.remove(i+2);
		AL.remove(j+1);

	}
	/**
	 * This is a constructor to build an empty Polynom with just the Monom zero
	 */
	public Polynom() {
		Monom m1=new Monom();
		this.AL.add(m1);
	}





	@Override
	/**
	 * This function returns the value f(x)
	 * This function uses an iterator and goes through each Monom
	 * And does f(x) on each one and sums it up
	 * @param x the input in which you want the Polynom to calculate  
	 * @return the sum of the Polynom with respect to x
	 */
	public double f(double x) {
		// TODO Auto-generated method stub
		double sum=0;
		Iterator<Monom> itr=AL.iterator();
		while(itr.hasNext()) {
			Monom m1=itr.next();
			sum=sum + m1.f(x);
		}
		return sum;

	}
	@Override
	/**
	 * This function is to add a Polynom to the original Polynom
	 * This function uses an Iterator and goes to each Monom 
	 * And uses the add Monom function to add each one
	 * @param p1 the Polynom in which you want to add
	 */
	public void add(Polynom_able p1) {
		// TODO Auto-generated method stub
		Iterator<Monom> itrP1=p1.iteretor();
		while(itrP1.hasNext()) {
			Monom m1=itrP1.next();
			this.add(m1);
		}
		this.removeZero();
	}


	@Override
	/**
	 * This function is to add a Monom to the current Polynom
	 * This function takes the input Monom and goes through the Polynom 
	 * to see if the power of the Monom in the Polynom is greater and 
	 * if it is continue through the Polynom until you either reach the end or a Monom with a smaller Monom or with a Monom with the same power.
	 * If it is at the end then and the Monom to the end of the Polynom.
	 * If it is at a Monom with a smaller power than add the Monom into the Polynom in that spot.
	 * If it is equal then add the Monom with the Monom with the same power.
	 * Then make sure to remove if there are any zero Monoms.
	 * @param m1 the Monom in which you want to add
	 */
	public void add(Monom m1) {
		// TODO Auto-generated method stub
		int i=0;
		Iterator<Monom> itr= AL.iterator();
		Comparator<Monom> com= new Monom_Comperator();
		if(AL.isEmpty())
			AL.add(m1);
		else {
			boolean end=false;
			//			Monom m2=itr.next();
			while(!end && itr.hasNext()) {
				Monom m2=itr.next();
				if(com.compare(m1, m2)==0) {
					m2.add(m1.get_coefficient(), m1.get_power());
					end=true;
				}
				if(com.compare(m1, m2)<0) {
					AL.add(AL.indexOf(m2), m1);
					end=true;
				}

			}
			if(end==false) {
				AL.add(m1);
			}
			//			if(!itr.hasNext())
			//				AL.add(m1);
			//			else if(m1.get_power()==itr.next().get_power())
			//				AL.get(i+1).set_coefficient(m1.get_coefficient()+AL.get(i+1).get_coefficient());
			//			else
			//				AL.add(i+1, m1);



		}
		this.duplicates();
		this.removeZero();

	}

	@Override
	/**
	 * This is a functiom to subtract a Polynom from the original Polynom 
	 * This function goes through the input Polynom by using an iterator 
	 * And then takes each Monom and adds it to the Polynom but times minus one
	 * @param p1 the Polynom in which you want to subtract 
	 */
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom p2= new Polynom(p1.toString());
		Iterator<Monom> itrP2=p2.iteretor();
		while(itrP2.hasNext()) {
			Monom m1=itrP2.next();
			Monom m2= new Monom(-(m1.get_coefficient()),m1.get_power());
			this.add(m2);	
		}	
	}

	@Override
	/**
	 * This function multiplies the original Polynom by the input Polynom
	 * It goes through the original Polynom and multiplies the coefficients 
	 * And adds the powers and saves it into another Polynom and once the 
	 * multiplication is done it will copy back the value into the original Polynom
	 * and then has to resort it and make sure there are no duplicate powers and no zero Monoms
	 * @param p1 the Polynom in which you want to multiply
	 */
	public void multiply(Polynom_able p1) {
		// TODO Auto-generated method stub
		Iterator <Monom> itrP1=p1.iteretor();
		Iterator <Monom> itrAL=AL.iterator();
		Polynom p2=new Polynom();
		while (itrAL.hasNext()) {
			Monom mAL= itrAL.next();
			double ceofAL=mAL.get_coefficient();
			int powAL=mAL.get_power();
			while(itrP1.hasNext()) {
				Monom mP1= itrP1.next();
				mAL.multiply(mP1.get_coefficient(), mP1.get_power());
				Monom temp = new Monom(mAL.toString());
				p2.add(temp);
				mAL.set_coefficient(ceofAL);
				mAL.set_power(powAL);

			}
			itrP1=p1.iteretor();
		}
		Iterator<Monom> itrP2=p2.iteretor();
		AL.clear();
		while(itrP2.hasNext()) {
			Monom mP2=itrP2.next();
			AL.add(mP2);
		}
		this.sort();
		this.duplicates();
		this.removeZero();
	}

	@Override
	/**
	 * This function is to check to see if 2 Polynoms are equal.
	 * @param p1 the Polynom in which you want to see if the Polynoms are equal
	 * @return the boolean value if equal or not
	 */
	public boolean equals(Polynom_able p1) {
		// TODO Auto-generated method stub
		Iterator<Monom> itrAL= AL.iterator();
		Iterator<Monom> itrP1= p1.iteretor();
		Monom mAL=new Monom();
		Monom mP1=new Monom();
		while(itrAL.hasNext()&&itrP1.hasNext()) {
			mAL=itrAL.next();
			mP1=itrP1.next();
			if(mAL.get_coefficient()!=mP1.get_coefficient()|| mAL.get_power()!=mP1.get_power())
				return false;
		}
		return true;
	}

	@Override
	/**
	 * This function is to check if it is a zero Polynom.
	 * @return the boolean value if it is a zero Polynom
	 */
	public boolean isZero() {
		// TODO Auto-generated method stub
		Iterator<Monom> itr=AL.iterator();
		if(itr.hasNext()) {
			Monom m1=itr.next();
			if(m1.get_coefficient()==0)
				return true;
		}
		return false;
	}

	@Override
	/**
	 * This is a function to find the root of a function within a distance of epsilon.
	 * The root of a function is where a function equals to 0.
	 * It finds it by taking the middle of the of the boundaries and goes right and left 
	 * based on if it multiplied by the lower boundary is greater or less than zero.
	 * @param x0 the lower end to check
	 * @param x1 the upper end to check
	 * @param eps the distance in which you need to be accurate by
	 * @return the root of the function
	 */
	public double root(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		double xMidO=(x0+x1)/2;
		double xMidN=0;
		boolean flag=true;
		while(flag) {
			if(f(x0)*f(xMidO)<0) {
				x1=xMidO;
			}
			else if(f(x0)*f(xMidO)>0) {
				x0=xMidO;

			}
			else
				break;

			xMidN=(x0+x1)/2;
			double epsMid=((xMidN-xMidO)/xMidN);

			xMidO=xMidN;
			if(Math.abs(epsMid)<eps)
				flag=false;
		}

		return xMidO;
	}

	@Override
	/**
	 * This is a function to make a deep copy and return that Polynom_able.
	 * @return A deep copy of the original Polynom
	 */
	public Polynom_able copy() {
		// TODO Auto-generated method stub
		Polynom p1=new Polynom();
		Iterator <Monom> itrAL=AL.iterator();
		while(itrAL.hasNext()) {
			Monom m1=new Monom(itrAL.next().toString());
			p1.add(m1);
		}

		return p1;
	}

	@Override
	/**
	 * This is a function that will do a derivative on the whole Polynom.
	 * This function uses an iterator and goes through the each Monom and uses the Monom's function to do a derivative.
	 * At the end check to see if there is a zero Monom and remove it
	 * @return The derivative of the Polynom 
	 */
	public Polynom_able derivative() {
		// TODO Auto-generated method stub
		Polynom_able p1= new Polynom();
		Iterator<Monom> itr= AL.iterator();
		Monom m1= new Monom();
		while(itr.hasNext()) {
			m1=itr.next();
			m1.derivative();
			p1.add(m1);
		}
		this.removeZero();
		return p1;
	}

	@Override
	/**
	 * This is a function to find the area beneath a function and the y axis.
	 * This function uses the Riemanns Integral starting from x0 till x1 with eps size steps.
	 * see: https://en.wikipedia.org/wiki/Riemann_integral
	 * @param x0 the lower end to check
	 * @param x1 the upper end to check
	 * @param eps the size of steps to take
	 * @return the area under the function that is above the the x axis
	 */
	public double area(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		double sum=0; 
		double min=0;
		for(double i=x0;i<x1;i=i+eps) {
			if(f(i)>0 && f(i+eps)>0) {
				min=Double.min(f(i), f(i+eps));
				sum=sum+(min*eps);
			}
		}
		return sum;

	}

	@Override
	/**
	 * A function to use iterator for this class
	 * @return the iterator for Monom
	 */

	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub

		return AL.iterator();
	}
	/**
	 * toString function so it could print out the Polynom
	 * @return the string in which you want to print
	 */
	public String toString() {
		Iterator<Monom> itr=AL.iterator();
		String s="";
		while(itr.hasNext()) {
			Monom m1=itr.next();
			if(m1.toString().charAt(0)!='-')
				s=s+m1.toString()+"+";
			else if(s.length()==0){
				s=s+m1.toString()+"+";		
			}
			else {
				s=s.substring(0, s.length()-1);
				s=s+m1.toString()+"+";
			}
		}
		if(s.length()>0)
			s=s.substring(0, s.length()-1);
		return s;
	}
	/**
	 * Remove all Monoms that have 0 as coefficient.
	 */
	private void removeZero() {
		for(int j=0;j<AL.size();j++) {
			if(AL.get(j).get_coefficient()==0)
				AL.remove(j);
		}
	}
	/** 
	 * This function adds all the Monoms with the same power together.
	 */
	private void duplicates() {
		Monom_Comperator mc=new Monom_Comperator();
		for (int j=1;j<AL.size();j++) {
			if(mc.compare(AL.get(j), AL.get(j-1))==0) {
				Monom mLow=AL.get(j-1);
				Monom mHi=AL.get(j);
				mLow.set_coefficient(mLow.get_coefficient()+mHi.get_coefficient());
				AL.remove(AL.get(j));
				j--;
			}
		}
	}
	/**
	 * This function sorts the Polynom based on power from highest to lowest.
	 */
	private void sort() {
		Monom_Comperator mc=new Monom_Comperator();
		for(int i=1;i<AL.size();i++) {
			for(int j=i;j>0;j--) {
				if(mc.compare(AL.get(j),AL.get(j-1))==-1){
					swap(j,j-1);
				}
			}
		}
	}
}
