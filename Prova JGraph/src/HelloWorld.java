import java.util.Hashtable;

import javax.swing.JFrame;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxVertexHandler;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class HelloWorld extends JFrame{
	
	public HelloWorld () {
		super("Hello, World!");
		
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		
		mxStylesheet stylesheet = graph.getStylesheet();

		Hashtable<String, Object> style = new Hashtable();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		stylesheet.putCellStyle("MYSTYLE", style); 
		
		Hashtable<String, Object> edge_style = new Hashtable();
		edge_style.put(mxConstants.STYLE_ROUNDED, true);
		stylesheet.putCellStyle("EDGE_STYLE", edge_style);

		Hashtable<String, Object> tran_style = new Hashtable(); 
		tran_style.put(mxConstants.STYLE_NOLABEL, true);
		tran_style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE); 
		tran_style.put(mxConstants.STYLE_STROKECOLOR, "Black");
		tran_style.put(mxConstants.STYLE_FILLCOLOR, "Black"); 
		stylesheet.putCellStyle("TRAN_STYLE", tran_style);


		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 120, 20, 80, 80, "MYSTYLE");
			Object v2 = graph.insertVertex(parent, null, "Hello", 40, 120, 80, 80, "MYSTYLE");
			Object v3 = graph.insertVertex(parent, null, "Hello", 200, 120, 80, 80, "MYSTYLE");
			Object v4 = graph.insertVertex(parent, null, "Hello", 120, 360, 80, 80, "MYSTYLE");
			graph.insertEdge(null, null, "", v1, v2);
			graph.insertEdge(null, null, "", v1, v3);
			graph.insertEdge(null, null, "", v2, v4);
			graph.insertEdge(null, null, "", v4, v1);
		}
		finally {
			graph.getModel().endUpdate();
		}
		mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
		layout.execute(graph.getDefaultParent());
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setEnabled(false);
		graphComponent.setDragEnabled(true);
		getContentPane().add(graphComponent);
	}
	public static void main(String[] args) {
		HelloWorld frame = new HelloWorld();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(400,320);
		frame.setVisible(true);
	}
}