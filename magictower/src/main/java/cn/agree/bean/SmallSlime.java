package cn.agree.bean;

public class SmallSlime extends Monster {
	private static final String NAME = "小史莱姆";
	private static final int LIFE = 35;
	private static final int ATTRACK = 18;
	private static final int DEFENCE = 1;
	private static final int MONEY = 1;
	
	public SmallSlime() {
		this.name = NAME;
		this.life = LIFE;
		this.attack = ATTRACK;
		this.defence = DEFENCE;
		this.money = MONEY;
	}
}
