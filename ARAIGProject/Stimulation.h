#ifndef STIMULATION_H
#define STIMULATION_H
#include <string>
#include <iostream>
#include <iomanip>
#include <fstream>


using namespace std;
namespace Project
{
    class stimulation
    {
        protected:
        string stimconf;
        public:
        virtual void display(std::ostream& os) const = 0;
        virtual void identify(std::ostream& os) const = 0;
        virtual string returnname() const = 0;
    };

    class Stims: public stimulation
    {
        protected:
        string stimsobject, temploc;
        enum location{ABS, FRONT, BACK, TRAPS};
        float intensity, frequency, duration;

        public:
        Stims(const string name, const string loc, const float intsty, const float frq, const float drn);
        ~Stims();
        void display(std::ostream& os) const;
        void identify(std::ostream& os) const;
        string returnname() const;
    };
    
    
    class Exoskeleton:  public stimulation
    {
        protected:
        string exoname;
        float intensity, duration;

        public:
        Exoskeleton(const string exname, const float intsty, const float drn);
        ~Exoskeleton();
        void display(std::ostream& os) const;
        void identify(std::ostream& os) const;
        string returnname() const;
    };
    
}
#endif