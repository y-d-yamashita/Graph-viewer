package jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.mathml;

import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.Edge;

/**
 * RecML Edge class
 * @author y-yamashita
 *
 */
public class MathMLEdge extends Edge{
	
	/**
	 * Constructor
	 */
	public MathMLEdge(){}
	
	/**
	 * toString method
	 */
	@Override
      public String toString(){
            return "";
     }
	
    /**
     * toXMLString method
     * @param indent
     * @return XML string
     */
    public String toXMLString(int srcID,int dstID,int id,String indent){
    	return new StringBuilder().append(indent).append("<edge id="+id+">\n").
    			append(indent+"	").append("<source>").append(srcID).append("</source>\n").
    			append(indent+"	").append("<dest>").append(dstID).append("</dest>\n").
    			append(indent).append("</edge>\n").toString();
    }


}
