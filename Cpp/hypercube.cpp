#include <iostream>
#include <queue>
#include <set>
#include <vector>
#include <fstream>
#include <string>
#include <algorithm>
#include "setutility.h"

using namespace std;

struct Node 										
{
	string name;	
	int g;	//path from start node to curr node 
	int h;	//number of 0's 
	int f;   //huerristics
	Node* parent;


};

class Compare
{
public:
	bool operator ()(const Node* lhs, const Node* rhs) const	//comparator
	{
		if(lhs->f > rhs->f)
		{
			return true;
		}
		else if(lhs->h > rhs->h)
		{
			return true;
		} 
		else
		{
			return lhs->name > rhs->name;
		}
	}
};

void updatef(Node* temp)					//create  f value
{
	temp->f = temp->g + temp->h;
}

int updateh(string temp)				//create h value
{

	int a = 0;
	for(int i =0; i<(int)temp.length();i++)
	{
		if(temp[i]=='0')
		{
			a++;
		}
	}
	return a;
}

set<string> expandnode(string line)		//creating neighbors
{
	int length = (int)line.length();
	set<string> expand;
	string temp = line;

	for(int i=0; i<length; i++)
	{
		if(line[i] == '0')
		{
			line[i] = '1';
			expand.insert(line);
		}
		else
		{
			line[i] = '0';
			expand.insert(line);
		}
		line = temp;
	}
	return expand;
}

int main(int argc,char* argv[])
{
	if(argc < 2)
	{
		cout<<"Not enough arguements"<<endl;
		return 0;
	}
	string start = argv[1];
	ifstream permit(argv[2]);

	int expansions = 0;
	int binarylength = (int)start.length();

	string finalstring(binarylength,'1');
	set<Node*> trash;						//vector to hold nodes to be deleted
	std::priority_queue<Node*,vector<Node*>, Compare> pq;

	set<string> permitted;					//set of permitted nodes
	string line;							

	bool flag = true;
	while(getline(permit,line))				//getting all permitted nodes
	{
		if(flag == true)					//skipping first line of txt file
		{
			flag = false;
		}
		else
		{
			permitted.insert(line);
		}
	}
	Node* begin = new Node;					//creating first node
	begin->name = start;
	begin->g = 0;
	begin->h = updateh(start);
	begin->f = begin->g+begin->h;
	begin->parent = NULL;

	pq.push(begin);		
	trash.insert(begin);					//push onto queue
	vector<string> visited;
	Node* finalnode = NULL;

	set<string> expand;
	while(!pq.empty())
	{
		Node* temp = pq.top();
		pq.pop();

		if(temp->name == finalstring)		//found target string
		{
			finalnode = temp;
			break;
		}

		if(find(visited.begin(), visited.end(), temp->name) != visited.end())	//if node is visisted, skip
		{
			continue;
		}
		visited.push_back(temp->name);

	  	expand = expandnode(temp->name);						//creating neighbors
	  	expand = intersectSet(expand, permitted);

	  	for(set<string>::iterator it= expand.begin();it != expand.end(); it++)	//inserting neighbors into queue
	  	{
	  		Node* temp1 = new Node;
	  		temp1->name = *it;
	  		temp1->g = temp->g+1;
	  		temp1->h = updateh(*it);
	  		temp1->f = temp1->g+temp1->h;
	  		temp1->parent = temp;

	  		pq.push(temp1);
	  		trash.insert(temp1);
	  	}
	  	expansions++;								//expansions
	}

	vector<string> path;
	while(finalnode != NULL)						//backtracking from final node
	{
		path.push_back(finalnode->name);
		finalnode = finalnode->parent;
	}

	if(pq.empty())									//no paths to target node
	{
		cout<<"No transformation"<<endl;
	}
	else
	{
		for(vector<string>::reverse_iterator it = path.rbegin(); it != path.rend(); ++it)
    	{
       		std::cout << *it << std::endl;
    	}
	}
	cout<<"expansions = "<<expansions<<endl;
	
	for(set<Node*>::iterator it= trash.begin(); it!= trash.end();it++)	//memory clear
	{
		delete *it;
	}
}