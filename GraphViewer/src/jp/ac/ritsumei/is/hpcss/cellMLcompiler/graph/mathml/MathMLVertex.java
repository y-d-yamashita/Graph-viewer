package jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.mathml;

import com.sun.org.apache.bcel.internal.classfile.SourceFile;

import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.MathException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.Vertex;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathExpression;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathFactor;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathOperand;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathOperator;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.Math_ci;


/**
 * Vertex class for RecML variable and expression
 * @author y-yamashita
 *
 */
public class MathMLVertex extends Vertex {
	MathOperand m_operand;
	MathOperator m_operator;
	
	private MathMLVertex() {
		m_operand=null;
		m_operator=null;
	}
	
	public MathMLVertex(MathFactor f){
		super();
		if(f instanceof MathOperand)
			m_operand=(MathOperand) f;
		else if(f instanceof MathOperator)
			m_operator=(MathOperator) f;
		
	}
	
	/**
	 * toString method
	 */
    @Override
    public String toString(){
    if(m_operand!=null){
    	return m_operand.toString();
    }else if(m_operator!=null){
    	return m_operator.toString();
    }else{
    	return "None";
    }
    }
    

}


