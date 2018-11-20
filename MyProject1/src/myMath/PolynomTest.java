package myMath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PolynomTest {

	@Test
	void testF() {
		Polynom p = new Polynom("2X^3");
		double output=p.f(3);
		assertEquals(54,output);
	}
	@Test
   void testAddPolynom() {
	   Polynom p1=new Polynom("3x^2+4X^3");
		Polynom p2= new Polynom("4X^2-3x+6X^3");
		p1.add(p2);
		assertEquals("10.0X^3+7.0X^2-3.0X^1",p1.toString());
   }
	@Test
	void testAddMonom() {
		Polynom p1= new Polynom("4X^2-3x+6X^3");
		Monom m1=new Monom("-7X");
		p1.add(m1);
		assertEquals("6.0X^3+4.0X^2-10.0X^1",p1.toString());
	}
	@Test
	void testSubPolynom() {
		Polynom p1=new Polynom("3X^2+4X^3");
		Polynom p2= new Polynom("4X^2-3X+6X^3");
		p1.substract(p2);
		assertEquals("-2.0X^3-1.0X^2+3.0X^1",p1.toString());
	}
	@Test
	void testMultPolynom() {
		Polynom p1=new Polynom("3X^2+4X^3");
		Polynom p2= new Polynom("4X^2-3x+6X^3");
		p1.multiply(p2);
		assertEquals("24.0X^6+34.0X^5-9.0X^3",p1.toString());
	}
	@Test
	void testEqualsPolynom() {
		Polynom p1=new Polynom("3X^2+4X^3-x+3x^4");
		assertEquals("3.0X^4+4.0X^3+3.0X^2-1.0X^1",p1.toString());
	}
	@Test
	void testIsZero() {
		Polynom p1= new Polynom();
		assertEquals("0.0X^0",p1.toString());
	}
	@Test
	void testIsCopy() {
		Polynom_able p1= new Polynom("4X^2-3X^1+6X^3");
		Polynom_able p2= p1.copy();
		assertEquals("6.0X^3+4.0X^2-3.0X^1",p2.toString());
	}
	@Test
	void testDerivative() {
		Polynom_able p1= new Polynom("4X^2-3X^1+6X^3");
		Polynom p2=new Polynom(p1.derivative().toString());
		assertEquals("18.0X^2+8.0X^1-3.0X^0",p2.toString());
	}
	@Test
	void testArea() {
		Polynom p1=new Polynom( "x^4+2-2X^2");
		double area=p1.area(-1.5, 2, .5);
		assertEquals(4.84375,area);
	}
	@Test
	void testRoot() {
		Polynom p1= new Polynom("-4x^2+3");
		double root=p1.root(0, 2, .2);
		assertEquals(0.875,root);
	}
	
}
