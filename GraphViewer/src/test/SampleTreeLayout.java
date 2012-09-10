package test;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.Annotation.Layer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class SampleTreeLayout {

	public static void main(String args[]){
		//Forest<MyNode,MyEdge> graph = new DelegateForest<MyNode, MyEdge>();
		DelegateTree<MyNode,MyEdge> graph = new DelegateTree<MyNode, MyEdge>();
		MyNode n1 = new MyNode("n1");
		MyNode n2 = new MyNode("n2");
		MyNode n3 = new MyNode("n3");
		MyNode n4 = new MyNode("n4");
		MyNode n5 = new MyNode("n5");
//	graph.addVertex(n1);
//	graph.addVertex(n2);
//	graph.addVertex(n3);
//	graph.addVertex(n4);
//	graph.addVertex(n5);
//	graph.addEdge(new MyEdge("e1"), n1,n2);
//	graph.addEdge(new MyEdge("e2"), n1,n3);
//	graph.addEdge(new MyEdge("e3"), n2,n4);
//	graph.addEdge(new MyEdge("e4"), n2,n5);
	graph.setRoot(n1);
	graph.addChild(new MyEdge("e1"), n1,n2);
	graph.addChild(new MyEdge("e2"), n1,n3);
	graph.addChild(new MyEdge("e3"), n2,n4);
	graph.addChild(new MyEdge("e4"), n2,n5);

	
	
	//Layout<MyNode, MyEdge> layout = new RadialTreeLayout<MyNode,MyEdge>(graph);
	//Layout<MyNode, MyEdge> layout = new TreeLayout<MyNode,MyEdge>(graph);
	DAGLayout<MyNode, MyEdge> layout = new DAGLayout<MyNode,MyEdge>(graph);
	//layout.setRoot(n1);
	
	//Layout<MyNode, MyEdge> layout = new BalloonLayout<MyNode,MyEdge>(graph);
	layout.setLocation(n1, new Point2D.Double(100,100));
	layout.setLocation(n2, new Point2D.Double(200,100));
	layout.setLocation(n3, new Point2D.Double(150,200));
	
//	BasicVisualizationServer<MyNode,MyEdge> panel =
//		new BasicVisualizationServer<MyNode, MyEdge>(layout,new Dimension(300,300));
	
	VisualizationViewer<MyNode,MyEdge> panel =
		new VisualizationViewer<MyNode, MyEdge>(layout,new Dimension(300,300));
	  DefaultModalGraphMouse<Integer,Number> gm = 
          new DefaultModalGraphMouse<Integer,Number>();
         gm.setMode(DefaultModalGraphMouse.Mode.PICKING);
         panel.setGraphMouse(gm);
         panel.setVertexToolTipTransformer(new ToStringLabeller<MyNode>());
    
         /** Image output *
         File file = new File("test.png");
         BufferedImage bufferedImage = 
 //  	    new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
 	    new BufferedImage(300,300, BufferedImage.TYPE_INT_RGB);
        	Graphics2D g2 = bufferedImage.createGraphics();
        	panel.paint(g2);
        	try {
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         ****************/
        	
	JFrame frame = new JFrame("Graph View");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().add(panel);
	frame.pack();
	frame.setVisible(true);
	}
}
