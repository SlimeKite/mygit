package cn.agree.roll;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cn.agree.bean.Hero;

public class HeroCell extends Cell{
	
	private static final long serialVersionUID = 1L;
	
	public HeroCell(){
		super();
	}
	
	public HeroCell(int x, int y, String image, int subX, int subY) throws IOException {
		super(x, y, image, subX, subY);
	}
	
	/**
	 * 向上移动容器
	 * @throws IOException 
	 */
	public void moveUp() throws IOException {
		Rectangle rec = this.getBounds();
		//向上移动
		this.setBounds(rec.x, rec.y - this.height, this.width, this.height);
		changeImage(0, 3, 1, 1);//只有一单位的图片会有改变
	}
	/**
	 * 向下移动容器
	 * @throws IOException 
	 */
	public void moveDown() throws IOException {
		Rectangle rec = this.getBounds();
		//向下移动
		this.setBounds(rec.x, rec.y + this.height, this.width, this.height);
		changeImage(0, 0, 1, 1);//只有一单位的图片会有改变
	}
	/**
	 * 向左移动容器
	 * @throws IOException 
	 */
	public void moveLeft() throws IOException {
		Rectangle rec = this.getBounds();
		//向左移动
		this.setBounds(rec.x - this.width, rec.y, this.width, this.height);
		changeImage(0, 1, 1, 1);//只有一单位的图片会有改变
	}
	/**
	 * 向右移动容器
	 * @throws IOException 
	 */
	public void moveRight() throws IOException {
		Rectangle rec = this.getBounds();
		//向右移动
		this.setBounds(rec.x + this.width, rec.y, this.width, this.height);
		changeImage(0, 2, 1, 1);//只有一单位的图片会有改变
	}
	/**
	 * 改变图片
	 * @param subX
	 * @param subY
	 * @param xLength
	 * @param yLength
	 * @throws IOException 
	 */
	private void changeImage(int subX, int subY, int xLength, int yLength) throws IOException {
		String path = Cell.class.getClassLoader().getResource("./image").getPath()+"/"+this.image;
		BufferedImage bi = ImageIO.read(new File(path));
		width = bi.getWidth() / 4;
		height = bi.getHeight() / 4;
		bi = bi.getSubimage(width*subX, height*subY, width*xLength, height*yLength);
		ImageIcon imageIcon = new ImageIcon(bi);
		this.setIcon(imageIcon);
	}

	@Override
	public boolean handle(Hero hero, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
}
