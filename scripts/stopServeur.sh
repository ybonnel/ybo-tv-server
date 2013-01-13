ps -ef | grep java | grep ybo-tv-server | grep -v grep | while read a b c
do
	kill -15 $b
done
