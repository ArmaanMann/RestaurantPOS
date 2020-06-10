package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Panel extends JPanel {

	private int rows = 1, cols = 10;
	private static Color FOREGROUND_COLOR = new Color(239, 55, 55);
	private int[] graph, oldArray, test;
	private int[] tests = {63, 83, 46, 14, 76, 75, 24, 76, 30, 93};
	private boolean[] blanks, correct;
	private boolean running = false, bubble, select, insert, quick, old = false, arraySet = false;;
	private int pivot = 9;
	private int time = 400;
	
	public Panel() {
		
		setPreferredSize(new Dimension(900, 250));
		setBackground(Color.LIGHT_GRAY);
		setForeground(FOREGROUND_COLOR);

		setFont(new Font("SansSerif", Font.BOLD, 25));
		
		graph = new int[10];
		blanks = new boolean[10];
		correct = new boolean[10];
		oldArray = new int[10];
		test = new int[10];
		
	}
	
	public void setOld(boolean old) {
		this.old = old;
	}

	public void setArray() {
		if (running)
			return;
		reset();
		if (old == false) {
			Random random = new Random();
		
			for (int i = 0; i < graph.length; i++) {
				graph[i] = random.nextInt(90)+10;
				//System.out.print(""+graph[i]+", ");
				oldArray[i] = graph[i];
			}
			
			//test = graph;
			//System.out.println();
			old = true;
		} else {
			for (int i = 0; i < graph.length; i++)
				graph[i] = oldArray[i];
		}
		/*for (int i = 0; i < graph.length; i++)
			graph[i] = tests[i];*/
		arraySet = true;
		repaint();
	}
	
	private void reset() {
		/*Arrays.sort(test);
		for (int i = 0; i < test.length; i++) {
			if (test[i] != graph[i]) {
				System.out.println("FAILED");
				break;
			}
		}*/
		
		
		pivot = 9;
		for (int i = 0; i < correct.length; i++) {
			correct[i] = false;
		}
		repaint();
	}
	
	private void setBlank(int pos, boolean set) {
		blanks[pos] = set;
	}
	
	private void bubbleSort() {
		bubble = true;
		System.out.println("Starting Bubble Sort");
		sort();
	}
	
	private void selectionSort() {
		select = true;
		System.out.println("Starting Selection Sort");
		sort();
	}
	
	private void insertionSort() {
		insert = true;
		System.out.println("Starting Insertion Sort");
		sort();
	}
	
	private void quickSort() {
		quick = true;
		System.out.println("Starting Quick Sort");
		sort();
	} 
	public void setTime(double speed) {
		int test = (int) (speed / 0.25);
			time = (4 - test) * 100 + 400; 
	}
	
	public void sortGo(int index) {
		if (running)
			return;
		switch (index) {
		case 0:
			bubbleSort();
			break;
		case 1:
			selectionSort();
			break;
		case 2:
			insertionSort();
			break;
		case 3:
			quickSort();
			break;
		}
	}
	
	private void done() {
		arraySet = false;
		JOptionPane.showMessageDialog(this.getParent(), "Algorithm has finished sorting!");
	}
	
	private void sort() {
		Thread sortThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				long timer = System.currentTimeMillis();
				
				running = true;
				int a = 0;
				int b = 1;
				int animCount = 0;
				int animTime = 4;
				boolean anim = false;
				int count = 0;
				boolean swap = false;
				boolean right = false;
				
				while (running) {
					if (System.currentTimeMillis() - timer > time) {
						if (bubble) {
							if (b > 9) {
								correct[graph.length - 1 - count] = true;
								a = 0;
								b = 1;
								count++;
								if (count > 8) {
									System.out.println("Finished!");
									running = false;
									for (int i = 0; i < correct.length; i++) {
										correct[i] = true;
										repaint();
									}
									done();
									break;
								}
							}
							if (graph[a] > graph[b]) {
								
								if (animCount < animTime) {
									if (anim == false) {
										setBlank(a, true);
										setBlank(b, true);
										anim = true;
									}
									else {
										setBlank(a, false);
										setBlank(b, false);
										anim = false;
									}
									animCount++;
								}
								else if (animCount >= animTime) {
									setBlank(a, false);
									setBlank(b, false);
									anim = false;
									int temp = graph[a];
									graph[a] = graph[b];
									graph[b] = temp;
									a++;
									b++;
									animCount = 0;
								}
							}
							else {
								a++;
								b++;
							}
						}
						else if (select) {
							if (b > 9) {
								int temp = graph[count];
								graph[count] = graph[a];
								graph[a] = temp;
								correct[count] = true;
								count++;
								a = count;
								b = a + 1;
								if (count > 8) {
									System.out.println("Finished!");
									running = false;
									for (int i = 0; i < correct.length; i++) {
										correct[i] = true;
										repaint();
									}
									done();
									break;
								}
									
							}
							if (graph[a] < graph[b]) {
								if (animCount < animTime) {
									if (anim == false) {
										setBlank(a, true);
										anim = true;
									}
									else {
										setBlank(a, false);
										anim = false;
									}
									animCount++;
								}
								else if (animCount >= animTime) {
									setBlank(a, false);
									anim = false;
									b++;
									animCount = 0;
								}
							}
							else {
								a = b;
								b++;
							}
						}
						else if (insert) {
							System.out.println("count "+count);
							if (b > 9) {
								System.out.println("Finished!");
								running = false;
								for (int i = 0; i < correct.length; i++) {
									correct[i] = true;
									repaint();
								}
								done();
								break;
							}
							if (graph[b] < graph[a]) {
								if (animCount < animTime) {
									if (anim == false) {
										setBlank(b, true);
										anim = true;
									}
									else {
										setBlank(b, false);
										anim = false;
									}
									animCount++;
								}
								else if (animCount >= animTime) {
									setBlank(b, false);
									anim = false;
									animCount = 0;
									int temp = graph[a];
									graph[a] = graph[b];
									graph[b] = temp;
									if (b > 1) {
										b--;
										a--;
									}
									else {
										b = count+1;
										a = b - 1;
										count++;
									}
									
								}
							}
							else {
								a++;
								b++;
							}
						}
						else if (quick) {
							if (right == true && pivot <= b) {
								System.out.println("Finished!");
								running = false;
								for (int i = 0; i < correct.length; i++) {
									correct[i] = true;
									blanks[i] = false;
									repaint();
								}
								done();
								break;
							}
							//System.out.println("a = "+a+", count = "+count);
							if (a > 9 || a >= pivot) {
								int temp = graph[count];
								//System.out.println(temp+"");
								graph[count] = graph[pivot];
								graph[pivot] = temp;
								correct[count] = true;
								if (swap == false) {
									b = count + 1;
									swap = true;
									pivot = count -1;
									if (b >= 9)
										swap = false;
								} else {
									if (graph[pivot] <= graph[count]) {
										pivot--;
									}
								}
								/*else {
									if (pivot == 0)
										pivot--;
									else if (graph[pivot] > graph[pivot - 1]) {
										System.out.println("CALLED");
									pivot--;
									}
								}*/
								if (right == false) {
									count = 0;
									a = 0;
								} else {
									count = b;
									a = count;
								}
							
							if (pivot < 0) {
								right = true;
								count = b;
								pivot = 9;
								a = count;
								}
							}
						/*	if (correct[pivot] == true) {
								System.out.println("correct pivot");
								pivot--;
							}*/
							/*if (pivot != 0)
								if (graph[pivot - 1] == graph[pivot] && pivot - a < 1)
									pivot--;*/
						//	System.out.println("a "+a+" pivot "+pivot+" count = "+count);
							else  if (graph[a] <= graph[pivot]) {
									if (animCount < animTime) {
										if (anim == false) {
											setBlank(a, true);
											anim = true;
										}
										else {
											setBlank(a, false);
											anim = false;
										}
										animCount++;
									}
									else if (animCount >= animTime) {
										setBlank(a, false);
										anim = false;
										animCount = 0;
										int temp = graph[count];
										graph[count] = graph[a];
										graph[a] = temp;
										a++;
										count++;
									}
								
							} else {
								a++;
								/*if (a < 10)
								if (correct[a] == true) {
									System.out.println("correct a");
									a++;
								}*/
							}
							
						}
						timer  += time;
						repaint();
					}
				}
			}
		});
		
		if (arraySet)
			sortThread.start();
		
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.white);
		int margin = 90;
		g.drawLine(0, 0, 900, 0);
		for (int i = 0; i <= rows; i++)
			g.drawLine(1, 1+(i*margin-15), 900, 1 + (i*margin-15));
		for (int j = 0; j < cols; j++)
			g.drawLine(0 + (j*margin), 0, 0+ (j*margin), 75);
		g.drawLine(899, 0, 899, 75);
		
		if (quick) {
			g.setColor(Color.ORANGE);
			g.drawRect(5 + pivot * 90, 5, 80, 65);
		}
	}
	
	private void drawGraph(Graphics g) {
		g.setFont(new Font("SansSerif", Font.BOLD, 30));
		for (int i = 0; i < graph.length; i++) {
			if (correct[i] == true)
				g.setColor(Color.green);
			else
				g.setColor(Color.red);
			g.drawString(""+graph[i], 25 + (i * 90), 48);
		}
	}
	
	private void drawBlanks(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		int margin = 90;
		for (int i = 0; i < blanks.length; i++) {
			if (blanks[i] == true)
				g.fillRect(2 + (i * margin), 2, 65, 65);
		}
	}
	
	private void drawCorrect(Graphics g) {
		
	}
	
	private void drawText(Graphics g) {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawGrid(g);
		drawGraph(g);
		drawBlanks(g);
		drawCorrect(g);
		drawText(g);
		
	}
}
