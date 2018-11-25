#include <iostream>
#include <fstream>
#include <sstream>
#include <stdlib.h>
#include <string>

using namespace std;

struct Warrior {
    string weapon;
    int power;

};

//Feel free to modify the parameters if you need other values
bool skirmish(Warrior*** protectors, Warrior*** invaders, int skirmish_row,
		      int prorows, int procols, int invrows, int invcols, int &reserves, ofstream &output)
{
    //returns true if the invaders breach the walls.

    for(int i=0; i<invcols; i++)
    {
		//checking if there is no attacking invader
        if(invaders[skirmish_row][i] == NULL)
        {
            output<<"No assault"<<endl;
            return false;
        }
		//checking if there is no defending protector
        if(protectors[i][skirmish_row] == NULL)
        {
            return true;
        }
	
		//creating values for weapons and power
        string pw = protectors[i][skirmish_row]->weapon;
        string iw = invaders[skirmish_row][i]->weapon;

        int pp = protectors[i][skirmish_row]->power;
        int ip = invaders[skirmish_row][i]->power;

		//case for when inv and pro have the same weapom
        if(pw == iw)
        {
			//if pro has more power than the invader -> invader killed
            if(pp > ip)
            {
                output<<"Invader killed"<<endl;
                delete invaders[skirmish_row][i];
                invaders[skirmish_row][i] = NULL;
            }
            //if inv has more power than protector -> check for defection -> apply reserves
            else if(pp < ip)
            {
                for(int e=0; e<invrows; e++)
                {
                    for(int d =0; d<invcols; d++)
                    {
                        if(invaders[e][d] == NULL)
                        {
                            invaders[e][d] = new Warrior();
                            invaders[e][d]->weapon = pw;
                            invaders[e][d]->power = pp;
                            output<<"Protector defected"<<endl;

                            if(reserves > 0)
                            {
                            	protectors[i][skirmish_row]->weapon = "axe";
                   				protectors[i][skirmish_row]->power = 100;
                                reserves = reserves - 1;
                            }
                            else
                            {
                 				delete protectors[i][skirmish_row];
                                protectors[i][skirmish_row] = NULL;
                            }
                            goto test;
                        }
                    }
                }
                output<<"Protector killed"<<endl;
                if(reserves > 0)
                {
                	protectors[i][skirmish_row]->weapon = "axe";
                   	protectors[i][skirmish_row]->power = 100;
                    reserves = reserves - 1;
                }
                else
                {
                    delete protectors[i][skirmish_row];                	
                    protectors[i][skirmish_row] = NULL;
                }

            }
            //draw if they have same power
            else
            {
                output<<"Duel ends in draw"<<endl;
            }
        }
        //if they don't have the same weapon -> case for when inv has an axe
        else if(pw == "axe")
        {
            output<<"Invader killed"<<endl;
            delete invaders[skirmish_row][i];
            invaders[skirmish_row][i] = NULL;
        }
        else if(iw == "axe")
        {
                for(int e=0; e<invrows; e++)
                {
                    for(int d =0; d<invcols; d++)
                    {
                        if(invaders[e][d] == NULL)
                        {
                            invaders[e][d] = new Warrior();
                            invaders[e][d]->weapon = pw;
                            invaders[e][d]->power = pp;
                            output<<"Protector defected"<<endl;

                            if(reserves > 0)
                            {
                                protectors[i][skirmish_row]->weapon = "axe";
                   				protectors[i][skirmish_row]->power = 100;
                                reserves = reserves - 1;
                            }
                            else
                            {
                     			delete protectors[i][skirmish_row];       	
                                protectors[i][skirmish_row] = NULL;
                            }
                            goto test;
                        }
                    }
                }
                output<<"Protector killed"<<endl;
                if(reserves > 0)
                {
                    protectors[i][skirmish_row]->weapon = "axe";
                    protectors[i][skirmish_row]->power = 100;
                    reserves = reserves - 1;
                }
                else
                {
                	delete protectors[i][skirmish_row];
                    protectors[i][skirmish_row] = NULL;
                }

        }
        //when the pro has an axe
        else
        {
            return false;
        }
        test: ;
    }
    return false;
}

