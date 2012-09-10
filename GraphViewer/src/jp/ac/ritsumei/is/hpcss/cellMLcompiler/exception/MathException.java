package jp.ac.ritsumei.is.hpcss.cellMLcompiler.exception;

/**
 * MathML例外クラス.
 */
@SuppressWarnings("serial")
public class MathException extends Exception {

	/**
	 * インスタンスを作成する.
	 * @param strClassName クラス名
	 * @param strFunctionName 関数名
	 * @param strMessage 例外メッセージ
	 */
	public MathException(String strClassName,
			String strFunctionName, String strMessage) {
		super(strClassName, strFunctionName, strMessage);
	}

	/*-----メッセージ取得-----*/
	/* (非 Javadoc)
	 * @see jp.ac.ritsumei.is.hpcss.cellMLonGPU.exception.Exception#getMessage()
	 */
	public String getMessage() {
		return "MathException In [" + m_strClassName + "." +
		m_strFunctionName + "] " + m_strMessage;
	}

}
