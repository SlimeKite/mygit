package cn.agree.roll;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cn.agree.bean.Hero;

public abstract class Cell extends JLabel{
	private static final long serialVersionUID = 1L;
	
	protected int xx;//容器的横坐标
	protected int yy;//容器的纵坐标
	protected String image;//容器存储的图片
	protected int width;//图片宽
	protected int height;//图片高
	protected int id;//图片位置
	protected Object obj;//怪物/道具
	
	public Cell() {
		
	}
	
	public Cell(int x, int y, String image) throws IOException{
		super();
		this.xx = x;
		this.yy = y;
		this.image = image;
		this.id = 0;
		init();
	}
	
	public Cell(int x, int y, String image, int subX, int subY) throws IOException {
		super();
		this.xx = x;
		this.yy = y;
		this.image = image;
		this.id = subX + subY*4;
		init(subX, subY);
	}
	
	private void init() throws IOException{
		String path = Cell.class.getClassLoader().getResource("./image").getPath()+"/"+this.image;
		BufferedImage bi = ImageIO.read(new File(path));
		width = bi.getWidth();
		height = bi.getHeight();
		ImageIcon imageIcon = new ImageIcon(bi);
		this.setIcon(imageIcon);
		this.setSize(width, height);//设置单元格大小
		//TODO  问题：传入的参数x与y，传入时就乘图片大小可以显示，只传入下标在Cell类乘图片大小无效！
		//TODO  已解决---父类JComponent中有x与y变量导致错误？？？
		this.setBounds(this.xx*width+285, this.yy*height+40, width, height);
	}

	private void init(int subX, int subY) throws IOException{
		String path = Cell.class.getClassLoader().getResource("./image").getPath()+"/"+this.image;
		BufferedImage bi = ImageIO.read(new File(path));
		width = bi.getWidth() / 4;
		height = bi.getHeight() / 4;
		bi = bi.getSubimage(width*subX, height*subY, width, height);
		ImageIcon imageIcon = new ImageIcon(bi);
		this.setIcon(imageIcon);
		this.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());//设置单元格大小
		//TODO  问题：传入的参数x与y，传入时就乘图片大小可以显示，只传入下标在Cell类乘图片大小无效！
		//TODO  已解决---父类JComponent中有x与y变量导致错误？？？
		this.setBounds(this.xx*width+285, this.yy*height+40, width, height);
	}

	public int getXx() {
		return xx;
	}

	public void setXx(int xx) {
		this.xx = xx;
	}

	public int getYy() {
		return yy;
	}

	public void setYy(int yy) {
		this.yy = yy;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + xx;
		result = prime * result + yy;
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
		Cell other = (Cell) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (xx != other.xx)
			return false;
		if (yy != other.yy)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [xx=" + xx + ", yy=" + yy + ", image=" + image + "]";
	}
	
	public abstract void moveUp() throws IOException;
	
	public abstract void moveDown() throws IOException;
	
	public abstract void moveLeft() throws IOException;
	
	public abstract void moveRight() throws IOException;
	
	public abstract boolean handle(Hero hero, Object obj);

}
