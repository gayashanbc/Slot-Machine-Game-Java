package model;

/*
 * an interface that defines the behaviour of a symbol
 * @author - Gayashan Bombuwala 2015047
 */
public interface ISymbol {
	void setImage(String imageName);

	String getImage();

	void setValue(int v);

	int getValue();
}
