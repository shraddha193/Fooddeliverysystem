package miniproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class node
{
	int vertexno;
	String name;
	
	public
	
	node()
	{
		vertexno=0;
		name=null;
	}
	
	node(int vn,String n)
	{
		vertexno=vn;
		name=n;
	}
}

class graph
{
	int nv;
	double cost[][]=new double[100][100];
	int time[][]=new int[100][100];
	ArrayList<node> nodeal=new ArrayList<node>();
	Scanner sc=new Scanner(System.in);
	public
	
	graph()
	{
		nv=0;
	}
	
	void hardcodedVertex()
	{
		node tempA=new node(0,"Vaishali Restaurant");
		nodeal.add(tempA);
		node tempB=new node(1,"Rupali Restaurant");
		nodeal.add(tempB);
		node tempC=new node(2,"Amrapali Restaurant");
		nodeal.add(tempC);
		node temp1=new node(3,"Tilak road");
		nodeal.add(temp1);
		node temp2=new node(4,"Model Colony");
		nodeal.add(temp2);
		node temp3=new node(5,"Ashoknagar");
		nodeal.add(temp3);
		node temp4=new node(6,"SB Road");
		nodeal.add(temp4);
		node temp5=new node(7,"Kothrud");
		nodeal.add(temp5);
		node temp6=new node(8,"Aundh");
		nodeal.add(temp6);
		node temp7=new node(9,"JM Road");
		nodeal.add(temp7);
		node temp8=new node(10,"Bund Garden");
		nodeal.add(temp8);
		node temp9=new node(11,"Sinhagad Road");
		nodeal.add(temp9);		
		nv=12;
	}
	
	void addVertex()   //for new dest
	{
		int c;
		do
		{
			System.out.println("enter place name");
			String pl=sc.next();
			node temp=new node(nv,pl); //first nv then ++ because starts with zero 
			nodeal.add(temp);
			nv++;
			
			System.out.println("Do you want to add more places?\n1=yes\n2=no");
			c=sc.nextInt();
		}while(c==1);
	}
	
	void HardcodedEdge()  
	{
		//initialize adjMatrix
		for(int i=0;i<nv;i++)
		{
			for(int j=0;j<nv;j++)
			{
				cost[i][j]=0;
				time[i][j]=0;
			}
		}
		//collect in matrices (time and dist)
		addEdge(0,4,5.5,30);
		addEdge(1,11,5.2,22);
		addEdge(2,3,5.3,27);
		addEdge(1,10,5,20);
		addEdge(2,9,4.8,17);
		addEdge(0,7,5.1,21);
		addEdge(0,5,5,20);
		addEdge(2,6,3.8,17);
		addEdge(0,9,4.9,20);
		addEdge(3,4,2,12);
		addEdge(4,5,3.2,14);
		addEdge(6,7,1,5);
		addEdge(7,10,2.5,12);
		addEdge(7,8,3.4,17);
		addEdge(8,11,1.7,9);
		addEdge(8,9,0.7,3);


	}
	
	void addEdge(int u,int v,double dist,int tim)//Mapping parameters into matrices
	{
		cost[u][v]=dist;
		cost[v][u]=dist;
		time[u][v]=tim;
		time[v][u]=tim;
	}
	
	void addEdge2() //accept and input in the matrices connections of newly added destinations into the hardcoded graph
	{
		int c;
		do
		{
			displayIndex();
			System.out.println("Enter connectivity indices");
			int u=sc.nextInt();
			int v=sc.nextInt();
			System.out.println("enter distance ");
			int dist=sc.nextInt();
			System.out.println("enter average time in minutes");
			int tim=sc.nextInt();
			addEdge(u,v,dist,tim);
			
			System.out.println("Do you want to add more routes?\n1=yes\n2=no");
			c=sc.nextInt();
		}while(c==1);
	}
	
