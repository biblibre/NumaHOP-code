#!/bin/sh

BUILD_PACKAGES="gcc g++ make openjdk-8-jdk-headless maven swig libyaz-dev"

apt-get update
apt-get install -y $BUILD_PACKAGES libxslt1.1

JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64

cd /root
curl -s https://ftp.indexdata.com/pub/yaz/yaz-4.2.69.tar.gz | tar xzf -
cd yaz-4.2.69 && ./configure && make install

cd /root
curl -s https://ftp.indexdata.com/pub/yaz4j/yaz4j-1.5.tar.gz | tar xzf -
cd yaz4j-1.5 && mvn package -DskipTests && cd unix && mvn package -DskipTests
mkdir -p /usr/java/packages/lib
cp target/libyaz4j.so /usr/java/packages/lib/

cd /root
rm -rf yaz-4.2.69 yaz4j-1.5 .m2

apt-get remove --purge -y $BUILD_PACKAGES
apt-get autoremove --purge -y
apt-get clean
