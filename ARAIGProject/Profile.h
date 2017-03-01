#ifndef PROFILE_H
#define PROFILE_H
#include <string>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <list>
#include <iterator>
#include <memory>
#include <vector>
#include <chrono>
#include <ctime>
#include "Task.h"
#include "Stimulation.h"
#include "ARAIGSensors.h"

using namespace std;
namespace Project
{
    class Profile
    {
        std::vector<Task*> ToRun;
        std::vector<Task*> Completed;
		
        string studFirstName;
        string studLastName;
        string studNum;
        string instFirstName;
        string instLastName;
        string empNum;
        string temp;

        //Pointers
        Task* taskptr = nullptr;
		
        int calMin;
        int calMax;
         

        string usrresponse;
        //Counter used to keep track if user is executing tasks sequentially
        int seqtaskincr;
        //Counter used to keep track of last executed task 
        int counterlast;

        public:
        Profile();
        Profile(const string pfn, ostream&, ARAIG_Sensors&);
		void completedTasks();
		void queueTask();
        void nextTask(const string taskqueue);

        //'run' function is execFlightPlan; nextTask is the function used to both activate the Task
		//and then move the Task to the Completed container. queueTask function
		//displays the remaining Tasks to be executed.
        void execFlightPlan();
        void writeprogress(const string usrfn);
        void Menu();
        void displayLast();
        void displayNext();
        void displayPendingTasks();
    };
}
#endif