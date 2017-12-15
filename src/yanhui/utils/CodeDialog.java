package yanhui.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Reader是抽象类，所以子类必须实现它的抽象方法
public class CodeDialog extends Reader{
	private String buffer = null;
	private int pos = 0;
	
	// off是偏移量
	public int read(char[] cbuf, int off, int len) throws IOException{
		if (buffer == null){
			String text = getDialogText();
			if (text == null){
				return -1;
			}
			else {
				// 如果获取到了对话框中的string,则输出
				System.out.println(text);
				buffer = text + "\n";// 缓冲区内容为对话框内容加上换行
				pos = 0;// pos是干啥的？
			}
		}
			
		int size = 0;
		int length = buffer.length();
		while (pos < length && size < len){
			cbuf[off+size++] = buffer.charAt(pos++);
		}
		// 如果全部读完则将缓冲区设为空
		if (pos == length){
			buffer = null;
		}
		return size;
	}
	public void close() throws IOException{ }
	
	// 显示文本框，选择输入并且传回输入的内容
	protected String getDialogText(){
		// area是一个文本框
		JTextArea area = new JTextArea(20, 40);
		JScrollPane pane = new JScrollPane(area);
		int result = JOptionPane.showOptionDialog(null, pane, "Input",
                                                  JOptionPane.OK_CANCEL_OPTION,
                                                  JOptionPane.PLAIN_MESSAGE,
                                                  null, null, null);
		// 获取用户选择是哪个按钮
		if (result == JOptionPane.OK_OPTION){
			return area.getText();
		}
		else {
			return null;
		}
	}
	
	public static Reader file() throws FileNotFoundException{
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			return new BufferedReader(new FileReader(chooser.getSelectedFile()));
		}
		else{
			throw new FileNotFoundException("");
		}
	}
}
