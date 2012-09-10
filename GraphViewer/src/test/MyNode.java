package test;

public class MyNode {
	String label;
	
	public MyNode(String label){
		this.label=label;
	}
	public MyNode() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString(){
		return label;
	}
	public void setLabel(String label){
		this.label=label;
	}
}
