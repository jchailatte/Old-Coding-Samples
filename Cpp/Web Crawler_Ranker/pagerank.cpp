#include <iostream>
#include <set>
#include <map>
#include <vector>
#include <fstream>
#include <algorithm>

#include "setutility.h"
#include "search.h"
#include "database.h"

using namespace std;

void Database::pagerank(set<string> candidateset,double restart,int step, ofstream &output)
{
	int setsize = (int)candidateset.size();
	int i = 0;											//index
	double n = 0;										//number of outgoing links

	map<string,int> candidateindex;						//map to assign an index to each webpage
	set<string>::iterator set_it1;
	set<string>::iterator set_it2;

	double pathprob[setsize][setsize];			//2D array to hold each pages outgoing probability
	double prob[setsize];						//1D array to hold each pages probability
	double tempprob[setsize];					//1D array to temp hold the updated probability values

	for(int i=0; i<setsize; i++)				//initializing initial page probability (STEP = 0)
	{
		double size = (double)candidateset.size();
		prob[i] = 1/size;
	}

	for(int i=0; i<setsize; i++)				//set every value in the 2D array to 0
	{
		for(int j=0; j<setsize; j++)
		{
			pathprob[i][j]=0;
		}
	}

	for(set_it1 = candidateset.begin(); set_it1 != candidateset.end(); set_it1++)	//assigning an index to each page
	{
		candidateindex.insert(pair<string, int>(*set_it1,i));
		i++;
	}

	for(set_it1 = candidateset.begin(); set_it1 != candidateset.end(); set_it1++)	//assigning values for each entry in 2D matrix
	{

		n =	(int)intersectSet(outlink[*set_it1],candidateset).size();									
		if(outlink[*set_it1].find(*set_it1) == outlink[*set_it1].end())			//check if a self-loop already exists
		{
			n = n + 1;
		}

		pathprob[candidateindex[*set_it1]][candidateindex[*set_it1]] = 1/n;

		for(set_it2 = outlink[*set_it1].begin();set_it2 != outlink[*set_it1].end(); set_it2++)
		if(candidateset.find(*set_it2) != candidateset.end())
		{
			pathprob[candidateindex[*set_it1]][candidateindex[*set_it2]] = 1/n;
		}
	}

	for(int i=0; i<step; i++)									//calculating probability
	{
		for(int a=0; a<setsize; a++)
		{
			double temp = 0;
			for(int b=0; b<setsize;b++)
			{
				temp = (pathprob[b][a] * prob[b])+temp;
			}
			tempprob[a]=temp;
		}

		for(int c =0; c < setsize; c++)
		{
			tempprob[c]= (tempprob[c]*(1-restart)) +  restart/n;
			prob[c] = tempprob[c];
		}
	}

	//doing this way to account for multikeys

	vector<pair<double,string> > pairings;

	for(map<string,int>::iterator map_it = candidateindex.begin(); map_it != candidateindex.end(); map_it++)	
	{															//insert the pages into array based on their index
		pairings.push_back(make_pair(prob[map_it->second],map_it->first));
	}

	stable_sort(pairings.rbegin(), pairings.rend());			//sorting the vector based off of the probability

	output<<setsize<<endl;										//output function
	for (int i=0; i<setsize; i++)
    {
        output<<pairings[i].second<<endl;
    }
 

}