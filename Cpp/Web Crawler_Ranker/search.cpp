#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <vector>

#include "search.h"
#include "database.h"
#include "readconfig.h"

using namespace std;

string converttolower(string phrase)
{
  int length = (int)phrase.length();
  for(int i=0; i<length; i++)
    {
      phrase[i] = (char)tolower(phrase[i]);
    }
    return phrase;
}

void printSet(set<string> tempset,ofstream &output)
{
    output<<tempset.size()<<endl;                     //print data
    for(set<string>::iterator set_it = tempset.begin(); set_it != tempset.end(); set_it++)
    {
      output<<*set_it<<endl;
    }
}

int main(int argc, char* argv[])
{
  map<string,string> param;

    param = readConfig(argc,argv);                                 //reading config file

    if(param.empty())                        //check 
    {
      cout<<"No config file"<<endl;
      return 0; 
    }

    ifstream index(param["INDEX_FILE"]);                           //opening files
    ifstream query(param["QUERY_FILE"]);
    ofstream output(param["OUTPUT_FILE"]);

    double restart = stod(param["RESTART_PROBABILITY"]);
    int step = stoi(param["STEP_NUMBER"]);

    string filepath = "";                                             //initializing variables
    string line = "";
    int i = 0;                              //index for location in the line

    vector<Webpage*> pages;                                           //vector to hold objects of class Webpage
    map<string, set<string> > incominglinks;          
    map<string, set<string> > outgoinglinks;                      

    while(getline(index,filepath))                                    //parsing the files into individual classes
    {
      if(filepath.find_first_not_of("\t\n' '") == std::string::npos)
      {
        //case for spaces, tabs, and newline -> do nothing
      }
      else
      { 
        ifstream webpage(filepath.c_str());                              //opening file lines in index
        
        while(getline(webpage,line))
        {
          pages.push_back(new Webpage());                             //storing files as Webpage object     
            pages[i]->parsestring(line,filepath,incominglinks);         //storing words into Webpage objects
        }
        i++;
        webpage.close();
      }
    }

    Database* data = new Database();                                  //creating Database object

    vector<Webpage*>::iterator vec_it;                  //creating iterators
    map<string, set<string> >::iterator map_it;                       //creating iterators
    set<string>::iterator set_it;
    
    for(vec_it = pages.begin(); vec_it != pages.end(); vec_it++)
    {
      data->setFileword((*vec_it)->getFilename(), (*vec_it)->getWords()); //store maps of filename to word sets
      outgoinglinks.insert(pair<string,set<string> >((*vec_it)->getFilename(), (*vec_it)->getLinks()));      
    }

    data->setIncominglink(incominglinks);                           //storing incominglinks data into database
    data->setOutgoingLink(outgoinglinks);             //storing outgoinglinks into database

    //commands are case-sensetive
    while(getline(query,line))
    {
      string sub = line.substr(0,line.find(' '));                 //reads first line in the command
      line.erase(0,sub.length()+1);                                

      if(sub.find_first_not_of("\t\n' '") == std::string::npos)
      {
          output<<"Invalid query"<<endl;
      }
        else if(line == "")                       //case for searching for commands as single word
        {
          sub = converttolower(sub);
          set<string> wordlinkset;
          if(line != "")
          {
            output<<"Invalid query"<<endl;
          }
          else
          {
            data->createwordmap(sub);
              wordlinkset = data->getwordset(sub);
              wordlinkset = data->expandlinks(wordlinkset);

              data->pagerank(wordlinkset, restart, step, output);
              //printSet(wordlinkset,output);
          }
      }
        else if(sub == "AND")                     //case for AND command
      {
          set<string> intersectword;
          while(line[0]==' ')
          {
            line.erase(0,1);
          }

          line=converttolower(line);
          data->intersectsearch(line,intersectword);
          intersectword = data->expandlinks(intersectword);


          data->pagerank(intersectword, restart, step, output);
          //printSet(intersectword,output);         
      }
      else if(sub == "OR")                      //coase for OR command
      {
          set<string> unionword;
          while(line[0]==' ')
          {
            line.erase(0,1);
          }
          line = converttolower(line);
          data->unionsearch(line,unionword);
          unionword = data->expandlinks(unionword);

          data->pagerank(unionword,restart,step,output);
          //printSet(unionword,output);
      }
      else if(sub == "PRINT")
      {
          while(line[0]==' ')
          {
              line.erase(0,1);
          }

          sub = line.substr(0,line.find(' '));
          line.erase(0,sub.length()+1); 

          if(line != "")
          {
              output<<"Invalid query"<<endl;
          }
          else
          {   
              ifstream temp(sub.c_str());
              string line2 = "";

              output<<sub<<endl;
              if(temp.is_open())                                  //checking if the file exists
              {
                  while(getline(temp,line2))
                  {
                    while(line2.find("(") != std::string::npos)
                    {
                        line2.erase(line2.find("("),line2.find(')')-line2.find("(")+1);
                    }
                    output<<line2<<endl;
                  }
              }
              else  
              {
                  output<<"Invalid query"<<endl;
              }
              temp.close();
          }
      }
      else if(sub == "INCOMING")
      {
          while(line[0]==' ')
            {
            line.erase(0,1);
            }
            sub = line.substr(0,line.find(' '));
            line.erase(0,sub.length()+1);

            if(line != "")
            {
              output<<"Invalid query"<<endl;
            }
            else
            {
              auto map_it  = incominglinks.find(sub);
              if((map_it != incominglinks.end()))
              {  
                  printSet(map_it->second,output);
              }
              else
              {
                  output<<"Invalid query"<<endl;
              }
            }
      }
      else if(sub == "OUTGOING")
      {
          while(line[0]==' ')
          {
              line.erase(0,1);
            }
          sub = line.substr(0,line.find(' '));
          line.erase(0,sub.length()+1);
          if(line != "")
          {
              output<<"Invalid query"<<endl;
            }
            else
            {
              if(outgoinglinks.find(sub) != outgoinglinks.end())  
              {
                printSet(outgoinglinks[sub],output);
              }
              else
              {
                output<<"Invalid query"<<endl;
              }
            }
      }
      else                                                  //case for single words 
      {
          sub = converttolower(sub);
          set<string> wordlinkset;
        if(line != "")
        {
          output<<"Invalid query"<<endl;
        }
          else
          {
              data->createwordmap(sub);
              wordlinkset = data->getwordset(sub);
              wordlinkset = data->expandlinks(wordlinkset);  

              data->pagerank(wordlinkset, restart, step, output);
              //printSet(wordlinkset,output);
          }
      }
    }

    //deallocating memory
    for(vec_it = pages.begin(); vec_it != pages.end(); vec_it++)
    {
      delete *vec_it;
    }
    delete data;

    //closing files
    index.close();
    query.close();
    output.close();
}