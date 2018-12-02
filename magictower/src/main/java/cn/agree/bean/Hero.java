package cn.agree.bean;

public class Hero {
	
	private String name;//英雄名称
	private int life;//英雄生命值
	private int attack;//英雄攻击力
	private int defence;//英雄防御力
	private int blueKeyNum;//蓝钥匙数量
	private int yellowKeyNum;//黄钥匙数量
	private int redKeyNum;//红钥匙数量
	private int money;//金钱
	private String sword;//剑
	private String shield;//盾
	private int x;//横坐标
	private int y;//纵坐标
	private int map;//游戏地图层数
	private int max;//游戏地图层数
	
	public Hero(){
		
	}

	public Hero(String name, String life, String attack, String defence, 
			String blueKeyNum, String yellowKeyNum, String redKeyNum, String money, 
			String sword, String shield, String x, String y, String map, String max) {
		super();
		this.name = name;
		this.life = Integer.parseInt(life);
		this.attack = Integer.parseInt(attack);
		this.defence = Integer.parseInt(defence);
		this.blueKeyNum = Integer.parseInt(blueKeyNum);
		this.yellowKeyNum = Integer.parseInt(yellowKeyNum);
		this.redKeyNum = Integer.parseInt(redKeyNum);
		this.money = Integer.parseInt(money);
		this.sword = sword;
		this.shield = shield;
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
		this.map = Integer.parseInt(map);
		this.max = Integer.parseInt(max);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getBlueKeyNum() {
		return blueKeyNum;
	}

	public void setBlueKeyNum(int blueKeyNum) {
		this.blueKeyNum = blueKeyNum;
	}

	public int getYellowKeyNum() {
		return yellowKeyNum;
	}

	public void setYellowKeyNum(int yellowKeyNum) {
		this.yellowKeyNum = yellowKeyNum;
	}

	public int getRedKeyNum() {
		return redKeyNum;
	}

	public void setRedKeyNum(int redKeyNum) {
		this.redKeyNum = redKeyNum;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getSword() {
		return sword;
	}

	public void setSword(String sword) {
		this.sword = sword;
	}

	public String getShield() {
		return shield;
	}

	public void setShield(String shield) {
		this.shield = shield;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attack;
		result = prime * result + blueKeyNum;
		result = prime * result + defence;
		result = prime * result + life;
		result = prime * result + map;
		result = prime * result + max;
		result = prime * result + money;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + redKeyNum;
		result = prime * result + ((shield == null) ? 0 : shield.hashCode());
		result = prime * result + ((sword == null) ? 0 : sword.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + yellowKeyNum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hero other = (Hero) obj;
		if (attack != other.attack)
			return false;
		if (blueKeyNum != other.blueKeyNum)
			return false;
		if (defence != other.defence)
			return false;
		if (life != other.life)
			return false;
		if (map != other.map)
			return false;
		if (max != other.max)
			return false;
		if (money != other.money)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (redKeyNum != other.redKeyNum)
			return false;
		if (shield == null) {
			if (other.shield != null)
				return false;
		} else if (!shield.equals(other.shield))
			return false;
		if (sword == null) {
			if (other.sword != null)
				return false;
		} else if (!sword.equals(other.sword))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (yellowKeyNum != other.yellowKeyNum)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hero [name=" + name + ", life=" + life + ", attack=" + attack + ", defence=" + defence + ", blueKeyNum="
				+ blueKeyNum + ", yellowKeyNum=" + yellowKeyNum + ", redKeyNum=" + redKeyNum + ", money=" + money
				+ ", sword=" + sword + ", shield=" + shield + ", x=" + x + ", y=" + y + ", map=" + map + ", max=" + max
				+ "]";
	}

}
