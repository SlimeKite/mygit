package cn.agree.bean;

public class SmallSkull extends Monster{
	private static final String NAME = "小骷髅";
	private static final int LIFE = 50;
	private static final int ATTRACK = 42;
	private static final int DEFENCE = 6;
	private static final int MONEY = 6;
	
	public SmallSkull() {
		this.name = NAME;
		this.life = LIFE;
		this.attack = ATTRACK;
		this.defence = DEFENCE;
		this.money = MONEY;
	}
}
