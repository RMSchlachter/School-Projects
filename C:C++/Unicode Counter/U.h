// U.h
#include <map>
#include <string>
#include <set>
typedef unsigned long long int ull;

//Create class
class U {
	public:
		std::string utf8encode(ull num);
		//void fillmap(std::string key, std::map <std::string, int> countMap);
		//void searchMap(std::string key, std::map <std::string, std::string> propertyMap, std::map <std::string, int> countMap);

		U();
		U(const U &copy);
		U(std::string filename, std::string literal);
		U &operator=(const U&);
		~U();

		void readstring(std::string literal);
		void readfile(std::string filename);
		void propfile(std::string propfile);
		int size();
		std::string get();
		std::string get(int index);
		std::string get(int first, int last);
		int propcount(std::string propname);
		void printmaps();
		std::set<std::string> props();
		void print();

				
		int size() const;
		std::string get() const;
		std::string get(int index) const;
		std::string get(int first, int last) const;
		int propcount(std::string propname) const;
		std::set<std::string> props() const;
		
	private:
		std::map<std::string, std::string> propertyMap;
		std::map<std::string,int> countMap;
		std::string accumulated;
		std::string readfirst;
		std::set<std::string> returnset;
};