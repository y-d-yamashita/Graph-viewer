package test;

public class MyEdge {
	String label;
	public MyEdge(String label){
		this.label=label;
	}
	@Override
	public String toString(){
		return label;
	}
	public MyEdge(){
		
	}
	public void setLable(String label){
		this.label=label;
	}
}
