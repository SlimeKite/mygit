package cn.agree.roll;

import java.io.IOException;

import cn.agree.bean.Hero;

public class CommonCell extends Cell{

	private static final long serialVersionUID = 1L;

	public CommonCell(){
		super();
	}
	
	public CommonCell(int x, int y, String image) throws IOException{
		super(x, y, image);
	}
	
	public CommonCell(int x, int y, String image, int subX, int subY) throws IOException {
		super(x, y, image, subX, subY);
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
		return false;
	}

}
