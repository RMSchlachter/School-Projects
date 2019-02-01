    #include "U.h"
    #include <iostream>
    using namespace std;
    const string pub="/s/bach/a/class/cs253/pub/UnicodeData.txt";   // ~ only works in shells

    int main() {

        try {
            // works
            U temp;
            //U temp(pub, "a³+b³≠c³");

            // works
            //cout << "woof:" << temp.propcount("woof") << '\n';

            //temp.readfile("/s/bach/a/class/cs253/pub/hamlet.txt");
            const string str1("/s/bach/a/class/cs253/pub/hamlet.txt");
            
            temp.readfile("second.txt");
            temp.propfile("/s/bach/a/class/cs253/pub/UnicodeData.txt");
            
            //temp.readfile("first.txt");
           // temp.print();

            // works
            //temp.get();
            const U foo = temp;
            U bar(temp);
            cout << "\ntemp.get: " << temp.get() << '\n';
            cout << "foo.get(): " << foo.get() << "\n\n";

            //temp.get(400);
            //cout << "propCount: " << temp.propcount("Lu") << '\n';
            //cout << "propCount: " << foo.propcount("Lu") << '\n';
            //cout << "propCount: " << bar.propcount("Lu") << '\n';
            //cout << temp.props() << '\n';
            //cout << foo.props() << '\n';
            //cout << bar.props() << '\n';

            cout << "temp.get(1): " << temp.get(1) << '\n';
            cout << "foo.get(1): " << foo.get(1) << "\n\n";

            cout << "temp.get(2): " << temp.get(3) << '\n';
            cout << "foo.get(2): " << foo.get(3) << "\n\n";

            cout << "temp.get(3,5): " << temp.get(3,5) << '\n';
            cout << "foo.get(3,5): " << foo.get(3,5) << "\n\n";

            cout << "temp.propcount(): " << temp.propcount("Sm") << '\n';
            cout << "foo.propcount(): " << foo.propcount("Sm") << "\n\n";

            temp.props();
            foo.props();



           // cout << "Should be 8: " << temp.size() << '\n'
           //      << "Should be 2: " << temp.propcount("Sm") << '\n'
           //      << "Should be b³: " << temp.get(3,5) << '\n';

           // cout << "Should be 8: " << foo.size() << '\n'
           //      << "Should be 2: " << foo.propcount("Sm") << '\n'
           //      << "Should be b³: " << foo.get(3,5) << '\n';

            //cout << "Should be 8: " << bar.size() << '\n'
            //     << "Should be 2: " << bar.propcount("Sm") << '\n'
            //     << "Should be b³: " << bar.get(3,5) << '\n';
        }

        catch (string error) {
            cout << "Caught an error: “" << error << "”\n";
        }

        return 0;
    }