void assignweapon(Warrior*** side, int rows, int cols,int c)
{
    for(int i = 0; i<rows; i++)
    {
        if(((i%2 == 0) && (c==1)) || ((i%2 != 0) && (c==2)))
        {
            for(int j=0; j<cols;j++)
            {
                side[i][j]->weapon = "axe";
            }
        }
        else
        {
            for(int j=0; j<cols;j++)
            {
                side[i][j]->weapon = "sword";
            }
        }
    }
}

void assignpower(Warrior*** side, int rows, int cols)
{
    for(int i=0; i<rows; i++)
    {
        for(int j=0; j<cols; j++)
        {
            side[i][j]->power = i*10+(j+1)*10;
        }
    }
}

int main(int argc, char* argv[])
{
    if (argc < 3) {
	   cerr << "Please provide an input and output file" << endl;
	   return -1;
    }

    ifstream input(argv[1]);
    ofstream output(argv[2]);

    int prorows = 0;
    int procols = 0;
    int invrows = 0;
    int invcols = 0;
    int reserve = 0;
    int skirmishes = 0;

    string line,values;

    //read the input file and initialize the values here.
    if(input.is_open())
    {
        stringstream ss;
        ss.str("");
        ss.clear();

        getline(input,line);
        ss << line;
        ss >> prorows;
        invcols = prorows;

        ss.str("");
        ss.clear();
        getline(input,line);
        ss << line;
        ss >> procols;
        invrows = procols;

        ss.str("");
        ss.clear();
        getline(input,line);
        ss << line;
        ss >> reserve;

        ss.str("");
        ss.clear();
        getline(input,line);
        ss << line;
        ss >> skirmishes;
	}

    Warrior ***protectors;
    Warrior ***invaders;

    //initialize the protectors and invaders here.

    protectors = new Warrior**[prorows];
    for(int i=0; i<prorows; i++)
    {

        protectors[i] = new Warrior*[procols];

        for(int j=0;j<procols;j++)
        {
            protectors[i][j] = new Warrior();
        }

    }
	//assigning weapons and power to each protector
    assignweapon(protectors,prorows,procols,1);
    assignpower(protectors,prorows,procols);

    invaders = new Warrior**[invrows];
    for(int i=0; i<invrows; i++)
    {
        invaders[i] = new Warrior*[invcols];
        for(int j=0;j<invcols;j++)
        {
            invaders[i][j] = new Warrior(); 
        }
    }
	
	//assigning weapons and power to each porotector
    assignweapon(invaders,invrows,invcols,2);
    assignpower(invaders,invrows,invcols);

    bool winner = true;
    for (int i=1; i <= skirmishes; i++)
    {
        int skirmish_row = 0;
        
        //assigning value to skirmish_row
        if(input.is_open())
        {
            stringstream ss;
            ss.str("");
            ss.clear();
            getline(input,line);
            ss << line;
            ss >> skirmish_row;
        }
        //In general, it is bad style to throw everything into your main function
        bool end = skirmish(protectors, invaders, skirmish_row, prorows, procols, invrows, invcols, reserve, output);

		//end loop as soon as invaders win
        if(end == true)
        {
            output<<"Winner: invaders"<<endl;
            winner = false;
            break;
        }
    }
    
    //output the winner and deallocate your memory.
    if(winner == true)
    {
        output<<"Winner: protectors"<<endl;
    }

    //deallocate memory for protectors
    for (int i = 0; i < prorows; i++)
    {
    	for (int j = 0; j < procols; j++)
		{
	    	delete protectors[i][j];
	  
		}
    }

    for (int i = 0; i < prorows; i++)
    {
    	delete [] protectors[i];
    }

    delete [] protectors;

    //deallocate memory for invaders
    for (int i = 0; i < invrows; i++)
    {
    	for (int j = 0; j < invcols; j++)
		{
	  		delete invaders[i][j];	        
		}
    }


    for (int i = 0; i < invrows; i++)
    {
    	delete [] invaders[i];
    }

    delete [] invaders;

    input.close();
    output.close();

    return 0;
}
