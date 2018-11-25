#ifndef DATABASE_H
#define DATABASE_H

#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <fstream>

#include "search.h"

class Database{

	public:
		Database();
		~Database();

		void setIncominglink(std::map<std::string, std::set<std::string> >);
		void setFileword(std::string, std::set<std::string>);
		void setOutgoingLink(std::map<std::string, std::set<std::string> >);

		std::set<std::string> getwordset(std::string);

		void createwordmap(std::string);
		void unionsearch(std::string,std::set<std::string>&);
		void intersectsearch(std::string,std::set<std::string>&);
		void addtomap(std::string,std::string);

		std::set<std::string> expandlinks(std::set<std::string>&);
		void pagerank(std::set<std::string>, double, int, std::ofstream&);

	private:

		std::map<std::string, std::set<std::string> > wordlink;
		std::map<std::string, std::set<std::string> > inclink;
		std::map<std::string, std::set<std::string> > outlink;
		std::map<std::string, std::set<std::string> > fileword;

};
#endif