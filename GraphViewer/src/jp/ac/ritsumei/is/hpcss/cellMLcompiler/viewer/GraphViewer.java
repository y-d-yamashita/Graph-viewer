package jp.ac.ritsumei.is.hpcss.cellMLcompiler.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.mathml.MathMLEdge;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.recml.RecMLEdge;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.graph.recml.RecMLVertex;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathExpression;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathFactor;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.parser.RecMLGraphAnalyzer;



public class GraphViewer implements ActionListener{

	DelegateTree<MathFactor,MathMLEdge> graph;
	Vector<MathExpression> exprList;
	int exprIndex=0;
	public void view(MathExpression expr){
		graph=null;
		graph = expr.toJungGraph();

	     Dimension viewArea = new Dimension(800, 500);
	     

	 	//Layout<MyNode, MyEdge> layout = new RadialTreeLayout<MyNode,MyEdge>(graph);
	 	//Layout<MyNode, MyEdge> layout = new TreeLayout<MyNode,MyEdge>(graph);
	 	DAGLayout<MathFactor, MathMLEdge> layout = new DAGLayout<MathFactor,MathMLEdge>(graph);
	 	//layout.setStretch(10);
	 	//layout.setRoot(n1);
	 	
	 	//Layout<MyNode, MyEdge> layout = new BalloonLayout<MyNode,MyEdge>(graph);
	 
//	 	BasicVisualizationServer<MyNode,MyEdge> panel =
//	 		new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
	 	
	 	VisualizationViewer<MathFactor,MathMLEdge> panel =
	 		new VisualizationViewer<MathFactor, MathMLEdge>(layout,viewArea);
	 	  DefaultModalGraphMouse<Integer,Number> gm = 
	           new DefaultModalGraphMouse<Integer,Number>();
	          gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
	          panel.setGraphMouse(gm);
//	          panel.setVertexToolTipTransformer(new ToStringLabeller<MathMLVertex>());
	   		panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MathFactor>());
	
	   		JButton button1 = new JButton("Button1");
	   		button1.addActionListener(this);
	   		JFrame frame = new JFrame("Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.getContentPane().add(button1,BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);

	}

	DAGLayout<MathFactor, MathMLEdge> layout ;
 	VisualizationViewer<MathFactor,MathMLEdge> panel;
 	DefaultModalGraphMouse<Integer,Number> gm;
 	JFrame frame;
 	Dimension viewArea;
	public void view(Vector<MathExpression> exprs){
		graph=null;
		exprList=exprs;
		System.out.println(exprList.size());
		graph = exprList.get(exprIndex).toJungGraph();
		System.out.println("Expression:"+exprIndex+", Size:"+exprList.size());
		
	     viewArea = new Dimension(800, 500);
	     

	 	//Layout<MyNode, MyEdge> layout = new RadialTreeLayout<MyNode,MyEdge>(graph);
	 	//Layout<MyNode, MyEdge> layout = new TreeLayout<MyNode,MyEdge>(graph);
	 	layout = new DAGLayout<MathFactor,MathMLEdge>(graph);
	 	//layout.setStretch(10);
	 	//layout.setRoot(n1);
	 	
	 	//Layout<MyNode, MyEdge> layout = new BalloonLayout<MyNode,MyEdge>(graph);
	 
//	 	BasicVisualizationServer<MyNode,MyEdge> panel =
//	 		new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
	 	
	 	panel =
	 		new VisualizationViewer<MathFactor, MathMLEdge>(layout,viewArea);
	 	  gm = 
	           new DefaultModalGraphMouse<Integer,Number>();
	          gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
	          panel.setGraphMouse(gm);
//	          panel.setVertexToolTipTransformer(new ToStringLabeller<MathMLVertex>());
	   		panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MathFactor>());
	
