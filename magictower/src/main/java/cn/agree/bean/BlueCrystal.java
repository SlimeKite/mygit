package cn.agree.bean;

import java.util.HashMap;
import java.util.Map;

public class BlueCrystal extends Prop{
	private static final String NAME = "蓝水晶";//道具名称
	private static final int ATTACT = 0;//攻击数值
	private static final int DEFENCE = 1;//防御数值
	private static final int LIFE = 0;//生命数值
	
	public BlueCrystal(){
		this.name = NAME;
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("attack", ATTACT);
		map.put("defence", DEFENCE);
		map.put("life", LIFE);
		this.value = map;
	}
}
