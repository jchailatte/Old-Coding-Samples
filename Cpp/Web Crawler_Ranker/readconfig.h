#include <iostream>
#include <map>
#include <string>
#include <fstream>
#include <algorithm>

void parstring(std::string line, std::map<std::string,std::string> &param)
{
	int i = 0;

	std::string parameter = "";
	std::string value = "";

	line.erase(std::remove_if(line.begin(), line.end(),isspace), line.end());

	while(line.length() != 0)
	{

		if(line[i] == '#')
		{
			if(i==0)
			{
				return;
			}
			else
			{
				value = line.substr(0,i);
				param.insert(std::pair<std::string,std::string>(parameter,value) );
				return;
			}
		}
		else if(line[i] == '=')
		{
			parameter = line.substr(0,i);
			line.erase(0,i+1);
			i=0;
			
		}
		else if(i == (int)line.length())
		{
			value = line.substr(0,i);
			param.insert(std::pair<std::string,std::string>(parameter,value));
			return;
		}
		else
		{
			i++;
		}
	}
}


std::map<std::string,std::string> readConfig(int argc, char *argv[])
{
	std::string line = "";
	std::ifstream config;
	std::map<std::string,std::string> param;

	if(argc == 1)
	{
		config.open("config.txt");
	}
	else
	{
		config.open(argv[1]);
	}

	while(getline(config,line))
	{
		parstring(line,param);
	}
	return param;
}