	void deleteEdge() //deleting(made 0) out of service roads . Input ends 
	{
		displayIndex();
		System.out.println("Enter connectivity indices");
		int u=sc.nextInt();
		int v=sc.nextInt();
		cost[u][v]=0;
		cost[v][u]=0;
		time[u][v]=0;
		time[v][u]=0;
	}
	
	void displayIndex() //display index no and their locations
	{
		System.out.println("The indices assigned to the places are as follows ");
		Iterator<node> itr=nodeal.iterator();
		while(itr.hasNext())
		{
			node temp=itr.next();
			System.out.println(temp.vertexno+"\t=\t"+temp.name);
		}
	}
	
	void display()  //distance matrix displayed
	{
		System.out.println("the adjacency matrix is ");
		
		System.out.print(" \t");
		for(int j=0;j<nv;j++)
		{
			System.out.print(j+"\t");
		}
		System.out.println("");
		
		for(int i=0;i<nv;i++)
		{
			System.out.print(i+"\t");
			for(int j=0;j<nv;j++)
			{
				System.out.print(cost[i][j]+"\t");
			}
			System.out.println(" ");
		}
	}
	
	void dijkstra()
	{
		int i;
		
		System.out.println("\nEnter a Hotel from our chain \n0 : Vaishali\n1 : Rupali\n2 : Amrapali  \n");
		int source=sc.nextInt();
		displayIndex();
		System.out.println("\nEnter your destination :");
		int dest=sc.nextInt();
		
		double min=9999;
		int index=0;
		//3 arrays 
		int visited[]=new int[nv];
		double dist[]=new double[nv];
		int from[]=new int[nv];
		
		
		
		//initializing all 3 arrays accordingly
		for(i=0;i<nv;i++)
		{
			visited[i]=0;
			dist[i]=0;
			from[i]=-1;
		}
		for(i=0;i<nv;i++)
		{
			for(int j=0;j<nv;j++)
			{
				if(cost[i][j]==0)
					cost[i][j]=9999;    // if there is no edge then make it infinity(9999)
			}
		}
		
		for(i=0;i<nv;i++)  
		{
			dist[i]=cost[source][i]; //distance between the source and all vertices is copied
			from[i]=source;     //in the beginning
			
		}
		visited[source]=1;  
		dist[source]=0;  //because no edge lies
		
		while(index!=dest) //till destination not reached
		{
			min=9999;
			for(i=0;i<nv;i++)
			{
				if(dist[i]<min && visited[i]!=1)//to find a distance that is the least and its vertex not visited
				{
					min=dist[i]; //overwrite min
					index=i;   //collect the index
				}
			}
			
			visited[index]=1; //mark visited
			
			for(int w=0;w<nv;w++) 
			{
				if(cost[index][w]!=9999 && visited[w]!=1) //find all connected vertices which are not visited
				{
					if(dist[w]>dist[index]+cost[index][w]) //if this is true then replace the distances of the required connected vertices
					{
						dist[w]=dist[index]+cost[index][w]; 
						from[w]=index; //update from vertex
					}
				}
			}
			
		}
		
		System.out.println("\nThe minimum distance got = "+dist[dest]);//end of while ,print
				
		//printing track
		int print[]=new int[nv]; //new array for path
		int p=dest;
		i=0;
		while(p!=source) //till source is not reached ,starting from destination
		{
			print[i]=p; //save indices in print array
			i++;
			p=from[p]; //reverse tracking
		}
		print[i]=p; //save source as does not enter while
		
		System.out.println("The path is ");
		for(int j=i;j>=0;j--)//reverse print
		{
			System.out.print(print[j]+"\t"); 
		}
		
		System.out.println("");
		
		// calculate average time required
		int tottime=0;
		for(int j=i;j>=1;j--)//time calculation
		{
			int k=print[j]; //collecting adjacent indices
			int l=print[j-1];
			tottime=tottime+time[k][l]; //from hardcoded time
		}
		System.out.println("your delivery will reach in "+tottime+" minutes");
		
	}
	
	
}

