package com.qst.dms.ui;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class JTableDemo extends AbstractTableModel 
		{        
		       private Vector TableData;//用来存放表格数据的线性表
		       String[][] TableDataArray;
		       private Vector TableColumn;//表格的列标题        
		       
		       public JTableDemo()
		       {
		           TableData = new Vector();
		          //显示列名
		          // String[] columnNames= {"列名1","列名2","列名3"};
		           TableColumn= new Vector(); 
//		           TableColumn.add(0, "用户名");
//		           TableColumn.add(1, "用户ID");
//		           TableColumn.add(2, "登录状态");
		           TableColumn.add("ID");
		           TableColumn.add("用户名");
		           TableColumn.add("地址");
		           
		           String []Line1 = {"001","test1","wuhan"};
		           //第1行
		           String []Line2 = {"002","test2","nanjing"};
		           //第2行
		           String []Line3 = {"003","test3","shanghai"};
		           
		           //将数据挂到线性表形成二维的数据表，形成映射
		           TableData.add(Line1);
		           TableData.add(Line2);
		           TableData.add(Line3);    
	       }
		       @Override
		       public int getRowCount()
		       {
		           //返回表的行数，返回TableData上挂的String数组个数
		           return TableData.size();
		    	   
		       }
		       
		       @Override
		       public int getColumnCount()
		       {
		           //返回表的列数，直接返回TableTitle.size()
		           return TableColumn.size();
		       }
		       
		       @Override
		       public Object getValueAt(int rowIndex, int columnIndex)
		       {
		            
		           //获取相应坐标位置的值，分下面两步
		           //获取每一行对应的String[]数组
		           String LineTemp[] = (String[])this.TableData.get(rowIndex);
		           //提取出对 应的数据
		           return LineTemp[columnIndex];
		       }
		  
		       @Override
		       public boolean isCellEditable(int rowIndex, int columnIndex)
		       {
		           //这个函数式设置每个单元格的编辑属性的
		           //这个函数AbstractTableModel已经实现
		           //这里我们设置为前两列为允许编辑状态
		           if(rowIndex<3&&columnIndex<3){
		               return true;
		           }
		           else{
		               return false;
		           }
		       }
		       @Override
		       public void setValueAt(Object aValue, int rowIndex, int columnIndex)
		       {
		           //当单元格的数据发生改变的时候调用该函数重设单元格的数据
		           //数据是放在TableData中,修改数据就是修改的
		           //TableData中的数据，所以我们仅仅在此处将TableData的对应数据修改即可              
		           ((String[])this.TableData.get(rowIndex))[columnIndex]=(String)aValue;
		           //然后调用函数fireTableCellUpdated(int row,int col);更新一下表格对应位置的数据显示即可完成对表格的数据修改；
		           this.fireTableCellUpdated(rowIndex, columnIndex);
		       }
		       public String getColumnName(int name)
		       {
		    	   //不加这一个方法列名是ABC读不进来
		           return (String)TableColumn.get(name);
		            
		       }
	
		//创建一个Dialog显示一下

		    public static void main(String[] args)
		    {               
		    	JTableDemo myModel=  new JTableDemo(); 
		        JTable table = new JTable(myModel);
		        JFrame jf = new JFrame();
		        JScrollPane jsp = new JScrollPane(table);//必须把JTable放入JScrollPane中，否则默认不显示列标题
		        jf.getContentPane().add(jsp);
		        jf.setBounds(0, 0, 500, 500);
		        jf.setVisible(true);
		        //这里我们可以检验直接在table的相应单元格上修改数据后，此单元格的值会不会变化
		        Scanner in = new Scanner(System.in);
		        int m=in.nextInt();
		        int n=in.nextInt();
		        String a=(String)myModel.getValueAt(m,n);
		        System.out.println(a);
		    }
		}

		




