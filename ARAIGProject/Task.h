#ifndef TASK_H
#define TASK_H
#include <string>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <list>
#include <iterator>
#include "Stimulation.h"


using namespace std;
namespace Project
{
    
    //Global variable to use for incrementing taskcount
    static int taskcount = 1;

    class Task  
    {
        string TaskName;
        std::list<stimulation*> stimcont;

        public:
        Task();
        Task(const string taskname);
        Task(const string taskname, std::list<stimulation*> ref);
        ~Task();
        stimulation* operator[](const int index);
		void dump(std::ostream& os);
        void execute(std::ostream& os);
        void removeStim(string stimname);
        string displayTaskName();
        Task(const Task& ref);
        Task& operator=(const Task&);
		Task(Task&&);
		Task&& operator=(Task&&);
		void operator+=(stimulation* ref);

    };

    //std::ostream& operator<<(std::ostream& os, const Stimulationst& s)
}
#endif