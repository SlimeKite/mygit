package cn.agree.bean;

public class MiddleSlime extends Monster{
	private static final String NAME = "中史莱姆";
	private static final int LIFE = 45;
	private static final int ATTRACK = 20;
	private static final int DEFENCE = 2;
	private static final int MONEY = 2;
	
	public MiddleSlime() {
		this.name = NAME;
		this.life = LIFE;
		this.attack = ATTRACK;
		this.defence = DEFENCE;
		this.money = MONEY;
	}
}
