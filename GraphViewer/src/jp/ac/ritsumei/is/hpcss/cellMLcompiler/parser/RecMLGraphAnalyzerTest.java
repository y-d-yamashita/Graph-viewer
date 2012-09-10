package jp.ac.ritsumei.is.hpcss.cellMLcompiler.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import jp.ac.ritsumei.is.hpcss.cellMLcompiler.parser.XMLAnalyzer;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.parser.XMLHandler;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.viewer.GraphViewer;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class RecMLGraphAnalyzerTest {
	
	/**
	* 数式展開システム コマンド版メインクラス
	*/
	
	//========================================================
	//DEFINE
	//========================================================
	protected static final String MAIN_VAR_RELATION_FILENAME = "relation.txt";
	protected static final String MAIN_VAR_INITIALIZE_FILENAME = "initialize.txt";

	/** Default parser name. */
	protected static final String DEFAULT_PARSER_NAME =
	"org.apache.xerces.parsers.SAXParser";

	protected static final String GENERATOR_CUDA = "cuda";
	protected static final String GENERATOR_COMMON = "common";
	protected static final String GENERATOR_SIMPLE = "simple";
	protected static final String GENERATOR_JAVA = "java";
	protected static final String GENERATOR_JAVA_BIGDECIMAL = "java_bigdecimal";
	// protected static final String DEFAULT_GENERATOR = GENERATOR_CUDA;
	protected static final String DEFAULT_GENERATOR = GENERATOR_JAVA_BIGDECIMAL;

	protected static String generatorName = GENERATOR_JAVA;
	protected static String outputDir = null;
	protected static String programFilename = null; //"fhn_euler_unisys2.java";
	protected static String relationFilename = MAIN_VAR_RELATION_FILENAME;
	protected static String initializeFilename = MAIN_VAR_INITIALIZE_FILENAME;

	/**
	* 数式展開システムコマンド版エントリポイント関数.
	* @param args コマンドライン文字列
	*/
	public static void main(String[] args) {

	
	//---------------------------------------------------
	//XMLパーサ初期化
	//---------------------------------------------------
	// create parser
	XMLReader parser = null;
	try {
	parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
	// バッファサイズが小さいと ハンドラの characters() が
	// 文字列を途中で切った値を返す。バッファサイズを大きくする
	// デフォルトは2k
	parser.setProperty("http://apache.org/xml/properties/input-buffer-size",
	new Integer(16 * 0x1000));
	} catch (Exception e) {
	System.err.println("error: Unable to instantiate parser ("
	+ DEFAULT_PARSER_NAME+")");
	System.exit(1);
	}

	//---------------------------------------------------
	//解析処理
	//---------------------------------------------------
	/*解析器インスタンス生成*/
	RecMLGraphAnalyzer pRecMLGraphMLAnalyzer = new RecMLGraphAnalyzer();

	/*各ファイル名初期化*/
	String strRelMLFileName;
	String strTecMLFileName;
	String strCellMLFileName;

	/*RelMLの解析*/
	if(!parseXMLFile("sample/graph.xml", parser, pRecMLGraphMLAnalyzer)){
	System.exit(1);
	}
	GraphViewer viewer = new GraphViewer();
	viewer.view(pRecMLGraphMLAnalyzer);
	
	}

	/**
	* XMLファイル解析関数.
	* @param strXMLFileName 読み込みファイル名
	* @param pParser XMLパーサインスタンス
	* @param pXMLAnalyzer RelML解析器インスタンス
	* @return 成否判定
	*/
	static boolean parseXMLFile(String strXMLFileName, XMLReader pParser,
	XMLAnalyzer pXMLAnalyzer) {

	try {

	/*ファイルの存在を確認*/
	if(!new File(strXMLFileName).canRead()){
	System.err.println("file can't open : " + strXMLFileName);
	return false;
	}

	/*パース処理*/
	XMLHandler handler = new XMLHandler(pXMLAnalyzer);
	pParser.setContentHandler(handler);
	pParser.parse(strXMLFileName);

	} catch (SAXParseException e) {
	/*エラー出力*/
	System.err.println("failed to parse file : " + strXMLFileName);
	return false;
	} catch (Exception e) {

	/*例外メッセージ出力*/
	System.err.println("error: Parse error occurred - "+e.getMessage());
	Exception se = e;
	if (e instanceof SAXException) {
	se = ((SAXException)e).getException();
	}
	if (se != null) {
	se.printStackTrace(System.err);
	} else {
	e.printStackTrace(System.err);
	}

	/*エラー出力*/
	System.err.println("failed to parse file : " + strXMLFileName);
	return false;
	}

	return true;
	}

	/**
	* システム使用方法の出力.
	*/
	private static void printUsage() {
	System.err.println("usage: ./parser [option] \"filename of RelML\" [output-option]");
	System.err.println("option:");
	        System.err.println(" -g name Select Generator by name.");
	        System.err.println(" name: {"
	         + GENERATOR_CUDA + "|"
	         + GENERATOR_COMMON + "|"
	         + GENERATOR_SIMPLE + "|"
	         + GENERATOR_JAVA + "|"
	         + GENERATOR_JAVA_BIGDECIMAL + "}");
	        System.err.println(" default Generator: " + DEFAULT_GENERATOR);
	System.err.println("output-option:");
	        System.err.println(" output-dir [Relation-file [Initialize-file [Program-file]]]");
	}

	
}
