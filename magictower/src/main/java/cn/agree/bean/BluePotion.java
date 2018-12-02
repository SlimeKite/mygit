package cn.agree.bean;

import java.util.HashMap;
import java.util.Map;

public class BluePotion extends Prop{
	private static final String NAME = "蓝药水";//道具名称
	private static final int ATTACT = 0;//攻击数值
	private static final int DEFENCE = 0;//防御数值
	private static final int LIFE = 50;//生命数值
	
	public BluePotion(){
		this.name = NAME;
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("attack", ATTACT);
		map.put("defence", DEFENCE);
		map.put("life", LIFE);
		this.value = map;
	}
}
