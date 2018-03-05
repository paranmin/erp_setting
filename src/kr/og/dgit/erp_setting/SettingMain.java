package kr.og.dgit.erp_setting;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kr.og.dgit.erp_setting.service.BackUpService;
import kr.og.dgit.erp_setting.service.InitService;
import kr.og.dgit.erp_setting.service.LoadService;

@SuppressWarnings("serial")
public class SettingMain extends JFrame {
	private JPanel contentPane;

	public SettingMain() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 10, 10));

		JButton btnInitial = new JButton(new AbstractBtnAction("초기화", this));
		contentPane.add(btnInitial);

		JButton btnDataBackUp = new JButton(new AbstractBtnAction("백업", this));
		contentPane.add(btnDataBackUp);

		JButton btnDataLoad = new JButton(new AbstractBtnAction("복원", this));
		contentPane.add(btnDataLoad);
	}
	
	public void initial() {
		InitService.getInstance().service();
		JOptionPane.showMessageDialog(null, "초기화 완료");
	}
	
	public void backUpData() {
		BackUpService.getInstance().service();
		JOptionPane.showMessageDialog(null, "백업");
	}
	
	public void loadData() {
		LoadService.getInstance().service();
		JOptionPane.showMessageDialog(null, "복원");
	}

}