	   		JButton prevButton = new JButton("Prev");
	   		JButton nextButton = new JButton("Next");
	   		prevButton.addActionListener(this);
	   		nextButton.addActionListener(this);
	   		 frame = new JFrame("Graph View");
	   		 JPanel panel2 = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.getContentPane().add(panel2,BorderLayout.SOUTH);
		panel2.add(prevButton,BorderLayout.WEST);
		panel2.add(nextButton,BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);

	}

	// アクションを処理するメソッド
		// この場合は、ボタンを処理する為にしか使っていません。
		public void actionPerformed(ActionEvent ae) {
			// どのアクションか判別するためにアクションコマンドを
			// 確認。(ボタンのアクションコマンドはボタン名)
		
			if( ae.getActionCommand() == "Prev" ) {
				if(0<exprIndex&&exprIndex<exprList.size()){
				graph=exprList.get(--exprIndex).toJungGraph();
				System.out.println("Expression:"+exprIndex+", Size:"+exprList.size());
			 	
				layout = new DAGLayout<MathFactor,MathMLEdge>(graph);
			 	//layout.setStretch(10);
			 	//layout.setRoot(n1);
			 	
			 	//Layout<MyNode, MyEdge> layout = new BalloonLayout<MyNode,MyEdge>(graph);
			 
//			 	BasicVisualizationServer<MyNode,MyEdge> panel =
//			 		new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
			 	
			 	panel =
			 		new VisualizationViewer<MathFactor, MathMLEdge>(layout,viewArea);
			 	  gm = 
			           new DefaultModalGraphMouse<Integer,Number>();
			          gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
			          panel.setGraphMouse(gm);
//			          panel.setVertexToolTipTransformer(new ToStringLabeller<MathMLVertex>());
			   		panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MathFactor>());
			
			   		JButton prevButton = new JButton("Prev");
			   		JButton nextButton = new JButton("Next");
			   		prevButton.addActionListener(this);
			   		nextButton.addActionListener(this);
	//		   		 frame = new JFrame("Graph View");
			   		 JPanel panel2 = new JPanel();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(panel,BorderLayout.CENTER);
				frame.getContentPane().add(panel2,BorderLayout.SOUTH);
				panel2.add(prevButton,BorderLayout.WEST);
				panel2.add(nextButton,BorderLayout.EAST);
				frame.pack();
				frame.repaint();
//				frame.setVisible(true);
				}else
					System.out.println("Last Expression:"+exprIndex);

	}
		

			if( ae.getActionCommand() == "Next" ) {
				if(-1<exprIndex&&exprIndex<exprList.size()-1){
				graph=exprList.get(++exprIndex).toJungGraph();
				System.out.println("Expression:"+exprIndex+", Size:"+exprList.size());
				
				layout = new DAGLayout<MathFactor,MathMLEdge>(graph);
			 	//layout.setStretch(10);
			 	//layout.setRoot(n1);
			 	
			 	//Layout<MyNode, MyEdge> layout = new BalloonLayout<MyNode,MyEdge>(graph);
			 
//			 	BasicVisualizationServer<MyNode,MyEdge> panel =
//			 		new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
			 	
			 	panel =
			 		new VisualizationViewer<MathFactor, MathMLEdge>(layout,viewArea);
			 	  gm = 
			           new DefaultModalGraphMouse<Integer,Number>();
			          gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
			          panel.setGraphMouse(gm);
//			          panel.setVertexToolTipTransformer(new ToStringLabeller<MathMLVertex>());
			   		panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MathFactor>());
			
			   		JButton prevButton = new JButton("Prev");
			   		JButton nextButton = new JButton("Next");
			   		prevButton.addActionListener(this);
			   		nextButton.addActionListener(this);
//			   		 frame = new JFrame("Graph View");
			   		 JPanel panel2 = new JPanel();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(panel,BorderLayout.CENTER);
				frame.getContentPane().add(panel2,BorderLayout.SOUTH);
				panel2.add(prevButton,BorderLayout.WEST);
				panel2.add(nextButton,BorderLayout.EAST);
				frame.pack();
				frame.repaint();
//				frame.setVisible(true);
			}else
				System.out.println("First Expression:"+exprIndex);

			}
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
