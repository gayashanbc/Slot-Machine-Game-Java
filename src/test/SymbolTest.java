package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Symbol;

public class SymbolTest {

	@Test
	public void compareValues() {
		Symbol s1 = new Symbol("bell", 4);
		Symbol s2 = new Symbol("plum", 5);

		int results[] = s1.compareValues(s2);
		assertEquals(0, results[0]);
		assertEquals(4, results[1]);

		results = s2.compareValues(s2);
		assertEquals(1, results[0]);
		assertEquals(5, results[1]);
	}

}


