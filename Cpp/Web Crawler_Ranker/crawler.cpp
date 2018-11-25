#include <iostream>
#include <string>
#include <map>
#include <set>
#include <fstream>

#include "readconfig.h"

using namespace std;

void parfile(string filename, set<string> &visited,ofstream &output)
{
	std::string line;
	
   	ifstream webpage(filename.c_str());					//opening file
   	
   	while(getline(webpage,line))
   	{
		string sub = "";

		while((int)line.find('(') != -1)				//find links
		{
			int i = (int)line.find('(');					
			sub = line.substr(i+1, line.find(')')-i-1);
			line.erase(i,line.find(')')-i+1);

			if(visited.find(sub) == visited.end())		//check if file has been parsed yet
			{
				ifstream opening(sub.c_str());
				visited.insert(sub);					//insert file into checker set

				if(opening.is_open())					//check if the file can be opened
				{
					output<<sub<<endl;					//write filename to output file
					parfile(sub,visited,output);		//recurse to read the next file
				}
				opening.close();
			}
		}	   			
   	}
   	webpage.close();
}


int main(int argc, char *argv[])
{
	map<string,string> param;								//map to store parameters
	set<string> visited;									//set to store visited files

	string filename;	
	string indexfile = "INDEX_FILE";						//parameter names
	string outfile = "OUTPUT_FILE";

	param = readConfig(argc,argv);							//reading config file

	ifstream index(param[indexfile]);						//opening files
	ofstream output(param[outfile]);

	while(getline(index,filename))							//reading seed file
	{
		if(visited.find(filename) == visited.end())
		{
			ifstream open(filename.c_str());
			visited.insert(filename);

			if(open.is_open())
			{
				output<<filename<<endl;
				parfile(filename,visited,output);
			}
			open.close();
		}
	}

	index.close();
	output.close();
}