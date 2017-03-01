#include "Task.h"
#include "ARAIGSensors.h"
#include "Stimulation.h"
#include "Profile.h"

using namespace std;
using namespace Project;
 int main (int argc, char* argv[]) {
     if (argc == 1) {
         std::cerr << argv[0] << ": missing file operand\n";
         return 1;
     }
     //argc should be 5; Main StimulationConfig.csv TaskConfiguration.csv SampleProfileConfiguration.csv StudentReport.txt
     else if (argc != 5) {
         std::cerr << argv[0] << ": incorrect number of arguments\n";
         return 2;
     }

    //Create an ARAIG object
	ARAIG_Sensors ARAIGObj(argv[1], argv[2]);

    //Create a profile object
    Profile ProfObj(argv[3], std::cout, ARAIGObj);

    //Initialize Menu selection from Profile.cpp
    ProfObj.Menu();
    
    //Pass on the output file to write on to
    ProfObj.writeprogress(argv[4]);

     /*
     std::cout << "\nPress any key to continue ... ";
     std::cin.get();
     */
 }





