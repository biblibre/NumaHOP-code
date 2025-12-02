#!/usr/bin/env sh

cat<<TXT | nvim -es "$1"
%s/"\(\s\|\n\)*+\s"//g
%s/Query("\([^"]*\)")/Query("""\r\t\t\1\r\t\t""")/
g/^\s*from/norm ^wwyw^PIselect 
g/\c^\s*\(select\|delete\|update\)/s/\c\(inner join\|left join\|join\|where\|order by\)/\r\t\t\1/g
wq
TXT

# Fix Native query by hand.
# Run mvn spring-javaformat.
