#include <mysql.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <iomanip>
#include <string>
#include <sstream>
#include <vector>
#include <ctime>
#include "dbconnect.h"

using namespace std;
 
//class object for wine vector
//holds wineName, wineType, vintage, score(rating), and price 
class Wine
{
public:
    Wine();
	Wine(string inputName,int inputVintage,int inputScore,double inputPrice,string inputType);
	
    string getName();
    string getType();
    int getVintage();
    int getScore();
    double getPrice();
private:
    string wineName;
    string wineType;
    int vintage;
    int score;  
    double price; 
}; 

//constructors
Wine::Wine()
{
	wineName="";
	wineType="";
	vintage=0;
	score=0;
	price=0;
}

Wine::Wine(string inputName,int inputVintage,int inputScore,double inputPrice,string inputType)
{
	wineName=inputName;
	vintage=inputVintage;
	wineType=inputType;
	price=inputPrice;
	score=inputScore;
}

//accesors
string Wine::getName()
{
	return wineName;
}

int Wine::getVintage()
{
	return vintage;
}

string Wine::getType()
{
	return wineType;
}

int Wine::getScore()
{
	return score;
}

double Wine::getPrice()
{
	return price;
}

/* Input:
 * the vector scoreList where the information will be stored
 * MYSQL_ROW variable row which contains the row of data
 * MYSQL_RES pointer res which contains all results 
 * 
 * Function:
 * takes the data from variable row and stores the information into the vector scoreList
 * adds up the total price of all wines that match the results
 * 
 * Output:
 * returns the total cost of all wines displayed stored in variable average
*/


double store(vector<Wine*> & scoreList,MYSQL_ROW row, MYSQL_RES* &res)
{
	double average=0;
	
	while ((row = mysql_fetch_row(res)) != NULL)
	{		
		scoreList.push_back(new Wine(row[0],atoi(row[1]),atoi(row[2]),atof(row[3]),row[4]));
		average+=atof(row[3]);
	}
	return average;
}

/* Input:
 * the vector scoreList which contains the stored wine information 
 * 
 * Function:
 * takes the information from vector scoreList and prints it out neatly 
*/
 
void printInfo(vector<Wine*> & scoreList)
{
	int a=scoreList.size();
	
	cout<<"-------------------------------------------------------------------------------------------"<<endl;
	cout<<"Name"<<setw(50)<<"Vintage"<<setw(10)<<"Rating"<<setw(10)<<"Price"<<setw(9)<<"Type"<<endl;
	cout<<"-------------------------------------------------------------------------------------------"<<endl;
	for(int i=0; i<a; i++)
	{	
		cout<<scoreList[i]->getName()<<setw(13)<<scoreList[i]->getVintage()<<setw(10)<<scoreList[i]->getScore()<<
			setw(10)<<scoreList[i]->getPrice()<<setw(10)<<scoreList[i]->getType()<<endl;
	}
	cout<<"-------------------------------------------------------------------------------------------"<<endl;
}

/* Input:
 * vector scoreList which already contains the wine information
 * variable average from the store function
 * 
 * Function:
 * checks if there is any information in scoreList
 * 		if no information is found, the function will print and end
 * if there is information, the function will call function printInfo
 * it will display the number of items found
 * it will display the average price of all items found
*/ 

void printandaverage(vector<Wine*> & scoreList,double average)
{
	if(scoreList.size()==0)
	{
		cout<<"-------------------------------------------------------------------------------------------"<<endl;	
		cout<<"There are no wines that fit the search criteria"<<endl;
	}
	else
	{
		printInfo(scoreList);	
		cout<<"There are "<<scoreList.size()<<" wines displayed"<<endl;
		cout<<"Average price of all displayed wines is $"<<fixed<<setprecision(2)<<average/scoreList.size()<<endl;
	}
	cout<<"-------------------------------------------------------------------------------------------"<<endl<<endl;			
}

