package model;

/*
 * a class that defines the functionalit of a symbol
 * @author - Gayashan Bombuwala 2015047
 */
public class Symbol implements ISymbol {
	private String imagePath;
	private int value;

	/*
	 * Constructor
	 * 
	 * @param imageName - name of the related image
	 * 
	 * @param value - value of the related symbol
	 */
	public Symbol(String imageName, int value) {
		imagePath = "../img/" + imageName;
		this.value = value;
	}

	/*
	 * compares whether the values of two symbols are the same
	 * 
	 * @param rhs - another symbol that needs to be compared with this symbol
	 * 
	 * @return - an array containing the isMatch boolean and the matching value
	 */
	public int[] compareValues(Symbol rhs) {
		int[] result = new int[2];
		result[1] = value;
		if (this.value == rhs.getValue()) {
			result[0] = 1;
		} else {
			result[0] = 0;
		}
		return result;
	}

	// getters and setter for the class fields
	@Override
	public void setImage(String imageName) {
		imagePath = "../img/" + imageName;
	}

	@Override
	public String getImage() {
		return imagePath;
	}

	@Override
	public void setValue(int v) {
		value = v;

	}

	@Override
	public int getValue() {
		return value;
	}

}
