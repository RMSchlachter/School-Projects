#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cstring>
#include <bitset>

using namespace std;
void blockCipher(string inputFile, string keyString, string outputFile, string mode);
void streamCipher(string inputFile, string outputFile, string mode);

int main(int argc, char *argv[]) {

    if (argc == 6) {               // has 5 valid arguments + program name
        string inputFile = argv[2];
        string outFile = argv[3];
        string keyFile = argv[4];
        string mode = argv[5];
        stringstream key;

        ifstream inFileStream(inputFile);
        ifstream keyFileStream(keyFile);

        if(inFileStream.fail()) {
            perror("Bad input file");
            exit(1);
        }

        if(keyFileStream.fail()) {
            perror("Bad key file");
            exit(1);
        } else {
            key << keyFileStream.rdbuf();
            //cout << "key: " << key.str() << '\n';
        }

        // check cipher type (B or S)
        if (strcmp(argv[1], "B") == 0) {
            if((strcmp(argv[5],"E") != 0) || (strcmp(argv[5],"D") != 0)) {
                blockCipher(inputFile, key.str(), outFile, mode);
            }
        } else if (strcmp(argv[1], "S") == 0) {
            streamCipher(inputFile, outFile, mode);
        }

        inFileStream.close();
    } else {
        perror("Invalid amount of arguments");
        exit(1);
    }


    return 0;
}


void blockCipher(string inputFile, string keyString, string outputFile, string mode) {

    ifstream inFileStream(inputFile);
    ofstream outFile(outputFile);
    string binaryKeyString;
    for(unsigned int i = 0; i < keyString.size(); i++) {
        bitset<8> byte = (keyString.c_str()[i]);
        binaryKeyString += byte.to_string();
    }

    bitset<64> key(binaryKeyString.c_str());
    bitset<8> pad(0x80);
    char c;
    if(mode == "E") {
        while(inFileStream.get(c)) {
            if(c == EOF) {
                break;
            }
            string blockString;
            string line;
            getline(inFileStream, line);

            // GET ONE BYTE AT A TIME
            vector<bitset<8>> blockVector;
            for(size_t i = 0; i < line.size();) {
                for(size_t j = 0; j < 8; j++) {
                    if(line[j] == NULL) {
                        blockString += pad.to_string();
                        i++;
                    } else {
                        bitset<8> byte(line.c_str()[j]);
                        blockVector.push_back(byte);
                        blockString += byte.to_string();
                        i++;
                    }
                }
            }

            // XOR
            bitset<64> block(blockString);
            //cout << "block: " << block << '\n';

            bitset<64> xorBlock(block^=key);
            string xorString = xorBlock.to_string();
            //cout << "xorBlock: " << xorString << '\n';

            vector <bitset<8>> xorVector;
            for(int i = 0; i < 64;) {
                string temp;
                for(int j = i; j < (i+8); j++) {
                    temp += (char)xorString[j];
                    //cout << xorString[j] << " ";
                }

                i+=8;
                bitset<8> byte(temp);
                xorVector.push_back(byte);
                //cout << "byte: " << byte << '\n';
            }


            // SWAP
            int start = 0;
            int end = xorVector.size();
            //end -= 1;
            while(start < end) {
                if(xorVector[start].to_ulong() % 2 == 0) {
                    // dont swap
                    //cout << "xorVector[" << start << "] = " << xorVector[start].to_ulong()
                    //     << "% 2 == 0: no swap with " << xorVector[end].to_ulong() << '\n';
                    start++;
                    continue;
                } else if(xorVector[start].to_ulong() %2 == 1) {
                    // swap
                    //cout << "xorVector[" << start << "] = " << xorVector[start].to_ulong()
                    //     << "% 2 == 1: swap with " << xorVector[end].to_ulong() << '\n';
                    swap(xorVector[start], xorVector[end]);
                    start++;
                    end--;
                }
            }

            // WRITE
            string output;
            for(unsigned int i = 0; i < xorVector.size(); i++) {
                //cout << xorVector[i] << " " << xorVector[i].to_ulong() << '\n';
                output += (char)xorVector[i].to_ulong();
            }

            outFile << output;
        }
        outFile.close();
    } else {  // mode = "D"
        while (!inFileStream.eof()) {
            string blockString;
            string line;
            getline(inFileStream, line);

            //cout << "line size: " << line.length() << '\n';

            // GET ONE BYTE
            vector<bitset<8>> lineVector;
            string temp;
            for (unsigned int i = 0; i < line.size(); i++) {
                temp += line[i];
                bitset<8> byte(temp.c_str()[i]);
                lineVector.push_back(byte);
            }

            //cout << "temp size: " << temp.size() << '\n';
            //cout << "temp: " << temp << '\n';

            // SWAP
            int start = 0;
            int end = lineVector.size();
            //cout << "END: " << end << '\n';
            while (start < end) {
                if (lineVector[start].to_ulong() % 2 == 0) {
                    // dont switch
                    //cout << "lineVector[" << start << "] = " << lineVector[start].to_ulong()
                    //     << "% 2 == 0: no swap with " << lineVector[end-1].to_ulong() << '\n';
                    start++;
                    continue;
                } else if (lineVector[start].to_ulong() % 2 == 1) {
                    // swap
                    //cout << "lineVector[" << start << "] = " << lineVector[start].to_ulong()
                    //     << "% 2 == 1: swap with " << lineVector[end-1].to_ulong() << '\n';
                    swap(lineVector[start], lineVector[end]);
                    start++;
                    end--;
                }
            }

            // XOR
            for (unsigned int i = 0; i < lineVector.size(); i++) {
                bitset<8> byte(line.c_str()[i]);
                blockString += byte.to_string();
            }

            bitset<64> block(blockString);
            //cout << "block: " << block << '\n';

            bitset<64> resultBlock(block ^= key);
            string result = resultBlock.to_string();

            vector <bitset<8>> xorVector;
            for(unsigned int i = 0; i < 64;) {
                string temp;
                for(unsigned int j = i; j < (i+8); j++) {
                    temp += (char)result[j];
                    //cout << xorString[j] << " ";
                }

                i+=8;
                bitset<8> byte(temp);
                if(byte == '0x80') {
                    continue;
                } else {
                    xorVector.push_back(byte);
                }
            }

            // WRITE
            string output;
            for (unsigned int i = 0; i < xorVector.size(); i++) {
                //cout << result[i] << " " << resultVec[i].to_ulong() << '\n';
                output += (char)xorVector[i].to_ulong();
            }

            outFile << output;
        }
    }
}


