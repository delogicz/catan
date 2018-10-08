package model;

/**
 * Determines allowed numbers on tokens.  
 * @author Daniel Panangian
 */
public enum TokenNumber {
	
	TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12);
	
	private int number;
	
	private TokenNumber(int number){
		this.number = number;
	}
	/**
	 * Getter for number
	 * @return
	 */
	public int getNumber(){
		return this.number;
	}
	
	/**
	 * Returns URL of a connected image needed for the view
	 * @return
	 */
	public String getImgUrl() {
		
		final String PATH = "img/token/";
		switch (this) {
		case TWO:
			return PATH + "num2.png";
		case THREE:
			return PATH + "num3.png";
		case FOUR:
			return PATH + "num4.png";
		case FIVE:
			return PATH + "num5.png";
		case SIX:
			return PATH + "num6.png";
		case SEVEN:
			return PATH + "robber.png";
		case EIGHT:
			return PATH + "num8.png";
		case NINE:
			return PATH + "num9.png";
		case TEN:
			return PATH + "num10.png";
		case ELEVEN:
			return PATH + "num11.png";
		case TWELVE:
			return PATH + "num12.png";
	
		default:
			return PATH + "num7.png";
			}
		
		}
	
	
}