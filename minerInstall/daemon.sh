#!/bin/sh

while : 
do
  stillRunning=$(ps -ef |grep "bcdn" |grep -v "grep")
  if [ ! -n "$stillRunning" ] ; then
  	cd /root/opt/M_BerryMiner_ubuntu_v1_0/server/
  	nohup ./bcdn &
  fi
sleep 2 
done