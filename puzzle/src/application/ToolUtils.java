package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolUtils {
	/**
	 * 低级难度
	 */
	public static final int LOWER = 3;
	/**
	 * 中级难度
	 */
	public static final int MIDDLE = 4;
	/**
	 * 高级难度
	 */
	public static final int HIGHER = 5;
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	/**
	 * 获取可用的数组
	 * @param level
	 * @return
	 */
	public static int[] getArray(int level) {
		int[] array = null;
		while(true) {
			array = count(level*level);
			if(check(level,array)) {
				break;
			}
		}
		return array;
	}
	
	/**
	 * 打乱数组
	 * @param length
	 * @return
	 */
	private static int[] count(int length) {
		int[] array = new int[length];
		for(int i = 0 ; i < length ;) {
			boolean flag = true;
			int ran = (int) (Math.random()*length);
			for(int j = 0 ; j < i ; j++) {
				if(ran == array[j]) {
					flag = false;
				}
			}
			if(flag) {
				array[i] = ran;
				i++;
			}
		}
		return array;
	}
	
	/**
	 * 判断数组是否符合拼图逆序数
	 * @param array
	 * @return
	 */
	private static boolean check(int level, int[] array) {
		int num = 0;
		int index = 0;
		for(int i = 0, len = array.length ; i < len ; i++) {
			for(int j = i+1 ; j < len ; j++) {
				if(array[i] == 0) {
					index = i;
				}
				if(array[i] > array[j]) {
					num++;
				}
			}
		}
		return (index/level+index%level+num)%2 == 0;
	}
	
	/**
	 * 取到裁剪出来的图片
	 * @param level
	 * @return
	 */
	public static ImageView[] getAllImage(int level, String path) {
		try {
			//设置每个ImageView的长宽
			int length = WIDTH/level;
			//定义对应等级数量的图片容器
			ImageView[] images = new ImageView[level*level];
			//取得图片对象
			File file = new File(path);
			Image image = new Image(new FileInputStream(file));
			//取得图片裁剪后的长宽
			double width = image.getWidth()/level;
			double height = image.getHeight()/level;
			
			for(int r = 0 ; r < level ; r++) {
				for(int c = 0 ; c < level ; c++) {
					//取得容器下标
					int index = r*level+c;
					//为容器显示特定位置(相当于截取图片)
					images[index] = new ImageView();
					images[index].setFitWidth(length);
					images[index].setFitHeight(length);
					images[index].setViewport(new Rectangle2D(c*width, r*height, width, height));
					images[index].setImage(image);
				}
			}
			//设定第一个图片为空
			images[0].setImage(null);
			return images;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 转化定时器的时间格式
	 * @param timer
	 * @return
	 */
	public static String transformTimer(int timer) {
		String result = Integer.toString(timer);
		if(result.length() == 1) {
			return "0"+result;
		}
		return result;
	}
	
	/**
	 * 取得新的前十排行榜
	 * @param num
	 * @param record
	 * @return 返回排行榜数据与新纪录的排名
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getRecord(int num, String record) {
		String level = "";
		if(LOWER == num) {
			level = "lower";
		}else if(MIDDLE == num) {
			level = "middle";
		}else if(HIGHER == num) {
			level = "higher";
		}
		Properties properties = new Properties();
		File file = new File("record/record.properties");
		try {
			properties.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			Map map = new HashMap<>();
			
			//更新排行榜
			for(int i = 1 ; i < 11 ; i++) {
				if(compareRecord(record, properties.getProperty(level+"-"+i))) {
					map.put("index", i);
					for(int j = 10 ; j > i ; j--) {
						properties.setProperty(level+"-"+j, properties.getProperty(level+"-"+(j-1)));
					}
					properties.setProperty(level+"-"+i, record);
					properties.store(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"),null);
					break;
				}
			}
			
			//读取新排行榜
			String[] strs = new String[10];
			for(int i = 1 ; i < 11 ; i++) {
				strs[i-1] = properties.getProperty(level+"-"+i);
			}
			map.put("record", strs);
			
			return map;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 用新纪录与排行榜比较，超越则为true
	 * @return
	 */
	private static boolean compareRecord(String newRecord, String oldRecord) {
		try {
			String[] newRecords = getRecord(newRecord);
			String[] oldRecords = getRecord(oldRecord);
			for (int i = 0; i < 3; i++) {
				if (compare(newRecords[i], oldRecords[i]) == 0) {
					continue;
				} else if (compare(newRecords[i], oldRecords[i]) == 1) {
					return false;
				} else if (compare(newRecords[i], oldRecords[i]) == -1) {
					return true;
				} else {
					return false;
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 取得时间的时分秒
	 * @param record
	 * @return
	 */
	private static String[] getRecord(String record) {
		String[] strs = new String[3];
		System.out.println(record);
		strs[0] = record.split("时")[0];
		strs[1] = record.split("时")[1].split("分")[0];
		strs[2] = record.split("时")[1].split("分")[1].split("秒")[0];
		return strs;
	}
	
	/**
	 * 比较两个字符串的大小
	 * 1大于2返回1
	 * 1等于2返回0
	 * 1小于2返回-1
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static Integer compare(String str1, String str2) {
		if(Integer.parseInt(str1)>Integer.parseInt(str2)) {
			return 1;
		}else if(Integer.parseInt(str1)==Integer.parseInt(str2)) {
			return 0;
		}else if(Integer.parseInt(str1)<Integer.parseInt(str2)) {
			return -1;
		}else {
			return null;
		}
	}
}
