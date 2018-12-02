package cn.agree.bean;
/**
 * 怪物类
 * @author lizhenyu
 *
 */
public abstract class Monster {
	
	protected String name;//怪物名称
	protected int life;//怪物血量
	protected int attack;//怪物攻击力
	protected int defence;//怪物防御力
	protected int money;//怪物掉落金钱
	
	public Monster() {
		
	}
	
	public Monster(String name, int life, int attack, int defence, int money) {
		super();
		this.name = name;
		this.life = life;
		this.attack = attack;
		this.defence = defence;
		this.money = money;
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + attack;
		result = prime * result + defence;
		result = prime * result + life;
		result = prime * result + money;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Monster other = (Monster) obj;
		if (attack != other.attack)
			return false;
		if (defence != other.defence)
			return false;
		if (life != other.life)
			return false;
		if (money != other.money)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Monster [name=" + name + ", life=" + life + ", attack=" + attack + ", defence=" + defence + ", money="
				+ money + "]";
	}
	
}