public class miniproject 
{
	public static void main(String[] args) 
	{
		int ans;
		Scanner sc=new Scanner(System.in);
		graph g=new graph();
		
		
		g.hardcodedVertex();
		g.HardcodedEdge();
		
		do
		{
			g.displayIndex();
		System.out.println("Is destination entered by user in this list?\n1=yes\n2=no");
		int c=sc.nextInt();
		if(c==2)
		{
			System.out.println("SORRY! WE DONT PROVIDE SERVICE IN THIS AREA");
			System.out.println("Do you want to add the destination to our connectivity?\n1=yes\n2=no");
			c=sc.nextInt();
			if(c==1)
			{
				System.out.println("To add the destination place");
				g.addVertex();
				System.out.println("To add the routes connecting destination place");
				g.addEdge2();				
			}	
		}
		
		//g.display();
		
		System.out.println("Is any road out of service?\n1=yes\n2=no");
		c=sc.nextInt();
		if(c==1)
		{
			g.deleteEdge();
			//g.display();
		}
		
		g.dijkstra();
		System.out.println("\nPress 1 to continue ");
		 ans=sc.nextInt();

		}while(ans==1);
		
	}
}

/*
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
Is destination entered by user in this list?
1=yes
2=no
1
Is any road out of service?
1=yes
2=no
2

Enter a Hotel from our chain 
0 : Vaishali
1 : Rupali
2 : Amrapali  

2
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road

Enter your destination :
7

The minimum distance got = 4.8
The path is 
2	6	7	
your delivery will reach in 22 minutes

Press 1 to continue 
1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
Is destination entered by user in this list?
1=yes
2=no
1
Is any road out of service?
1=yes
2=no
1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
Enter connectivity indices
2
6

Enter a Hotel from our chain 
0 : Vaishali
1 : Rupali
2 : Amrapali  

2
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road

Enter your destination :
7

The minimum distance got = 8.9
The path is 
2	9	8	7	
your delivery will reach in 37 minutes

Press 1 to continue 
1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
Is destination entered by user in this list?
1=yes
2=no
2
SORRY! WE DONT PROVIDE SERVICE IN THIS AREA
Do you want to add the destination to our connectivity?
1=yes
2=no
1
To add the destination place
enter place name
Dahanukar
Do you want to add more places?
1=yes
2=no
2
To add the routes connecting destination place
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
12	=	Dahanukar
Enter connectivity indices
9
12
enter distance 
2
enter average time in minutes
12
Do you want to add more routes?
1=yes
2=no
2
Is any road out of service?
1=yes
2=no
2

Enter a Hotel from our chain 
0 : Vaishali
1 : Rupali
2 : Amrapali  

1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
12	=	Dahanukar

Enter your destination :
12

The minimum distance got = 9.600000000000001
The path is 
1	11	8	9	12	
your delivery will reach in 46 minutes

Press 1 to continue 
1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
12	=	Dahanukar
Is destination entered by user in this list?
1=yes
2=no
1
Is any road out of service?
1=yes
2=no
1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
12	=	Dahanukar
Enter connectivity indices
1
11

Enter a Hotel from our chain 
0 : Vaishali
1 : Rupali
2 : Amrapali  

1
The indices assigned to the places are as follows 
0	=	Vaishali Restaurant
1	=	Rupali Restaurant
2	=	Amrapali Restaurant
3	=	Tilak road
4	=	Model Colony
5	=	Ashoknagar
6	=	SB Road
7	=	Kothrud
8	=	Aundh
9	=	JM Road
10	=	Bund Garden
11	=	Sinhagad Road
12	=	Dahanukar

Enter your destination :
12

The minimum distance got = 13.6
The path is 
1	10	7	8	9	12	
your delivery will reach in 64 minutes

Press 1 to continue 
0


*/

