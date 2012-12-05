
WORK_DIR=src/main/webapp/local-xml

rm -rf ${WORK_DIR} 2>/dev/null

mkdir -p ${WORK_DIR}

curl http://transports-rennes.ic-s.org/version/ybo-tv/xml/ | grep "compressed" | cut -d'"' -f8 | while read a
do
	curl http://transports-rennes.ic-s.org/version/ybo-tv/xml/$a > ${WORK_DIR}/$a
done

