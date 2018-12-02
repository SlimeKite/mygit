package cn.agree.bean;

public class SmallBat extends Monster{
	private static final String NAME = "小蝙蝠";
	private static final int LIFE = 35;
	private static final int ATTRACK = 38;
	private static final int DEFENCE = 3;
	private static final int MONEY = 3;
	
	public SmallBat() {
		this.name = NAME;
		this.life = LIFE;
		this.attack = ATTRACK;
		this.defence = DEFENCE;
		this.money = MONEY;
	}
}
