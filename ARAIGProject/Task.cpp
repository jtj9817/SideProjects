#include "Task.h"
#include <list>

using namespace std;
namespace Project
{
   //Without list
   Task::Task()
   {   
        TaskName = "Task " + std::to_string(taskcount);
        taskcount++;
        stimcont.clear();
   }

   Task::Task(const string taskname)
   {
      TaskName= taskname;
   }
   //With list
   Task::Task(const string taskname, std::list<stimulation*> ref)
   {
       stimcont = ref;
       //TaskName = "Task " + std::to_string(taskcount);
       TaskName = taskname;
       taskcount++;
       if(stimcont.empty())
       {
           cout << "stimcont is empty" << endl;
       }
   }

   Task::~Task()
   {
        while(!stimcont.empty()) 
        {
            //delete stimcont.front();
            stimcont.pop_front();
        }
            
       TaskName.clear();
       stimcont.clear();   
   }

 
   Task::Task(const Task& taskref)
   {
   		//cout << "Initializing Copy Constructor" << endl;
		TaskName.clear();
		stimcont.clear();
		
		*this = taskref;
   }

   Task& Task::operator=(const Task& src)
   {
        //cout << "Initializing copy assignment operator for a task" << endl;
        if(this != &src)
		{   
			TaskName.clear();
			
			stimcont = src.stimcont;
			TaskName = src.TaskName;
		}
		return *this;
   }

   Task::Task(Task&& tasksrc)
   {
   		//cout << "Initializing Move Constructor" << endl;
		stimcont = tasksrc.stimcont;
		TaskName = tasksrc.TaskName;
		
		tasksrc.stimcont.clear();
		tasksrc.TaskName.clear();
   }

   Task&& Task::operator=(Task&& tasksrc2)
	{
		if(&tasksrc2 != this)
			{
                //cout << "Initializing move assignment operator" << endl;
				//Free resources allocated for the current object
				stimcont.clear();
				TaskName.clear();
				
				//Perform move
				stimcont = std::move(tasksrc2.stimcont);
				TaskName = tasksrc2.TaskName;
				
				//Free resources of the reference object
				tasksrc2.stimcont.clear();
				tasksrc2.TaskName.clear();
			}
		return std::move(*this);
	}

	void Task::operator+=(stimulation* ref)
	{
            stimcont.push_back(ref);
	}

    
    void Task::dump(std::ostream& os)
    {   

        int x  = stimcont.size();
        cout << "stimcont of " << TaskName << " has " << x << " elements in it!" << endl;

        for(auto i: stimcont)
           {
               (*i).identify(std::cout);
           }
    }
    

    //Stimulations&
    stimulation* Task::operator[](const int index)
    {
        auto i = stimcont.begin();
        std::advance(i, index);
        return *i;
    }
    
    void Task::execute(std::ostream& os)
    {
        os << "Executing stimulation objects of " << TaskName << endl;
       for(auto i: stimcont)
        {
            (*i).display(std::cout);
        }
    }

    void Task::removeStim(string stimname)
    {
         std::list<stimulation*>::iterator iter;

        for(iter = stimcont.begin(); iter != stimcont.end(); iter++)
        {
            if ((*iter)->returnname() == stimname)
            {
                cout << "Removing element with stimname of " << stimname << endl;
                iter = stimcont.erase(iter);
            }
        }
    }

    string Task::displayTaskName()
    {
      return TaskName;
    }
    /*
    std::ostream& operator<<(std::ostream& os, const Stimulations& s)
	{
		s.display(os);
        return os;
    }
	*/
    
}