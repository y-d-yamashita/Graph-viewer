package jp.ac.ritsumei.is.hpcss.cellMLcompiler.parser;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import test.MyEdge;
import test.MyNode;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.CellMLException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.MathException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.RecMLException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.RelMLException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.TableException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.TecMLException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.XMLException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.recML.RecMLDefinition;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.recML.RecMLDefinition.eRecMLGraphTag;

/**
 * MathML解析クラス.
 */
public class RecMLGraphAnalyzer extends XMLAnalyzer {

	Graph<MyNode,MyEdge> graph;
	Map<Integer,MyNode> vertexMap;
	Map<Integer,MyEdge> edgeMap;
	MyNode	curVertex;
	MyEdge	curEdge;
	
	Integer varID;
	Integer equID;
	Integer srcID;
	Integer dstID;

	boolean varFlag;
	boolean equFlag;
	boolean srcFlag;
	boolean dstFlag;
/**
	 * MathML解析インスタンスを作成する.
	 */
	public RecMLGraphAnalyzer() {
		}

		/* ========================================================
	 * findTagStart
	 * 開始タグ解析メソッド
	 * 
	 * @arg
	 * string			strTag		: 開始タグ名
	 * XMLAttribute*	pXMLAttr	: 属性クラスインスタンス
	 * 
	 * @throws
	 * 		MathException
	 * (非 Javadoc)
	 * @see jp.ac.ritsumei.is.hpcss.cellMLonGPU.parser.XMLAnalyzer#findTagStart(java.lang.String, jp.ac.ritsumei.is.hpcss.cellMLonGPU.parser.XMLAttribute)
	 * ======================================================== */
	public void findTagStart(String strTag, XMLAttribute pXMLAttr)
	throws MathException, XMLException, RelMLException, CellMLException, TecMLException, RecMLException {
		eRecMLGraphTag tagKind;
		/*タグの種類を特定*/
		try {
			tagKind = RecMLDefinition.getRecMLGraphTagId(strTag);
		}
		catch (RecMLException e) {
			System.err.println(e.getMessage());
			throw new RecMLException("Analyzer","findTagStart",
					"can't specify MathML tag [" + strTag + "]");
		}

		/*種類ごとの処理*/
		switch(tagKind){

			//-----------------------------------演算子の解析
		case CTAG_RECML:
			break;
		case CTAG_GRAPH:
		//	graph = new DirectedSparseGraph<MyNode, MyEdge>();
			graph = new DirectedSparseGraph<MyNode, MyEdge>();
				break;
		case CTAG_NODES:
			vertexMap=new HashMap<Integer,MyNode>();
			break;
		case CTAG_NODE:
			curVertex=new MyNode();
			vertexMap.put(new Integer(pXMLAttr.getValue("id")),curVertex);
			break;
		case CTAG_VARIABLE:
			varFlag=true;
			break;
		case CTAG_EQUATION:
			equFlag=true;
			break;
		case CTAG_EDGES:
			edgeMap=new HashMap<Integer,MyEdge>();
			break;
		case CTAG_EDGE:
			curEdge=new MyEdge();
			edgeMap.put(new Integer(pXMLAttr.getValue("id")),curEdge);
			break;
		case CTAG_SOURCE:
			srcFlag=true;
			break;
		case CTAG_DEST:
			dstFlag=true;
			break;
			
		//-----------------------------------未定義の種別
		default:
			/*例外処理*/
			throw new MathException("MathMLAnalyzer","findTagStart",
					"unknown MathML tag found [" + strTag + "]");
		}
	}

	/* (非 Javadoc)
	 * @see jp.ac.ritsumei.is.hpcss.cellMLonGPU.parser.XMLAnalyzer#findTagEnd(java.lang.String)
	 */
	public void findTagEnd(String strTag)
	throws MathException, RelMLException, CellMLException, RecMLException {
		eRecMLGraphTag tagKind;

		/*タグの種類を特定*/
		try{
			tagKind = RecMLDefinition.getRecMLGraphTagId(strTag);
		}catch (RecMLException e) {
			System.err.println(e.getMessage());
		throw new RecMLException("Analyzer","findTagStart",
				"can't specify MathML tag [" + strTag + "]");
		}

		/*種類ごとの処理*/
		switch(tagKind){

		case CTAG_RECML:
			break;
		case CTAG_GRAPH:
			break;
		case CTAG_NODES:
			break;
		case CTAG_NODE:
			curVertex=null;
			break;
		case CTAG_VARIABLE:
			varFlag=false;
			break;
		case CTAG_EQUATION:
			equFlag=false;
			break;
		case CTAG_EDGES:
			break;
		case CTAG_EDGE:
			graph.addEdge(curEdge,
					vertexMap.get(srcID),
					vertexMap.get(dstID));
			curEdge=null;
			break;
		case CTAG_SOURCE:
			srcFlag=false;
			break;
		case CTAG_DEST:
			srcFlag=false;
			break;
				default:
			/*例外処理*/
			throw new RecMLException("MathMLAnalyzer","findTagEnd",
					"unknown MathML tag found [" + strTag + "]");
		}
	}

	/* (非 Javadoc)
	 * @see jp.ac.ritsumei.is.hpcss.cellMLonGPU.parser.XMLAnalyzer#findText(java.lang.String)
	 */
	public void findText(String strText)
	throws MathException, CellMLException, TableException {
	if(varFlag==true)
		varID=Integer.valueOf(strText);
	else if(equFlag==true)
		equID=Integer.valueOf(strText);
	else if(srcFlag==true)
		srcID=Integer.valueOf(strText);
	else if(dstFlag==true)
		dstID=Integer.valueOf(strText);
		
	}
	
	public void view(){
	     Dimension viewArea = new Dimension(300, 300);
	     
		Layout<MyNode, MyEdge> layout = 
			 //new FRLayout<MyNode, MyEdge>(graph);
		new KKLayout<MyNode, MyEdge>(graph);
		//new CircleLayout<MyNode, MyEdge>(graph);
		//new StaticLayout<MyNode,MyEdge>(graph);
		//new ISOMLayout<MyNode, MyEdge>(graph);
		
		//	layout.setLocation(n1, new Point2D.Double(100,100));
	//	layout.setLocation(n2, new Point2D.Double(200,100));
	//	layout.setLocation(n3, new Point2D.Double(150,200));
		
		BasicVisualizationServer<MyNode,MyEdge> panel =
			new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
		
		JFrame frame = new JFrame("Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
