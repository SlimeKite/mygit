package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Controller implements Initializable{
	
	private Main app;
	
	private static int FIRST = 200;
	private static int SECOND = 200;
	private static int THIRD = 200;
	
	private Integer flag = -1;//全局锁，-1--游戏未开始，0--游戏就绪，1--游戏开始
	
	private List<String> list = new ArrayList<String>();//所有图片的路径
	private String path;//图片地址
	
	private int level;//游戏等级
	private ImageView[] images;//拼图全容器
	private int[] array;//存放ImageView的位置
	
	private int h;//记录时间--小时
	private int m;//记录时间--分钟
	private int s;//记录时间--秒
	private Timer timer;//定时器
	
	private MediaPlayer player;//音乐播放器
	
	private Text[] resultInfo = new Text[2];//最终结果
	private Text[] recordName = new Text[10];//名次表示
	private Text[] recordScore = new Text[10];//前十记录
	private Text newRecord = new Text("New Record!");//新记录
	
	@FXML
	GridPane gridPane;
	@FXML
	ImageView image;
	@FXML
	Text hour;
	@FXML
	Text min;
	@FXML
	Text sec;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initNode();
		initMusic();
		initImage();
		replaceImg();
	}
	
	/**
	 * 初始化最终展示界面的组件属性
	 */
	private void initNode() {
		for(int i = 0 ; i < 2 ; i++) {
			resultInfo[i] = new Text();
			resultInfo[i].setFont(new Font(40));
			resultInfo[i].setFill(Color.GRAY);
			resultInfo[i].setWrappingWidth(ToolUtils.WIDTH);
			resultInfo[i].setTextAlignment(TextAlignment.CENTER);
		}
		String[] strs = new String[] {
				"第一名", "第二名", "第三名", "第四名", "第五名", 
				"第六名", "第七名", "第八名", "第九名", "第十名"
		};
		for(int i = 0 ; i < 10 ; i++) {
			recordName[i] = new Text(strs[i]);
			recordName[i].setFill(Color.RED);
			recordName[i].setFont(new Font(25));
			recordName[i].setStroke(Color.YELLOW);
			recordName[i].setStrokeType(StrokeType.OUTSIDE);
			recordName[i].setStrokeWidth(1);
			recordName[i].setTextAlignment(TextAlignment.CENTER);
			recordName[i].setWrappingWidth(FIRST);
			recordScore[i] = new Text();
			recordScore[i].setFill(Color.BLUE);
			recordScore[i].setFont(new Font(25));
			recordScore[i].setStroke(Color.WHITESMOKE);
			recordScore[i].setStrokeType(StrokeType.OUTSIDE);
			recordScore[i].setStrokeWidth(1);
			recordScore[i].setTextAlignment(TextAlignment.CENTER);
			recordScore[i].setWrappingWidth(SECOND);
		}
		newRecord.setFill(Color.YELLOW);
		newRecord.setFont(new Font(30));
		newRecord.setStroke(Color.BLUE);
		newRecord.setStrokeType(StrokeType.OUTSIDE);
		newRecord.setStrokeWidth(1);
		newRecord.setWrappingWidth(THIRD);
	}
	
	/**
	 * 初始化音乐播放器
	 */
	private void initMusic() {
		File music = new File("music/痛苦之村列瑟芬.mp3");
		URI uri = music.toURI();
		Media media = new Media(uri.toString());
		player = new MediaPlayer(media);
		player.setCycleCount(-1);
	}
	
	/**
	 * 初始化循环图片
	 */
	private void initImage() {
		File file = new File("image");
		File[] files = file.listFiles();
		for(File f : files) {
			list.add(f.getPath());
		}
	}
	
	public void setApp(Main app) {
		this.app = app;
	}
	
	/**
	 * 更换图片
	 */
	@FXML
	public void replaceImg() {
		try {
			flag = -1;//游戏未开始
			player.pause();//音乐暂停
			int index = (int)(Math.random()*(list.size()-1));
			list.add(list.get(index));
			list.remove(index);
			path = list.get(list.size()-1);
			Image img = new Image(new FileInputStream(new File(path)));
			image.setImage(img);
			clearGridPane();
			resetTimer();
			h = m = s = 0;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML
	public void lower() {
		System.out.println("开始一局低难度游戏");
		flag = 0;//游戏就绪
		player.pause();//音乐暂停
		level = ToolUtils.LOWER;
		handler(level);
	}
	
	@FXML
	public void middle() {
		System.out.println("开始一局中难度游戏");
		flag = 0;//游戏就绪
		player.pause();//音乐暂停
		level = ToolUtils.MIDDLE;
		handler(level);
	}
	
	@FXML
	public void higher() {
		System.out.println("开始一局高难度游戏");
		flag = 0;//游戏就绪
		player.pause();//音乐暂停
		level = ToolUtils.HIGHER;
		handler(level);
	}
	
	@FXML
	public void start() {
		if(flag == 0) {
			flag = 1;//打开游戏开关
			player.play();
			timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					if(++s == 60) {
						s = 0;
						if(++m == 60) {
							m = 0;
							if(++h == 24) {
								System.out.println("真菜");
								resetGridPane(false);
							}
						}
					}
					hour.setText(ToolUtils.transformTimer(h));
					min.setText(ToolUtils.transformTimer(m));
					sec.setText(ToolUtils.transformTimer(s));
				}
			}, 0, 1000);
		}
	}

	/**
	 * 当更换图片、更换难度、暂停游戏时，都应该执行此方法
	 */
	@FXML
	public void pause() {
		flag = 0;//关闭游戏开关
		player.pause();
		if(timer!=null) {
			timer.cancel();
		}
	}
	
	/**
	 * 退出游戏
	 */
	@FXML
	public void exit() {
		app.closeApp();
		resetTimer();
	}
	
	/**
	 * 生成拼图
	 * @param level
	 */
	private void handler(int level) {
		clearGridPane();
		resetTimer();
		h = m = s = 0;
		ColumnConstraints[] columns = new ColumnConstraints[level];
		RowConstraints[] rows = new RowConstraints[level];
		for(int i = 0 ; i < level ; i++) {
			columns[i] = new ColumnConstraints(ToolUtils.WIDTH/level);
			rows[i] = new RowConstraints(ToolUtils.HEIGHT/level);
		}
		
		gridPane.getColumnConstraints().addAll(columns);
		gridPane.getRowConstraints().addAll(rows);
		//取得裁剪到的图片，存放在容器中
		images = ToolUtils.getAllImage(level, path);
		//打乱容器的位置，开始拼图
		array = ToolUtils.getArray(level);
		for(int i = 0 ; i < level ; i++) {
			for(int j = 0 ; j < level ; j++) {
				gridPane.add(images[array[i*level+j]], j, i);
			}
		}
	}
	
	/**
	 * 清空拼图版面
	 */
	private void clearGridPane() {
		if(images != null) {
			//当开始另一局游戏时，要将界面清理
			gridPane.getChildren().removeAll(images);
			gridPane.getChildren().removeAll(recordName);
			gridPane.getChildren().removeAll(recordScore);
			gridPane.getChildren().removeAll(resultInfo);
			gridPane.getChildren().remove(newRecord);
			gridPane.getColumnConstraints().removeAll(gridPane.getColumnConstraints());
			gridPane.getRowConstraints().removeAll(gridPane.getRowConstraints());
		}
	}
	
	/**
	 * 点击实现图片移动
	 * @param event
	 */
	@FXML
	public void runGame(MouseEvent event) {
		if(flag == 1) {
			//只有游戏进行时才允许操作
			double xOffset = event.getSceneX() - 200;//事件源相对于拼图的x坐标
			double yOffset = event.getSceneY();//事件源相对于拼图的y坐标
			int length = ToolUtils.WIDTH/level;//ImageView的长度
			int x = (int) xOffset/length;//被点击ImageView在GridPane中的横向位置
			int y = (int) yOffset/length;//被点击ImageView在GridPane中的纵向位置
			int num = x + y * level;//被点击ImageView在GridPane中的下标
			int yy = GridPane.getRowIndex(images[0]);//空ImageView在GridPane中的纵向位置
			int xx = GridPane.getColumnIndex(images[0]);//空ImageView在GridPane中的横向位置
			int numm = xx + yy * level;//空ImageView在GridPane中的下标
			if(yy == y && xx - x == 1) {
				System.out.println("右");
			}else if(yy == y && xx - x == -1) {
				System.out.println("左");
			}else if(xx == x && yy - y == -1) {
				System.out.println("上");
			}else if(xx == x && yy - y == 1) {
				System.out.println("下");
			}else {
				return;
			}
			GridPane.setRowIndex(images[array[num]], yy);
			GridPane.setColumnIndex(images[array[num]], xx);
			GridPane.setRowIndex(images[0], y);
			GridPane.setColumnIndex(images[0], x);
			int index = array[num];
			array[num] = array[numm];
			array[numm] = index;
			if(success()) {
				//游戏成功，不允许点击拼图面板
				System.out.println("游戏成功");
				clearGridPane();
				resetGridPane(true);
				resetTimer();
				flag = -1;
				player.pause();
			}
		}
	}
	
	/**
	 * 判断游戏是否成功
	 * @return
	 */
	@SuppressWarnings("static-access")
	private boolean success() {
		for(int i = 0, len = level*level ; i < len ; i++) {
			int index = gridPane.getRowIndex(images[i]) * level + gridPane.getColumnIndex(images[i]);
			if(index != i) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 重置定时器
	 */
	private void resetTimer() {
		h = m = s = 0;
		hour.setText(ToolUtils.transformTimer(h));
		min.setText(ToolUtils.transformTimer(m));
		sec.setText(ToolUtils.transformTimer(s));
		if(timer != null) {
			timer.cancel();
		}
	}
	
	/**
	 * 生成成功/失败界面
	 */
	@SuppressWarnings("rawtypes")
	private void resetGridPane(boolean flag) {
		if(flag) {
			//游戏成功
			resultInfo[0].setText("游戏成功");
			resultInfo[1].setText("总用时:"+hour.getText()+"时"+min.getText()+"分"+sec.getText()+"秒");
		}else {
			//游戏失败
			resultInfo[0].setText("游戏失败");
			resultInfo[1].setText("能一天拼不出来的你是第一个😒");
		}
		ColumnConstraints[] columns = new ColumnConstraints[3];
		for(int i = 0, len = columns.length ; i < len ; i++) {
			columns[i] = new ColumnConstraints();
		}
		columns[0].setPrefWidth(FIRST);
		columns[1].setPrefWidth(SECOND);
		columns[2].setPrefWidth(THIRD);
		RowConstraints[] rows = new RowConstraints[12];
		for(int i = 0, len = rows.length ; i < len ; i++) {
			rows[i] = new RowConstraints();
			rows[i].setPrefHeight(50);
		}
		gridPane.getRowConstraints().addAll(rows);
		gridPane.getColumnConstraints().addAll(columns);
		gridPane.add(resultInfo[0], 0, 0);
		gridPane.add(resultInfo[1], 0, 1);
		GridPane.setColumnSpan(resultInfo[0], 3);
		GridPane.setColumnSpan(resultInfo[1], 3);
		Map map = ToolUtils.getRecord(level, hour.getText()+"时"+min.getText()+"分"+sec.getText()+"秒");
		if(map.get("index")!=null) {
			int index = (int)map.get("index");
			gridPane.add(newRecord, 2, 1+index);
		}
		String[] strs = (String[]) map.get("record");
		for(int i = 0, len = strs.length ; i < len ; i++) {
			recordScore[i].setText(strs[i]);
			gridPane.add(recordName[i], 0, 2+i);
			gridPane.add(recordScore[i], 1, 2+i);
		}
	}
}
