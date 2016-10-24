package tdd.vendingMachine;

/**
 * @author Marek Kort
 *
 */
public enum Nominal {
	FIVE(5.0f),
	TWO(2.0f),
	ONE(1.0f),
	ZERO_FIFTY(0.5f),
	ZERO_TWENTY(0.2f),
	ZERO_TEN(0.1f);
	
	private float value;
	private Nominal(float val){
		this.value = val;
	}
	
	public float value(){
		return this.value;
	}
}
