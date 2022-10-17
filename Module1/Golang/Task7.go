package main

import (
	"fmt"
	"time"
)

type Tunnel struct {
	number        int
	trainsOnLeft  int
	trainsOnRight int
}

func (tunnel Tunnel) Work(OtherTunnel *Tunnel) {
	var trainNum = 0
	for tunnel.trainsOnLeft > 0 || tunnel.trainsOnRight > 0 {
		if tunnel.trainsOnLeft > 0 {
			tunnel.trainsOnLeft--
			trainNum++
			fmt.Println("Train", trainNum, "entered tunnel", tunnel.number, "on the left")
			if tunnel.trainsOnLeft > 0 {
				OtherTunnel.trainsOnLeft++
				tunnel.trainsOnLeft--
			}
			time.Sleep(time.Second)
		}
		if tunnel.trainsOnRight > 0 {
			tunnel.trainsOnRight--
			trainNum++
			fmt.Println("Train", trainNum, "entered tunnel", tunnel.number, "on the right")
			if tunnel.trainsOnRight > 0 {
				OtherTunnel.trainsOnRight++
				tunnel.trainsOnRight--
			}
			time.Sleep(time.Second)
		}
	}
}

func main() {

	var Tunnel1 = Tunnel{number: 1, trainsOnLeft: 5, trainsOnRight: 5}
	var Tunnel2 = Tunnel{number: 2, trainsOnLeft: 5, trainsOnRight: 5}

	go Tunnel1.Work(&Tunnel2)
	Tunnel2.Work(&Tunnel1)
}
