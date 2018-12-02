package cn.agree.bean;

public class BlueWizard extends Monster{
	private static final String NAME = "蓝巫师";
	private static final int LIFE = 60;
	private static final int ATTRACK = 32;
	private static final int DEFENCE = 8;
	private static final int MONEY = 5;
	
	public BlueWizard() {
		this.name = NAME;
		this.life = LIFE;
		this.attack = ATTRACK;
		this.defence = DEFENCE;
		this.money = MONEY;
	}
}
