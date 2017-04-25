package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * a class that defines the functionality of a reel
 * @author - Gayashan Bombuwala 2015047
 */
public class Reel {
	private List<Symbol> symbolList;

	/*
	 * Default no-arg constructor
	 */
	public Reel() {
		// checks whether the symbol list has been already initialized
		if (symbolList == null) {
			symbolList = new ArrayList<Symbol>();

			symbolList.add(new Symbol("redseven.png", 7));
			symbolList.add(new Symbol("bell.png", 6));
			symbolList.add(new Symbol("watermelon.png", 5));
			symbolList.add(new Symbol("plum.png", 4));
			symbolList.add(new Symbol("lemon.png", 3));
			symbolList.add(new Symbol("cherry.png", 2));
		}

		Collections.shuffle(symbolList);
	}

	// getter method of the symbolList variable
	public List<Symbol> spin() {
		return symbolList;
	}

}
