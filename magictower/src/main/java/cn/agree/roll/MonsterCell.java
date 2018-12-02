package cn.agree.roll;

import java.io.IOException;

import cn.agree.bean.Hero;
import cn.agree.bean.Monster;

public class MonsterCell extends Cell{

	private static final long serialVersionUID = 1L;
	
	public MonsterCell(){
		super();
	}
	
	public MonsterCell(int x, int y, String image, int subX, int subY, Monster monster) throws IOException {
		super(x, y, image, subX, subY);
		this.obj = monster;
	}
	
	@Override
	public void moveUp() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handle(Hero hero, Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof Monster)){
			return false;
		}
		Monster monster = (Monster) obj;
		int damage1 = hero.getAttack()-monster.getDefence();
		int damage2 = monster.getAttack()-hero.getDefence();
		if(damage1<=0){
			//英雄攻击不足攻击怪物
			return false;
		}
		while(true){
			//英雄先手，怪物掉血
			monster.setLife(monster.getLife()-damage1);
			if(monster.getLife()<=0){
				//怪物死亡
				hero.setMoney(hero.getMoney()+monster.getMoney());
				return true;
			}
			//怪物后手，英雄掉血
			if(damage2>0){
				//若怪物攻击不足，英雄不掉血
				hero.setLife(hero.getLife()-damage2);
				if(hero.getLife()<=0){
					//英雄死亡
					return false;
				}
			}
		}
	}

}
