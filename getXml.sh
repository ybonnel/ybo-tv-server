
WORK_DIR=src/main/webapp/local-xml

rm -rf ${WORK_DIR} 2>/dev/null

mkdir -p ${WORK_DIR}

curl http://ybo-tv.ybonnel.fr/local-xml/ | grep "compressed" | cut -d'"' -f8 | while read a
do
	curl http://ybo-tv.ybonnel.fr/local-xml/$a > ${WORK_DIR}/$a
done

