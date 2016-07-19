package kr.ac.sungkyul.thread;

public class UpperCaseAlphabet {	// 클래스를 변경하지 못하는 경우
	public void print(){
		for(int i = 'A'; i < 'Z'; i++){
			System.out.print((char)i);
		}
	}
}
