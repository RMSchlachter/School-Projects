package ssnet

import (
	"net"
	"fmt"
	"bufio"
	"strings"
	"os"
	"log"
	"strconv"
	"math/rand"
	"time"
)

var (
	totalSteps = 0
	stepCount = 0
	steps SteppingStones
)


func GetSS() SteppingStones{
	return steps
}


func GetSteps() int {
	return totalSteps
}


type steppingStone struct {
	Addr net.IP
	Port uint16
}


type SteppingStones []steppingStone


func (s steppingStone) String() string {
	return fmt.Sprintf("%s:%d", s.Addr, s.Port)
}


func SendReqToRandomStone(url string, sstones SteppingStones) (conn net.Conn, err error) {

	fmt.Println(stepCount)
	if stepCount == 0 {
		// get URL

		return
	} else {
		rand.Seed(time.Now().UTC().UnixNano())
		index := rand.Intn(stepCount)
		stone := sstones[index]
		sstones[index] = sstones[stepCount-1] 	// Replace index with last stone
		sstones = sstones[:stepCount-1]   		// Chop off the last stone
		stepCount--
		stoneString := stone.String()

		conn, err = net.Dial("tcp", stoneString)
		//fmt.Println("dialing to",stone)
		//fmt.Println(sstones)
	}

	return conn, err
}


func HandleReqFromConn(conn net.Conn) (err error) {
	channel := make(chan net.Conn)
	channel <- conn
	buf := bufio.NewReader(conn)
	message, err := buf.ReadString('\n')
	conn.Write([]byte(message))
	fmt.Println(message)

	return err
}


func newSteppingStone(ipstr string, portstr string) (ss steppingStone, err error) {

	address := net.ParseIP(ipstr)
	temp, _ := strconv.Atoi(portstr)
	port := uint16(temp)
	returnStone := steppingStone{address, port}
	return returnStone, err
}


func ReadChainFile(fname string) (SteppingStones, error) {

	toScan, err := os.Open(fname)
	if err != nil {
		log.Fatal(err)
	}

	reader := bufio.NewReader(toScan)

	for {
		line, err := reader.ReadString('\n')
		if err != nil {
			break
		}

		if len(line) == 2 {
			//fmt.Println("1 arg")
			trimmedLine := strings.TrimSuffix(line, "\n")
			stepCount, _ = strconv.Atoi(trimmedLine)
			if stepCount <= 0 {
				log.Fatal("No steps specified.")
			}
			continue
		}

		splitString := strings.Split(line, " ")
		ipstr, portstr := splitString[0], splitString[1]
		portString := strings.TrimSuffix(portstr, "\n")
		//fmt.Println("ReadChainFile: ipstr:", ipstr, "portstr:", portString);
		stone, err := newSteppingStone(ipstr, portString)
		steps = append(steps, stone)
	}

	return steps, err
}
