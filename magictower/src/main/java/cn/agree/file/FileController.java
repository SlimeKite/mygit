package cn.agree.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.agree.bean.Hero;
import cn.agree.roll.TowerMap;

public class FileController {
	/**
	 * 清理旧存档
	 */
	public static void clearOldData() {
		File file = new File("./src/main/resources/oldData");//读取oldData文件夹
		File files[] = file.listFiles();//获取oldData下所有的文件
		for(File f : files){
			f.delete();
		}
	}
	/**
	 * 清空缓存文件
	 */
	private static void clearCache() {
		File file = new File("./src/main/resources/cacheData");//读取cacheData文件夹
		File files[] = file.listFiles();//获取cacheData下所有的文件
		for(File f : files){
			f.delete();
		}
	}
	/**
	 * 建立新存档
	 * @throws IOException 
	 */
	public static void setNewData() throws IOException {
		setData("newData");
	}
	
	/**
	 * 读取旧存档
	 * @throws IOException
	 */
	public static void setOldData() throws IOException {
		setData("oldData");
	}
	
	private static void setData(String type) throws IOException {
		File data = new File("./src/main/resources/"+type);
		File cacheData = new File("./src/main/resources/cacheData");
		if(!cacheData.exists()){
			// 若该文件夹不存在，则创建
			cacheData.mkdir();
		}
		clearCache();
		File[] files = data.listFiles();
		if(files == null || files.length == 0){
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		for(File f : files){
			fis = new FileInputStream(f);
			fos = new FileOutputStream("./src/main/resources/cacheData/"+f.getName());
			byte[] bytes = new byte[1024];
			int num;//结束标志
			while((num = fis.read(bytes)) != -1){
				fos.write(bytes,0,num);
			}
		}
		fis.close();
		fos.close();
	}
	
	/**
	 * 读取英雄信息放置到英雄类中
	 * @return 
	 * @throws DocumentException
	 * @throws FileNotFoundException 
	 */
	public static Hero readHero() throws DocumentException, FileNotFoundException {
		Map<String,String> map = new HashMap<String,String>();
		//创建SAXReader对象
		SAXReader saxReader = new SAXReader();
		String path = "./src/main/resources/cacheData/hero.xml";
		Document document = saxReader.read(new FileInputStream(new File(path)));
		Element rootElement = document.getRootElement();//获取xml根节点
		for(Iterator<?> iterator = rootElement.elementIterator(); iterator.hasNext();){
			// 获取英雄的属性节点
			Element element = (Element) iterator.next();
			Element element2 = element.element("value");
			String name = element.getName();
			String value = element2.getStringValue();
			map.put(name, value);
		}
		Hero hero = new Hero(map.get("name"), map.get("life"), map.get("attack"), map.get("defence"), 
				map.get("blueKeyNum"), map.get("yellowKeyNum"), map.get("redKeyNum"), map.get("money"), 
				map.get("sword"), map.get("shield"), map.get("x"), map.get("y"), map.get("map"), map.get("max"));
		return hero;
	}
	
	/**
	 * 将英雄的信息写入到xml中
	 * @param hero
	 * @throws DocumentException
	 * @throws IOException 
	 */
	private static void writeHero(Hero hero) throws DocumentException, IOException {
		SAXReader saxReader = new SAXReader();
		String path = "./src/main/resources/cacheData/hero.xml";
		Document document = saxReader.read(new FileInputStream(new File(path)));
		Element rootElement = document.getRootElement();//获取xml根节点
		for(Iterator<?> iterator = rootElement.elementIterator(); iterator.hasNext();){
			// 获取英雄的属性节点
			Element element = (Element) iterator.next();
			Element element2 = element.element("value");
			String name = element.getName();
			switch (name) {
				case "name":
					element2.setText(hero.getName());
					break;
				case "life":
					element2.setText(Integer.toString(hero.getLife()));
					break;
				case "attack":
					element2.setText(Integer.toString(hero.getAttack()));
					break;
				case "defence":
					element2.setText(Integer.toString(hero.getDefence()));
					break;
				case "blueKeyNum":
					element2.setText(Integer.toString(hero.getBlueKeyNum()));
					break;
				case "yellowKeyNum":
					element2.setText(Integer.toString(hero.getYellowKeyNum()));
					break;
				case "redKeyNum":
					element2.setText(Integer.toString(hero.getRedKeyNum()));
					break;
				case "money":
					element2.setText(Integer.toString(hero.getMoney()));
					break;
				case "sword":
					element2.setText(hero.getSword());
					break;
				case "shield":
					element2.setText(hero.getShield());
					break;
				case "x":
					element2.setText(Integer.toString(hero.getX()));
					break;
				case "y":
					element2.setText(Integer.toString(hero.getY()));
					break;
				case "map":
					element2.setText(Integer.toString(hero.getMap()));
					break;
				case "max":
					element2.setText(Integer.toString(hero.getMax()));
					break;
		
				default:
					break;
			}
			saveDocument(document, new File(path));
		}
	}
	
	private static void saveDocument(Document document,File xmlFile) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(xmlFile));//创建输出流
		OutputFormat of = OutputFormat.createCompactFormat();
		of.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(writer, of);
		xmlWriter.write(document);
		xmlWriter.flush();
		xmlWriter.close();
	}
	
	/**
	 * 存档
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public static void saveData(Hero hero) throws IOException, DocumentException {
		writeHero(hero);
		File cacheData = new File("./src/main/resources/cacheData");
		File oldData = new File("./src/main/resources/oldData");
		if(!oldData.exists()){
			// 若该文件夹不存在，则创建
			oldData.mkdir();
		}
		File[] files = cacheData.listFiles();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		for(File f : files){
			fis = new FileInputStream(f);
			fos = new FileOutputStream("./src/main/resources/oldData/"+f.getName());
			byte[] bytes = new byte[1024];
			int num;//结束标志
			while((num = fis.read(bytes)) != -1){
				fos.write(bytes,0,num);
			}
		}
		fis.close();
		fos.close();
	}
	/**
	 * 判断文件夹中是否有文件存在
	 * @param name
	 * @return
	 */
	public static boolean hasFile(String name) {
		File file = new File("./src/main/resources/"+name);//读取oldData文件夹
		if(!file.exists()){
			file.mkdirs();//若旧存档文件夹不存在则创建
		}
		File files[] = file.listFiles();//获取oldData下所有的文件
		return files != null && files.length > 0;
	}
	/**
	 * 读取地图
	 * @throws IOException 
	 */
	public static void readMap(TowerMap t, int i) throws IOException {
		String path = "./src/main/resources/cacheData/map"+i;
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[][] strs = new String[15][15];
		int num = 0;
		while((line = br.readLine()) != null){
			strs[num++] = line.split(",");
		}
		t.setMaps(strs);
		br.close();
	}
	/**
	 * 缓存地图
	 * @throws IOException 
	 */
	public static void cacheData(TowerMap t, int num) throws IOException {
		String path = "./src/main/resources/cacheData/map"+num;
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		String[][] strs = t.getMaps();
		//TODO 怎么获取二维数组的行和列
		String str;
		for(int i = 0 ; i < 13 ; i++){
			StringBuilder sb = new StringBuilder();
			for(int j = 0 ; j < 13 ; j++){
				str = strs[i][j];
				if(str.length() == 1){
					str = str+" ";
				}
				sb.append(str + ",");
			}
			sb.substring(0, sb.length()-1);
			bw.write(sb.toString());
			bw.newLine();
		}
		bw.close();
	}
}
