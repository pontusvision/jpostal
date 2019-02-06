Here are the mods I had to make:

1) you have to run the pontus-postal and build it separately.
copy the ./src/.lib/libpostal-1.dll (from the pontus-postal project) to  the ./src/main/resources/lib/win-amd64/ directory in this project:
cp ../pontus-postal/src/.lib/libpostal-1.dll ./src/main/resources/lib/win-amd64/libpostal.dll

2) once you build the postal.dll, then we have to run the app here.

Here are the mods:

1) build.gradle:
```
//compileJava.dependsOn(buildJniLib)
```

2) run the following:
```
./configure --libdir=$(pwd)/src/main/resources/lib/win-amd64  --disable-static --enable-shared
```

3) make the following files look like this:
```
$ cat src/main/c/Makefile.am
lib_LTLIBRARIES = libjpostal_parser.la libjpostal_expander.la

libjpostal_expander_la_SOURCES = jpostal_AddressExpander.c
libjpostal_expander_la_CFLAGS = $(LIBPOSTAL_CFLAGS)
libjpostal_expander_la_LIBADD = $(LIBPOSTAL_LIBS)
libjpostal_expander_la_LDFLAGS = -no-undefined -L/c/work/pontus-git/pontusvision-x/tech/pontus-jpostal/src/main/resources/lib/win-amd64/ -lpostal

libjpostal_parser_la_SOURCES = jpostal_AddressParser.c
libjpostal_parser_la_CFLAGS = $(LIBPOSTAL_CFLAGS)
libjpostal_parser_la_LIBADD = $(LIBPOSTAL_LIBS)
libjpostal_parser_la_LDFLAGS = -no-undefined -L/c/work/pontus-git/pontusvision-x/tech/pontus-jpostal/src/main/resources/lib/win-amd64/ -lpostal
```

... and change the following in configure:
```
#PKG_CHECK_MODULES(LIBPOSTAL, libpostal,, as_fn_error $? "Could not find libpostal" "$LINENO" 5)
```
4) then re-run the same command as(2)

5) then make install 

6) then  copy the libs
```
cp ./src/main/c/.libs/libjpostal_parser-0.dll ./src/main/resources/lib/win-amd64/libjpostal_parser.dll
cp ./src/main/c/.libs/libjpostal_parser-0.dll ./src/main/resources/lib/win-amd64/libjpostal_parser.dll
```
7) run gradle:
```
./gradlew install
```
