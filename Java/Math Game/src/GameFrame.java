import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


class GameFrame extends JFrame implements ActionListener{

	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension d = tk.getScreenSize();	
	
	List<Score> list = new LinkedList<Score>();
	
	private JTextField name1;		
	
	private int score=0;
	private String f;
	
	private JLabel sign;
	
	private JTextField field1;
	private JTextField field2;
	private JTextField field3;
	
	public GameFrame(){
			
	setTitle("Math Game");
	setSize(d.width/2, d.height/2);
	setResizable(false);
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	centerWindow(this);					//Call central display method
   
	StartPanel();
	}
	
	private void centerWindow(JFrame frame) {		//Programmer-definded method to centering a window open
		int centeredWidth = ((int)d.getWidth() - frame.getWidth())/2;	//Compute center width
		int centeredHeight = ((int)d.getHeight() - frame.getHeight())/2;	//Compute ceenter height
		setLocation(centeredWidth, centeredHeight);						//call method in JFrame
	}
	
public void StartPanel(){
	
	setSize(d.width/4, d.height/4);
	
	setLayout(new BorderLayout());
	
	JLabel a =new JLabel("Math Game");
	JLabel b = new JLabel("By Jonathan Chai");
	JLabel c = new JLabel("CS-170-02");
	
	JPanel title1 = new JPanel(new BorderLayout());	
	JPanel title2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	a.setHorizontalAlignment(SwingConstants.CENTER);
	b.setHorizontalAlignment(SwingConstants.CENTER);
	c.setHorizontalAlignment(SwingConstants.CENTER);

	
	name1 = new JTextField(null,20);
	
	TitledBorder border = BorderFactory.createTitledBorder("Name: ");
	name1.setBorder(border);

	title1.add(a,BorderLayout.NORTH);
	title1.add(b,BorderLayout.CENTER);
	title1.add(c,BorderLayout.SOUTH);

	title2.add(name1);
	name1.setHorizontalAlignment(JTextField.CENTER);	

	JButton begin = new JButton("Begin");
	
	this.add(title1,BorderLayout.NORTH);
	this.add(title2,BorderLayout.CENTER);
	this.add(begin,BorderLayout.SOUTH);
 
	begin.addActionListener(this);
	}

public void GamePanel(){
	setSize(d.width/4, d.height/5);
	
	if(Math.random()<0.5)
	{
		f = "+";
	}
	else
	{
		f = "-";
	}
	setLayout(new BorderLayout());
	JLabel a =new JLabel("Math Game");
	sign = new JLabel(f);
	JLabel eq = new JLabel("=");
	a.setHorizontalAlignment(SwingConstants.CENTER);
	
	JPanel question = new JPanel(new FlowLayout());
	JPanel buttons = new JPanel(new FlowLayout());
	
	int random1 = (int) (Math.random()*10+1);
	int random2 = (int) (Math.random()*10+1);
	String ran1 = Integer.toString(random1);
	String ran2 = Integer.toString(random2);
	
	field1 = new JTextField(ran1,5);
	field1.setEditable(false);
	field2 = new JTextField(ran2,5);
	field2.setEditable(false);
	field3 = new JTextField("",5);
	
	JButton score = new JButton("Score");
	JButton submit = new JButton("Submit");

	question.add(field1);
	question.add(sign);
	question.add(field2);
	question.add(eq);
	question.add(field3);
	
	buttons.add(score);
	buttons.add(submit);
	
	this.add(a,BorderLayout.NORTH);
	this.add(question,BorderLayout.CENTER);
	this.add(buttons,BorderLayout.SOUTH);	
	
	submit.addActionListener(this);
	score.addActionListener(this);
}

public void FailPanel(){
	
	setSize(d.width/5, d.height/5);
	
	setLayout(new BorderLayout());
	
	JPanel restart = new JPanel(new FlowLayout());
	
	JLabel end = new JLabel("Sorry that is incorrect");
	end.setHorizontalAlignment(SwingConstants.CENTER);
	
	JButton score = new JButton("Score");
	JButton res = new JButton("Restart");
	
	restart.add(score);
	restart.add(res);
		
	this.add(end,BorderLayout.NORTH);
	this.add(restart,BorderLayout.SOUTH);
	
	score.addActionListener(this);
	res.addActionListener(this);
}

public void actionPerformed(ActionEvent e) {
	String name = e.getActionCommand();
	if(name.equals("Begin"))
	{
		getContentPane().removeAll();
		GamePanel();
	}
	else if(name.equals("Submit"))
	{
		String answer = new String(field3.getText());
		if(Validator.validateInt(answer) == false)
		{
			JOptionPane.showMessageDialog(null,"Invalid Entry");
		}
		else
		{
			int x = Integer.parseInt(field1.getText());
			int y = Integer.parseInt(field2.getText());
			int z = Integer.parseInt(answer);
			if(f.equals("+"))
			{
				if(x+y==z)
				{
					score++;
					if(Math.random()<0.5)
					{
						f = "+";
					}
					else
					{
						f = "-";
					}
					int random1 = (int) (Math.random()*10+1);
					int random2 = (int) (Math.random()*10+1);
					String ran1 = Integer.toString(random1);
					String ran2 = Integer.toString(random2);
					field1.setText(ran1);
					field2.setText(ran2);
					sign.setText(f);
					field3.setText("");
				}
				else
				{		
					Score entry = new Score(name1.getText(),score);
					list.add(entry);
					getContentPane().removeAll();
					FailPanel();
				}
			}
			else if(f.equals("-"))
			{
				if(x-y == z)
				{
					score++;
					if(Math.random()<0.5)
					{
						f = "+";
					}
					else
					{
						f = "-";
					}
					int random1 = (int) (Math.random()*10+1);
					int random2 = (int) (Math.random()*10+1);
					String ran1 = Integer.toString(random1);
					String ran2 = Integer.toString(random2);
					field1.setText(ran1);
					field2.setText(ran2);
					sign.setText(f);
					field3.setText("");
				}
				else
				{		
					Score entry = new Score(name1.getText(),score);
					list.add(entry);
					getContentPane().removeAll();
					FailPanel();
				}	
			}
		}
		
	}
	else if(name.equals("Score"))
	{
		 Collections.sort(list, new NumberComparator());
		 if(list.size() >= 6)
		 {
			 list.remove(5);
		 }
		 
		 JTextArea scorelist = new JTextArea("Top 5 Scores" + "\n" + list.toString());
		 scorelist.setEditable(false);
		 JOptionPane.showMessageDialog(null,scorelist);
	}
	else if(name.equals("Restart"))
	{
		score = 0;
		getContentPane().removeAll();
		StartPanel();
	}
	
	}
}

