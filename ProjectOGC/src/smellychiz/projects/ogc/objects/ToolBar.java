package smellychiz.projects.ogc.objects;

import java.util.LinkedList;
import java.util.List;

import smellychiz.projects.ogc.util.helpers.Vector3;

public class ToolBar {
	int selected = 0;
	Vector3 pos;
	
	GameObject bar, selectedItem;
	
	
	List<GameObject> items = new LinkedList<GameObject>();
	
	public ToolBar(){
		
	}
	
	public void touched(float x, float y){
		
	}
	
	public void draw(){
		for (int i = 0; 0<items.size(); i++){
			items.get(i).draw();
		}
	}
	
}
