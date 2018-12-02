package cn.agree.roll;

import java.io.IOException;
import java.util.Map;

import cn.agree.bean.Hero;
import cn.agree.bean.Prop;

public class PropCell extends Cell{

private static final long serialVersionUID = 1L;
	
	public PropCell(){
		super();
	}
	
	public PropCell(int x, int y, String image, int subX, int subY, Prop prop) throws IOException {
		super(x, y, image, subX, subY);
		this.obj = prop;
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
		if(!(obj instanceof Prop)){
			return false;
		}
		Prop prop = (Prop)this.getObj();
		Map<String,Integer> map = prop.getValue();
		hero.setLife(hero.getLife()+map.get("life"));
		hero.setAttack(hero.getAttack()+map.get("attack"));
		hero.setDefence(hero.getDefence()+map.get("defence"));
		return true;
	}

}
