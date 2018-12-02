package cn.agree.start;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.DocumentException;

import cn.agree.bean.Bean;
import cn.agree.bean.BlueCrystal;
import cn.agree.bean.BluePotion;
import cn.agree.bean.BlueWizard;
import cn.agree.bean.Hero;
import cn.agree.bean.MiddleSkull;
import cn.agree.bean.MiddleSlime;
import cn.agree.bean.RedCrystal;
import cn.agree.bean.RedPotion;
import cn.agree.bean.SmallBat;
import cn.agree.bean.SmallSkull;
import cn.agree.bean.SmallSlime;
import cn.agree.file.FileController;
import cn.agree.roll.Cell;
import cn.agree.roll.CommonCell;
import cn.agree.roll.HeroCell;
import cn.agree.roll.MonsterCell;
import cn.agree.roll.PropCell;
import cn.agree.roll.TowerMap;

public class MagicTower {
	//设置各组件大小
	public static final int WIDTH = 800;//界面宽
	public static final int HEIGHT = 600;//界面高
	public static final int INFO_WIDHT = 250;//英雄信息界面宽
	private static final int TITLE_WIDTH = 450;//标题宽
	private static final int TITLE_HEIGHT = 100;//标题高
	private static final int NAME_WIDTH = 300;//名字宽
	private static final int NAME_HEIGHT = 30;//名字高
	
	private static JFrame jFrame;
	private static JLayeredPane jLayeredPane;
	private static JButton newButton;
	private static JButton oldButton;
	private static JTextField roleName;
	private static JButton sure;
	private static JLabel tips1;
	private static JLabel tips2;
	private static JDialog jDialog;
	
	/**
	 * 角色信息
	 * ---姓名，生命，攻击，防御，金币，黄钥匙，蓝钥匙，红钥匙
	 */
	private static JLabel[] jLabels;
	
	private Hero hero;//英雄
	private String name;//英雄名称
	private Cell heroCell;//英雄容器
	private TowerMap t = new TowerMap();//地图
	private Cell[][] cells = new Cell[15][15];//地图
	
	private AudioClip audioClip;
	
	static{
		jFrame = new JFrame("魔塔");
		jLayeredPane = jFrame.getLayeredPane();
		newButton = new JButton("新的开始");
		oldButton = new JButton("旧的旅程");
		roleName = new JTextField("理查德");//角色名称创建
		sure = new JButton("确定");//确认关闭按钮
		tips1 = new JLabel("姓名不能为空!");//提示信息
		tips2 = new JLabel("姓名最大五位!");//提示信息
		jDialog = new JDialog(jFrame, "", true);//创建子窗口
		jLabels = new JLabel[8];//姓名，生命，攻击，防御，金币，黄钥匙，蓝钥匙，红钥匙
	}
	
	public static void main(String[] args) {
		//1.画首页
		paintMain();
		//2.监听开始
		new MagicTower().setListenner();
	}
	
