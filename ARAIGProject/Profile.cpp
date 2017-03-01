#include "Profile.h"

using namespace std;
using std::chrono::system_clock;
namespace Project
{
		Profile::Profile()
		{
			studLastName.clear();
			studNum.clear();
			instFirstName.clear();
			instLastName.clear();
			empNum.clear();
			
			calMin = 0;
			calMax = 0;

			seqtaskincr = 0;

			ToRun.clear();
			Completed.clear();
		}
        Profile::Profile(const string pfn, ostream& os, ARAIG_Sensors& arg)
        {

			std::ifstream fileread;
			int linescount = 0;
			string tempdump;
			fileread.open(pfn, ios::in);
				while(fileread.good())
				{
					getline(fileread, tempdump, '\n');
					linescount++;
				}
			fileread.close();

			//Debug lines counted to verify file integrity
			cout << "Sample Profile Config file has " << linescount << " lines" << endl;

			fileread.open(pfn, ios::in);

					//Placeholder variables for parsing
					string tempval;
					string tempfn;
					int taskcounter = 0;
					
					getline(fileread, studFirstName, ',');
					getline(fileread, studLastName, ',');
					getline(fileread, studNum, '\n');
						
					getline(fileread, instFirstName, ',');
					getline(fileread, instLastName, ',');
					getline(fileread, empNum, '\n');
					
					fileread >> calMax;
					fileread.ignore();
					fileread >> calMin;
					fileread.ignore();

					linescount -= 3;
					for(int i = 0; i < linescount; i++)
					{
						fileread >> temp;
						if(temp.substr(0,4) == "Task")
						{
						//Debug statement to make sure 'Task' keyword was parsed 
						//cout << "Task keyword was encountered" << endl;
							if(arg.find(temp))
								{
									taskptr = new Task(arg.getTask(temp));
									//Debug statement to make sure that the Task was properly created
									//cout << taskptr->displayTaskName() << " was created." << endl;
									ToRun.push_back(taskptr);
									taskcounter++;
								}
						}
					}
			fileread.close();
			ToRun.pop_back();
			seqtaskincr = 0;
			counterlast = 0;
			
        }

        void Profile::completedTasks()
		{
			if(Completed.empty())
			{
				cout << "Completed tasks container is empty." << endl;
				cout << endl;
				cout << endl;
				Menu();
			}
			for(int i = 0; i != Completed.size(); i++)
			{
				cout << Completed[i]->displayTaskName() << endl;
			}
			cout << endl;
			Menu();
		}
		
		void Profile::queueTask()
		{
			for(int i = 0; i != ToRun.size(); i++)
			{
				cout << ToRun[i]->displayTaskName() << endl;
			}
		}
		
        void Profile::nextTask(const string taskqueue)
		{
			if(ToRun.empty())
			{
				cout << "Congratulations! No more tasks to be completed!" << endl;
				cout << endl;
				Menu();
			}
			for(int i = 0; i != ToRun.size(); i++)
			{
				if(ToRun[i]->displayTaskName() == taskqueue)
				{
					ToRun[i]->execute(std::cout);
					Completed.push_back(std::move(ToRun[i]));
					ToRun.erase(ToRun.begin()+i);

					counterlast++;

					cout << endl;
					Menu();
				}
			}
		}
		
        void Profile::execFlightPlan()
		{	
			bool check = false;
			while(check == false)
			{
				string taskqueue;
				cout << "Displaying tasks ready to be executed." << endl;
				queueTask();
				cout << "Enter task to be executed: " ;
				cin >>  taskqueue;
				if(taskqueue == ToRun[seqtaskincr]->displayTaskName())
				{
					check = true;
					nextTask(taskqueue);	
				}
				else
				{
					string x = ToRun[seqtaskincr]->displayTaskName();
					cout << "Task you entered is incorrect. Try again." << endl;
					cout << "Task that should be done is: " << x << endl;
					cout << endl;
					check = false;
				}
			}
		}

		void Profile::Menu()
		{
			cout << "Welcome to the ARAIG Flight Simulator Program." << endl;
			cout << "Student Name: " << studFirstName << " " << studLastName << endl;
			cout << "Student Number: " << studNum << endl;
			cout << "Instructor: " << instFirstName << " " << instLastName << endl;
			cout << "Employee Number: " << empNum << endl;
			cout << "Maximum Calibration: " << calMax << endl;
			cout << "Minimum Calibration: " << calMin << endl;
			cout << "Select a number from the following options: " << endl;
			cout << "1. Start flight simulator." << endl;
			cout << "2. Display completed tasks." << endl;
			cout << "3. Display pending tasks to be completed." << endl;
			cout << "4. Display last task completed. " << endl;
			cout << "5. Display next task that should be executed " << endl;
			cout << "6. Exit " << endl;
			cout << "Enter your choice: ";
			cin >> usrresponse;
			try
			{
				if(usrresponse == "1")
						execFlightPlan();
				else if(usrresponse == "2")
						completedTasks();
				else if(usrresponse == "3")
						displayPendingTasks();
				else if(usrresponse == "4")
						displayLast();
				else if(usrresponse == "5")
						displayNext();
				else if(usrresponse == "6")
					{
						cout << "\nPress any key to continue ... " << endl;
						cin.get();
					}
				else
					throw "Invalid input";
			}
			catch(char const* msg)
			{
				cout << "Invalid input. Terminating program." << endl;
			}
			/*Debugging statement to make sure that ToRun container has been properly populated
			for (int i = 0; i < ToRun.size(); i++)
			{
        		ToRun[i]->dump(cout);
    		}  */
		}
		
		void Profile::writeprogress(const string usrfn)
		{
			//Timestamp for progress report
			/*
			system_clock::time_point today = system_clock::now();
			std::time_t curtime;
			curtime = std::chrono::system_clock::to_time_t(today);
			string timestamp = ctime_s(&curtime);
			*/
			ofstream writefile;
			writefile.open(usrfn);
			writefile << "The following tasks are completed by student " << studFirstName << " " << studLastName << endl;
			writefile << "Student was supervised by " << instFirstName << " " << instLastName << endl;
			//writefile << "Task completion was done on " << timestamp << endl;

			for(int i = 0; i < Completed.size(); i++)
			{
				writefile << Completed[i]->displayTaskName() << endl;
			}
			writefile.close();
		}

		void Profile::displayLast()
		{
			if(!Completed.empty())
			{
				string x = Completed[counterlast-1]->displayTaskName();
				cout << x << endl;
				cout << endl;
				Menu();
			}
			cout << "Completed tasks container is empty. No tasks found." << endl;
			cout << endl;
			Menu();
		}

		void Profile::displayNext()
		{
			string x = ToRun[seqtaskincr]->displayTaskName();
			cout << x << " is the task to be executed." << endl;
			cout << endl;
			cout << endl;
			Menu();
		}

		void Profile::displayPendingTasks()
		{
			for(int i = 0; i != ToRun.size(); i++)
			{
				cout << ToRun[i]->displayTaskName() << endl;
			}
			cout << endl;
			cout << endl;
			Menu();
		}
}