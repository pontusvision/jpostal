#!/usr/bin/env bash
./bootstrap.sh
if [[ $(uname -o) == 'Msys' ]]; then
./configure --libdir=$(pwd)/src/main/resources/lib/win-amd64 --disable-static --enable-shared

else
./configure --libdir=$(pwd)/src/main/resources/lib/linux-x86_64
if
make clean install