void streamCipher(string inputFile, string outputFile, string mode) {

    // do encryption
    string hexKey = "0123456789ABCDEF";
    ifstream inFileStream(inputFile);

    ofstream outFile(outputFile);
    vector <bitset<8>> keyVector;
    vector<int> writeVector;
    string keyString;

    for(unsigned int i = 0; i < hexKey.size(); i++) {
        bitset<8> byte = (hexKey.c_str()[i]);
        keyString += byte.to_string();
    }

    vector <bitset<8>> xorVector;
    for(unsigned int i = 0; i < 64;) {
        string temp;
        for(unsigned int j = i; j < (i+8); j++) {
            temp += (char)keyString[j];
            //cout << xorString[j] << " ";
        }

        i+=8;
        bitset<8> byte(temp);
        keyVector.push_back(byte);
        //cout << "byte: " << byte << '\n';
    }


    //bitset<127> key(keyString.c_str());

    char c;
    unsigned int i = 0;

    string outString;
    string testString;


    while(!inFileStream.eof()) {

        while(inFileStream.get(c)) {
            if(i = 8) {
                i = 0;
            }

            bitset<8> inByte = c;
            int bit;

            for(unsigned int j = 0; j < 8; j++) {
                bit = inByte[7-j];
                //testString += to_string(bit);
                //bit ^= key[i];
                outString += to_string(bit);
            }

            bitset<8> byte(outString.c_str());
            byte ^= keyVector[i];
            outString = byte.to_ulong();
            outFile << outString;
            outString.clear();
            i++;
        }
    }
    inFileStream.close();
    outFile.close();

}