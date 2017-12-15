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

// Reader�ǳ����࣬�����������ʵ�����ĳ��󷽷�
public class CodeDialog extends Reader{
	private String buffer = null;
	private int pos = 0;
	
	// off��ƫ����
	public int read(char[] cbuf, int off, int len) throws IOException{
		if (buffer == null){
			String text = getDialogText();
			if (text == null){
				return -1;
			}
			else {
				// �����ȡ���˶Ի����е�string,�����
				System.out.println(text);
				buffer = text + "\n";// ����������Ϊ�Ի������ݼ��ϻ���
				pos = 0;// pos�Ǹ�ɶ�ģ�
			}
		}
			
		int size = 0;
		int length = buffer.length();
		while (pos < length && size < len){
			cbuf[off+size++] = buffer.charAt(pos++);
		}
		// ���ȫ�������򽫻�������Ϊ��
		if (pos == length){
			buffer = null;
		}
		return size;
	}
	public void close() throws IOException{ }
	
	// ��ʾ�ı���ѡ�����벢�Ҵ������������
	protected String getDialogText(){
		// area��һ���ı���
		JTextArea area = new JTextArea(20, 40);
		JScrollPane pane = new JScrollPane(area);
		int result = JOptionPane.showOptionDialog(null, pane, "Input",
                                                  JOptionPane.OK_CANCEL_OPTION,
                                                  JOptionPane.PLAIN_MESSAGE,
                                                  null, null, null);
		// ��ȡ�û�ѡ�����ĸ���ť
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
