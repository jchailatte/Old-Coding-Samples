#include <string>
#include <iostream>
#include <map>
#include <set>
#include <cctype>
#include <fstream>

#include "database.h"
#include "search.h"
#include "setutility.h"

using namespace std;

Database::Database() 
{

}

Database::~Database()
{
	
}

void Database::setIncominglink(map<string, set<string> > incominglink)
{
	inclink = incominglink;
}

void Database::setOutgoingLink(map<string, set<string> > outgoinglink)
{
  outlink = outgoinglink;
}

void Database::setFileword(string file, set<string> words)
{
  fileword[file]=words;
}

void Database::addtomap(string word, string link)
{
	wordlink[word].insert(link);
}

void Database::unionsearch(string line,set<string> &unionword)										//get result for OR
{
	string sub = "";
	set<string>::iterator set_it;

	while(line.length() != 0)												//parse string for words following OR
    {
    	sub = line.substr(0,line.find(' '));
    	line.erase(0,sub.length()+1);
    	createwordmap(sub);													//create maps of words to sets

    	if(wordlink.find(sub) != wordlink.end())							//union the sets of words
    	{
    		unionword = unionSet(wordlink.find(sub)->second,unionword);		
    	}
    }
}

void Database::intersectsearch(string line,set<string> &intersectword)									//get result for AND
{
  string sub = "";
  set<string>::iterator set_it;

  sub= line.substr(0, line.find(' '));										//create map
  line.erase(0,sub.length()+1);
  createwordmap(sub);

  auto map_it = wordlink.find(sub);											//create first set
  if(map_it != wordlink.end())
  {
    intersectword=map_it->second;
  }
    
  while(line.length() != 0)													//intersect the sets	
  {
    sub = line.substr(0, line.find(' '));
    line.erase(0,sub.length()+1);
    createwordmap(sub);
    
    if(wordlink.find(sub) != wordlink.end())
    {
    	intersectword = intersectSet(intersectword, wordlink.find(sub)->second);
	  }
  }
}

void Database::createwordmap(string sub)
{
    if (wordlink.count(sub) > 0)
    {
      return;
    }
    for(int i=0; i< (int)sub.length(); i++)
    {
      sub[i] = (char)tolower(sub[i]);
    }
       
    map<string, set<string> >::iterator map_it;
    set<string>::iterator set_it;

    for(map_it = fileword.begin(); map_it != fileword.end(); map_it++)
    {
        if(map_it->second.find(sub) != map_it->second.end())
        {
          addtomap(sub,map_it->first);
        }
    }
}

set<string> Database::getwordset(string word)
{
  set<string> emptyset;

	auto map_it  = wordlink.find(word);
	if((map_it != wordlink.end()))
  {	
    return map_it->second;
  }
  else
  {
    return emptyset;
  }

}

set<string> Database::expandlinks(set<string> &seed)
{
  set<string> exlinks;
  exlinks = unionSet(seed, exlinks);              //adding initial set to the expanded set

  for(set<string>::iterator set_it = seed.begin(); set_it != seed.end(); set_it++)\
  {
    if(inclink.find(*set_it) != inclink.end())                      //adding incominglinks to set
    { 
        exlinks = unionSet(exlinks,inclink.find(*set_it)->second);          
    }

    if(outlink.find(*set_it) != outlink.end())                      //adding outgoinglinks to set
    { 
        exlinks = unionSet(exlinks,outlink.find(*set_it)->second);          
    }
  }
  return exlinks;
}
