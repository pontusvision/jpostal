#!/usr/bin/env bash
./bootstrap.sh
./configure --libdir=$(pwd)/src/main/resources/lib/linux-x86_64
make clean install
