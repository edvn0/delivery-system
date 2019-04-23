#!/bin/sh

cat /home/robot/java/splash.txt
java -Xms64m -Xmx64m -XX:+UseSerialGC -Xshare:off -noverify -cp "/home/robot/java/programs/shipping-system-2.5.3-all.jar" com.bth.ShippingSystemMain.ShippingSystemMain
exit $?
