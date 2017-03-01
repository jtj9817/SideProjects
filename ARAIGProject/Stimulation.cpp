#include <string>
#include <iostream>
#include <iomanip>
#include <fstream>
#include "Stimulation.h"

using namespace std;
namespace Project
{
        //Parameters as follows: Stims(name, location, intensity, frequency, duration)
        Stims::Stims(const string name, const string loc, const float intsty, const float frq, const float drn)
        {
            stimsobject = name;
            intensity = intsty;
            frequency = frq;
            duration = drn;
            temploc = loc;
        }
        Stims::~Stims()
        {
            stimsobject.clear();
            intensity = 0.0;
            frequency = 0.0;
            duration = 0.0;
        }
        void Stims::display(std::ostream& os) const
        {
            os << "Type: stim" << std::endl;
            os << "Name: " << stimsobject << std::endl;
            os << "Location: " << temploc << std::endl;
            os << "Intensity: " << setw(2) << setprecision(2) << intensity << std::endl;
            os << "Frequency: " << setw(2) << setprecision(2) << frequency << std::endl;
            os << "Duration: " <<  setw(2) <<  setprecision(2) << duration << std::endl;
        }
        
        void Stims::identify(std::ostream& os) const
        {
            os << stimsobject << std::endl;
        }

        string Stims::returnname() const
        {
            return stimsobject;
        }

        Exoskeleton::Exoskeleton(const string exname, const float intsty, const float drn)
        {
            exoname = exname;
            intensity = intsty;
            duration = drn;
        }

        Exoskeleton::~Exoskeleton()
        {
            exoname.clear();
            intensity = 0.0;
            duration = 0.0;
        }

        void Exoskeleton::display(std::ostream& os) const
        {
            os << "Type: exoskeleton" << std::endl;
            os << "Name: " << exoname << std::endl;
            os << "Intensity: " << setw(2) << setprecision(2) << intensity << std::endl;
            os << "Duration: " <<  setw(2) <<  setprecision(2) << duration << std::endl;
        }

        void Exoskeleton::identify(std::ostream& os) const
        {
            os << exoname << std::endl;
        }

        string Exoskeleton::returnname() const
        {
            return exoname;
        }
}