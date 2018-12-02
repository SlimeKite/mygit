package cn.agree.roll;

import java.util.Arrays;

public class TowerMap {
	private String[][] maps;

	public TowerMap() {
		super();
	}

	public TowerMap(String[][] maps) {
		super();
		this.maps = maps;
	}

	public String[][] getMaps() {
		return maps;
	}

	public void setMaps(String[][] maps) {
		this.maps = maps;
	}
	
	public void setMap(int i, int j, String str){
		this.maps[i][j] = str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(maps);
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
		TowerMap other = (TowerMap) obj;
		if (!Arrays.deepEquals(maps, other.maps))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TowerMap [maps=" + Arrays.toString(maps) + "]";
	}
	
}
