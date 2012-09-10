package jp.ac.ritsumei.is.hpcss.cellMLcompiler.viewer;

import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.mathml.MathMLEdge;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.mathml.MathMLVertex;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.recml.RecMLEdge;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.recml.RecMLVertex;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathExpression;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.parser.RecMLGraphAnalyzer;



public class GraphViewer {
	public void view(MathExpression expr){
		DelegateTree<MathMLVertex,MathMLEdge> graph=null;
		graph = expr.toJungGraph();

	     Dimension viewArea = new Dimension(800, 800);
	     

	 	//Layout<MyNode, MyEdge> layout = new RadialTreeLayout<MyNode,MyEdge>(graph);
	 	//Layout<MyNode, MyEdge> layout = new TreeLayout<MyNode,MyEdge>(graph);
	 	DAGLayout<MathMLVertex, MathMLEdge> layout = new DAGLayout<MathMLVertex,MathMLEdge>(graph);
	 	//layout.setStretch(10);
	 	//layout.setRoot(n1);
	 	
	 	//Layout<MyNode, MyEdge> layout = new BalloonLayout<MyNode,MyEdge>(graph);
	 
//	 	BasicVisualizationServer<MyNode,MyEdge> panel =
//	 		new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
	 	
	 	VisualizationViewer<MathMLVertex,MathMLEdge> panel =
	 		new VisualizationViewer<MathMLVertex, MathMLEdge>(layout,viewArea);
	 	  DefaultModalGraphMouse<Integer,Number> gm = 
	           new DefaultModalGraphMouse<Integer,Number>();
	          gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
	          panel.setGraphMouse(gm);
//	          panel.setVertexToolTipTransformer(new ToStringLabeller<MathMLVertex>());
	   		panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MathMLVertex>());
		JFrame frame = new JFrame("Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

	}
	
	public void view(RecMLGraphAnalyzer recmlGraphAnalyzer)
	{
	     Dimension viewArea = new Dimension(300, 300);
	     
		Layout<RecMLVertex, RecMLEdge> layout = 
			 //new FRLayout<MyNode, MyEdge>(graph);
		new KKLayout<RecMLVertex, RecMLEdge>(recmlGraphAnalyzer.toJungGraph());
		//new CircleLayout<MyNode, MyEdge>(graph);
		//new StaticLayout<MyNode,MyEdge>(graph);
		//new ISOMLayout<MyNode, MyEdge>(graph);
		
		//	layout.setLocation(n1, new Point2D.Double(100,100));
	//	layout.setLocation(n2, new Point2D.Double(200,100));
	//	layout.setLocation(n3, new Point2D.Double(150,200));

	 	VisualizationViewer<RecMLVertex, RecMLEdge>panel =
		 		new VisualizationViewer<RecMLVertex, RecMLEdge>(layout,new Dimension(300,300));
 	
	 	  DefaultModalGraphMouse<Integer,Number> gm = 
		           new DefaultModalGraphMouse<Integer,Number>();
		          gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
		          panel.setGraphMouse(gm);
	panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<RecMLVertex>());
  		
		JFrame frame = new JFrame("Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

	}
}
