#ifndef SEARCH_H
#define SEARCH_H

#include <string>
#include <set>

#include "database.h"

class Webpage{

public:
	Webpage();
	~Webpage();

	void parsestring(std::string,std::string, std::map<std::string,std::set<std::string> >&);
	std::string converttolower(std::string);

	std::string getFilename();		
	std::set<std::string> getWords();
	std::set<std::string> getLinks();

private:

	std::string filename;				//filepath
	std::set<std::string> words;		//all words contained inside a webpage
	std::set<std::string> links;		//outgoing links
};

#endif