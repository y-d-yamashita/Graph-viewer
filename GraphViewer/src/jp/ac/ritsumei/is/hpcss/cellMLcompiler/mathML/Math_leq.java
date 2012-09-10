package jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML;

import jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception.MathException;
import jp.ac.ritsumei.is.hpcss.cellMLcompiler.mathML.MathMLDefinition.eMathOperator;

/**
 * MathML演算子leqクラス
 */
public class Math_leq extends MathOperator {

	/*-----コンストラクタ-----*/
	public Math_leq(String[] strAttr) {
		super("<=", eMathOperator.MOP_LEQ, MathMLDefinition.MATH_OPERATOR_MIN_FACTOR_LEQ, strAttr);
	}
	
	public Math_leq() {
		this(null);
	}

	/*-----演算命令メソッド-----*/
	public double calculate() throws MathException {
		/*被演算子の個数チェック*/
		if(m_vecFactor.size() < MathMLDefinition.MATH_OPERATOR_MIN_FACTOR_LEQ) {
			throw new MathException("Math_leq","calculate","lack of operand");
		}

		/*左辺値・右辺値取得*/
		double dLeftValue = m_vecFactor.get(0).getValue();
		double dRightValue = m_vecFactor.get(1).getValue();

		/*左辺値の値を返す*/
		return (dLeftValue <= dRightValue) ? 1 : 0;
	}

	/*-----文字列変換メソッド-----*/
	public String toLegalString() throws MathException {

		/*被演算子の個数チェック*/
		if(m_vecFactor.size() < MathMLDefinition.MATH_OPERATOR_MIN_FACTOR_LEQ) {
			throw new MathException("Math_leq","toLegalString","lack of operand");
		}

		return " ( " + m_vecFactor.get(0).toLegalString()
			+ " <= " + m_vecFactor.get(1).toLegalString() + " ) ";
	}

}
