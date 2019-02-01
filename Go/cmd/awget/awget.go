package main

import (
	"net/http"
	"log"
	"os"
	"flag"
	"ssnet/ssnet"
)


const (
	defaultChainFile  = "chaingang.txt"
	chainFileUsage   = "filename containing stepping stones for the request"
)


func init() {
}


func usageExit() {

}


func validateUrl(a string) {

	_, err := http.Get(a)

	if err != nil {
		log.Fatalln(os.Args[0], "Invalid URL.")
	}
}


func validateFile(a string) {
	//fmt.Println(a)
	_, err :=os.Open(a)
	if err != nil {
		log.Fatalln(os.Args[0], "Could not open file.")
	}
}


func storeResponse(sstones ssnet.SteppingStones, url string) {
	//for {
		//conn.Write([]byte(url))
	//}
}


func main() {

	// has either 1 or 2 args
	// 		1 arg: only has url
	//		2 args: url and file flags
	if len(os.Args) != 2 && len(os.Args) != 3 {
		log.Fatalln(os.Args[0], "Incorrect amount of arguments.")
	}

	url := flag.String("u", "", "url")
	chainFileName := flag.String("c", "../ssnet/examples/chaingang.txt", "chainfile")
	flag.Parse()

	validateUrl(*url)
	validateFile(*chainFileName)

	ss, _ := ssnet.ReadChainFile(*chainFileName)

	// create connections between servers and stones
	//go storeResponse(ss, *url)

	for i := 0; i < ssnet.GetSteps(); i++ {
		conn, _ := ssnet.SendReqToRandomStone(*url, ss)		// ssnet.GetSS()
		go ssnet.HandleReqFromConn(conn)
		conn.Write([]byte(*url))
		//fmt.Println("awget",ss)
	}
}