int main(int argc, char* argv[])
{
	cout<<"Lab 5: Wine "<<endl;
	cout<<"Program written by: Jonathan Chai"<<endl;
	cout<<"Course Info: CS116 Thursday"<<endl;
	time_t now= time(0);
	char*dt = ctime(&now);
	cout<<"Date: "<<dt<<endl;
	
  MYSQL *conn;		// the connection
  MYSQL_RES *res;	// the results
  MYSQL_ROW row;	// the results row (line by line)
  
  struct connection_details mysqlD;
  mysqlD.server = (char *)"localhost";  // where the mysql database is
  mysqlD.user = (char *)"root";		// the root user of mysql	
  mysqlD.password = (char *)"password"; // the password of the root user in mysql
  mysqlD.database = (char *)"mysql";	// the databse to pick
 
  // connect to the mysql database
  conn = mysql_connection_setup(mysqlD);
 
  res = mysql_perform_query(conn, (char *)"use wine");

//while loop to repeat program until exited
while(1)
{
	//menu
	cout<<"1. Search for wine within a specific range of rating"<<endl;
	cout<<"2. Search for wine within a specific price range"<<endl;
	cout<<"3. Display all wine sorted by price"<<endl;
	cout<<"4. Display all wine sorted by rating"<<endl;
	cout<<"5. Display all wine sorted by rating, type of wine, and then price"<<endl;
	cout<<"6. Exit"<<endl;
	int option;
	cin>>option;

	//vector which the wine information is to be stored into
	vector<Wine*> scoreList;	 

	//variables to store user input
	int g,h;
	double k,l;

	switch(option)
	{
		case 1:
		{
			//asking for upper and lower limits as well as checking input
			cout<<"Between what two scores do you want the wines to be between?"<<endl;
			cout<<"Lower Limit: ";
			cin>>g;
			while(g >= 100||g < 0)
			{
				cout<<"Must be between 0 and 99"<<endl;
				cout<<"Lower Limit: ";
				cin>>g;
			}
			
			cout<<"Upper Limit: ";
			cin>>h;
			while(h>100||h<=0||g>h)
			{
				cout<<"Must be between 1 and 100 and greater than or equal to your first input"<<endl;
				cout<<"Upper Limit: ";
				cin>>h;
			}
			cout<<endl;

			//perform the query in SQL depending on user input
			ostringstream cmd;
			cmd<<"select * from wineInfo where rating >= "<<g<<" and rating <= "<<h<<" order by rating ASC,wineType,Price ASC";
			res=mysql_perform_query(conn,(char *)cmd.str().c_str());
			cout<<(char*)cmd.str().c_str()<<endl;
	
			//calling store and print functions
			double average=store(scoreList,row,res);
			printandaverage(scoreList,average);
			
			//clearing vector for repeated use
			scoreList.clear();
			break;
		}

		case 2:
		{
			//asking for upper and lower limits as well as checking input
			cout<<"Between what two prices do you want the wines to be between?"<<endl;
			cout<<"Lower Limit: ";
			cin>>k;
			while(k < 0)
			{
				cout<<"Must be positive"<<endl;
				cout<<"Lower Limit: ";
				cin>>k;
			}
		
			cout<<"Upper Limit: ";
			cin>>l;
			while(l<=0||k>l)
			{
				cout<<"Must be positive and greater than or equal to your first input"<<endl;
				cout<<"Upper Limit: ";
				cin>>l;
			}	
			cout<<endl;
			cout<<"---------------------------------------------------"<<endl;

			//perform the query in SQL depending on user input
			ostringstream cmd;
			cmd<<"select * from wineInfo where price >= " <<k<<" and price <= "<<l<<" order by price ASC,rating ASC,wineType";
			res=mysql_perform_query(conn,(char *)cmd.str().c_str());
			cout<<(char*)cmd.str().c_str()<<endl;
	
			//calling store and print functions
			double average=store(scoreList,row,res);				
			printandaverage(scoreList,average);
			
			//clearing vector for repeated use
			scoreList.clear();
			break;
		}
		
		case 3:
		{
			//perform the query in SQL depending on user input
			ostringstream cmd_price;
			cmd_price<<"select * from wineInfo order by price ASC,rating ASC,wineType";
			res=mysql_perform_query(conn,(char *)cmd_price.str().c_str());
			cout<<(char*)cmd_price.str().c_str()<<endl;
	
			//calling store and print functions	
			double average=store(scoreList,row,res);				
			printandaverage(scoreList,average);
			
			//clearing vector for repeated use
			scoreList.clear();
			break;
		}
		
		case 4:
		{
			//perform the query in SQL depending on user input
			ostringstream cmd;
			cmd<<"select * from wineInfo order by rating ASC,price ASC,wineType";
			res=mysql_perform_query(conn,(char *)cmd.str().c_str());
			cout<<(char*)cmd.str().c_str()<<endl;
	
			//calling store and print functions	
			double average=store(scoreList,row,res);				
			printandaverage(scoreList,average);
			
			//clearing vector for repeated use		
			scoreList.clear();
			break;
		}
		
		case 5:
		{
			//perform the query in SQL depending on user input	
			ostringstream cmd;
			cmd<<"select * from wineInfo order by rating DESC,wineType,price ASC";
			res=mysql_perform_query(conn,(char *)cmd.str().c_str());
			cout<<(char*)cmd.str().c_str()<<endl;
	
			//calling store and print functions
			store(scoreList,row,res);				
			printInfo(scoreList);	
			cout<<endl;
			
			//clearing vector for repeated use
			scoreList.clear();
			break;
		}
		
		case 6:
		{	
			//exit out of loop
			exit(1);
			break;
		}	
		
		default:
		{
			//default statement
			cout<<"Please enter a valid choice"<<endl;
		}
	}
} 
 		 /* clean up the database result set */
		mysql_free_result(res);
		/* clean up the database link */
		mysql_close(conn);
  return 0;
}


