#include "Stimulation.h"
#include "Task.h"
#include "ARAIGSensors.h"


using namespace std;
namespace Project
{

    ARAIG_Sensors::ARAIG_Sensors(const string stimfn, const string taskfn)
    {
        if(stimfn.empty() || taskfn.empty())
		{
			std::cout << "Filename is empty! " << std::endl;
		}
		else
		{
            //Block for creating dynamic array of stim objects
            stimlinescount = 0;
			string temp;
			std::fstream fileread;

			fileread.open(stimfn, ios::in);
			while(!fileread.eof())
			{
				getline(fileread, temp, '\n');
				stimlinescount++;
			}
            fileread.close();

            stimlinescount--;
            //Debug statement to confirm lines counted is the same as intended
            //cout << "Stimulation Config file has " << stimlinescount << " lines" << endl;

			//Allocate dynamic memory for the stimobjects array
			stimobjects = new stimulation*[stimlinescount];
            
			//Reinitialize the file
            fileread.open(stimfn, ios::in);
			for(int i =0; i < stimlinescount; i++)
			{
                    //Identify what type of Stimulation object
					getline(fileread, identifier, ',');

                    //Block for conditional statements for types 'stim' or 'Exoskeleton'
                    if(identifier == "stim")
                    {
                        getline(fileread, tempstimname, ',');
                        getline(fileread, templocname, ',');

                        fileread >> tempint;
                        fileread.ignore();
                        fileread >> tempfrq;
                        fileread.ignore();
                        fileread >> tempdrn;
                        fileread.ignore();
                        
                        stimobjects[i] = new Stims(tempstimname, templocname, tempint, tempfrq, tempdrn);
                    }
                    else if(identifier == "exoskeleton")
                    {
                        getline(fileread, tempstimname, ',');

                        fileread >> tempint;
						fileread.ignore();
						fileread >> tempdrn;
						fileread.ignore();

						stimobjects[i] = new Exoskeleton(tempstimname, tempint,tempdrn);
                    }
                    //End of block
					fileread.ignore();
			}
			fileread.close();
            //End of block for creating dynamic array of stim objects

			//Find a sample Stim object to verify that the dynamic array is working
            /*
			for(int i = 0; i != stimlinescount; i++)
			{
				if(stimobjects[i]->returnname() == "Sim30")
					cout << "Result is found: " << stimobjects[i]->returnname() << endl;
			}*/

            //Start of block for creating a list of Task objects
			
			//Create containers to hold string names
            std::list<string> tasks;
            std::list<string> temps;

            tasklinescount = 0;
            int tempcounter = 0;
            int arrayincr = -1;
			string temp2;
			std::fstream taskread;

            taskread.open(taskfn, ios::in);
            while(taskread.good())
            {
                taskread >> TaskIndicator;
                tempcounter++;
                if(TaskIndicator.substr(0,4) == "TASK")
                    tasklinescount++;
            }
            taskread.close();
            tempcounter--;

            //Debug statement to make sure that TASK keywords were identified
            //cout << "Task configuration file has " << tasklinescount << " TASK keywords" << endl;

            //Initialize dynamic array of Task objects
            taskobjects = new Task[tasklinescount];


            taskread.open(taskfn, ios::in);
			while(taskread.good())
			{
                taskread >> TaskIndicator;
				if(TaskIndicator.substr(0,4) == "TASK")
                {
                    arrayincr++;
                    temptaskname = TaskIndicator.substr(5);
                    //Debug statement for TASK keyword and TASK name 
                    //cout << "TASK keyword was encountered and the TASK name is " << temptaskname << endl;
                    std::shared_ptr<Task> temptask(new Task(temptaskname));
                    //Debug statement to make sure that shared pointer worked
                    //cout << temptask->displayTaskName() << "  was created." << endl;
                    taskobjects[arrayincr] = *temptask;
                }
                else
                {
                    for(int x = 0; x < stimlinescount; x++)
                    {
                        if (stimobjects[x]->returnname() == TaskIndicator)
                        {
                            tempstims = stimobjects[x];
                            taskobjects[arrayincr] += tempstims;
                            //Debug statement to make sure that the stimms parsed was properly added to the Task designated
                            //cout << "A stims object was added to " << taskobjects[arrayincr].displayTaskName() << endl;
                        }
                    }
			    }
            }//End of stimobjects block
            taskread.close();
        }//End bracket for else statement of ARAIG_Sensors Constructor 

            //Debug statement that stimobjects array was properly populated
            /*
            for(int i = 0; i < stimlinescount; i++)
            {
                stimobjects[i]->identify(std::cout);
            }
            */
            //Debug statement that taskbojects array was properly populated
            /*
            string x;
            for(int i = 0; i < tasklinescount; i++)
            {
                x = taskobjects[i].displayTaskName();
                cout << x << endl;
            }*/
            
    }

    void ARAIG_Sensors::dump(std::ostream& os)
    {
        for(int i = 0; i < tasklinescount; i++)
        {
            taskobjects[i].dump(std::cout);
        }
    }

	Task& ARAIG_Sensors::getTask(string taskname)
	{
		for(int i = 0; i < tasklinescount; i++)
		{
			if(taskobjects[i].displayTaskName() == taskname)
				 reftask = taskobjects[i];
		}
        return reftask;
	}

    ARAIG_Sensors::~ARAIG_Sensors()
    {
        delete [] stimobjects;
		delete [] taskobjects;
    }

    int ARAIG_Sensors::returnTaskLines()
    {
        return tasklinescount;
    }
    
    bool ARAIG_Sensors::find(string taskid)
    {
        for(int i = 0; i < tasklinescount; i++)
		{
			if(taskobjects[i].displayTaskName() == taskid)
				return true;
		}
        return false;
    }
}