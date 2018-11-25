#include <string>
#include <iostream>
#include <map>
#include <set>
#include <cctype>
#include <fstream>

#include "search.h"
#include "database.h"

using namespace std;

Webpage::Webpage()
{

}

Webpage::~Webpage()
{

}

string Webpage::converttolower(string phrase)
{
	int c = (int)phrase.length();
	for(int i=0; i< c; i++)
	{
		phrase[i] = (char)tolower(phrase[i]);
	}

	return phrase;
}

void Webpage::parsestring(string line, string filepath, map<string, set<string> > &incominglinks)
{
	int i = 0;
	string sub = "";

	filename = filepath;				//set filename equal to filepath
	incominglinks[filename];			//creating a map key for every file

	while(line.length() != 0)			//parsing character by character
	{
		if(line[i] == ']')				//case for anchor text
		{
			sub = line.substr(0,i); 
			line.erase(0,i+1);			
			sub = converttolower(sub);

			words.insert(sub);
			i=0;
		}
		else if(line[i] == '(')			//case for links
		{
			sub = line.substr(i+1, line.find(')')-i-1);
			line.erase(i,line.find(')')-i+1);

			links.insert(sub);
			incominglinks[sub].insert(filename);

			i=0;
		}
		else if(line[i] == ' ')			//case for whitespace
		{

		    sub = line.substr(0,i);
		    line.erase(0,i+1);
		    sub = converttolower(sub);	

		    words.insert(sub);
		    i=0;
		}
		else if(isalnum(line[i]) != 0)	//case for numbers and letters
		{
			i++;
		}
		else 							//case for other characters
		{
			sub = line.substr(0,i);
			line.erase(0, i+1);
			sub = converttolower(sub);

			words.insert(sub);
			i=0;
		}
	}
}

string Webpage::getFilename()
{
	return filename;
}

set<string> Webpage::getWords()
{
	return words;
}

set<string> Webpage::getLinks()
{
	return links;
}
