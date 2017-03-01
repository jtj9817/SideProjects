#ifndef ARAIG_H
#define ARAIG_H
#include <string>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <list>
#include <iterator>
#include <memory>
#include "Task.h"
#include "Stimulation.h"

using namespace std;
namespace Project
{
    class ARAIG_Sensors
    {
        string temptaskname;
        string identifier;
        string TaskIndicator;
        string stimidentifier;

        string templocname;
        string tempstimname;
        float tempfrq;
        float tempint;
        float tempdrn;

        //Arrays to be dynamically allocated then populated
        stimulation** stimobjects;
        Task* taskobjects;

        int stimlinescount;
        int tasklinescount;

        //Create temporary pointers to a Task and Stims object
        //Task* temptask  = nullptr;
        stimulation* tempstims = nullptr;
        //Variable to use for getTask w/ Reference-type function return 
        Task reftask;

        public:
        ARAIG_Sensors(const string stimfn, const string taskfn);
        void dump(std::ostream& os);
        ~ARAIG_Sensors();
		Task& getTask(string taskname);
        int returnTaskLines();
        bool find(string taskid);

    };
}
#endif