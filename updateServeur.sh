mvn clean install assembly:single 
if [ $? -eq 0 ]
then
	cp scripts/* /home/ybonnel/ybo-tv/app/ybo-tv-server/
	cp target/ybo-tv-server.jar /home/ybonnel/ybo-tv/app/ybo-tv-server/ybo-tv-server.jar.new
	cd /home/ybonnel/ybo-tv/app/ybo-tv-server
	./stopServeur.sh
	mv ybo-tv-server.jar ybo-tv-server.jar.old
	mv ybo-tv-server.jar.new ybo-tv-server.jar
	./startServeur.sh
	sleep 20
	tail -10 serveur.log
fi