	/**
	 * 画初始面板
	 * @param game
	 */
	private static void paintMain() {
		// 创建顶层容器
		jFrame.setSize(WIDTH, HEIGHT);//设置面板大小
		jFrame.setResizable(false);//设置窗口大小不可变
		jFrame.setAlwaysOnTop(true);//设置窗口在最前端显示
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭按钮
		jFrame.setLocationRelativeTo(null);//面板居中
		
		// 加载背景图片
		String imagePath = MagicTower.class.getClassLoader().getResource("./image/backgroup.jpg").getPath();
		ImageIcon imageIcon = new ImageIcon(imagePath);
		JLabel jLabel = new JLabel(imageIcon);
		jLabel.setBounds(0, 0, WIDTH, HEIGHT);
		jLayeredPane.add(jLabel, JLayeredPane.PALETTE_LAYER);//设置在第二层
		
		// 画标题
		JLabel title = new JLabel("魔塔50层");//设置标题名称
		title.setBounds((WIDTH-TITLE_WIDTH)/2, HEIGHT/10, TITLE_WIDTH, TITLE_HEIGHT);//固定标题位置
		title.setForeground(Color.RED);//设置字体颜色
		title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));//设置字体大小、样式
		jLayeredPane.add(title, JLayeredPane.MODAL_LAYER);//设置在第三层
		
		// 写作者，方法同画标题
		JLabel author = new JLabel("made by lizhenyu");
		author.setBounds(WIDTH/2, HEIGHT/10 + TITLE_HEIGHT, NAME_WIDTH, NAME_HEIGHT);
		author.setForeground(Color.BLUE);
		author.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 30));
		jLayeredPane.add(author, JLayeredPane.MODAL_LAYER);
		
		// 创建新游戏按钮
		newButton.setBounds(WIDTH/4, HEIGHT/3, WIDTH/2, HEIGHT/8);
		newButton.setBackground(Color.LIGHT_GRAY);
		newButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		jLayeredPane.add(newButton,JLayeredPane.MODAL_LAYER);
		
		// 创建旧存档按钮
		oldButton.setBounds(WIDTH/4, HEIGHT/3*2, WIDTH/2, HEIGHT/8);
		oldButton.setBackground(Color.LIGHT_GRAY);
		oldButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		jLayeredPane.add(oldButton,JLayeredPane.MODAL_LAYER);

		jFrame.setVisible(true);//设置可见
	}
	
	/**
	 * 监听开始按钮
	 */
	private void setListenner() {
		newButton.addActionListener(new newButtonHandler());
		oldButton.addActionListener(new oldButtonHandler());
	}
	
	/**
	 * 开始新游戏
	 * @author lizhenyu
	 *
	 */
	private class newButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//判断是否有存档存在
			File file = new File("./src/main/resources/oldData");//读取oldData文件夹
			if(!file.exists()){
				file.mkdirs();//若旧存档文件夹不存在则创建
			}
			File files[] = file.listFiles();//获取oldData下所有的文件
			if(files != null && files.length > 0){
				//说明有旧存档
				int result = JOptionPane.showConfirmDialog(jFrame, 
						"确认放弃原存档吗？", "亲~请慎重选择", JOptionPane.YES_NO_OPTION);//YES--0  NO--1
				if(0 != result){
					return;
				}
			}
			
			FileController.clearOldData();//清空旧存档
			int width = WIDTH/4*3;
			int height = HEIGHT/3;
			jDialog.setSize(width, height);//设置子窗口大小
			jDialog.setResizable(false);//设置子窗口不可编辑大小
			jDialog.setLocationRelativeTo(null);//设置子窗口居中
			jDialog.setUndecorated(true);
			
			JLabel info = new JLabel("大侠高名：");
			info.setBounds(width/2-200, height/2-50, 200, 50);
			info.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
			jDialog.getLayeredPane().add(info, JLayeredPane.MODAL_LAYER);
			
			roleName.setBounds(width/2, height/2-55, 200, 50);
			roleName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
			jDialog.getLayeredPane().add(roleName, JLayeredPane.MODAL_LAYER);
			
			tips1.setBounds(width/2-75, height/2, 150, 30);
			tips1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			tips1.setForeground(Color.BLUE);
			tips1.setVisible(false);
			jDialog.getLayeredPane().add(tips1, JLayeredPane.MODAL_LAYER);
			
			tips2.setBounds(width/2-75, height/2, 150, 30);
			tips2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			tips2.setForeground(Color.BLUE);
			tips2.setVisible(false);
			jDialog.getLayeredPane().add(tips2, JLayeredPane.MODAL_LAYER);
			
			sure.setBounds(width/2-50, height/2+35, 100, 30);
			sure.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
			jDialog.getLayeredPane().add(sure, JLayeredPane.MODAL_LAYER);

			sure.addActionListener(new sureButtonHandler());
			
			jDialog.setVisible(true);//设置子窗口可见
			
		}
		
	}
	
	/**
	 * 创建角色确认按钮
	 * @author lizhenyu
	 *
	 */
	private class sureButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			name = roleName.getText();
			if(name == null || name.trim().isEmpty()){
				tips2.setVisible(false);
				tips1.setVisible(true);
				return;
			}else if(name.length()>5){
				tips1.setVisible(false);
				tips2.setVisible(true);
				return;
			}
			jDialog.dispose();//关闭子窗口
			startGame("newData");
		}
		
	}
	
	/**
	 * 继续旧存档
	 * @author lizhenyu
	 *
	 */
	private class oldButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			File file = new File("./src/main/resources/oldData");//读取oldData文件夹
			if(!file.exists()){
				file.mkdirs();//若旧存档文件夹不存在则创建
			}
			File files[] = file.listFiles();//获取oldData下所有的文件
			if(files != null && files.length > 0){
				//说明有旧存档
				startGame("oldData");
			}else{
				//说明没有旧存档
				JOptionPane.showMessageDialog(jFrame, "没有存档！", "", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	/**
	 * 开始游戏
	 * @param path 存档路径:oldData---旧存档   newData---新游戏
	 */
	private void startGame(String path) {
		// 清空进游戏界面
		jLayeredPane.removeAll();
		jLayeredPane.repaint();
		
		try {
			// 初始化游戏
			initGame(path);
			// 监听英雄容器
			if(jFrame.getKeyListeners() == null || jFrame.getKeyListeners().length == 0){
				jFrame.addKeyListener(new heroKeyListenner());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 初始化游戏
	 * @param path 存档路径:oldData---旧存档   newData---新游戏
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private void initGame(String path) throws IOException, DocumentException {
		if("newData".equals(path)){
			// 如果是新开游戏，把newData下的存档放置到oldData与cache中
			FileController.setNewData();
			hero = FileController.readHero();
			hero.setName(name);
		}else if("oldData".equals(path)){
			// 如果是读取存档，把oldData下的存档放置到cache中
			FileController.setOldData();
			hero = FileController.readHero();
		}
		
		System.out.println(hero.toString());

		jFrame.setBackground(Color.BLACK);
		
		// 画英雄信息
		paintHeroInfo();
		
		// 画游戏主界面
		paintGame();
		
		// 添加游戏背景音乐
		addMusic("战斗-魔塔核心.wav",true);
		
	}
	
	/**
	 * 画英雄信息
	 * @throws IOException 
	 */
	private void paintHeroInfo() throws IOException {
		//画角色信息的底
		JPanel jpanel = new JPanel();
		jpanel.setBounds(0, 0, 250, 600);
		jpanel.setBackground(Color.CYAN);
		jLayeredPane.add(jpanel, JLayeredPane.DEFAULT_LAYER);
		
		JLabel base1 = new JLabel();
		base1.setBounds(0, 0, 250, 180);
		base1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5, true));
		jLayeredPane.add(base1, JLayeredPane.PALETTE_LAYER);
		
		//画角色名称
		jLabels[0] = new JLabel("英雄：["+hero.getName()+"]",JLabel.CENTER);
		jLabels[0].setBounds(0,0,250,50);
		jLabels[0].setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		jLayeredPane.add(jLabels[0], JLayeredPane.MODAL_LAYER);
		
		//画角色基本信息
		String[] beanInfo = new String[]{"生命：","攻击力：","防御力：","金钱："};
		int[] beanResult = new int[]{hero.getLife(),hero.getAttack(),hero.getDefence(),hero.getMoney()};
		for(int i = 0,len = beanInfo.length;i<len;i++){
			JLabel jLabel = new JLabel(beanInfo[i]);
			jLabel.setBounds(50,50+i*30,100,30);
			jLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			jLabels[i+1] = new JLabel(Integer.toString(beanResult[i]));
			jLabels[i+1].setBounds(150,50+i*30,150,30);
			jLabels[i+1].setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			jLayeredPane.add(jLabel, JLayeredPane.MODAL_LAYER);
			jLayeredPane.add(jLabels[i+1], JLayeredPane.MODAL_LAYER);
		}
		
		JLabel base2 = new JLabel();
		base2.setBounds(0, 190, 250, 150);
		base2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5, true));
		jLayeredPane.add(base2, JLayeredPane.PALETTE_LAYER);
		
		//画钥匙
		String[] keyType = new String[]{"yellowKey","blueKey","redKey"};
		int[] keyResult = new int[]{hero.getYellowKeyNum(),hero.getBlueKeyNum(),hero.getRedKeyNum()};
		for(int i = 0,len=keyType.length;i<len;i++){
			String path = MagicTower.class.getClassLoader().getResource("./image/"+keyType[i]+".png").getPath();
			BufferedImage bi = ImageIO.read(new File(path));
			ImageIcon yellowKey = new ImageIcon(bi);
			JLabel jLabel = new JLabel(yellowKey,JLabel.CENTER);
			jLabel.setBounds(25,190+i*50,100,50);
			jLabels[i+5] = new JLabel(Integer.toString(keyResult[i]),JLabel.CENTER);
			jLabels[i+5].setBounds(100,190+i*50,150,50);
			jLabels[i+5].setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
			jLayeredPane.add(jLabel, JLayeredPane.MODAL_LAYER);
			jLayeredPane.add(jLabels[i+5], JLayeredPane.MODAL_LAYER);
		}
		
		JLabel base3 = new JLabel();
		base3.setBounds(0, 350, 250, 150);
		base3.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5, true));
		jLayeredPane.add(base3, JLayeredPane.PALETTE_LAYER);

		//TODO jLayeredPane添加button按钮，英雄的监听事件失效
		//TODO 问题已解决，button按钮会聚焦，需要将焦点重新放置到主面板上
		JButton saveButton = new JButton("存档");
		saveButton.setBounds(30, 520, 80, 30);
		saveButton.setBackground(Color.PINK);
		saveButton.addActionListener(new saveButtonHandler());
		jLayeredPane.add(saveButton, JLayeredPane.MODAL_LAYER);
		
		JButton loadButton = new JButton("读取");
		loadButton.setBounds(140, 520, 80, 30);
		loadButton.setBackground(Color.PINK);
		loadButton.addActionListener(new loadButtonHandler());
		jLayeredPane.add(loadButton, JLayeredPane.MODAL_LAYER);
		
		jFrame.requestFocus();
	}
	
	/**
	 * 画游戏界面
	 * @throws IOException
	 */
	private void paintGame() throws IOException {
		FileController.readMap(t, hero.getMap());
		String[][] strs = t.getMaps();
		Component[] coms = jLayeredPane.getComponents();
		for(Component com : coms){
			if(com instanceof Cell){
				jLayeredPane.remove(com);
			}
		}
		//i为行，j为列
		for(int i = 0 ; i < 13 ; i++){
			for(int j = 0 ; j < 13 ; j++){
				if(Bean.WALL.equals(strs[i][j].trim())){
					cells[i][j] = new CommonCell(j, i, "wall.png", 1, 0);
					jLayeredPane.add(cells[i][j], JLayeredPane.PALETTE_LAYER);
				}else{
					cells[i][j] = new CommonCell(j, i, "wall.png", 2, 0);
					jLayeredPane.add(cells[i][j], JLayeredPane.PALETTE_LAYER);
					switch(strs[i][j].trim()){
						case Bean.HERO:
							heroCell = new HeroCell(j, i, "hero.png", 2, 0);
							jLayeredPane.add(heroCell, JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BLUE_DOOR:
							cells[i][j] = new CommonCell(j, i, "door.png", 1, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.RED_DOOR:
							cells[i][j] = new CommonCell(j, i, "door.png", 2, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.YELLOW_DOOR:
							cells[i][j] = new CommonCell(j, i, "door.png", 0, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BLUE_KEY:
							cells[i][j] = new CommonCell(j, i, "key.png", 1, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.RED_KEY:
							cells[i][j] = new CommonCell(j, i, "key.png", 2, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.YELLOW_KEY:
							cells[i][j] = new CommonCell(j, i, "key.png", 0, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.STAIRS1:
							cells[i][j] = new CommonCell(j, i, "stairs1.png");
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.STAIRS2:
							cells[i][j] = new CommonCell(j, i, "stairs2.png");
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.RED_POTION:
							cells[i][j] = new PropCell(j, i, "potion.png", 0, 0, new RedPotion());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BLUE_POTION:
							cells[i][j] = new PropCell(j, i, "potion.png", 1, 0, new BluePotion());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.RED_CRYSTAL:
							cells[i][j] = new PropCell(j, i, "crystal.png", 0, 0, new RedCrystal());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BLUE_CRYSTAL:
							cells[i][j] = new PropCell(j, i, "crystal.png", 1, 0, new BlueCrystal());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.RED_WIZARD:
							cells[i][j] = new MonsterCell(j, i, "door.png", 0, 1, new SmallSlime());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BLUE_WIZARD:
							cells[i][j] = new MonsterCell(j, i, "wizard.png", 0, 0, new BlueWizard());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.SMALL_SLIME:
							cells[i][j] = new MonsterCell(j, i, "slime.png", 0, 0, new SmallSlime());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.MIDDLE_SLIME:
							cells[i][j] = new MonsterCell(j, i, "slime.png", 0, 1, new MiddleSlime());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BIG_SLIME:
							cells[i][j] = new MonsterCell(j, i, "slime.png", 0, 3, new SmallSlime());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.SMALL_SKULL:
							cells[i][j] = new MonsterCell(j, i, "skull.png", 0, 0, new SmallSkull());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.MIDDLE_SKULL:
							cells[i][j] = new MonsterCell(j, i, "skull.png", 0, 1, new MiddleSkull());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.BIG_SKULL:
							cells[i][j] = new MonsterCell(j, i, "skull.png", 0, 4, new SmallSlime());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.SMALL_BAT:
							cells[i][j] = new MonsterCell(j, i, "bat.png", 0, 0, new SmallBat());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.MIDDLE_BAT:
							cells[i][j] = new MonsterCell(j, i, "bat.png", 0, 1, new SmallSlime());
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.WARD_DOOR:
							cells[i][j] = new CommonCell(j, i, "wall.png", 3, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.MIDDLE_SOLDIER:
							cells[i][j] = new CommonCell(j, i, "soldier.png", 0, 1);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.GOD:
							cells[i][j] = new CommonCell(j, i, "npc.png", 0, 0);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.TRADER:
							cells[i][j] = new CommonCell(j, i, "npc.png", 0, 1);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
						case Bean.THIEF:
							cells[i][j] = new CommonCell(j, i, "npc.png", 0, 2);
							jLayeredPane.add(cells[i][j], JLayeredPane.MODAL_LAYER);
							break;
					}
				}
			}
		}
	}
	
	/**
	 * 添加音乐
	 * @param name
	 * @param flag 是否循环
	 * @throws MalformedURLException
	 */
	private void addMusic(String name,Boolean flag) throws MalformedURLException {
		String path = "./src/main/resources/music/"+name;
		File file = new File(path);
		URI uri = file.toURI();
		URL url = uri.toURL();
		audioClip = Applet.newAudioClip(url);
		if(flag){
			audioClip.loop();
		}else{
			audioClip.play();
		}
	}
	
	private class heroKeyListenner implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			try {
				Rectangle rec = heroCell.getBounds();
				int i = (int) ((rec.getY() - 35)/heroCell.getHeight());
				int j = (int) ((rec.getX() - 285)/heroCell.getWidth());
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						if(deal(cells[i-1][j])){
							return;
						}
						t.setMap(i-1, j, Bean.HERO);
						t.setMap(i, j, Bean.FLOOR);
						heroCell.moveUp();
						break;
					case KeyEvent.VK_DOWN:
						if(deal(cells[i+1][j])){
							return;
						}
						t.setMap(i+1, j, Bean.HERO);
						t.setMap(i, j, Bean.FLOOR);
						heroCell.moveDown();
						break;
					case KeyEvent.VK_LEFT:
						if(deal(cells[i][j-1])){
							return;
						}
						t.setMap(i, j-1, Bean.HERO);
						t.setMap(i, j, Bean.FLOOR);
						heroCell.moveLeft();
						break;
					case KeyEvent.VK_RIGHT:
						if(deal(cells[i][j+1])){
							return;
						}
						t.setMap(i, j+1, Bean.HERO);
						t.setMap(i, j, Bean.FLOOR);
						heroCell.moveRight();
						break;
					default:
						break;
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

		private boolean deal(Cell cell) throws IOException {
			String image = cell.getImage();
			int id = cell.getId();
			int num;
			switch (image) {
				case "wall.png":
					if(id == 2){
						return false;
					}
					return true;
				case "stairs1.png":
					// 保存当前层数数据到缓存中
					FileController.cacheData(t, hero.getMap());
					
					hero.setMap(hero.getMap()+1);
					
					// 重画游戏主界面
					paintGame();
					
					return true;
				case "stairs2.png":
					// 保存当前层数数据到缓存中
					FileController.cacheData(t, hero.getMap());

					hero.setMap(hero.getMap()-1);
					
					// 重画游戏主界面
					paintGame();
					
					return true;
				case "key.png":
					if(id == 0){
						//黄钥匙
						num = Integer.parseInt(jLabels[5].getText());
						jLabels[5].setText(Integer.toString(num+1));
						hero.setYellowKeyNum(hero.getYellowKeyNum()+1);
					}else if(id == 1){
						//蓝钥匙
						num = Integer.parseInt(jLabels[6].getText());
						jLabels[6].setText(Integer.toString(num+1));
						hero.setBlueKeyNum(hero.getBlueKeyNum()+1);
					}else if(id == 2){
						//红钥匙
						num = Integer.parseInt(jLabels[7].getText());
						jLabels[7].setText(Integer.toString(num+1));
						hero.setRedKeyNum(hero.getRedKeyNum()+1);
					}
					cell.setImage("wall.png");
					cell.setId(2);
					jLayeredPane.remove(cell);
					return false;
				case "door.png":
					if(id == 0){
						//黄门
						if(hero.getYellowKeyNum()>0){
							num = Integer.parseInt(jLabels[5].getText());
							jLabels[5].setText(Integer.toString(num-1));
							hero.setYellowKeyNum(hero.getYellowKeyNum()-1);
							cell.setImage("wall.png");
							cell.setId(2);
							jLayeredPane.remove(cell);
							return false;
						}
					}else if(id == 1){
						//蓝门
						if(hero.getBlueKeyNum()>0){
							num = Integer.parseInt(jLabels[6].getText());
							jLabels[6].setText(Integer.toString(num-1));
							hero.setBlueKeyNum(hero.getBlueKeyNum()-1);
							cell.setImage("wall.png");
							cell.setId(2);
							jLayeredPane.remove(cell);
							return false;
						}
					}else if(id == 2){
						//红门
						if(hero.getRedKeyNum()>0){
							num = Integer.parseInt(jLabels[7].getText());
							jLabels[7].setText(Integer.toString(num-1));
							hero.setRedKeyNum(hero.getRedKeyNum()-1);
							cell.setImage("wall.png");
							cell.setId(2);
							jLayeredPane.remove(cell);
							return false;
						}
					}
					return true;
				default:
					//其它的另作处理
					if(cell instanceof MonsterCell){
						//怪物
						if(cell.handle(hero, cell.getObj())){
							jLabels[1].setText(Integer.toString(hero.getLife()));
							jLabels[4].setText(Integer.toString(hero.getMoney()));
							cell.setImage("wall.png");
							cell.setId(2);
							jLayeredPane.remove(cell);
							addMusic("Damage2.wav",false);
							return false;
						}
						if(hero.getLife()<0){
							gameover();
						}else{
							System.out.println("攻击不足");
						}
						return true;
					}else if(cell instanceof PropCell){
						//道具
						if(cell.handle(hero, cell.getObj())){
							jLabels[1].setText(Integer.toString(hero.getLife()));
							jLabels[2].setText(Integer.toString(hero.getAttack()));
							jLabels[3].setText(Integer.toString(hero.getDefence()));
							cell.setImage("wall.png");
							cell.setId(2);
							jLayeredPane.remove(cell);
							return false;
						}
						return true;
					}else{
						return true;
					}
			}
		}
		
		private void gameover() {
			jDialog = new JDialog(jFrame, "", true);
			jDialog.setSize(400, 200);//设置子窗口大小
			jDialog.setResizable(false);//设置子窗口不可编辑大小
			jDialog.setLocationRelativeTo(null);//设置子窗口居中
			jDialog.setUndecorated(true);//去掉子窗口的边框
			jDialog.setBackground(Color.GREEN);
			
			JLayeredPane j = jDialog.getLayeredPane();
			
			JLabel info = new JLabel("您的英雄已死亡，请选择",JLabel.CENTER);
			info.setBounds(0, 0, 400, 100);
			info.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
			j.add(info,JLayeredPane.DEFAULT_LAYER);
			
			JButton yes = new JButton("重新开始");
			yes.setBounds(50, 130, 100, 40);
			yes.setBackground(Color.LIGHT_GRAY);
			yes.addActionListener(new yesButtonHandler());
			j.add(yes,JLayeredPane.DEFAULT_LAYER);
			
			JButton no = new JButton("读取存档");
			no.setBounds(250, 130, 100, 40);
			no.setBackground(Color.LIGHT_GRAY);
			no.addActionListener(new noButtonHandler());
			j.add(no,JLayeredPane.DEFAULT_LAYER);
			
			jDialog.setVisible(true);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {

		}
		
	}
	
	private class yesButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jDialog.dispose();
			audioClip.stop();
			startGame("newData");
		}
		
	}
	
	private class noButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			jDialog.dispose();
			audioClip.stop();
			if(FileController.hasFile("oldData")){
				//说明有旧存档
				startGame("oldData");
			}else{
				//说明没有旧存档
				JOptionPane.showMessageDialog(jFrame, "没有存档！", "", JOptionPane.INFORMATION_MESSAGE);
				startGame("newData");
			}
		}
		
	}
	
	private class saveButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Timer timer = new Timer();
				FileController.cacheData(t, hero.getMap());
				FileController.saveData(hero);
				timer.schedule(new TimerTask() {
					public void run() {
						JOptionPane.showMessageDialog(jLayeredPane, "保存成功！");
					}
				}, 1000);
				jFrame.requestFocus();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private class loadButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(FileController.hasFile("oldData")){
				//说明有旧存档
				audioClip.stop();
				startGame("oldData");
			}else{
				//说明没有旧存档
				JOptionPane.showMessageDialog(jFrame, "没有存档！", "", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
}
