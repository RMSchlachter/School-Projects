package main

import (
	"net"
	"flag"
	"os"
	"strconv"
	"fmt"
	"strings"
	"log"
	"bufio"
)

var (
	port     uint64
	portString string
	listener net.Listener
)

func init() {
	const (
		portDefaultMsg = "port to bind the server at. Defaults to 8989"
	)
}

func cleanup() {

}

func main() {

	// 1. ss takes one optional argument, the port it will listen on.
	if len(os.Args) != 1 {						// have port number arg
		portString = os.Args[1]
		s := flag.Arg(1)
		port, _ = strconv.ParseUint(s, 16, 16)
	} else {									// no port number arg
		portString = strconv.Itoa(8989)
		//port = 8989
	}

	host, _ := os.Hostname()
	addrs, _ := net.LookupIP(host)
	segment := strings.SplitAfter(addrs[0].String(), " ")
	ipString := strings.Join(segment," ")

	// 2. ss prints the hostname and port, it is running on.
	fmt.Println("Host IP:",  ipString)
	fmt.Println("Port:", portString)

	// 4. Create a loop statement and set the socket in listen mode.
	ln, _ := net.Listen("tcp", ":"+portString)

	for {
		conn, err := ln.Accept()
		if err != nil {
			log.Fatalln(err)
		}

		go func() {
			buf := bufio.NewReader(conn)

			for {
				message, err := buf.ReadString('\n')
				fmt.Println("Message received from", conn.RemoteAddr(),":", message)

				if err != nil {
					fmt.Printf("Disconnected.\n")
					break
				}
				conn.Write([]byte(message))
			}
		}()


	}
}
